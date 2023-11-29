package com.iweb.dao;


import com.iweb.Util.UserEnum;
import com.iweb.entity.User;

import java.util.List;

/**
 * @author Liu Xiong
 * @date 26/11/2023 上午11:32
 */
public interface UserDAO {
    /**
     * 信息添加
     *
     * @param u 除id外的用户对象
     */
    boolean insert(User u);

    /**
     * 信息删除
     *
     * @param empId 需要删除的用户信息的id
     */
    boolean delete(Integer empId);

    /**
     * 更改信息
     *
     * @param u 根据传入的用户信息对象的内容进行整体替换
     */
    boolean updateAll(User u);

    /**
     * 更改账号
     * 用户的账号为员工的id，正常是不应该更改的
     * 仅在员工id更改之后进行协同更改
     *
     * @param u 需要更改权限的用户对象
     */
    boolean updateUsername(User u);

    /**
     * 更改密码
     *
     * @param u 需要更改权限的用户对象
     */
    boolean updatePassword(User u);

    /**
     * 更改权限
     *
     * @param u 需要更改权限的用户对象
     */
    boolean updateType(User u);


    /**
     * 查询所有用户信息
     *
     * @return 返回用户信息类型的集合
     */
    List<User> selectAll(UserEnum userEnum, int key);

    List<User> selectAll();

    /**
     * 根据用户名(员工id)查询用户信息
     *
     * @return 返回用户信息类型
     */
    User selectByUsername(int username);

    /**
     * 根据身份查询用户信息
     *
     * @return 返回用户信息类型的集合
     */
    List<User> selectByType(int type);


    /**
     * 用户登录匹配
     *
     * @param u 客户端尝试登录的用户信息
     * @return 返回数据库中匹配的用户信息，没有则返回null
     */
    User select(User u);
}
