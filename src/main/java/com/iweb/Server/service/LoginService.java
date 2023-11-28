package com.iweb.Server.service;

import com.iweb.Server.Util.UserCacheUtil;
import com.iweb.Server.dao.UserDAO;
import com.iweb.Server.dao.impl.UserDAOImpl;
import com.iweb.Server.entity.User;

/**
 * @author Liu Xiong
 * @date 26/11/2023 下午2:58
 */
public class LoginService {
    public static User login(User inputUser) {
        boolean flag = false;
        // 先从用户缓存集合中进行匹配
        User user = UserCacheUtil.getUser(inputUser);
        // 找到匹配的用户
        if (user != null) {
            return user;
        }
        // 没有找到则从数据库进行匹配
        UserDAO userDAO = new UserDAOImpl();
        user = userDAO.select(inputUser);
        // 找到匹配的返回用户对象
        if (user != null) {
            // 将该用户信息添加到用户缓存
            UserCacheUtil.insert(user);
            return user;
        }
        // 都没有找到匹配用户
        return null;
    }
}
