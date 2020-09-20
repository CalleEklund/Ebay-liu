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

public class LogInFragment extends Fragment {

    private Validator validator;
    private VolleyService volleyService;
    private Gson gson;
    LoggedInFragment loggedInFragment;
    OnAcccesKeyListener mainParent;


    public static LogInFragment newInstance() {
        return new LogInFragment();
    }

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

        emailTextView.setText("calle@gmail.com");
        passwordTextView.setText("callelosen");


        textView.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_login, new ProfileFragment()).commit();
                loginButton.setVisibility(View.INVISIBLE);

            }
        });

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
//                            Log.d("key", result);
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

    public void setCurrentUser(final String userAccessToken) {
        volleyService.getCurrentUser(userAccessToken, new VolleyService.VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                /** TODO:
                 * Se om du kan göra detta smidigare genom att använda gsonbuilder för att kunna excluda hasAccesskey och sätt den sedan till true
                 * https://www.tutorialspoint.com/gson/gson_excluded_serialization.htm
                 * Använda transiten framför värderna
                 */
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

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnAcccesKeyListener) {
            mainParent = (OnAcccesKeyListener) context;
        } else {
            throw new ClassCastException(context.toString() + "must implement interface");
        }
    }

    public interface OnAcccesKeyListener {
        void hasAccessKey(boolean hasKey, User loggedInUser);
    }
}
