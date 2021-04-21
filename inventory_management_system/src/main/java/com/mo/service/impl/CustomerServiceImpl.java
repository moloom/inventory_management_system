package com.mo.service.impl;

import com.mo.mapper.CustomerMapper;
import com.mo.pojo.Customer;
import com.mo.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerMapper customerMapper;

    /**
     * 插入一条 customer
     *
     * @param customer
     * @return
     */
    @Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED, timeout = 15)
    @Override
    public int insertCustomer(Customer customer) {
        return customerMapper.insertCustomer(customer);
    }

    /**
     * 查找所有的 customer
     *
     * @return
     */
    @Transactional(readOnly = true, timeout = 15)
    @Override
    public List<Customer> findAllCustomer() {
        return customerMapper.findAllCustomer();
    }

    /**
     * 删除一条 customer
     *
     * @param id
     * @return
     */
    @Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED, timeout = 15)
    @Override
    public int deleteCustomer(Integer id) {
        return customerMapper.deleteCustomer(id);
    }

    /**
     * 修改一条 customer
     *
     * @param customer
     * @return
     */
    @Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED, timeout = 15)
    @Override
    public int updateCustomer(Customer customer) {
        return customerMapper.updateCustomer(customer);
    }

    /**
     * 查询一条 customer
     * 条件：id
     *
     * @param id
     * @return
     */
    @Transactional(readOnly = true)
    @Override
    public Customer findOneCustomerById(Integer id) {
        return customerMapper.findOneCustomerById(id);
    }

    /**
     * 查询customer 集合
     * 条件：name
     *
     * @param map
     * @return
     */
    @Transactional(readOnly = true)
    @Override
    public List<Customer> findCustomerListByName(Map<String, Object> map) {
        return customerMapper.findCustomerListByName(map);
    }

    /**
     * 查询符合条件的记录数
     * 条件：name
     *
     * @param map
     * @return
     */
    @Transactional(readOnly = true)
    @Override
    public Integer findCustomerCountByName(Map<String, Object> map) {
        return customerMapper.findCustomerCountByName(map);
    }
}
