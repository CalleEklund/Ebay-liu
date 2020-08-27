package com.example.liubiljett.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.liubiljett.R;
import com.example.liubiljett.RowItem;

public class DetailFragment extends Fragment {  // den här verkar vara knas

    /*
    private String mHeadline;
    private String mPrice;
    private int mImage;
    private String mDescription;

    public DetailFragment(final RowItem ad){
        this.mHeadline = ad.getHeadline();
        this.mPrice = ad.getPrice();
        this.mImage = ad.getImageID();
        this.mDescription = ad.getDescription();
    }

     */

    public DetailFragment(){

    }

    public static DetailFragment newInstance(String searchedName) {
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putString("group", searchedName);
        fragment.setArguments(args);
        return fragment;
    }
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_detail, container, false);
        /*
        TextView headline = root.findViewById(R.id.detailHeadline);
        TextView price = root.findViewById(R.id.detailPrice);
        ImageView image = root.findViewById(R.id.detailImage);
        TextView description = root.findViewById(R.id.detailDescription);

        headline.setText(mHeadline);
        price.setText(mPrice);
        image.setImageResource(mImage);
        description.setText(mDescription);

         */

        return root;

    }


}

