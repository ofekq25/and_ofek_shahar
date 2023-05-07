package com.example.firetry1;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class myEventAdapter extends RecyclerView.Adapter<myEventAdapter.ViewHolder> implements Filterable {
    private static Context context;
    private static List<Event> mList;
    private static List<Event> eventsListFull;
    private OnItemClickListener mListener;
    private List<Event> exampleListFull;
    static UserName userName;


    public interface OnItemClickListener {
        void onItemClick(int position);

    }

    public void setOnItemClickListener(myUserAdapter.OnItemClickListener listener) {
        mListener = (OnItemClickListener) listener;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }


    public myEventAdapter(Context context) {
        this.context = context;
        this.mList = new ArrayList<>();
        this.eventsListFull = mList;
    }

    public void add(Event event) {
        mList.add(event);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        //private ImageView mImageView;
        final ImageView imageView;
        final TextView mTitle;
        final TextView mTime;
        final TextView mDate;
        final TextView mManager_email;

        public ViewHolder(View itemView, OnItemClickListener listener) {
            super(itemView);
            mTitle = itemView.findViewById(R.id.textview_title2);
            mTime = itemView.findViewById(R.id.textview_hour2);
            mDate = itemView.findViewById(R.id.textview_date2);
            mManager_email = itemView.findViewById(R.id.textview_managerEmail2);
            imageView = (ImageView) itemView.findViewById(R.id.icon);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                            //todo pass data to details activity after event clicks item
                            Event item = new Event();
                            Intent i = new Intent(context, EventDetails.class);
                            i.putExtra("title", mList.get(position).getTitle());
                            i.putExtra("hour", mList.get(position).getTime());
                            i.putExtra("date", mList.get(position).getDate());
                            i.putExtra("managerEmail", mList.get(position).getManagersEmail());
                            i.putExtra("place", mList.get(position).getPlace());
                            i.putExtra("description", mList.get(position).getDescription());
                            i.putExtra("type", mList.get(position).getType());
                            context.startActivity(i);
                        }
                    }
                }
            });


        }

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_item_design2, parent, false);
        ViewHolder evh = new ViewHolder(v, mListener);
        getUser();
        return evh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Event currentItem = mList.get(position);

        holder.mTitle.setText(currentItem.getTitle());
        holder.mTime.setText(currentItem.getTime());
        holder.mDate.setText(currentItem.getDate());
        holder.mManager_email.setText(currentItem.getManagersEmail());
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (userName.getIsManager() == true) {
                    deleteDialog2(currentItem);

                }

            }
        });


    }

    public void getUser() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getEmail()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                userName = documentSnapshot.toObject(UserName.class);
            }
        });
    }

    //todo create a dialog in order to delete an event (user || manager)
    public void deleteDialog(Event event) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Warning")
                .setIcon(R.drawable.ic_baseline_warning_24)
                .setMessage("are you sure you want to delete this Event")
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(context, "deleting", Toast.LENGTH_SHORT).show();
                        FirebaseFirestore db = FirebaseFirestore.getInstance();
                        db.collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getEmail()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                userName = documentSnapshot.toObject(UserName.class);
                            }
                        });
                        if (userName.getIsManager() == true) {
                            //deleteManager(event);
                            delete_users_CollectionOfManager(event);
                        } else {
                            //deleteUser(event);
                        }
                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });

        builder.create().show();
    }

    public void deleteDialog2(Event event) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Warning")
                .setIcon(R.drawable.ic_baseline_warning_24)
                .setMessage("are you sure you want to delete this Event")
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {


                        delete_users_CollectionOfManager(event);


                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });

        builder.create().show();
    }

    //todo create delete sequence
    //todo זכור מחיקת דוקיומנט לא מוחקת את תת האוספים שלו
    public void deleteManager(Event event) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        db.collection("managers").document(user.getEmail())
                .collection("myEvents").document(event.getTitle()).delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("TAG", "DocumentSnapshot successfully deleted!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("TAG", "Error deleting document", e);
                    }
                });
        ;

        db.collection("events").document(event.getTitle()).delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("TAG", "DocumentSnapshot successfully deleted!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("TAG", "Error deleting document", e);
                    }
                });

        //db.collection("events").document(event.getTitle()).collection("enterUsers")


    }


    public void deleteManager2(Event event) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        db.collection("managers").document(user.getEmail())
                .collection("myEvents").document(event.getTitle()).delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("TAG", "DocumentSnapshot successfully deleted!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("TAG", "Error deleting document", e);
                    }
                });


        db.collection("events").document(event.getTitle()).collection("enterUsers")
                .document(user.getEmail()).delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("TAG", "DocumentSnapshot successfully deleted!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("TAG", "Error deleting document", e);
                    }
                });


        DocumentReference docSnap = db.collection("events").document(event.getTitle()).collection("enterUsers")
                .document(user.getEmail());
        docSnap.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {

            }
        });


    }




    //todo delete the collection of registered users in event the manager created and after it deleted
    public void delete_users_CollectionOfManager(Event event) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference colRef = db.collection("events").document(event.getTitle())
                .collection("enterUsers");

        //todo delete this event from all the users in enterUsers collection
        colRef.whereEqualTo("isManager", false)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("TAG", document.getId() + " => " + document.getData());


                                db.collection("users").document(document.getId())
                                        .collection("myEvents").document(event.getTitle()).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Log.d("TAG", "DocumentSnapshot successfully deleted!");
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.w("TAG", "Error deleting document", e);
                                            }
                                        });


                            }
                        } else {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                        }
                    }
                });


        //todo delete users from enterUsers collection
        colRef.whereEqualTo("isManager", false)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("TAG", document.getId() + " => " + document.getData());


                                colRef.document(document.getId()).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Log.d("TAG", "DocumentSnapshot successfully deleted!");
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.w("TAG", "Error deleting document", e);
                                            }
                                        });


                            }
                        } else {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                        }
                    }
                });

        //todo delete manager event in my Events and in events collection
        deleteManager(event);
    }






    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Event> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(mList);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Event item : mList) {
                    if (item.getTitle().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @SuppressLint("NotifyDataSetChanged")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mList.clear();
            mList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

/*
    @Override
    public Filter getFilter() {
        return eventsFilter;
    }

    private final Filter eventsFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {

            ArrayList<Event> filteredUsersList = new ArrayList<>();

            if (charSequence == null || charSequence.length() == 0) {
                filteredUsersList.addAll(eventsArrayListFull);
            } else {

                String filterPattern = charSequence.toString().toLowerCase().trim();
                for (Event event: eventsArrayListFull){
                    if(event.getTitle().toLowerCase().contains(filterPattern))
                        filteredUsersList.add(event);

                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredUsersList;
            results.count = filteredUsersList.size();
            return  results;
        }
        @Override
        protected void publishResults(CharSequence charSequence, FilterResults results) {

            mList.clear();
            mList.addAll((ArrayList) results.values);
            notifyDataSetChanged();
        }
    };



 */


}