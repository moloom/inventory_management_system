package com.mo.controller;

import com.alibaba.fastjson.JSONArray;
import com.mo.pojo.Duty;
import com.mo.pojo.Employee;
import com.mo.pojo.Material;
import com.mo.pojo.Product;
import com.mo.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Controller
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @GetMapping(value = {"/"})
    public String index(HttpServletRequest request) {
        // 查询 库存预警的 信息 start
        List<Material> materialList = employeeService.findViewAlertRM();
        List<Product> productList = employeeService.findViewAlertRP();
        if (materialList.size() > 4) {
            for (int i = 4; i < materialList.size(); ++i) {
                materialList.remove(i);
            }
        }
        if (productList.size() > 4) {
            for (int i = 4; i < productList.size(); ++i) {
                productList.remove(i);
            }
        }
        request.setAttribute("materialList", materialList);
        request.setAttribute("productList", productList);
        // end

        //查询最近7天内物料使用分布 start
        Map<String, Object> map = employeeService.findMaterialUseInSeven();
        //end
        //查询最近7天，商品销量top10
        List<Product> productList1 = employeeService.findProductSalesInSeven();
        request.setAttribute("productListOfTop", productList1);
        //查询客户数量、供应商数、物料种类、商品种类
        Map<String, Object> map2 = employeeService.findCount();
        map.putAll(map2);
        //查询 近7天商品销售top4
        Map<String, Object> map3 = employeeService.findProductSalesInSevenTop();
        map.putAll(map3);
        //end
        //查询销售额
        Float oneDay = employeeService.findSalesInDay(1);
        Float twoDay = employeeService.findSalesInDay(2);
        Float sevenDay = employeeService.findSalesInDay(7);
        Float fourteenDay = employeeService.findSalesInDay(14);
        //排除空值异常
        if (oneDay == null) oneDay = Float.valueOf(0);
        map.put("oneDaySales", oneDay);
        map.put("oneDayThanYesterday", (Math.round((twoDay - oneDay) / oneDay) / 100.0) - 1);
        map.put("sevenDaySales", sevenDay);
        if (fourteenDay - sevenDay != 0)
            map.put("sevenDayThanYesterday", Math.round((fourteenDay - sevenDay) / sevenDay) - 1);
        else map.put("sevenDayThanYesterday", 0);
        request.setAttribute("mapOfUse", map);
        return "main";
    }

    @GetMapping("/login")
    public String login(HttpSession session) {
        session.removeAttribute("mInOutRepositoryBid");
        session.removeAttribute("pInOutRepositoryBid");
        return "login";
    }

    @PostMapping("/loginSubmit")
    public String login(Employee employee, HttpSession session, HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (employee.getUid() != null && !StringUtils.isEmpty(employee.getPassword())) {
            Employee em = employeeService.login(employee);
            if (em != null) {
                session.setAttribute("employeeSession", em);
                response.sendRedirect("/");
            }
        }
        request.setAttribute("msg", "登录失败，请重新登录！");
        return "login";
    }

    @GetMapping("/employeeDetail.html")
    public String detail(Integer id, HttpServletRequest request) {
        System.out.println(id);
        Employee employee = employeeService.findEmployeeById(id);
        request.setAttribute("employeeMsg", employee);
        return "employeeDetail";
    }

    @GetMapping("/updatePassword.html")
    public String toUpdatePassword() {
        return "updatePassword";
    }

    @PostMapping("/updatePassword")
    public String updatePassword(@RequestParam("newPassword") String newPassword, Employee employee, HttpServletRequest request) {
        employee = employeeService.updatePassword(employee, newPassword);
        if (employee != null) return "success";
        request.setAttribute("msg", "修改密码失败！");
        return "updatePassword";
    }

    @GetMapping("/logOut.html")
    public String logOut(HttpSession session, HttpServletRequest request) {
        session.removeAttribute("employeeSession");
        session.removeAttribute("mInOutRepositoryBid");
        session.removeAttribute("pInOutRepositoryBid");
        request.setAttribute("msg", "退出登录成功！");
        return "login";
    }

    @GetMapping(value = "/signup.html")
    public String toSignup(HttpServletRequest request) {
        List<Duty> dutyList = employeeService.findDutyList();
        request.setAttribute("dutyMsg", dutyList);
        return "signup";
    }

    @GetMapping("/signupSuccess.html")
    public String signupSuccess() {
        return "signupSuccess";
    }

    @GetMapping("/retrievePassword.html")
    public String toRetrievePassword() {
        return "retrievePassword";
    }

    @PostMapping("/retrievePassword")
    public String retrievePassword(Employee employee, HttpServletRequest request) {
        System.out.println("----retrievePassword---------------" + employee);
        int flag = employeeService.retrievePassword(employee);
        if (flag != 0) {
            request.setAttribute("msg", "修改密码成功！");
            return "login";
        } else {
            request.setAttribute("msg", "修改密码失败！");
            return "retrievePassword";
        }
    }

    @GetMapping("/success.html")
    public String success() {
        return "success";
    }


    /*-------------------------ajax请求----------------------------------------------*/

    /**
     * ajax，注册
     *
     * @param employee
     * @return
     */
    @PostMapping("/signup")
    @ResponseBody
    public String signup(Employee employee, HttpSession session) {
        System.out.println("--------singup-----------" + employee);
        String flag = employeeService.insertEmployee(employee);
        if (!flag.equals("0")) {
            employee.setUid(flag);
            System.out.println("employee\n\n" + flag + "\n" + employee);
            session.setAttribute("signupSession", employee);
            return JSONArray.toJSONString("/signupSuccess.html");
        } else {
            session.setAttribute("msg", "注册失败！");
            return JSONArray.toJSONString("/signup.html");
        }
    }


}
