package com.iweb.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Liu Xiong
 * @date 25/11/2023 下午5:02
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Salary {
    private int empId;
    private BigDecimal baseSalary;
    // 奖金
    private BigDecimal bonus;
    // 扣款
    private BigDecimal deductions;
    private Date paymentDate;
    private String notes;

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return "Salary(" +
                "empId=" + empId +
                ", baseSalary=" + baseSalary +
                ", bonus=" + bonus +
                ", deductions=" + deductions +
                ", paymentDate=" + (paymentDate == null ? null : sdf.format(paymentDate)) +
                ", notes=" + notes +
                ')';
    }
}
