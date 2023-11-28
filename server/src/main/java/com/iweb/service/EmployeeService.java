package com.iweb.service;

import com.iweb.dao.EmployeeDAO;
import com.iweb.dao.impl.EmployeeDAOImpl;
import com.iweb.entity.Employee;

import java.util.List;

/**
 * @author Liu Xiong
 * @date 27/11/2023 下午12:53
 */
public class EmployeeService {
    private static EmployeeDAO employeeDAO = new EmployeeDAOImpl();

    /**
     * 查询所有员工信息
     *
     * @return 返回所有员工的集合，没有则返回null
     */
    public static List<Employee> getAllEmployee() {
        return employeeDAO.selectAll();
    }

    public static Employee selectById(int id) {
        return employeeDAO.selectById(id);
    }

    /**
     * 根据姓名模糊查询员工信息
     *
     * @return 返回员工的集合，没有则返回null
     */
    public static List<Employee> selectByName(String name) {
        return employeeDAO.selectByName(name);
    }

    /**
     * 根据部门名称模糊查询员工信息
     *
     * @return 返回员工的集合，没有则返回null
     */
    public static List<Employee> selectByDepartment(String department) {
        return employeeDAO.selectByDepartment(department);
    }

    /**
     * 新增员工
     */
    public static boolean insertEmployee(Employee employee) {
        // 员工id重复则不允许新增
        if (selectById(employee.getId()) != null) {
            return false;
        }
        return employeeDAO.insert(employee);
    }

    /**
     * 更改信息
     */
    public static boolean updateEmployee(Employee employee) {
        return employeeDAO.update(employee);
    }

    /**
     * 删除员工
     */
    public static boolean deleteEmployee(int id) {
        return employeeDAO.delete(id);
    }
}
