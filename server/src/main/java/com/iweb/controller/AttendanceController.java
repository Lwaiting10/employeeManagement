package com.iweb.controller;

import com.iweb.Util.CommunicationUtil;
import com.iweb.Util.TransformUtil;
import com.iweb.entity.Attendance;
import com.iweb.entity.User;
import com.iweb.service.AttendanceService;

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
            String key = CommunicationUtil.receive(socket);
            if (key == null) {
                CommunicationUtil.send(socket, "wrong");
                continue;
            }
            switch (key) {
                // 0 - 退出
                case "0": {
                    CommunicationUtil.send(socket, "exit");
                    return;
                }
                // 1 - 查看所有考勤信息
                case "1": {
                    CommunicationUtil.send(socket, "selectAll");
                    // 发送所有考勤信息
                    List<Attendance> attendances = AttendanceService.getAllAttendance();
                    if (attendances != null) {
                        CommunicationUtil.send(socket, attendances.toString());
                    } else {
                        // 没有数据返回 null
                        CommunicationUtil.send(socket, "null");
                    }
                    break;
                }
                // 2 - 根据员工id查找
                case "2": {
                    CommunicationUtil.send(socket, "selectByEmpId");
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
                    break;
                }
                // 3 - 添加信息
                case "3": {
                    // TODO: 28/11/2023 做相应的限制管理
                    CommunicationUtil.send(socket, "insert");
                    // 获取客户端发来的新增考勤信息
                    Attendance newAttendance = TransformUtil.getAttendance(CommunicationUtil.receive(socket));
                    // 新增操作
                    if (newAttendance != null) {
                        if (AttendanceService.insertAttendance(newAttendance)) {
                            // 新增成功,返回true
                            CommunicationUtil.send(socket, "true");
                        } else {
                            // 新增失败,返回false
                            CommunicationUtil.send(socket, "false");
                        }
                    } else {
                        // 数据有误
                        CommunicationUtil.send(socket, "false");
                    }
                    break;
                }
                // 4 - 更改信息
                case "4": {
                    CommunicationUtil.send(socket, "update");
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
                    break;
                }
                // 5 - 删除信息
                case "5": {
                    CommunicationUtil.send(socket, "delete");
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
                            if (AttendanceService.deleteAttendance(attendance.getAttendanceId())) {
                                // 删除成功，返回true
                                CommunicationUtil.send(socket, "true");
                            } else {
                                // 删除失败,返回false
                                CommunicationUtil.send(socket, "false");
                            }
                        } else {
                            // 取消删除
                            break;
                        }
                    } else {
                        // 没有找到，返回null
                        CommunicationUtil.send(socket, "null");
                    }
                    break;
                }
                default:
                    CommunicationUtil.send(socket, "wrong");
            }
        }
    }
}
