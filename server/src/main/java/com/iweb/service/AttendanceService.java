package com.iweb.service;

import com.iweb.dao.AttendanceDAO;
import com.iweb.dao.impl.AttendanceDAOImpl;
import com.iweb.entity.Attendance;

import java.util.List;

/**
 * @author Liu Xiong
 * @date 28/11/2023 下午2:12
 */
public class AttendanceService {
    private static AttendanceDAO attendanceDAO = new AttendanceDAOImpl();

    /**
     * 查询所有考勤信息
     *
     * @return 返回所有考勤的集合，没有则返回null
     */
    public static List<Attendance> getAllAttendance() {
        return attendanceDAO.selectAll();
    }

    public static Attendance selectByEmpId(int id) {
        return attendanceDAO.selectByEmpId(id);
    }

    /**
     * 新增考勤
     * 员工id重复则不允许新增
     */
    public static boolean insertAttendance(Attendance attendance) {
        // 员工id重复则不允许新增
        if (selectByEmpId(attendance.getEmpId()) != null) {
            return false;
        }
        return attendanceDAO.insert(attendance);
    }

    /**
     * 更改信息
     */
    public static boolean updateAttendance(Attendance attendance) {
        return attendanceDAO.update(attendance);
    }

    /**
     * 删除信息
     */
    public static boolean deleteAttendance(int id) {
        return attendanceDAO.delete(id);
    }
}
