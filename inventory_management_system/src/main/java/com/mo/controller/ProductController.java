package com.mo.controller;

import com.mo.pojo.*;
import com.mo.service.ProductService;
import com.mo.utils.MyToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ProductController {

    @Autowired
    private ProductService productService;

    /**
     * 转到增加 Clothing_Types 页面
     *
     * @return
     */
    @GetMapping("/addClothingTypes.html")
    public String toAddClothingTypes() {
        return "product/addClothingTypes";
    }

    /**
     * 增加一条 Clothing_Types
     *
     * @param clothingTypes
     * @return
     */
    @PostMapping("/addClothingTypes")
    public String addClothingTypes(ClothingTypes clothingTypes, HttpServletRequest request) {
        int flag = productService.insertClothingTypes(clothingTypes);
        if (flag == 1) return "success";
        request.setAttribute("msg", "添加失败!");
        return "error";
    }

    @GetMapping("/viewClothingTypes.html")
    public String viewClothingTypes(HttpServletRequest request) {
        List<ClothingTypes> clothingTypesList = productService.findAllClothingTypes();
        request.setAttribute("clothingTypesList", clothingTypesList);
        return "product/viewClothingTypes";
    }

    @GetMapping("/deleteClothingTypes")
    public String deleteClothingTypes(Integer id, HttpServletRequest request) {
        int flag = 0;
        if (id != null) flag = productService.deleteClothingTypes(id);
        if (flag == 1) return "success";
        request.setAttribute("msg", "删除失败!");
        return "error";
    }

    /**
     * 转到添加 product 页面
     *
     * @param request
     * @return
     */
    @GetMapping("/addProduct.html")
    public String toAddProduct(HttpServletRequest request) {
        List<ClothingTypes> clothingTypesList = productService.findAllClothingTypes();
        request.setAttribute("clothingTypesList", clothingTypesList);
        return "product/addProduct";
    }

    @PostMapping("/addProduct")
    public String addProduct(Product product, HttpServletRequest request) {
        int flag = 0;
        product.setPid(new MyToString().getMyToString());
        product.setAvailable_quantity(0);
        product.setFrozen_quantity(0);
        product.setRequisite_delivery_quantity(0);
        product.setTotal_quantity(0);
        //判空
        if (!product.isEmpty()) flag = productService.insertProduct(product);
        if (flag == 1) return "success";
        request.setAttribute("msg", "添加失败，请输入正确的数值");
        return "error";
    }

    @GetMapping("/manageProduct.html")
    public String tomManageProduct(String name, Integer clothing_types_id, Integer clothing_sex, Integer pageindex, HttpServletRequest request) {
        Page page = new Page(pageindex);
        // 查询所有服装类型
        List<ClothingTypes> clothingTypesList = productService.findAllClothingTypes();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", name);
        map.put("clothing_types_id", clothing_types_id);
        map.put("clothing_sex", clothing_sex);
        map.put("start", page.getSqlSelectPageStart());
        List<Product> productList = productService.findProductListByMap(map);
        page.setTotalCount(productService.findProductCountByMap(map));
        request.setAttribute("productList", productList);
        request.setAttribute("clothingTypesList", clothingTypesList);
        request.setAttribute("page", page);
        if (name != null && name != "") request.setAttribute("name", name);
        if (clothing_types_id != null) request.setAttribute("clothing_types_id", clothing_types_id);
        if (clothing_sex != null) request.setAttribute("clothing_sex", clothing_sex);
        return "product/manageProduct";
    }

    @GetMapping("/detailProduct.html")
    public String detailProduct(Integer id, HttpServletRequest request) {
        Product product = productService.findOneProductById(id);
        request.setAttribute("product", product);
        return "product/detailProduct";
    }

    @GetMapping("/updateProduct.html")
    public String toUpdateProduct(Integer id, HttpServletRequest request) {
        // 查询所有服装类型
        List<ClothingTypes> clothingTypesList = productService.findAllClothingTypes();
        Product product = productService.findOneProductById(id);
        request.setAttribute("product", product);
        request.setAttribute("clothingTypesList", clothingTypesList);
        return "product/updateProduct";
    }

    @PostMapping("/updateProduct")
    public String updateProduct(Product product, HttpServletRequest request) {
        int flag = 0;
        if (!product.isEmpty()) flag = productService.updateProduct(product);
        if (flag == 1) return "success";
        request.setAttribute("msg", "修改商品信息失败！");
        return "error";
    }

}
