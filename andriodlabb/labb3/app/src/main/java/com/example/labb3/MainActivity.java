package com.example.labb3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements ListFragment.ItemSelectedListener, DetailsFragment.OnFragmentactionListener {

    ListFragment listFragment;
    DetailsFragment detailsFragment;

    int[] details = {3, 4, 5, 6, 7};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listFragment = new ListFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.frameLayout, listFragment).commit();
    }

    public JSONArray fetchInfo(String groupName) {

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://tddd80server.herokuapp.com/medlemmar/" + groupName;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.v("TAG","resp: " + response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("E", "Error: " + error);
            }
        });
        queue.add(jsonObjectRequest);

        return new JSONArray();

    }

    @Override
    public void onItemSelected(ListView l, View v, int position, long id, String groupName) {
        fetchInfo(groupName);
        detailsFragment = new DetailsFragment();
        Bundle args = new Bundle();
        args.putInt("members", details[(int) id]);
        detailsFragment.setArguments(args);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            transaction.replace(R.id.frameLayout, detailsFragment);
        } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            transaction.replace(R.id.detailsFrame, detailsFragment);
        }

        transaction.addToBackStack(null);
        transaction.commit();

    }

    @Override
    public void swapback() {
        if (findViewById(R.id.frameLayout) != null)
            getSupportFragmentManager()
                    .beginTransaction().replace(R.id.frameLayout, listFragment).commit();
    }


}

