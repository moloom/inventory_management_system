package com.mo.service;

import com.mo.pojo.*;

import java.util.List;
import java.util.Map;

public interface PInOutRepositoryService {

    /**
     * 添加 pInOutRepository
     *
     * @param pInOutRepository
     * @return
     */
    public int insertPInOutRepository(PInOutRepository pInOutRepository);

    /**
     * 修改 pInOutRepository
     *
     * @param pInOutRepository
     * @return
     */
    public int updatePInOutRepository(PInOutRepository pInOutRepository);

    /**
     * 修改 单据的状态 为退货
     *
     * @param id
     * @return
     */
    public Integer updatePIORStatus(Integer id, Integer modify);

    /**
     * 查询一条 pInOutRepository
     * 条件：bid
     *
     * @param bid
     * @return
     */
    public PInOutRepository findOnePInOutRepositoryByBid(String bid);

    /**
     * 查询一条  PInOutRepository
     * 条件 id
     *
     * @param id
     * @return
     */
    public PInOutRepository findOnePInOutRepositoryByid(String id);

    /**
     * 删除所选中的 Product
     *
     * @param id
     * @return
     */
    public int deleteSelectProduct(String bid, Integer id);

    /**
     * 修改单据中 商品 的数量
     *
     * @param pInOutRepository
     * @return
     */
    public int updateSelectProductQuantity(PInOutRepository pInOutRepository);

    /**
     * 查询所有的仓库
     *
     * @return
     */
    public List<Repository> findAllRepository();

    /**
     * 查询所有的服装种类
     *
     * @return
     */
    public List<ClothingTypes> findAllClothingTypes();

    /**
     * 查询商品单据
     * 条件: bid、status、repository_id、天数
     *
     * @param map
     * @return
     */
    public List<PInOutRepository> findPiorList(Map<String, Object> map);

    /**
     * 查询商品单据条数
     * 条件: bid、status、repository_id、天数
     *
     * @param map
     * @return
     */
    public Integer findPiorCount(Map<String, Object> map);

    /**
     * 出入库的最终操作
     * 1：查询出相应的单据
     * 2：查询相应的 商品 单价，和供应商
     * 3：计算填充单据中商品对应的 unit_price，total_price
     * 4: 新建 商品 流水记录，并填充记录
     *
     * @param pInOutRepository
     * @return
     */
    public Boolean addPInOutRepository(PInOutRepository pInOutRepository);

    /**
     * 查询所有的 customer
     *
     * @return
     */
    public List<Customer> findAllCustomer();

    /**
     * 查询 customer 名字
     * 条件：id
     *
     * @param id
     * @return
     */
    public String findCustomerName(Integer id);

    /**
     * 查询 productRecord
     * 条件，pid
     *
     * @param bid
     * @return
     */
    public List<ProductRecord> findProductRecordByPid(String bid);

    /**
     * 查询一条 product
     * 条件：id
     *
     * @param id
     * @return
     */
    public Product findProductById(Integer id);

    /**
     * 查询 product 符合条件的记录条数
     * 条件： name,clothing_types_id
     *
     * @param map
     * @return
     */
    public Integer findProductCountByNameAndClothing_types_id(Map<String, Object> map);

    /**
     * 查询 product
     * 条件：name,clothing_types_id,start
     *
     * @param map
     * @return
     */
    public List<Product> findProductListByNameAndClothing_types_id(Map<String, Object> map);

    /**
     * 查询 可用数量 <= 最低库存
     *
     * @return
     */
    public List<Product> viewAlertRP();


}
