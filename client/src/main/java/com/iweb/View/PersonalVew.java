package com.iweb.View;

import com.iweb.Util.*;
import com.iweb.entity.Attendance;
import com.iweb.entity.Employee;
import com.iweb.entity.Salary;

import java.io.IOException;
import java.util.Scanner;

import static com.iweb.Util.PrintUtil.log;

/**
 * @author Liu Xiong
 * @date 27/11/2023 下午11:11
 */
public class PersonalVew {
    private final static Scanner SCANNER = new Scanner(System.in);

    /**
     * 个人信息管理界面
     */
    public static void manageView() throws IOException {
        log("===================个人信息管理===================");
        while (true) {
            log("0 - 退出");
            log("1 - 查看个人信息");
            log("2 - 更改密码");
            String message = CommunicationUtil.chooseAndGetMessage();
            if ("exit".equals(message)) {
                return;
            }
            // 1 - 查看个人信息
            if ("information".equals(message)) {
                // 跳转个人信息页面
                informationView();
            }
            // 2 - 更改密码
            if ("changePassword".equals(message)) {
                // 跳转更改密码页面
                changePasswordView();
            }
        }
    }

    /**
     * 更改密码页面
     */
    private static void changePasswordView() throws IOException {
        log("输入原密码:");
        // 发送原密码进行匹配
        CommunicationUtil.send(ScannerUtil.getPassword());
        // 接收服务器反馈
        if ("true".equals(CommunicationUtil.receive())) {
            // 密码匹配
            log("输入新密码:");
            // 发送新密码进行更改
            CommunicationUtil.send(ScannerUtil.getPassword());
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
            String message = CommunicationUtil.chooseAndGetMessage();
            // 0 - 退出
            if ("exit".equals(message)) {
                return;
            }
            // 1 - 查看员工信息
            if ("employee".equals(message)) {
                // 接收员工信息
                Employee employee = TransformUtil.getEmployee(CommunicationUtil.receive());
                // 展示个人信息
                ShowUtil.showEmployee(employee);
            }
            // 2 - 查看薪资信息
            if ("salary".equals(message)) {
                // 接收薪资信息
                Salary salary = TransformUtil.getSalary(CommunicationUtil.receive());
                // 展示薪资信息
                ShowUtil.showSalary(salary);
            }
            // 3 - 查看考勤信息
            if ("attendance".equals(message)) {
                // 接收考勤信息
                Attendance attendance = TransformUtil.getAttendance(CommunicationUtil.receive());
                // 展示考勤信息
                ShowUtil.showAttendance(attendance);
            }
        }
    }
}
