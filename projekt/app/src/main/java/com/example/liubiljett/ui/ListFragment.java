package com.example.liubiljett.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.liubiljett.classes.Post;
import com.example.liubiljett.R;
import com.example.liubiljett.handlers.FeedAdapter;
import com.example.liubiljett.classes.User;
import com.example.liubiljett.handlers.VolleyService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * Class used for displaying a list of posts, ex: the main feed, and followed user's posts
 */
public class ListFragment extends Fragment {
    private ItemSelectedListener mainParent;
    private ArrayList<Post> rowItems;
    private VolleyService volleyService;
    private Gson gson;
    private ListView listView;
    private User currentUser;
    private FeedAdapter adapter;
    @SuppressLint("UseSwitchCompatOrMaterialCode") Switch showPost;

    public ListFragment() {

    }

    /**
     * Instance for retrieving the current user
     * @param currentUser current user in a User object
     * @return fragment with the current user as arguments
     */
    public static ListFragment newInstance(User currentUser) {
        ListFragment fragment = new ListFragment();
        Bundle args = new Bundle();
        Gson gson = new Gson();
        String json = gson.toJson(currentUser);
        args.putString("currentUser", json);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_feed, container, false);
        volleyService = new VolleyService(getContext());
        rowItems = new ArrayList<>();
        gson = new Gson();
        listView = root.findViewById(R.id.itemList);
        FragmentActivity fragmentActivity = requireActivity();
        adapter = new FeedAdapter(fragmentActivity, rowItems);
        showPost = root.findViewById(R.id.showPostsSwitch);

        if (getArguments() != null) {
            String currentUserString = getArguments().getString("currentUser");
            if (!currentUserString.equals("null")) {
                currentUser = gson.fromJson(currentUserString, User.class);
            }
        } else {
            Log.d("ERROR", "args null");
        }
        //If no user is logged in disable the function to show followed user's posts
        if(currentUser == null || currentUser.getUser_following().size() == 0){
            showPost.setEnabled(false);
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mainParent = (ItemSelectedListener) getActivity().getSupportFragmentManager().findFragmentByTag("mainpageFragment");
                mainParent.onItemSelected(rowItems.get(position), currentUser);

            }

        });
        //Filter the feed so that it shows only followed user's posts
        showPost.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    showPost.setText(R.string.showFollowing);
                    volleyService.getFollowedUsersPosts(currentUser.getAccessToken(), new VolleyService.VolleyCallback() {
                        @Override
                        public void onSuccess(String result) {
                            List<Post> followedPosts = gson.fromJson(result, new TypeToken<List<Post>>() {
                            }.getType());
                            addRowItems(followedPosts);

                                listView.setAdapter(adapter);


                        }

                        @Override
                        public void onError(String result) {
                            listView.setAdapter(adapter);

                            Log.d("ERROR", result);
                        }
                    });
                } else {
            //Shows all published posts when the user used the switch
                    showPost.setText(R.string.showAll);
                    volleyService.getAllPosts(new VolleyService.VolleyCallback() {


                        @Override
                        public void onSuccess(String result)  {
                            List<Post> feedPosts = gson.fromJson(result, new TypeToken<List<Post>>() {
                            }.getType());
                            addRowItems(feedPosts);
                            listView.setAdapter(adapter);
                        }
                        @Override
                        public void onError(String result) {
                            Log.d("ERROR", result);
                        }
                    });
                }
            }
        });
        //Initial retrieve for the all published posts
        volleyService.getAllPosts(new VolleyService.VolleyCallback() {


            @Override
            public void onSuccess(String result)  {
                List<Post> feedPosts = gson.fromJson(result, new TypeToken<List<Post>>() {
                }.getType());
                addRowItems(feedPosts);
                listView.setAdapter(adapter);
            }
            @Override
            public void onError(String result) {
                Log.d("ERROR", result);
            }
        });




        return root;
    }

    /**
     * Adds the posts to an array
     * @param currentFeed Retrieved list of posts from database
     */
    private void addRowItems(List<Post> currentFeed) {
        rowItems.clear();
        rowItems.addAll(currentFeed);
    }

    /**
     * Interface used for passing the clicked item and current user between
     * the feed and detailfragment
     */
    public interface ItemSelectedListener {
        void onItemSelected(Post listItem, User user);
    }
}
