package com.example.labb2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity implements ListFragment.ItemSelectedListener {

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

    @Override
    public void onItemSelected(ListView l, View v, int position, long id) {
        //Log.v("TAG", ": " + id);
        detailsFragment = new DetailsFragment();
        Bundle args = new Bundle();
        args.putInt("members", details[(int) id]);
        //args.putString("groupmembers",details[(int)id]);
        detailsFragment.setArguments(args);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.frameLayout, detailsFragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }

}
