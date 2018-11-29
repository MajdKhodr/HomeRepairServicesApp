package com.scum.seg.ondemandhomerepairservices;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;


public class AvailabilityAdapter extends RecyclerView.Adapter<AvailabilityAdapter.MyViewHolder> {
    private ArrayList<Availability> mDataset;
    private Context mContext;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView mTime;
        TextView mAvailability;

        public MyViewHolder(View v) {
            super(v);
            mTime = itemView.findViewById(R.id.time);
            mAvailability = itemView.findViewById(R.id.available);
        }
    }

    public AvailabilityAdapter(ArrayList<Availability> myDataset, Context context) {
        mContext = context;
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public AvailabilityAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                               int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.list_availability, null);


        MyViewHolder vh = new MyViewHolder(view);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.mTime.setText(Long.toString(mDataset.get(position).getTime()));
        holder.mAvailability.setText(mDataset.get(position).getDesc());

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}

