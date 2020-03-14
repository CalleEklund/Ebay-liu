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
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class DetailsFragment extends Fragment {
    private String groupName;
    private JSONArray resp;
    private TextView textView;
    private Button button;
    private ListFragment.ItemSelectedListener mainParent;
    private DetailsFragment.OnFragmentactionListener goBackToParent;

    public DetailsFragment() {
    }


    private void fetchInfo(String groupName) {

        RequestQueue queue = Volley.newRequestQueue(getActivity());
        String url = "https://tddd80server.herokuapp.com/medlemmar/" + groupName;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    resp = response.getJSONArray("medlemmar");
                    ArrayList<Object> items = new Gson().fromJson(resp.toString(), new TypeToken<List<Object>>() {
                    }.getType());
                    LinkedTreeMap<Object, Object> item;
                    for (int i = 0; i < items.size(); i++) {
                        item = (LinkedTreeMap<Object, Object>) items.get(i);

                        String epost = item.get("epost").toString();
                        String namn = item.get("namn").toString();
                        String svarade;
                        if (item.get("svarade") == null) {
                            svarade = "";
                        } else {
                            svarade = item.get("svarade").toString();
                        }
                        textView.append("\nNamn: " + namn + "\nEpost: " + epost + "\nSvarade: " + svarade);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("E", "Error: " + error);
            }
        });
        queue.add(jsonObjectRequest);

        new JSONArray();

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            groupName = getArguments().getString("group");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_details, container, false);
        textView = rootView.findViewById(R.id.display);

        fetchInfo(groupName);

        button = rootView.findViewById(R.id.goBack);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBackToParent.swapback();
            }
        });
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ListFragment.ItemSelectedListener) {
            mainParent = (ListFragment.ItemSelectedListener) context;
            goBackToParent = (DetailsFragment.OnFragmentactionListener) context;
        } else {
            throw new ClassCastException(context.toString() + "implement interface");
        }

    }

    public interface OnFragmentactionListener {
        void swapback();
    }

}
