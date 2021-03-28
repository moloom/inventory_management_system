package com.mo.mapper;

import com.mo.pojo.Customer;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface CustomerMapper {

    @Insert({"insert into customer(name,contact,telephone,address,create_by,create_time) " +
            "values(#{name},#{contact},#{telephone},#{address},#{create_by},now())"})
    public int insertCustomer(Customer customer);

    @Select("select * from customer ")
    public List<Customer> findAllCustomer();

    @Delete("delete from customer where id=#{id}")
    public int deleteCustomer(Integer id);

    @Select("select * from customer where id=#{id}")
    public Customer findOneCustomerById(Integer id);

    public int updateCustomer(Customer customer);

    public List<Customer> findCustomerListByName(Map<String, Object> map);

    public Integer findCustomerCountByName(Map<String, Object> map);
}
