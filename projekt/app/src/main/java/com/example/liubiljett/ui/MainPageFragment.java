package com.example.liubiljett.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.liubiljett.Post;
import com.example.liubiljett.R;


public class MainPageFragment extends Fragment implements ListFragment.ItemSelectedListener {

    ListFragment listFragment;
    DetailFragment detailFragment;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_mainpage, container, false);
        super.onCreate(savedInstanceState);
        listFragment = new ListFragment();
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frame_layout, listFragment);
        ft.commit();


        return root;
    }

    @Override
    public void onItemSelected(Post listItem) {
        if (getActivity().findViewById(R.id.frame_layout) != null) {
            detailFragment = DetailFragment.newInstance(listItem);
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, detailFragment).addToBackStack(null).commit();
        }
    }
}