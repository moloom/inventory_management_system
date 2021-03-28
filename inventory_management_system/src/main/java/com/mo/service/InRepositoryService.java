package com.mo.service;

import com.mo.pojo.InRepository;
import com.mo.pojo.Material;
import com.mo.pojo.Repository;

import java.util.List;
import java.util.Map;

public interface InRepositoryService {
    /**
     * 添加 InRepository
     *
     * @param inRepository
     * @return
     */
    public int insertInRepository(InRepository inRepository);

    /**
     * 修改InRepository
     *
     * @param inRepository
     * @return
     */
    public int updateInRepository(InRepository inRepository);

    /**
     * 查询一条 InRepository
     * 条件：bid
     *
     * @param bid
     * @return
     */
    public InRepository findOneInRepositoryByBid(String bid);

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
     * @param inRepository
     * @return
     */
    public int updateSelectMaterialQuantity(InRepository inRepository);

    /**
     * 查询所有的仓库
     *
     * @return
     */
    public List<Repository> findAllRepository();

    /**
     * 入库的最终操作
     * 1：查询出相应的单据
     * 2：查询相应的物料单价，和供应商
     * 3：计算填充单据中物料对应的 unit_price，total_price、supplier_id
     * 4: 新建一条物料流水记录，并填充记录
     *
     * @param inRepository
     * @return
     */
    public Boolean addInRepository(InRepository inRepository);

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

}
