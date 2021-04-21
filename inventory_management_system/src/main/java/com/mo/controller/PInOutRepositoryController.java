package com.mo.controller;

import com.alibaba.fastjson.JSONArray;
import com.mo.pojo.*;
import com.mo.service.PInOutRepositoryService;
import com.mo.service.ProductService;
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
import java.util.*;

@Controller
public class PInOutRepositoryController {
    @Autowired
    ProductService productService;

    @Autowired
    PInOutRepositoryService pInOutRepositoryService;

    /**
     * 1：判断是否第一次点击入库,
     * 2：是：创建一个 pInOutRepository 表，表内只有订单编号和自动生成的id
     * 2：不是：获取session中的单据 bid，查询单据
     * 3：如果单据中没有选择商品，则直接请求界面。如果单据中有商品，就把商品显示出来。
     *
     * @return
     */
    @GetMapping("/addPInOutRepositoryOne.html")
    public String toPInOutRepositoryService(HttpSession session, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", null);
        map.put("clothing_types_id", null);
        map.put("clothing_sex", null);
        map.put("start", 0);
        //如果是第一次请求，则生成一个单据，
        if (session.getAttribute("pInOutRepositoryBid") == null) {
            PInOutRepository pInOutRepository = new PInOutRepository();
            //拼接单据编号，使其为：IB2122222222，格式
            pInOutRepository.setBid("PB" + new MyToString().getMyToString().substring(2));
            //添加 create_by
            pInOutRepository.setCreate_by(((Employee) session.getAttribute("employeeSession")).getId());
            int flag = pInOutRepositoryService.insertPInOutRepository(pInOutRepository);
            if (flag == 0) return "error";
            request.setAttribute("productLength", 0);
            session.setAttribute("pInOutRepositoryBid", pInOutRepository.getBid());
        } else {
            PInOutRepository pInOutRepository = pInOutRepositoryService.findOnePInOutRepositoryByBid((String) session.getAttribute("pInOutRepositoryBid"));
            //判断单据里 商品 是否为空
            if (pInOutRepository.getProduct_id() == null || pInOutRepository.getProduct_id().equals("")) {
                request.setAttribute("productLength", 0);
                return "pInOutRepository/addPInOutRepositoryOne";
            }
            //查询所有的 商品
            List<Product> productList = pInOutRepositoryService.findProductListByNameAndClothing_types_id(map);
            List<String> productIdList = null;
            //显示已选中的商品
            List<Product> p2 = new ArrayList<Product>();
            if (pInOutRepository.getProduct_id() != null) {
                productIdList = MySubString.subString(pInOutRepository.getProduct_id(), ",");
                for (String l : productIdList) {
                    for (Product product : productList) {
                        if (product.getId() == Integer.valueOf(l)) p2.add(product);
                    }
                }
            }
            //把数量字段，转换为list
            List<String> qs = MySubString.subString(pInOutRepository.getQuantity(), ",");
            //索引
            int index = 0;
            for (Product p : p2) {
                //给前台传个商品数量的值
                p.setQuantity(qs.get(index));
                ++index;
            }
            request.setAttribute("productLength", p2.size());
            request.setAttribute("productList", p2);
        }
        return "pInOutRepository/addPInOutRepositoryOne";
    }

    /**
     * 1:查询出未被选中的商品。并显示出来
     *
     * @param name
     * @param pageindex
     * @param request
     * @return
     */
    @GetMapping("/selectProduct")
    public String selectProduct(String name, Integer clothingTypesId, Integer clothing_sex, Integer pageindex, HttpSession session, HttpServletRequest request) {
        Page page = new Page(pageindex);
        pageindex = page.getPageIndex(pageindex);
        List<Product> productList = null;
        List<String> idList = null;
        Map<String, Object> map = new HashMap<String, Object>();
        List<ClothingTypes> clothingTypesList = pInOutRepositoryService.findAllClothingTypes();
        //根据bid查询对应的单据
        PInOutRepository pInOutRepository = pInOutRepositoryService.findOnePInOutRepositoryByBid(session.getAttribute("pInOutRepositoryBid").toString());
        //防止无数据时查询无结果
        if (clothingTypesId != null && clothingTypesId == 0) clothingTypesId = null;
        if (clothing_sex != null && clothing_sex == 0) clothing_sex = null;
        map.put("name", name);
        map.put("clothing_types_id", clothingTypesId);
        map.put("clothing_sex", clothing_sex);
        map.put("start", page.getSqlSelectPageStart());
        int count = pInOutRepositoryService.findProductCountByNameAndClothing_types_id(map);
        //hashSet，集合元素唯一
        //这里不需要重写hashCode和equals 因为hash值一样
        Collection<Product> myProductList = new HashSet<Product>();
        //让已经选中商品不再出现在挑选商品页面中
        if (pInOutRepository.getProduct_id() != null && !pInOutRepository.getProduct_id().equals("")) {
            //1：设一个变量，用于检测要显示的商品是否有10条
            //2：没有达到：继续请求第二页的商品数据，循环检测没有没有被选中的商品。直到有10条为止
            //3：同时需修改相应的page的totalCount
            idList = MySubString.subString(pInOutRepository.getProduct_id(), ",");
            boolean countFlag = true;
            while (countFlag) {
                productList = pInOutRepositoryService.findProductListByNameAndClothing_types_id(map);

                Iterator<Product> productIterator = productList.iterator();
                while (productIterator.hasNext()) {
                    Product p1 = productIterator.next();
                    Iterator<String> iid = idList.iterator();
                    while (iid.hasNext()) {
                        String id = iid.next();
                        if (p1.getId().intValue() == Integer.valueOf(id).intValue()) {
                            productIterator.remove();
                            break;
                        }
                    }
                }
                int i = 0;
                //这个是防止 条件查询无结果而报错
                if (count > 0 && myProductList.size() < 10) {
                    while (myProductList.size() < 10 && i < productList.size()) {
                        myProductList.add(productList.get(i));
                        ++i;
                    }
                    pageindex += 10;
                    map.put("start", pageindex);
                    //这是，为了：未被选中的商品大于10条时，一页可以显示十条数据
                    if ((count - pageindex) <= 0)
                        countFlag = false;
                } else {
                    countFlag = false;
                }
            }
        } else {
            count = productService.findProductCountByMap(map);
            myProductList = productService.findProductListByMap(map);
        }
        //只查了10条数据出来，排除掉已选中的，一页显示的记录根本就没有10条
        //防止 count - idList.size() =1，而造成页数为0
        if (idList == null) page.setTotalCount(count);
        else page.setTotalCount(count - idList.size());
        request.setAttribute("productList", myProductList);
        //防止查询有数据时，数据条数却为 1
        if (page.getTotalCount() <= 0 && !myProductList.isEmpty()) page.setTotalCount(count);
        if (page.getTotalCount() <= 0) page.setTotalCount(0);
        request.setAttribute("page", page);
        request.setAttribute("clothingTypesList", clothingTypesList);
        if (name != null && name != "") request.setAttribute("name", name);
        if (clothingTypesId != null) request.setAttribute("clothingTypesId", clothingTypesId);
        if (clothing_sex != null) request.setAttribute("clothing_sex", clothing_sex);
        return "pInOutRepository/selectProduct";
    }

    /**
     * 1:把传入的 productId 保存到数据库的单据表中
     * 2：根据单据表中的商品id，查询每个商品信息
     *
     * @param productId
     * @return
     */
    @PostMapping("/selectProductSubmit")
    public String selectProduct(String productId, HttpSession session, HttpServletRequest request) {
        List<String> productIdList = null;
        //查询所有的 商品
        List<Product> productList = productService.findAllProduct();
        //根据bid查询对应的单据
        PInOutRepository pInOutRepository = pInOutRepositoryService.findOnePInOutRepositoryByBid(session.getAttribute("pInOutRepositoryBid").toString());
        //判断是否选了商品
        if (productId != null && !"".equals(productId)) {
            List<String> sl = new ArrayList<String>();
            //初始化quantity、unit_price字符串，格式 0,0,0,0
            List<String> stringList = MySubString.subString(productId, ",");
            //生成与商品同样个数的 0,0,0,0 list，并转为字符串
            for (String s : stringList) {
                sl.add("0");
            }
            //判断 productId 是否为空，为空则直接赋值，不为空则拼接字符串
            String str = OwnSubStringTool.listToString(sl);
            if (pInOutRepository.getProduct_id() == null || pInOutRepository.getProduct_id().equals("")) {
                pInOutRepository.setProduct_id(productId);
                //初始化 quantity、unit_price
                pInOutRepository.setUnit_price(str);
                pInOutRepository.setQuantity(str);
            } else {
                pInOutRepository.setProduct_id(pInOutRepository.getProduct_id() + "," + productId);
                //初始化 quantity、unit_price
                pInOutRepository.setQuantity(pInOutRepository.getQuantity() + "," + str);
                pInOutRepository.setUnit_price(pInOutRepository.getUnit_price() + "," + str);
            }
            //修改所选商品
            int flag = pInOutRepositoryService.updatePInOutRepository(pInOutRepository);
            // 修改出错转到出错界面
            if (flag == 0)
                return "error";
        }
        //显示已选中的商品
        List<Product> p2 = new ArrayList<Product>();
        if (pInOutRepository.getProduct_id() != null) {
            productIdList = MySubString.subString(pInOutRepository.getProduct_id(), ",");
            for (String l : productIdList) {
                for (Product product : productList) {
                    if (product.getId() == Integer.valueOf(l)) p2.add(product);
                }
            }
        }
        //排除未选中商品而报空指针异常
        if (pInOutRepository.getQuantity() != null && !"".equals(pInOutRepository.getQuantity())) {
            //把数量字段，转换为list
            List<String> qs = MySubString.subString(pInOutRepository.getQuantity(), ",");
            //索引
            int index = 0;
            for (Product m : p2) {
                //给前台传个商品数量的值
                m.setQuantity(qs.get(index));
                ++index;
            }
        }
        request.setAttribute("productLength", p2.size());
        request.setAttribute("productList", p2);
        return "pInOutRepository/addPInOutRepositoryOne";
    }

    @GetMapping("/addPInOutRepositoryTwo.html")
    public String addPInOutRepositoryTwo(int flag, HttpServletRequest request) {
        //获取仓库信息，发送过去
        List<Repository> repositoryList = pInOutRepositoryService.findAllRepository();
        List<Customer> customerList = pInOutRepositoryService.findAllCustomer();
        request.setAttribute("repositoryList", repositoryList);
        request.setAttribute("customerList", customerList);
        request.setAttribute("flag", flag);
        return "pInOutRepository/addPInOutRepositoryTwo";
    }

    /**
     * 入库的最终操作
     * 1:计算填充单据中商品对应的 unit_price，total_price
     * 2: 循环新建各个商品流水记录，并填充记录
     * 3：修改，相应商品的数量
     *
     * @param pInOutRepository
     * @param session
     * @return
     */
    @PostMapping("/addPInOutRepository")
    public String addPInOutRepository(PInOutRepository pInOutRepository, HttpSession session) {
        pInOutRepository.setModify_by(((Employee) session.getAttribute("employeeSession")).getId());
        pInOutRepository.setBid((String) session.getAttribute("pInOutRepositoryBid"));
        boolean flag = pInOutRepositoryService.addPInOutRepository(pInOutRepository);
        if (!flag) return "error";
        session.removeAttribute("pInOutRepositoryBid");
        return "success";
    }


    /**
     * 转到管理单据页面，
     * 1：查询出所有的单据，
     *
     * @return
     */
    @GetMapping("/managePInOutRepository.html")
    public String managePInOutRepository(Integer dayTime, String bid, Integer status, Integer repository_id, Integer pageindex, HttpServletRequest request) {
        Page page = new Page(pageindex);
        if (dayTime != null && dayTime == -1) dayTime = null;
        if (status != null && status == 0) status = null;
        if (repository_id != null && repository_id == 0) repository_id = null;
        if (bid != null && bid.equals("")) bid = null;
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("dayTime", dayTime);
        map.put("bid", bid);
        map.put("status", status);
        map.put("repository_id", repository_id);
        map.put("start", page.getSqlSelectPageStart());
        List<Repository> repositoryList = pInOutRepositoryService.findAllRepository();
        List<PInOutRepository> piorList = pInOutRepositoryService.findPiorList(map);
        page.setTotalCount(pInOutRepositoryService.findPiorCount(map));
        request.setAttribute("piorList", piorList);
        request.setAttribute("page", page);
        request.setAttribute("repositoryList", repositoryList);
        if (dayTime != null) request.setAttribute("dayTime", dayTime);
        if (bid != null && !bid.equals("")) request.setAttribute("bid", bid);
        if (status != null) request.setAttribute("status", status);
        if (repository_id != null) request.setAttribute("repository_id", repository_id);
        return "pInOutRepository/managePInOutRepository";
    }

    @GetMapping("/detailRecordOfPIOR.html")
    public String detailRecordOfPIOR(String bid, HttpServletRequest request) {
        List<ProductRecord> productRecordList = pInOutRepositoryService.findProductRecordByPid(bid);
        request.setAttribute("productRecordList", productRecordList);
        return "pInOutRepository/detailRecordOfPIOR";
    }

    /*1：查出单据
     * 2：根据单据内的商品id，循环查询商品，放到一个list中
     * 3：发送商品list、单据 到前端*/
    @GetMapping("/buildPIORList.html")
    public String buildPIORList(String id, HttpServletRequest request) {
        List<Product> productList = new ArrayList<Product>();
        PInOutRepository pInOutRepository = pInOutRepositoryService.findOnePInOutRepositoryByid(id);
        if (pInOutRepository.getStatus() != null && pInOutRepository.getStatus() == 2) {
            pInOutRepository.setCustomer_name(pInOutRepositoryService.findCustomerName(pInOutRepository.getCustomer_id()));
        }
        List<String> stringList = MySubString.subString(pInOutRepository.getProduct_id(), ",");
        List<String> qList = MySubString.subString(pInOutRepository.getQuantity(), ",");
        int index = 0;
        for (String s : stringList) {
            Product product = pInOutRepositoryService.findProductById(Integer.valueOf(s));
            product.setQuantity(qList.get(index));
            productList.add(product);
            ++index;
        }
        request.setAttribute("pInOutRepository", pInOutRepository);
        request.setAttribute("productList", productList);
        return "pInOutRepository/buildPIORList";
    }

    @GetMapping("/updatePIORStatus.html")
    public String updatePIORStatus(Integer id, HttpSession session) {
        Employee employee = (Employee) session.getAttribute("employeeSession");
        Integer flag = pInOutRepositoryService.updatePIORStatus(id, employee.getId());
        if (flag != 1) return "error";
        return "redirect:/managePInOutRepository.html";
    }

    @GetMapping("/viewAlertRP.html")
    public String viewAlertRP(HttpServletRequest request) {
        List<Product> productList = pInOutRepositoryService.viewAlertRP();
        request.setAttribute("productList", productList);
        return "pInOutRepository/viewAlertRP";
    }


    /*-----------------ajax请求------------------------*/
    @PostMapping("/deleteSelectProduct")
    @ResponseBody
    public String deleteSelectProduct(Integer id, HttpSession session) {
        int flag = pInOutRepositoryService.deleteSelectProduct(session.getAttribute("pInOutRepositoryBid").toString(), id);
        if (flag == 1) return JSONArray.toJSONString("success");
        return JSONArray.toJSONString("error");
    }

    @PostMapping("/updateSelectProductQuantity")
    @ResponseBody
    public String updateSelectProductQuantity(String quantity, String id, HttpSession session) {
        //获取 用户id作为修改者，前面的修改请求也要改
        Employee employee = (Employee) session.getAttribute("employeeSession");
        //获取 bid
        PInOutRepository pInOutRepository = new PInOutRepository();
        pInOutRepository.setBid((String) session.getAttribute("pInOutRepositoryBid"));
        pInOutRepository.setModify_by(employee.getId());
        pInOutRepository.setQuantity(quantity);
        pInOutRepository.setProduct_id(id);
        int flag = pInOutRepositoryService.updateSelectProductQuantity(pInOutRepository);
        if (flag == 1) return JSONArray.toJSONString("success");
        return JSONArray.toJSONString("fail");
    }

}
