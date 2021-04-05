package com.i2c.groceryapp.model;

public class ClearCart {
    private String data;

    private String success;

    private String message;

    public String getData() {
        return data;
    }

    public void setData(String data) {
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

    @Override
    public String toString() {
        return "ClassPojo [data = " + data + ", success = " + success + ", message = " + message + "]";
    }
}
