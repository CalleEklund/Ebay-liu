package com.example.labb3;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Lägg in en listview som håller listan samt ett fragment som håller details
 */
public class ListFragment extends Fragment {

    //String[] items = {"Japan", "Sverige", "Kina", "Norge", "Dannmark"};
    JSONArray itemsJson;
    // ArrayList<String> items = new ArrayList<>();
    ItemSelectedListener mainParent;
    Context parent;
    TextView textView;
    ListView listView;
    ArrayAdapter<String> listViewAdapter;

    public ListFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list, container, false);

        textView = rootView.findViewById(R.id.idTextView);
        listView = rootView.findViewById(R.id.idListVIew);
        fetchData();

        return rootView;
    }

    private void fetchData() {
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        String url = "https://tddd80server.herokuapp.com/grupper";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    itemsJson = response.getJSONArray("grupper");
                    ArrayList<String> items = new Gson().fromJson(itemsJson.toString(), new TypeToken<List<String>>() {
                    }.getType());
                    listViewAdapter = new ArrayAdapter<>(
                            getActivity(),
                            R.layout.support_simple_spinner_dropdown_item,
                            items
                    );


                    listView.setAdapter(listViewAdapter);

                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            mainParent.onItemSelected(listView, view, position, id);
                        }
                    });
                    //Log.v("TAG", "response:" + itemsJson);

                    //textView.setText(response.getString("grupper"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //textView.setText("Response: " + response.toString());

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                textView.setText("That didnt work");
            }
        });

        queue.add(jsonObjectRequest);


    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof ItemSelectedListener) {
            mainParent = (ItemSelectedListener) context;
            parent = context;
        } else {
            throw new ClassCastException(context.toString() + "implement interface");
        }


    }

    public interface ItemSelectedListener {
        void onItemSelected(ListView l, View v, int position, long id);
    }

}
