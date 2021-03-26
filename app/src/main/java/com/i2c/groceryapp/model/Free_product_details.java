package com.i2c.groceryapp.model;

public class Free_product_details {
    private String image;

    private String thumb_image;

    private String product_id;

    private String name;

    private String mrp_price;

    private String retail_price;

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

    @Override
    public String toString() {
        return "ClassPojo [image = " + image + ", thumb_image = " + thumb_image + ", product_id = " + product_id + ", name = " + name + ", mrp_price = " + mrp_price + ", retail_price = " + retail_price + "]";
    }
}
