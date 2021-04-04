package com.i2c.groceryapp.model;

public class All_SubCategoryList {
    private String brand_companie_id;

    private String name;

    private String logo;

    public String getBrand_companie_id() {
        return brand_companie_id;
    }

    public void setBrand_companie_id(String brand_companie_id) {
        this.brand_companie_id = brand_companie_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    @Override
    public String toString() {
        return "ClassPojo [brand_companie_id = " + brand_companie_id + ", name = " + name + ", logo = " + logo + "]";
    }
}
