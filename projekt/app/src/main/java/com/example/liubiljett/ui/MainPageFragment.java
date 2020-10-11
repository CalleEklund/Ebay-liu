package com.example.liubiljett.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.liubiljett.classes.Post;
import com.example.liubiljett.R;
import com.example.liubiljett.classes.User;
import com.google.gson.Gson;


/**
 * Frame class for switching between ListFragment and DetailFragment
 */
public class MainPageFragment extends Fragment implements ListFragment.ItemSelectedListener {

    ListFragment listFragment;
    DetailFragment detailFragment;
    User currentUser;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_mainpage, container, false);
        super.onCreate(savedInstanceState);
        Gson gson = new Gson();
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

    /**
     * Send the user to DetailFragment with the clicked posts data
     * @param listItem the clicked post item
     * @param user current user
     */
    @Override
    public void onItemSelected(Post listItem, User user) {
        if (getActivity().findViewById(R.id.frame_layout) != null) {
            detailFragment = DetailFragment.newInstance(listItem, user);
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, detailFragment).addToBackStack(null).commit();
        }
    }
}