package com.iweb.service;

import com.iweb.dao.UserDAO;
import com.iweb.dao.impl.UserDAOImpl;
import com.iweb.entity.Attendance;
import com.iweb.entity.User;

import java.util.List;

/**
 * @author Liu Xiong
 * @date 26/11/2023 下午2:58
 */
public class UserService {
    private static UserDAO userDAO = new UserDAOImpl();

    public static User login(User inputUser) {
        // 返回用户对象
        return userDAO.select(inputUser);
    }

    /**
     * 密码匹配
     */
    public static boolean passwordMatch(User user, String inputPassword) {
        return user.getPassword().equals(inputPassword);
    }

    /**
     * 密码更改
     */
    public static boolean passwordChange(User user) {
        return userDAO.updatePassword(user);
    }

    /**
     * 更改权限
     */
    public static boolean typeChange(User user) {
        return userDAO.updateType(user);
    }

    /**
     * 查询所有信息
     *
     * @return 返回所有集合，没有则返回null
     */
    public static List<User> getAllUser() {
        return userDAO.selectAll();
    }

    public static User selectByUsername(int id) {
        return userDAO.selectByUsername(id);
    }

    /**
     * 新增
     * 员工id重复则不允许新增
     */
    public static boolean insertUser(User user) {
        // 员工id重复则不允许新增
        if (selectByUsername(user.getUsername()) != null) {
            return false;
        }
        return userDAO.insert(user);
    }

    /**
     * 删除信息
     */
    public static boolean deleteUser(int id) {
        return userDAO.delete(id);
    }
}
