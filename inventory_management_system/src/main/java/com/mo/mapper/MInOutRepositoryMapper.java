package com.mo.mapper;

import com.mo.pojo.MInOutRepository;
import com.mo.pojo.Material;
import com.mo.pojo.MaterialRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface MInOutRepositoryMapper {

    @Update("update m_in_out_repository set material_id=null,quantity=null,unit_price=null,supplier_id=null where id=#{id}")
    public int updateMInOutRepositoryToNull(Integer id);

    @Update("update m_in_out_repository set status=3,modify_by=#{modify},modify_time=now()  where id=#{id} ")
    public Integer updateMIORStatus(Integer id, Integer modify);

    public int insertMaterialRecord(MaterialRecord materialRecord);

    public int updateMaterialQuantityOfTotalAndAvailable(Material material);

    public Integer deleteEmptyMIOR();

    @Select("select * from repository")
    public List<com.mo.pojo.Repository> findAllRepository();

    @Select("select * from m_in_out_repository where bid=#{bid}")
    public MInOutRepository findOneMInOutRepositoryByBid(String bid);

    @Select("select m.*,s.name as supplier_name from material m,supplier s where m.id=#{id} and s.id=m.supplier_id")
    public Material findMaterialById(Integer id);

    @Select("select r.*,m.name as material_name,m.pid as material_id,e.name as create_name " +
            "from material_record r,material m,employee e " +
            "where r.iid like #{iid} and r.mid=m.id and e.id=r.create_by ")
    public List<MaterialRecord> findMaterialRecordByIid(String iid);

    public MInOutRepository findOneMInOutRepositoryByid(String id);

    public List<MInOutRepository> findMiorList(Map<String, Object> map);

    public Integer findMiorCount(Map<String, Object> map);

    public List<Material> viewAlertRM();

    public int insertMInOutRepository(MInOutRepository mInOutRepository);

    public int updateMInOutRepository(MInOutRepository mInOutRepository);

    public Integer findMaterialCountByNameAndSupplier(Map<String, Object> map);

    public List<Material> findMaterialListByNameAndSupplier(Map<String, Object> map);

}
