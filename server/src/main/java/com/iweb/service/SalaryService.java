package com.iweb.service;

import com.iweb.dao.SalaryDAO;
import com.iweb.dao.impl.SalaryDAOImpl;
import com.iweb.entity.Salary;

import java.util.List;

/**
 * @author Liu Xiong
 * @date 28/11/2023 下午2:12
 */
public class SalaryService {
    private static SalaryDAO salaryDAO = new SalaryDAOImpl();

    /**
     * 查询所有薪资信息
     *
     * @return 返回所有薪资的集合，没有则返回null
     */
    public static List<Salary> getAllSalary() {
        return salaryDAO.selectAll();
    }

    public static Salary selectByEmpId(int id) {
        return salaryDAO.selectByEmpId(id);
    }


    /**
     * 根据发放日期模糊查询薪资信息
     *
     * @return 返回薪资的集合，没有则返回null
     */
    public static List<Salary> selectByPaymentDate(String paymentDate) {
        return salaryDAO.selectByPaymentDate(paymentDate);
    }

    /**
     * 新增薪资
     * 员工id重复则不允许新增
     */
    public static boolean insertSalary(Salary salary) {
        // 员工id重复则不允许新增
        if (selectByEmpId(salary.getEmpId()) != null) {
            return false;
        }
        return salaryDAO.insert(salary);
    }

    /**
     * 更改信息
     */
    public static boolean updateSalary(Salary salary) {
        return salaryDAO.update(salary);
    }

    /**
     * 删除信息
     */
    public static boolean deleteSalary(int id) {
        return salaryDAO.delete(id);
    }
}
