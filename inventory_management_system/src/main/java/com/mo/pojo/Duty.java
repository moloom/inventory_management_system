package com.mo.pojo;

import java.sql.Timestamp;

public class Duty {
    private Integer id;
    private String name;
    private Integer create_by;
    private Timestamp create_time;

    public Duty() {
    }

    public Duty(Integer id, String name, Integer create_by, Timestamp create_time) {
        this.id = id;
        this.name = name;
        this.create_by = create_by;
        this.create_time = create_time;
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
        return "Duty{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", create_by=" + create_by +
                ", create_time=" + create_time +
                '}';
    }
}
