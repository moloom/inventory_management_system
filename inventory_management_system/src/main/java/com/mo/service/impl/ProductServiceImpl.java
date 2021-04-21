package com.mo.service.impl;

import com.mo.mapper.ProductMapper;
import com.mo.pojo.ClothingTypes;
import com.mo.pojo.Product;
import com.mo.pojo.ProductRecord;
import com.mo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper productMapper;

    /**
     * 增加一条 clothingTypes
     *
     * @param clothingTypes
     * @return
     */
    @Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED, timeout = 15)
    @Override
    public int insertClothingTypes(ClothingTypes clothingTypes) {
        return productMapper.insertClothingTypes(clothingTypes);
    }

    /**
     * 插入一条 product
     *
     * @param product
     * @return
     */
    @Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED, timeout = 15)
    @Override
    public int insertProduct(Product product) {
        return productMapper.insertProduct(product);
    }

    /**
     * 删除一条 clothingTypes
     *
     * @param id
     * @return
     */
    @Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED, timeout = 15)
    @Override
    public int deleteClothingTypes(Integer id) {
        return productMapper.deleteClothingTypes(id);
    }

    /**
     * 删除一条 Product
     *
     * @param id
     * @return
     */
    @Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED, timeout = 15)
    @Override
    public int deleteProduct(Integer id) {
        return productMapper.deleteProduct(id);
    }

    /**
     * 修改一条 product
     *
     * @param product
     * @return
     */
    @Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED, timeout = 15)
    @Override
    public int updateProduct(Product product) {
        return productMapper.updateProduct(product);
    }

    /**
     * 查询所有的 ClothingTypes
     *
     * @return
     */
    @Transactional(timeout = 15, readOnly = true)
    @Override
    public List<ClothingTypes> findAllClothingTypes() {
        return productMapper.findAllClothingTypes();
    }

    /**
     * 查询所有的 product
     *
     * @return
     */
    @Transactional(timeout = 15, readOnly = true)
    @Override
    public List<Product> findAllProduct() {
        return productMapper.findAllProduct();
    }

    /**
     * 查询一条 product
     * 条件：id
     *
     * @param id
     * @return
     */
    @Transactional(timeout = 15, readOnly = true)
    @Override
    public Product findOneProductById(Integer id) {
        return productMapper.findOneProductById(id);
    }

    /**
     * 查询 product
     * 条件：name、clothingTypes,color
     *
     * @param map
     * @return
     */
    @Transactional(timeout = 15, readOnly = true)
    @Override
    public List<Product> findProductListByMap(Map<String, Object> map) {
        return productMapper.findProductListByMap(map);
    }

    /**
     * 查询符合条件的记录数，与上面 配套使用
     *
     * @param map
     * @return
     */
    @Transactional(timeout = 15, readOnly = true)
    @Override
    public Integer findProductCountByMap(Map<String, Object> map) {
        return productMapper.findProductCountByMap(map);
    }

    /**
     * 查询 商品对应的所有流水记录
     *
     * @param id
     * @return
     */
    @Transactional(timeout = 15, readOnly = true)
    @Override
    public List<ProductRecord> findProductRecordByProductId(Integer id) {
        return productMapper.findProductRecordByProductId(id);
    }

    /**
     * 查询商品队友的流水记录条数
     *
     * @param id
     * @return
     */
    @Override
    public Integer findProductRecordCountByProductId(Integer id) {
        return productMapper.findProductRecordCountByProductId(id);
    }
}
