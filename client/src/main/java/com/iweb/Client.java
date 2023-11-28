package com.iweb;

import com.iweb.Util.DataUtil;
import com.iweb.View.LoginView;

import java.net.Socket;


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
            e.printStackTrace();
        }
    }
}
