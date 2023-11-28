package com.iweb.View;


import com.iweb.Util.CommunicationUtil;
import com.iweb.Util.DataUtil;
import com.iweb.Util.TransformUtil;
import com.iweb.entity.User;

import java.util.Scanner;

import static com.iweb.Util.PrintUtil.log;


/**
 * @author Liu Xiong
 * @date 26/11/2023 下午2:26
 */
public class LoginView {
    private final static Scanner SCANNER = new Scanner(System.in);

    public static void mainView() {
        log("欢迎来到用户登录系统");
        log("================");
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
                // 获取登陆页面输入的用户对象
                User inputUser = loginView();
                if (inputUser != null) {
                    CommunicationUtil.send(inputUser.toString());
                    message = CommunicationUtil.receive();
                    // 登录失败
                    if ("false".equals(message)) {
                        log("用户名或密码错误！");
                    } else {
                        // 登录成功
                        // 获取登陆的用户对象
                        DataUtil.user = TransformUtil.getUser(message);

                        message = CommunicationUtil.receive();
                        // 普通用户界面跳转
                        if ("NormalUserView".equals(message)) {
                            log("登录成功！");
                            NormalUserView.userInfoView();
                        }
                        // 管理员页面跳转
                        if ("AdminView".equals(message)) {
                            log("登录成功");
                            AdminView.userInfoView();
                        }
                    }
                }
            }
        }
    }

    public static User loginView() {
        while (true) {
            try {
                log("请输入您的用户名:");
                String inputUsername = SCANNER.nextLine();
                if ("".equals(inputUsername)) {
                    log("用户名不能为空！");
                    continue;
                }
                log("请输入您的密码:");
                String inputPassword = SCANNER.nextLine();
                if ("".equals(inputPassword)) {
                    log("密码不能为空！");
                    continue;
                }
                User inputUser = new User();
                inputUser.setUsername(Integer.parseInt(inputUsername));
                inputUser.setPassword(inputPassword);
                return inputUser;
            } catch (Exception e) {
                System.out.println("输入有误,用户名为纯数字得员工id,请重新输入:");
            }
        }
    }
}
