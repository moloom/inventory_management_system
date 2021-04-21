package com.mo.mapper;

import com.mo.pojo.Duty;
import com.mo.pojo.Employee;
import com.mo.pojo.Material;
import com.mo.pojo.Product;
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

    /**
     * 查询 product 当前数量低于最低库存的
     *
     * @return
     */
    @Select("select * from material where available_quantity <= minimum_stock")
    public List<Material> findViewAlertRM();

    /**
     * 查询 product 当前数量低于最低库存的
     *
     * @return
     */
    @Select("select * from product where available_quantity <= minimum_stock")
    public List<Product> findViewAlertRP();

    /**
     * 查询 7天内，被使用最多的4种物料
     *
     * @return
     */
    @Select("select sum(quantity) as quantity,mid as id from  material_record " +
            "where status=2 and DATE_SUB(CURDATE(), INTERVAL 7 DAY) <= date(create_time) " +
            "group by mid order by sum(quantity) limit 0,4")
    public List<Material> findMaterialUseInSeven();

    /**
     * 查询物料名称
     * 条件：id
     *
     * @param id
     * @return
     */
    @Select("select name from material where id=#{id}")
    public String findMaterialNameById(Integer id);

    /**
     * 查询 最近7天内 物料被领的数量
     *
     * @return
     */
    @Select("select sum(quantity) from  material_record where status=2 and DATE_SUB(CURDATE(), INTERVAL 7 DAY) <= date(create_time)")
    public Integer findTotalUseInSeven();

    /**
     * 查询 近7天内 销量最高的 product
     *
     * @return
     */
    @Select("select sum(quantity) as quantity,pid as id from  product_record " +
            "where status=2 and DATE_SUB(CURDATE(), INTERVAL 7 DAY) <= date(create_time) group by pid order by sum(quantity) limit 0,10")
    public List<Product> findProductSalesInSeven();

    /**
     * 查询近7天的销量总数
     *
     * @return
     */
    @Select("select sum(quantity) from  product_record where status=2 and DATE_SUB(CURDATE(), INTERVAL 7 DAY) <= date(create_time)")
    public Integer findTotalProductUseInSeven();

    /**
     * 查询 product 的name
     * 条件：id
     *
     * @param id
     * @return
     */
    @Select("select * from product where id=#{id}")
    public Product findProductById(Integer id);

    /**
     * 查询 商品的条数
     *
     * @return
     */
    @Select("select count(1) from product")
    public Integer findProductCount();

    /**
     * 查询物料的条数
     *
     * @return
     */
    @Select("select count(1) from material")
    public Integer findMaterialCount();

    /**
     * 查询客户的条数
     *
     * @return
     */
    @Select("select count(1) from customer")
    public Integer findCustomerCount();

    /**
     * 查询供应商的条数
     *
     * @return
     */
    @Select("select count(1) from supplier")
    public Integer findSupplierCount();

    /**
     * 查询 多少天 内的销售额
     * 条件 ：天数
     *
     * @param day
     * @return
     */
    @Select("select sum(total_price) from p_in_out_repository where DATE_SUB(CURDATE(), INTERVAL #{day} DAY) <= date(create_time)")
    public Float findSalesInDay(Integer day);
}
