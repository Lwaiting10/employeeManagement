package com.iweb.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Liu Xiong
 * @date 26/11/2023 上午11:30
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private int userId;
    private int username;
    private String password;
    private String type;

    public User(int username, String password, String type) {
        this.username = username;
        this.password = password;
        this.type = type;
    }

    public User(int username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(int username) {
        this.username = username;
    }

    /**
     * 用于登录验证
     *
     * @param u 用户传入的信息
     * @return 如果匹配则返回true，反之返回false
     */
    public boolean verify(User u) {
        return u.username == username && u.password.equals(password);
    }

    /**
     * 是否是管理员判断
     */
    public boolean isAdmin() {
        return "1".equals(type);
    }
}
