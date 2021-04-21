package com.mo.mapper;

import com.mo.pojo.MaterialRecord;
import com.mo.pojo.Supplier;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface SupplierMapper {

    @Insert({"insert into supplier(name,contact,telephone,address,create_by,create_time) " +
            "values(#{name},#{contact},#{telephone},#{address},#{create_by},now())"})
    public int insertSupplier(Supplier supplier);

    @Select("select * from supplier ")
    public List<Supplier> findAllSupplier();

    @Delete("delete from supplier where id=#{id}")
    public int deleteSupplier(Integer id);

    @Select("select * from supplier where id=#{id}")
    public Supplier findOneSupplierById(Integer id);

    public int updateSupplier(Supplier supplier);

    public List<Supplier> findSupplierListByName(Map<String, Object> map);

    public Integer findSupplierCountByName(Map<String, Object> map);

    public List<MaterialRecord> findSupplierRecordByid(Integer id, Integer start);

    @Select("select count(1) from material_record where mid in (select id from material where supplier_id=#{id} )")
    public Integer findSupplierRecordCountByid(Integer id);
}
