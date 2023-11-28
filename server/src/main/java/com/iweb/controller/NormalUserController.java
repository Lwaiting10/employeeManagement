package com.iweb.controller;

import com.iweb.Util.CommunicationUtil;
import com.iweb.entity.User;

import java.io.IOException;
import java.net.Socket;

/**
 * @author Liu Xiong
 * @date 27/11/2023 下午12:06
 */
public class NormalUserController {

    public static void normalUserController(Socket socket, User user) throws IOException {
        // 跳转个人信息管理界面
        PersonalInformationController.personalInformationManage(socket,user);
    }
}
