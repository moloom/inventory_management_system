package com.mo.service;

import com.mo.pojo.Duty;
import com.mo.pojo.Employee;

import java.util.List;

public interface EmployeeService {
    /**
     * 登录
     *
     * @param employee
     * @return
     */
    public Employee login(Employee employee);

    /**
     * 根据 id 查询 1 条employee
     *
     * @param id
     * @return
     */
    public Employee findEmployeeById(Integer id);

    /**
     * 1：查询telephone、oldPassword、id是否正确
     * 2：正确->重新访问数据库修改密码
     *
     * @param employee
     * @param newPassword
     * @return 修改成功返回 1，错误返回 0
     */
    public Employee updatePassword(Employee employee, String newPassword);


    /**
     * 1：查询所有的 Duty
     *
     * @return
     */
    public List<Duty> findDutyList();

    /**
     * 1：生成一个uid，格式为：202188888228的12位编号
     * 2：查询编号是否被注册
     * 3：插入数据
     *
     * @param employee
     * @return
     */
    public String insertEmployee(Employee employee);

    /**
     * 1：查询是否有这个telephone、账号相匹配的字段
     * 2：插入
     *
     * @param employee
     * @return
     */
    public int retrievePassword(Employee employee);
}
