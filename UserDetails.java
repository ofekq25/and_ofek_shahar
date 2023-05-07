package com.example.firetry1;

import android.annotation.SuppressLint;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class UserDetails extends AppCompatActivity {
    TextView nameTitle,emailTitle,textTitle;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent i = getIntent();
        String name = i.getStringExtra("name");
        String email = i.getStringExtra("email");

        //set text fields
        nameTitle = (TextView) findViewById(R.id.nameInit);
        emailTitle = (TextView) findViewById(R.id.emailInit);

        nameTitle.setText(name);
        emailTitle.setText(email);


    }
}