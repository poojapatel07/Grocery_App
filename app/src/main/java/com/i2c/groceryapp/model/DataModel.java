package com.i2c.groceryapp.model;

public class DataModel {

    String name;
    int Icon;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIcon() {
        return Icon;
    }

    public void setIcon(int icon) {
        Icon = icon;
    }

    public DataModel(String name, int icon) {
        this.name = name;
        Icon = icon;
    }
}
