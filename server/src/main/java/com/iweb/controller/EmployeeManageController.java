package com.iweb.controller;

import com.iweb.Util.CommunicationUtil;
import com.iweb.Util.TransformUtil;
import com.iweb.entity.Employee;
import com.iweb.entity.User;
import com.iweb.service.EmployeeService;

import java.io.IOException;
import java.net.Socket;
import java.util.List;

/**
 * @author Liu Xiong
 * @date 27/11/2023 下午11:01
 */
public class EmployeeManageController {
    /**
     * 员工管理
     */
    public static void employeeManage(Socket socket, User user) throws IOException {
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
                // 1 - 查看所有员工
                case "1": {
                    // 将所有员工信息传给客户端
                    CommunicationUtil.send(socket, "allEmployee");
                    // 调用查询共服务
                    List<Employee> employees = EmployeeService.getAllEmployee();
                    // 返回数据
                    if (employees == null) {
                        CommunicationUtil.send(socket, "null");
                    } else {
                        CommunicationUtil.send(socket, employees.toString());
                    }
                    break;
                }
                // 2 - 根据姓名查找员工
                case "2": {
                    CommunicationUtil.send(socket, "selectEmployeeByName");
                    // 接收客户端发来的查询关键字
                    String name = CommunicationUtil.receive(socket);
                    // 调用查询服务
                    List<Employee> employees = EmployeeService.selectByName(name);
                    // 返回数据
                    if (employees == null) {
                        CommunicationUtil.send(socket, "null");
                    } else {
                        CommunicationUtil.send(socket, employees.toString());
                    }
                    break;
                }
                // 3 - 根据部门查找员工
                case "3": {
                    CommunicationUtil.send(socket, "selectEmployeeByDepartment");
                    // 接收客户端发来的查询关键字
                    String department = CommunicationUtil.receive(socket);
                    // 调用查询服务
                    List<Employee> employees = EmployeeService.selectByDepartment(department);
                    // 返回数据
                    if (employees == null) {
                        CommunicationUtil.send(socket, "null");
                    } else {
                        CommunicationUtil.send(socket, employees.toString());
                    }
                    break;
                }
                // 4 - 添加员工
                case "4": {
                    CommunicationUtil.send(socket, "insertEmployee");
                    // 获取客户端发来的新增员工信息
                    String inputEmployeeInfo = CommunicationUtil.receive(socket);
                    // 将信息转换成员工对象
                    Employee inputEmployee = TransformUtil.getEmployee(inputEmployeeInfo);
                    // 调用新增员工服务功能
                    if (inputEmployee != null) {
                        if (EmployeeService.insertEmployee(inputEmployee)) {
                            CommunicationUtil.send(socket, "success");
                        } else {
                            CommunicationUtil.send(socket, "fail");
                        }
                    }
                    break;
                }
                // 5 - 更改员工信息
                case "5": {
                    CommunicationUtil.send(socket, "updateEmployee");
                    // 获取客户端发来的需要更改的员工id
                    String inputEmployeeId = CommunicationUtil.receive(socket);
                    // 检查该id员工是否存在
                    if (inputEmployeeId != null) {
                        Employee employee = EmployeeService.selectById(Integer.parseInt(inputEmployeeId));
                        if (employee != null) {
                            // 该员工存在，发送给客户端
                            CommunicationUtil.send(socket, employee.toString());
                            // 接收客户端发来的修改后的信息并更改信息
                            employee = TransformUtil.getEmployee(CommunicationUtil.receive(socket));
                            // 将更新信息写入数据库
                            if (EmployeeService.updateEmployee(employee)) {
                                // 写入成功，返回true
                                CommunicationUtil.send(socket, "true");
                            } else {
                                // 写入失败,返回false
                                CommunicationUtil.send(socket, "false");
                            }
                        } else {
                            // 不存在。返回null
                            CommunicationUtil.send(socket, "null");
                            break;
                        }
                    } else {
                        // 接收的消息为无效内容
                        CommunicationUtil.send(socket, "wrong");
                    }
                    break;
                }
                // 6 - 删除员工
                case "6": {
                    CommunicationUtil.send(socket, "deleteEmployee");
                    // 获取客户端发来的需要删除的员工id
                    String inputEmployeeId = CommunicationUtil.receive(socket);
                    // 检查该id员工是否存在
                    if (inputEmployeeId != null) {
                        Employee employee = EmployeeService.selectById(Integer.parseInt(inputEmployeeId));
                        if (employee != null) {
                            // 该员工存在，发送给客户端
                            CommunicationUtil.send(socket, employee.toString());
                            // 获取客户端的确认信息
                            if ("y".equalsIgnoreCase(CommunicationUtil.receive(socket))) {
                                // 确认删除
                                if (EmployeeService.deleteEmployee(employee.getId())) {
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
                            // 不存在。返回null
                            CommunicationUtil.send(socket, "null");
                            break;
                        }
                    } else {
                        // 接收的消息为无效内容
                        CommunicationUtil.send(socket, "wrong");
                    }
                    break;
                }
                default:
                    CommunicationUtil.send(socket, "wrong");
            }
        }

    }
}
