package com.mo.pojo;

import java.sql.Timestamp;

public class OutRecord {
    private Integer id;
    private Integer oid;
    private Integer pid;
    private Integer status;
    private Integer quantity;
    private Integer create_by;
    private Timestamp create_time;

    public OutRecord() {
    }

    public OutRecord(Integer id, Integer oid, Integer pid, Integer status, Integer quantity, Integer create_by, Timestamp create_time) {
        this.id = id;
        this.oid = oid;
        this.pid = pid;
        this.status = status;
        this.quantity = quantity;
        this.create_by = create_by;
        this.create_time = create_time;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOid() {
        return oid;
    }

    public void setOid(Integer oid) {
        this.oid = oid;
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
        return "OutRecord{" +
                "id=" + id +
                ", oid=" + oid +
                ", pid=" + pid +
                ", status=" + status +
                ", quantity=" + quantity +
                ", create_by=" + create_by +
                ", create_time=" + create_time +
                '}';
    }
}
