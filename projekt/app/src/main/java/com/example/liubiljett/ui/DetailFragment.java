package com.example.liubiljett.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.liubiljett.R;
import com.example.liubiljett.RowItem;
import com.example.liubiljett.TestAdapter;
import com.google.gson.Gson;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class DetailFragment extends Fragment {

    String clickedItem;
    Gson gson;
    TextView headline;
    TextView price;
    TextView description;
    //ImageView image;
    ArrayList<String> commentPosts;
    String comment;

    public DetailFragment() {
        gson = new Gson();
    }

    public static DetailFragment newInstance(RowItem listItem) {
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        Gson gson = new Gson();
        String json = gson.toJson(listItem);
        args.putString("result", json);
        fragment.setArguments(args);
        return fragment;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        final View root = inflater.inflate(R.layout.fragment_detail, container, false);
        headline = root.findViewById(R.id.detailHeadline);
        price = root.findViewById(R.id.detailPrice);
        //image = root.findViewById(R.id.detailImage);
        description = root.findViewById(R.id.detailDescription);
        commentPosts = new ArrayList<>();

        if (getArguments() != null) {
            clickedItem = getArguments().getString("result");
        }
        RowItem clicked = gson.fromJson(clickedItem, RowItem.class);
        fillData(clicked);

        @SuppressLint("UseSwitchCompatOrMaterialCode") final Switch like = root.findViewById(R.id.like);
        if (like != null) {
            like.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        //lägg till post i gillade
                    } else {
                        //ta bort post ur gillade
                    }
                }
            });
        }

        @SuppressLint("UseSwitchCompatOrMaterialCode") final Switch follow = root.findViewById(R.id.follow);
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

        final EditText commentField = root.findViewById(R.id.comment);

        // FRÅN: https://stackoverflow.com/questions/8063439/android-edittext-finished-typing-event
        commentField.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE
                        || event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER){
                    if (event == null || !event.isShiftPressed()){
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



    private void fillData(RowItem data) {
        headline.setText(data.getHeadline());
        price.setText(data.getPrice());
        description.setText(data.getDescription());
        //image.setImageResource(data.getImageID());
    }


}

