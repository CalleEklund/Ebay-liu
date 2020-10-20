package com.example.liubiljett.ui;

import android.annotation.SuppressLint;
import android.content.Context;
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

/**
 * Class used för displaying posts in a more detailed view, accessed when a post is clicked
 */
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
    String creatorIdStr = "";
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    Switch like;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    Switch follow;
    LogInFragment.OnAcccesKeyListener mainParent;


    public DetailFragment() {
        gson = new Gson();
    }

    /**
     * Instance to display the clicked post
     *
     * @param listItem    The clicked post
     * @param currentUser The current User
     * @return the fragment with arguments of the clicked post and the current user
     */
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

    /**
     * Sets up the post and retrieves the argument
     */
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        final View root = inflater.inflate(R.layout.fragment_detail, container, false);
        volleyService = new VolleyService(getContext());
        headline = root.findViewById(R.id.detailHeadline);
        price = root.findViewById(R.id.detailPrice);
        description = root.findViewById(R.id.detailDescription);
        commentField = root.findViewById(R.id.comment);
        commentPosts = new ArrayList<>();
        like = root.findViewById(R.id.like);
        follow = root.findViewById(R.id.follow);

        String clickedItem = null;
        String currentUserString = null;
        if (getArguments() != null) {
            clickedItem = getArguments().getString("result");
            currentUserString = getArguments().getString("user");
        }
        clicked = gson.fromJson(clickedItem, Post.class);
        currentUser = gson.fromJson(currentUserString, User.class);
        showPost(clicked);


        setCreatorId();


        /*
         * Instantiate the like and follow switch also adds the comments to the post
         */
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
                                currentUser.addLikedPosts(clicked);
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
                    } else {
                        volleyService.unLikePost(currentUser.getAccessToken(), clicked.getId(), new VolleyService.VolleyCallback() {
                            @Override
                            public void onSuccess(String result) {
                                Toast toast = Toast.makeText(getContext(),
                                        result,
                                        Toast.LENGTH_SHORT);
                                toast.show();
                                currentUser.removeLikedPosts(clicked);
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
            });
        }

        if (follow != null) {
            follow.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        volleyService.followUser(creatorIdStr, currentUser.getAccessToken(), new VolleyService.VolleyCallback() {
                            @Override
                            public void onSuccess(String result) {
                                Toast toast = Toast.makeText(getContext(),
                                        result,
                                        Toast.LENGTH_SHORT);
                                toast.show();
                                currentUser.addFollowedUser(Integer.parseInt(creatorIdStr));
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
                    } else {
                        volleyService.unFollowUser(creatorIdStr, currentUser.getAccessToken(), new VolleyService.VolleyCallback() {
                            @Override
                            public void onSuccess(String result) {
                                Toast toast = Toast.makeText(getContext(),
                                        result,
                                        Toast.LENGTH_SHORT);
                                toast.show();
                                currentUser.removeFollowedUser(Integer.parseInt(creatorIdStr));
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
            });
        }
        clicked.getComments().removeAll(Arrays.asList("", null));
        for (int i = 0; i < clicked.getComments().size(); i++) {
            commentPosts.add(clicked.getCommentedBy().get(i) + ": " + clicked.getComments().get(i));
        }

        /*
         * Instantiate the comment field
         */
        commentField.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE || event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    final String comment = commentField.getText().toString();
                    volleyService.addComment(currentUser.getAccessToken(), clicked.getId(), comment, new VolleyService.VolleyCallback() {
                        @Override
                        public void onSuccess(String result) {
                            Toast toast = Toast.makeText(getContext(),
                                    result,
                                    Toast.LENGTH_SHORT);
                            toast.show();
                            commentPosts.add(currentUser.getEmail() + ": " + comment);
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
                }
                return false;
            }
        });

        ListView listView = root.findViewById(R.id.commentsList);
        ArrayAdapter<String> commentAdapter = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_list_item_1, commentPosts);
        listView.setAdapter(commentAdapter);

        return root;

    }

    /**
     * Retrieves the creator's id of the post
     */
    private void setCreatorId() {
        volleyService.getPostCreator(clicked.getId(), new VolleyService.VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                creatorIdStr = result;

                initialPostCheck(like, follow, creatorIdStr);
            }

            @Override
            public void onError(String result) {
                Log.d("ERROR", result);
            }
        });
    }

    /**
     * Checks whether the clicked post is the current user own post or already liked/followed,
     * set the like and follow switches accordingly
     *
     * @param like      Like switch
     * @param follow    Follow switch
     * @param creatorId the creator of the post id
     */
    private void initialPostCheck(@SuppressLint("UseSwitchCompatOrMaterialCode") Switch like,
                                  @SuppressLint("UseSwitchCompatOrMaterialCode") Switch follow,
                                  String creatorId) {
        creatorIdStr = creatorId;

        if (currentUser != null) {
            if (creatorIdStr.equals(String.valueOf(currentUser.getId()))) {

                like.setChecked(true);
                follow.setChecked(true);
                like.setEnabled(false);
                follow.setEnabled(false);
            }
            if (currentUser.isLikedPost(clicked)) {
                like.setChecked(true);
            }
            if (currentUser.isFollowed(creatorId)) {
                follow.setChecked(true);
            }
        } else {
            like.setEnabled(false);
            follow.setEnabled(false);
            commentField.setEnabled(false);
        }
    }

    /**
     * Fills the fragment with detail of the post
     *
     * @param data Current post in Post object
     */
    @SuppressLint("SetTextI18n")
    private void showPost(Post data) {
        headline.setText("Title: " + data.getTitle());
        price.setText("Price: " + data.getPrice() + "kr");
        description.setText("Övrig info: " + data.getDesc());

    }

    /**
     * Check if MainActivity implements the right interface, if true then retrieve MainActivity's context
     *
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

