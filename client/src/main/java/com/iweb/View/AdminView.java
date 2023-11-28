package com.iweb.View;

import com.iweb.Util.*;
import com.iweb.entity.Employee;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

import static com.iweb.Util.PrintUtil.log;

/**
 * @author Liu Xiong
 * @date 26/11/2023 下午2:32
 */
public class AdminView {
    private final static Scanner SCANNER = new Scanner(System.in);

    public static void userInfoView() {
        log("欢迎管理员," + DataUtil.user.getUsername() + "!");
        while (true) {
            log("请输入您要操作的功能序号：");
            log("0 - 退出");
            log("1 - 员工管理");
            log("2 - 个人信息管理");
            log("3 - 考勤管理");
            log("4 - 工资管理");
            log("5 - 用户管理");
            String message = CommunicationUtil.chooseAndGetMessage();
            if ("exit".equals(message)) {
                return;
            }
            // 跳转员工管理
            if ("employeeManage".equals(message)) {
                EmployeeView.mainView();
            }
            // 跳转个人信息管理
            if ("personalInformationManage".equals(message)) {
                PersonalVew.manageView();
            }
            // 跳转考勤管理
            if ("attendanceManage".equals(message)) {
                AttendanceView.mainView();
            }
            // 跳转工资管理
            if ("salaryManage".equals(message)) {
                SalaryView.mainView();
            }
            // 跳转用户管理
            if ("userManage".equals(message)) {
                UserView.mainView();
            }
        }
    }
}
