package com.iweb.Client.View;

import com.iweb.Client.entity.User;

import java.util.Scanner;

import static com.iweb.Client.Util.PrintUtil.log;

/**
 * @author Liu Xiong
 * @date 26/11/2023 下午2:26
 */
public class LoginView {
    private static final Scanner SCANNER = new Scanner(System.in);

    public static void mainView() {
        while (true) {
            log("欢迎来到用户登录系统");
            log("================");
            log("请输入您想要操作的业务！");
            log("0. 退出");
            log("1. 登录");
            String inputKey = SCANNER.nextLine();
            if ("0".equals(inputKey)) {
                break;
            }
            // TODO: 26/11/2023
            // Controller.mainController(inputKey);
        }
    }

    public static User loginView() {
        log("请输入您的用户名:");
        int inputUsername = SCANNER.nextInt();
        log("请输入您的密码:");
        String inputPassword = SCANNER.nextLine();
        User inputUser = new User();
        inputUser.setUsername(inputUsername);
        inputUser.setPassword(inputPassword);
        return inputUser;
    }
}
