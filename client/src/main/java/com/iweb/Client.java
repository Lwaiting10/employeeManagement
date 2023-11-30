package com.iweb;

import com.iweb.Util.DataUtil;
import com.iweb.View.LoginView;

import java.net.Socket;

import static com.iweb.Util.PrintUtil.log;


/**
 * @author Liu Xiong
 * @date 26/11/2023 下午3:51
 */
public class Client {
    public static void main(String[] args) {
        try {
            String ip = "127.0.0.1";
            DataUtil.socket = new Socket(ip, 8888);
            LoginView.mainView();
        } catch (Exception e) {
            log("连接服务器失败!");
        }
    }
}
