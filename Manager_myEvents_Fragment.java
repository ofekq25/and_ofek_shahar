package com.example.firetry1;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;


public class Manager_myEvents_Fragment extends Fragment {

    UserName userName;
    FirebaseAuth mAuth;
    FirebaseUser user;
    FirebaseFirestore db;
    CollectionReference myNotifyRef;

    myEventAdapter userAdapter;
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Manager_myEvents_Fragment() {
        // Required empty public constructor
    }



    // TODO: Rename and change types and number of parameters
    public static Manager_myEvents_Fragment newInstance(String param1, String param2) {
        Manager_myEvents_Fragment fragment = new Manager_myEvents_Fragment();
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
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        db = FirebaseFirestore.getInstance();
        getUserDeatils();
        loadEvents();


    }

    private void getUserDeatils() {
        db.collection("users").document(user.getEmail())
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                         userName = documentSnapshot.toObject(UserName.class);
                    }
                });


    }


    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search_events_fragment, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.user_rv_myEvents);

        userAdapter = new myEventAdapter(getActivity());
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(userAdapter);


        userAdapter.setOnItemClickListener(new myEventAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

            }
        });

        return view;
    }






    //todo load manager events
    private void loadEvents(){
        db.collection("managers").document(user.getEmail())
                .collection("myEvents")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> dsList = queryDocumentSnapshots.getDocuments();
                        for (DocumentSnapshot ds : dsList) {
                            Event data = ds.toObject(Event.class);
                            userAdapter.add(data);
                        }
                    }
                });
    }



}