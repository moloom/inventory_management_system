package com.mo.pojo;

import java.sql.Timestamp;

public class ClothingTypes {
    private Integer id;
    private String name;
    private Integer create_by;
    private Timestamp create_time;

    //
    private String create_name;


    public String getCreate_name() {
        return create_name;
    }

    public void setCreate_name(String create_name) {
        this.create_name = create_name;
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
        return "ClothingTypes{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", create_by=" + create_by +
                ", create_time=" + create_time +
                ", create_name='" + create_name + '\'' +
                '}';
    }
}
