package com.iweb.dao.impl;

import com.iweb.Util.DBUtil;
import com.iweb.Util.SalaryEnum;
import com.iweb.dao.SalaryDAO;
import com.iweb.entity.Salary;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Liu Xiong
 * @date 28/11/2023 上午10:58
 */
public class SalaryDAOImpl implements SalaryDAO {
    @Override
    public synchronized boolean insert(Salary s) {
        String sql = "insert into salary(emp_id,base_salary,bonus,deductions,payment_date,notes)" + "values(?,?,?,?,?,?)";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, s.getEmpId());
            ps.setBigDecimal(2, s.getBaseSalary());
            ps.setBigDecimal(3, s.getBonus());
            ps.setBigDecimal(4, s.getDeductions());
            ps.setDate(5, new Date(s.getPaymentDate().getTime()));
            ps.setString(6, s.getNotes());
            ps.execute();
            return true;
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return false;
    }

    @Override
    public synchronized boolean delete(Integer id) {
        String sql = "delete from salary where emp_id=" + id;
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.execute();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public synchronized boolean update(Salary s) {
        String sql = "update salary set base_salary=?,bonus=?,deductions=?,payment_date=?,notes=? where emp_id=?";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setBigDecimal(1, s.getBaseSalary());
            ps.setBigDecimal(2, s.getBonus());
            ps.setBigDecimal(3, s.getDeductions());
            ps.setDate(4, new Date(s.getPaymentDate().getTime()));
            ps.setString(5, s.getNotes());
            ps.setInt(6, s.getEmpId());
            ps.execute();
            return true;
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Salary> selectAll(SalaryEnum salaryEnum, String key) {
        List<Salary> salaries = new ArrayList<>();
        String sql;
        switch (salaryEnum) {
            case ALL:
                sql = "select * from salary";
                break;
            case EMP_ID:
                sql = "select * from salary where emp_id =" + Integer.parseInt(key);
                break;
            case PAYMENT_DATE:
                sql = "select * from salary where payment_date like '%" + key + "%'";
                break;
            default:
                return null;
        }
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)
        ) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Salary s = new Salary();
                s.setEmpId(rs.getInt("emp_id"));
                s.setBaseSalary(rs.getBigDecimal("base_salary"));
                s.setBonus(rs.getBigDecimal("bonus"));
                s.setDeductions(rs.getBigDecimal("deductions"));
                s.setPaymentDate(rs.getDate("payment_date"));
                s.setNotes(rs.getString("notes"));
                salaries.add(s);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return salaries.isEmpty() ? null : salaries;
    }

    @Override
    public List<Salary> selectAll() {
        return selectAll(SalaryEnum.ALL, "");
    }

    @Override
    public Salary selectByEmpId(int empId) {
        List<Salary> salaries = selectAll(SalaryEnum.EMP_ID, String.valueOf(empId));
        return salaries == null ? null : salaries.get(0);
    }

    @Override
    public List<Salary> selectByPaymentDate(String paymentDate) {
        return selectAll(SalaryEnum.PAYMENT_DATE, paymentDate);
    }
}
