package com.i2c.groceryapp.model;

import java.util.ArrayList;

public class Category {
    private String name;

    private String logo;

    private ArrayList<Subcategories_list> subcategories_list;

    private String categorie_id;

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

    public ArrayList<Subcategories_list> getSubcategories_list() {
        return subcategories_list;
    }

    public void setSubcategories_list(ArrayList<Subcategories_list> subcategories_list) {
        this.subcategories_list = subcategories_list;
    }

    public String getCategorie_id() {
        return categorie_id;
    }

    public void setCategorie_id(String categorie_id) {
        this.categorie_id = categorie_id;
    }

    @Override
    public String toString() {
        return "ClassPojo [name = " + name + ", logo = " + logo + ", subcategories_list = " + subcategories_list + ", categorie_id = " + categorie_id + "]";
    }
}
