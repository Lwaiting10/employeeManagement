package com.iweb.controller;

import com.iweb.Util.CommunicationUtil;
import com.iweb.entity.Attendance;
import com.iweb.entity.Employee;
import com.iweb.entity.Salary;
import com.iweb.entity.User;
import com.iweb.service.AttendanceService;
import com.iweb.service.EmployeeService;
import com.iweb.service.SalaryService;
import com.iweb.service.UserService;

import java.io.IOException;
import java.net.Socket;

import static com.iweb.Util.Log.log;

/**
 * @author Liu Xiong
 * @date 27/11/2023 下午11:06
 */
public class PersonalInformationController {
    public static void personalInformationManage(Socket socket, User user) throws IOException {
        while (true) {
            String key = CommunicationUtil.receive(socket);
            switch (key) {
                // 0 - 退出
                case "0": {
                    CommunicationUtil.send(socket, "exit");
                    return;
                }
                // 1 - 查看个人信息
                case "1": {
                    CommunicationUtil.send(socket, "information");
                    // 跳转个人信息展示
                    showInformation(socket, user);
                    break;
                }
                // 2 - 更改密码
                case "2": {
                    CommunicationUtil.send(socket, "changePassword");
                    String inputPassword = CommunicationUtil.receive(socket);
                    if (UserService.passwordMatch(user, inputPassword)) {
                        // 密码匹配
                        CommunicationUtil.send(socket, "true");
                        // 接收新密码
                        String newPassword = CommunicationUtil.receive(socket);
                        // 临时存储原密码
                        String tempPassword = user.getPassword();
                        // 密码更改
                        user.setPassword(newPassword);
                        if (UserService.passwordChange(user)) {
                            // 更改成功,返回true
                            CommunicationUtil.send(socket, "true");
                            log(socket.getInetAddress() + "更改账号:" + user.getUsername() + "的密码为: " + user.getPassword());
                        } else {
                            // 更改失败.返回false 改回原密码
                            user.setPassword(tempPassword);
                            CommunicationUtil.send(socket, "false");
                        }
                    } else {
                        // 不匹配
                        CommunicationUtil.send(socket, "false");
                    }
                    break;
                }
                default:
                    CommunicationUtil.send(socket, "wrong");
            }
        }
    }

    private static void showInformation(Socket socket, User user) throws IOException {
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
                // 1 - 查看员工信息
                case "1": {
                    CommunicationUtil.send(socket, "employee");
                    // 获取登录用户的员工信息
                    Employee employee = EmployeeService.selectById(user.getUsername());
                    if (employee != null) {
                        // 发送个人信息
                        CommunicationUtil.send(socket, employee.toString());
                    } else {
                        CommunicationUtil.send(socket, "null");
                    }
                    break;
                }
                // 2 - 查看薪资信息
                case "2": {
                    CommunicationUtil.send(socket, "salary");
                    // 获取登录用户的薪资信息
                    Salary salary = SalaryService.selectByEmpId(user.getUsername());
                    if (salary != null) {
                        // 发送薪资信息
                        CommunicationUtil.send(socket, salary.toString());
                    } else {
                        CommunicationUtil.send(socket, "null");
                    }
                    break;
                }
                case "3": {
                    CommunicationUtil.send(socket, "attendance");
                    // 获取登录用户的考勤信息
                    Attendance attendance = AttendanceService.selectByEmpId(user.getUsername());
                    if (attendance != null) {
                        // 发送考勤信息
                        CommunicationUtil.send(socket, attendance.toString());
                    } else {
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
