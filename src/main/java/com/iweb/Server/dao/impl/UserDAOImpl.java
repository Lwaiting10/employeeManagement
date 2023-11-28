package com.iweb.Server.dao.impl;

import com.iweb.Server.Util.DBUtil;
import com.iweb.Server.dao.UserDAO;
import com.iweb.Server.entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Liu Xiong
 * @date 26/11/2023 上午11:37
 */
public class UserDAOImpl implements UserDAO {
    @Override
    public boolean insert(User u) {
        String sql = "insert into login_information(username,password,type)" + "values(?,?,?)";
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
    public boolean delete(Integer id) {
        String sql = "delete from login_information where login_id=" + id;
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.execute();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateAll(User u) {
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
    public boolean updateUsername(User u) {
        String sql = "update login_information set username=? where login_id=?";
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
    public boolean updatePassword(User u) {
        String sql = "update login_information set password=? where login_id=?";
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
    public boolean updateType(User u) {
        String sql = "update login_information set type=? where login_id=?";
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
    public List<User> selectAll() {
        List<User> users = new ArrayList<>();
        String sql = "select * from login_information";
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
    public User select(User u) {
        List<User> users = selectAll();
        for (User user : users) {
            if (user.verify(u)) {
                return user;
            }
        }
        return null;
    }
}
