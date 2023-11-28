package com.iweb.dao.impl;

import com.iweb.Util.AttendanceEnum;
import com.iweb.Util.DBUtil;
import com.iweb.dao.AttendanceDAO;
import com.iweb.entity.Attendance;
import com.iweb.entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Liu Xiong
 * @date 28/11/2023 下午12:07
 */
public class AttendanceDAOImpl implements AttendanceDAO {
    @Override
    public synchronized boolean insert(Attendance a) {
        String sql = "insert into attendance(emp_id,start_date,end_date,late_count,early_leave_count,leave_record)"
                + "values(?,?,?,?,?,?)";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, a.getEmpId());
            ps.setDate(2, new Date(a.getStartDate().getTime()));
            ps.setDate(3, new Date(a.getEndDate().getTime()));
            ps.setInt(4, a.getLateCount());
            ps.setInt(5, a.getEarlyLeaveCount());
            ps.setString(6, a.getLeaveRecord());
            ps.execute();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                a.setAttendanceId(rs.getInt(1));
            }
            return true;
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return false;
    }

    @Override
    public synchronized boolean delete(Integer id) {
        String sql = "delete from attendance where attendance_id =" + id;
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.execute();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public synchronized boolean update(Attendance a) {
        String sql = "update attendance set emp_id=?,start_date=?,end_date=?,late_count=?," +
                "early_leave_count=?,leave_record=? where attendance_id =?";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)
        ) {
            ps.setInt(1, a.getEmpId());
            ps.setDate(2, new Date(a.getStartDate().getTime()));
            ps.setDate(3, new Date(a.getEndDate().getTime()));
            ps.setInt(4, a.getLateCount());
            ps.setInt(5, a.getEarlyLeaveCount());
            ps.setString(6, a.getLeaveRecord());
            ps.execute();
            return true;
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Attendance> selectAll(AttendanceEnum attendanceEnum, int key) {
        List<Attendance> attendances = new ArrayList<>();
        String sql;
        switch (attendanceEnum) {
            case ALL:
                sql = "select * from attendance";
                break;
            case EMP_ID:
                sql = "select * from attendance where emp_id =" + key;
                break;
            default:
                return null;
        }
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)
        ) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Attendance a = new Attendance();
                a.setAttendanceId(rs.getInt("attendance_id"));
                a.setEmpId(rs.getInt("emp_id"));
                a.setStartDate(rs.getDate("start_date"));
                a.setEndDate(rs.getDate("end_date"));
                a.setLateCount(rs.getInt("late_count"));
                a.setEarlyLeaveCount(rs.getInt("early_leave_count"));
                a.setLeaveRecord(rs.getString("leave_record"));
                attendances.add(a);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return attendances.isEmpty() ? null : attendances;
    }

    @Override
    public List<Attendance> selectAll() {
        return selectAll(AttendanceEnum.ALL, 0);
    }

    @Override
    public Attendance selectByEmpId(int empId) {
        List<Attendance> attendances = selectAll(AttendanceEnum.EMP_ID, empId);
        return attendances == null ? null : attendances.get(0);
    }
}
