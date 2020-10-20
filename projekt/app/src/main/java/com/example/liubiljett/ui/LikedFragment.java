package com.example.liubiljett.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.liubiljett.R;
import com.example.liubiljett.classes.Post;
import com.example.liubiljett.classes.User;
import com.example.liubiljett.handlers.FeedAdapter;
import com.example.liubiljett.handlers.VolleyService;
import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * Class used for display a user's liked posts
 */
public class LikedFragment extends Fragment {

    private User currentUser;
    private ListView likedList;
    ArrayList<Post> userLikedPosts;
    private VolleyService volleyService;
    private Gson gson;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_liked, container, false);
        gson = new Gson();
        userLikedPosts = new ArrayList<>();
        volleyService = new VolleyService(getContext());
        likedList = root.findViewById(R.id.liked_list);


        if (getArguments() != null) {
            String currentUserString = getArguments().getString("result");
            if (currentUserString != null) {
                currentUser = gson.fromJson(currentUserString, User.class);
            }
        } else {
            Log.d("ERROR", "args null");
        }
        showLikedPosts();



        return root;
    }

    /**
     * Gets the current user's liked posts, and inputs them into the FeedAdapter
     */
    public void showLikedPosts(){
        volleyService.getCurrentUser(currentUser.getAccessToken(), new VolleyService.VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                currentUser = gson.fromJson(result, User.class);
                userLikedPosts.addAll(currentUser.getLiked_post());
                FeedAdapter adapter = new FeedAdapter(requireActivity(), userLikedPosts);
                likedList.setAdapter(adapter);
            }

            @Override
            public void onError(String result) {

            }
        });

    }
}
