package com.example.liubiljett.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.liubiljett.Post;
import com.example.liubiljett.R;
import com.example.liubiljett.TestAdapter;
import com.example.liubiljett.User;
import com.example.liubiljett.VolleyService;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;

public class LoggedInFragment extends Fragment {
    TextView profileName;
    TextView profileEmail;
    ListView userCreatedPosts;
    ArrayList<Post> rowItems;
    String currentUser;
    Gson gson;


    public LoggedInFragment() {
        gson = new Gson();
    }

    public static LoggedInFragment newInstance(User inUser) {
        LoggedInFragment loggedInFragment = new LoggedInFragment();
        Bundle args = new Bundle();
        args.putString("result", new Gson().toJson(inUser));
        loggedInFragment.setArguments(args);
        return loggedInFragment;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_loggedin, container, false);

        profileName = root.findViewById(R.id.profileName);
        profileEmail = root.findViewById(R.id.profileEmail);
        userCreatedPosts = root.findViewById(R.id.postedByUserList);
        rowItems = new ArrayList<>();


        if (getArguments() != null) {
            currentUser = getArguments().getString("result");
        } else {
            Log.d("ERROR", "args null");
        }
        User u = gson.fromJson(currentUser, User.class);
        setData(u);

        final Button logOut = root.findViewById(R.id.button4);
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //LOGGA UT
            }
        });

        return root;
    }



    @SuppressLint("SetTextI18n")
    public void setData(User u) {
        profileName.setText("Namn: " + u.getName());
        profileEmail.setText("Email: " + u.getEmail());
        rowItems.addAll(u.getCreated_post());
        TestAdapter adapter = new TestAdapter(requireActivity(), rowItems);
        userCreatedPosts.setAdapter(adapter);

    }
}
