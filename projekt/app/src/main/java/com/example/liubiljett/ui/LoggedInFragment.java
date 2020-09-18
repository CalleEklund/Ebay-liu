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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.liubiljett.Post;
import com.example.liubiljett.R;
import com.example.liubiljett.TestAdapter;
import com.example.liubiljett.User;
import com.example.liubiljett.VolleyService;
import com.google.gson.Gson;

import java.util.ArrayList;

public class LoggedInFragment extends Fragment {
    TextView profileName;
    TextView profileEmail;
    ListView userCreatedPosts;
    ArrayList<Post> rowItems;
    String currentUserString;
    User currentUser;
    Gson gson;
    private VolleyService volleyService;
    LogInFragment.OnAcccesKeyListener mainParent;


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
        volleyService = new VolleyService(getContext());
        profileName = root.findViewById(R.id.profileName);
        profileEmail = root.findViewById(R.id.profileEmail);
        userCreatedPosts = root.findViewById(R.id.postedByUserList);
        rowItems = new ArrayList<>();


        if (getArguments() != null) {
            currentUserString = getArguments().getString("result");
        } else {
            Log.d("ERROR", "args null");
        }
        currentUser = gson.fromJson(currentUserString, User.class);
        setData(currentUser);

        final Button logOut = root.findViewById(R.id.button4);
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                volleyService.logOutUser(currentUser.getAccessToken(), new VolleyService.VolleyCallback() {
                    @Override
                    public void onSuccess(String result) {
                        Toast toast = Toast.makeText(getContext(),
                                result,
                                Toast.LENGTH_SHORT);

                        toast.show();
                        mainParent.hasAccessKey(false, null);
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_logged, new LogInFragment()).commit();
                    }

                    @Override
                    public void onError(String result) {
                        Toast toast = Toast.makeText(getContext(),
                                result,
                                Toast.LENGTH_SHORT);

                        toast.show();
                    }
                });
            }

        });

        return root;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof LogInFragment.OnAcccesKeyListener) {
            mainParent = (LogInFragment.OnAcccesKeyListener) context;
        } else {
            throw new ClassCastException(context.toString() + "must implement interface");
        }
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
