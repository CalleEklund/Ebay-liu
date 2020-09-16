package com.example.liubiljett.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.liubiljett.Post;
import com.example.liubiljett.R;
import com.example.liubiljett.User;
import com.example.liubiljett.VolleyService;
import com.google.gson.Gson;

import java.util.ArrayList;

public class DetailFragment extends Fragment {

    String clickedItem;
    Gson gson;
    TextView headline;
    TextView price;
    TextView description;
    TextView createdBy;
    ArrayList<String> commentPosts;
    String comment;
    private VolleyService volleyService;
    User currentUser;
    Post clicked;
    String currentUserString;
    private EditText commentField;

    public DetailFragment() {
        gson = new Gson();
    }

    public static DetailFragment newInstance(Post listItem, User currentUser) {
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        Gson gson = new Gson();
        String json = gson.toJson(listItem);
        args.putString("result", json);
        json = gson.toJson(currentUser);
        args.putString("user", json);
        fragment.setArguments(args);
        return fragment;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        final View root = inflater.inflate(R.layout.fragment_detail, container, false);
        volleyService = new VolleyService(getContext());
        headline = root.findViewById(R.id.detailHeadline);
        price = root.findViewById(R.id.detailPrice);
        description = root.findViewById(R.id.detailDescription);
        commentField = root.findViewById(R.id.comment);
        commentPosts = new ArrayList<>();
        @SuppressLint("UseSwitchCompatOrMaterialCode") final Switch like = root.findViewById(R.id.like);
        @SuppressLint("UseSwitchCompatOrMaterialCode") final Switch follow = root.findViewById(R.id.follow);


        if (getArguments() != null) {
//            Log.d("args", getArguments().toString());
            clickedItem = getArguments().getString("result");
            currentUserString = getArguments().getString("user");
        }
        clicked = gson.fromJson(clickedItem, Post.class);
        currentUser = gson.fromJson(currentUserString, User.class);
        showPost(clicked);
        if(currentUser != null){
            if(isOwnPost()){
                like.setChecked(true);
                follow.setChecked(true);
                like.setEnabled(false);
                follow.setEnabled(false);
            }else if(isLikedPost()){
                like.setChecked(true);
            }
            //Lägg till en check för följda personer
        }else{
            like.setEnabled(false);
            follow.setEnabled(false);
            commentField.setEnabled(false);
        }

        if (like != null) {
            like.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        Log.d("TAG","inlägget gillat");
                        volleyService.likePost(currentUser.getAccessToken(), clicked.getId(), new VolleyService.VolleyCallback() {
                            @Override
                            public void onSuccess(String result) {
                                Toast toast = Toast.makeText(getContext(),
                                        result,
                                        Toast.LENGTH_SHORT);
                                toast.show();
                            }

                            @Override
                            public void onError(String result) {
                                Toast toast = Toast.makeText(getContext(),
                                        result,
                                        Toast.LENGTH_SHORT);
                                toast.show();
                            }
                        });
                    } else {
                        Log.d("TAG","inlägget ogillat");
                        volleyService.unLikePost(currentUser.getAccessToken(), clicked.getId(), new VolleyService.VolleyCallback() {
                            @Override
                            public void onSuccess(String result) {
                                Toast toast = Toast.makeText(getContext(),
                                        result,
                                        Toast.LENGTH_SHORT);
                                toast.show();
                            }

                            @Override
                            public void onError(String result) {
                                Toast toast = Toast.makeText(getContext(),
                                        result,
                                        Toast.LENGTH_SHORT);
                                toast.show();
                            }
                        });
                        //ta bort post ur gillade
                    }
                }
            });
        }

        if (follow != null) {
            follow.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        //lägg till användare i following
                    } else {
                        //ta bort användare ur following
                    }
                }
            });
        }


        // FRÅN: https://stackoverflow.com/questions/8063439/android-edittext-finished-typing-event
        commentField.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE
                        || event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    if (event == null || !event.isShiftPressed()) {
                        comment = commentField.getText().toString();
                        commentPosts.add(comment);

                        commentField.setText("");
                        return true;
                    }
                }
                return false;
            }
        });
        /*
        final Button publish = root.findViewById(R.id.postComment);
        publish.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick(View v) {
                comment = commentField.getText().toString();
                commentPosts.add(comment); //lägg till namn innan såhär: name + ": " + comment

            }
        });

         */


        ListView listView = root.findViewById(R.id.commentsList);
        ArrayAdapter<String> commentAdapter = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_list_item_1, commentPosts);
        listView.setAdapter(commentAdapter);

        //kommentar skrivs i R.id.comment, som ska läggas i R.id.commentsList när den skickas.
        //spara kommentaren i databasen kopplat till posten

        return root;

    }

    public boolean isOwnPost() {
        for (Post userCreatedPost : currentUser.getCreated_post()) {
            if (userCreatedPost.getId() == clicked.getId()) {
                return true;
            }
        }
        return false;
    }
    public boolean isLikedPost(){
        for (Post userLikedPost : currentUser.getLiked_post()) {
            if (userLikedPost.getId() == clicked.getId()) {
                return true;
            }
        }
        return false;
    }


    @SuppressLint("SetTextI18n")
    private void showPost(Post data) {
        headline.setText("Title: " + data.getTitle());
        price.setText("Price: " + data.getPrice());
        description.setText("Övrig info: " + data.getDesc());
    }


}

