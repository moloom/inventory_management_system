package com.mo.mapper;

import com.mo.pojo.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface PInOutRepositoryMapper {

    @Select("select * from p_in_out_repository where bid=#{bid}")
    public PInOutRepository findOnePInOutRepositoryByBid(String bid);

    public PInOutRepository findOnePInOutRepositoryByid(String id);

    @Update("update p_in_out_repository set product_id=null,quantity=null,unit_price=null where id=#{id}")
    public int updatePInOutRepositoryToNull(Integer id);

    public Integer deleteEmptyPIOR();

    @Select("select * from clothing_types")
    public List<ClothingTypes> findAllClothingTypes();

    @Select("select * from repository")
    public List<com.mo.pojo.Repository> findAllRepository();

    @Select("select p.*,c.name as clothing_types_name from product p,clothing_types c where p.id=#{id} and c.id=p.clothing_types_id")
    public Product findProductById(Integer id);

    @Select("select * from customer ")
    public List<Customer> findAllCustomer();

    @Select("select name from customer where id=#{id} ")
    public String findCustomerName(Integer id);

    @Select("select r.*,p.name as product_name,p.pid as product_id,e.name as create_name " +
            "from product_record r,product p,employee e " +
            "where r.iid like #{bid} and r.pid=p.id and e.id=r.create_by ")
    public List<ProductRecord> findProductRecordByPid(String bid);

    public List<Product> viewAlertRP();

    public List<PInOutRepository> findPiorList(Map<String, Object> map);

    public Integer findPiorCount(Map<String, Object> map);

    public int insertPInOutRepository(PInOutRepository pInOutRepository);

    public int updatePInOutRepository(PInOutRepository pInOutRepository);

    @Update("update p_in_out_repository set status=3,modify_by=#{modify},modify_time=now()  where id=#{id} ")
    public Integer updatePIORStatus(Integer id, Integer modify);

    public Integer findProductCountByNameAndClothing_types_id(Map<String, Object> map);

    public List<Product> findProductListByNameAndClothing_types_id(Map<String, Object> map);

    public int insertProductRecord(ProductRecord productRecord);

    public int updateProductQuantityOfTotalAndAvailable(Product product);

}
