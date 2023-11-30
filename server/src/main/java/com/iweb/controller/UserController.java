package com.iweb.controller;

import com.iweb.Server;
import com.iweb.Util.CommunicationUtil;
import com.iweb.Util.TransformUtil;
import com.iweb.entity.User;
import com.iweb.service.UserService;

import java.io.IOException;
import java.net.Socket;
import java.util.List;
import java.util.Map;

import static com.iweb.Util.Log.log;

/**
 * @author Liu Xiong
 * @date 28/11/2023 下午2:49
 */
public class UserController {
    public static void userManage(Socket socket, User user) throws IOException {
        while (true) {
            String key = CommunicationUtil.receive(socket);
            switch (key) {
                // 0 - 退出
                case "0": {
                    CommunicationUtil.send(socket, "exit");
                    return;
                }
                // 1 - 查看所有考勤信息
                case "1": {
                    CommunicationUtil.send(socket, "selectAll");
                    selectAll(socket, user);
                    break;
                }
                // 2 - 根据员工id查找
                case "2": {
                    CommunicationUtil.send(socket, "selectByEmpId");
                    selectByEmpId(socket, user);
                    break;
                }
                // 3 - 添加信息
                case "3": {
                    CommunicationUtil.send(socket, "insert");
                    insert(socket, user);
                    break;
                }
                // 4 - 更改信息
                case "4": {
                    CommunicationUtil.send(socket, "update");
                    update(socket, user);
                    break;
                }
                // 5 - 删除信息
                case "5": {
                    CommunicationUtil.send(socket, "delete");
                    delete(socket, user);
                    break;
                }
                default:
                    CommunicationUtil.send(socket, "wrong");
            }
        }
    }

    private static void selectAll(Socket socket, User user) throws IOException {
        // 发送所有考勤信息
        List<User> users = UserService.getAllUser();
        if (users != null) {
            CommunicationUtil.send(socket, users.toString());
        } else {
            // 没有数据返回 null
            CommunicationUtil.send(socket, "null");
        }
    }

    private static void selectByEmpId(Socket socket, User user) throws IOException {
        // 接收要查询的信息
        String inputEmpIdStr = CommunicationUtil.receive(socket);
        // 查询改id是否存在
        User user1 = UserService.selectByUsername(Integer.parseInt(inputEmpIdStr));
        if (user1 != null) {
            // 存在，返回该对象
            CommunicationUtil.send(socket, user1.toString());
        } else {
            // 不存在,返回null
            CommunicationUtil.send(socket, "null");
        }
    }

    private static void insert(Socket socket, User user) throws IOException {
        // 获取客户端发来的新增考勤信息
        User newUser = TransformUtil.getUser(CommunicationUtil.receive(socket));
        // 新增操作
        if (newUser != null) {
            if (UserService.selectByUsername(newUser.getUsername()) == null) {
                if (UserService.insertUser(newUser)) {
                    // 新增成功,返回true
                    CommunicationUtil.send(socket, "true");
                    log(socket.getInetAddress() + "使用管理员账号:" + user.getUsername() + "添加了用户信息:" + newUser);
                } else {
                    // 新增失败,返回false
                    CommunicationUtil.send(socket, "false");
                    log("数据库写入失败:" + newUser);
                }
            } else {
                // 该员工id已经存在用户信息
                CommunicationUtil.send(socket, "repeat");
            }

        } else {
            // 数据有误
            CommunicationUtil.send(socket, "wrong");
        }
    }

    private static void update(Socket socket, User user) throws IOException {
        // 接收员工id
        int inputEmpId = Integer.parseInt(CommunicationUtil.receive(socket));
        // 查找该员工信息
        User user1 = UserService.selectByUsername(inputEmpId);
        if (user1 != null) {
            // 检查是否为管理员，管理员禁止更改
            if (user1.isAdmin()) {
                CommunicationUtil.send(socket, "admin");
                return;
            }
            // 不是管理员，返回信息
            CommunicationUtil.send(socket, user1.toString());
            // 接收修改后的数据
            User userUpdate = TransformUtil.getUser(CommunicationUtil.receive(socket));
            // 修改操作
            if (UserService.passwordChange(userUpdate) && UserService.typeChange(userUpdate)) {
                // 修改成功,返回true
                CommunicationUtil.send(socket, "true");
                log(socket.getInetAddress() + "使用管理员账号:" + user.getUsername() + "更改了用户信息: 原信息: "
                        + user1 + ",更新为: " + userUpdate);
                // 强制下线被修改的用户
                forcedLogout(userUpdate);
            } else {
                // 修改失败,返回false
                CommunicationUtil.send(socket, "false");
                log("数据库写入失败:" + userUpdate);
            }
        } else {
            // 没有找到，返回null
            CommunicationUtil.send(socket, "null");
        }
    }

    private static void delete(Socket socket, User user) throws IOException {
        // 接收员工id
        int inputEmpId = Integer.parseInt(CommunicationUtil.receive(socket));
        // 查找该员工信息
        User user1 = UserService.selectByUsername(inputEmpId);
        if (user1 != null) {
            // 检查是否为管理员，管理员禁止更改
            if (user1.isAdmin()) {
                CommunicationUtil.send(socket, "admin");
                return;
            }
            // 找到，返回信息
            CommunicationUtil.send(socket, user1.toString());
            // 获取客户端的确认信息
            if ("y".equalsIgnoreCase(CommunicationUtil.receive(socket))) {
                // 确认删除
                if (UserService.deleteUser(user1.getUsername())) {
                    // 删除成功，返回true
                    CommunicationUtil.send(socket, "true");
                    log(socket.getInetAddress() + "使用管理员账号:" + user.getUsername() + "删除用户信息: " + user1);
                    // 强制下线被修改的用户
                    forcedLogout(user1);
                } else {
                    // 删除失败,返回false
                    CommunicationUtil.send(socket, "false");
                }
            } else {
                // 取消删除
                return;
            }
        } else {
            // 没有找到，返回null
            CommunicationUtil.send(socket, "null");
        }
    }

    /**
     * 如果修改或删除的用户已经登录，则强制下线
     * 如果修改的是本人的密码则不会强制下线
     *
     * @param user 被修改或者删除的用户信息
     */
    private static void forcedLogout(User user) throws IOException {
        // 是否是登录的账号
        for (Map.Entry<Socket, User> entry : Server.map.entrySet()) {
            if (entry.getValue().getUsername() == user.getUsername()) {
                log(entry.getKey().getInetAddress() + "登录的账号:" + entry.getValue().getUsername() + " 信息被修改,被强制下线！");
                // 是登录的用户，强制下线
                entry.getKey().close();
                entry.setValue(new User());
            }
        }
    }
}
