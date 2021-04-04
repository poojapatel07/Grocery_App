package com.i2c.groceryapp.model;

import java.util.ArrayList;

public class Order_product_data {
    private String product_dp;

    private String amount;

    private String margin;

    private String is_free_product;

    private String free_product_name;

    private FreeProductImageBean free_product_image;

    private Product_image product_image;

    private ArrayList<String> created_at;

    private String mrp_price;

    private String free_product_id;

    private String total;

    private String product_id;

    private String qty;

    private String name;

    private String total_free_product_qty;

    private String order_id;

    public String getProduct_dp() {
        return product_dp;
    }

    public void setProduct_dp(String product_dp) {
        this.product_dp = product_dp;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getMargin() {
        return margin;
    }

    public void setMargin(String margin) {
        this.margin = margin;
    }

    public String getIs_free_product() {
        return is_free_product;
    }

    public void setIs_free_product(String is_free_product) {
        this.is_free_product = is_free_product;
    }

    public String getFree_product_name() {
        return free_product_name;
    }

    public void setFree_product_name(String free_product_name) {
        this.free_product_name = free_product_name;
    }

    public FreeProductImageBean getFree_product_image() {
        return free_product_image;
    }

    public void setFree_product_image(FreeProductImageBean free_product_image) {
        this.free_product_image = free_product_image;
    }

    public Product_image getProduct_image() {
        return product_image;
    }

    public void setProduct_image(Product_image product_image) {
        this.product_image = product_image;
    }

    public ArrayList<String> getCreated_at() {
        return created_at;
    }

    public void setCreated_at(ArrayList<String> created_at) {
        this.created_at = created_at;
    }

    public String getMrp_price() {
        return mrp_price;
    }

    public void setMrp_price(String mrp_price) {
        this.mrp_price = mrp_price;
    }

    public String getFree_product_id() {
        return free_product_id;
    }

    public void setFree_product_id(String free_product_id) {
        this.free_product_id = free_product_id;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTotal_free_product_qty() {
        return total_free_product_qty;
    }

    public void setTotal_free_product_qty(String total_free_product_qty) {
        this.total_free_product_qty = total_free_product_qty;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    @Override
    public String toString() {
        return "ClassPojo [product_dp = " + product_dp + ", amount = " + amount + ", margin = " + margin + ", is_free_product = " + is_free_product + ", free_product_name = " + free_product_name + ", free_product_image = " + free_product_image + ", product_image = " + product_image + ", created_at = " + created_at + ", mrp_price = " + mrp_price + ", free_product_id = " + free_product_id + ", total = " + total + ", product_id = " + product_id + ", qty = " + qty + ", name = " + name + ", total_free_product_qty = " + total_free_product_qty + ", order_id = " + order_id + "]";
    }

    public static class FreeProductImageBean {
        /**
         * image : https://silverfoxstudio.in/fresh_and_fine/public/images/products/15739013845dcfd448b2658.jpg
         * thumb_image : https://silverfoxstudio.in/fresh_and_fine/public/images/products/thumbnail/15739013845dcfd448b2658.jpg
         */

        private String image;
        private String thumb_image;

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getThumb_image() {
            return thumb_image;
        }

        public void setThumb_image(String thumb_image) {
            this.thumb_image = thumb_image;
        }
    }

}
