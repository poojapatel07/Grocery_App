package com.i2c.groceryapp.retrofit.response;
import com.i2c.groceryapp.model.Last_banner;
import com.i2c.groceryapp.model.Slider_list;
import com.i2c.groceryapp.model.Todayspecial_list;
import java.util.ArrayList;

public class RestResponse<T> {
    private T data;
    private String success;
    private String message;
    private ArrayList<Todayspecial_list> todayspecial_list;
    private ArrayList<Slider_list> Slider_list;
    private Last_banner first_banner;
    private Last_banner last_banner;

    public ArrayList<Todayspecial_list> getTodayspecial_list() {
        return todayspecial_list;
    }

    public void setTodayspecial_list(ArrayList<Todayspecial_list> todayspecial_list) {
        this.todayspecial_list = todayspecial_list;
    }

    public ArrayList<com.i2c.groceryapp.model.Slider_list> getSlider_list() {
        return Slider_list;
    }

    public void setSlider_list(ArrayList<com.i2c.groceryapp.model.Slider_list> slider_list) {
        Slider_list = slider_list;
    }

    public Last_banner getFirst_banner() {
        return first_banner;
    }

    public void setFirst_banner(Last_banner first_banner) {
        this.first_banner = first_banner;
    }

    public Last_banner getLast_banner() {
        return last_banner;
    }

    public void setLast_banner(Last_banner last_banner) {
        this.last_banner = last_banner;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
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
