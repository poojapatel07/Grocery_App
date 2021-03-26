package com.i2c.groceryapp.model;

import java.util.ArrayList;

public class Product_details {

    private ArrayList<Other_margin_list> other_margin_list;

    private String product_dp;

    private String image;

    private String min_order_qty;

    private String margin;

    private Free_product_details free_product_details;

    private String is_free_product;

    private String thumb_image;

    private String pro_qty_for_free;

    private String mrp_price;

    private String retail_price;

    private String min_qty_for_free;

    private String product_id;

    private String name;

    public ArrayList<Other_margin_list> getOther_margin_list() {
        return other_margin_list;
    }

    public void setOther_margin_list(ArrayList<Other_margin_list> other_margin_list) {
        this.other_margin_list = other_margin_list;
    }

    public String getProduct_dp() {
        return product_dp;
    }

    public void setProduct_dp(String product_dp) {
        this.product_dp = product_dp;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getMin_order_qty() {
        return min_order_qty;
    }

    public void setMin_order_qty(String min_order_qty) {
        this.min_order_qty = min_order_qty;
    }

    public String getMargin() {
        return margin;
    }

    public void setMargin(String margin) {
        this.margin = margin;
    }

    public Free_product_details getFree_product_details() {
        return free_product_details;
    }

    public void setFree_product_details(Free_product_details free_product_details) {
        this.free_product_details = free_product_details;
    }

    public String getIs_free_product() {
        return is_free_product;
    }

    public void setIs_free_product(String is_free_product) {
        this.is_free_product = is_free_product;
    }

    public String getThumb_image() {
        return thumb_image;
    }

    public void setThumb_image(String thumb_image) {
        this.thumb_image = thumb_image;
    }

    public String getPro_qty_for_free() {
        return pro_qty_for_free;
    }

    public void setPro_qty_for_free(String pro_qty_for_free) {
        this.pro_qty_for_free = pro_qty_for_free;
    }

    public String getMrp_price() {
        return mrp_price;
    }

    public void setMrp_price(String mrp_price) {
        this.mrp_price = mrp_price;
    }

    public String getRetail_price() {
        return retail_price;
    }

    public void setRetail_price(String retail_price) {
        this.retail_price = retail_price;
    }

    public String getMin_qty_for_free() {
        return min_qty_for_free;
    }

    public void setMin_qty_for_free(String min_qty_for_free) {
        this.min_qty_for_free = min_qty_for_free;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ClassPojo [other_margin_list = " + other_margin_list + ", product_dp = " + product_dp + ", image = " + image + ", min_order_qty = " + min_order_qty + ", margin = " + margin + ", free_product_details = " + free_product_details + ", is_free_product = " + is_free_product + ", thumb_image = " + thumb_image + ", pro_qty_for_free = " + pro_qty_for_free + ", mrp_price = " + mrp_price + ", retail_price = " + retail_price + ", min_qty_for_free = " + min_qty_for_free + ", product_id = " + product_id + ", name = " + name + "]";
    }
}
