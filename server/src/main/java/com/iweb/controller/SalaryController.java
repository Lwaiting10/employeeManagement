package com.iweb.controller;

import com.iweb.Util.CommunicationUtil;
import com.iweb.Util.TransformUtil;
import com.iweb.entity.Salary;
import com.iweb.entity.User;
import com.iweb.service.EmployeeService;
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
            if ("".equals(key)) {
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
                    selectAll(socket, user);
                    break;
                }
                // 2 - 根据员工id查找
                case "2": {
                    CommunicationUtil.send(socket, "selectByEmpId");
                    selectByEmpId(socket, user);
                    break;
                }
                // 3 - 根据工资发放日期查找(模糊查找)
                case "3": {
                    CommunicationUtil.send(socket, "selectByPaymentDate");
                    selectByPaymentDate(socket, user);
                    break;
                }
                // 4 - 添加信息
                case "4": {
                    CommunicationUtil.send(socket, "insert");
                    insert(socket, user);
                    break;
                }
                // 5 - 更改信息
                case "5": {
                    CommunicationUtil.send(socket, "update");
                    update(socket, user);
                    break;
                }
                // 6 - 删除信息
                case "6": {
                    CommunicationUtil.send(socket, "delete");
                    delete(socket, user);
                    break;
                }
                default:
                    CommunicationUtil.send(socket, "wrong");
            }
        }
    }

    /**
     * 删除信息
     */
    private static void delete(Socket socket, User user) throws IOException {
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
                return;
            }
        } else {
            // 没有找到，返回null
            CommunicationUtil.send(socket, "null");
        }
    }

    /**
     * 更改信息
     */
    private static void update(Socket socket, User user) throws IOException {
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
    }

    /**
     * 新增信息
     */
    private static void insert(Socket socket, User user) throws IOException {
        // 获取客户端发来的新增信息
        Salary newSalary = TransformUtil.getSalary(CommunicationUtil.receive(socket));
        // 新增操作
        if (newSalary != null) {
            if (EmployeeService.selectById(newSalary.getEmpId()) == null) {
                // 不可以新增 员工id不存在的信息
                CommunicationUtil.send(socket, "absent");
            } else if (SalaryService.selectByEmpId(newSalary.getEmpId()) != null) {
                // 该员工已经有薪资信息 不可再次添加
                CommunicationUtil.send(socket, "repeat");
            } else {
                if (SalaryService.insertSalary(newSalary)) {
                    // 新增成功,返回true
                    CommunicationUtil.send(socket, "true");
                } else {
                    // 新增失败,返回false
                    CommunicationUtil.send(socket, "false");
                }
            }
        } else {
            // 数据有误
            CommunicationUtil.send(socket, "incorrectData");
        }
    }

    /**
     * 根据工资发放日期查找(模糊查找)
     */
    private static void selectByPaymentDate(Socket socket, User user) throws IOException {
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
    }

    /**
     * 根据员工id查找
     */
    private static void selectByEmpId(Socket socket, User user) throws IOException {
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
    }

    /**
     * 查看所有信息
     */
    private static void selectAll(Socket socket, User user) throws IOException {
        // 发送所有信息
        List<Salary> salaries = SalaryService.getAllSalary();
        if (salaries != null) {
            CommunicationUtil.send(socket, salaries.toString());
        } else {
            // 没有数据返回 null
            CommunicationUtil.send(socket, "null");
        }
    }
}

