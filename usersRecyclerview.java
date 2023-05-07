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

public class usersRecyclerview extends AppCompatActivity {
    FirebaseFirestore db;
    myUserAdapter userAdapter;
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    String title_text,manager_email_txt;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_recyclerview);
        db = FirebaseFirestore.getInstance();
        userAdapter = new myUserAdapter(this);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        mRecyclerView.setAdapter(userAdapter);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(userAdapter);


        Intent i = getIntent();
        String title = i.getStringExtra("title");
        String manager_email = i.getStringExtra("managerEmail");
        title_text = title;
        manager_email_txt = manager_email_txt;

        userAdapter.setOnItemClickListener(new myUserAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {


            }


        });
        loadUsers(title_text);
    }



    // todo load users
    private void loadUsers(String title) {
        db.collection("events").document(title).collection("enterUsers").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> dsList = queryDocumentSnapshots.getDocuments();
                        for (DocumentSnapshot ds : dsList) {
                            UserName user = ds.toObject(UserName.class);

                            userAdapter.add(user);
                        }
                    }
                });

    }


    //todo filter recyclerview
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


/*
filter
https://www.youtube.com/watch?v=CTvzoVtKoJ8

https://www.youtube.com/watch?v=GvVz4KO4D54

pass data recycler
https://www.youtube.com/watch?v=jO0RkS-Ag3A

Firebase Push Notifications
https://www.youtube.com/watch?v=e9llz2TXBz8
 */