package com.example.liubiljett.ui;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.util.Log;
import android.util.Xml;
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

import com.example.liubiljett.Validator;
import com.example.liubiljett.VolleyService;
import com.example.liubiljett.R;

import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

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

        nameTextView.setText("Calle");
        emailTextView.setText("calle@gmail.com");
        passwordTextView.setText("losen123");
        confirmPasswordTextView.setText("losen123");

        Button createButton = root.findViewById(R.id.create_button);
        createButton.setOnClickListener(new AdapterView.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                String name = nameTextView.getText().toString();
                String email = emailTextView.getText().toString();
                String password = passwordTextView.getText().toString();
                String confirmPassword = confirmPasswordTextView.getText().toString();

                if (validator.checkInput(0,name, email, password, confirmPassword)) {
                    volleyService.createAccount(name, email, password, new VolleyService.VolleyCallback() {
                        @Override
                        public void onSuccess(String result) {
                            Toast toast = Toast.makeText(getContext(),
                                    result,
                                    Toast.LENGTH_SHORT);

                            toast.show();

                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_login, new LoggedInFragment()).commit();

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
