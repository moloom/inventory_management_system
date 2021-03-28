package com.mo.controller;

import com.alibaba.fastjson.JSONArray;
import com.mo.pojo.Duty;
import com.mo.pojo.Employee;
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

@Controller
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @GetMapping(value = {"/"})
    public String index() {
        return "main";
    }

    @GetMapping("/login")
    public String login() {
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
        System.out.println("---------" + newPassword + "------" + employee);
        employee = employeeService.updatePassword(employee, newPassword);
        if (employee != null) {
            return "success";
        }
        request.setAttribute("msg", "修改密码失败！");
        return "updatePassword";
    }

    @GetMapping("/logOut.html")
    public String logOut(HttpSession session, HttpServletRequest request) {
        session.removeAttribute("employeeSession");
        session.removeAttribute("inRepositoryBid");
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
            System.out.println("employee\n\n"+flag+"\n"+employee);
            session.setAttribute("signupSession", employee);
            return JSONArray.toJSONString("/signupSuccess.html");
        } else {
            session.setAttribute("msg", "注册失败！");
            return JSONArray.toJSONString("/signup.html");
        }

    }
}
