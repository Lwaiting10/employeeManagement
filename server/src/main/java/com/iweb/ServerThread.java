package com.iweb;

import com.iweb.controller.LoginController;
import com.iweb.entity.User;

import java.net.Socket;
import java.util.ArrayList;

/**
 * @author Liu Xiong
 * @date 26/11/2023 下午3:48
 */
public class ServerThread extends Thread {
    public static Socket socket;
    public static User user;

    ArrayList<Socket> list;

    public ServerThread(Socket socket, ArrayList<Socket> list) {
        ServerThread.socket = socket;
        this.list = list;
    }

    @Override
    public void run() {
        try {
            LoginController.mainController(socket, user);
        } catch (Exception e) {
        } finally {
            // 如果连接当前服务器的客户端 出现了其他问题 也应该从list集合中将其删除
            try {
                synchronized (list) {
                    socket.close();
                    list.remove(socket);
                    System.out.println(socket.getInetAddress() + "已经退出本系统，当前人数为:" + list.size());
                }
            } catch (Exception e) {
            }
        }
    }
}
