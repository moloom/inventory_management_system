package com.mo.controller;

import com.mo.pojo.Employee;
import com.mo.pojo.Repository;
import com.mo.service.RepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class RepositoryController {
    @Autowired
    private RepositoryService repositoryService;

    @GetMapping("/addRepository.html")
    public String toAddRepository(HttpServletRequest request) {
        //查询出 duty=2 的员工 (仓库管理员)
        List<Employee> employeeList = repositoryService.findEmployeeByDuty_id(2);
        request.setAttribute("employeeList", employeeList);
        return "repository/addRepository";
    }

    @PostMapping("/addRepository")
    public String addRepository(Repository repository) {
        int flag = repositoryService.insertRepository(repository);
        if (flag == 1) {
            return "success";
        }
        return "error";
    }

    @GetMapping("/manageRepository.html")
    public String toManageRepository(HttpServletRequest request) {
        List<Repository> repositoryList = repositoryService.findAllRepository();
        request.setAttribute("repositoryList", repositoryList);
        return "repository/manageRepository";
    }

    @GetMapping("/manageRepositorySearch")
    public String manageMaterialSearch(String name, HttpServletRequest request) {
        List<Repository> repositoryList = null;
        repositoryList = repositoryService.findRepositoryListByName(name);
        request.setAttribute("repositoryList", repositoryList);
        return "repository/manageRepository";
    }

    @GetMapping("/detailRepository.html")
    public String detailRepository(Integer id, HttpServletRequest request) {
        Repository repository = null;
        if (id != null) {
            repository = repositoryService.findOneRepositoryById(id);
        }
        request.setAttribute("repository", repository);
        return "repository/detailRepository";
    }

    @GetMapping("/updateRepository.html")
    public String toUpdateRepository(Integer id, HttpServletRequest request) {
        List<Employee> employeeList = repositoryService.findEmployeeByDuty_id(2);
        Repository repository = repositoryService.findOneRepositoryById(id);
        request.setAttribute("repository", repository);
        request.setAttribute("employeeList", employeeList);
        return "repository/updateRepository";
    }

    @PostMapping("/updateRepository")
    public String updateRepository(Repository repository) {
        System.out.println(repository);
        int flag = repositoryService.updateRepository(repository);
        if (flag == 1)
            return "success";
        else
            return "error";
    }

}
