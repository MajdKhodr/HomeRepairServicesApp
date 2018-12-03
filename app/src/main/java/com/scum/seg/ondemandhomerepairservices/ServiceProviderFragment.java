package com.scum.seg.ondemandhomerepairservices;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ServiceProviderFragment extends Fragment {

    ArrayList<String> serviceProviders;
    ArrayList<User> providers;
    private static final String TAG = "ServiceProviderFragment";
    private RecyclerView mServiceRecyclerView;
    private ServiceProviderAdapter mServicesAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        providers = new ArrayList<>();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View v = inflater.inflate(R.layout.fragment_service_provider, container, false);

        mServiceRecyclerView = v.findViewById(R.id.service_provider_recyclerview);
        mServiceRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        Fragment fragment1 = getActivity().getSupportFragmentManager().findFragmentById(R.id.fragment);

        Bundle bundle = this.getArguments();
        Service service = null;
        if (bundle != null) {
            service = (Service) bundle.get("Service");


        }
        mServicesAdapter = new ServiceProviderAdapter(getActivity(), providers, fragment1, service);
        mServiceRecyclerView.setAdapter(mServicesAdapter);

        getServiceProvider();



        return v;
    }


    private void getServiceProvider() {
        Bundle bundle = this.getArguments();
        Service service;

        if (bundle != null) {
            service = (Service) bundle.get("Service");
        } else {
            return;
        }

        final int[] counter = {0};

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Services/" + service.getKey() + "/ServiceProviders");
        ValueEventListener serviceListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                serviceProviders = new ArrayList<>();
                // Loop through list of services
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    serviceProviders.add((String) childSnapshot.getValue());
                    Log.d(TAG, "onDataChange: " + childSnapshot.getValue().toString());
                    getProviders(counter[0]);
                    counter[0]++;
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        mDatabase.addListenerForSingleValueEvent(serviceListener);

    }

    private void getProviders(int i) {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Users/" + serviceProviders.get(i));
        ValueEventListener serviceListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                user.setKey(dataSnapshot.getKey());
                addUser(user);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        mDatabase.addListenerForSingleValueEvent(serviceListener);
    }

    private void addUser(User user){
        mServicesAdapter.addItem(user);
    }
    
}
