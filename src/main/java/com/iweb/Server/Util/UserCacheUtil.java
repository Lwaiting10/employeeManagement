package com.iweb.Server.Util;

import com.iweb.Server.entity.User;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户缓存类
 * 便于快速登录
 *
 * @author Liu Xiong
 * @date 26/11/2023 下午3:09
 */
public class UserCacheUtil {
    private final static List<User> CACHE_USERS = new ArrayList<>();

    public static void insert(User user) {
        CACHE_USERS.add(user);
    }

    public static User getUser(User inputUser) {
        // 用户缓存集合为空 返回null
        if (CACHE_USERS.isEmpty()) {
            return null;
        }
        // 遍历用户缓存 如果存在匹配的返回该用户对象
        for (User user : CACHE_USERS) {
            if (user.verify(inputUser)) {
                return user;
            }
        }
        // 没有匹配用户，返回null
        return null;
    }
}
