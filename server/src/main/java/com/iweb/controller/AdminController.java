package com.iweb.controller;

import com.iweb.Util.CommunicationUtil;
import com.iweb.entity.User;


import java.io.IOException;
import java.net.Socket;

import static com.iweb.Util.Log.log;

/**
 * @author Liu Xiong
 * @date 26/11/2023 下午11:45
 */
public class AdminController {
    public static void adminController(Socket socket, User user) throws IOException {
        System.out.println("管理员:" + user.getUsername() + "登入了系统！");
        log(socket.getInetAddress() + "登录管理员账号:" + user.getUsername());
        while (true) {
            String key = CommunicationUtil.receive(socket);
            switch (key) {
                // 0 - 退出
                case "0": {
                    CommunicationUtil.send(socket, "exit");
                    System.out.println("管理员：" + user.getUsername() + "登出系统！");
                    log(socket.getInetAddress() + "登出管理员账号:" + user.getUsername());
                    return;
                }
                // 1 - 员工管理
                case "1": {
                    CommunicationUtil.send(socket, "employeeManage");
                    EmployeeManageController.employeeManage(socket, user);
                    break;
                }
                // 2 - 个人信息管理
                case "2": {
                    CommunicationUtil.send(socket, "personalInformationManage");
                    PersonalInformationController.personalInformationManage(socket, user);
                    break;
                }
                // 3 - 考勤管理
                case "3": {
                    CommunicationUtil.send(socket, "attendanceManage");
                    AttendanceController.attendanceManage(socket, user);
                    break;
                }
                // 4 - 工资管理
                case "4": {
                    CommunicationUtil.send(socket, "salaryManage");
                    SalaryController.salaryManage(socket, user);
                    break;
                }
                // 5 - 用户管理
                case "5": {
                    CommunicationUtil.send(socket, "userManage");
                    UserController.userManage(socket, user);
                    break;
                }
                default:
                    CommunicationUtil.send(socket, "wrong");
            }
        }
    }
}
