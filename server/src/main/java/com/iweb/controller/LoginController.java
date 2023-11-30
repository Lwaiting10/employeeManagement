package com.iweb.controller;

import com.iweb.Server;
import com.iweb.Util.CommunicationUtil;
import com.iweb.Util.TransformUtil;
import com.iweb.entity.User;
import com.iweb.service.UserService;

import java.io.IOException;
import java.net.Socket;
import java.util.Map;

import static com.iweb.Util.Log.log;

/**
 * @author Liu Xiong
 * @date 26/11/2023 下午2:51
 */
public class LoginController {
    public static void mainController(Socket socket, User user) {
        try {
            while (true) {
                String key = CommunicationUtil.receive(socket);
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
                            CommunicationUtil.send(socket, "fail");
                            break;
                        }
                        // 重复登陆处理
                        if (repeatedLogin(user)) {
                            // 该账号已经登录
                            user = null;
                            CommunicationUtil.send(socket, "repeatLanding");
                            break;
                        }
                        // 添加到用户集合
                        addToSocket(socket, user);
                        CommunicationUtil.send(socket, "success");
                        // 登陆成功,将用户信息发送给客户端
                        CommunicationUtil.send(socket, user.toString());
                        switch (user.getType()) {
                            case "0": {
                                // 普通用户界面跳转
                                CommunicationUtil.send(socket, "NormalUserView");
                                NormalUserController.normalUserController(socket, user);
                                break;
                            }
                            case "1": {
                                // 管理员界面跳转
                                CommunicationUtil.send(socket, "AdminView");
                                AdminController.adminController(socket, user);
                                break;
                            }
                            default: {
                                // 权限有误
                                CommunicationUtil.send(socket, "wrong");
                                // 控制台打印输出错误提醒
                                log("(wrong) 用户:" + user.getUsername() + "权限出现错误:" + user.getType());
                                System.out.println("(wrong) 用户:" + user.getUsername() + "权限出现错误:" + user.getType());
                            }
                        }
                        // 用户登出，从用户集合删除
                        deleteFromSocket(socket);
                        break;
                    }
                    default:
                        CommunicationUtil.send(socket, "wrong");
                }
            }
        } catch (IOException e) {
            // 用户异常退出
            deleteFromSocket(socket);
            if (user != null && user.getUsername() != 0) {
                System.out.println(("1".equals(user.getType()) ? "管理员:" : "用户:") + user.getUsername() + "登出了系统!");
                log(socket.getInetAddress() + "登出账号:" + user.getUsername());
            }
        }
    }


    /**
     * 添加到对应socket的value中
     */
    private static void addToSocket(Socket socket, User user) {
        for (Map.Entry<Socket, User> entry : Server.map.entrySet()) {
            if (entry.getKey() == socket) {
                entry.setValue(user);
            }
        }
    }

    /**
     * 重复登陆判断
     */
    private static boolean repeatedLogin(User user) {
        for (Map.Entry<Socket, User> entry : Server.map.entrySet()) {
            if (entry.getValue().getUsername() == user.getUsername()) {
                return true;
            }
        }
        return false;
    }

    /**
     * 用户登出，从对应socket的value中删除
     */
    private static void deleteFromSocket(Socket socket) {
        for (Map.Entry<Socket, User> entry : Server.map.entrySet()) {
            if (entry.getKey() == socket) {
                entry.setValue(new User());
            }
        }
    }

}
