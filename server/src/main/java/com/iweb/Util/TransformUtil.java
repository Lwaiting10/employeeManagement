package com.iweb.Util;

import com.iweb.dao.AttendanceDAO;
import com.iweb.dao.SalaryDAO;
import com.iweb.dao.impl.AttendanceDAOImpl;
import com.iweb.dao.impl.SalaryDAOImpl;
import com.iweb.entity.Attendance;
import com.iweb.entity.Employee;
import com.iweb.entity.Salary;
import com.iweb.entity.User;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Liu Xiong
 * @date 26/11/2023 下午6:36
 */
public class TransformUtil {
    /**
     * 提取字符串信息中的数据(仅有单个数据)
     */
    private static List<String> getInfo(String infoStr) {
        List<String> info = new ArrayList<>();
        while (infoStr.contains("=")) {
            infoStr = infoStr.substring(infoStr.indexOf("=") + 1);
            info.add(infoStr.substring(0, !infoStr.contains(",") ? infoStr.indexOf(")") : infoStr.indexOf(",")));
        }
        return info;
    }

    /**
     * 提取字符串信息中的数据(集合数据)
     */
    private static List<String> getListInfo(String infoStr) {
        List<String> info = new ArrayList<>();
        while (infoStr.contains("(")) {
            infoStr = infoStr.substring(infoStr.indexOf("(") + 1);
            info.add("(" + infoStr.substring(0, infoStr.indexOf(")")) + ")");
        }
        return info;
    }

    /**
     * 提取字符串信息中的数据,再创建用户对象
     */
    public static User getUser(String infoStr) {
        if ("".equals(infoStr) || "null".equals(infoStr)) {
            return null;
        }
        List<String> info = getInfo(infoStr);
        User user = new User();
        user.setUserId(Integer.parseInt(info.get(0)));
        user.setUsername(Integer.parseInt(info.get(1)));
        user.setPassword(info.get(2));
        user.setType(info.get(3));
        return user;
    }

    /**
     * 获取用户集合
     */
    private static List<User> getUserList(String infoStr) {
        if ("".equals(infoStr) || "null".equals(infoStr)) {
            return null;
        }
        List<User> users = new ArrayList<>();
        if (infoStr.charAt(0) != '[') {
            users.add(getUser(infoStr));
            return users;
        }
        // 将集合分成单个对象
        List<String> infoStrByCut = getListInfo(infoStr);
        for (String s : infoStrByCut) {
            users.add(getUser(s));
        }
        return users;
    }

    /**
     * 提取字符串信息中的数据,再创建员工对象
     */
    public static Employee getEmployee(String infoStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if ("".equals(infoStr) || "null".equals(infoStr)) {
            return null;
        }
        List<String> info = getInfo(infoStr);
        Employee employee = new Employee();
        employee.setId(Integer.parseInt(info.get(0)));
        employee.setName(info.get(1));
        employee.setGender(info.get(2));
        employee.setDepartment(info.get(3));
        try {
            employee.setHireDate("null".equals(info.get(4)) ? null : sdf.parse(info.get(4)));
            employee.setBirthday("null".equals(info.get(5)) ? null : sdf.parse(info.get(5)));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return employee;
    }

    /**
     * 获取员工集合
     */
    public static List<Employee> getEmployeeList(String infoStr) {
        if ("".equals(infoStr) || "null".equals(infoStr)) {
            return null;
        }
        List<Employee> employees = new ArrayList<>();
        if (infoStr.charAt(0) != '[') {
            employees.add(getEmployee(infoStr));
            return employees;
        }
        // 将集合分成单个对象
        List<String> infoStrByCut = getListInfo(infoStr);
        for (String s : infoStrByCut) {
            employees.add(getEmployee(s));
        }
        return employees;
    }

    /**
     * 提取字符串信息中的数据,再创建薪资对象
     */
    public static Salary getSalary(String infoStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if ("".equals(infoStr) || "null".equals(infoStr)) {
            return null;
        }
        List<String> info = getInfo(infoStr);
        Salary salary = new Salary();
        salary.setEmpId(Integer.parseInt(info.get(0)));
        salary.setBaseSalary("null".equals(info.get(1)) ? null : new BigDecimal(info.get(1)));
        salary.setBonus("null".equals(info.get(2)) ? null : new BigDecimal(info.get(2)));
        salary.setDeductions("null".equals(info.get(3)) ? null : new BigDecimal(info.get(3)));
        try {
            salary.setPaymentDate("null".equals(info.get(4)) ? null : sdf.parse(info.get(4)));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        salary.setNotes(info.get(5));
        return salary;
    }

    /**
     * 获取薪资集合
     */
    public static List<Salary> getSalaryList(String infoStr) {
        if ("".equals(infoStr) || "null".equals(infoStr)) {
            return null;
        }
        List<Salary> salaries = new ArrayList<>();
        if (infoStr.charAt(0) != '[') {
            salaries.add(getSalary(infoStr));
            return salaries;
        }
        // 将集合分成单个对象
        List<String> infoStrByCut = getListInfo(infoStr);
        for (String s : infoStrByCut) {
            salaries.add(getSalary(s));
        }
        return salaries;
    }

    /**
     * 提取字符串信息中的数据,再创建薪资对象
     */
    public static Attendance getAttendance(String infoStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if ("".equals(infoStr) || "null".equals(infoStr)) {
            return null;
        }
        List<String> info = getInfo(infoStr);
        Attendance attendance = new Attendance();
        attendance.setAttendanceId(Integer.parseInt(info.get(0)));
        attendance.setEmpId(Integer.parseInt(info.get(1)));
        try {
            attendance.setStartDate("null".equals(info.get(2)) ? null : sdf.parse(info.get(2)));
            attendance.setEndDate("null".equals(info.get(3)) ? null : sdf.parse(info.get(3)));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        attendance.setLateCount(Integer.parseInt(info.get(4)));
        attendance.setEarlyLeaveCount(Integer.parseInt(info.get(5)));
        attendance.setLeaveRecord(info.get(6));
        return attendance;
    }

    /**
     * 获取薪资集合
     */
    public static List<Attendance> getAttendanceList(String infoStr) {
        if ("".equals(infoStr) || "null".equals(infoStr)) {
            return null;
        }
        List<Attendance> attendances = new ArrayList<>();
        if (infoStr.charAt(0) != '[') {
            attendances.add(getAttendance(infoStr));
            return attendances;
        }
        // 将集合分成单个对象
        List<String> infoStrByCut = getListInfo(infoStr);
        for (String s : infoStrByCut) {
            attendances.add(getAttendance(s));
        }
        return attendances;
    }
}
