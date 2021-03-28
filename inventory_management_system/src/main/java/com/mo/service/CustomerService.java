package com.mo.service;

import com.mo.pojo.Customer;

import java.util.List;
import java.util.Map;

public interface CustomerService {


    /**
     * 插入一条 customer
     *
     * @param customer
     * @return
     */
    public int insertCustomer(Customer customer);

    /**
     * 查找所有的 customer
     *
     * @return
     */
    public List<Customer> findAllCustomer();

    /**
     * 删除一条 customer
     *
     * @param id
     * @return
     */
    public int deleteCustomer(Integer id);

    /**
     * 修改一条 customer
     *
     * @param customer
     * @return
     */
    public int updateCustomer(Customer customer);

    /**
     * 查询一条 customer
     * 条件：id
     *
     * @param id
     * @return
     */
    public Customer findOneCustomerById(Integer id);

    /**
     * 查询customer 集合
     * 条件：name
     *
     * @param map
     * @return
     */
    public List<Customer> findCustomerListByName(Map<String, Object> map);

    /**
     * 查询符合条件的记录数
     * 条件：name
     *
     * @param map
     * @return
     */
    public Integer findCustomerCountByName(Map<String, Object> map);
}
