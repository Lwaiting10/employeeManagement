package com.iweb.dao.impl;


import com.iweb.Util.DBUtil;
import com.iweb.Util.EmployeeEnum;
import com.iweb.dao.EmployeeDAO;
import com.iweb.entity.Employee;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Liu Xiong
 * @date 25/11/2023 下午4:21
 */
public class EmployeeDAOImpl implements EmployeeDAO {
    @Override
    public synchronized boolean insert(Employee e) {
        String sql = "insert into employee(emp_id,name,gender,department,hire_date,birthday)" + "values(?,?,?,?,?,?)";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, e.getId());
            ps.setString(2, e.getName());
            ps.setString(3, e.getGender());
            ps.setString(4, e.getDepartment());
            ps.setDate(5, new Date(e.getHireDate().getTime()));
            ps.setDate(6, new Date(e.getBirthday().getTime()));
            ps.execute();
            return true;
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return false;
    }

    @Override
    public synchronized boolean delete(Integer id) {
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
    public synchronized boolean update(Employee e) {
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
    public List<Employee> selectAll(EmployeeEnum employeeEnum, String key) {
        List<Employee> employees = new ArrayList<>();
        String sql;
        switch (employeeEnum) {
            case ALL:
                sql = "select * from employee";
                break;
            case ID:
                sql = "select * from employee where emp_id =" + Integer.parseInt(key);
                break;
            case NAME:
                sql = "select * from employee where name like '%" + key + "%'";
                break;
            case DEPARTMENT:
                sql = "select * from employee where department like '%" + key + "%'";
                break;
            default:
                return null;
        }
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
    public List<Employee> selectAll() {
        return selectAll(EmployeeEnum.ALL, "");
    }

    @Override
    public Employee selectById(int id) {
        List<Employee> employees = selectAll(EmployeeEnum.ID, String.valueOf(id));
        return employees == null ? null : employees.get(0);
    }

    @Override
    public List<Employee> selectByName(String name) {
        return selectAll(EmployeeEnum.NAME, name);
    }

    @Override
    public List<Employee> selectByDepartment(String department) {
        return selectAll(EmployeeEnum.DEPARTMENT, department);
    }
}
