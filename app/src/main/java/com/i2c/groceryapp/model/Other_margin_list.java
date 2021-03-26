package com.i2c.groceryapp.model;

public class Other_margin_list {
    private String product_margin_id;

    private String min_order_qty;

    private String margin;

    private String price;

    private String product_id;

    private String other_cart_qty;

    public String getProduct_margin_id() {
        return product_margin_id;
    }

    public void setProduct_margin_id(String product_margin_id) {
        this.product_margin_id = product_margin_id;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getOther_cart_qty() {
        return other_cart_qty;
    }

    public void setOther_cart_qty(String other_cart_qty) {
        this.other_cart_qty = other_cart_qty;
    }

    @Override
    public String toString() {
        return "ClassPojo [product_margin_id = " + product_margin_id + ", min_order_qty = " + min_order_qty + ", margin = " + margin + ", price = " + price + ", product_id = " + product_id + ", other_cart_qty = " + other_cart_qty + "]";
    }

}
