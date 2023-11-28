package com.iweb.controller;

import com.iweb.Util.CommunicationUtil;
import com.iweb.Util.TransformUtil;
import com.iweb.entity.User;
import com.iweb.service.UserService;

import java.io.IOException;
import java.net.Socket;

/**
 * @author Liu Xiong
 * @date 26/11/2023 下午2:51
 */
public class LoginController {
    public static void mainController(Socket socket, User user) throws IOException {
        while (true) {
            String key = CommunicationUtil.receive(socket);
            if (key == null) {
                CommunicationUtil.send(socket, "wrong");
                continue;
            }
            switch (key) {
                case "0":{
                    CommunicationUtil.send(socket,"exit");
                    return;
                }
                case "1": {
                    // 向客户端发送转换登录页面信息
                    CommunicationUtil.send(socket, "loginView");
                    // 接受客户端发来的登录信息
                    String inputUserInfo = CommunicationUtil.receive(socket);
                    // 将登录信息转换成用户对象
                    User inputUser = TransformUtil.getUser(inputUserInfo);
                    // 进行登录验证
                    user = UserService.login(inputUser);
                    // 登陆失败，向客户端发送失败信息
                    if (user == null) {
                        CommunicationUtil.send(socket, "false");
                        break;
                    }
                    // 登陆成功,将用户信息发送给客户端
                    CommunicationUtil.send(socket, user.toString());
                    // 普通用户界面跳转
                    if ("0".equals(user.getType())) {
                        CommunicationUtil.send(socket, "NormalUserView");
                        NormalUserController.normalUserController(socket, user);
                        break;
                    }
                    // 管理员界面跳转
                    if ("1".equals(user.getType())) {
                        CommunicationUtil.send(socket, "AdminView");
                        AdminController.adminController(socket, user);
                        break;
                    }
                }
                default:
                    CommunicationUtil.send(socket, "wrong");
            }
        }
    }
}
