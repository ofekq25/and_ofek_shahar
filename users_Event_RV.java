package com.example.firetry1;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.style.BackgroundColorSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class users_Event_RV extends AppCompatActivity {
    FirebaseFirestore db;
    myEventAdapter userAdapter;
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    static String title_text,manager_email_txt;


    //todo in this activity you should see the users who enter the manger Event and their details
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_recyclerview);
        db = FirebaseFirestore.getInstance();
        userAdapter = new myEventAdapter(this);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_users_in_event);
        mRecyclerView.setAdapter(userAdapter);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(userAdapter);

        Intent i = getIntent();
        String title = i.getStringExtra("title");
        String manager_email = i.getStringExtra("managerEmail");
        title_text = title;
        manager_email_txt = manager_email;  //todo save manager_email

        userAdapter.setOnItemClickListener(new myUserAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(users_Event_RV.this, "hello" + position, Toast.LENGTH_SHORT).show();


            }


        });
        loadUsers(title_text);
    }



    // todo load events info
    private void loadUsers(String title) {

        /*
        db.collection("managers").document(manager_email).collection("myEvents")
                .document(title).collection("users_signed")
         */
        db.collection("events").document(title).collection("enterUsers").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> dsList = queryDocumentSnapshots.getDocuments();
                        for (DocumentSnapshot ds : dsList) {
                            Event event = ds.toObject(Event.class);
                            userAdapter.add(event);
                        }
                    }
                });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.user_menu,menu);
        MenuItem menuItem = menu.findItem(R.id.actionSearch);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setQueryHint("Search Here!");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String newText) {
                return false;

            }

            @Override
            public boolean onQueryTextChange(String newText) {
                userAdapter.getFilter().filter(newText);

                if(newText.length() == 0){loadUsers(title_text);}

                return false;
            }
        });


        return true;

    }

}
