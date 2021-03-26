package com.i2c.groceryapp.model;

public class FavouriteModel {
    private String product_id;

    private String in_cart_qty;

    private String favorite_id;

    private Product_details product_details;

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getIn_cart_qty() {
        return in_cart_qty;
    }

    public void setIn_cart_qty(String in_cart_qty) {
        this.in_cart_qty = in_cart_qty;
    }

    public String getFavorite_id() {
        return favorite_id;
    }

    public void setFavorite_id(String favorite_id) {
        this.favorite_id = favorite_id;
    }

    public Product_details getProduct_details() {
        return product_details;
    }

    public void setProduct_details(Product_details product_details) {
        this.product_details = product_details;
    }

    @Override
    public String toString() {
        return "ClassPojo [product_id = " + product_id + ", in_cart_qty = " + in_cart_qty + ", favorite_id = " + favorite_id + ", product_details = " + product_details + "]";
    }
}
