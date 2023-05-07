package com.example.firetry1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Source;
import com.google.firebase.firestore.auth.User;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private final String TAG = "APP";
    private FirebaseAuth mAuth;
    private  FirebaseFirestore db;
    EditText EmailEditText,PassEditText;
    Button LoginBTN;
    TextView SignUP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        initListener();

    }

    //todo one click listener
    public void initListener()
    {

        EmailEditText = findViewById(R.id.etEmail);
        EmailEditText.setOnClickListener(this);

        PassEditText = findViewById(R.id.etPassword);
        PassEditText.setOnClickListener(this);

        LoginBTN = (Button) findViewById(R.id.etLogin);
        SignUP = (TextView) findViewById(R.id.etRegisterPage);
        LoginBTN.setOnClickListener(this);
        SignUP.setOnClickListener(this);


    }






    //todo should lead to different login pages - navigation bars
    public void LoginAccepted(Boolean isManager){
        if(isManager == true) {
            Intent intent1 = new Intent(this, Manager_nav_Activity.class);
            startActivity(intent1);
        }
        if(isManager != true){
            Intent intent2 = new Intent(this, User_nav_Activity.class);
            startActivity(intent2);
        }

    }



    //todo this action should get custom object if logged in and should navigate to login activity if manager or not
    private void LoginOperation() {
        FirebaseUser user = mAuth.getCurrentUser();
        DocumentReference docRef = db.collection("users").document(user.getEmail());
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                UserName userName = documentSnapshot.toObject(UserName.class);
                LoginAccepted(userName.getIsManager());
            }
        });
    }



    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();


        if(user != null)
        {

            LoginOperation();
            //todo check if user is signed in





        }

    }

    public void reload2(){startActivity(new Intent(MainActivity.this,MainActivityNavs.class));}


    public void onClick(View view) {
        if (view == LoginBTN) {
            //todo login to app and move to next page

            String email = EmailEditText.getText().toString();

            String password = PassEditText.getText().toString();

            if (email.isEmpty()) {
                Toast.makeText(this, "email is empty", Toast.LENGTH_SHORT).show();
            } else if (password.isEmpty()) {
                Toast.makeText(this, "password is empty", Toast.LENGTH_SHORT).show();
            } else {
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(TAG,"this is success");

                                    //startActivity(new Intent(MainActivity.this, LoginActivity.class));
                                    LoginOperation();
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(MainActivity.this, "login failed", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }


        }
        if(view == SignUP){
            //todo go to register activity
            Intent intent = new Intent(MainActivity.this,RegisterActivity.class);
            startActivity(intent);
        }
    }
}




/*
links:
https://www.youtube.com/watch?v=OETdH66AWAs


Upload Image using Camera
https://www.youtube.com/watch?v=kWeeWOlzEKM

slidebar
https://www.youtube.com/watch?v=cOj4CuIHU1E


android screen dashboard
https://www.youtube.com/watch?v=ejbX9MO2ems

cool drawer check it up
https://github.com/mzule/FantasySlide

buttom bar using figma
https://www.youtube.com/watch?v=CrLiHAdYFyk
https://www.youtube.com/watch?v=P8O78APM17c


cool effect
https://www.youtube.com/watch?v=Gz91A7Gii-8

 Float Notification
https://www.youtube.com/watch?v=v1s36wmqP8M

push notification service
https://www.youtube.com/watch?v=e9llz2TXBz8


https://www.youtube.com/watch?v=IJsEnI4sjZE
    https://github.com/MonsterTechnoGits/Modern-Dashbord-Android-UI-Design/commit/3e96f6b19697747129b5440478189b50c523b7d6

    https://www.youtube.com/watch?v=YXdgLqDJ0NM

    https://www.youtube.com/watch?v=TStttJRAPhE&list=PLQ_Ai1O7sMV0cC_gcDD938mz9JF3wxZJG

    https://www.youtube.com/watch?v=wDl1ZsCKBtI&list=PL9Jev8MmTtECfhtkw-_2_HEh69fz4JjPi&index=28

Request Multiple Permissions at once in android
https://www.youtube.com/watch?v=y0gX4FD3nxk
 */