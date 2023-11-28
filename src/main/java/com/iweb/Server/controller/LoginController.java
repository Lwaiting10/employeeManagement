package com.iweb.Server.controller;

import com.iweb.Client.Util.PrintUtil;
import com.iweb.Server.entity.User;

/**
 * @author Liu Xiong
 * @date 26/11/2023 下午2:51
 */
public class LoginController {
    public static void mainController(String key) {
        switch (key) {
            case "1":
                User inputUser = View.loginView();
                boolean login = Service.login(inputUser);
                if (login) {
                    // 跳转到下一个视图
                    View.userInfoView();
                } else {
                    PrintUtil.log("登录用户名密码有误！请重新输入！");
                    return;
                }
                break;
            case "2":
                // TODO: 10/11/2023 调用注册相关业务
                // User inputUser = View.registerView();
                // boolean register = Service.register(inputUser);
                break;
        }
    }
}
