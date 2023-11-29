package com.iweb.controller;

import com.iweb.Util.CommunicationUtil;
import com.iweb.Util.TransformUtil;
import com.iweb.entity.Attendance;
import com.iweb.entity.User;
import com.iweb.service.AttendanceService;
import com.iweb.service.EmployeeService;

import java.io.IOException;
import java.net.Socket;
import java.util.List;

/**
 * @author Liu Xiong
 * @date 28/11/2023 下午2:45
 */
public class AttendanceController {
    public static void attendanceManage(Socket socket, User user) throws IOException {
        while (true) {
            switch (CommunicationUtil.receive(socket)) {
                // 0 - 退出
                case "0": {
                    CommunicationUtil.send(socket, "exit");
                    return;
                }
                // 1 - 查看所有考勤信息
                case "1": {
                    CommunicationUtil.send(socket, "selectAll");
                    selectAll(socket, user);
                    break;
                }
                // 2 - 根据员工id查找
                case "2": {
                    CommunicationUtil.send(socket, "selectByEmpId");
                    selectByEmpId(socket, user);
                    break;
                }
                // 3 - 添加信息
                case "3": {
                    CommunicationUtil.send(socket, "insert");
                    insert(socket, user);
                    break;
                }
                // 4 - 更改信息
                case "4": {
                    CommunicationUtil.send(socket, "update");
                    update(socket, user);
                    break;
                }
                // 5 - 删除信息
                case "5": {
                    CommunicationUtil.send(socket, "delete");
                    delete(socket, user);
                    break;
                }
                default:
                    CommunicationUtil.send(socket, "wrong");
            }
        }
    }

    /**
     * 删除信息
     */
    private static void delete(Socket socket, User user) throws IOException {
        // 接收员工id
        int inputEmpId = Integer.parseInt(CommunicationUtil.receive(socket));
        // 查找该员工信息
        Attendance attendance = AttendanceService.selectByEmpId(inputEmpId);
        if (attendance != null) {
            // 找到，返回信息
            CommunicationUtil.send(socket, attendance.toString());
            // 获取客户端的确认信息
            if ("y".equalsIgnoreCase(CommunicationUtil.receive(socket))) {
                // 确认删除
                if (AttendanceService.deleteAttendance(attendance.getEmpId())) {
                    // 删除成功，返回true
                    CommunicationUtil.send(socket, "true");
                } else {
                    // 删除失败,返回false
                    CommunicationUtil.send(socket, "false");
                }
            } else {
                // 取消删除
                return;
            }
        } else {
            // 没有找到，返回null
            CommunicationUtil.send(socket, "null");
        }
    }

    /**
     * 更改信息
     */
    private static void update(Socket socket, User user) throws IOException {
        // 接收员工id
        int inputEmpId = Integer.parseInt(CommunicationUtil.receive(socket));
        // 查找该员工信息
        Attendance attendance = AttendanceService.selectByEmpId(inputEmpId);
        if (attendance != null) {
            // 找到，返回信息
            CommunicationUtil.send(socket, attendance.toString());
            // 接收修改后的数据
            Attendance attendanceUpdate = TransformUtil.getAttendance(CommunicationUtil.receive(socket));
            // 修改操作
            if (AttendanceService.updateAttendance(attendanceUpdate)) {
                // 修改成功,返回true
                CommunicationUtil.send(socket, "true");
            } else {
                // 修改失败,返回false
                CommunicationUtil.send(socket, "false");
            }
        } else {
            // 没有找到，返回null
            CommunicationUtil.send(socket, "null");
        }
    }

    /**
     * 添加信息
     */
    private static void insert(Socket socket, User user) throws IOException {
        // 获取客户端发来的新增考勤信息
        Attendance newAttendance = TransformUtil.getAttendance(CommunicationUtil.receive(socket));
        // 新增操作
        if (newAttendance != null) {
            if (EmployeeService.selectById(newAttendance.getEmpId()) == null) {
                // 不可以新增 员工id不存在的信息
                CommunicationUtil.send(socket, "absent");
            } else if (AttendanceService.selectByEmpId(newAttendance.getEmpId()) != null) {
                // 该员工已经有薪资信息 不可再次添加
                CommunicationUtil.send(socket, "repeat");
            } else {
                if (AttendanceService.insertAttendance(newAttendance)) {
                    // 新增成功,返回true
                    CommunicationUtil.send(socket, "true");
                } else {
                    // 新增失败,返回false
                    CommunicationUtil.send(socket, "false");
                }
            }
        } else {
            // 数据有误
            CommunicationUtil.send(socket, "incorrectData");
        }
    }

    /**
     * 根据员工id查找
     */
    private static void selectByEmpId(Socket socket, User user) throws IOException {
        // 接收要查询的信息
        String inputEmpIdStr = CommunicationUtil.receive(socket);
        // 查询改id是否存在
        Attendance attendance = AttendanceService.selectByEmpId(Integer.parseInt(inputEmpIdStr));
        if (attendance != null) {
            // 存在，返回该对象
            CommunicationUtil.send(socket, attendance.toString());
        } else {
            // 不存在,返回null
            CommunicationUtil.send(socket, "null");
        }
    }

    /**
     * 查看所有考勤信息
     */
    private static void selectAll(Socket socket, User user) throws IOException {
        // 发送所有考勤信息
        List<Attendance> attendances = AttendanceService.getAllAttendance();
        if (attendances != null) {
            CommunicationUtil.send(socket, attendances.toString());
        } else {
            // 没有数据返回 null
            CommunicationUtil.send(socket, "null");
        }
    }
}
