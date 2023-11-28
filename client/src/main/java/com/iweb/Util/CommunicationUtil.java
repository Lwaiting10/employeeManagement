package com.iweb.Util;

import java.io.*;
import java.util.Scanner;

import static com.iweb.Util.PrintUtil.log;

/**
 * @author Liu Xiong
 * @date 26/11/2023 下午5:06
 */
public class CommunicationUtil {
    private final static Scanner SCANNER = new Scanner(System.in);

    public static boolean send(String message) {
        try {
            OutputStream os = DataUtil.socket.getOutputStream();
            DataOutputStream dos = new DataOutputStream(os);
            dos.writeUTF(message);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String receive() {
        try {
            InputStream is = DataUtil.socket.getInputStream();
            DataInputStream dis = new DataInputStream(is);
            return dis.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 选择选项并且接收服务器发来的页面跳转信息
     */
    public static String chooseAndGetMessage() {
        String message;
        while (true) {
            String choose = SCANNER.nextLine();
            // 向服务端发送选择
            send(choose);
            // 接收回复
            message = receive();
            if ("wrong".equals(message)) {
                log("输入有误！重新输入：");
            } else {
                return message;
            }
        }
    }

    public static void deleteConfirm() {
        while (true) {
            log("确定要删除吗？(y/n)");
            switch (SCANNER.nextLine()) {
                // 确定删除
                case "y":
                case "Y": {
                    // 向服务器发送删除请求
                    send("y");
                    // 接收服务器的反馈
                    if ("true".equals(receive())) {
                        log("删除成功！");
                    } else {
                        log("删除失败！");
                    }
                    return;
                }
                case "n":
                case "N": {
                    // 向服务器发送删除请求
                    send("n");
                    return;
                }
                default:
                    // 输入有误
                    log("输入有误！请重新输入:");
            }
        }
    }
}
