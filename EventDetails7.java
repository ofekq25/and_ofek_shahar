package com.example.firetry1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class EventDetails7 extends AppCompatActivity implements View.OnClickListener{

    TextView ETmanager_email,ETtitle,ETdate,ETtime,ETplace,ETdescription,ETtype;
    ImageView Imanager_email,Ititle,Idate,Itime,Iplace,Idescription,Itype;
    ImageView backPreviousPage;
    Button enterEventBtn;
    FirebaseUser user;
    FirebaseAuth mAuth;
    FirebaseFirestore db;
    CollectionReference userRef;
    UserName userName1;
    Event nEvent;
    String title_txt;

    static UserName userName_keep;
    static Event eventData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_innerprofile_user7);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        db = FirebaseFirestore.getInstance();

        Intent i = getIntent();
        String title = i.getStringExtra("title");
        String time = i.getStringExtra("hour");
        String date = i.getStringExtra("date");
        String manager_email = i.getStringExtra("managerEmail");
        String place = i.getStringExtra("place");
        String description = i.getStringExtra("description");
        String type = i.getStringExtra("type");
        title_txt = title;




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

        eventData  = new Event(title,date,time,place,description,type,manager_email);


        backPreviousPage = (ImageView)findViewById(R.id.backPage);
        backPreviousPage.setOnClickListener(this);

        ETmanager_email.setText(manager_email);
        ETtitle.setText(title);
        ETdate.setText(date);
        ETtime.setText(time);
        ETplace.setText(place);
        ETdescription.setText(description);
        ETtype.setText(type);

        enterEventBtn = (Button)findViewById(R.id.sign_up_event);
        enterEventBtn.setVisibility(View.GONE);


        getUserInput();





    }




    //todo insert the deatils of username which enter manager event
    //todo it will appear inside (events collection -> specific event (doc) -> enterUsers Collection)
    //todo collection events==>  specific event -> collection joinedUsers

    public void addToManager(Event event, UserName userName){
        db.collection("events").document(event.getTitle())
                .collection("enterUsers").document(user.getEmail())
                .set(userName);

    }

    //todo add event to user myEvents collection
    public void addUser(Event event, UserName userName){
        userRef = db.collection("users").document(user.getEmail()).collection("myEvents");
        userRef.document(event.getTitle()).set(event);

    }




    //todo gets user input
    public void getUserInput(){
        db.collection("users").document(user.getEmail()).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            String name = documentSnapshot.getString("name");
                            String email = documentSnapshot.getString("email");
                            String password = documentSnapshot.getString("password");
                            Boolean isManager = documentSnapshot.getBoolean("isManager");

                            UserName userName = new UserName(name,email,password,isManager);
                            userName_keep = userName;


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



    @Override
    public void onClick(View view) {
        if(view == backPreviousPage){

            if(userName_keep.getIsManager() == true){
                startActivity(new Intent(EventDetails7.this, Manager_nav_Activity.class));
            }

            if(userName_keep.getIsManager() == false){
                startActivity(new Intent(EventDetails7.this, User_nav_Activity.class));
            }

        }


    }
}