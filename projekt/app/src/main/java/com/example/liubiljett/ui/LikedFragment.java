package com.example.liubiljett.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.liubiljett.R;
import com.example.liubiljett.User;
import com.google.gson.Gson;

public class LikedFragment extends Fragment {

    private String currentUserString;
    private User currentUser;
    private Gson gson;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_liked, container, false);
        gson = new Gson();
        if (getArguments() != null) {
            currentUserString = getArguments().getString("result");
            currentUser = gson.fromJson(currentUserString, User.class);
            Log.d("liked", currentUser.toString());
        } else {
            Log.d("ERROR", "args null");
        }

        return root;
    }
}
