package com.mo.service;

import com.mo.pojo.MaterialRecord;
import com.mo.pojo.Supplier;

import java.util.List;
import java.util.Map;

public interface SupplierService {


    /**
     * 插入一条 supplier
     *
     * @param supplier
     * @return
     */
    public int insertSupplier(Supplier supplier);

    /**
     * 查找所有的 supplier
     *
     * @return
     */
    public List<Supplier> findAllSupplier();

    /**
     * 删除一条供应商
     *
     * @param id
     * @return
     */
    public int deleteSupplier(Integer id);

    /**
     * 修改一条供应商
     *
     * @param supplier
     * @return
     */
    public int updateSupplier(Supplier supplier);

    /**
     * 查询一条 supplier
     * 条件：id
     *
     * @param id
     * @return
     */
    public Supplier findOneSupplierById(Integer id);

    /**
     * 查询supplier 集合
     * 条件：name
     *
     * @param map
     * @return
     */
    public List<Supplier> findSupplierListByName(Map<String, Object> map);

    /**
     * 查询符合条件的记录数
     * 条件：name
     *
     * @param map
     * @return
     */
    public Integer findSupplierCountByName(Map<String, Object> map);

    /**
     * 查询供应商的流失记录
     *
     * @param id
     * @param start
     * @return
     */
    public List<MaterialRecord> findSupplierRecordByid(Integer id, Integer start);

    /**
     * 查询 供应商 的记录条数
     *
     * @param id
     * @return
     */
    public Integer findSupplierRecordCountByid(Integer id);
}
