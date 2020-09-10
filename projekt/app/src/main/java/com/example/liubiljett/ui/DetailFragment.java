package com.example.liubiljett.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.liubiljett.Post;
import com.example.liubiljett.R;
import com.example.liubiljett.RowItem;
import com.google.gson.Gson;

import org.w3c.dom.Text;

public class DetailFragment extends Fragment {

    String clickedItem;
    Gson gson;
    TextView headline;
    TextView price;
    TextView description;
    //ImageView image;

    public DetailFragment() {
        gson = new Gson();
    }

    public static DetailFragment newInstance(Post listItem) {
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

        View root = inflater.inflate(R.layout.fragment_detail, container, false);
        headline = root.findViewById(R.id.detailHeadline);
        price = root.findViewById(R.id.detailPrice);
        //image = root.findViewById(R.id.detailImage);
        description = root.findViewById(R.id.detailDescription);

        if (getArguments() != null) {
            clickedItem = getArguments().getString("result");
        }
        RowItem clicked = gson.fromJson(clickedItem, RowItem.class);
        fillData(clicked);

        return root;

    }



    private void fillData(RowItem data) {
        headline.setText(data.getHeadline());
        price.setText(data.getPrice());
        description.setText(data.getDescription());
        //image.setImageResource(data.getImageID());
    }


}

