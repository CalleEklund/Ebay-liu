package com.example.liubiljett;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.liubiljett.classes.User;
import com.example.liubiljett.ui.ListFragment;
import com.example.liubiljett.ui.LogInFragment;
import com.example.liubiljett.ui.MainPageFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;

/**
 * Main class which holds the navigation bar and passes the current user to the different fragments
 */
public class MainActivity extends AppCompatActivity implements LogInFragment.OnAcccesKeyListener {
    private boolean hasUserAccessKey;
    private User loggedInUser;
    BottomNavigationView navView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);


        navView = findViewById(R.id.nav_view);
        final NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        /*
         * Send the user to the different fragments depending on which navigation item clicked.
         * Also passes the right arguments to the destination fragment.
         */
        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Bundle args = new Bundle();

                switch (item.getItemId()) {
                    case R.id.navigation_post:
                        args.putString("result", new Gson().toJson(loggedInUser));
                        navController.navigate(R.id.navigation_post, args);
                        break;
                    case R.id.navigation_liked:
                        if (!hasUserAccessKey) {

                            Toast toast = Toast.makeText(getApplicationContext(),
                                    "Du måste vara inloggad för att se dina gillade inlägg.",
                                    Toast.LENGTH_SHORT);
                            toast.show();
                        } else {
                            args.putString("result", new Gson().toJson(loggedInUser));
                            navController.navigate(R.id.navigation_liked, args);
                        }
                        break;
                    case R.id.navigation_feed:
                        args.putString("user", new Gson().toJson(loggedInUser));
                        args.putString("hasAccessKey", String.valueOf(hasUserAccessKey));
                        navController.navigate(R.id.navigation_feed, args);
                        break;
                    case R.id.navigation_profile:

                        args.putString("result", new Gson().toJson(loggedInUser));
                        if (hasUserAccessKey) {
                            navController.navigate(R.id.navigation_loggedin, args);

                        } else {
                            navController.navigate(R.id.navigation_profile);
                        }
                        break;
                }
                return true;
            }
        });
        getSupportFragmentManager().beginTransaction().add(new ListFragment(), "listFragment").commit();
        getSupportFragmentManager().beginTransaction().add(new MainPageFragment(), "mainpageFragment").commit();


    }

    /**
     * Interface method to retrieve the current user and if the user has accesskey
     *
     * @param hasKey if user has accesskey
     * @param u      current user
     */
    @Override
    public void hasAccessKey(boolean hasKey, User u) {
        hasUserAccessKey = hasKey;
        loggedInUser = u;

    }
}