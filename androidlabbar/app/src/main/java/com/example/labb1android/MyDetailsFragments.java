package com.example.labb1android;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MyDetailsFragments extends Fragment {
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MyListFragment.ItemSelectedListener) {
            MyListFragment.ItemSelectedListener myContext = (MyListFragment.ItemSelectedListener) context;
        } else {
            throw new ClassCastException(context.toString() + " must implement");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        String groupName = savedInstanceState.getString("groupName");
        View rootView = inflater.inflate(R.layout.activity_labb2, container, false);
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
