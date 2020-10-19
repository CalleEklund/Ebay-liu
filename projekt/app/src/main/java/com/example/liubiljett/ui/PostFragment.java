package com.example.liubiljett.ui;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.liubiljett.classes.Post;
import com.example.liubiljett.R;
import com.example.liubiljett.classes.User;
import com.example.liubiljett.handlers.Validator;
import com.example.liubiljett.handlers.VolleyService;
import com.google.gson.Gson;

/**
 * Class user for creating a post
 */
public class PostFragment extends Fragment {

    private User currentUser;
    private Validator validator;
    private VolleyService volleyService;

    TextView postTitleTextView;
    TextView postPriceTextView;
    TextView postDescriptionTextView;

    Button postButton;
    LogInFragment.OnAcccesKeyListener mainParent;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_post, container, false);
        volleyService = new VolleyService(getContext());
        validator = new Validator(getContext());
        Gson gson = new Gson();

        postButton = root.findViewById(R.id.publishButton);

        postTitleTextView = root.findViewById(R.id.postHeadline);
        postPriceTextView = root.findViewById(R.id.postPrice);
        postDescriptionTextView = root.findViewById(R.id.postDescription);

        if (getArguments() != null) {
            String currentUserString = getArguments().getString("result");
            if (currentUserString != null) {
                currentUser = gson.fromJson(currentUserString, User.class);
            }
        } else {
            Log.d("ERROR", "args null");
        }

        /*
         * Checks if the input for a post i valid, and stores the post to the current user's created posts
         */
        postButton.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick(View v) {
                String postTitle = postTitleTextView.getText().toString();
                String postPrice = postPriceTextView.getText().toString();
                String postDescription = postDescriptionTextView.getText().toString();
                final Post newPost = new Post(postTitle, postPrice, postDescription);
                if(currentUser == null){
                    Toast toast = Toast.makeText(getContext(),"Du kan inte skapa ett inlägg om du inte är inloggad",Toast.LENGTH_SHORT);
                    toast.show();
                }else{
                    if (validator.checkPostInput(newPost)) {
                        volleyService.uploadPost(currentUser.getAccessToken(), newPost, new VolleyService.VolleyCallback() {
                            @Override
                            public void onSuccess(String result) {

                                Toast toast = Toast.makeText(getContext(),
                                        result,
                                        Toast.LENGTH_SHORT);

                                toast.show();
                                currentUser.addPost(newPost);
                                mainParent.hasAccessKey(currentUser.isAccessToken(), currentUser);
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
                }

            }
        });

        return root;
    }

    /**
     * Check if MainActivity implements the right interface, if true then retrieve MainActivity's context
     * @param context MainActivity's context
     */
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof LogInFragment.OnAcccesKeyListener) {
            mainParent = (LogInFragment.OnAcccesKeyListener) context;
        } else {
            throw new ClassCastException(context.toString() + "must implement interface");
        }
    }
}
