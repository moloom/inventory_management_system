package com.mo.pojo;

import java.sql.Timestamp;

public class Repository {
    private Integer id;
    private String name;
    private Integer manager_id;
    private String address;
    private String telephone;
    private Integer create_by;
    private Timestamp create_time;
    private Integer modify_by;
    private Timestamp modify_time;

    //附
    private String manager_name;

    public Repository() {
    }

    public Repository(Integer id, String name, Integer manager_id, String address, String telephone, Integer create_by, Timestamp create_time, Integer modify_by, Timestamp modify_time) {
        this.id = id;
        this.name = name;
        this.manager_id = manager_id;
        this.address = address;
        this.telephone = telephone;
        this.create_by = create_by;
        this.create_time = create_time;
        this.modify_by = modify_by;
        this.modify_time = modify_time;
    }

    public String getManager_name() {
        return manager_name;
    }

    public void setManager_name(String manager_name) {
        this.manager_name = manager_name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getManager_id() {
        return manager_id;
    }

    public void setManager_id(Integer manager_id) {
        this.manager_id = manager_id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
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
        return "Repository{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", manager_id=" + manager_id +
                ", address='" + address + '\'' +
                ", telephone='" + telephone + '\'' +
                ", create_by=" + create_by +
                ", create_time=" + create_time +
                ", modify_by=" + modify_by +
                ", modify_time=" + modify_time +
                ", manager_name='" + manager_name + '\'' +
                '}';
    }
}
