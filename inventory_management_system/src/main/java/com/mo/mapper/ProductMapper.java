package com.mo.mapper;

import com.mo.pojo.ClothingTypes;
import com.mo.pojo.Product;
import com.mo.pojo.ProductRecord;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
@org.springframework.stereotype.Repository
public interface ProductMapper {

    @Insert("insert into clothing_types(name,create_by,create_time) values(#{name},#{create_by},now())")
    public int insertClothingTypes(ClothingTypes clothingTypes);

    public int insertProduct(Product product);

    @Delete("delete from clothing_types where id=#{id} ")
    public int deleteClothingTypes(Integer id);

    @Delete("delete from product where id=#{id} ")
    public int deleteProduct(Integer id);

    public int updateProduct(Product product);

    @Select("select c.*,e.name as create_name from clothing_types c,employee e where e.id=c.create_by ")
    public List<ClothingTypes> findAllClothingTypes();

    @Select("select p.*,c.name as clothing_types_name from product p, clothing_types c where c.id=p.clothing_types_id")
    public List<Product> findAllProduct();

    @Select("select p.*,c.name as clothing_types_name from product p, clothing_types c where p.id=#{id} and c.id=p.clothing_types_id")
    public Product findOneProductById(Integer id);

    @Select("select c.*,p.pid as product_id,p.name as product_name from product_record c ,product p where c.pid=#{id} and c.pid=p.id")
    public List<ProductRecord> findProductRecordByProductId(Integer id);

    @Select("select count(1) from product_record  where pid=#{id}")
    public Integer findProductRecordCountByProductId(Integer id);


    public List<Product> findProductListByMap(Map<String, Object> map);

    public Integer findProductCountByMap(Map<String, Object> map);
}
