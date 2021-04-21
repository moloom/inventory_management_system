package com.mo.pojo;

import java.sql.Timestamp;

public class ProductRecord {
    private Integer id;
    private String iid;
    private Integer pid;
    private Integer status;
    private Integer quantity;
    private Integer create_by;
    private Timestamp create_time;

    //附
    private String product_name;
    private String product_id;
    private String create_name;


    public String getCreate_name() {
        return create_name;
    }

    public void setCreate_name(String create_name) {
        this.create_name = create_name;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIid() {
        return iid;
    }

    public void setIid(String iid) {
        this.iid = iid;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
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

    @Override
    public String toString() {
        return "ProductRecord{" +
                "id=" + id +
                ", iid='" + iid + '\'' +
                ", pid=" + pid +
                ", status=" + status +
                ", quantity=" + quantity +
                ", create_by=" + create_by +
                ", create_time=" + create_time +
                ", product_name='" + product_name + '\'' +
                ", product_id='" + product_id + '\'' +
                ", create_name='" + create_name + '\'' +
                '}';
    }
}
