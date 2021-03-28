package com.mo.mapper;

import com.mo.pojo.MaterialRecord;
import com.mo.pojo.InRepository;
import com.mo.pojo.Material;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface InRepositoryMapper {

    @Delete("drop from in_repository where id=#{id}")
    public int deleteInRepository(Integer id);

    @Select("select * from in_repository where bid=#{bid}")
    public InRepository findOneInRepositoryByBid(String bid);

    @Update("update in_repository set material_id=null,quantity=null,unit_price=null,supplier_id=null where id=#{id}")
    public int updateInRepositoryToNull(Integer id);

    @Select("select * from repository")
    public List<com.mo.pojo.Repository> findAllRepository();

    @Select("select * from material where id=#{id}")
    public Material findMaterialById(Integer id);

    public int insertInRepository(InRepository inRepository);

    public int updateInRepository(InRepository inRepository);

    public Integer findMaterialCountByNameAndSupplier(Map<String, Object> map);

    public List<Material> findMaterialListByNameAndSupplier(Map<String, Object> map);

    public int insertMaterialRecord(MaterialRecord materialRecord);

    public int updateMaterialQuantityOfTotalAndAvailable(Material material);

}
