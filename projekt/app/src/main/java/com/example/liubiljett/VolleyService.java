package com.example.liubiljett;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class VolleyService {
    String baseURL = "http://10.0.2.2:5000/";

    Context mContext;

    public VolleyService(Context mContext) {
        this.mContext = mContext;
    }


    public void createAccount(String name, String email, String password, final VolleyCallback volleyCallback) {
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
        ) {
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded";
            }
        };

        queue.add(request);
    }

    public void logInUser(String email, String password, final VolleyCallback volleyCallback) {
        String logInUserURL = baseURL + "user/login/" + email + "/" + password;
        RequestQueue queue = Volley.newRequestQueue(mContext);
        JsonObjectRequest request = new JsonObjectRequest(JsonObjectRequest.Method.POST, logInUserURL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String volleyResponse = response.getString("access_token");
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
                responseBody = new String(error.networkResponse.data, StandardCharsets.UTF_8);
                try {
                    out = new JSONObject(responseBody).getString("Error");
                    volleyCallback.onError(out);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded";
            }
        };
        queue.add(request);
    }

    public void getCurrentUser(final String accessToken, final VolleyCallback volleyCallback) {
        String logInUserURL = baseURL + "user/current";
        RequestQueue queue = Volley.newRequestQueue(mContext);
        JsonObjectRequest request = new JsonObjectRequest(JsonObjectRequest.Method.POST, logInUserURL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                String volleyResponse = response.toString();
                volleyCallback.onSuccess(volleyResponse);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String responseBody;
                String out;
                responseBody = new String(error.networkResponse.data, StandardCharsets.UTF_8);
                try {
                    out = new JSONObject(responseBody).getString("msg");
                    volleyCallback.onError(out);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "Bearer " + accessToken);
                return params;
            }


        };

        queue.add(request);
    }

    public void uploadPost(final String accessToken, Post post, final VolleyCallback volleyCallback) {
        String postTitle = post.getTitle();
        String postPrice = post.getPrice();
        String postDescription = post.getDesc();
        String addPostURL = baseURL + "user/createpost/" + postTitle + "/" + postPrice + "/" + postDescription;

        RequestQueue queue = Volley.newRequestQueue(mContext);
        JsonObjectRequest request = new JsonObjectRequest(JsonObjectRequest.Method.POST, addPostURL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                String volleyResponse = null;
                try {
                    volleyResponse = response.getString("message");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                volleyCallback.onSuccess(volleyResponse);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String responseBody;
                String out;
                responseBody = new String(error.networkResponse.data, StandardCharsets.UTF_8);
                try {
                    out = new JSONObject(responseBody).getString("Error");
                    volleyCallback.onError(out);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "Bearer " + accessToken);
                return params;
            }


        };

        queue.add(request);

    }


    public void getAllPosts(final VolleyCallback volleyCallback) {
        String allPostURL = baseURL + "post/all";
        RequestQueue queue = Volley.newRequestQueue(mContext);
        JsonObjectRequest request = new JsonObjectRequest(JsonObjectRequest.Method.POST, allPostURL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                String volleyResponse = null;
                try {
                    volleyResponse = response.getString("all");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                volleyCallback.onSuccess(volleyResponse);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String responseBody;
                String out;
                responseBody = new String(error.networkResponse.data, StandardCharsets.UTF_8);
                try {
                    out = new JSONObject(responseBody).getString("Error");
                    volleyCallback.onError(out);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
        queue.add(request);
    }

    public interface VolleyCallback {
        void onSuccess(String result);

        void onError(String result);
    }
}



