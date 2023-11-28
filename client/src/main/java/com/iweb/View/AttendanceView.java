package com.iweb.View;

import com.iweb.Util.CommunicationUtil;
import com.iweb.Util.ScannerUtil;
import com.iweb.Util.ShowUtil;
import com.iweb.Util.TransformUtil;
import com.iweb.entity.Attendance;

import java.util.Date;
import java.util.List;

import static com.iweb.Util.PrintUtil.log;

/**
 * 考勤管理
 *
 * @author Liu Xiong
 * @date 28/11/2023 下午2:42
 */
public class AttendanceView {
    public static void mainView() {
        log("===================考勤管理===================");
        while (true) {
            log("0 - 退出");
            log("1 - 查看所有考勤信息");
            log("2 - 根据员工id查找");
            log("3 - 添加信息");
            log("4 - 更改信息");
            log("5 - 删除信息");
            String message = CommunicationUtil.chooseAndGetMessage();
            if ("exit".equals(message)) {
                return;
            }
            // 1 - 查看所有考勤信息
            if ("selectAll".equals(message)) {
                selectAllView();
            }
            // 2 - 根据员工id查找
            if ("selectByEmpId".equals(message)) {
                selectByEmpIdView();
            }
            // 3 - 添加信息
            if ("insert".equals(message)) {
                insertView();
            }
            // 4 - 更改信息
            if ("update".equals(message)) {
                updateView();
            }
            // 5 - 删除信息
            if ("delete".equals(message)) {
                deleteView();
            }
        }
    }

    /**
     * 查找所有的页面
     */
    private static void selectAllView() {
        // 接收服务器数据
        List<Attendance> attendances = TransformUtil.getAttendanceList(CommunicationUtil.receive());
        // 打印信息
        ShowUtil.showAttendance(attendances);
    }

    /**
     * 根据员工id查找页面
     */
    private static void selectByEmpIdView() {
        // 发送要查询的员工id信息
        CommunicationUtil.send(String.valueOf(ScannerUtil.getId()));
        // 接收服务器反馈
        Attendance attendance = TransformUtil.getAttendance(CommunicationUtil.receive());
        // 展示信息
        ShowUtil.showAttendance(attendance);
    }

    /**
     * 新增页面
     */
    private static void insertView() {
        // TODO: 28/11/2023 做相应的限制管理
        // 获取添加页面的输入的考勤信息 并 将新增信息发给服务器
        log("输入新增考勤信息:");
        CommunicationUtil.send(getAttendance(getEmpId()).toString());
        // 获取服务器反馈
        if ("true".equals(CommunicationUtil.receive())) {
            log("新增成功!");
        } else {
            log("新增失败！");
        }
    }

    /**
     * 删除页面
     */
    private static void deleteView() {
        log("输入要删除的员工id:");
        // 发送需要删除的员工id
        CommunicationUtil.send(String.valueOf(ScannerUtil.getInt()));
        // 接收服务器返回的数据
        Attendance attendance = TransformUtil.getAttendance(CommunicationUtil.receive());
        if (attendance != null) {
            // 获得数据
            // 展示原数据
            log("该员工的考勤信息如下:");
            ShowUtil.showAttendance(attendance);
            // 删除确认操作
            CommunicationUtil.deleteConfirm();
        } else {
            // 没有获取数据
            log("没有该id的员工考勤信息！");
        }
    }

    /**
     * 更改信息页面
     */
    private static void updateView() {
        log("输入要更改信息的员工id:");
        // 发送需要更改信息的员工id
        CommunicationUtil.send(String.valueOf(ScannerUtil.getInt()));
        // 接收服务器返回的数据
        Attendance attendance = TransformUtil.getAttendance(CommunicationUtil.receive());
        if (attendance != null) {
            // 获得数据
            // 展示原数据
            log("原数据如下:");
            ShowUtil.showAttendance(attendance);
            log("输入修改后的数据:");
            // 获取修改后的数据
            Attendance attendanceUpdate = getAttendance(attendance.getEmpId());
            attendanceUpdate.setAttendanceId(attendance.getAttendanceId());
            // 将数据发送回服务器
            CommunicationUtil.send(attendanceUpdate.toString());
            // 接收服务器反馈
            if ("true".equals(CommunicationUtil.receive())) {
                log("修改成功！");
            } else {
                log("修改失败！");
            }
        } else {
            // 没有获取数据
            log("没有该id的员工考勤信息！");
        }
    }

    private static int getEmpId() {
        log("员工id(纯数字):");
        return ScannerUtil.getInt();
    }

    private static Attendance getAttendance(int inputEmpId) {
        log("考勤开始日期(yyyy-MM-dd):");
        Date inputStartDate = ScannerUtil.getDate();
        log("考勤结束日期(yyyy-MM-dd):");
        Date inputEndDate = ScannerUtil.getDate();
        log("迟到次数:");
        int inputLateCount = ScannerUtil.getInt();
        log("早退次数:");
        int inputEarlyLeaveCount = ScannerUtil.getInt();
        log("请假情况:");
        String inputLeaveRecord = ScannerUtil.getString();
        return new Attendance
                (inputEmpId, inputStartDate, inputEndDate, inputLateCount, inputEarlyLeaveCount, inputLeaveRecord);
    }
}
