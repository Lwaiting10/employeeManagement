package com.iweb.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Liu Xiong
 * @date 28/11/2023 上午10:40
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Attendance {
    private int attendanceId;
    private int empId;
    private Date startDate;
    private Date endDate;
    private int lateCount;
    private int earlyLeaveCount;
    private String leaveRecord;

    public Attendance(int empId, Date startDate, Date endDate, int lateCount, int earlyLeaveCount, String leaveRecord) {
        this.empId = empId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.lateCount = lateCount;
        this.earlyLeaveCount = earlyLeaveCount;
        this.leaveRecord = leaveRecord;
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return "Attendance(" +
                "attendanceId=" + attendanceId +
                ", empId=" + empId +
                ", startDate=" + (startDate == null ? null : sdf.format(startDate)) +
                ", endDate=" + (endDate == null ? null : sdf.format(endDate)) +
                ", lateCount=" + lateCount +
                ", earlyLeaveCount=" + earlyLeaveCount +
                ", leaveRecord=" + leaveRecord +
                ')';
    }
}
