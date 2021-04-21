package com.mo.mapper;

import com.mo.pojo.Material;
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
public interface MaterialMapper {

    @Select("select * from supplier ")
    public List<Supplier> findAllSupplier();

    @Select("select m.*,s.name as supplier_name from material m , supplier s where m.pid=#{pid} and m.supplier_id=s.id")
    public Material findOneMaterialByPid(String pid);

    @Insert({"insert into material" +
            "(pid,name,specification,color,unit_price,position,minimum_stock,unit,supplier_id,note," +
            "total_quantity,available_quantity,frozen_quantity,requisite_delivery_quantity,create_by,create_time) " +
            "values(#{pid},#{name},#{specification},#{color},#{unit_price},#{position},#{minimum_stock},#{unit},#{supplier_id},#{note}," +
            "#{total_quantity},#{available_quantity},#{frozen_quantity},#{requisite_delivery_quantity},#{create_by},now())"})
    public int insertMaterial(Material material);

    @Select("select count(1) from material_record where mid=#{id}")
    public Integer detailMaterialRecordCount(Integer id);

    @Select("select m.*,s.name as supplier_name from material m , supplier s where m.supplier_id=s.id")
    public List<Material> findAllMaterial();

    @Delete("delete from material where id=#{id}")
    public Integer deleteMaterial(Integer id);

    public List<MaterialRecord> detailMaterialRecord(Integer id, Integer start);

    public List<Material> findMaterialListByNameAndSupplier(Map<String, Object> map);

    public Integer findMaterialCountByNameAndSupplier(Map<String, Object> map);

    public int updateMaterial(Material material);

}
