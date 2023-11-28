package com.iweb.Client.View;

import java.util.Scanner;

import static com.iweb.Client.Util.PrintUtil.log;

/**
 * @author Liu Xiong
 * @date 26/11/2023 下午2:31
 */
public class NormalUserView {
    private final static Scanner SCANNER = new Scanner(System.in);

    public static void userInfoView() {
        while (true) {
            log("请输入您要操作的功能序号：");
            log("0 - 退出");
            log("1 - 修改用户名");
            log("2 - 修改密码");
            String inputKey = SCANNER.nextLine();
            if ("0".equals(inputKey)) {
                break;
            }
            // TODO: 16/11/2023 用户相关操作
        }
    }
}
