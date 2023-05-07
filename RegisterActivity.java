package com.example.firetry1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth mAuth;

    RadioGroup radioGroup;
    RadioButton radioButton;

    //user input
    EditText Username, Useremail, Userpassword;


    Button btnRegister;
    TextView btnMainPage;
    FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        radioGroup = findViewById(R.id.radioGroup_register);

        Username = (EditText) findViewById(R.id.RegName);
        Useremail = (EditText) findViewById(R.id.RegEmail);
        Userpassword = (EditText) findViewById(R.id.RegPassword);
        btnMainPage = (TextView) findViewById(R.id.MainPageBtn);
        btnRegister = (Button) findViewById(R.id.btnSignupUser);
        mAuth = FirebaseAuth.getInstance();

        Username.setOnClickListener(this);
        Useremail.setOnClickListener(this);
        Userpassword.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
        btnMainPage.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();


    }


    //todo get radio btn text
    private String radioButtonTxt() {

        // get selected radio button from radioGroup
        int selectedId = radioGroup.getCheckedRadioButtonId();
        // find the radiobutton by returned id
        radioButton = (RadioButton) findViewById(selectedId);

        String radioButtonText = radioButton.getText().toString();

        return radioButtonText;
    }

    //todo set type of user
    private Boolean managerSelection() {

        // get selected radio button from radioGroup
        int selectedId = radioGroup.getCheckedRadioButtonId();
        // find the radiobutton by returned id
        radioButton = (RadioButton) findViewById(selectedId);

        Boolean selected = false;
        String radioButtonText = radioButton.getText().toString();

        if (radioButtonText == "Ordinary") {
            return selected;
        } else {
            selected = true;
        }

        return selected;
    }

    public boolean selected(String b) {
        if (b == "Manager") {
            return true;
        }
        else {
            return false;
        }

    }

    //todo determines which type of user is it and moves it to navigation bar activity
    public void Register_Selction(String b, UserName userName){

        if(b.matches("Manager")){
            db.collection("managers").document(userName.getEmail()).set(userName);
            startActivity(new Intent(RegisterActivity.this, Manager_nav_Activity.class));
        }

        if(b.matches("Ordinary")){
            startActivity(new Intent(RegisterActivity.this, User_nav_Activity.class));
        }
    }



    public String checkButton(View v) {
        int radioId = radioGroup.getCheckedRadioButtonId();

        radioButton = findViewById(radioId);


        //Toast.makeText(this, "Selected Radio Button: " + radioButton.getText(),
        //Toast.LENGTH_SHORT).show();
        return (String) radioButton.getText().toString();

    }
    public void onClick(View v) {

        if (v == btnRegister) {
            Boolean state=false;
            String b = radioButtonTxt();
            if(b.matches("Manager")){
                state = true;
            }

            if(b.matches("Ordinary")){
                state = false;
            }




            String b1 = radioButtonTxt();



            String name = Username.getText().toString();
            String email = Useremail.getText().toString();
            String password = Userpassword.getText().toString();
            UserName nUser = new UserName(name, email, password, state);
            if (name.isEmpty()) {
                Username.setError("name empty");
            } else if (email.isEmpty()) {
                Useremail.setError("email empty");
            } else if (password.isEmpty()) {
                Userpassword.setError("password empty");
            } else {
                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            db.collection("users").document(nUser.getEmail()).set(nUser);
                            Toast.makeText(RegisterActivity.this, "user registered successfully", Toast.LENGTH_SHORT).show();
                            Register_Selction(radioButtonTxt(),nUser);
                        } else {
                            Toast.makeText(RegisterActivity.this, "registration error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                        }

                    }
                });
            }


        }


        if (v == btnMainPage) {
            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }
}