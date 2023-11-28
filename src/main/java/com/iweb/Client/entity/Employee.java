package com.iweb.Client.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author Liu Xiong
 * @date 25/11/2023 下午4:12
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    private Integer id;
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
}
