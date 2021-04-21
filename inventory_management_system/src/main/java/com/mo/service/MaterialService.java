package com.mo.service;

import com.mo.pojo.Material;
import com.mo.pojo.MaterialRecord;
import com.mo.pojo.Supplier;

import java.util.List;
import java.util.Map;

public interface MaterialService {

    /**
     * 查询所有的供应商
     *
     * @return
     */
    public List<Supplier> findAllSupplier();

    /**
     * 插入一条供应商数据
     *
     * @param material
     * @return
     */
    public int insertMaterial(Material material);

    /**
     * 删除一条 material
     *
     * @param id
     * @return
     */
    public Integer deleteMaterial(Integer id);

    /**
     * 查询 Material
     * 条件：name、supplier_id
     *
     * @param map
     * @return
     */
    public List<Material> findMaterialListByNameAndSupplier(Map<String, Object> map);

    /**
     * 查询符合条件的记录数
     *
     * @param map
     * @return
     */
    public Integer findMaterialCountByNameAndSupplier(Map<String, Object> map);

    /**
     * 查询一条 Material记录
     * 条件：pid
     *
     * @param pid
     * @return
     */
    public Material findOneMaterialByPid(String pid);

    /**
     * 查询所有的material
     *
     * @return
     */
    public List<Material> findAllMaterial();

    /**
     * 修改物料
     *
     * @param material
     * @return
     */
    public int updateMaterial(Material material);

    /**
     * 查下 物料的相关流水记录
     * 条件 物料id
     *
     * @param id
     * @return
     */
    public List<MaterialRecord> detailMaterialRecord(Integer id, Integer start);

    /**
     * 查询 与物料有关的记录条数
     *
     * @param id
     * @return
     */
    public Integer detailMaterialRecordCount(Integer id);
}
