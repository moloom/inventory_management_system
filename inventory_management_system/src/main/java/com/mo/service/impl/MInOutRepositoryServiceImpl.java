package com.mo.service.impl;

import com.mo.mapper.MInOutRepositoryMapper;
import com.mo.pojo.*;
import com.mo.service.MInOutRepositoryService;
import com.mo.utils.MySubString;
import com.mo.utils.OwnSubStringTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Service
public class MInOutRepositoryServiceImpl implements MInOutRepositoryService {

    @Autowired
    private MInOutRepositoryMapper mInOutRepositoryMapper;

    /**
     * 插入一条 mInOutRepository，bid必须唯一
     *
     * @param mInOutRepository
     * @return
     */
    @Override
    @Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED, timeout = 15)
    public int insertMInOutRepository(MInOutRepository mInOutRepository) {
        //bid唯一
        MInOutRepository mInOutRepository1 = mInOutRepositoryMapper.findOneMInOutRepositoryByBid(mInOutRepository.getBid());
        if (mInOutRepository1 != null) return 0;
        return mInOutRepositoryMapper.insertMInOutRepository(mInOutRepository);
    }

    /**
     * 修改 mInOutRepository
     *
     * @param mInOutRepository
     * @return
     */
    @Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED, timeout = 15)
    @Override
    public int updateMInOutRepository(MInOutRepository mInOutRepository) {
        return mInOutRepositoryMapper.updateMInOutRepository(mInOutRepository);
    }

    /**
     * 修改 单据的状态 为退货
     *
     * @param id
     * @param modify
     * @return
     */
    @Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED, timeout = 15)
    @Override
    public Integer updateMIORStatus(Integer id, Integer modify) {
        return mInOutRepositoryMapper.updateMIORStatus(id, modify);
    }

    /**
     * 查询一条 MInOutRepository
     * 条件：bid
     *
     * @param bid
     * @return
     */
    @Override
    @Transactional(readOnly = true, timeout = 15)
    public MInOutRepository findOneMInOutRepositoryByBid(String bid) {
        return mInOutRepositoryMapper.findOneMInOutRepositoryByBid(bid);
    }

    /**
     * 查询一条  MInOutRepository
     * 条件 id
     *
     * @param id
     * @return
     */
    @Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED, timeout = 15)
    @Override
    public MInOutRepository findOneMInOutRepositoryByid(String id) {
        //顺带删除 无效的 MInOutRepository
        mInOutRepositoryMapper.deleteEmptyMIOR();
        return mInOutRepositoryMapper.findOneMInOutRepositoryByid(id);
    }

    /**
     * 删除所选中的物料
     *
     * @param bid
     * @param id
     * @return
     */
    @Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED, timeout = 15)
    @Override
    public int deleteSelectMaterial(String bid, Integer id) {
        MInOutRepository mInOutRepository = mInOutRepositoryMapper.findOneMInOutRepositoryByBid(bid);
        //如果只选中了一个物料，直接修改物料id、数量、单价、供应商 为null
        if (mInOutRepository.getMaterial_id().length() == 1)
            return mInOutRepositoryMapper.updateMInOutRepositoryToNull(mInOutRepository.getId());
        //把要删除的id从单据表的物料id字符串中删除
        MInOutRepository mInOutRepository1 = OwnSubStringTool.eliminateString(mInOutRepository, id.toString());
        return mInOutRepositoryMapper.updateMInOutRepository(mInOutRepository1);
    }

    /**
     * 修改单据中物料的数量
     *
     * @param mInOutRepository
     * @return
     */
    @Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED, timeout = 15)
    @Override
    public int updateSelectMaterialQuantity(MInOutRepository mInOutRepository) {
        //1：查出相应的单据
        //2：在MaterialId循环到相应的位置
        //3：获取索引，修改同样位置上的 unit_price，quantity、supplier_id
        MInOutRepository mInOutRepository2 = mInOutRepositoryMapper.findOneMInOutRepositoryByBid(mInOutRepository.getBid());
        List<String> quantity = MySubString.subString(mInOutRepository2.getQuantity(), ",");
        List<String> material_id = MySubString.subString(mInOutRepository2.getMaterial_id(), ",");
        Iterator<String> ms = material_id.iterator();
        int index = -1;//索引
        while (ms.hasNext()) {
            String i = ms.next();
            ++index;
            if (i.equals(mInOutRepository.getMaterial_id())) {
                quantity.remove(index);
                quantity.add(index, mInOutRepository.getQuantity());
                break;
            }
        }
        mInOutRepository.setQuantity(OwnSubStringTool.listToString(quantity));
        mInOutRepository.setMaterial_id(null);
        return mInOutRepositoryMapper.updateMInOutRepository(mInOutRepository);
    }

    /**
     * 查询所有的仓库
     *
     * @return
     */
    @Override
    @Transactional(readOnly = true, timeout = 15)
    public List<Repository> findAllRepository() {
        return mInOutRepositoryMapper.findAllRepository();
    }

    /**
     * 查询商品单据
     * 条件: bid、status、repository_id、天数
     *
     * @param map
     * @return
     */
    @Transactional(readOnly = true, timeout = 15)
    @Override
    public List<MInOutRepository> findMiorList(Map<String, Object> map) {
        return mInOutRepositoryMapper.findMiorList(map);
    }

    /**
     * 查询 物料 单据 条数
     * 条件 bid、status、repository_id、天数
     *
     * @param map
     * @return
     */
    @Override
    public Integer findMiorCount(Map<String, Object> map) {
        return mInOutRepositoryMapper.findMiorCount(map);
    }

    /**
     * 查询 MaterialRecord
     * 条件，iid
     *
     * @param iid
     * @return
     */
    @Override
    public List<MaterialRecord> findMaterialRecordByIid(String iid) {
        return mInOutRepositoryMapper.findMaterialRecordByIid(iid);
    }

    /**
     * 查询一条 material
     * 条件：id
     *
     * @param id
     * @return
     */
    @Transactional(readOnly = true, timeout = 15)
    @Override
    public Material findMaterialById(Integer id) {
        return mInOutRepositoryMapper.findMaterialById(id);
    }

    /**
     * 入库的最终操作
     * 1：查询出相应的单据
     * 2：查询相应的物料单价，和供应商
     * 3：计算填充单据中物料对应的 unit_price，total_price、supplier_id
     * 4: 循环新建各个物料流水记录，并填充记录
     *
     * @param mInOutRepository
     * @return
     */
    @Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED, timeout = 15)
    @Override
    public Boolean addMInOutRepository(MInOutRepository mInOutRepository) {
        //查询出相应的单据
        MInOutRepository ir = mInOutRepositoryMapper.findOneMInOutRepositoryByBid(mInOutRepository.getBid());
        List<String> materialIdList = MySubString.subString(ir.getMaterial_id(), ",");
        List<String> quantityList = MySubString.subString(ir.getQuantity(), ",");
        List<String> unitPriceList = new ArrayList<String>();
        List<String> supplierIdList = new ArrayList<String>();
        Iterator<String> qtm = quantityList.iterator();
        BigDecimal totalPrice = new BigDecimal(0.00);
        int index = -1;
        boolean flagOfIR = true;
        boolean flagOfMaterial = true;
        while (qtm.hasNext()) {
            ++index;
            BigDecimal q = new BigDecimal(Double.valueOf(qtm.next()));
            //查询相应的物料单价，和供应商
            Material material = mInOutRepositoryMapper.findMaterialById(Integer.valueOf(materialIdList.get(index)));
            unitPriceList.add(material.getUnit_price().toString());
            supplierIdList.add(material.getSupplier_id().toString());
            totalPrice = totalPrice.add(material.getUnit_price().multiply(q));

            //如果是出库，数量取负数
            if (mInOutRepository.getStatus() != 1) q = q.multiply(new BigDecimal(-1));
            //增加流水记录，
            MaterialRecord materialRecord = new MaterialRecord();
            materialRecord.setCreate_by(mInOutRepository.getModify_by());
            materialRecord.setIid(ir.getBid());
            materialRecord.setMid(material.getId());
            materialRecord.setQuantity(Integer.valueOf(q.toString()));
            materialRecord.setStatus(mInOutRepository.getStatus());
            int f = mInOutRepositoryMapper.insertMaterialRecord(materialRecord);
            if (f != 1) flagOfIR = false;

            //修改，相应物料的数量
            material.setTotal_quantity(material.getTotal_quantity() + Integer.valueOf(q.toString()));
            material.setAvailable_quantity(material.getAvailable_quantity() + Integer.valueOf(q.toString()));
            material.setModify_by(mInOutRepository.getModify_by());
            int fm = mInOutRepositoryMapper.updateMaterialQuantityOfTotalAndAvailable(material);
            if (fm != 1) flagOfMaterial = false;
        }
        mInOutRepository.setBid(ir.getBid());
        mInOutRepository.setUnit_price(OwnSubStringTool.listToString(unitPriceList));
        mInOutRepository.setSupplier_id(OwnSubStringTool.listToString(supplierIdList));
        mInOutRepository.setTotal_price(totalPrice);
        System.out.println(mInOutRepository);
        int flag = mInOutRepositoryMapper.updateMInOutRepository(mInOutRepository);
        if (flag == 1 && flagOfIR && flagOfMaterial) return true;
        return null;
    }

    /**
     * 查询 material 符合条件的记录条数
     * 条件：supplier_id 。name,start
     *
     * @param map
     * @return
     */
    @Transactional(readOnly = true, timeout = 15)
    @Override
    public Integer findMaterialCountByNameAndSupplier(Map<String, Object> map) {
        return mInOutRepositoryMapper.findMaterialCountByNameAndSupplier(map);
    }

    /**
     * 查询 material
     * 条件：supplier_id 。name,start
     *
     * @param map
     * @return
     */
    @Transactional(readOnly = true, timeout = 15)
    @Override
    public List<Material> findMaterialListByNameAndSupplier(Map<String, Object> map) {
        return mInOutRepositoryMapper.findMaterialListByNameAndSupplier(map);
    }

    /**
     * 查询 可用数量 <= 最低库存
     *
     * @return
     */
    @Override
    public List<Material> viewAlertRM() {
        return mInOutRepositoryMapper.viewAlertRM();
    }

}
