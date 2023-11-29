package com.iweb.View;


import com.iweb.Util.CommunicationUtil;
import com.iweb.Util.DataUtil;
import com.iweb.Util.ScannerUtil;
import com.iweb.Util.TransformUtil;
import com.iweb.entity.User;

import java.io.IOException;
import java.util.Scanner;

import static com.iweb.Util.PrintUtil.log;


/**
 * @author Liu Xiong
 * @date 26/11/2023 下午2:26
 */
public class LoginView {
    private final static Scanner SCANNER = new Scanner(System.in);

    public static void mainView() throws IOException {
        log("*****************欢迎来到用户登录系统******************");
        while (true) {
            log("请输入您想要操作的业务！");
            log("0. 退出");
            log("1. 登录");
            String message = CommunicationUtil.chooseAndGetMessage();
            if ("exit".equals(message)) {
                return;
            }
            // 进行登录操作
            if ("loginView".equals(message)) {
                loginView();
            }
        }
    }

    /**
     * 登录界面
     */
    private static void loginView() throws IOException {
        // 获取登陆页面输入的用户对象,并发送给服务器
        CommunicationUtil.send(getInputUser().toString());
        // 登录失败
        switch (CommunicationUtil.receive()) {
            case "fail":
                log("用户名或密码错误！");
                break;
            case "repeatLanding":
                log("该账号已经登录！");
                break;
            case "success": {
                log("登录成功！");
                // 获取登陆的用户对象
                DataUtil.user = TransformUtil.getUser(CommunicationUtil.receive());
                switch (CommunicationUtil.receive()) {
                    // 普通用户界面跳转
                    case "NormalUserView":
                        NormalUserView.userInfoView();
                        break;
                    // 管理员页面跳转
                    case "AdminView":
                        AdminView.userInfoView();
                        break;
                    case "wrong":
                        log("用户权限有误,被强制退出！");
                        break;
                }
            }
        }
    }

    /**
     * 获取登录界面输入的用户信息
     */
    private static User getInputUser() {
        log("请输入您的用户名:");
        int inputUsername = ScannerUtil.getInt();
        log("请输入您的密码:");
        String inputPassword = ScannerUtil.getString();
        return new User(inputUsername, inputPassword);
    }
}
