package com.example.firetry1;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeFragment extends Fragment implements View.OnClickListener{

    //todo same code could be applied to managers an users
    final String KEY_NAME = "name";
    final String KEY_EMAIL = "email";
    final String KEY_EVENTS_NUM = "events";
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    FirebaseUser user;
    DocumentReference usersRef;

    CircleImageView circleImageView;
    EditText ETname,Etemail;
    ImageView Add_notify_btn;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        setHasOptionsMenu(true);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        user = mAuth.getCurrentUser();
        usersRef = db.collection("users").document(user.getEmail());
        getFieldsInput();
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_home, container, false);

        ETname =  v.findViewById(R.id.name_profile_managers);
        Etemail = v.findViewById(R.id.email_profile_managers);
        circleImageView = v.findViewById(R.id.Imageview_profileInit);

        Add_notify_btn = v.findViewById(R.id.notifications_list_user);

        Add_notify_btn.setOnClickListener(this);

        return  v;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_users,menu);
    }

    //todo should open a menu with options
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //todo go to logout page
        if(item.getItemId()== R.id.menu_logout){
            mAuth.signOut();
            startActivity(new Intent(getActivity(),MainActivity.class));
        }

        //todo go to edit page
        if(item.getItemId()== R.id.menu_edit){
            startActivity(new Intent(getActivity(),Update.class));
        }
        return super.onOptionsItemSelected(item);
    }


    //todo gets current user info
    public void getFieldsInput(){
        usersRef.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            String name = documentSnapshot.getString(KEY_NAME);
                            String email = documentSnapshot.getString(KEY_EMAIL);
                            //int EventsNum = documentSnapshot.get(KEY_EVENTS_NUM);

                            ETname.setText(name);
                            Etemail.setText(email);

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
        if(view == Add_notify_btn){
            startActivity(new Intent(getActivity(),setUserNotificationActivity.class));
        }



    }
}

