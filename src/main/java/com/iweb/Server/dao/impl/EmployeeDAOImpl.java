package com.iweb.Server.dao.impl;

import com.iweb.Server.Util.DBUtil;
import com.iweb.Server.dao.EmployeeDAO;
import com.iweb.Server.entity.Employee;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Liu Xiong
 * @date 25/11/2023 下午4:21
 */
public class EmployeeDAOImpl implements EmployeeDAO {
    @Override
    public boolean insert(Employee e) {
        String sql = "insert into employee(name,gender,department,hire_date,birthday)" + "values(?,?,?,?,?)";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, e.getName());
            ps.setString(2, e.getGender());
            ps.setString(3, e.getDepartment());
            ps.setDate(4, new Date(e.getHireDate().getTime()));
            ps.setDate(5, new Date(e.getBirthday().getTime()));
            ps.execute();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                e.setId(rs.getInt(1));
            }
            return true;
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(Integer id) {
        String sql = "delete from employee where emp_id=" + id;
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.execute();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(Employee e) {
        String sql = "update employee set name=?,gender=?,department=?,hire_date=?,birthday=? where emp_id=?";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)
        ) {
            ps.setString(1, e.getName());
            ps.setString(2, e.getGender());
            ps.setString(3, e.getDepartment());
            ps.setDate(4, new Date(e.getHireDate().getTime()));
            ps.setDate(5, new Date(e.getBirthday().getTime()));
            ps.setInt(6, e.getId());
            ps.execute();
            return true;
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Employee> selectAll() {
        List<Employee> employees = new ArrayList<>();
        String sql = "select * from employee";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)
        ) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Employee e = new Employee();
                e.setId(rs.getInt("emp_id"));
                e.setName(rs.getString("name"));
                e.setGender(rs.getString("gender"));
                e.setDepartment(rs.getString("department"));
                e.setHireDate(rs.getDate("hire_date"));
                e.setBirthday(rs.getDate("birthday"));
                employees.add(e);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return employees.isEmpty() ? null : employees;
    }

    @Override
    public List<Employee> selectByName(String name) {
        List<Employee> result = new ArrayList<>();
        List<Employee> employees = selectAll();
        for (Employee e : employees) {
            if (e.getName().contains(name)) {
                result.add(e);
            }
        }
        return result.isEmpty() ? null : result;
    }

    @Override
    public List<Employee> selectByDepartment(String department) {
        List<Employee> result = new ArrayList<>();
        List<Employee> employees = selectAll();
        for (Employee e : employees) {
            if (e.getDepartment().contains(department)) {
                result.add(e);
            }
        }
        return result.isEmpty() ? null : result;
    }
}
