package com.example.liubiljett;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;


import com.example.liubiljett.ui.DetailFragment;
import com.example.liubiljett.ui.LikedFragment;
import com.example.liubiljett.ui.ListFragment;
import com.example.liubiljett.ui.LogInFragment;
import com.example.liubiljett.ui.LoggedInFragment;
import com.example.liubiljett.ui.MainPageFragment;
import com.example.liubiljett.ui.PostFragment;
import com.example.liubiljett.ui.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;


public class MainActivity extends AppCompatActivity implements LogInFragment.OnAcccesKeyListener {  //ANVÄND javac -Xlint:unchecked MainActivity.java
    boolean hasUserAccessKey = false;
    User loggedInUser = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_liked, R.id.navigation_feed, R.id.navigation_post,  R.id.navigation_profile)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_feed:
                        Log.d("NAV","feed");
                        MainPageFragment mainPageFragment = new MainPageFragment();
                        loadFragment(mainPageFragment);
                        break;
                    case R.id.navigation_post:
                        PostFragment postFragment = new PostFragment();
                        loadFragment(postFragment);
                        break;
                    case R.id.navigation_liked:
                        Log.d("NAV","liked");
                        LikedFragment likedFragment = new LikedFragment();
                        loadFragment(likedFragment);
                        break;
                    case R.id.navigation_profile:
                        if(hasUserAccessKey){
                            LoggedInFragment loggedInFragment = LoggedInFragment.newInstance(loggedInUser);
                            loadFragment(loggedInFragment);
                        }else{
                            LogInFragment logInFragment = new LogInFragment();
                            loadFragment(logInFragment);
                        }
                        break;
                }

                return true;
            }
        });
        //Ta bort senare
//        NavigationUI.setupWithNavController(navView, navController);

        getSupportFragmentManager().beginTransaction().add(new ListFragment(), "listFragment").commit();
        getSupportFragmentManager().beginTransaction().add(new MainPageFragment(), "mainpageFragment").commit();


    }
    private void loadFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment,fragment).commit();

    }
    @Override
    public void hasAccessKey(boolean hasKey, User u) {
        hasUserAccessKey = hasKey;
        loggedInUser = u;
    }
}



