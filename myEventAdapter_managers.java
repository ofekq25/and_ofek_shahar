package com.example.firetry1;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


public class myEventAdapter_managers extends RecyclerView.Adapter<myEventAdapter_managers.ViewHolder> implements Filterable {
    private static Context context;
    private static List<Event> mList;
    private static List<Event> eventsListFull;
    private OnItemClickListener mListener;
    private List<Event> exampleListFull;





    public interface OnItemClickListener{
        void onItemClick(int position);

    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }


    public myEventAdapter_managers(Context context){
        this.context=context;
        this.mList = new ArrayList<>();
        this.eventsListFull = mList;
    }
    public void add(Event event){
        mList.add(event);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        //private ImageView mImageView;
        final TextView mTitle;
        final TextView mTime;
        final TextView mDate;
        final TextView mManager_email;
        final ImageView imageView1;
        public ViewHolder(View itemView, OnItemClickListener listener) {
            super(itemView);
            mTitle = itemView.findViewById(R.id.textview_title2);
            mTime = itemView.findViewById(R.id.textview_hour2);
            mDate = itemView.findViewById(R.id.textview_date2);
            mManager_email = itemView.findViewById(R.id.textview_managerEmail2);
            imageView1 = itemView.findViewById(R.id.delete);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                            //todo pass data to details activity after event clicks item
                            Event item = new Event();
                            Intent i = new Intent(context, EventDetails2.class);
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
        ViewHolder evh = new ViewHolder(v,mListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Event currentItem = mList.get(position);

        holder.mTitle.setText(currentItem.getTitle());
        holder.mTime.setText(currentItem.getTime());
        holder.mDate.setText(currentItem.getDate());
        holder.mManager_email.setText(currentItem.getManagersEmail());


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