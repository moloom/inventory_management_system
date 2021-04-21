package com.mo.pojo;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class PInOutRepository {
    private Integer id;
    private String bid;
    private String product_id;
    private String quantity;
    private String unit_price;
    private BigDecimal total_price;
    private Integer customer_id;
    //取货部门
    private String takeDepartment;
    //取货人姓名
    private String takeName;
    private Integer status;
    private Timestamp deliver_date;
    private Integer repository_id;
    private Integer create_by;
    private Timestamp create_time;
    private Integer modify_by;
    private Timestamp modify_time;

    //
    private String create_name;
    private String repository_name;
    private String customer_name;

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getRepository_name() {
        return repository_name;
    }

    public void setRepository_name(String repository_name) {
        this.repository_name = repository_name;
    }

    public String getCreate_name() {
        return create_name;
    }

    public void setCreate_name(String create_name) {
        this.create_name = create_name;
    }

    public String getTakeDepartment() {
        return takeDepartment;
    }

    public void setTakeDepartment(String takeDepartment) {
        this.takeDepartment = takeDepartment;
    }

    public String getTakeName() {
        return takeName;
    }

    public void setTakeName(String takeName) {
        this.takeName = takeName;
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

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
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

    public Integer getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(Integer customer_id) {
        this.customer_id = customer_id;
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
        return "PInOutRepository{" +
                "id=" + id +
                ", bid='" + bid + '\'' +
                ", product_id='" + product_id + '\'' +
                ", quantity='" + quantity + '\'' +
                ", unit_price='" + unit_price + '\'' +
                ", total_price=" + total_price +
                ", customer_id=" + customer_id +
                ", takeDepartment='" + takeDepartment + '\'' +
                ", takeName='" + takeName + '\'' +
                ", status=" + status +
                ", deliver_date=" + deliver_date +
                ", repository_id=" + repository_id +
                ", create_by=" + create_by +
                ", create_time=" + create_time +
                ", modify_by=" + modify_by +
                ", modify_time=" + modify_time +
                ", create_name='" + create_name + '\'' +
                ", repository_name='" + repository_name + '\'' +
                ", customer_name='" + customer_name + '\'' +
                '}';
    }
}
