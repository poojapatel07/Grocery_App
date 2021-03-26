package com.i2c.groceryapp.model;

public class ReviewCartModel {

    private String product_margin_id;

    private String cart_id;

    private String is_favorite;

    private String product_id;

    private String qty;

    private Product_details product_details;

    public String getProduct_margin_id() {
        return product_margin_id;
    }

    public void setProduct_margin_id(String product_margin_id) {
        this.product_margin_id = product_margin_id;
    }

    public String getCart_id() {
        return cart_id;
    }

    public void setCart_id(String cart_id) {
        this.cart_id = cart_id;
    }

    public String getIs_favorite() {
        return is_favorite;
    }

    public void setIs_favorite(String is_favorite) {
        this.is_favorite = is_favorite;
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

    public Product_details getProduct_details() {
        return product_details;
    }

    public void setProduct_details(Product_details product_details) {
        this.product_details = product_details;
    }

    @Override
    public String toString() {
        return "ClassPojo [product_margin_id = " + product_margin_id + ", cart_id = " + cart_id + ", is_favorite = " + is_favorite + ", product_id = " + product_id + ", qty = " + qty + ", product_details = " + product_details + "]";
    }
}
