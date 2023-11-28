package com.iweb.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Liu Xiong
 * @date 25/11/2023 下午4:12
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    private int id;
    private String name;
    private String gender;
    private String department;
    private Date hireDate;
    private Date birthday;

    public Employee(String name, String gender, String department, Date hireDate, Date birthday) {
        this.name = name;
        this.gender = gender;
        this.department = department;
        this.hireDate = hireDate;
        this.birthday = birthday;
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return "Employee(" +
                "id=" + id +
                ", name=" + name +
                ", gender=" + gender +
                ", department=" + department +
                ", hireDate=" + (hireDate == null ? null : sdf.format(hireDate)) +
                ", birthday=" + (birthday == null ? null : sdf.format(birthday)) +
                ')';
    }
}
