package com.i2c.groceryapp.ws.interfaces;

import com.android.volley.VolleyError;


public interface VolleyResponseListener {
    void onResponse(Object response, String url, boolean isSuccess, VolleyError error);
}
