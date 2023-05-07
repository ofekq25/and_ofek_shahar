package com.example.firetry1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.text.DateFormat;
import java.util.Calendar;


public class AddEventActivity extends AppCompatActivity implements View.OnClickListener
{
    FirebaseFirestore db;
    FirebaseAuth mAuth;
    FirebaseUser user;
    CollectionReference managersRef;

    RadioGroup radioGroup;
    RadioButton radioButton;

    EditText Etitle, Edate, Etime, Eplace, Edescription;
    Button Bdate, Btime, Bplace, Bdescription;
    Button save;

    private int mYear, mMonth, mDay, mHour, mMinute;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        initListeners();
    }

    public void initListeners() {

        Etitle = (EditText) findViewById(R.id.in_name);
        Eplace = (EditText) findViewById(R.id.in_Place);
        Etime = (EditText) findViewById(R.id.in_time);
        Edate = (EditText) findViewById(R.id.in_date);
        Edescription= (EditText) findViewById(R.id.in_description);

        Bdate = findViewById(R.id.btn_date);
        Btime = findViewById(R.id.btn_time);
        Bplace = findViewById(R.id.btn_place);
        Bdescription = findViewById(R.id.btn_description);

        radioGroup = findViewById(R.id.radioGroup);

        Bdate.setOnClickListener(this);
        Btime.setOnClickListener(this);


        save = (Button) findViewById(R.id.btnSave_event);
        save.setOnClickListener(this);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        //todo create a collection ref in order to store the path of event
        managersRef = db.collection("managers").document(user.getEmail())
                .collection("myEvents");

    }

    //todo gets the text of radio button selected
    private String radioButtonTxt() {

        // get selected radio button from radioGroup
        int selectedId = radioGroup.getCheckedRadioButtonId();
        // find the radiobutton by returned id
        radioButton = findViewById(selectedId);

        String radioButtonText = radioButton.getText().toString();

        return radioButtonText;
    }

    //todo saves {Event} in the manager sub-collection
    //todo saves {Event} in event collection

    public void saveData(){
        String title = Etitle.getText().toString();
        String date = Edate.getText().toString();
        String time = Etime.getText().toString();
        String place = Eplace.getText().toString();
        String description = Edescription.getText().toString();
        String type = radioButtonTxt();

        Event nEvent = new Event(title,date,time,place,description,type,user.getEmail());

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        //todo saves event into events collection and uses title as key
        db.collection("events").document(nEvent.getTitle()).set(nEvent);

        //todo saves event into sub-collection myEvents and uses title as key
        managersRef.document(nEvent.getTitle()).set(nEvent);

        Toast.makeText(AddEventActivity.this, "event was saved", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(AddEventActivity.this,Manager_nav_Activity.class));


    }








    //todo checks if all fields are filled
    public boolean isAllEmpty(){
        if(Etitle.getText().toString() != ""
                && Edescription.getText().toString()!= ""  &&
                Etime.getText().toString()!= "" &&
                Edate.getText().toString()!= "")
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    @Override
    public void onClick(View view) {

        if (view == Bdate) {
            //todo  Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(AddEventActivity.this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            Edate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }

        if (view == Btime) {
            // todo Get Current Time
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            // Use Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(AddEventActivity.this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {

                            Etime.setText(hourOfDay + ":" + minute);
                        }
                    }, mHour, mMinute, false);
            timePickerDialog.show();
        }


        if (view == save) {

            if (Etitle.getText().toString().isEmpty()) {
                Etitle.setError("name empty");
            } else if(Edate.getText().toString().isEmpty()) {
                Edate.setError("date empty");
            } else if(Etime.getText().toString().isEmpty()) {
                Etime.setError("time empty");
            } else if (Eplace.getText().toString().isEmpty()) {
                Eplace.setError("place empty");
            } else if (Edescription.getText().toString().isEmpty()) {
                Edescription.setError("description empty");
            }else{
                if(isAllEmpty()==true){
                    saveData();
                }
            }

            }


            }

        }

