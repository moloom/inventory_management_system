package com.mo.controller;

import com.mo.pojo.MaterialRecord;
import com.mo.pojo.Page;
import com.mo.pojo.Supplier;
import com.mo.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class SupplierController {

    @Autowired
    private SupplierService supplierService;

    @GetMapping("/addSupplier.html")
    public String toAddSupplier() {
        return "supplier/addSupplier";
    }

    @PostMapping("/addSupplier")
    public String addSupplier(Supplier supplier, HttpServletRequest request) {
        int flag = supplierService.insertSupplier(supplier);
        if (flag != 0) return "success";
        request.setAttribute("msg", "添加失败");
        return "supplier/addSupplier";
    }

    @GetMapping("/manageSupplier.html")
    public String toManageSupplier(HttpServletRequest request) {
        Page page = new Page();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", null);
        map.put("start", 0);
        page.setCurrentPageNo(1);
        List<Supplier> supplierList = supplierService.findAllSupplier();
        page.setTotalCount(supplierService.findSupplierCountByName(map));
        request.setAttribute("supplierList", supplierList);
        request.setAttribute("page", page);
        return "supplier/manageSupplier";
    }

    @GetMapping("/manageSupplierSearch")
    public String manageSupplierSearch(String name, Integer pageindex, HttpServletRequest request) {
        Page page = new Page();
        Map<String, Object> map = new HashMap<String, Object>();
        List<Supplier> supplierList;
        //根据前端传过来的页数，计算sql中的start变量的值
        if (pageindex == null || pageindex == 0) {
            pageindex = 0;
            page.setCurrentPageNo(1);
        } else {
            page.setCurrentPageNo(pageindex + 1);
            pageindex *= 10;
        }
        map.put("name", name);
        map.put("start", pageindex);
        supplierList = supplierService.findSupplierListByName(map);
        page.setTotalCount(supplierService.findSupplierCountByName(map));
        request.setAttribute("supplierList", supplierList);
        request.setAttribute("page", page);
        //过滤掉前端传入的无效数据发送回去
        if (name != null && name != "") request.setAttribute("name", name);
        return "supplier/manageSupplier";
    }

    @GetMapping("/detailSupplier.html")
    public String detailSupplier(Integer id, HttpServletRequest request) {
        Supplier supplier = supplierService.findOneSupplierById(id);
        request.setAttribute("supplier", supplier);
        return "supplier/detailSupplier";
    }

    @GetMapping("/updateSupplier.html")
    public String toUpdateSupplier(Integer id, HttpServletRequest request) {
        //根据id查询 supplier，发送到前端
        Supplier supplier = supplierService.findOneSupplierById(id);
        request.setAttribute("supplier", supplier);
        return "supplier/updateSupplier";
    }

    @PostMapping("/updateSupplier")
    public String updateSupplier(Supplier supplier) {
        int flag = supplierService.updateSupplier(supplier);
        if (flag == 1) return "success";
        return "error";
    }

    @GetMapping("/detailSupplierRecord.html")
    public String detailSupplierRecord(Integer id, Integer pageindex, HttpServletRequest request) {
        Page page = new Page(pageindex);
        if (id == null) id = 0;
        List<MaterialRecord> supplierRecordList = supplierService.findSupplierRecordByid(id, page.getSqlSelectPageStart());
        page.setTotalCount(supplierService.findSupplierRecordCountByid(id));
        request.setAttribute("supplierRecordList", supplierRecordList);
        request.setAttribute("page", page);
        return "supplier/detailSupplierRecord";
    }

}
