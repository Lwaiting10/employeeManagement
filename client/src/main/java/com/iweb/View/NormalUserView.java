package com.iweb.View;

import com.iweb.Util.DataUtil;


import static com.iweb.Util.PrintUtil.log;

/**
 * @author Liu Xiong
 * @date 26/11/2023 下午2:31
 */
public class NormalUserView {
    public static void userInfoView() {
        log("欢迎用户" + DataUtil.user.getUsername() + "!");
        // 跳转个人信息管理页面
        PersonalVew.manageView();
    }
}
