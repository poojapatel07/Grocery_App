package com.i2c.groceryapp.retrofit.response;
import java.util.ArrayList;

public class ListResponse<T> {

    private ArrayList<T> data;

    private String success;

    private String message;

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
