package com.iweb.Server.dao;

import com.iweb.Server.entity.User;

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
     * @param id 需要删除的用户信息的id
     */
    boolean delete(Integer id);

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
     * 查询所有登录信息
     *
     * @return 返回登录信息类型的集合
     */
    List<User> selectAll();


    /**
     * 用户登录匹配
     *
     * @param u 客户端尝试登录的用户信息
     * @return 返回数据库中匹配的用户信息，没有则返回null
     */
    User select(User u);
}
