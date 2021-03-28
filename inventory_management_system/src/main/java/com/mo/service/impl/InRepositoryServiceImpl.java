package com.mo.service.impl;

import com.mo.mapper.InRepositoryMapper;
import com.mo.pojo.InRepository;
import com.mo.pojo.Material;
import com.mo.pojo.MaterialRecord;
import com.mo.pojo.Repository;
import com.mo.service.InRepositoryService;
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
public class InRepositoryServiceImpl implements InRepositoryService {

    @Autowired
    private InRepositoryMapper inRepositoryMapper;

    /**
     * 插入一条 inRepository，bid必须唯一
     *
     * @param inRepository
     * @return
     */
    @Override
    @Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED, timeout = 15)
    public int insertInRepository(InRepository inRepository) {
        //bid唯一
        InRepository inRepository1 = inRepositoryMapper.findOneInRepositoryByBid(inRepository.getBid());
        if (inRepository1 != null) return 0;
        return inRepositoryMapper.insertInRepository(inRepository);
    }

    /**
     * 修改InRepository
     *
     * @param inRepository
     * @return
     */
    @Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED, timeout = 15)
    @Override
    public int updateInRepository(InRepository inRepository) {
        return inRepositoryMapper.updateInRepository(inRepository);
    }

    /**
     * 查询一条 InRepository
     * 条件：bid
     *
     * @param bid
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public InRepository findOneInRepositoryByBid(String bid) {
        return inRepositoryMapper.findOneInRepositoryByBid(bid);
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
        InRepository inRepository = inRepositoryMapper.findOneInRepositoryByBid(bid);
        //如果只选中了一个物料，直接修改物料id、数量、单价、供应商 为null
        if (inRepository.getMaterial_id().length() == 1)
            return inRepositoryMapper.updateInRepositoryToNull(inRepository.getId());
        //把要删除的id从单据表的物料id字符串中删除
        InRepository inRepository1 = OwnSubStringTool.eliminateString(inRepository, id.toString());
        return inRepositoryMapper.updateInRepository(inRepository1);
    }

    /**
     * 修改单据中物料的数量
     *
     * @param inRepository
     * @return
     */
    @Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED, timeout = 15)
    @Override
    public int updateSelectMaterialQuantity(InRepository inRepository) {
        //1：查出相应的单据
        //2：在MaterialId循环到相应的位置
        //3：获取索引，修改同样位置上的 unit_price，quantity、supplier_id
        InRepository inRepository2 = inRepositoryMapper.findOneInRepositoryByBid(inRepository.getBid());
        List<String> quantity = MySubString.subString(inRepository2.getQuantity(), ",");
        List<String> material_id = MySubString.subString(inRepository2.getMaterial_id(), ",");
        Iterator<String> ms = material_id.iterator();
        int index = -1;//索引
        while (ms.hasNext()) {
            String i = ms.next();
            ++index;
            if (i.equals(inRepository.getMaterial_id())) {
                quantity.remove(index);
                quantity.add(index, inRepository.getQuantity());
                break;
            }
        }
        inRepository.setQuantity(OwnSubStringTool.listToString(quantity));
        inRepository.setMaterial_id(null);
        return inRepositoryMapper.updateInRepository(inRepository);
    }

    /**
     * 查询所有的仓库
     *
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public List<Repository> findAllRepository() {
        return inRepositoryMapper.findAllRepository();
    }

    /**
     * 入库的最终操作
     * 1：查询出相应的单据
     * 2：查询相应的物料单价，和供应商
     * 3：计算填充单据中物料对应的 unit_price，total_price、supplier_id
     * 4: 循环新建各个物料流水记录，并填充记录
     *
     * @param inRepository
     * @return
     */
    @Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED, timeout = 15)
    @Override
    public Boolean addInRepository(InRepository inRepository) {
        //查询出相应的单据
        InRepository ir = inRepositoryMapper.findOneInRepositoryByBid(inRepository.getBid());
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
            Material material = inRepositoryMapper.findMaterialById(Integer.valueOf(materialIdList.get(index)));
            unitPriceList.add(material.getUnit_price().toString());
            supplierIdList.add(material.getSupplier_id().toString());
            totalPrice = totalPrice.add(material.getUnit_price().multiply(q));
            //如果入库成功，新建一条流水记录，并填充数据，
            MaterialRecord materialRecord = new MaterialRecord();
            materialRecord.setCreate_by(inRepository.getModify_by());
            materialRecord.setIid(ir.getBid());
            materialRecord.setMid(material.getId());
            materialRecord.setQuantity(Integer.valueOf(q.toString()));
            materialRecord.setStatus(1);//默认为1。  1：正常入库，2退货
            int f = inRepositoryMapper.insertMaterialRecord(materialRecord);
            if (f != 1) flagOfIR = false;

            //修改，相应物料的数量
            material.setTotal_quantity(Integer.valueOf(q.toString()));
            material.setAvailable_quantity(Integer.valueOf(q.toString()));
            material.setModify_by(inRepository.getModify_by());
            int fm = inRepositoryMapper.updateMaterialQuantityOfTotalAndAvailable(material);
            if (fm != 1) flagOfMaterial = false;
        }
        ir.setUnit_price(OwnSubStringTool.listToString(unitPriceList));
        ir.setSupplier_id(OwnSubStringTool.listToString(supplierIdList));
        ir.setModify_by(inRepository.getModify_by());
        ir.setDeliver_date(inRepository.getDeliver_date());
        ir.setRepository_id(inRepository.getRepository_id());
        ir.setTotal_price(totalPrice);
        ir.setStatus(1);//1代表正常入库
        int flag = inRepositoryMapper.updateInRepository(ir);
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
    @Transactional(readOnly = true)
    @Override
    public Integer findMaterialCountByNameAndSupplier(Map<String, Object> map) {
        return inRepositoryMapper.findMaterialCountByNameAndSupplier(map);
    }

    /**
     * 查询 material
     * 条件：supplier_id 。name,start
     *
     * @param map
     * @return
     */
    @Transactional(readOnly = true)
    @Override
    public List<Material> findMaterialListByNameAndSupplier(Map<String, Object> map) {
        return inRepositoryMapper.findMaterialListByNameAndSupplier(map);
    }

}
