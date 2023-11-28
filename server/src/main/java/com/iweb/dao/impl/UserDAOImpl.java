package com.iweb.dao.impl;


import com.iweb.Util.DBUtil;
import com.iweb.Util.UserEnum;
import com.iweb.dao.UserDAO;
import com.iweb.entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Liu Xiong
 * @date 26/11/2023 上午11:37
 */
public class UserDAOImpl implements UserDAO {
    @Override
    public synchronized boolean insert(User u) {
        String sql = "insert into user(username,password,type)" + "values(?,?,?)";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, u.getUsername());
            ps.setString(2, u.getPassword());
            ps.setString(3, u.getType());
            ps.execute();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                u.setUserId(rs.getInt(1));
            }
            return true;
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return false;
    }

    @Override
    public synchronized boolean delete(Integer id) {
        String sql = "delete from user where login_id=" + id;
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.execute();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public synchronized boolean updateAll(User u) {
        if (updateUsername(u)) {
            if (updatePassword(u)) {
                if (updatePassword(u)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public synchronized boolean updateUsername(User u) {
        String sql = "update user set username=? where login_id=?";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)
        ) {
            ps.setInt(1, u.getUsername());
            ps.setInt(2, u.getUserId());
            ps.execute();
            return true;
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return false;
    }

    @Override
    public synchronized boolean updatePassword(User u) {
        String sql = "update user set password=? where login_id=?";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)
        ) {
            ps.setString(1, u.getPassword());
            ps.setInt(2, u.getUserId());
            ps.execute();
            return true;
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return false;
    }

    @Override
    public synchronized boolean updateType(User u) {
        String sql = "update user set type=? where login_id=?";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)
        ) {
            ps.setString(1, u.getType());
            ps.setInt(2, u.getUserId());
            ps.execute();
            return true;
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return false;
    }

    @Override
    public List<User> selectAll(UserEnum userEnum, int key) {
        List<User> users = new ArrayList<>();
        String sql;
        switch (userEnum) {
            case ALL:
                sql = "select * from user";
                break;
            case USERNAME:
                sql = "select * from user where username like '%" + key + "%'";
                break;
            case TYPE:
                sql = "select * from user where type =" + key;
                break;
            default:
                return null;
        }
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)
        ) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                User u = new User();
                u.setUserId(rs.getInt(1));
                u.setUsername(rs.getInt(2));
                u.setPassword(rs.getString(3));
                u.setType(rs.getString(4));
                users.add(u);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return users.isEmpty() ? null : users;
    }

    @Override
    public List<User> selectAll() {
        return selectAll(UserEnum.ALL, 0);
    }

    @Override
    public User selectByUsername(int username) {
        List<User> users = selectAll(UserEnum.USERNAME, username);
        return users == null ? null : users.get(0);
    }

    @Override
    public List<User> selectByType(int type) {
        return selectAll(UserEnum.TYPE, type);
    }

    @Override
    public User select(User u) {
        List<User> users = selectAll();
        if (users == null) {
            return null;
        }
        for (User user : users) {
            if (user.verify(u)) {
                return user;
            }
        }
        return null;
    }
}
