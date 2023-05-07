package com.example.firetry1;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class EventDetails extends AppCompatActivity {
    TextView ETmanager_email,ETtitle,ETdate,ETtime,ETplace,ETdescription,ETtype;
    ImageView Imanager_email,Ititle,Idate,Itime,Iplace,Idescription,Itype;
    ImageView backPreviousPage;
    Event event_keep;
    UserName userName;
    FirebaseUser user;
    FirebaseAuth mAuth;
    FirebaseFirestore db;
    DocumentReference eventsRef;


    //todo כפתור הרשמה להצטרפות
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_innerprofile);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        event_keep = new Event();
        Intent i = getIntent();
        String title = i.getStringExtra("title");
        String time = i.getStringExtra("hour");
        String date = i.getStringExtra("date");
        String manager_email = i.getStringExtra("managerEmail");
        String place = i.getStringExtra("place");
        String description = i.getStringExtra("description");
        String type = i.getStringExtra("type");



        ETmanager_email = (TextView) findViewById(R.id.manager_emailInit);
        ETtitle = (TextView) findViewById(R.id.titleInit);
        ETdate = (TextView) findViewById(R.id.dateInit);
        ETtime = (TextView) findViewById(R.id.timeInit);
        ETplace = (TextView) findViewById(R.id.placeInit);
        ETdescription = (TextView) findViewById(R.id.descriptionInit);
        ETtype = (TextView) findViewById(R.id.typeInit);

        Imanager_email = (ImageView) findViewById(R.id.manager_email_icon);
        Ititle = (ImageView) findViewById(R.id.title_icon);
        Idate = (ImageView) findViewById(R.id.date_icon);
        Itime = (ImageView) findViewById(R.id.time_icon);
        Iplace = (ImageView) findViewById(R.id.place_icon);
        Idescription = (ImageView) findViewById(R.id.description_icon);
        Itype = (ImageView) findViewById(R.id.type_icon);

        backPreviousPage = (ImageView)findViewById(R.id.backPage);
        backPreviousPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EventDetails.this, Manager_nav_Activity.class));
            }
        });
        //todo show event Users for the manager who created the event
        Button showUsersSignedRV = (Button)findViewById(R.id.show_eventUsers_manager);
        showUsersSignedRV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i2 = new Intent(EventDetails.this, usersRecyclerview.class);
                i2.putExtra("title", title);
                startActivity(i2);

                //startActivity(new Intent(EventDetails.this,usersRecyclerview.class));
            }
        });

        ETmanager_email.setText(manager_email);
        ETtitle.setText(title);
        ETdate.setText(date);
        ETtime.setText(time);
        ETplace.setText(place);
        ETdescription.setText(description);
        ETtype.setText(type);

        Event eventData  = new Event(manager_email,title,date,time,place,description,type);

    }






    //todo get this user details
    public void getUserDetails(){
        db.collection("users").document(user.getEmail()).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            userName = documentSnapshot.toObject(UserName.class);

                        } else {
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("TAG", e.toString());
                    }
                });
    }


    public  void joinEvent(Event event, UserName userName){
        DocumentReference AddOrdinaryUser = db.collection("users").document(user.getEmail());
        //todo save event to user myEvents
        db.collection("users").document(user.getEmail()).set(event);

        //todo save user to Manager myEvents
        //todo collection order==>  Manager -> myEvents -> joinedUsers
        db.collection("managers").document(event.getManagersEmail()).collection("myEvents").document(event.getTitle())
                .collection("joinedUsers").document(user.getEmail())
                .set(AddOrdinaryUser);


    }



}