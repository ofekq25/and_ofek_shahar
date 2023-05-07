package com.example.firetry1;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
//import android.support.v4.app.DialogFragment;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.sql.Time;
import java.text.DateFormat;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;

import javax.xml.datatype.Duration;

public class setNotificationActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {
    private TextView mTextView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_notification);

        mTextView = findViewById(R.id.textView);

        Button buttonTimePicker = findViewById(R.id.button_timepicker);
        buttonTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(), "time picker");

            }
        });

        Button buttonCancelAlarm = findViewById(R.id.button_cancel);
        buttonCancelAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(setNotificationActivity.this, Manager_nav_Activity.class));

            }
        });
    }


    //todo gets the time set on time picker
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Calendar c = Calendar.getInstance();

        //c.set(Calendar.YEAR,1);
        //c.set(Calendar.MONTH, 2);
        //c.set(Calendar.DAY_OF_MONTH, 17);
        //c.set(Calendar.DATE,1);


        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
        c.set(Calendar.MINUTE, minute);
        c.set(Calendar.SECOND, 0);

        String hour_save = hourOfDay + ":" + minute;

        Calendar setCal2 = setCal(hourOfDay, minute, Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH);
        Calendar newCal = getTime(minute, hourOfDay);
        updateTimeText(newCal);
        startAlarm(newCal);

        saveNotifyDetails(hour_save);
    }
    //עובד עבור שנים
    //לא עובד עבור ימים
    //לא עובד עבור חודשים


    private Calendar setCal(int hourOfDay, int minute, int year, int month, int day) {
        Calendar c = Calendar.getInstance();

        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
        c.set(Calendar.MINUTE, minute);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, day);
        return c;
    }

    //working
    private Calendar getTime(int minute, int hour) {

        int day = Calendar.DAY_OF_MONTH;
        int month = Calendar.MONTH;
        int year = Calendar.YEAR;


        Calendar c = Calendar.getInstance();
        c.set(year, month, day, hour, minute);
        return c;
    }


    //todo update textview
    private void updateTimeText(Calendar c) {
        String timeText = "Alarm set for: ";
        timeText += DateFormat.getTimeInstance(DateFormat.SHORT).format(c.getTime());

        mTextView.setText(timeText);
    }

    //todo create the alarm
    private void startAlarm(Calendar c) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);

        if (c.before(Calendar.getInstance())) {
            c.add(Calendar.DATE, 1);
        }

        alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                c.getTimeInMillis(),
                pendingIntent
        );
        startActivity(new Intent(this, Manager_nav_Activity.class));


        //alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
    }


    //todo save notification details in firestore
    public void saveNotifyDetails(String hour){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        String date_now = todayDate();
        NotificationData_extended notificationData = new NotificationData_extended(date_now,hour);
        NotificationData_extended data = new NotificationData_extended(date_now,hour);


        //todo saves to db without any supporting key
        db.collection("managers")
                .document(user.getEmail()).collection("myNotifications")
                .document().set(data);


    }

    //todo get today's date and returns it as a String
    public String todayDate(){
        Calendar cal = Calendar.getInstance();

        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH)+1;
        int dayofmonth = cal.get(Calendar.DAY_OF_MONTH);
        String date = dayofmonth + "." + month + "." + year;
        return date;
    }

}



