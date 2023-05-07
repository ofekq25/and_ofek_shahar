package com.example.firetry1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


public class myNotifyAdapter extends RecyclerView.Adapter<myNotifyAdapter.ViewHolder> {
    private static Context context;
    private static List<NotificationData_extended> mList;
    private List<NotificationData_extended> exampleListFull;



//todo adapter for notifications



    public myNotifyAdapter(Context context){
        this.context=context;
        this.mList = new ArrayList<>();


    }
    public void add(NotificationData_extended user){
        mList.add(user);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        //private ImageView mImageView;
        final TextView mDate;
        final TextView mHour;

        public ViewHolder(View itemView) {
            super(itemView);
            mDate = itemView.findViewById(R.id.date_notify);
            mHour = itemView.findViewById(R.id.hour_notify);




        }

    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.notifcation_item, parent, false);
        ViewHolder evh = new ViewHolder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        NotificationData_extended currentItem = mList.get(position);

        holder.mDate.setText(currentItem.getDate());
        holder.mHour.setText(currentItem.getHour());


    }

    @Override
    public int getItemCount() {
        return mList.size();
    }



}