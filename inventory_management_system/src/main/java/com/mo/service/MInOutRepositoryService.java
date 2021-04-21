package com.mo.service;

import com.mo.pojo.*;

import java.util.List;
import java.util.Map;

public interface MInOutRepositoryService {

    /**
     * 添加 mInOutRepository
     *
     * @param mInOutRepository
     * @return
     */
    public int insertMInOutRepository(MInOutRepository mInOutRepository);

    /**
     * 修改 mInOutRepository
     *
     * @param mInOutRepository
     * @return
     */
    public int updateMInOutRepository(MInOutRepository mInOutRepository);

    /**
     * 修改 单据的状态 为退货
     *
     * @param id
     * @return
     */
    public Integer updateMIORStatus(Integer id, Integer modify);

    /**
     * 查询一条 mInOutRepository
     * 条件：bid
     *
     * @param bid
     * @return
     */
    public MInOutRepository findOneMInOutRepositoryByBid(String bid);

    /**
     * 查询一条  MInOutRepository
     * 条件 id
     *
     * @param id
     * @return
     */
    public MInOutRepository findOneMInOutRepositoryByid(String id);

    /**
     * 删除所选中的试题
     *
     * @param id
     * @return
     */
    public int deleteSelectMaterial(String bid, Integer id);

    /**
     * 修改单据中物料的数量
     *
     * @param mInOutRepository
     * @return
     */
    public int updateSelectMaterialQuantity(MInOutRepository mInOutRepository);

    /**
     * 查询所有的仓库
     *
     * @return
     */
    public List<Repository> findAllRepository();

    /**
     * 查询物料单据
     * 条件: bid、status、repository_id、天数
     *
     * @param map
     * @return
     */
    public List<MInOutRepository> findMiorList(Map<String, Object> map);

    /**
     * 查询 物料 单据 条数
     * 条件 bid、status、repository_id、天数
     *
     * @param map
     * @return
     */
    public Integer findMiorCount(Map<String, Object> map);

    /**
     * 查询 MaterialRecord
     * 条件，iid
     *
     * @param iid
     * @return
     */
    public List<MaterialRecord> findMaterialRecordByIid(String iid);

    /**
     * 查询一条 material
     * 条件：id
     *
     * @param id
     * @return
     */
    public Material findMaterialById(Integer id);

    /**
     * 入库的最终操作
     * 1：查询出相应的单据
     * 2：查询相应的物料单价，和供应商
     * 3：计算填充单据中物料对应的 unit_price，total_price、supplier_id
     * 4: 新建一条物料流水记录，并填充记录
     *
     * @param mInOutRepository
     * @return
     */
    public Boolean addMInOutRepository(MInOutRepository mInOutRepository);

    /**
     * 查询 material 符合条件的记录条数
     * 条件：supplier_id 。name,start
     *
     * @param map
     * @return
     */
    public Integer findMaterialCountByNameAndSupplier(Map<String, Object> map);

    /**
     * 查询 material
     * 条件：supplier_id 。name,start
     *
     * @param map
     * @return
     */
    public List<Material> findMaterialListByNameAndSupplier(Map<String, Object> map);

    /**
     * 查询 可用数量 <= 最低库存
     *
     * @return
     */
    public List<Material> viewAlertRM();
}
