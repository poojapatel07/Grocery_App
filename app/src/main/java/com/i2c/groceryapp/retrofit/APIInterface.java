package com.i2c.groceryapp.retrofit;

import com.i2c.groceryapp.model.AddUpdateCart;
import com.i2c.groceryapp.model.Category;
import com.i2c.groceryapp.model.Data;
import com.i2c.groceryapp.model.FavUnFavModel;
import com.i2c.groceryapp.model.FavouriteModel;
import com.i2c.groceryapp.model.ReviewCartModel;
import com.i2c.groceryapp.model.Subcategories_list;
import com.i2c.groceryapp.model.Todayspecial_list;
import com.i2c.groceryapp.retrofit.response.ListResponse;
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

    @FormUrlEncoded
    @POST("home_list")
    Call<RestResponse<Todayspecial_list>> homeList(@Field(RequestParam.API_TOKEN) String api_token);


    @FormUrlEncoded
    @POST("add_cart")
    Call<AddUpdateCart> addToCart(@Field(RequestParam.API_TOKEN) String api_token,
                                  @Field(RequestParam.PRODUCT_ID) String product_id,
                                  @Field(RequestParam.QTY) String qty,
                                  @Field(RequestParam.PRODUCT_MARGIN_ID) String product_margin_id);

    @FormUrlEncoded
    @POST("update_cart")
    Call<AddUpdateCart> updateToCart(@Field(RequestParam.API_TOKEN) String api_token,
                                     @Field(RequestParam.PRODUCT_ID) String product_id,
                                     @Field(RequestParam.QTY) String qty,
                                     @Field(RequestParam.PRODUCT_MARGIN_ID) String product_margin_id);

    @FormUrlEncoded
    @POST("add_favourite")
    Call<FavUnFavModel> add_favourite(@Field(RequestParam.API_TOKEN) String api_token,
                                      @Field(RequestParam.PRODUCT_ID) String product_id);

    @FormUrlEncoded
    @POST("update_favourite")
    Call<FavUnFavModel> remove_favourite(@Field(RequestParam.API_TOKEN) String api_token,
                                         @Field(RequestParam.PRODUCT_ID) String product_id);

    @FormUrlEncoded
    @POST("categories_list")
    Call<ListResponse<Category>> categories_list(@Field(RequestParam.API_TOKEN) String api_token);


    @FormUrlEncoded
    @POST("subcategories_list")
    Call<ListResponse<Subcategories_list>> subcategories_list(@Field(RequestParam.API_TOKEN) String api_token,
                                                              @Field(RequestParam.CATEGORY_ID) String category_id);


    @FormUrlEncoded
    @POST("favourite_list")
    Call<ListResponse<FavouriteModel>> favourite_list(@Field(RequestParam.API_TOKEN) String api_token);

    @FormUrlEncoded
    @POST("freebies_product_list")
    Call<ListResponse<Todayspecial_list>> freebies_product_list(@Field(RequestParam.API_TOKEN) String api_token,
                                                                @Field(RequestParam.PAGE_NO) String page_no);


    @FormUrlEncoded
    @POST("trade_product_list")
    Call<ListResponse<Todayspecial_list>> trade_product_list(@Field(RequestParam.API_TOKEN) String api_token,
                                                                @Field(RequestParam.PAGE_NO) String page_no);


    @FormUrlEncoded
    @POST("cart_list")
    Call<ListResponse<ReviewCartModel>> cart_list(@Field(RequestParam.API_TOKEN) String api_token);



}
