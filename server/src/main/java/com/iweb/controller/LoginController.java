package com.iweb.controller;

import com.iweb.Server;
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
    public static void mainController(Socket socket, User user) {
        try {
            while (true) {
                String key = CommunicationUtil.receive(socket);
                if (key == null) {
                    CommunicationUtil.send(socket, "wrong");
                    continue;
                }
                switch (key) {
                    case "0": {
                        CommunicationUtil.send(socket, "exit");
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
                        // 重复登陆处理
                        if (repeatedLogin(user)) {
                            // 该账号已经登录
                            CommunicationUtil.send(socket, "login");
                            break;
                        }
                        // 添加到用户集合
                        Server.users.add(user);
                        // 登陆成功,将用户信息发送给客户端
                        CommunicationUtil.send(socket, user.toString());
                        // 普通用户界面跳转
                        if ("0".equals(user.getType())) {
                            CommunicationUtil.send(socket, "NormalUserView");
                            NormalUserController.normalUserController(socket, user);
                        }
                        // 管理员界面跳转
                        if ("1".equals(user.getType())) {
                            CommunicationUtil.send(socket, "AdminView");
                            AdminController.adminController(socket, user);
                        }
                        // 用户登出，从用户集合删除
                        Server.users.remove(user);
                        break;
                    }
                    default:
                        CommunicationUtil.send(socket, "wrong");
                }
            }
        } catch (IOException e) {
            // 用户异常退出
            Server.users.remove(user);
            if (user != null && user.getUsername() != 0) {
                System.out.println(("1".equals(user.getType()) ? "管理员:" : "用户:")
                        + user.getUsername() + "登出了系统!");
            }
        }
    }

    /**
     * 将登录成功的信息发送回登录的线程
     */
    private static User saveUserInfo(User user) {
        return user;
    }

    /**
     * 重复登陆判断
     */
    private static boolean repeatedLogin(User user) {
        if (Server.users.isEmpty()) {
            return false;
        }
        for (User u : Server.users) {
            if (u.getUsername() == user.getUsername()) {
                return true;
            }
        }
        return false;
    }
}
