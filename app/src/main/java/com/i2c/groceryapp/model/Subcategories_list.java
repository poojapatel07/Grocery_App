package com.i2c.groceryapp.model;

public class Subcategories_list {
    private String brand_companie_id;

    private String subcategorie_id;

    private String name;

    private String logo;

    private String brand_id;

    public String getBrand_companie_id() {
        return brand_companie_id;
    }

    public void setBrand_companie_id(String brand_companie_id) {
        this.brand_companie_id = brand_companie_id;
    }

    public String getSubcategorie_id() {
        return subcategorie_id;
    }

    public void setSubcategorie_id(String subcategorie_id) {
        this.subcategorie_id = subcategorie_id;
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

    public String getBrand_id() {
        return brand_id;
    }

    public void setBrand_id(String brand_id) {
        this.brand_id = brand_id;
    }

    @Override
    public String toString() {
        return "ClassPojo [brand_companie_id = " + brand_companie_id + ", subcategorie_id = " + subcategorie_id + ", name = " + name + ", logo = " + logo + ", brand_id = " + brand_id + "]";
    }
}

