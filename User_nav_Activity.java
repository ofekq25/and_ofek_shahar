package com.example.firetry1;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class User_nav_Activity extends AppCompatActivity {

    //todo user navigation bar activity
    FloatingActionButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //todo this was all the problem
        setContentView(R.layout.activity_user_nav);
        //todo set fab so user could go to setNotification page
        fab = (FloatingActionButton) findViewById(R.id.fab_user);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(User_nav_Activity.this,setUserNotificationActivity.class));
            }
        });
        replaceFragment(new HomeFragment());
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setBackground(null);

        bottomNavigationView.setOnItemSelectedListener(item -> {


            if (item.getItemId() == R.id.home) {
                replaceFragment(new HomeFragment());
            }


            if (item.getItemId() == R.id.search) {
                replaceFragment(new SearchEventsFragment());
                //todo events with all events

            }



            if (item.getItemId() == R.id.notify) {
                replaceFragment(new User_notifyFragment());
                //todo notification list

            }


            if (item.getItemId() == R.id.favorite) {
                replaceFragment(new User_myEvents_Fragment());
                //todo fragment for events that user joined in

            }


            return true;

        });

    }


    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }
}