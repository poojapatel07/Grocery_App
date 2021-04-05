package com.i2c.groceryapp.retrofit;

import com.i2c.groceryapp.model.AddUpdateCart;
import com.i2c.groceryapp.model.All_SubCategoryList;
import com.i2c.groceryapp.model.Category;
import com.i2c.groceryapp.model.ClearCart;
import com.i2c.groceryapp.model.Data;
import com.i2c.groceryapp.model.FavUnFavModel;
import com.i2c.groceryapp.model.FavouriteModel;
import com.i2c.groceryapp.model.MyOrderList;
import com.i2c.groceryapp.model.OrderList;
import com.i2c.groceryapp.model.ReviewCartModel;
import com.i2c.groceryapp.model.Subcategories_list;
import com.i2c.groceryapp.model.Todayspecial_list;
import com.i2c.groceryapp.retrofit.response.ListResponse;
import com.i2c.groceryapp.retrofit.response.RestResponse;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

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

    @FormUrlEncoded
    @POST("all_brand_companie_list")
    Call<ListResponse<All_SubCategoryList>> all_brand_companie_list(@Field(RequestParam.API_TOKEN) String api_token);

    @FormUrlEncoded
    @POST("brand_companie_products_list")
    Call<ListResponse<Todayspecial_list>> brand_companie_products_list(@Field(RequestParam.API_TOKEN) String api_token,
                                                                       @Field(RequestParam.BRAND_COMPANY_ID) String brand_companie_id);

    @FormUrlEncoded
    @POST("order_list")
    Call<ListResponse<OrderList>> order_list(@Field(RequestParam.API_TOKEN) String api_token,
                                             @Field(RequestParam.PAGE_NO) String page_no);


    @Multipart
    @POST("update_profile_logo")
    Call<RestResponse<Data>> update_profile_logo(@Part MultipartBody.Part image,
                                                 @Part(RequestParam.API_TOKEN) RequestBody api_token);


    @FormUrlEncoded
    @POST("update_location")
    Call<RestResponse<Data>> update_location(@Field(RequestParam.API_TOKEN) String api_token,
                                             @Field(RequestParam.SHIPPING_ADDRESS) String shipping_address);


    @FormUrlEncoded
    @POST("update_profile")
    Call<RestResponse<Data>> update_profile(@Field(RequestParam.API_TOKEN) String api_token,
                                            @Field(RequestParam.NAME) String name,
                                            @Field(RequestParam.MOBILE) String mobile,
                                            @Field(RequestParam.DATE_OF_BIRTH) String date_of_birth,
                                            @Field(RequestParam.WHATSAPP_NO) String whatsapp_no,
                                            @Field(RequestParam.ANNIVERSARY_DATE) String anniversary_date,
                                            @Field(RequestParam.BILLING_ADDRESS) String billing_address,
                                            @Field(RequestParam.SHIPPING_ADDRESS) String shipping_address);

    @FormUrlEncoded
    @POST("update_password")
    Call<RestResponse<Data>> update_password(@Field(RequestParam.API_TOKEN) String api_token,
                                             @Field(RequestParam.OLD_PASSWORD) String old_pass,
                                             @Field(RequestParam.CHANGE_PASSWORD) String changed_pass,
                                             @Field(RequestParam.CONFIRM_PASSWORD) String confirm_pass);

    @FormUrlEncoded
    @POST("logout")
    Call<RestResponse<String>> logout(@Field(RequestParam.API_TOKEN) String api_token);


    @FormUrlEncoded
    @POST("all_product_list")
    Call<ListResponse<Todayspecial_list>> all_search_product_list(@Field(RequestParam.API_TOKEN) String api_token,
                                                                  @Field(RequestParam.PAGE_NO) String page_no,
                                                                  @Field(RequestParam.SORT_BY) String sort_by,
                                                                  @Field(RequestParam.SEARCH_PRODUCT) String search_product);

    @FormUrlEncoded
    @POST("product_list")
    Call<ListResponse<Todayspecial_list>> product_list(@Field(RequestParam.API_TOKEN) String api_token,
                                                       @Field(RequestParam.PAGE_NO) String page_no,
                                                       @Field(RequestParam.SUBCATEGORY_Id) String subcategory_id,
                                                       @Field(RequestParam.SORT_BY) String sort_by,
                                                       @Field(RequestParam.BRAND_ID) String brand_id,
                                                       @Field(RequestParam.BRAND_COMPANY_ID) String brand_companie_id);


    @FormUrlEncoded
    @POST("clear_cart")
    Call<ClearCart> clear_cart(@Field(RequestParam.API_TOKEN) String api_token);

    @FormUrlEncoded
    @POST("add_order")
    Call<ListResponse<String>> add_order(@Field(RequestParam.API_TOKEN) String api_token,
                                         @Field(RequestParam.TOTAL_AMOUNT) String total_amount,
                                         @Field(RequestParam.COUPON) String coupon,
                                         @Field(RequestParam.DISCOUNT_AMOUNT) String discount_amount,
                                         @Field(RequestParam.GRANT_TOTAL) String grand_total,
                                         @Field(RequestParam.PAYMENT_TYPE) String payment_type,
                                         @Field(RequestParam.PAYMENT_STATUS) String payment_status,
                                         @Field(RequestParam.SHIPPING_ADDRESS) String shipping_address,
                                         @Field(RequestParam.BILLING_ADDRESS) String billing_address);

}
