package com.example.liubiljett.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.liubiljett.R;

public class LoggedInFragment extends Fragment {
    TextView profileName;
    TextView profileEmail;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_loggedin, container, false);
        profileName = root.findViewById(R.id.profileName);
        profileEmail = root.findViewById(R.id.profileEmail);
        setData();
        return root;
    }

    public void setData(){
        profileName.setText(R.string.name);
        profileEmail.setText(R.string.email);
    }
}
