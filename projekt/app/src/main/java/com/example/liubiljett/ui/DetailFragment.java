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

import com.example.liubiljett.classes.Post;
import com.example.liubiljett.R;
import com.example.liubiljett.classes.User;
import com.example.liubiljett.handlers.VolleyService;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;

public class DetailFragment extends Fragment {

    private Gson gson;
    private TextView headline;
    private TextView price;
    private TextView description;
    private ArrayList<String> commentPosts;
    private VolleyService volleyService;
    User currentUser;
    private Post clicked;
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

        String clickedItem = null;
        String currentUserString = null;
        if (getArguments() != null) {
            clickedItem = getArguments().getString("result");
            currentUserString = getArguments().getString("user");
        }
        clicked = gson.fromJson(clickedItem, Post.class);
        currentUser = gson.fromJson(currentUserString, User.class);
        showPost(clicked);
        initialPostCheck(like, follow);

        if (like != null) {
            like.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
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
                    }
                }
            });
        }

        if (follow != null) {
            follow.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        volleyService.getPostCreator(clicked.getId(), new VolleyService.VolleyCallback() {
                            @Override
                            public void onSuccess(String result) {
                                Log.d("res",result);
                            }

                            @Override
                            public void onError(String result) {

                            }
                        });
                        //lägg till användare i following
                    } else {
                        //ta bort användare ur following
                    }
                }
            });
        }
        clicked.getComments().removeAll(Arrays.asList("", null));
        for (int i = 0; i < clicked.getComments().size(); i++) {
            commentPosts.add(clicked.getCommentedBy().get(i) + ": " + clicked.getComments().get(i));
        }

        // FRÅN: https://stackoverflow.com/questions/8063439/android-edittext-finished-typing-event
        commentField.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE || event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
//                  Bort kommentarad för att den kallar på addComment med en tom sträng en andra gång vilket knasar till det
//                    if (event == null || !event.isShiftPressed()) {
                    final String comment = commentField.getText().toString();
                    volleyService.addComment(currentUser.getAccessToken(), clicked.getId(), comment, new VolleyService.VolleyCallback() {
                        @Override
                        public void onSuccess(String result) {
                            Toast toast = Toast.makeText(getContext(),
                                    result,
                                    Toast.LENGTH_SHORT);
                            toast.show();
                            commentPosts.add(comment);
                        }

                        @Override
                        public void onError(String result) {
                            Toast toast = Toast.makeText(getContext(),
                                    result,
                                    Toast.LENGTH_SHORT);

                            toast.show();
                        }
                    });
                    commentField.setText("");
//                        return true;
//                    }
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
        Log.d("post",clicked.toString());
        ListView listView = root.findViewById(R.id.commentsList);
        ArrayAdapter<String> commentAdapter = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_list_item_1, commentPosts);
        listView.setAdapter(commentAdapter);

        //kommentar skrivs i R.id.comment, som ska läggas i R.id.commentsList när den skickas.
        //spara kommentaren i databasen kopplat till posten

        return root;

    }

    private void initialPostCheck(Switch like, Switch follow) {

        if (currentUser != null) {
            if (currentUser.isOwnPost(clicked)) {
                like.setChecked(true);
                follow.setChecked(true);
                like.setEnabled(false);
                follow.setEnabled(false);
            } else if (currentUser.isLikedPost(clicked)) {
                like.setChecked(true);
            }
            //Lägg till en check för följda personer
        } else {
            like.setEnabled(false);
            follow.setEnabled(false);
            commentField.setEnabled(false);
        }
    }
    @SuppressLint("SetTextI18n")
    private void showPost(Post data) {
        headline.setText("Title: " + data.getTitle());
        price.setText("Price: " + data.getPrice());
        description.setText("Övrig info: " + data.getDesc());

    }


}

