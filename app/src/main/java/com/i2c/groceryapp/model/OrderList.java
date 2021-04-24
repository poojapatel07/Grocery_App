package com.i2c.groceryapp.model;

import java.util.ArrayList;

public class OrderList {
    private String order_no;

    private String coupon;

    private String payment_status;

    private String discount;

    private String created_at;

    private String billing_address;

    private ArrayList<Order_product_data> order_product_data;

    private String payment_type;

    private String total_amount;

    private ArrayList<Order_status_data> order_status_data;

    private String product_qty;

    private String grand_total;

    private String shipping_address;

    private String order_id;

    private String status;

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public String getCoupon() {
        return coupon;
    }

    public void setCoupon(String coupon) {
        this.coupon = coupon;
    }

    public String getPayment_status() {
        return payment_status;
    }

    public void setPayment_status(String payment_status) {
        this.payment_status = payment_status;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }



    public String getBilling_address() {
        return billing_address;
    }

    public void setBilling_address(String billing_address) {
        this.billing_address = billing_address;
    }

    public ArrayList<Order_product_data> getOrder_product_data() {
        return order_product_data;
    }

    public void setOrder_product_data(ArrayList<Order_product_data> order_product_data) {
        this.order_product_data = order_product_data;
    }

    public String getPayment_type() {
        return payment_type;
    }

    public void setPayment_type(String payment_type) {
        this.payment_type = payment_type;
    }

    public String getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(String total_amount) {
        this.total_amount = total_amount;
    }


    public ArrayList<Order_status_data> getOrder_status_data() {
        return order_status_data;
    }

    public void setOrder_status_data(ArrayList<Order_status_data> order_status_data) {
        this.order_status_data = order_status_data;
    }

    public String getProduct_qty() {
        return product_qty;
    }

    public void setProduct_qty(String product_qty) {
        this.product_qty = product_qty;
    }

    public String getGrand_total() {
        return grand_total;
    }

    public void setGrand_total(String grand_total) {
        this.grand_total = grand_total;
    }

    public String getShipping_address() {
        return shipping_address;
    }

    public void setShipping_address(String shipping_address) {
        this.shipping_address = shipping_address;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ClassPojo [order_no = " + order_no + ", coupon = " + coupon + ", payment_status = " + payment_status + ", discount = " + discount + ", created_at = " + created_at + ", billing_address = " + billing_address + ", order_product_data = " + order_product_data + ", payment_type = " + payment_type + ", total_amount = " + total_amount + ", order_status_data = " + order_status_data + ", product_qty = " + product_qty + ", grand_total = " + grand_total + ", shipping_address = " + shipping_address + ", order_id = " + order_id + ", status = " + status + "]";
    }
}
