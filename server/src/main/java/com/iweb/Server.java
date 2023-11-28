package com.iweb;


import com.iweb.controller.LoginController;
import com.iweb.entity.User;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.*;

/**
 * @author Liu Xiong
 * @date 26/11/2023 下午3:49
 */
public class Server {
    public volatile static ArrayList<Socket> list = new ArrayList<>();
    public static ThreadPoolExecutor threadPoolExecutor =
            new ThreadPoolExecutor(10, 15, 60, TimeUnit.SECONDS, new LinkedBlockingDeque<>());

    // 登录用户集合 (用于重复登录处理)
    public volatile static ArrayList<User> users = new ArrayList<>();

    public static void main(String[] args) {
        // 创建服务器端 指定监听端口
        try {
            ServerSocket ss = new ServerSocket(8888);
            // 服务器端需要不断接受来自客户端的请求 并且将客户端发送过来的socket对象存放在list中
            while (true) {
                Socket socket = ss.accept();

                list.add(socket);
                System.out.println(socket.getInetAddress() + "进入了本系统！在线人数为:" + list.size());
                // 每接受到一个客户端 就开启一个线程为其服务
                threadPoolExecutor.execute(new Runnable() {
                    private User currentUser;
                    User user = new User();

                    @Override
                    public void run() {
                        try {
                            LoginController.mainController(socket, user);
                        } catch (Exception e) {

                        } finally {
                            // 如果连接当前服务器的客户端 出现了其他问题 从list集合中将其删除
                            try {
                                synchronized (list) {
                                    socket.close();
                                    list.remove(socket);
                                    System.out.println(socket.getInetAddress() + "退出了本系统，当前人数为:" + list.size());
                                }
                            } catch (Exception e) {
                            }
                        }
                    }
                });
            }
        } catch (IOException e) {
        }
    }
}
