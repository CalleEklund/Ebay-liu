package com.example.labb3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
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

import java.util.List;

public class MainActivity extends AppCompatActivity implements ListFragment.ItemSelectedListener, DetailsFragment.OnFragmentactionListener {

    ListFragment listFragment;
    DetailsFragment detailsFragment;
    String gName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        View v = findViewById(R.id.frameLayout);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (v != null) {
            detailsFragment = new DetailsFragment();
            listFragment = new ListFragment();
            ft.replace(R.id.frameLayout, listFragment);
            ft.commit();
        } else {
            listFragment = (ListFragment) getSupportFragmentManager().findFragmentById(R.id.listFragment);
            detailsFragment = (DetailsFragment) getSupportFragmentManager().findFragmentById(R.id.detailsFragemnt);
            System.out.println(savedInstanceState);
            if (savedInstanceState != null) {
                System.out.println(savedInstanceState.getString("group"));
            }
        }

    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("group", gName);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        gName = savedInstanceState.getString("group");
        if (findViewById(R.id.frameLayout) == null) {
            detailsFragment.fetchData(gName);
        }

    }


    @Override
    public void onItemSelected(String groupName) {
        gName = groupName;
        if (findViewById(R.id.frameLayout) != null) {
            detailsFragment = DetailsFragment.newInstance(groupName);
            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, detailsFragment).addToBackStack(null).commit();
        } else {
            detailsFragment.fetchData(groupName);
        }


    }

    @Override
    public void swapback() {
        if (findViewById(R.id.frameLayout) != null)
            getSupportFragmentManager()
                    .beginTransaction().replace(R.id.frameLayout, listFragment).commit();
    }


}

