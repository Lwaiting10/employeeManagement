package com.iweb.controller;

import com.iweb.Util.CommunicationUtil;
import com.iweb.Util.TransformUtil;
import com.iweb.entity.Salary;
import com.iweb.entity.User;
import com.iweb.service.SalaryService;

import java.io.IOException;
import java.net.Socket;
import java.util.List;

/**
 * @author Liu Xiong
 * @date 28/11/2023 下午2:48
 */
public class SalaryController {
    public static void salaryManage(Socket socket, User user) throws IOException {
        while (true) {
            String key = CommunicationUtil.receive(socket);
            if (key == null) {
                CommunicationUtil.send(socket, "wrong");
                continue;
            }
            switch (key) {
                // 0 - 退出
                case "0": {
                    CommunicationUtil.send(socket, "exit");
                    return;
                }
                // 1 - 查看所有信息
                case "1": {
                    CommunicationUtil.send(socket, "selectAll");
                    // 发送所有信息
                    List<Salary> salaries = SalaryService.getAllSalary();
                    if (salaries != null) {
                        CommunicationUtil.send(socket, salaries.toString());
                    } else {
                        // 没有数据返回 null
                        CommunicationUtil.send(socket, "null");
                    }
                    break;
                }
                // 2 - 根据员工id查找
                case "2": {
                    CommunicationUtil.send(socket, "selectByEmpId");
                    // 接收要查询的信息
                    String inputEmpIdStr = CommunicationUtil.receive(socket);
                    // 查询改id是否存在
                    Salary salary = SalaryService.selectByEmpId(Integer.parseInt(inputEmpIdStr));
                    if (salary != null) {
                        // 存在，返回该对象
                        CommunicationUtil.send(socket, salary.toString());
                    } else {
                        // 不存在,返回null
                        CommunicationUtil.send(socket, "null");
                    }
                    break;
                }
                // 3 - 根据工资发放日期查找(模糊查找)
                case "3": {
                    CommunicationUtil.send(socket, "selectByPaymentDate");
                    // 接收查找关键字
                    String dateStr = CommunicationUtil.receive(socket);
                    // 查找
                    List<Salary> salaries = SalaryService.selectByPaymentDate(dateStr);
                    if (salaries != null) {
                        // 查找到数据 返回
                        CommunicationUtil.send(socket, salaries.toString());
                    } else {
                        // 没有查找到 返回null
                        CommunicationUtil.send(socket, "null");
                    }
                    break;
                }
                // 4 - 添加信息
                case "4": {
                    // TODO: 28/11/2023 做相应的限制管理
                    CommunicationUtil.send(socket, "insert");
                    // 获取客户端发来的新增考勤信息
                    Salary newSalary = TransformUtil.getSalary(CommunicationUtil.receive(socket));
                    // 新增操作
                    if (newSalary != null) {
                        if (SalaryService.insertSalary(newSalary)) {
                            // 新增成功,返回true
                            CommunicationUtil.send(socket, "true");
                        } else {
                            // 新增失败,返回false
                            CommunicationUtil.send(socket, "false");
                        }
                    } else {
                        // 数据有误
                        CommunicationUtil.send(socket, "false");
                    }
                    break;
                }
                // 5 - 更改信息
                case "5": {
                    CommunicationUtil.send(socket, "update");
                    // 接收员工id
                    int inputEmpId = Integer.parseInt(CommunicationUtil.receive(socket));
                    // 查找该员工信息
                    Salary salary = SalaryService.selectByEmpId(inputEmpId);
                    if (salary != null) {
                        // 找到，返回信息
                        CommunicationUtil.send(socket, salary.toString());
                        // 接收修改后的数据
                        Salary salaryUpdate = TransformUtil.getSalary(CommunicationUtil.receive(socket));
                        // 修改操作
                        if (SalaryService.updateSalary(salaryUpdate)) {
                            // 修改成功,返回true
                            CommunicationUtil.send(socket, "true");
                        } else {
                            // 修改失败,返回false
                            CommunicationUtil.send(socket, "false");
                        }
                    } else {
                        // 没有找到，返回null
                        CommunicationUtil.send(socket, "null");
                    }
                    break;
                }
                // 6 - 删除信息
                case "6": {
                    CommunicationUtil.send(socket, "delete");
                    // 接收员工id
                    int inputEmpId = Integer.parseInt(CommunicationUtil.receive(socket));
                    // 查找该员工信息
                    Salary salary = SalaryService.selectByEmpId(inputEmpId);
                    if (salary != null) {
                        // 找到，返回信息
                        CommunicationUtil.send(socket, salary.toString());
                        // 获取客户端的确认信息
                        if ("y".equalsIgnoreCase(CommunicationUtil.receive(socket))) {
                            // 确认删除
                            if (SalaryService.deleteSalary(salary.getEmpId())) {
                                // 删除成功，返回true
                                CommunicationUtil.send(socket, "true");
                            } else {
                                // 删除失败,返回false
                                CommunicationUtil.send(socket, "false");
                            }
                        } else {
                            // 取消删除
                            break;
                        }
                    } else {
                        // 没有找到，返回null
                        CommunicationUtil.send(socket, "null");
                    }
                    break;
                }
                default:
                    CommunicationUtil.send(socket, "wrong");
            }
        }
    }
}

