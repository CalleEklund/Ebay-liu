package com.example.liubiljett.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.liubiljett.Post;
import com.example.liubiljett.R;
import com.example.liubiljett.User;
import com.google.gson.Gson;


public class MainPageFragment extends Fragment implements ListFragment.ItemSelectedListener {

    private Gson gson;
    ListFragment listFragment;
    DetailFragment detailFragment;

    User currentUser;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_mainpage, container, false);
        super.onCreate(savedInstanceState);
        gson = new Gson();
        if (getArguments() != null) {
            String currentUserString = getArguments().getString("user");

            currentUser = gson.fromJson(currentUserString, User.class);
        } else {
            Log.d("ERROR", "args null");
        }

        listFragment = ListFragment.newInstance(currentUser);
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frame_layout, listFragment);
        ft.commit();


        return root;
    }

    @Override
    public void onItemSelected(Post listItem, User user) {
        if (getActivity().findViewById(R.id.frame_layout) != null) {
            detailFragment = DetailFragment.newInstance(listItem, user);
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, detailFragment).addToBackStack(null).commit();
        }
    }
}