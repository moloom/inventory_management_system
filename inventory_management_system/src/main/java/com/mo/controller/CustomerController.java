package com.mo.controller;

import com.mo.pojo.Customer;
import com.mo.pojo.Page;
import com.mo.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/addCustomer.html")
    public String toAddCustomer() {
        return "customer/addCustomer";
    }

    @PostMapping("/addCustomer")
    public String addCustomer(Customer customer) {
        int flag = customerService.insertCustomer(customer);
        if (flag != 0)
            return "success";
        else {
            return "error";
        }
    }

    @GetMapping("/manageCustomer.html")
    public String toManageCustomer(HttpServletRequest request) {
        Page page = new Page();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", null);
        map.put("start", 0);
        page.setCurrentPageNo(1);
        List<Customer> customerList = customerService.findAllCustomer();
        page.setTotalCount(customerService.findCustomerCountByName(map));
        request.setAttribute("customerList", customerList);
        request.setAttribute("page", page);
        return "customer/manageCustomer";
    }

    @GetMapping("/manageCustomerSearch")
    public String manageCustomerSearch(String name, Integer pageindex, HttpServletRequest request) {
        Page page = new Page();
        Map<String, Object> map = new HashMap<String, Object>();
        List<Customer> customerList;
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
        customerList = customerService.findCustomerListByName(map);
        page.setTotalCount(customerService.findCustomerCountByName(map));
        request.setAttribute("customerList", customerList);
        request.setAttribute("page", page);
        //过滤掉前端传入的无效数据发送回去
        if (name != null && name != "")
            request.setAttribute("name", name);
        return "customer/manageCustomer";
    }

    @GetMapping("/detailCustomer.html")
    public String detailCustomer(Integer id, HttpServletRequest request) {
        Customer customer = customerService.findOneCustomerById(id);
        request.setAttribute("customer", customer);
        return "customer/detailCustomer";
    }

    @GetMapping("/updateCustomer.html")
    public String toUpdateCustomer(Integer id, HttpServletRequest request) {
        //根据id查询customer，发送到前端
        Customer customer = customerService.findOneCustomerById(id);
        request.setAttribute("customer", customer);
        return "customer/updateCustomer";
    }

    @PostMapping("/updateCustomer")
    public String updateCustomer(Customer customer) {
        int flag = customerService.updateCustomer(customer);
        if (flag == 1) return "success";
        return "error";
    }

}
