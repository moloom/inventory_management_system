package com.mo.service.impl;

import com.mo.mapper.EmployeeMapper;
import com.mo.pojo.Duty;
import com.mo.pojo.Employee;
import com.mo.service.EmployeeService;
import com.mo.utils.MyToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    /**
     * 登录
     *
     * @param employee
     * @return
     */
    @Transactional(readOnly = true,timeout = 15)
    @Override
    public Employee login(Employee employee) {
        return employeeMapper.login(employee);
    }


    /**
     * 根据 id 查询 1 条employee
     *
     * @param id
     * @return
     */
    @Transactional(readOnly = true,timeout = 15)
    @Override
    public Employee findEmployeeById(Integer id) {
        return employeeMapper.findEmployeeById(id);
    }

    /**
     * 1：查询telephone、oldPassword、id是否正确
     * 2：正确->重新访问数据库修改密码
     *
     * @param employee
     * @param newPassword
     * @return 修改成功返回 1，错误返回 0
     *
     */
    @Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED, timeout = 15)
    @Override
    public Employee updatePassword(Employee employee, String newPassword) {
        Employee employee2 = employeeMapper.findEmployeeByIdTelephonePassword(employee);
        if (employee2 != null) {
            employee2.setPassword(newPassword);
            System.out.println("====2==" + employee2);
            int flag = employeeMapper.updatePassword(employee2);
            if (flag == 1) {
                return employee2;
            }
        }
        return null;
    }

    /**
     * 查询所有的 Duty
     *
     * @return
     */
    @Transactional(readOnly = true,timeout = 15)
    @Override
    public List<Duty> findDutyList() {
        return employeeMapper.findDutyList();
    }

    /**
     * 1：生成一个uid，格式为：202188888228的12位编号
     * 2：查询编号是否被注册
     * 3：插入数据
     *
     * @param employee
     * @return
     */
    @Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED, timeout = 15)
    @Override
    public String insertEmployee(Employee employee) {
        MyToString myToString = new MyToString();
        employee.setUid(myToString.getMyToString());
        //根据职务给予权限
        if (employee.getDuty_id() == 3) {
            employee.setRights(4);
        } else if (employee.getDuty_id() == 2) {
            employee.setRights(3);
        } else if (employee.getDuty_id() == 1) {
            employee.setRights(2);
        }
        employee.setCreate_by(1);
        Employee employee1 = employeeMapper.findEmployeeByUid(employee.getUid());
        if (employee1 == null) {
            int flag = employeeMapper.insertEmployee(employee);
            if (flag == 1) {
                return employee.getUid();
            }
        }
        return "0";
    }

    /**
     * 1：查询是否有这个telephone、账号相匹配的字段
     * 2：插入
     *
     * @param employee
     * @return
     */
    @Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED, timeout = 15)
    @Override
    public int retrievePassword(Employee employee) {
        int flag = 0;
        Employee employee1 = employeeMapper.findEmployeeByUidTelephone(employee);
        if (employee1 != null) {
            employee.setId(employee1.getId());
            flag = employeeMapper.updatePassword(employee);
        }
        return flag;
    }


}
