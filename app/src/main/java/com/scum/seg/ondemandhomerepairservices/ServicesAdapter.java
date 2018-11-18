package com.scum.seg.ondemandhomerepairservices;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class ServicesAdapter extends RecyclerView.Adapter<ServicesAdapter.ServiceHolder> {


    private Context context;
    private List<Service> serviceList;
    private android.support.v4.app.Fragment fragment;
    private ServiceProvider provider = new ServiceProvider();

    public ServicesAdapter(Context context, List<Service> servicesList, android.support.v4.app.Fragment fragment) {
        this.serviceList = servicesList;
        this.context = context;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public ServiceHolder onCreateViewHolder(@NonNull final ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_services, null);

        final ServiceHolder v = new ServiceHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent intent;
                final Service service;
                intent = new Intent(context, ServiceActivity.class);
                service = serviceList.get(v.getAdapterPosition());
                intent.putExtra("Service", service);
                intent.putExtra("ServicePosition", v.getAdapterPosition());
                fragment.startActivityForResult(intent, 1);


            }
        });
        return v;
    }



    @Override
    public void onBindViewHolder(@NonNull final ServiceHolder serviceHolder, int i) {
        final Service service = serviceList.get(i);

        double rate = service.getServiceRate();
        String stringRate = "";

        if (rate == (long) rate) {
            stringRate = String.format("%d", (long) rate);
        } else {
            stringRate = String.format("%s", rate);
        }



        serviceHolder.mServiceRate.setText(stringRate + " $/h");
        serviceHolder.mService.setText(service.getServiceName());

        serviceHolder.itemView.setOnLongClickListener(new View.OnLongClickListener(){
            @Override
            public boolean onLongClick(View v) {
                if(((User) ((Activity) (context)).getIntent().getSerializableExtra("User")).getType().equals("service provider")){
                    final CharSequence[] add = {"Add","Cancel"};
                    final CharSequence[] delete = {"Remove","Cancel"};
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);

                    builder.setTitle("Select Action");

                    if(provider.getServices().contains(service)){
                        builder.setItems(delete, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(which == 0){
                                    provider.getServices().remove(service);
                                    serviceHolder.itemView.setBackgroundColor(android.R.drawable.btn_default);

                                }

                            }
                        });
                    }
                    else{
                        builder.setItems(add, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(which == 0){
                                    provider.addService(service);
                                    serviceHolder.itemView.setBackgroundColor(Color.parseColor("#6CABDD"));
                                }

                            }
                        });
                    }



                    builder.show();

                }

                return true;
            }


        });

    }

    @Override
    public int getItemCount() {
        return serviceList.size();
    }

    public void removeItem(final int position) {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Services");
        ValueEventListener serviceListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                int counter = 0;
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    if (counter == position) {
                        childSnapshot.getRef().setValue(null);
                    }
                    counter++;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        mDatabase.addListenerForSingleValueEvent(serviceListener);


        serviceList.remove(position);
        notifyItemRemoved(position);
    }

    public void addItem(Service service) {
        serviceList.add(service);
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Services");
        ref.push().setValue(service);
        notifyItemInserted(serviceList.size() - 1);
    }

    public void replaceItem(final int position, final Service service) {

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Services");
        ValueEventListener serviceListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("Database", "Updating from adapter");
                int counter = 0;
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    if (counter == position) {
                        childSnapshot.getRef().setValue(service);
                    }
                    counter++;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        mDatabase.addListenerForSingleValueEvent(serviceListener);

        serviceList.add(position, service);
        notifyDataSetChanged();
    }


    public class ServiceHolder extends RecyclerView.ViewHolder {
        TextView mService;
        TextView mServiceRate;


        public ServiceHolder(View itemView) {
            super(itemView);

            mService = itemView.findViewById(R.id.textview_service_value);
            mServiceRate = itemView.findViewById(R.id.textview_servicerate_value);
        }

    }


}
