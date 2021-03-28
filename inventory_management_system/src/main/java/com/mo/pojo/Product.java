package com.mo.pojo;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Product {
    private Integer id;
    private String pid;
    private String name;
    private Integer clothing_types_id;
    private Integer clothing_sex;
    private String style;
    private String color;
    private BigDecimal unit_price;
    private Integer size;    //尺码(1~7表示XS-S-M-L-XL-XXL次吗)（大于20的数字代表裤子的尺码）
    private String unit;
    private Integer minimum_stock;
    private Integer total_quantity;
    private Integer available_quantity;
    private Integer frozen_quantity;
    private Integer requisite_delivery_quantity;
    private String note;
    private Integer create_by;
    private Timestamp create_time;
    private Integer modify_by;
    private Timestamp modify_time;

    //附
    private String size_name;
    private String clothing_types_name;
    private String clothing_sex_name;

    //判断重要的数据是否空
    public boolean isEmpty() {
        if (pid != null) return false;
        if (name != null) return false;
        if (clothing_types_id != null || clothing_types_id != 0) return false;
        if (style != null) return false;
        if (color != null) return false;
        if (unit_price != null) return false;
        if (size != null) return false;
        if (unit != null) return false;
        if (minimum_stock != null) return false;
        if (note != null) return false;
        if (create_by != null) return false;
        return true;
    }

    public Integer getClothing_sex() {
        return clothing_sex;
    }

    public void setClothing_sex(Integer clothing_sex) {
        this.clothing_sex = clothing_sex;
        switch (clothing_sex) {
            case 1:
                clothing_sex_name = "男装";
                break;
            case 2:
                clothing_sex_name = "女装";
                break;
            case 3:
                clothing_sex_name = "中性(男女都能穿)";
        }
    }

    public String getClothing_sex_name() {
        return clothing_sex_name;
    }

    public void setClothing_sex_name(String clothing_sex_name) {
        this.clothing_sex_name = clothing_sex_name;
    }

    public String getClothing_types_name() {
        return clothing_types_name;
    }

    public void setClothing_types_name(String clothing_types_name) {
        this.clothing_types_name = clothing_types_name;
    }

    public String getSize_name() {
        return size_name;
    }

    public void setSize_name(String size_name) {
        this.size_name = size_name;
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

    public Integer getClothing_types_id() {
        return clothing_types_id;
    }

    public void setClothing_types_id(Integer clothing_types_id) {
        this.clothing_types_id = clothing_types_id;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
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

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
        switch (size) {
            case 1:
                size_name = "XS";
                break;
            case 2:
                size_name = "S";
                break;
            case 3:
                size_name = "M";
                break;
            case 4:
                size_name = "L";
                break;
            case 5:
                size_name = "XL";
                break;
            case 6:
                size_name = "XXL";
                break;
            default:
                size_name = null;
        }
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Integer getMinimum_stock() {
        return minimum_stock;
    }

    public void setMinimum_stock(Integer minimum_stock) {
        this.minimum_stock = minimum_stock;
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

    public Integer getRequisite_delivery_quantity() {
        return requisite_delivery_quantity;
    }

    public void setRequisite_delivery_quantity(Integer requisite_delivery_quantity) {
        this.requisite_delivery_quantity = requisite_delivery_quantity;
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

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", pid='" + pid + '\'' +
                ", name='" + name + '\'' +
                ", clothing_types_id=" + clothing_types_id +
                ", clothing_sex=" + clothing_sex +
                ", style='" + style + '\'' +
                ", color='" + color + '\'' +
                ", unit_price=" + unit_price +
                ", size=" + size +
                ", unit='" + unit + '\'' +
                ", minimum_stock=" + minimum_stock +
                ", total_quantity=" + total_quantity +
                ", available_quantity=" + available_quantity +
                ", frozen_quantity=" + frozen_quantity +
                ", requisite_delivery_quantity=" + requisite_delivery_quantity +
                ", note='" + note + '\'' +
                ", create_by=" + create_by +
                ", create_time=" + create_time +
                ", modify_by=" + modify_by +
                ", modify_time=" + modify_time +
                ", size_name='" + size_name + '\'' +
                ", clothing_types_name='" + clothing_types_name + '\'' +
                ", clothing_sex_name='" + clothing_sex_name + '\'' +
                '}';
    }
}
