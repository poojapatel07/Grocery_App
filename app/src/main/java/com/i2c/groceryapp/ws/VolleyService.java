package com.i2c.groceryapp.ws;

import android.util.Base64;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.i2c.groceryapp.ws.controllers.ApplicationClass;
import com.i2c.groceryapp.ws.interfaces.VolleyResponseListener;

import java.util.HashMap;
import java.util.Map;


public class VolleyService {
    private static final String TAG = "Response";



    public static <T> void PostMethod(final String url, final Class<T> modelClass,
                                      final HashMap<String, String> headers,
                                      final VolleyResponseListener listener) {

//        Log.e(TAG, "URL: " + url);
        // Initialize a new StringRequest
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.e(TAG, "onResponse: " + response);

                        GsonBuilder gsonBuilder = new GsonBuilder();
                        Gson gson = gsonBuilder.create();
                        T responseObject = gson.fromJson(response, modelClass);
                        listener.onResponse(responseObject, url, true, null);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "onErrorResponse: EROR 1:::"+error.getMessage() );
                        Log.e(TAG, "onErrorResponse: EROR 2:::"+error.toString() );
                        Log.e(TAG, "onErrorResponse: EROR 3:::"+error.networkResponse.toString() );
                        Log.e(TAG, "onErrorResponse: EROR 4:::"+error.getLocalizedMessage());
                        listener.onResponse(null, url, false, error);
                    }
                }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();

                String UserName = "admin";
                String Password = ":admin";

                String credentials = UserName + Password;

                String auth = "Basic "+ Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
                headers.put("Authorization", auth);
                headers.put("Content-Type", "application/x-www-form-urlencoded");
                return headers;
            }


        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        // Access the RequestQueue through singleton class.
        ApplicationClass.getInstance().addToRequestQueue(stringRequest);
    }




}