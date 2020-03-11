package com.example.labb2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;


public class DetailsFragment extends Fragment {
    int members;
    TextView textView;
    Button button;
    ListFragment.ItemSelectedListener mainParent;
    DetailsFragment.OnFragmentactionListener goBackToParent;

    public DetailsFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            members = getArguments().getInt("members");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_details, container, false);
        textView = rootView.findViewById(R.id.display);
        textView.setText("Antal medlemmar är: " + members);

        button = rootView.findViewById(R.id.goBack);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBackToParent.swapback();
            }
        });
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ListFragment.ItemSelectedListener) {
            mainParent = (ListFragment.ItemSelectedListener) context;
            goBackToParent = (DetailsFragment.OnFragmentactionListener) context;
        } else {
            throw new ClassCastException(context.toString() + "implemtn interface");
        }

    }

    public interface OnFragmentactionListener {
        void swapback();
    }

}
