package com.mo.controller;

import com.alibaba.fastjson.JSONArray;
import com.mo.pojo.*;
import com.mo.service.InRepositoryService;
import com.mo.service.MaterialService;
import com.mo.utils.MySubString;
import com.mo.utils.MyToString;
import com.mo.utils.OwnSubStringTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.*;

@Controller
public class InRepositoryController {
    @Autowired
    MaterialService materialService;

    @Autowired
    InRepositoryService inRepositoryService;

    /**
     * 1：判断是否第一次点击入库,
     * 2：是：创建一个inRepository表，表内只有订单编号和自动生成的id
     * 2：不是：获取session中的单据 bid，查询单据
     * 3：如果单据中没有选择物料，则直接请求界面。如果单据中有物料，就把物料显示出来。
     *
     * @return
     */
    @GetMapping("/inRepository.html")
    public String toInRepository(HttpSession session, HttpServletRequest request) {

        //如果是第一次请求，则生成一个单据，
        if (session.getAttribute("inRepositoryBid") == null) {
            InRepository inRepository = new InRepository();
            //拼接单据编号，使其为：IB2122222222，格式
            inRepository.setBid("IB" + new MyToString().getMyToString().substring(2));
            //添加 create_by
            inRepository.setCreate_by(((Employee) session.getAttribute("employeeSession")).getId());
            int flag = inRepositoryService.insertInRepository(inRepository);
            if (flag == 0) return "error";
            request.setAttribute("materialLength", 0);
            session.setAttribute("inRepositoryBid", inRepository.getBid());
        } else {
            InRepository inRepository = inRepositoryService.findOneInRepositoryByBid((String) session.getAttribute("inRepositoryBid"));
            //判断单据里物料是否为空
            if (inRepository.getMaterial_id() == null || inRepository.getMaterial_id().equals("")) {
                request.setAttribute("materialLength", 0);
                return "repository/inRepository.html";
            }
            //查询所有的 物料
            List<Material> materialList = materialService.findAllMaterial();
            List<String> materialIdList = null;
            //显示已选中的物料
            List<Material> m2 = new ArrayList<Material>();
            if (inRepository.getMaterial_id() != null) {
                materialIdList = MySubString.subString(inRepository.getMaterial_id(), ",");
                for (String l : materialIdList) {
                    for (Material material : materialList) {
                        if (material.getId() == Integer.valueOf(l)) m2.add(material);
                    }
                }
            }
            //把数量字段，转换为list
            List<String> qs = MySubString.subString(inRepository.getQuantity(), ",");
            //索引
            int index = 0;
            for (Material m : m2) {
                //给前台传个物料数量的值
                m.setQuantity(qs.get(index));
                ++index;
            }
            request.setAttribute("materialLength", m2.size());
            request.setAttribute("materialList", m2);
        }
        return "repository/inRepository.html";
    }

    /**
     * 1:查询出未被选中的物料。并显示出来
     *
     * @param name
     * @param supplier_id
     * @param pageindex
     * @param request
     * @return
     */
    @GetMapping("/selectMaterial")
    public String selectMaterial(String name, String supplier_id, Integer pageindex, HttpSession session, HttpServletRequest request) {
        Page page = new Page();
        List<Material> materialList = null;
        List<String> idList = null;
        Map<String, Object> map = new HashMap<String, Object>();
        //根据bid查询对应的单据
        InRepository inRepository = inRepositoryService.findOneInRepositoryByBid(session.getAttribute("inRepositoryBid").toString());
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
        int count = inRepositoryService.findMaterialCountByNameAndSupplier(map);
        if (inRepository.getMaterial_id() != null)
            idList = MySubString.subString(inRepository.getMaterial_id(), ",");
        //hashSet，集合元素唯一
        //这里不需要重写hashCode和equals 因为hash值一样
        Collection<Material> myMaterialList = new HashSet<Material>();
        //让已经选中物料不再出现在挑选物料页面中
        if (inRepository.getMaterial_id() != null && !inRepository.getMaterial_id().equals("")) {
            //1：设一个变量，用于检测要显示的物料是否有10条
            //2：没有达到：继续请求第二页的物料数据，循环检测没有没有被选中的物料。直到有10条为止
            //3：同时需修改相应的page的totalCount
            boolean countFlag = true;
            while (countFlag) {
                materialList = inRepositoryService.findMaterialListByNameAndSupplier(map);

                Iterator<Material> materialIterator = materialList.iterator();
                while (materialIterator.hasNext()) {
                    Material m1 = materialIterator.next();
                    Iterator<String> iid = idList.iterator();
                    while (iid.hasNext()) {
                        String id = iid.next();
                        if (m1.getId().intValue() == Integer.valueOf(id).intValue()) {
                            materialIterator.remove();
                            break;
                        }
                    }
                }
                int i = 0;
                //这个是防止 条件查询无结果而报错
                if (count > 0 && myMaterialList.size() < 10) {
                    while (myMaterialList.size() < 10 && i < materialList.size()) {
                        myMaterialList.add(materialList.get(i));
                        ++i;
                    }
                    pageindex += 10;
                    map.put("start", pageindex);
                    //这是，为了：未被选中的物料大于10条时，一页可以显示十条数据
                    if ((count - pageindex) <= 0)
                        countFlag = false;
                } else {
                    countFlag = false;
                }
            }
        } else {
            count = materialService.findMaterialCountByNameAndSupplier(map);
            myMaterialList = materialService.findMaterialListByNameAndSupplier(map);
        }
        //只查了10条数据出来，排除掉已选中的，一页显示的记录根本就没有10条
        //查询供应商list
        List<Supplier> supplierList = materialService.findAllSupplier();
        //防止 count - idList.size() =1，而造成页数为0
        if (idList == null) page.setTotalCount(count);
        else page.setTotalCount(count - idList.size());
        request.setAttribute("supplierList", supplierList);
        request.setAttribute("materialList", myMaterialList);
        //防止查询有数据时，数据条数却为 1
        if (page.getTotalCount() <= 0 && !myMaterialList.isEmpty()) page.setTotalCount(count);

        request.setAttribute("page", page);
        if (name != null && name != "") request.setAttribute("name", name);
        if (supplier_id != null && supplier_id != "") request.setAttribute("supplier_id", supplier_id);
        return "repository/selectMaterial";
    }

    /**
     * 1:把传入的materialId保存到数据库的单据表中
     * 2：根据单据表中的物料id，查询每个物料信息
     *
     * @param materialId
     * @return
     */
    @PostMapping("/selectMaterialSubmit")
    public String selectMaterial(String materialId, HttpSession session, HttpServletRequest request) {
        List<String> materialIdList = null;
        //查询所有的 物料
        List<Material> materialList = materialService.findAllMaterial();
        //根据bid查询对应的单据
        InRepository inRepository = inRepositoryService.findOneInRepositoryByBid(session.getAttribute("inRepositoryBid").toString());
        //判断是否选了物料
        if (materialId != null && !"".equals(materialId)) {
            List<String> sl = new ArrayList<String>();
            //初始化quantity、unit_price、supplier_id字符串，格式 0,0,0,0
            List<String> stringList = MySubString.subString(materialId, ",");
            //生成与物料同样个数的 0,0,0,0 list，并转为字符串
            for (String s : stringList) {
                sl.add("0");
            }
            //判断materialId是否为空，为空则直接赋值，不为空则拼接字符串
            String str = OwnSubStringTool.listToString(sl);
            if (inRepository.getMaterial_id() == null || inRepository.getMaterial_id().equals("")) {
                inRepository.setMaterial_id(materialId);
                //初始化 quantity、unit_price、supplier_id
                inRepository.setUnit_price(str);
                inRepository.setQuantity(str);
                inRepository.setSupplier_id(str);
            } else {
                inRepository.setMaterial_id(inRepository.getMaterial_id() + "," + materialId);
                //初始化 quantity、unit_price、supplier_id
                inRepository.setSupplier_id((inRepository.getSupplier_id() + "," + str));
                inRepository.setQuantity(inRepository.getQuantity() + "," + str);
                inRepository.setUnit_price(inRepository.getUnit_price() + "," + str);
            }
            //修改所选物料
            int flag = inRepositoryService.updateInRepository(inRepository);
            // 修改出错转到出错界面
            if (flag == 0)
                return "error";
        }
        //显示已选中的物料
        List<Material> m2 = new ArrayList<Material>();
        if (inRepository.getMaterial_id() != null) {
            materialIdList = MySubString.subString(inRepository.getMaterial_id(), ",");
            for (String l : materialIdList) {
                for (Material material : materialList) {
                    if (material.getId() == Integer.valueOf(l)) m2.add(material);
                }
            }
        }
        //排除未选中物料而报空指针异常
        if (inRepository.getQuantity() != null && !"".equals(inRepository.getQuantity())) {
            //把数量字段，转换为list
            List<String> qs = MySubString.subString(inRepository.getQuantity(), ",");
            //索引
            int index = 0;
            for (Material m : m2) {
                //给前台传个物料数量的值
                m.setQuantity(qs.get(index));
                ++index;
            }
        }
        request.setAttribute("materialLength", m2.size());
        request.setAttribute("materialList", m2);
        return "repository/inRepository";
    }

    @GetMapping("/inRepositoryTwo.html")
    public String inRepositoryTwo(HttpServletRequest request) {
        //获取仓库信息，发送过去
        List<Repository> repositoryList = inRepositoryService.findAllRepository();
        request.setAttribute("repositoryList", repositoryList);
        return "repository/inRepositoryTwo";
    }

    /**
     * 入库的最终操作
     * 1:计算填充单据中物料对应的 unit_price，total_price、supplier_id
     * 2: 循环新建各个物料流水记录，并填充记录
     * 3：修改，相应物料的数量
     *
     * @param inRepository
     * @param deliverdate
     * @param session
     * @return
     */
    @PostMapping("/addInRepository")
    public String addInRepository(InRepository inRepository, String deliverdate, HttpSession session) {
        //处理时间格式
        String y = deliverdate.substring(0, deliverdate.indexOf("/"));
        deliverdate = deliverdate.substring(deliverdate.indexOf("/") + 1);
        String d = deliverdate.substring(0, deliverdate.indexOf("/"));
        deliverdate = deliverdate.substring(deliverdate.indexOf("/") + 1);
        deliverdate = deliverdate + "-" + y + "-" + d + " 00:00:00";
        inRepository.setDeliver_date(Timestamp.valueOf(deliverdate));
        inRepository.setModify_by(((Employee) session.getAttribute("employeeSession")).getId());
        inRepository.setBid((String) session.getAttribute("inRepositoryBid"));
        boolean flag = inRepositoryService.addInRepository(inRepository);
        if (flag) {
            session.removeAttribute("inRepositoryBid");
            return "success";
        }
        return "error";
    }

    /*-----------------ajax请求------------------------*/
    @PostMapping("/deleteSelectMaterial")
    @ResponseBody
    public String deleteSelectMaterial(Integer id, HttpSession session) {
        int flag = inRepositoryService.deleteSelectMaterial(session.getAttribute("inRepositoryBid").toString(), id);
        if (flag == 1) return JSONArray.toJSONString("success");
        return JSONArray.toJSONString("fail");
    }

    @PostMapping("/updateSelectMaterialQuantity")
    @ResponseBody
    public String updateSelectMaterialQuantity(String quantity, String id, HttpSession session) {
        //获取 用户id作为修改者，前面的修改请求也要改
        Employee employee = (Employee) session.getAttribute("employeeSession");
        //获取 bid
        InRepository inRepository = new InRepository();
        inRepository.setBid((String) session.getAttribute("inRepositoryBid"));
        inRepository.setModify_by(employee.getId());
        inRepository.setQuantity(quantity);
        inRepository.setMaterial_id(id);
        int flag = inRepositoryService.updateSelectMaterialQuantity(inRepository);
        if (flag == 1) return JSONArray.toJSONString("success");
        return JSONArray.toJSONString("fail");
    }

}
