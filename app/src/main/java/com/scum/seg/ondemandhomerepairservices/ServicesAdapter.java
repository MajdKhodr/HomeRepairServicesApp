package com.scum.seg.ondemandhomerepairservices;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ServicesAdapter extends RecyclerView.Adapter<ServicesAdapter.ServiceHolder> {


    private static final String TAG = "ServicesAdapter" ;
    private Context context;
    private List<Service> serviceList;
    private android.support.v4.app.Fragment fragment;
    private ServiceProvider provider = new ServiceProvider();

    public ServicesAdapter(Context context, List<Service> servicesList, android.support.v4.app.Fragment fragment) {
        this.serviceList = servicesList;
        this.context = context;
        this.fragment = fragment;

        User user = (User) ((Activity) context).getIntent().getSerializableExtra("User");
        provider.setKey(user.getKey());

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
                User user = (User) ((Activity) context).getIntent().getSerializableExtra("User");

                if (user.getType().equals("admin")) {
                    final Intent intent;
                    final Service service;
                    intent = new Intent(context, ServiceActivity.class);
                    service = serviceList.get(v.getAdapterPosition());
                    intent.putExtra("Service", service);
                    intent.putExtra("ServicePosition", v.getAdapterPosition());
                    fragment.startActivityForResult(intent, 1);
                }else if(user.getType().equals("home owner")){

                    Fragment fragment = new ServiceProviderFragment();
                    AppCompatActivity activity = (AppCompatActivity) view.getContext();

                    FragmentManager fm = activity.getSupportFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();

                    Fragment users = fm.findFragmentById(R.id.users);

                    ft.replace(R.id.fragment,fragment);
                    ft.addToBackStack(null);
                    if (users != null) {
                        ft.hide(users);
                    }

                    Bundle bundle = new Bundle();
                    bundle.putSerializable("Service",serviceList.get(v.getAdapterPosition()));
                    fragment.setArguments(bundle);

                    ft.commit();




                }

            }
        });
        return v;
    }


    @Override
    public void onBindViewHolder(@NonNull final ServiceHolder serviceHolder, int i) {
        final Service service = serviceList.get(i);

        if(service.isAssigned()){
            serviceHolder.itemView.setBackgroundColor(Color.parseColor("#6CABDD"));
        }

        double rate = service.getServiceRate();
        String stringRate = "";

        if (rate == (long) rate) {
            stringRate = String.format("%d", (long) rate);
        } else {
            stringRate = String.format("%s", rate);
        }


        serviceHolder.mServiceRate.setText(stringRate + " $/h");
        serviceHolder.mService.setText(service.getServiceName());

        serviceHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (((User) ((Activity) (context)).getIntent().getSerializableExtra("User")).getType().equals("service provider")) {
                    final CharSequence[] add = {"Add", "Cancel"};
                    final CharSequence[] delete = {"Remove", "Cancel"};
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);

                    builder.setTitle("Select Action");

                    if ((service.isAssigned())) {
                        builder.setItems(delete, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (which == 0) {
                                    provider.getServices().remove(service);
                                    serviceHolder.itemView.setBackgroundColor(android.R.drawable.btn_default);
                                    service.setAssigned(false);

                                    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Services/" + service.getKey() + "/ServiceProviders");
                                    ValueEventListener serviceListener = new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                            for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                                                if (childSnapshot.getValue().equals(provider.getKey())) {
                                                    childSnapshot.getRef().setValue(null);
                                                }
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    };
                                    mDatabase.addListenerForSingleValueEvent(serviceListener);


                                }

                            }
                        });
                    } else {
                        builder.setItems(add, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (which == 0) {
                                    provider.addService(service);
                                    serviceHolder.itemView.setBackgroundColor(Color.parseColor("#6CABDD"));
                                    service.setAssigned(true);
                                    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Services/" + service.getKey() + "/ServiceProviders" );
                                    mDatabase.push().setValue(provider.getKey());
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

            mService = itemView.findViewById(R.id.text_view_service);
            mServiceRate = itemView.findViewById(R.id.textview_servicerate_value);
        }

    }

    public void replaceAll(List<Service> models) {

        for(int i = 0; i < models.size(); i ++){
            int index = 0;
            while(index < serviceList.size()){
                if(!serviceList.get(index).getServiceName().equals(models.get(i).getServiceName())){
                    serviceList.remove(index);
                    index = 0;
                }

                else{
                    index ++;
                }
            }

        }

        notifyDataSetChanged();

    }

    public void returnToOriginal(){
        serviceList = new ArrayList<>();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Services");
        ValueEventListener serviceListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Loop through list of services
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    Service service = childSnapshot.getValue(Service.class);
                    service.setKey(childSnapshot.getKey());
                    serviceList.add(service);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        };
        mDatabase.addListenerForSingleValueEvent(serviceListener);
        notifyDataSetChanged();


    }



    public void updateRecyclerView(List<Service> services){
        serviceList = new ArrayList<Service>();
        serviceList.addAll(services);
        notifyDataSetChanged();

    }


}
