package com.example.liubiljett;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;

public class VolleyService {
    String baseURL = "http://10.0.2.2:5000/";

    Context mContext;

    public VolleyService(Context mContext) {
        this.mContext = mContext;
    }

//    public void testConn() {
//        RequestQueue queue = Volley.newRequestQueue(mContext);
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, baseURL,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        Log.d("RESP", response);
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.v("Error", String.valueOf(error));
//            }
//
//
//        }) {
//            @Override
//            public String getBodyContentType() {
//                return "application/x-www-form-urlencoded; charset=UTF-8";
//            }
//        };
//
//        queue.add(stringRequest);
//
//    }

    public void createAccount(String name, String email, String password,final VolleyCallback volleyCallback) {
        String createAccountURL = baseURL + "user/register/" + name + "/" + password + "/" + email;


        RequestQueue queue = Volley.newRequestQueue(mContext);
        JsonObjectRequest request = new JsonObjectRequest(JsonObjectRequest.Method.POST,
                createAccountURL, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                           String volleyResponse = response.getString("message");
                            volleyCallback.onSuccess(volleyResponse);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                String responseBody;
                String out;
                try {
                    responseBody = new String(error.networkResponse.data, StandardCharsets.UTF_8);
                    out = new JSONObject(responseBody).getString("message");
                    volleyCallback.onError(out);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }
        );

        queue.add(request);
    }

    public interface VolleyCallback {
        void onSuccess(String result);

        void onError(String result);
    }
}
