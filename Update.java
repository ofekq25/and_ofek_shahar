package com.example.firetry1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Update extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private FirebaseFirestore db;
    DocumentSnapshot documentSnapshot;

    private static final String TAG = "APP";
    private static final String KEY_NAME = "name";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASSWORD = "password";
    static  UserName userName_keep;
    //user input
    EditText ET_name;


    Button btnUpdate;
    TextView goBack;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        db = FirebaseFirestore.getInstance();

        initListeners();
        getData();

    }

    //todo gets username current info
    public void getData(){
        db.collection("users").document(user.getEmail()).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                    UserName userName = documentSnapshot.toObject(UserName.class);
                    String email= userName.getEmail(); //shouldn't be editted

                    userName_keep = new UserName(userName.getName(),userName.getEmail(),userName.getPassword(),userName.getIsManager());
                    }
                });

    }


    //todo update username info
    public void updateInfo(){
        Boolean isManager = userName_keep.getIsManager();
        String name = ET_name.getText().toString();
        UserName userName = new UserName(name,userName_keep.getEmail(),userName_keep.getPassword(),userName_keep.getIsManager());
        if(isManager==true){
            db.collection("users").document(user.getEmail()).set(userName);
            db.collection("managers").document(user.getEmail()).set(userName);
        }
        if(isManager==false){
            db.collection("users").document(user.getEmail()).set(userName);
        }
    }


    //todo on click listener
public void initListeners()
{

    ET_name = (EditText) findViewById(R.id.RegName);
    btnUpdate = (Button) findViewById(R.id.btnUpdateUser);
    goBack = (TextView) findViewById(R.id.etLoginPage);

    ET_name.setOnClickListener(this);
    btnUpdate.setOnClickListener(this);
    goBack.setOnClickListener(this);
}
   


   public void UpdateFunc(){
    String name = ET_name.getText().toString();
    String email = user.getEmail();

    db.collection("users").document(email).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
        @Override
        public void onSuccess(DocumentSnapshot documentSnapshot) {
            documentSnapshot.toObject(UserName.class).getIsManager();
        }
    });


    UserName user = new UserName(name,email,userName_keep.getPassword(),userName_keep.getIsManager());

    db.collection("users").document(user.getEmail()).set(user);
   }

   //todo return the user to his navigation bar page (user or manager)
   public void  goBackToNavbar(boolean isManager){
       if(isManager == true){startActivity(new Intent(Update.this,Manager_nav_Activity.class));}
       if(isManager == false){startActivity(new Intent(Update.this,User_nav_Activity.class));}


       FirebaseUser user3 = FirebaseAuth.getInstance().getCurrentUser();

   }

    @Override
    public void onClick(View view) {
        //todo update info + go back
        if(view == btnUpdate)
        {
            updateInfo();
            goBackToNavbar(userName_keep.getIsManager());
        }
        if(view == goBack){
            //todo go back
            goBackToNavbar(userName_keep.getIsManager());
        }

    }
}