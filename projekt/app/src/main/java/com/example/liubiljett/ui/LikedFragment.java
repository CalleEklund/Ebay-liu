package com.example.liubiljett.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.liubiljett.classes.Post;
import com.example.liubiljett.R;
import com.example.liubiljett.handlers.FeedAdapter;
import com.example.liubiljett.classes.User;
import com.google.gson.Gson;

import java.util.ArrayList;

public class LikedFragment extends Fragment {

    private User currentUser;
    private ListView likedList;
    ArrayList<Post> userLikedPosts;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_liked, container, false);
        Gson gson = new Gson();
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
        /**TODO:
         * Lägg så att den hämtar användare för att hitta de gillade inläggen
         */
        userLikedPosts.addAll(u.getLiked_post());
        FeedAdapter adapter = new FeedAdapter(requireActivity(), userLikedPosts);
        likedList.setAdapter(adapter);
    }
}
