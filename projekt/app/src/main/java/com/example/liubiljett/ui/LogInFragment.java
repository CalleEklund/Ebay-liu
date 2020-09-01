package com.example.liubiljett.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.liubiljett.R;

public class LogInFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        final View root = inflater.inflate(R.layout.fragment_login, container, false);

        final TextView textView = root.findViewById(R.id.register_link);
        final Button loginButton = root.findViewById(R.id.login_button);
        textView.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_login, new ProfileFragment()).commit();
                //Button loginButton = root.findViewById(R.id.login_button);
                loginButton.setVisibility(View.INVISIBLE);

            }
        });

        loginButton.setOnClickListener(new AdapterView.OnClickListener() {

            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_login, new LoggedInFragment()).commit();
                loginButton.setVisibility(View.INVISIBLE);
            }
        });



        return root;
    }
}
