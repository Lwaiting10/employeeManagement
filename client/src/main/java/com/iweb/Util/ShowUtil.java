package com.iweb.Util;

import com.iweb.entity.Attendance;
import com.iweb.entity.Employee;
import com.iweb.entity.Salary;
import com.iweb.entity.User;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static com.iweb.Util.PrintUtil.log;

/**
 * @author Liu Xiong
 * @date 27/11/2023 下午11:17
 */
public class ShowUtil {
    /**
     * 打印员工信息
     */
    public static void showEmployee(List<Employee> employees) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if (employees == null) {
            log("未查询到信息！");
            return;
        }
        System.out.println("员工编号\t姓名\t性别\t部门\t入职时间\t\t出生日期");
        for (Employee e : employees) {
            System.out.println(e.getId() + "\t" + e.getName() + "\t" + e.getGender() + "\t" +
                    e.getDepartment() + "\t\t" + (e.getHireDate() == null ? "null" : sdf.format(e.getHireDate()))
                    + "\t" + (e.getBirthday() == null ? "null" : sdf.format(e.getBirthday())));
        }
    }

    public static void showEmployee(Employee e) {
        if (e == null) {
            log("未查询到信息！");
            return;
        }
        List<Employee> employees = new ArrayList<>();
        employees.add(e);
        showEmployee(employees);
    }

    /**
     * 打印薪资信息
     */
    public static void showSalary(List<Salary> salaries) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if (salaries == null) {
            log("未查询到信息！");
            return;
        }
        System.out.println("员工编号\t基础薪资\t奖金\t扣款\t发放时间\t\t备注");
        for (Salary s : salaries) {
            System.out.println(s.getEmpId() + "\t" + s.getBaseSalary() + "\t" + s.getBonus() + "\t" + s.getDeductions() +
                    "\t\t" + (s.getPaymentDate() == null ? "null" : sdf.format(s.getPaymentDate())) + "\t" + s.getNotes());
        }
    }

    public static void showSalary(Salary s) {
        if (s == null) {
            log("未查询到信息！");
            return;
        }
        List<Salary> salaries = new ArrayList<>();
        salaries.add(s);
        showSalary(salaries);
    }

    /**
     * 打印考勤信息
     */
    public static void showAttendance(List<Attendance> attendances) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if (attendances == null) {
            log("未查询到信息！");
            return;
        }
        System.out.println("员工编号\t开始时间\t\t结束时间\t\t迟到次数\t早退次数\t请假情况");
        for (Attendance a : attendances) {
            System.out.println(a.getEmpId() + "\t" + (a.getStartDate() == null ? "null" : sdf.format(a.getStartDate()))
                    + "\t\t" + (a.getEndDate() == null ? "null" : sdf.format(a.getEndDate())) + "\t\t"
                    + a.getLateCount() + "\t" + a.getEarlyLeaveCount() + "\t" + a.getLeaveRecord());
        }
    }

    public static void showAttendance(Attendance a) {
        if (a == null) {
            log("未查询到信息！");
            return;
        }
        List<Attendance> attendances = new ArrayList<>();
        attendances.add(a);
        showAttendance(attendances);
    }

    /**
     * 打印用户信息
     */
    public static void showUser(List<User> users) {
        if (users == null) {
            log("未查询到信息！");
            return;
        }
        System.out.println("用户名\t密码\t权限");
        for (User u : users) {
            System.out.println(u.getUsername() + "\t" + u.getPassword() + "\t" +
                    ("1".equals(u.getType()) ? "管理员" : "普通用户"));
        }
    }

    public static void showUser(User u) {
        if (u == null) {
            log("未查询到信息！");
            return;
        }
        List<User> users = new ArrayList<>();
        users.add(u);
        showUser(users);
    }
}
