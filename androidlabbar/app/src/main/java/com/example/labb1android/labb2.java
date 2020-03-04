package com.example.labb1android;

import android.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ListView;

public class labb2 extends AppCompatActivity implements MyListFragment.ItemSelectedListener {
    private static final String TAG = "labb2";
    private MyListFragment listFragment;
    private FrameLayout frameLayout;
    private MyDetailsFragments detailsFragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_labb2);
        Log.d(TAG, "onCreate: started");

    }

    @Override
    public void onItemSelected(ListView l, View v, int position, long id) {
        frameLayout = (FrameLayout) findViewById(R.id.container);
        detailsFragments = new MyDetailsFragments();
        Bundle args = new Bundle();
        args.putString("groupName",groupName);
        detailsFragments.setArguments(args);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(frameLayout,detailsFragments);
        transaction.addToBackStack(null);
        transaction.commit();

        }
    }






    /*@Override
    public static void onItemSelected(ListView l, View v, int position, long id) {

    }*/
}
