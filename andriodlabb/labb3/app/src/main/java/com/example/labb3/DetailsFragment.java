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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class DetailsFragment extends Fragment {
    String groupName;
    private View mainView;
    private Button button;
    private DetailsFragment.OnFragmentactionListener goBackToParent;
    private Members members;

    public DetailsFragment() {

    }

    public static DetailsFragment newInstance(String searchedName) {
        DetailsFragment fragment = new DetailsFragment();
        Bundle args = new Bundle();
        args.putString("group", searchedName);
        fragment.setArguments(args);
        return fragment;
    }
    public void fetchData(String groupName) {
        String gruppUrl = "https://tddd80server.herokuapp.com/medlemmar/"+groupName;
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());

        StringRequest stringRequest = new StringRequest(Request.Method.GET, gruppUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                ListView lis = mainView.findViewById(R.id.listData);
                Gson gson = new Gson();
                members = gson.fromJson(response, Members.class);
                final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1, members.getMedlemmar());
                lis.setAdapter(adapter);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(stringRequest);
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
        mainView = inflater.inflate(R.layout.fragment_details, container, false);
        if (getArguments() != null) {
            groupName = getArguments().getString("group");
        }
        fetchData(groupName);
        button = mainView.findViewById(R.id.goBack);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBackToParent.swapback();
            }
        });
        return mainView;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof DetailsFragment.OnFragmentactionListener) {
            goBackToParent = (DetailsFragment.OnFragmentactionListener) context;
        } else {
            throw new ClassCastException(context.toString() + "implement interface");
        }

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        System.out.println("3");
        outState.putString("group",groupName);
    }

    public interface OnFragmentactionListener {
        void swapback();
    }

}
