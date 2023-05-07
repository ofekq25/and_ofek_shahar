package com.example.firetry1;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Manager_nav_Activity extends AppCompatActivity {
    //todo manager navigation bar activity

    BottomNavigationView bottomNavigationView;
    FloatingActionButton fab;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_nav);

        replaceFragment(new HomeFragment2());
        //todo set fab so user could go to setNotification page
        fab = (FloatingActionButton)findViewById(R.id.fab_manager);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Manager_nav_Activity.this,AddEventActivity.class));
            }
        });
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView2);
        bottomNavigationView.setBackground(null);

        bottomNavigationView.setOnItemSelectedListener(item -> {

            if(item.getItemId() == R.id.fab_manager){
                Intent i = new Intent(this, setNotificationActivity.class);
                //todo set notify
            }



            if(item.getItemId() == R.id.search){
                //replaceFragment(new AddEventFragment());
                replaceFragment(new SearchEventsFragment2());
                //todo events with all events
            }

            if(item.getItemId() == R.id.home){
                replaceFragment(new HomeFragment2());
            }


            if(item.getItemId() == R.id.notify){
                replaceFragment(new Manager_notifyFragment());
                //todo notification list
            }


            if(item.getItemId() == R.id.favorite){
                replaceFragment(new Manager_myEvents_Fragment());
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