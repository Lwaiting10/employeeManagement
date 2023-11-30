package com.iweb;


import com.iweb.controller.LoginController;
import com.iweb.entity.User;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.*;

import static com.iweb.Util.Log.log;

/**
 * @author Liu Xiong
 * @date 26/11/2023 下午3:49
 */
public class Server {
    private final static int PORT = 8888;
    public static ThreadPoolExecutor threadPoolExecutor =
            new ThreadPoolExecutor(10, 15, 60, TimeUnit.SECONDS, new LinkedBlockingDeque<>());

    public volatile static HashMap<Socket, User> map = new HashMap<>();

    public static void main(String[] args) {
        // 创建服务器端 指定监听端口
        try (ServerSocket ss = new ServerSocket(PORT);
        ) {
            System.out.println("用户管理系统服务端已开启!");
            log("用户管理系统服务端开启");
            // 服务器端需要不断接受来自客户端的请求 并且将客户端发送过来的socket对象存放在map中
            while (true) {
                Socket socket = ss.accept();
                User user = new User();
                map.put(socket, user);
                log(socket.getInetAddress() + "进入了系统");
                System.out.println(socket.getInetAddress() + "进入了本系统！在线人数为:" + map.size());
                // 每接受到一个客户端 就开启一个线程为其服务
                threadPoolExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            LoginController.mainController(socket, user);
                        } catch (Exception e) {

                        } finally {
                            // 如果连接当前服务器的客户端 出现了其他问题 从list集合中将其删除
                            try {
                                synchronized (map) {
                                    socket.close();
                                    map.remove(socket);
                                    log(socket.getInetAddress() + "退出了系统");
                                    System.out.println(socket.getInetAddress() + "退出了本系统，当前人数为:" + map.size());
                                }
                            } catch (IOException e) {
                            }
                        }
                    }
                });
            }
        } catch (IOException e) {
            System.out.println(PORT + "端口被占用！");
            log(PORT + "端口被占用！");
        }
    }
}
