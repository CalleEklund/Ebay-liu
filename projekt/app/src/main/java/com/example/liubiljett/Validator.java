package com.example.liubiljett;

import android.content.Context;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {
    Context mContext;

    public Validator(Context context) {
        mContext = context;
    }

    private boolean checkPassword(String password, String confirmPassword) {
        return password.equals(confirmPassword);
    }

    private boolean checkEmptyInput(int createAccount, String name, String email, String password, String confirmPassword) {
        switch (createAccount) {
            case 0:
                return name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty();
            case 1:
                return email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty();
            default:
                return false;
        }


    }

    private boolean checkEmail(String email) {
        Pattern VALID_EMAIL_ADDRESS_REGEX =
                Pattern.compile("^[\\w-_.+]*[\\w-_.]@([\\w]+[.])+[\\w]+[\\w]$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
        return !matcher.find();
    }

    public boolean checkInput(int createAccount,String name, String email, String password, String confirmPassword) {
        if (!checkPassword(password, confirmPassword)) {
            Toast toast = Toast.makeText(mContext,
                    "Lösenorden matcher inte varandra.",
                    Toast.LENGTH_SHORT);

            toast.show();
        } else if (checkEmail(email)) {
            Toast toast = Toast.makeText(mContext,
                    "Ogiltig email address",
                    Toast.LENGTH_SHORT);

            toast.show();
        } else if (checkEmptyInput(createAccount,name, email, password, confirmPassword)) {
            Toast toast = Toast.makeText(mContext,
                    "Tomma inputs",
                    Toast.LENGTH_SHORT);

            toast.show();
        } else {
            return true;
        }
        return false;
    }
}
