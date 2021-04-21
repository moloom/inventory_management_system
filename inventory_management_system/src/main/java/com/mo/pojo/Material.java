package com.mo.pojo;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Material {
    private Integer id;
    private String pid;
    private String name;
    private String specification;
    private String color;
    private BigDecimal unit_price;
    private String position;
    private Integer total_quantity;
    private Integer available_quantity;
    private Integer frozen_quantity;
    private Integer requisite_delivery_quantity;
    private Integer minimum_stock;
    private String unit;
    private Integer supplier_id;
    private String note;
    private Integer create_by;
    private Timestamp create_time;
    private Integer modify_by;
    private Timestamp modify_time;

    //附
    private String supplier_name;
    private String quantity;
    private Float price;

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getSupplier_name() {
        return supplier_name;
    }

    public void setSupplier_name(String supplier_name) {
        this.supplier_name = supplier_name;
    }

    public Integer getRequisite_delivery_quantity() {
        return requisite_delivery_quantity;
    }

    public void setRequisite_delivery_quantity(Integer requisite_delivery_quantity) {
        this.requisite_delivery_quantity = requisite_delivery_quantity;
    }

    public Integer getMinimum_stock() {
        return minimum_stock;
    }

    public void setMinimum_stock(Integer minimum_stock) {
        this.minimum_stock = minimum_stock;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public BigDecimal getUnit_price() {
        return unit_price;
    }

    public void setUnit_price(BigDecimal unit_price) {
        this.unit_price = unit_price;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Integer getTotal_quantity() {
        return total_quantity;
    }

    public void setTotal_quantity(Integer total_quantity) {
        this.total_quantity = total_quantity;
    }

    public Integer getAvailable_quantity() {
        return available_quantity;
    }

    public void setAvailable_quantity(Integer available_quantity) {
        this.available_quantity = available_quantity;
    }

    public Integer getFrozen_quantity() {
        return frozen_quantity;
    }

    public void setFrozen_quantity(Integer frozen_quantity) {
        this.frozen_quantity = frozen_quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Integer getSupplier_id() {
        return supplier_id;
    }

    public void setSupplier_id(Integer supplier_id) {
        this.supplier_id = supplier_id;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
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

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        if (unit_price != null) {
            price = unit_price.multiply(BigDecimal.valueOf(Integer.valueOf(quantity))).floatValue();
        }
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Material{" +
                "id=" + id +
                ", pid='" + pid + '\'' +
                ", name='" + name + '\'' +
                ", specification='" + specification + '\'' +
                ", color='" + color + '\'' +
                ", unit_price=" + unit_price +
                ", position='" + position + '\'' +
                ", total_quantity=" + total_quantity +
                ", available_quantity=" + available_quantity +
                ", frozen_quantity=" + frozen_quantity +
                ", requisite_delivery_quantity=" + requisite_delivery_quantity +
                ", minimum_stock=" + minimum_stock +
                ", unit='" + unit + '\'' +
                ", supplier_id=" + supplier_id +
                ", note='" + note + '\'' +
                ", create_by=" + create_by +
                ", create_time=" + create_time +
                ", modify_by=" + modify_by +
                ", modify_time=" + modify_time +
                ", quantity='" + quantity + '\'' +
                ", supplier_name='" + supplier_name + '\'' +
                '}';
    }
}
