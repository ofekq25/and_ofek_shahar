package com.example.firetry1;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivityNavs extends AppCompatActivity implements View.OnClickListener{


    Button btn,btn2,goNextPage;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_navs);
        btn = (Button) findViewById(R.id.button);
        btn2 = (Button) findViewById(R.id.button2);
        goNextPage = (Button) findViewById(R.id.goNextPage);


        btn.setOnClickListener(this);
        btn2.setOnClickListener(this);
        goNextPage.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v==btn){
            Intent i = new Intent(MainActivityNavs.this,User_nav_Activity.class);
            startActivity(i);
        }
        if(v==btn2){
            Intent i = new Intent(MainActivityNavs.this,Manager_nav_Activity.class);
            startActivity(i);
        }

        if(v==goNextPage){
            //Intent i = new Intent(MainActivityNavs.this,actionNextPage.class);
           // startActivity(i);
        }
    }
}