package com.i2c.groceryapp.retrofit.response;
import com.i2c.groceryapp.model.Brand_companies_list;
import com.i2c.groceryapp.model.Brand_list;

import java.util.ArrayList;

public class ListResponse<T> {

    private ArrayList<T> data;

    private String success;

    private String message;

    private ArrayList<Brand_companies_list>brand_companies_list;

    private ArrayList<Brand_list>brand_list;

    public ArrayList<Brand_companies_list> getBrand_companies_list() {
        return brand_companies_list;
    }

    public void setBrand_companies_list(ArrayList<Brand_companies_list> brand_companies_list) {
        this.brand_companies_list = brand_companies_list;
    }

    public ArrayList<Brand_list> getBrand_list() {
        return brand_list;
    }

    public void setBrand_list(ArrayList<Brand_list> brand_list) {
        this.brand_list = brand_list;
    }

    public ArrayList<T> getData() {
        return data;
    }

    public void setData(ArrayList<T> data) {
        this.data = data;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
