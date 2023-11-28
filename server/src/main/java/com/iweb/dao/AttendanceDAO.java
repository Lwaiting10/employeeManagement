package com.iweb.dao;

import com.iweb.Util.AttendanceEnum;
import com.iweb.entity.Attendance;


import java.util.List;

/**
 * @author Liu Xiong
 * @date 28/11/2023 上午10:46
 */
public interface AttendanceDAO {
    /**
     * 信息添加
     *
     * @param a 除id外的考勤对象
     */
    boolean insert(Attendance a);

    /**
     * 信息删除
     *
     * @param id 需要删除的考勤信息的id
     */
    boolean delete(Integer id);

    /**
     * 更改信息
     *
     * @param a 根据传入的考勤信息对象的内容进行整体替换
     */
    boolean update(Attendance a);

    /**
     * 查询所有考勤信息
     *
     * @return 返回考勤信息类型的集合
     */
    List<Attendance> selectAll(AttendanceEnum attendanceEnum, int key);
    List<Attendance> selectAll();

    /**
     * 根据用户名(员工id)查询考勤信息
     *
     * @return 返回考勤信息
     */
    Attendance selectByEmpId(int empId);
}
