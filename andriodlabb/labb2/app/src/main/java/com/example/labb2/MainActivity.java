package com.example.labb2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ListView;

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

    @Override
    public void onItemSelected(ListView l, View v, int position, long id) {
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

