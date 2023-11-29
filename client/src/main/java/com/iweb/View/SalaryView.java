package com.iweb.View;

import com.iweb.Util.CommunicationUtil;
import com.iweb.Util.ScannerUtil;
import com.iweb.Util.ShowUtil;
import com.iweb.Util.TransformUtil;
import com.iweb.entity.Attendance;
import com.iweb.entity.Salary;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import static com.iweb.Util.PrintUtil.log;

/**
 * 薪资管理
 *
 * @author Liu Xiong
 * @date 28/11/2023 下午2:54
 */
public class SalaryView {
    public static void mainView() throws IOException {
        log("===================薪资管理===================");
        while (true) {
            log("0 - 退出");
            log("1 - 查看所有薪资信息");
            log("2 - 根据员工id查找");
            log("3 - 根据工资发放日期查找(模糊查找)");
            log("4 - 添加信息");
            log("5 - 更改信息");
            log("6 - 删除信息");
            switch (CommunicationUtil.chooseAndGetMessage()) {
                case "exit":
                    return;
                // 1 - 查看所有考勤信息
                case "selectAll":
                    selectAllView();
                    break;
                // 2 - 根据员工id查找
                case "selectByEmpId":
                    selectByEmpIdView();
                    break;
                // 3 - 根据工资发放日期查找(模糊查找)
                case "selectByPaymentDate":
                    selectByPaymentDateView();
                    break;
                // 4 - 添加信息
                case "insert":
                    insertView();
                    break;
                // 5 - 更改信息
                case "update":
                    updateView();
                    break;
                // 6 - 删除信息
                case "delete":
                    deleteView();
                    break;
            }
        }
    }

    /**
     * 根据工资发放日期查找(模糊查找)
     */
    private static void selectByPaymentDateView() throws IOException {
        log("输入要查询的发放日期(模糊查找):");
        // 发送需要要查询的发放日期(模糊查找)
        CommunicationUtil.send(ScannerUtil.getString());
        // 接收服务器返回的数据
        List<Salary> salaries = TransformUtil.getSalaryList(CommunicationUtil.receive());
        // 展示原数据
        log("查询信息如下:");
        ShowUtil.showSalary(salaries);
    }

    /**
     * 查找所有的页面
     */
    private static void selectAllView() throws IOException {
        // 接收服务器数据
        List<Salary> salaries = TransformUtil.getSalaryList(CommunicationUtil.receive());
        // 打印信息
        ShowUtil.showSalary(salaries);
    }

    /**
     * 根据员工id查找页面
     */
    private static void selectByEmpIdView() throws IOException {
        // 发送要查询的员工id信息
        CommunicationUtil.send(String.valueOf(ScannerUtil.getId()));
        // 接收服务器反馈
        Salary salary = TransformUtil.getSalary(CommunicationUtil.receive());
        // 展示信息
        ShowUtil.showSalary(salary);
    }

    /**
     * 新增页面
     */
    private static void insertView() throws IOException {
        // 获取添加页面的输入的考勤信息 并 将新增信息发给服务器
        log("输入新增薪资信息:");
        CommunicationUtil.send(getSalary(ScannerUtil.getId()).toString());
        String message = CommunicationUtil.receive();
        // 获取服务器反馈
        switch (message) {
            case "true":
                log("新增成功!");
                break;
            case "absent":
                log("不可以新增员工id不存在的信息!");
                break;
            case "repeat":
                log("该员工已经有薪资信息,不可再次添加!");
                break;
            case "incorrectData":
                log("数据有误!");
                break;
            default:
                log("新增失败！");
                break;
        }
    }

    /**
     * 删除页面
     */
    private static void deleteView() throws IOException {
        log("输入要删除的员工id:");
        // 发送需要删除的员工id
        CommunicationUtil.send(String.valueOf(ScannerUtil.getInt()));
        // 接收服务器返回的数据
        Salary salary = TransformUtil.getSalary(CommunicationUtil.receive());
        if (salary != null) {
            // 获得数据
            // 展示原数据
            log("该员工的薪资信息如下:");
            ShowUtil.showSalary(salary);
            // 删除确认操作
            CommunicationUtil.deleteConfirm();
        } else {
            // 没有获取数据
            log("没有该id的员工薪资信息！");
        }
    }

    /**
     * 更改信息页面
     */
    private static void updateView() throws IOException {
        log("输入要更改信息的员工id:");
        // 发送需要更改信息的员工id
        CommunicationUtil.send(String.valueOf(ScannerUtil.getInt()));
        // 接收服务器返回的数据
        Salary salary = TransformUtil.getSalary(CommunicationUtil.receive());
        if (salary != null) {
            // 获得数据
            // 展示原数据
            log("原数据如下:");
            ShowUtil.showSalary(salary);
            log("输入修改后的数据:");
            // 获取修改后的数据
            Salary salaryUpdate = getSalary(salary.getEmpId());
            // 将数据发送回服务器
            CommunicationUtil.send(salaryUpdate.toString());
            // 接收服务器反馈
            if ("true".equals(CommunicationUtil.receive())) {
                log("修改成功！");
            } else {
                log("修改失败！");
            }
        } else {
            // 没有获取数据
            log("没有该id的员工薪资信息！");
        }
    }

    /**
     * 获取用户输入的信息
     */
    private static Salary getSalary(int inputEmpId) {
        log("基本工资:");
        BigDecimal inputBaseSalary = ScannerUtil.getBigDecimal();
        log("奖金:");
        BigDecimal inputBonus = ScannerUtil.getBigDecimal();
        log("扣款:");
        BigDecimal inputDeductions = ScannerUtil.getBigDecimal();
        log("工资发放日期(yyyy-MM-dd):");
        Date inputPaymentDate = ScannerUtil.getDate();
        log("备注:");
        String inputNotes = ScannerUtil.getString();
        return new Salary(inputEmpId, inputBaseSalary, inputBonus, inputDeductions, inputPaymentDate, inputNotes);
    }
}
