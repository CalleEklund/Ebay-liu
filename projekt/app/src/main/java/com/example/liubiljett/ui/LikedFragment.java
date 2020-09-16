package com.example.liubiljett.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.liubiljett.Post;
import com.example.liubiljett.R;
import com.example.liubiljett.TestAdapter;
import com.example.liubiljett.User;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;

public class LikedFragment extends Fragment {

    private User currentUser;
    private Gson gson;
    private ListView likedList;
    ArrayList<Post> userLikedPosts;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_liked, container, false);
        gson = new Gson();
        userLikedPosts = new ArrayList<>();

        likedList = root.findViewById(R.id.liked_list);


        if (getArguments() != null) {
            String currentUserString = getArguments().getString("result");
            if (currentUserString != null) {
                currentUser = gson.fromJson(currentUserString, User.class);
            }
        } else {
            Log.d("ERROR", "args null");
        }


        showLikedPosts(currentUser);

        return root;
    }
    public void showLikedPosts(User u){
        userLikedPosts.addAll(u.getLiked_post());
        TestAdapter adapter = new TestAdapter(requireActivity(), userLikedPosts);
        likedList.setAdapter(adapter);
    }
}
