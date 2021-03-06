package com.example.liubiljett.ui;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.liubiljett.R;
import com.example.liubiljett.classes.User;
import com.example.liubiljett.handlers.Validator;
import com.example.liubiljett.handlers.VolleyService;
import com.google.gson.Gson;

/**
 * Class for the login page
 */
public class LogInFragment extends Fragment {

    private Validator validator;
    private VolleyService volleyService;
    private Gson gson;
    LoggedInFragment loggedInFragment;
    OnAcccesKeyListener mainParent;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        final View root = inflater.inflate(R.layout.fragment_login, container, false);
        volleyService = new VolleyService(getContext());
        validator = new Validator(getContext());
        gson = new Gson();
        final TextView textView = root.findViewById(R.id.register_link);
        final Button loginButton = root.findViewById(R.id.login_button);
        final TextView emailTextView = root.findViewById(R.id.emailLogin);
        final TextView passwordTextView = root.findViewById(R.id.passwordLogin);

        emailTextView.setText("felix@gmail.com");
        passwordTextView.setText("felixlosen");

        //Sends the user to create account page
        textView.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_login, new ProfileFragment()).commit();
                loginButton.setVisibility(View.INVISIBLE);

            }
        });

        //Checks user input and if correct logs in the user
        loginButton.setOnClickListener(new AdapterView.OnClickListener() {

            @Override
            public void onClick(View v) {
                String email = emailTextView.getText().toString();
                String password = passwordTextView.getText().toString();
                if (validator.checkInput(1, "", email, password, password)) {
                    volleyService.logInUser(email, password, new VolleyService.VolleyCallback() {
                        @Override
                        public void onSuccess(String result) {
                            setCurrentUser(result);
                            loginButton.setVisibility(View.INVISIBLE);

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

        return root;

    }

    /**
     * Retrieves the current user with its accesstoken
     * from the database and sends the user to the loggedin page
     * @param userAccessToken user's accesstoken retrieved from logging in
     */
    public void setCurrentUser(final String userAccessToken) {
        volleyService.getCurrentUser(userAccessToken, new VolleyService.VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                User newUser = gson.fromJson(result, User.class);
                newUser.setAccessToken(userAccessToken);
                newUser.setHasAccessToken(true);
                loggedInFragment = LoggedInFragment.newInstance(newUser);
                mainParent.hasAccessKey(newUser.isAccessToken(), newUser);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_login, loggedInFragment).commit();
            }

            @Override
            public void onError(String result) {
                Log.e("ERROR SET USER", result);
            }
        });
    }

    /**
     * Check if MainActivity implements the right interface, if true then retrieve MainActivity's context
     * @param context MainActivity's context
     */
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnAcccesKeyListener) {
            mainParent = (OnAcccesKeyListener) context;
        } else {
            throw new ClassCastException(context.toString() + "must implement interface");
        }
    }

    /**
     * Interface to pass the user and if the user has an accesskey
     */
    public interface OnAcccesKeyListener {
        void hasAccessKey(boolean hasKey, User loggedInUser);
    }
}
