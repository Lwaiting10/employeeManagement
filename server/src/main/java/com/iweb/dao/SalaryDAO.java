package com.iweb.dao;

import com.iweb.Util.SalaryEnum;
import com.iweb.entity.Salary;

import java.util.List;

/**
 * @author Liu Xiong
 * @date 28/11/2023 上午10:45
 */
public interface SalaryDAO {
    /**
     * 信息添加
     *
     * @param s 薪资对象
     */
    boolean insert(Salary s);

    /**
     * 信息删除
     *
     * @param id 需要删除的薪资的id
     */
    boolean delete(Integer id);

    /**
     * 更改信息
     *
     * @param s 根据传入的薪资对象的内容进行整体替换
     */
    boolean update(Salary s);


    /**
     * 查询所有薪资信息
     *
     * @return 返回薪资类型的集合
     */
    List<Salary> selectAll(SalaryEnum salaryEnum,String key);
    List<Salary> selectAll();


    /**
     * 根据员工id关键字查询薪资信息
     *
     * @return 返回薪资类型的集合
     */
    Salary selectByEmpId(int empId);

    /**
     * 根据方法日期关键字查询薪资信息
     *
     * @return 返回薪资类型的集合
     */
    List<Salary> selectByPaymentDate(String paymentDate);
}
