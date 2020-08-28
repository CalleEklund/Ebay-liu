package com.example.liubiljett;

import android.os.Bundle;


import com.example.liubiljett.ui.DetailFragment;
import com.example.liubiljett.ui.ListFragment;
import com.example.liubiljett.ui.MainPageFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;


public class MainActivity extends AppCompatActivity {  //ANVÄND javac -Xlint:unchecked MainActivity.java
    //I TERMINALEN OCH GÅ IGENOM FELEN DÄR

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_feed, R.id.navigation_post, R.id.navigation_profile, R.id.navigation_liked, R.id.navigation_chat)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        getSupportFragmentManager().beginTransaction().add(new ListFragment(), "listFragment").commit();
        getSupportFragmentManager().beginTransaction().add(new MainPageFragment(), "mainpageFragment").commit();


    }
}
