package com.mo.service.impl;

import com.mo.mapper.PInOutRepositoryMapper;
import com.mo.pojo.*;
import com.mo.service.PInOutRepositoryService;
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
public class PInOutRepositoryServiceImpl implements PInOutRepositoryService {

    @Autowired
    private PInOutRepositoryMapper pInOutRepositoryMapper;

    /**
     * 插入一条 pInOutRepository，bid必须唯一
     *
     * @param pInOutRepository
     * @return
     */
    @Override
    @Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED, timeout = 15)
    public int insertPInOutRepository(PInOutRepository pInOutRepository) {
        //bid唯一
        PInOutRepository pInOutRepository1 = pInOutRepositoryMapper.findOnePInOutRepositoryByBid(pInOutRepository.getBid());
        if (pInOutRepository1 != null) return 0;
        return pInOutRepositoryMapper.insertPInOutRepository(pInOutRepository);
    }

    /**
     * 修改 pInOutRepository
     *
     * @param pInOutRepository
     * @return
     */
    @Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED, timeout = 15)
    @Override
    public int updatePInOutRepository(PInOutRepository pInOutRepository) {
        return pInOutRepositoryMapper.updatePInOutRepository(pInOutRepository);
    }

    /**
     * 修改 单据的状态 为退货
     *
     * @param id
     * @return
     */
    @Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED, timeout = 15)
    @Override
    public Integer updatePIORStatus(Integer id, Integer modify) {
        return pInOutRepositoryMapper.updatePIORStatus(id, modify);
    }

    /**
     * 查询一条 pInOutRepository
     * 条件：bid
     *
     * @param bid
     * @return
     */
    @Override
    @Transactional(readOnly = true, timeout = 15)
    public PInOutRepository findOnePInOutRepositoryByBid(String bid) {
        return pInOutRepositoryMapper.findOnePInOutRepositoryByBid(bid);
    }

    /**
     * 查询一条  PInOutRepository
     * 条件 id
     *
     * @param id
     * @return
     */
    @Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED, timeout = 15)
    @Override
    public PInOutRepository findOnePInOutRepositoryByid(String id) {
        //顺带删除 无效的 PInOutRepository
        pInOutRepositoryMapper.deleteEmptyPIOR();
        return pInOutRepositoryMapper.findOnePInOutRepositoryByid(id);
    }

    /**
     * 删除所选中的 product
     *
     * @param bid
     * @param id
     * @return
     */
    @Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED, timeout = 15)
    @Override
    public int deleteSelectProduct(String bid, Integer id) {
        PInOutRepository pInOutRepository = pInOutRepositoryMapper.findOnePInOutRepositoryByBid(bid);
        //如果只选中了一个商品，直接修改 商品 id、数量、单价、 为null
        if (pInOutRepository.getProduct_id().length() == 1)
            return pInOutRepositoryMapper.updatePInOutRepositoryToNull(pInOutRepository.getId());
        //把要删除的id从单据表的 商品 id字符串中删除
        PInOutRepository pInOutRepository1 = OwnSubStringTool.eliminateStringP(pInOutRepository, id.toString());
        return pInOutRepositoryMapper.updatePInOutRepository(pInOutRepository1);
    }

    /**
     * 修改单据中商品的数量
     *
     * @param pInOutRepository
     * @return
     */
    @Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED, timeout = 15)
    @Override
    public int updateSelectProductQuantity(PInOutRepository pInOutRepository) {
        //1：查出相应的单据
        //2：在ProductId循环到相应的位置
        //3：获取索引，修改同样位置上的 unit_price，quantity
        PInOutRepository pInOutRepository2 = pInOutRepositoryMapper.findOnePInOutRepositoryByBid(pInOutRepository.getBid());
        List<String> quantity = MySubString.subString(pInOutRepository2.getQuantity(), ",");
        List<String> material_id = MySubString.subString(pInOutRepository2.getProduct_id(), ",");
        Iterator<String> ms = material_id.iterator();
        int index = -1;//索引
        while (ms.hasNext()) {
            String i = ms.next();
            ++index;
            if (i.equals(pInOutRepository.getProduct_id())) {
                quantity.remove(index);
                quantity.add(index, pInOutRepository.getQuantity());
                break;
            }
        }
        pInOutRepository.setQuantity(OwnSubStringTool.listToString(quantity));
        pInOutRepository.setProduct_id(null);
        return pInOutRepositoryMapper.updatePInOutRepository(pInOutRepository);
    }

    /**
     * 查询所有的仓库
     *
     * @return
     */
    @Override
    @Transactional(readOnly = true, timeout = 15)
    public List<Repository> findAllRepository() {
        return pInOutRepositoryMapper.findAllRepository();
    }

    /**
     * 查询所有的服装种类
     *
     * @return
     */
    @Transactional(readOnly = true, timeout = 15)
    @Override
    public List<ClothingTypes> findAllClothingTypes() {
        return pInOutRepositoryMapper.findAllClothingTypes();
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
    public List<PInOutRepository> findPiorList(Map<String, Object> map) {
        return pInOutRepositoryMapper.findPiorList(map);
    }

    /**
     * 查询商品单据条数
     * 条件: bid、status、repository_id、天数
     *
     * @param map
     * @return
     */
    @Transactional(readOnly = true, timeout = 15)
    @Override
    public Integer findPiorCount(Map<String, Object> map) {
        return pInOutRepositoryMapper.findPiorCount(map);
    }

    /**
     * 入库的最终操作
     * 1：查询出相应的单据
     * 2：查询相应的商品单价
     * 3：计算填充单据中商品对应的 unit_price，total_price
     * 4: 循环新建各个商品流水记录，并填充记录
     *
     * @param pInOutRepository
     * @return
     */
    @Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED, timeout = 15)
    @Override
    public Boolean addPInOutRepository(PInOutRepository pInOutRepository) {
        //查询出相应的单据
        PInOutRepository ir = pInOutRepositoryMapper.findOnePInOutRepositoryByBid(pInOutRepository.getBid());
        List<String> productIdList = MySubString.subString(ir.getProduct_id(), ",");
        List<String> quantityList = MySubString.subString(ir.getQuantity(), ",");
        List<String> unitPriceList = new ArrayList<String>();
        Iterator<String> qtm = quantityList.iterator();
        BigDecimal totalPrice = new BigDecimal(0.00);
        int index = -1;
        boolean flagOfIR = true;
        boolean flagOfProduct = true;
        while (qtm.hasNext()) {
            ++index;
            BigDecimal q = new BigDecimal(Double.valueOf(qtm.next()));
            //查询相应的商品单价
            Product product = pInOutRepositoryMapper.findProductById(Integer.valueOf(productIdList.get(index)));
            unitPriceList.add(product.getUnit_price().toString());
            totalPrice = totalPrice.add(product.getUnit_price().multiply(q));

            //如果是出库，数量取负数
            if (pInOutRepository.getStatus() != 1) q = q.multiply(new BigDecimal(-1));
            //增加流水记录，
            ProductRecord productRecord = new ProductRecord();
            productRecord.setCreate_by(pInOutRepository.getModify_by());
            productRecord.setIid(ir.getBid());
            productRecord.setPid(product.getId());
            productRecord.setQuantity(Integer.valueOf(q.toString()));
            productRecord.setStatus(pInOutRepository.getStatus());
            int f = pInOutRepositoryMapper.insertProductRecord(productRecord);
            if (f != 1) flagOfIR = false;

            //修改，相应商品的数量
            product.setTotal_quantity(product.getTotal_quantity() + Integer.valueOf(q.toString()));
            product.setAvailable_quantity(product.getAvailable_quantity() + Integer.valueOf(q.toString()));
            product.setModify_by(pInOutRepository.getModify_by());
            int fm = pInOutRepositoryMapper.updateProductQuantityOfTotalAndAvailable(product);
            if (fm != 1) flagOfProduct = false;
        }
        pInOutRepository.setBid(ir.getBid());
        pInOutRepository.setUnit_price(OwnSubStringTool.listToString(unitPriceList));
        pInOutRepository.setTotal_price(totalPrice);
        System.out.println(pInOutRepository);
        int flag = pInOutRepositoryMapper.updatePInOutRepository(pInOutRepository);
        if (flag == 1 && flagOfIR && flagOfProduct) return true;
        return null;
    }

    /**
     * 查询所有的 customer
     *
     * @return
     */
    @Transactional(readOnly = true, timeout = 15)
    @Override
    public List<Customer> findAllCustomer() {
        return pInOutRepositoryMapper.findAllCustomer();
    }

    /**
     * 查询 customer 名字
     * 条件：id
     *
     * @param id
     * @return
     */
    @Transactional(readOnly = true, timeout = 15)
    @Override
    public String findCustomerName(Integer id) {
        return pInOutRepositoryMapper.findCustomerName(id);
    }

    /**
     * 查询 productRecord list
     * 条件，bid
     *
     * @param bid
     * @return
     */
    @Transactional(readOnly = true, timeout = 15)
    @Override
    public List<ProductRecord> findProductRecordByPid(String bid) {
        return pInOutRepositoryMapper.findProductRecordByPid(bid);
    }

    /**
     * 查询一条 product
     * 条件：id
     *
     * @param id
     * @return
     */
    @Transactional(readOnly = true, timeout = 15)
    @Override
    public Product findProductById(Integer id) {
        return pInOutRepositoryMapper.findProductById(id);
    }

    /**
     * 查询 product 符合条件的记录条数
     * 条件： name,clothing_types_id，start
     *
     * @param map
     * @return
     */
    @Transactional(readOnly = true, timeout = 15)
    @Override
    public Integer findProductCountByNameAndClothing_types_id(Map<String, Object> map) {
        return pInOutRepositoryMapper.findProductCountByNameAndClothing_types_id(map);
    }

    /**
     * 查询 product 符合条件的记录条数
     * 条件： name,clothing_types_id，start
     *
     * @param map
     * @return
     */
    @Transactional(readOnly = true, timeout = 15)
    @Override
    public List<Product> findProductListByNameAndClothing_types_id(Map<String, Object> map) {
        return pInOutRepositoryMapper.findProductListByNameAndClothing_types_id(map);
    }

    /**
     * 查询 可用数量 <= 最低库存
     *
     * @return
     */
    @Transactional(readOnly = true, timeout = 15)
    @Override
    public List<Product> viewAlertRP() {
        return pInOutRepositoryMapper.viewAlertRP();
    }

}
