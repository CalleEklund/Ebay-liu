package com.example.liubiljett.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.liubiljett.R;

import java.util.Objects;

public class ProfileFragment extends Fragment {


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        final Button createButton = root.findViewById(R.id.create_button);
        createButton.setOnClickListener(new AdapterView.OnClickListener() {

            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_login, new LoggedInFragment()).commit();
                createButton.setVisibility(View.INVISIBLE);
            }
        });
        return root;
    }

}
