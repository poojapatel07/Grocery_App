package com.i2c.groceryapp.model;

public class Product_image {
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

    @Override
    public String toString() {
        return "ClassPojo [image = " + image + ", thumb_image = " + thumb_image + "]";
    }
}
