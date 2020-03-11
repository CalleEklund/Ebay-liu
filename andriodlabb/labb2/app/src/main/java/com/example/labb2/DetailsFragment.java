package com.example.labb2;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;


public class DetailsFragment extends Fragment {
    int members;
    TextView textView;

    public DetailsFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            members = getArguments().getInt("members");
        }
        Log.v("TAG", ": " + members);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_details, container, false);
        textView = rootView.findViewById(R.id.display);
        textView.setText("Antal medlemmar är: " + members);
        return rootView;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

}
