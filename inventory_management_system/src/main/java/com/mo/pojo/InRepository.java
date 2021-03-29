package com.mo.pojo;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class InRepository {

    private Integer id;
    private String bid;
    private String material_id;
    private String quantity;
    private String unit_price;
    private BigDecimal total_price;
    private String supplier_id;

    private Integer status;
    //1:正常入库，2:正常出库，3:退货（退货动作发生时，数量取它的相反数，并加减物料的总数量和可用数量）
    private Timestamp deliver_date;//这个可以删掉。
    private Integer repository_id;
    private Integer create_by;
    private Timestamp create_time;
    private Integer modify_by;
    private Timestamp modify_time;

    //取货部门
    private String takeDepatement;
    //取货人姓名
    private String takeName;


    /*物料出入库的时候，在页面中放一个下拉框，选择出库、入库，根据选择，增加或者隐藏不相干的输入框*/

    public InRepository() {
    }

    public InRepository(Integer id, String bid, String material_id, String quantity, String unit_price, BigDecimal total_price, String supplier_id, Integer status, Timestamp deliver_date, Integer repository_id, Integer create_by, Timestamp create_time, Integer modify_by, Timestamp modify_time) {
        this.id = id;
        this.bid = bid;
        this.material_id = material_id;
        this.quantity = quantity;
        this.unit_price = unit_price;
        this.total_price = total_price;
        this.supplier_id = supplier_id;
        this.status = status;
        this.deliver_date = deliver_date;
        this.repository_id = repository_id;
        this.create_by = create_by;
        this.create_time = create_time;
        this.modify_by = modify_by;
        this.modify_time = modify_time;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBid() {
        return bid;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }

    public String getMaterial_id() {
        return material_id;
    }

    public void setMaterial_id(String material_id) {
        this.material_id = material_id;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getUnit_price() {
        return unit_price;
    }

    public void setUnit_price(String unit_price) {
        this.unit_price = unit_price;
    }

    public BigDecimal getTotal_price() {
        return total_price;
    }

    public void setTotal_price(BigDecimal total_price) {
        this.total_price = total_price;
    }

    public String getSupplier_id() {
        return supplier_id;
    }

    public void setSupplier_id(String supplier_id) {
        this.supplier_id = supplier_id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Timestamp getDeliver_date() {
        return deliver_date;
    }

    public void setDeliver_date(Timestamp deliver_date) {
        this.deliver_date = deliver_date;
    }

    public Integer getRepository_id() {
        return repository_id;
    }

    public void setRepository_id(Integer repository_id) {
        this.repository_id = repository_id;
    }

    public Integer getCreate_by() {
        return create_by;
    }

    public void setCreate_by(Integer create_by) {
        this.create_by = create_by;
    }

    public Timestamp getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Timestamp create_time) {
        this.create_time = create_time;
    }

    public Integer getModify_by() {
        return modify_by;
    }

    public void setModify_by(Integer modify_by) {
        this.modify_by = modify_by;
    }

    public Timestamp getModify_time() {
        return modify_time;
    }

    public void setModify_time(Timestamp modify_time) {
        this.modify_time = modify_time;
    }

    @Override
    public String toString() {
        return "InRepository{" +
                "id=" + id +
                ", bid=" + bid +
                ", material_id='" + material_id + '\'' +
                ", quantity='" + quantity + '\'' +
                ", unit_price='" + unit_price + '\'' +
                ", total_price=" + total_price +
                ", supplier_id='" + supplier_id + '\'' +
                ", status=" + status +
                ", deliver_date=" + deliver_date +
                ", repository_id=" + repository_id +
                ", create_by=" + create_by +
                ", create_time=" + create_time +
                ", modify_by=" + modify_by +
                ", modify_time=" + modify_time +
                '}';
    }
}
