package com.mo.service.impl;

import com.mo.mapper.MaterialMapper;
import com.mo.pojo.Material;
import com.mo.pojo.MaterialRecord;
import com.mo.pojo.Supplier;
import com.mo.service.MaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class MaterialServiceImpl implements MaterialService {

    @Autowired
    private MaterialMapper materialMapper;

    /**
     * 查询所有的供应商
     *
     * @return
     */
    @Override
    @Transactional(readOnly = true, timeout = 15)
    public List<Supplier> findAllSupplier() {
        return materialMapper.findAllSupplier();
    }

    /**
     * 插入一条供应商数据
     *
     * @param material
     * @return
     */
    @Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED, timeout = 15)
    @Override
    public int insertMaterial(Material material) {
        //查询pid是否已存在
        if (materialMapper.findOneMaterialByPid(material.getPid()) == null)
            return materialMapper.insertMaterial(material);
        else return 0;
    }

    /**
     * 查询 Material
     * 条件：name、supplier_id
     *
     * @param map
     * @return
     */
    @Transactional(readOnly = true, timeout = 15)
    @Override
    public List<Material> findMaterialListByNameAndSupplier(Map<String, Object> map) {
        return materialMapper.findMaterialListByNameAndSupplier(map);
    }

    /**
     * 查询符合条件的记录数
     *
     * @param map
     * @return
     */
    @Transactional(readOnly = true, timeout = 15)
    @Override
    public Integer findMaterialCountByNameAndSupplier(Map<String, Object> map) {
        return materialMapper.findMaterialCountByNameAndSupplier(map);
    }

    /**
     * 查询一条 material
     * 条件：pid
     *
     * @param pid
     * @return
     */
    @Transactional(readOnly = true, timeout = 15)
    @Override
    public Material findOneMaterialByPid(String pid) {
        return materialMapper.findOneMaterialByPid(pid);
    }

    /**
     * 查询所有的material
     *
     * @return
     */
    @Transactional(readOnly = true, timeout = 15)
    @Override
    public List<Material> findAllMaterial() {
        return materialMapper.findAllMaterial();
    }

    /**
     * 修改物料
     *
     * @param material
     * @return
     */
    @Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED, timeout = 15)
    @Override
    public int updateMaterial(Material material) {
        return materialMapper.updateMaterial(material);
    }

    /**
     * 查下 物料的相关流水记录
     * 条件 物料id
     *
     * @param id
     * @return
     */
    @Transactional(readOnly = true, timeout = 15)
    @Override
    public List<MaterialRecord> detailMaterialRecord(Integer id, Integer start) {
        return materialMapper.detailMaterialRecord(id, start);
    }

    /**
     * 查询 与物料有关的记录条数
     *
     * @param id
     * @return
     */
    @Transactional(readOnly = true, timeout = 15)
    @Override
    public Integer detailMaterialRecordCount(Integer id) {
        return materialMapper.detailMaterialRecordCount(id);
    }


}
