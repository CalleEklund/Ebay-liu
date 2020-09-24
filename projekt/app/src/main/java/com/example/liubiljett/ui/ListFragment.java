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

public class ListFragment extends Fragment {
    private ItemSelectedListener mainParent;
    private ArrayList<Post> rowItems;
    private Gson gson;
    private ListView listView;
    private User currentUser;
    private FeedAdapter adapter;
    private VolleyService volleyService;
    public ListFragment() {

    }

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

        if (getArguments() != null) {
            String currentUserString = getArguments().getString("currentUser");
            if (!currentUserString.equals("null")) {
                currentUser = gson.fromJson(currentUserString, User.class);
            }
        } else {
            Log.d("ERROR", "args null");
        }




        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mainParent = (ItemSelectedListener) getActivity().getSupportFragmentManager().findFragmentByTag("mainpageFragment");
                mainParent.onItemSelected(rowItems.get(position),currentUser);

            }

        });
        @SuppressLint("UseSwitchCompatOrMaterialCode") final Switch showPost = root.findViewById(R.id.showPostsSwitch);
        showPost.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    showPost.setText(R.string.showFollowing);
                    //filtrera så att man bara lägger till de rowitems som är gjorda av de man följer
                } else {
                    showPost.setText(R.string.showAll);
                    //visa alla poster som vanligt
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




        return root;
    }

    private void addRowItems(List<Post> currentFeed) {
        rowItems.addAll(currentFeed);
    }

    public interface ItemSelectedListener {
        void onItemSelected(Post listItem,User user);
    }
}
