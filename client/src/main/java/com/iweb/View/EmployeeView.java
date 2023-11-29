package com.iweb.View;

import com.iweb.Util.CommunicationUtil;
import com.iweb.Util.ScannerUtil;
import com.iweb.Util.ShowUtil;
import com.iweb.Util.TransformUtil;
import com.iweb.entity.Employee;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import static com.iweb.Util.PrintUtil.log;

/**
 * @author Liu Xiong
 * @date 28/11/2023 下午2:55
 */
public class EmployeeView {
    /**
     * 员工管理界面
     */
    public static void mainView() throws IOException {
        log("===================员工管理===================");
        while (true) {
            log("请输入您要操作的功能序号：");
            log("0 - 退出");
            log("1 - 查看所有员工");
            log("2 - 根据姓名查找员工");
            log("3 - 根据部门查找员工");
            log("4 - 添加员工");
            log("5 - 更改员工信息");
            log("6 - 删除员工");
            // 0 - 退出
            switch (CommunicationUtil.chooseAndGetMessage()) {
                case "exit":
                    return;
                // 1 - 查看所有员工
                case "selectAll": {
                    selectAll();
                    break;
                }
                // 2 - 根据姓名查找员工
                case "selectByName": {
                    selectByName();
                    break;
                }
                // 3 - 根据部门查找员工
                case "selectByDepartment": {
                    selectByDepartment();
                    break;
                }
                // 4 - 添加员工
                case "insert": {
                    insertView();
                    break;
                }
                // 5 - 更改员工信息
                case "update": {
                    // 跳转更新员工信息页面
                    updateEmployeeView();
                    break;
                }
                // 6 - 删除员工
                case "delete": {
                    // 跳转删除员工信息界面
                    deleteEmployeeView();
                    break;
                }
            }
        }
    }

    /**
     * 查看所有员工
     */
    private static void selectAll() throws IOException {
        // 接收服务器传来的员工信息并转成对象集合
        List<Employee> employees = TransformUtil.getEmployeeList(CommunicationUtil.receive());
        // 展示信息
        ShowUtil.showEmployee(employees);
    }

    /**
     * 根据姓名查找员工
     */
    private static void selectByName() throws IOException {
        // 获取要查询的员工姓名
        String name = ScannerUtil.getName();
        // 将信息传给服务器
        CommunicationUtil.send(name);
        // 接收服务器传来的员工信息并转成对象集合
        List<Employee> employees = TransformUtil.getEmployeeList(CommunicationUtil.receive());
        // 展示信息
        ShowUtil.showEmployee(employees);
    }

    /**
     * 根据部门查找员工
     */
    private static void selectByDepartment() throws IOException {
        // 获取要查询的员工部门
        String department = ScannerUtil.getDepartment();
        // 将信息传给服务器
        CommunicationUtil.send(department);
        // 接收服务器传来的员工信息并转成对象集合
        List<Employee> employees = TransformUtil.getEmployeeList(CommunicationUtil.receive());
        // 展示信息
        ShowUtil.showEmployee(employees);
    }

    /**
     * 添加员工
     */
    private static void insertView() throws IOException {
        // 获取新增员工的对象
        // 将新增员工对象发送给服务器
        CommunicationUtil.send(getInsertEmployee().toString());
        // 获取服务器反馈
        switch (CommunicationUtil.receive()) {
            case "success":
                log("新增员工成功！");
                break;
            case "repeat":
                log("新增员工失败,已存在同id员工！");
                break;
            case "fail":
                log("新增失败!");
                break;
            case "wrong":
                log("数据出错!");
                break;
        }
    }

    /**
     * 从服务器获取要操作的员工对象
     *
     * @return 返回服务器匹配的用户对象, 没有匹配的返回null
     */
    private static Employee getEmployeeFromServer() throws IOException {
        int inputId = ScannerUtil.getId();
        // 将员工id发送给服务器
        CommunicationUtil.send(String.valueOf(inputId));
        // 接收服务器反馈
        String message = CommunicationUtil.receive();
        if ("null".equals(message)) {
            return null;
        }
        if ("wrong".equals(message)) {
            return null;
        }
        // 返回获取的员工对象
        return TransformUtil.getEmployee(message);
    }

    /**
     * 删除员工信息
     */
    private static void deleteEmployeeView() throws IOException {
        log("根据提示输入要删除的对象");
        // 获取要操作的对象
        Employee employee = getEmployeeFromServer();
        if (employee == null) {
            log("没有该id的员工！");
            return;
        }
        log("要删除的员工信息如下：");
        ShowUtil.showEmployee(employee);
        log("提醒:删除员工信息，同时其相关信息也会一并删除：工资信息、考勤信息以及用户信息等!");
        // 删除确认操作
        CommunicationUtil.deleteConfirm();
    }

    /**
     * 更新员工信息
     */
    private static void updateEmployeeView() throws IOException {
        log("根据提示输入要更改的对象");
        Employee employee = getEmployeeFromServer();
        if (employee == null) {
            log("没有该id的员工！");
            return;
        }
        log("要修改的员工原信息如下：");
        ShowUtil.showEmployee(employee);
        log("输入修改后的信息:");
        employee.setName(ScannerUtil.getName());
        employee.setGender(ScannerUtil.getGender());
        employee.setDepartment(ScannerUtil.getDepartment());
        employee.setHireDate(ScannerUtil.getHireDate());
        employee.setBirthday(ScannerUtil.getBirthday());
        // 将修改后的员工信息发送给服务器
        CommunicationUtil.send(employee.toString());
        // 接收服务反馈信息
        if ("true".equals(CommunicationUtil.receive())) {
            log("更该成功！");
        } else {
            log("更改失败！");
        }
    }

    private static Employee getInsertEmployee() {
        log("输入新增员工信息:");
        int inputId = ScannerUtil.getId();
        String inputName = ScannerUtil.getName();
        String inputGender = ScannerUtil.getGender();
        String inputDepartment = ScannerUtil.getDepartment();
        Date inputHireDate = ScannerUtil.getHireDate();
        Date inputBirthday = ScannerUtil.getBirthday();
        return new Employee(inputId, inputName, inputGender, inputDepartment, inputHireDate, inputBirthday);
    }
}
