package com.mo.pojo;

import java.sql.Timestamp;

public class MaterialRecord {
    private Integer id;
    private String iid;
    private Integer mid;
    private Integer status;
    private Integer quantity;
    private Integer create_by;
    private Timestamp create_time;


    //附
    private String material_name;
    private String employee_name;

    public MaterialRecord() {
    }

    public String getEmployee_name() {
        return employee_name;
    }

    public void setEmployee_name(String employee_name) {
        this.employee_name = employee_name;
    }

    public String getMaterial_name() {
        return material_name;
    }

    public void setMaterial_name(String material_name) {
        this.material_name = material_name;
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

    public Integer getMid() {
        return mid;
    }

    public void setMid(Integer mid) {
        this.mid = mid;
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
        return "MaterialRecord{" +
                "id=" + id +
                ", iid='" + iid + '\'' +
                ", mid=" + mid +
                ", status=" + status +
                ", quantity=" + quantity +
                ", create_by=" + create_by +
                ", create_time=" + create_time +
                ", material_name='" + material_name + '\'' +
                ", employee_name='" + employee_name + '\'' +
                '}';
    }
}
