package com.example.liubiljett.handlers;

import android.content.Context;
import android.widget.Toast;

import com.example.liubiljett.classes.Post;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A validator class used for validating user's input at account registration, login
 */

public class Validator {
    Context mContext;

    public Validator(Context context) {
        mContext = context;
    }

    /**
     *  Checks if the user's password and confirm password match
     * @param password User's password
     * @param confirmPassword User confirming password
     * @return True or False
     */
    private boolean checkPassword(String password, String confirmPassword) {
        return password.equals(confirmPassword);
    }

    /**
     *  Used at account creation and login procedure
     * @param createAccount an int that decides if the function should be applied
     *                      to account or login
     * @param name User's name
     * @param email User's email
     * @param password User's password
     * @param confirmPassword User's confirming password
     * @return True or False
     */
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

    /**
     * Checks with regex if the User's email input matches with a correct email pattern
     * @param email User's email
     * @return True or False
     */
    private boolean checkEmail(String email) {
        Pattern VALID_EMAIL_ADDRESS_REGEX =
                Pattern.compile("^[\\w-_.+]*[\\w-_.]@([\\w]+[.])+[\\w]+[\\w]$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
        return !matcher.find();
    }

    /**
     * Collection function that checks all three earlier checks
     * @return A message to the user if inputs are correct or not
     */
    public boolean checkInput(int createAccount, String name, String email, String password, String confirmPassword) {
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
        } else if (checkEmptyInput(createAccount, name, email, password, confirmPassword)) {
            Toast toast = Toast.makeText(mContext,
                    "Tomma inputs",
                    Toast.LENGTH_SHORT);

            toast.show();
        } else {
            return true;
        }
        return false;
    }

    /**
     * Checks if all inputs are set in post creation
     * @param p Post object
     * @return True or False
     */
    public boolean checkPostInput(Post p) {
        return p.getTitle() != null || p.getPrice() != null || p.getDesc() != null;
    }
}
