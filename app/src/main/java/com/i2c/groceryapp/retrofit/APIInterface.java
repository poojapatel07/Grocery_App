package com.i2c.groceryapp.retrofit;

import com.i2c.groceryapp.model.Data;
import com.i2c.groceryapp.retrofit.response.RestResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface APIInterface {
    @FormUrlEncoded
    @POST("login")
    Call<RestResponse<Data>> Login(@Field(RequestParam.EMAIL) String email,
                                   @Field(RequestParam.PASSWORD) String password,
                                   @Field(RequestParam.DEVICE_TYPE) String device_type,
                                   @Field(RequestParam.DEVICE_TOKEN) String device_token);

    @FormUrlEncoded
    @POST("otp_login")
    Call<RestResponse<Data>> otp_login(@Field(RequestParam.MOBILE) String mobile,
                                       @Field(RequestParam.DEVICE_TYPE) String device_type,
                                       @Field(RequestParam.DEVICE_TOKEN) String device_token);

    @FormUrlEncoded
    @POST("registration")
    Call<RestResponse<Data>> Register(@Field(RequestParam.NAME) String name,
                                      @Field(RequestParam.EMAIL) String email,
                                      @Field(RequestParam.MOBILE) String mobile,
                                      @Field(RequestParam.PASSWORD) String password,
                                      @Field(RequestParam.MOBILE_CODE) String mobile_code,
                                      @Field(RequestParam.USER_TYPE) String user_type,
                                      @Field(RequestParam.CLIENT_ID) String client_id,
                                      @Field(RequestParam.DEVICE_TYPE) String device_type,
                                      @Field(RequestParam.DEVICE_TOKEN) String device_token);

    @FormUrlEncoded
    @POST("otp_verify")
    Call<RestResponse<Data>> otp_verify(@Field(RequestParam.MOBILE) String mobile,
                                        @Field(RequestParam.OTP) String otp);


    @FormUrlEncoded
    @POST("change_password")
    Call<RestResponse<Data>> change_new_password(@Field(RequestParam.MOBILE) String mobile,
                                                 @Field(RequestParam.OTP) String otp,
                                                 @Field(RequestParam.NEW_PASSWORD) String new_password);


}
