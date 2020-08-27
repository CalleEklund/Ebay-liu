package com.example.liubiljett.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.liubiljett.R;
import com.example.liubiljett.RowItem;
import com.example.liubiljett.TestAdapter;

import java.util.ArrayList;
import java.util.Objects;


public class MainPageFragment extends Fragment implements ListFragment.ItemSelectedListener {

    ArrayList<RowItem> rowItems;
    ListFragment listFragment;
    DetailFragment detailFragment;
    String gName;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_mainpage, container, false);
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        View v = root.findViewById(R.id.frame_layout);
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        if (v != null) {
            detailFragment = new DetailFragment();
            listFragment = new ListFragment();
            ft.replace(R.id.frame_layout, listFragment);
            ft.commit();
        }




        return root;
    }


    @Override
    public void onItemSelected(String groupName) {

        gName = groupName;
        if (getView().findViewById(R.id.frame_layout) != null) {
            detailFragment = DetailFragment.newInstance(groupName);
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, detailFragment).addToBackStack(null).commit();
        }
    }


}