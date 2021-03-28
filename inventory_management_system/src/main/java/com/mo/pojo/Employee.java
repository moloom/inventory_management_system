package com.mo.pojo;

import java.sql.Timestamp;

public class Employee {
    private Integer id;
    private String uid;
    private String password;
    private String name;
    private Integer sex;
    private Integer duty_id;
    private Integer rights;
    private Timestamp birthday;
    private String telephone;
    private Integer create_by;
    private Timestamp create_time;
    private Integer modify_by;
    private Timestamp modify_time;

    //附
    private String duty_name;


    public Employee() {
    }

    public Employee(Integer id, String uid, String password, String name, Integer sex, Integer duty_id, Integer rights, Timestamp birthday, String telephone, Integer create_by, Timestamp create_time, Integer modify_by, Timestamp modify_time, String duty_name) {
        this.id = id;
        this.uid = uid;
        this.password = password;
        this.name = name;
        this.sex = sex;
        this.duty_id = duty_id;
        this.rights = rights;
        this.birthday = birthday;
        this.telephone = telephone;
        this.create_by = create_by;
        this.create_time = create_time;
        this.modify_by = modify_by;
        this.modify_time = modify_time;
        this.duty_name = duty_name;
    }

    public Timestamp getBirthday() {
        return birthday;
    }

    public void setBirthday(Timestamp birthday) {
        this.birthday = birthday;
    }

    public String getDuty_name() {
        return duty_name;
    }

    public void setDuty_name(String duty_name) {
        this.duty_name = duty_name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getDuty_id() {
        return duty_id;
    }

    public void setDuty_id(Integer duty_id) {
        this.duty_id = duty_id;
    }

    public Integer getRights() {
        return rights;
    }

    public void setRights(Integer rights) {
        this.rights = rights;
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
        return "Employee{" +
                "id=" + id +
                ", uid=" + uid +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", sex=" + sex +
                ", duty_id=" + duty_id +
                ", rights=" + rights +
                ", birthday=" + birthday +
                ", telephone='" + telephone + '\'' +
                ", create_by=" + create_by +
                ", create_time=" + create_time +
                ", modify_by=" + modify_by +
                ", modify_time=" + modify_time +
                ", duty_name='" + duty_name + '\'' +
                '}';
    }
}
