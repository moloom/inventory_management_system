package com.mo.controller;

import com.alibaba.fastjson.JSONArray;
import com.mo.pojo.*;
import com.mo.service.MaterialService;
import com.mo.utils.MyToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class MaterialController {

    @Autowired
    private MaterialService materialService;

    @GetMapping("/addMaterial.html")
    public String toAddMaterial(String bid, HttpServletRequest request) {
        List<Supplier> supplierList = materialService.findAllSupplier();
        request.setAttribute("supplierList", supplierList);
        if (bid != null && "".equals(bid)) request.setAttribute("pid", bid);
        return "material/addMaterial";
    }

    @GetMapping("/manageMaterial.html")
    public String toManageMaterial(HttpServletRequest request) {
        Page page = new Page();
        //查询供应商list
        List<Supplier> supplierList = materialService.findAllSupplier();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", null);
        map.put("supplier_id", null);
        map.put("start", 0);
        page.setCurrentPageNo(1);
        List<Material> materialList = materialService.findMaterialListByNameAndSupplier(map);
        page.setTotalCount(materialService.findMaterialCountByNameAndSupplier(map));
        request.setAttribute("materialList", materialList);
        request.setAttribute("supplierList", supplierList);
        request.setAttribute("page", page);
        return "material/manageMaterial";
    }

    @GetMapping("/manageMaterialSearch")
    public String manageMaterialSearch(String name, String supplier_id, Integer pageindex, HttpServletRequest request) {
        Page page = new Page();
        List<Material> materialList = null;
        //查询供应商list
        List<Supplier> supplierList = materialService.findAllSupplier();
        Map<String, Object> map = new HashMap<String, Object>();
        //根据前端传过来的页数，计算sql中的start变量的值
        if (pageindex == null || pageindex == 0) {
            pageindex = 0;
            page.setCurrentPageNo(1);
        } else {
            page.setCurrentPageNo(pageindex + 1);
            pageindex *= 10;
        }
        //防止无数据时查询无结果
        if (supplier_id != null && supplier_id.equals("0")) supplier_id = null;
        map.put("name", name);
        map.put("supplier_id", supplier_id);
        map.put("start", pageindex);
        materialList = materialService.findMaterialListByNameAndSupplier(map);
        page.setTotalCount(materialService.findMaterialCountByNameAndSupplier(map));
        request.setAttribute("materialList", materialList);
        request.setAttribute("supplierList", supplierList);
        request.setAttribute("page", page);
        if (name != null && name != "") request.setAttribute("name", name);
        if (supplier_id != null && supplier_id != "") request.setAttribute("supplier_id", supplier_id);
        return "material/manageMaterial";
    }

    @GetMapping("/detailMaterial.html")
    public String detailMaterial(String pid, HttpServletRequest request) {
        Material material = null;
        if (pid != null && pid != "") material = materialService.findOneMaterialByPid(pid);
        request.setAttribute("material", material);
        return "material/detailMaterial";
    }

    @GetMapping("/updateMaterial.html")
    public String toUpdateMaterial(String pid, HttpServletRequest request) {
        Material material = materialService.findOneMaterialByPid(pid);
        List<Supplier> supplierList = materialService.findAllSupplier();
        request.setAttribute("supplierList", supplierList);
        request.setAttribute("material", material);
        return "material/updateMaterial";
    }

    @PostMapping("/updateMaterial")
    public String updateMaterial(Material material) {
        int flag = materialService.updateMaterial(material);
        if (flag == 1) return "success";
        return "error";
    }

    /**
     * 查询 物料的流水记录
     *
     * @param id
     * @return
     */
    @GetMapping("/detailMaterialRecord.html")
    public String detailMaterialRecord(Integer id, Integer pageindex, HttpServletRequest request) {
        Page page = new Page(pageindex);
        page.setTotalCount(materialService.detailMaterialRecordCount(id));
        List<MaterialRecord> materialRecordList = materialService.detailMaterialRecord(id, page.getSqlSelectPageStart());
        request.setAttribute("materialRecordList", materialRecordList);
        request.setAttribute("page", page);
        return "material/detailMaterialRecord";
    }

    /*----------------------------------ajax请求-----------------*/
    @PostMapping("/addMaterial")
    @ResponseBody
    public String addMaterial(String bid, Material material) {
        MyToString myToString = new MyToString();
        material.setPid(myToString.getMyToString());
        if (material.getColor() == null || material.getColor().equals("")) material.setColor("-");
        //设置默认值为0
        material.setAvailable_quantity(0);
        material.setTotal_quantity(0);
        material.setFrozen_quantity(0);
        material.setRequisite_delivery_quantity(0);
        int flag = materialService.insertMaterial(material);
        if (flag == 1) {
            if (bid != null && "".equals(bid)) return JSONArray.toJSONString("/inRepository.html");
            return JSONArray.toJSONString("/success.html");
        }
        return JSONArray.toJSONString("/error");
    }
}

