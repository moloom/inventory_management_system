package com.mo.service.impl;

import com.mo.mapper.SupplierMapper;
import com.mo.pojo.MaterialRecord;
import com.mo.pojo.Supplier;
import com.mo.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class SupplierServiceImpl implements SupplierService {

    @Autowired
    private SupplierMapper supplierMapper;

    /**
     * 插入一条 supplier
     *
     * @param supplier
     * @return
     */
    @Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED, timeout = 15)
    @Override
    public int insertSupplier(Supplier supplier) {
        return supplierMapper.insertSupplier(supplier);
    }

    /**
     * 查找所有的 supplier
     *
     * @return
     */
    @Transactional(readOnly = true, timeout = 15)
    @Override
    public List<Supplier> findAllSupplier() {
        return supplierMapper.findAllSupplier();
    }

    /**
     * 删除一条供应商
     *
     * @param id
     * @return
     */
    @Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED, timeout = 15)
    @Override
    public int deleteSupplier(Integer id) {
        return supplierMapper.deleteSupplier(id);
    }

    /**
     * 修改一条供应商
     *
     * @param supplier
     * @return
     */
    @Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED, timeout = 15)
    @Override
    public int updateSupplier(Supplier supplier) {
        return supplierMapper.updateSupplier(supplier);
    }

    /**
     * 查询一条 supplier
     * 条件：id
     *
     * @param id
     * @return
     */
    @Transactional(readOnly = true, timeout = 15)
    @Override
    public Supplier findOneSupplierById(Integer id) {
        return supplierMapper.findOneSupplierById(id);
    }

    /**
     * 查询supplier 集合
     * 条件：name
     *
     * @param map
     * @return
     */
    @Transactional(readOnly = true, timeout = 15)
    @Override
    public List<Supplier> findSupplierListByName(Map<String, Object> map) {
        return supplierMapper.findSupplierListByName(map);
    }

    /**
     * 查询符合条件的记录数
     * 条件：name
     *
     * @param map
     * @return
     */
    @Transactional(readOnly = true, timeout = 15)
    @Override
    public Integer findSupplierCountByName(Map<String, Object> map) {
        return supplierMapper.findSupplierCountByName(map);
    }

    /**
     * 查询供应商的流水记录
     *
     * @param id
     * @return
     */
    @Transactional(readOnly = true, timeout = 15)
    @Override
    public List<MaterialRecord> findSupplierRecordByid(Integer id, Integer start) {
        return supplierMapper.findSupplierRecordByid(id, start);
    }

    /**
     * 查询 供应商 的记录条数
     *
     * @param id
     * @return
     */
    @Override
    public Integer findSupplierRecordCountByid(Integer id) {
        return supplierMapper.findSupplierRecordCountByid(id);
    }

}
