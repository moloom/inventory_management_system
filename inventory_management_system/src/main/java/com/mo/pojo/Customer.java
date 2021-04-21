package com.mo.pojo;

import java.sql.Timestamp;

public class Customer {
    private Integer id;
    private String name;
    private String contact;
    private String telephone;
    private String address;
    private Integer create_by;
    private Timestamp create_time;
    private Integer modify_by;
    private Timestamp modify_time;

    public Customer() {
    }

    public Customer(Integer id, String name, String contact, String telephone, String address, Integer create_by, Timestamp create_time, Integer modify_by, Timestamp modify_time) {
        this.id = id;
        this.name = name;
        this.contact = contact;
        this.telephone = telephone;
        this.address = address;
        this.create_by = create_by;
        this.create_time = create_time;
        this.modify_by = modify_by;
        this.modify_time = modify_time;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
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
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", contact='" + contact + '\'' +
                ", telephone='" + telephone + '\'' +
                ", address='" + address + '\'' +
                ", create_by=" + create_by +
                ", create_time=" + create_time +
                ", modify_by=" + modify_by +
                ", modify_time=" + modify_time +
                '}';
    }
}
