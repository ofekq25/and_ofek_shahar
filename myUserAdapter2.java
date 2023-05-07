package com.example.firetry1;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


public class myUserAdapter2 extends RecyclerView.Adapter<myUserAdapter2.ViewHolder> implements Filterable {
    private static Context context;
    private static List<UserName> mList;
    private static List<UserName> usersListFull;
    private OnItemClickListener mListener;
    private List<UserName> exampleListFull;





    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }


    public myUserAdapter2(Context context){
        this.context=context;
        this.mList = new ArrayList<>();
        this.usersListFull = mList;
    }
    public void add(UserName user){
        mList.add(user);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        //private ImageView mImageView;
        final TextView mName;
        final TextView mEmail;
        final TextView mText;


        public ViewHolder(View itemView, OnItemClickListener listener) {
            super(itemView);
            mName = itemView.findViewById(R.id.textview);
            mEmail = itemView.findViewById(R.id.textview2);
            mText = itemView.findViewById(R.id.textview3);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                            //todo pass data to details activity after user clicks item
                            UserName item = new UserName();
                            Intent i = new Intent(context, UserDetails.class);
                            i.putExtra("name", mList.get(position).getName());
                            i.putExtra("email", mList.get(position).getEmail());
                            i.putExtra("text", "");
                            context.startActivity(i);
                        }
                    }
                }
            });




        }

    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item_design, parent, false);
        ViewHolder evh = new ViewHolder(v,mListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        UserName currentItem = mList.get(position);

        holder.mName.setText(currentItem.getName());
        holder.mEmail.setText(currentItem.getEmail());
        holder.mText.setText("");

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
            List<UserName> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(mList);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (UserName item : mList) {
                    if (item.getName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

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
        return usersFilter;
    }

    private final Filter usersFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {

            ArrayList<UserName> filteredUsersList = new ArrayList<>();

            if (charSequence == null || charSequence.length() == 0) {
                filteredUsersList.addAll(usersArrayListFull);
            } else {

                String filterPattern = charSequence.toString().toLowerCase().trim();
                for (UserName user: usersArrayListFull){
                    if(user.getName().toLowerCase().contains(filterPattern))
                        filteredUsersList.add(user);

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