package com.iweb.dao;


import com.iweb.Util.EmployeeEnum;
import com.iweb.entity.Employee;

import java.util.List;

/**
 * @author Liu Xiong
 * @date 25/11/2023 下午4:15
 */
public interface EmployeeDAO {
    /**
     * 信息添加
     *
     * @param e 员工对象
     */
    boolean insert(Employee e);

    /**
     * 信息删除
     *
     * @param id 需要删除的员工的id
     */
    boolean delete(Integer id);

    /**
     * 更改信息
     *
     * @param e 根据传入的员工对象的内容进行整体替换
     */
    boolean update(Employee e);


    /**
     * 查询所有员工信息
     *
     * @return 返回员工类型的集合
     */
    List<Employee> selectAll(EmployeeEnum employeeEnum, String key);
    List<Employee> selectAll();

    /**
     * 根据id关键字查询员工信息
     *
     * @return 返回员工类型的集合
     */
    Employee selectById(int id);

    /**
     * 根据name关键字查询员工信息
     *
     * @return 返回员工类型的集合
     */
    List<Employee> selectByName(String name);

    /**
     * 根据部门关键字查询员工信息
     *
     * @return 返回员工类型的集合
     */
    List<Employee> selectByDepartment(String department);
}
