package com.example.liubiljett.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.liubiljett.VolleyService;
import com.example.liubiljett.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProfileFragment extends Fragment {

    private VolleyService volleyService;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_profile, container, false);

        volleyService = new VolleyService(getContext());

        final TextView nameEditText = root.findViewById(R.id.name);
        final TextView emailEditText = root.findViewById(R.id.email);
        final TextView passwordEditText = root.findViewById(R.id.password);
        final TextView confirmPasswordEditText = root.findViewById(R.id.confirm_password);

        nameEditText.setText("Calle");
        emailEditText.setText("calle@gmail.com");
        passwordEditText.setText("losen123");
        confirmPasswordEditText.setText("losen123");

        Button createButton = root.findViewById(R.id.create_button);
        createButton.setOnClickListener(new AdapterView.OnClickListener() {

            @Override
            public void onClick(View v) {
                //getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_login, new LoggedInFragment()).commit();
                String name = nameEditText.getText().toString();
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                String confirmPassword = confirmPasswordEditText.getText().toString();

                if (checkInput(name, email, password, confirmPassword)) {
                    volleyService.createAccount(name, email, password, new VolleyService.VolleyCallback() {
                        @Override
                        public void onSuccess(String result) {
                            Toast toast = Toast.makeText(getContext(),
                                    result,
                                    Toast.LENGTH_SHORT);

                            toast.show();

                        }

                        @Override
                        public void onError(String result) {
                            Toast toast = Toast.makeText(getContext(),
                                    result,
                                    Toast.LENGTH_SHORT);

                            toast.show();
                        }
                    });
//                    Log.d("Response", response);
                }

//                createButton.setVisibility(View.INVISIBLE);
            }
        });
        return root;
    }


    private boolean checkPassword(String password, String confirmPassword) {
        return password.equals(confirmPassword);
    }

    private boolean checkEmptyInput(String name, String email, String password, String confirmPassword) {
        return name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty();
    }

    private boolean checkEmail(String email) {
        Pattern VALID_EMAIL_ADDRESS_REGEX =
                Pattern.compile("^[\\w-_.+]*[\\w-_.]@([\\w]+[.])+[\\w]+[\\w]$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
        return !matcher.find();
    }

    private boolean checkInput(String name, String email, String password, String confirmPassword) {
        if (!checkPassword(password, confirmPassword)) {
            Toast toast = Toast.makeText(getContext(),
                    "Lösenorden matcher inte varandra.",
                    Toast.LENGTH_SHORT);

            toast.show();
        } else if (checkEmail(email)) {
            Toast toast = Toast.makeText(getContext(),
                    "Ogiltig email address",
                    Toast.LENGTH_SHORT);

            toast.show();
        } else if (checkEmptyInput(name, email, password, confirmPassword)) {
            Toast toast = Toast.makeText(getContext(),
                    "Tomma inputs",
                    Toast.LENGTH_SHORT);

            toast.show();
        } else {
            return true;
        }
        return false;
    }
}
