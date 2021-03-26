package com.i2c.groceryapp.utils;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.i2c.groceryapp.activity.HomeActivity;
import com.i2c.groceryapp.activity.LoginRegisterActivity;
import com.i2c.groceryapp.activity.MainActivity;
import com.i2c.groceryapp.model.Data;
import com.i2c.groceryapp.model.Todayspecial_list;

import java.lang.reflect.Type;
import java.util.ArrayList;


public class SessionManager {
    private static Editor editor;
    private SharedPreferences sharedPreferences;
    private Context context;
    private static Editor editorSAVELogin;
    private SharedPreferences sharedPreferencesSAVELogin;

    public SessionManager(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(Constant.PREF_NAME, Context.MODE_PRIVATE);
        sharedPreferencesSAVELogin = context.getSharedPreferences(Constant.PREF_NAME_SAVELOGIN, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editorSAVELogin = sharedPreferencesSAVELogin.edit();
        editor.apply();
    }


    public boolean isKeyExist(String key) {
        return sharedPreferences.contains(key);
    }


    public void setStringValue(String key, String value) {
        editor.putString(key, value);
        editor.apply();
        editor.commit();
    }


    public String getStringValue(String key) {
        return sharedPreferences.getString(key, "");
    }


    public boolean getBoolen(String key) {
        return sharedPreferences.getBoolean(key, false);
    }


    public void setBooleanValue(String key, Boolean value) {
        editor.putBoolean(key, value);
        editor.apply();
        editor.commit();
    }

    public int getInteger(String key) {
        return sharedPreferences.getInt(key, 0);
    }


    public void setIntegerValue(String key, Integer value) {
        editor.putInt(key, value);
        editor.apply();
        editor.commit();
    }

    public void checkLogin() {
        if (this.getBoolen(Constant.IS_ALREADY_LOGIN)) {
            Intent login = new Intent(context, HomeActivity.class);
            login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(login);
        } else {
            Intent login = new Intent(context, LoginRegisterActivity.class);
            login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(login);
        }
    }


//    public void saveLoginData(User_data userData) {
//        Gson gson = new Gson();
//        String personString = gson.toJson(userData);
//
//        editor.putString(Constant.LOGIN_USER_DATA, personString);
//        editor.commit();
//    }
//
//    public User_data getLogin() {
//        Gson gson = new Gson();
//        String json = sharedPreferences.getString(Constant.LOGIN_USER_DATA, null);
//
//        Type type = new TypeToken<User_data>() {
//        }.getType();
//        return gson.fromJson(json, type);
//    }

    public void logoutUser() {
        editor.clear();
        editor.apply();
//        checkLogin();
    }

    public void setDeviceToken(String api_token) {
        editorSAVELogin.putString(Constant.DEVICE_TOKEN, api_token);
        editorSAVELogin.apply();
        editorSAVELogin.commit();
    }

    public String getDevice_token() {
        return sharedPreferencesSAVELogin.getString(Constant.DEVICE_TOKEN, "");
    }

    public void saveLoginData(Data data){
        Gson gson = new Gson();
        String getData = gson.toJson(data);
        editor.putString(Constant.LOGIN_USER_DATA, getData);
        editor.commit();
    }

    public Data getLoginData(){
        Gson gson = new Gson();
        String json = sharedPreferences.getString(Constant.LOGIN_USER_DATA, null);
        Type type = new TypeToken<Data>() {
        }.getType();
        return gson.fromJson(json, type);
    }

    /*store service list*/
    public ArrayList<Todayspecial_list> save_today_specialList(ArrayList<Todayspecial_list> homeBanners) {
        Gson gson = new Gson();
        String personString = gson.toJson(homeBanners);
        editor.putString(Constant.TODAY_SPECIAL_LIST, personString);
        editor.commit();
        return homeBanners;
    }

    public ArrayList<Todayspecial_list> get_today_specialList() {
        Gson gson = new Gson();
        String json = sharedPreferences.getString(Constant.TODAY_SPECIAL_LIST, null);
        Type type = new TypeToken<ArrayList<Todayspecial_list>>() {
        }.getType();
        String s = "{" + "data :" + json + "}";
        return gson.fromJson(json, type);
    }


}