package com.mo.service.impl;

import com.mo.mapper.EmployeeMapper;
import com.mo.pojo.Duty;
import com.mo.pojo.Employee;
import com.mo.pojo.Material;
import com.mo.pojo.Product;
import com.mo.service.EmployeeService;
import com.mo.utils.MyToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    /**
     * 登录
     *
     * @param employee
     * @return
     */
    @Transactional(readOnly = true, timeout = 15)
    @Override
    public Employee login(Employee employee) {
        return employeeMapper.login(employee);
    }


    /**
     * 根据 id 查询 1 条employee
     *
     * @param id
     * @return
     */
    @Transactional(readOnly = true, timeout = 15)
    @Override
    public Employee findEmployeeById(Integer id) {
        return employeeMapper.findEmployeeById(id);
    }

    /**
     * 1：查询telephone、oldPassword、id是否正确
     * 2：正确->重新访问数据库修改密码
     *
     * @param employee
     * @param newPassword
     * @return 修改成功返回 1，错误返回 0
     */
    @Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED, timeout = 15)
    @Override
    public Employee updatePassword(Employee employee, String newPassword) {
        Employee employee2 = employeeMapper.findEmployeeByIdTelephonePassword(employee);
        if (employee2 != null) {
            employee2.setPassword(newPassword);
            System.out.println("====2==" + employee2);
            int flag = employeeMapper.updatePassword(employee2);
            if (flag == 1) {
                return employee2;
            }
        }
        return null;
    }

    /**
     * 查询所有的 Duty
     *
     * @return
     */
    @Transactional(readOnly = true, timeout = 15)
    @Override
    public List<Duty> findDutyList() {
        return employeeMapper.findDutyList();
    }

    /**
     * 1：生成一个uid，格式为：202188888228的12位编号
     * 2：查询编号是否被注册
     * 3：插入数据
     *
     * @param employee
     * @return
     */
    @Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED, timeout = 15)
    @Override
    public String insertEmployee(Employee employee) {
        MyToString myToString = new MyToString();
        employee.setUid(myToString.getMyToString());
        //根据职务给予权限
        if (employee.getDuty_id() == 3) {
            employee.setRights(4);
        } else if (employee.getDuty_id() == 2) {
            employee.setRights(3);
        } else if (employee.getDuty_id() == 1) {
            employee.setRights(2);
        }
        employee.setCreate_by(1);
        Employee employee1 = employeeMapper.findEmployeeByUid(employee.getUid());
        if (employee1 == null) {
            int flag = employeeMapper.insertEmployee(employee);
            if (flag == 1) {
                return employee.getUid();
            }
        }
        return "0";
    }

    /**
     * 1：查询是否有这个telephone、账号相匹配的字段
     * 2：插入
     *
     * @param employee
     * @return
     */
    @Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED, timeout = 15)
    @Override
    public int retrievePassword(Employee employee) {
        int flag = 0;
        Employee employee1 = employeeMapper.findEmployeeByUidTelephone(employee);
        if (employee1 != null) {
            employee.setId(employee1.getId());
            flag = employeeMapper.updatePassword(employee);
        }
        return flag;
    }

    /**
     * 查询 material 当前数量低于最低库存的
     *
     * @return
     */
    @Transactional(readOnly = true, timeout = 15)
    @Override
    public List<Material> findViewAlertRM() {
        return employeeMapper.findViewAlertRM();
    }

    /**
     * 查询 product 当前数量低于最低库存的
     *
     * @return
     */
    @Transactional(readOnly = true, timeout = 15)
    @Override
    public List<Product> findViewAlertRP() {
        return employeeMapper.findViewAlertRP();
    }

    /**
     * 查询近七天内，物料的使用分布
     *
     * @return
     */
    @Transactional(readOnly = true, timeout = 15)
    @Override
    public Map<String, Object> findMaterialUseInSeven() {
        Map<String, Object> map = new HashMap<String, Object>();
        List<Material> materialList = employeeMapper.findMaterialUseInSeven();
        float sum = employeeMapper.findTotalUseInSeven() * -1;
        for (Material m : materialList) {
            m.setName(employeeMapper.findMaterialNameById(m.getId()));
            Integer q = Integer.valueOf(m.getQuantity()) * -1;
            m.setQuantity(q.toString());
        }
        int i = 0;
        Integer percentsum = 0;
        map.put("blueName", materialList.get(i).getName());
        map.put("bluePercent", ((float) (Math.round(Float.valueOf(materialList.get(i).getQuantity()) / sum * 100))));
        percentsum += Math.round(Float.valueOf(materialList.get(i).getQuantity()) / sum * 100);
        ++i;
        map.put("greenName", materialList.get(i).getName());
        map.put("greenPercent", ((float) (Math.round(Float.valueOf(materialList.get(i).getQuantity()) / sum * 100))));
        percentsum += Math.round(Float.valueOf(materialList.get(i).getQuantity()) / sum * 100);
        ++i;
        map.put("purpleName", materialList.get(i).getName());
        map.put("purplePercent", ((float) (Math.round(Float.valueOf(materialList.get(i).getQuantity()) / sum * 100))));
        percentsum += Math.round(Float.valueOf(materialList.get(i).getQuantity()) / sum * 100);
        ++i;
        map.put("aeroName", materialList.get(i).getName());
        map.put("aeroPercent", ((float) (Math.round(Float.valueOf(materialList.get(i).getQuantity()) / sum * 100))));
        percentsum += Math.round(Float.valueOf(materialList.get(i).getQuantity()) / sum * 100);
        map.put("OthersPercent", Integer.valueOf(100 - percentsum));
        return map;
    }

    /**
     * 查询 近7天，商品销量的top10
     *
     * @return
     */
    @Transactional(readOnly = true, timeout = 15)
    @Override
    public List<Product> findProductSalesInSeven() {
        List<Product> productList = employeeMapper.findProductSalesInSeven();
        for (Product p : productList) {
            Integer m = Integer.valueOf(p.getQuantity()) * -1;
            p.setQuantity(m.toString());
            Product pp = employeeMapper.findProductById(p.getId());
            p.setName(pp.getName());
            p.setPid(pp.getPid());
        }
        return productList;
    }

    /**
     * 查询客户数量、供应商数、物料种类、商品种类
     *
     * @return
     */
    @Transactional(readOnly = true, timeout = 15)
    @Override
    public Map<String, Object> findCount() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("customerSize", employeeMapper.findCustomerCount());
        map.put("supplierSize", employeeMapper.findSupplierCount());
        map.put("productSize", employeeMapper.findProductCount());
        map.put("materialSize", employeeMapper.findMaterialCount());
        return map;
    }

    /**
     * 查询 多少天 内的销售额
     * 条件 ：天数
     *
     * @param day
     * @return
     */
    @Override
    public Float findSalesInDay(Integer day) {
        return employeeMapper.findSalesInDay(day);
    }

    /**
     * 查询 近7天内 商品的销售分布
     *
     * @return
     */
    @Override
    public Map<String, Object> findProductSalesInSevenTop() {
        Map<String, Object> map = new HashMap<String, Object>();
        List<Product> productList = employeeMapper.findProductSalesInSeven();
        List<Product> products = new ArrayList<Product>();
        float sum = employeeMapper.findTotalProductUseInSeven() * -1;
        for (int i = 0; i < 4; ++i) {
            Product product = employeeMapper.findProductById(productList.get(i).getId());
            productList.get(i).setName(product.getName());
            Integer q = Integer.valueOf(productList.get(i).getQuantity()) * -1;
            productList.get(i).setQuantity(q.toString());
            products.add(productList.get(i));
        }
        int i = 0;
        Integer percentsum = 0;
        map.put("BlueName", products.get(i).getName());
        map.put("BlueV", ((float) (Math.round(Float.valueOf(products.get(i).getQuantity()) / sum * 100))));
        percentsum += Math.round(Float.valueOf(products.get(i).getQuantity()) / sum * 100);
        ++i;
        map.put("GreenName", products.get(i).getName());
        map.put("GreenV", ((float) (Math.round(Float.valueOf(products.get(i).getQuantity()) / sum * 100))));
        percentsum += Math.round(Float.valueOf(products.get(i).getQuantity()) / sum * 100);
        ++i;
        map.put("GrayName", products.get(i).getName());
        map.put("GrayV", ((float) (Math.round(Float.valueOf(products.get(i).getQuantity()) / sum * 100))));
        percentsum += Math.round(Float.valueOf(products.get(i).getQuantity()) / sum * 100);
        ++i;
        map.put("PurpleName", products.get(i).getName());
        map.put("PurpleV", ((float) (Math.round(Float.valueOf(products.get(i).getQuantity()) / sum * 100))));
        percentsum += Math.round(Float.valueOf(products.get(i).getQuantity()) / sum * 100);
        map.put("OthersV", Integer.valueOf(100 - percentsum));
        return map;
    }


}
