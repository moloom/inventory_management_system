package com.mo.mapper;

import com.mo.pojo.Duty;
import com.mo.pojo.Employee;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeMapper {
//    驼峰命名和_命名转换
//    @Options(useGeneratedKeys = true,keyProperty = "id")

    /**
     * 查询 1 条employee
     * 条件：uid，password
     *
     * @param employee
     * @return
     */
    @Select("select e.*,d.name as duty_name from employee e , duty d where e.uid=#{uid} and e.password=#{password} and e.duty_id=d.id")
    public Employee login(Employee employee);

    /**
     * 查询 1 条employee
     * 条件：id
     *
     * @param id
     * @return
     */
    @Select("select e.*,d.name as duty_name from employee e , duty d where e.id=#{id} and e.duty_id=d.id")
    public Employee findEmployeeById(Integer id);

    /**
     * 查询 1 条 employee
     * 条件；id、telephone、password
     *
     * @param employee
     * @return
     */
    @Select("select * from employee where id=#{id} and telephone=#{telephone} and password=#{password}")
    public Employee findEmployeeByIdTelephonePassword(Employee employee);

    /**
     * 修改 密码
     * 条件： id、password
     *
     * @param employee
     * @return
     */
    @Update("update employee set password = #{password} , modify_time = now() where id = #{id}")
    public int updatePassword(Employee employee);

    @Select("select * from duty")
    public List<Duty> findDutyList();

    @Select("select * from employee where uid=#{uid}")
    public Employee findEmployeeByUid(String uid);

    @Insert({"insert into employee(uid,name,telephone,password,sex,duty_id,rights,birthday,create_by,create_time) " +
            "values(#{uid},#{name},#{telephone},#{password},#{sex},#{duty_id},#{rights},#{birthday},#{create_by},now())"})
    public int insertEmployee(Employee employee);

    /**
     * 查询 1 条 employee
     * 条件；uid、telephone
     *
     * @param employee
     * @return
     */
    @Select("select * from employee where uid=#{uid} and telephone=#{telephone}")
    public Employee findEmployeeByUidTelephone(Employee employee);
}
