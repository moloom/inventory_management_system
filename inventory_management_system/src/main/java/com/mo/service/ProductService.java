package com.mo.service;

import com.mo.pojo.ClothingTypes;
import com.mo.pojo.Product;

import java.util.List;
import java.util.Map;

public interface ProductService {

    /**
     * 增加一条 clothingTypes
     *
     * @param clothingTypes
     * @return
     */
    public int insertClothingTypes(ClothingTypes clothingTypes);

    /**
     * 插入一条 product
     *
     * @param product
     * @return
     */
    public int insertProduct(Product product);

    /**
     * 删除一条 clothingTypes
     *
     * @param id
     * @return
     */
    public int deleteClothingTypes(Integer id);

    /**
     * 删除一条 Product
     *
     * @param id
     * @return
     */
    public int deleteProduct(Integer id);

    /**
     * 修改一条 product
     *
     * @param product
     * @return
     */
    public int updateProduct(Product product);

    //---------查询--------------------------------------

    /**
     * 查询所有的 ClothingTypes
     *
     * @return
     */
    public List<ClothingTypes> findAllClothingTypes();

    /**
     * 查询所有的 product
     *
     * @return
     */
    public List<Product> findAllProduct();

    /**
     * 查询一条 product
     * 条件：id
     *
     * @param id
     * @return
     */
    public Product findOneProductById(Integer id);


    /**
     * 查询 product
     * 条件：name、clothingTypes,color
     *
     * @param map
     * @return
     */
    public List<Product> findProductListByMap(Map<String, Object> map);

    /**
     * 查询符合条件的记录数，与上面 配套使用
     *
     * @param map
     * @return
     */
    public Integer findProductCountByMap(Map<String, Object> map);

    /**
     * 查下 物料的相关流水记录
     * 条件 物料id
     *
     * @param id
     * @return
     */
//    public List<MaterialRecord> detailMaterialRecord(Integer id, Integer start);

    /**
     * 查询 与物料有关的记录条数
     *
     * @param id
     * @return
     */
//    public Integer detailMaterialRecordCount(Integer id);
}
