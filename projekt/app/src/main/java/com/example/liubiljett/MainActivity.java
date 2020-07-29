package com.example.liubiljett;

import android.os.Bundle;
import android.widget.ListView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.example.liubiljett.R;

public class MainActivity extends AppCompatActivity {  //ANVÄND javac -Xlint:unchecked MainActivity.java
                                                        //I TERMINALEN OCH GÅ IGENOM FELEN DÄR

    String[] headLineArray = {"Big Chonkus", "ODZ", "Beatles", "LasseMajas detektivbyrå"};
    String[] priceArray = {"100kr", "120kr", "999kr", "100kr"};
    Integer[] imageArray = {R.drawable.account, R.drawable.comment, R.drawable.heart, R.drawable.search};

    ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_feed, R.id.navigation_post, R.id.navigation_profile,R.id.navigation_liked,R.id.navigation_chat)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        MyAdapter mAdapter = new MyAdapter(this, headLineArray, priceArray, imageArray);
        listView = findViewById(R.id.listviewID);
        listView.setAdapter(mAdapter);

        //github.com/crazycodeboy/react-native-splash-screen/issues/
    }

}
