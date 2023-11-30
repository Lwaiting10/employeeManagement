package com.iweb.View;

import com.iweb.Util.CommunicationUtil;
import com.iweb.Util.ScannerUtil;
import com.iweb.Util.ShowUtil;
import com.iweb.Util.TransformUtil;
import com.iweb.entity.Attendance;
import com.iweb.entity.User;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import static com.iweb.Util.PrintUtil.log;

/**
 * 用户管理
 *
 * @author Liu Xiong
 * @date 28/11/2023 下午2:54
 */
public class UserView {
    public static void mainView() throws IOException {
        log("===================用户管理===================");
        while (true) {
            log("0 - 退出");
            log("1 - 查看所有用户信息");
            log("2 - 根据员工id查找");
            log("3 - 添加信息");
            log("4 - 更改信息");
            log("5 - 删除信息");
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
                // 3 - 添加信息
                case "insert":
                    insertView();
                    break;
                // 4 - 更改信息
                case "update":
                    updateView();
                    break;
                // 5 - 删除信息
                case "delete":
                    deleteView();
                    break;
            }
        }
    }

    /**
     * 查找所有的页面
     */
    private static void selectAllView() throws IOException {
        // 接收服务器数据
        List<User> users = TransformUtil.getUserList(CommunicationUtil.receive());
        // 打印信息
        ShowUtil.showUser(users);
    }

    /**
     * 根据员工id查找页面
     */
    private static void selectByEmpIdView() throws IOException {
        // 发送要查询的员工id信息
        log("员工id(纯数字):");
        CommunicationUtil.send(String.valueOf(ScannerUtil.getInt()));
        // 接收服务器反馈
        User user = TransformUtil.getUser(CommunicationUtil.receive());
        // 展示信息
        ShowUtil.showUser(user);
    }

    /**
     * 新增页面
     */
    private static void insertView() throws IOException {
        // 获取添加页面的输入的考勤信息 并 将新增信息发给服务器
        log("输入新增用户信息:");
        log("用户名(纯数字):");
        int inputUsername = ScannerUtil.getInt();
        log("密码:");
        String inputPassword = ScannerUtil.getString();
        log("权限(0-普通用户/1-管理员):");
        String inputType = ScannerUtil.getType();
        User inputUser = new User(inputUsername, inputPassword, inputType);
        // 向服务器发送信息
        CommunicationUtil.send(inputUser.toString());
        // 获取服务器反馈
        switch (CommunicationUtil.receive()) {
            case "true":
                log("新增成功!");
                break;
            case "repeat":
                log("该员工id已经存在用户信息");
                break;
            case "false":
                log("新增失败!");
                break;
            case "wrong":
                log("数据出错!");
                break;
        }
    }

    /**
     * 删除页面
     */
    private static void deleteView() throws IOException {
        log("输入要删除的用户id:");
        // 发送需要删除的用户id
        CommunicationUtil.send(String.valueOf(ScannerUtil.getInt()));
        String message = CommunicationUtil.receive();
        if ("admin".equals(message)) {
            log("该用户是管理员，禁止操作!");
            return;
        }
        // 接收服务器返回的数据
        User user = TransformUtil.getUser(message);
        if (user != null) {
            // 获得数据
            // 展示原数据
            log("用户信息如下:");
            ShowUtil.showUser(user);
            // 删除确认操作
            CommunicationUtil.deleteConfirm();
        } else {
            // 没有获取数据
            log("没有该id的用户信息！");
        }
    }

    /**
     * 更改信息页面
     */
    private static void updateView() throws IOException {
        log("输入要更改信息的用户id:");
        // 发送需要更改信息的员工id
        CommunicationUtil.send(String.valueOf(ScannerUtil.getInt()));
        // 接收服务器反馈
        String message = CommunicationUtil.receive();
        if ("admin".equals(message)) {
            log("该用户是管理员，禁止操作!");
            return;
        }
        User user = TransformUtil.getUser(message);
        if (user != null) {
            // 获得数据
            // 展示原数据
            log("原数据如下:");
            ShowUtil.showUser(user);
            log("输入修改后的数据:");
            // 获取修改后的数据
            log("密码:");
            user.setPassword(ScannerUtil.getString());
            log("权限(0-普通用户/1-管理员):");
            user.setType(ScannerUtil.getType());
            // 将数据发送回服务器
            CommunicationUtil.send(user.toString());
            // 接收服务器反馈
            if ("true".equals(CommunicationUtil.receive())) {
                log("修改成功！");
            } else {
                log("修改失败！");
            }
        } else {
            // 没有获取数据
            log("没有该id的用户信息！");
        }
    }
}

