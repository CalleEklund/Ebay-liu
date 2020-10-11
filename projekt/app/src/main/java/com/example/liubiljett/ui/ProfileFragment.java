package com.example.liubiljett.ui;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.liubiljett.handlers.Validator;
import com.example.liubiljett.handlers.VolleyService;
import com.example.liubiljett.R;

/**
 * Class for create account page
 */
public class ProfileFragment extends Fragment {

    private VolleyService volleyService;
    private Validator validator;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_profile, container, false);

        volleyService = new VolleyService(getContext());
        validator = new Validator(getContext());

        final TextView nameTextView = root.findViewById(R.id.name);
        final TextView emailTextView = root.findViewById(R.id.email);
        final TextView passwordTextView = root.findViewById(R.id.password);
        final TextView confirmPasswordTextView = root.findViewById(R.id.confirm_password);

        Button createButton = root.findViewById(R.id.create_button);

        /*
         * Check if user input is valid, if true the creates an account and sends the user to the login page
         */
        createButton.setOnClickListener(new AdapterView.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                String name = nameTextView.getText().toString();
                String email = emailTextView.getText().toString();
                String password = passwordTextView.getText().toString();
                String confirmPassword = confirmPasswordTextView.getText().toString();

                if (validator.checkInput(0, name, email, password, confirmPassword)) {
                    volleyService.createAccount(name, email, password, new VolleyService.VolleyCallback() {
                        @Override
                        public void onSuccess(String result) {
                            Toast toast = Toast.makeText(getContext(),
                                    result,
                                    Toast.LENGTH_SHORT);

                            toast.show();

                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_login, new LogInFragment()).commit();

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


}
