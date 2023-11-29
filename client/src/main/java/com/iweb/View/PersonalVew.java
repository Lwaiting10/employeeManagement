package com.iweb.View;

import com.iweb.Util.*;
import com.iweb.entity.Attendance;
import com.iweb.entity.Employee;
import com.iweb.entity.Salary;

import java.io.IOException;

import static com.iweb.Util.PrintUtil.log;

/**
 * @author Liu Xiong
 * @date 27/11/2023 下午11:11
 */
public class PersonalVew {

    /**
     * 个人信息管理界面
     */
    public static void manageView() throws IOException {
        log("===================个人信息管理===================");
        while (true) {
            log("0 - 退出");
            log("1 - 查看个人信息");
            log("2 - 更改密码");
            switch (CommunicationUtil.chooseAndGetMessage()) {
                case "exit":
                    return;
                // 1 - 查看个人信息
                case "information":
                    // 跳转个人信息页面
                    informationView();
                    break;
                // 2 - 更改密码
                case "changePassword":
                    // 跳转更改密码页面
                    changePasswordView();
                    break;
            }
        }
    }

    /**
     * 更改密码页面
     */
    private static void changePasswordView() throws IOException {
        log("输入原密码:");
        // 发送原密码进行匹配
        CommunicationUtil.send(ScannerUtil.getString());
        // 接收服务器反馈
        if ("true".equals(CommunicationUtil.receive())) {
            // 密码匹配
            log("输入新密码:");
            // 发送新密码进行更改
            CommunicationUtil.send(ScannerUtil.getString());
            // 接收服务器反馈
            if ("true".equals(CommunicationUtil.receive())) {
                log("更改成功!");
            } else {
                log("更改失败!");
            }
        } else {
            log("原密码错误!");
        }
    }

    /**
     * 展示个人信息
     */
    private static void informationView() throws IOException {
        while (true) {
            log("0 - 退出");
            log("1 - 查看员工信息");
            log("2 - 查看薪资信息");
            log("3 - 查看考勤信息");
            switch (CommunicationUtil.chooseAndGetMessage()) {
                // 0 - 退出
                case "exit":
                    return;
                // 1 - 查看员工信息
                case "employee": {
                    // 接收员工信息
                    Employee employee = TransformUtil.getEmployee(CommunicationUtil.receive());
                    // 展示个人信息
                    ShowUtil.showEmployee(employee);
                    break;
                }
                // 2 - 查看薪资信息
                case "salary": {
                    // 接收薪资信息
                    Salary salary = TransformUtil.getSalary(CommunicationUtil.receive());
                    // 展示薪资信息
                    ShowUtil.showSalary(salary);
                    break;
                }
                // 3 - 查看考勤信息
                case "attendance": {
                    // 接收考勤信息
                    Attendance attendance = TransformUtil.getAttendance(CommunicationUtil.receive());
                    // 展示考勤信息
                    ShowUtil.showAttendance(attendance);
                    break;
                }
            }
        }
    }
}
