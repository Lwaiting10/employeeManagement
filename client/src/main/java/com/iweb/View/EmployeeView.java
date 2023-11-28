package com.iweb.View;

import com.iweb.Util.CommunicationUtil;
import com.iweb.Util.ScannerUtil;
import com.iweb.Util.ShowUtil;
import com.iweb.Util.TransformUtil;
import com.iweb.entity.Employee;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

import static com.iweb.Util.PrintUtil.log;

/**
 * @author Liu Xiong
 * @date 28/11/2023 下午2:55
 */
public class EmployeeView {
    private final static Scanner SCANNER = new Scanner(System.in);

    /**
     * 员工管理界面
     */
    public static void mainView() {
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
            String message = CommunicationUtil.chooseAndGetMessage();
            // 0 - 退出
            if ("exit".equals(message)) {
                return;
            }
            // 1 - 查看所有员工
            if ("allEmployee".equals(message)) {
                // 接收服务器传来的员工信息并转成对象集合
                List<Employee> employees = TransformUtil.getEmployeeList(CommunicationUtil.receive());
                // 展示信息
                ShowUtil.showEmployee(employees);
            }
            // 2 - 根据姓名查找员工
            if ("selectEmployeeByName".equals(message)) {
                // 获取要查询的员工姓名
                String name = selectEmployeeByNameView();
                // 将信息传给服务器
                CommunicationUtil.send(name);
                // 接收服务器传来的员工信息并转成对象集合
                List<Employee> employees = TransformUtil.getEmployeeList(CommunicationUtil.receive());
                // 展示信息
                ShowUtil.showEmployee(employees);
            }
            // 3 - 根据部门查找员工
            if ("selectEmployeeByDepartment".equals(message)) {
                // 获取要查询的员工部门
                String department = selectEmployeeByDepartmentView();
                // 将信息传给服务器
                CommunicationUtil.send(department);
                // 接收服务器传来的员工信息并转成对象集合
                List<Employee> employees = TransformUtil.getEmployeeList(CommunicationUtil.receive());
                // 展示信息
                ShowUtil.showEmployee(employees);
            }
            // 4 - 添加员工
            if ("insertEmployee".equals(message)) {
                // 获取新增员工的对象
                Employee employee = insertEmployeeView();
                // 将新增员工对象发送给服务器
                CommunicationUtil.send(employee.toString());
                // 获取服务器反馈
                message = CommunicationUtil.receive();
                if ("success".equals(message)) {
                    log("新增员工成功！");
                }
                if ("fail".equals(message)) {
                    log("新增员工失败,已存在同id员工！");
                }
            }
            // 5 - 更改员工信息
            if ("updateEmployee".equals(message)) {
                // 跳转更新员工信息页面
                updateEmployeeView();
            }
            // 6 - 删除员工
            if ("deleteEmployee".equals(message)) {
                // 跳转删除员工信息界面
                deleteEmployeeView();
            }
        }
    }

    /**
     * 从服务器获取要操作的员工对象
     *
     * @return 返回服务器匹配的用户对象, 没有匹配的返回null
     */
    private static Employee getEmployeeFromServer() {
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
    private static void deleteEmployeeView() {
        log("根据提示输入要删除的对象");
        // 获取要操作的对象
        Employee employee = getEmployeeFromServer();
        if (employee == null) {
            log("没有该id的员工！");
            return;
        }
        log("要删除的员工信息如下：");
        ShowUtil.showEmployee(employee);
        // 删除确认操作
        CommunicationUtil.deleteConfirm();
    }

    /**
     * 更新员工信息
     */
    private static void updateEmployeeView() {
        log("根据提示输入要更改的对象");
        Employee employee = getEmployeeFromServer();
        if (employee == null) {
            log("没有该id的员工！");
            return;
        }
        log("要修改的员工原信息如下：");
        ShowUtil.showEmployee(employee);
        log("输入修改后的信息:");
        if (employee != null) {
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
    }

    private static String selectEmployeeByNameView() {
        return ScannerUtil.getName();
    }

    private static String selectEmployeeByDepartmentView() {
        return ScannerUtil.getDepartment();
    }

    private static Employee insertEmployeeView() {
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
