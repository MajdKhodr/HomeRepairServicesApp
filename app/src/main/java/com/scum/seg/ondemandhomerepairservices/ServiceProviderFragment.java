package com.scum.seg.ondemandhomerepairservices;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

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
        setHasOptionsMenu(true);
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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.filter_menu,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final CharSequence[] timeAndRate = {"Time", "Rate","Cancel"};
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Filter Based On...");

        builder.setItems(timeAndRate, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(which == 0){
                    AlertDialog.Builder timeAlertDialog = new AlertDialog.Builder(getContext());
                    timeAlertDialog.setTitle("Time");

                    LinearLayout layout =  new LinearLayout(getContext());
                    layout.setOrientation(LinearLayout.VERTICAL);

                    CheckBox checkBox1 = new CheckBox(getContext());
                    checkBox1.setText("8:00 - 9:00");
                    CheckBox checkBox2 = new CheckBox(getContext());
                    checkBox2.setText("9:00 - 10:00");
                    CheckBox checkBox3 = new CheckBox(getContext());
                    checkBox3.setText("10:00 - 11:00");
                    CheckBox checkBox4 = new CheckBox(getContext());
                    checkBox4.setText("11:00 - 12:00");
                    CheckBox checkBox5 = new CheckBox(getContext());
                    checkBox5.setText("12:00 - 1:00");
                    CheckBox checkBox6 = new CheckBox(getContext());
                    checkBox6.setText("1:00 - 2:00");
                    CheckBox checkBox7 = new CheckBox(getContext());
                    checkBox7.setText("2:00 - 3:00");
                    CheckBox checkBox8 = new CheckBox(getContext());
                    checkBox8.setText("3:00 - 4:00");
                    CheckBox checkBox9 = new CheckBox(getContext());
                    checkBox9.setText("4:00 - 5:00");
                    CheckBox checkBox10 = new CheckBox(getContext());
                    checkBox10.setText("5:00 - 6:00");
                    CheckBox checkBox11 = new CheckBox(getContext());
                    checkBox11.setText("6:00 - 7:00");
                    CheckBox checkBox12 = new CheckBox(getContext());
                    checkBox12.setText("7:00 - 8:00");

                    layout.addView(checkBox1);
                    layout.addView(checkBox2);
                    layout.addView(checkBox3);
                    layout.addView(checkBox4);
                    layout.addView(checkBox5);
                    layout.addView(checkBox6);
                    layout.addView(checkBox7);
                    layout.addView(checkBox8);
                    layout.addView(checkBox9);
                    layout.addView(checkBox10);
                    layout.addView(checkBox11);
                    layout.addView(checkBox12);

                    timeAlertDialog.setView(layout).setPositiveButton("Filter", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //This is where you deal with the user clicking "Filter"

                        }
                    });

                    timeAlertDialog.show();

                }

                else if(which == 1){

                }

                else{

                }
            }
        });

        builder.show();

        return true;
    }
}
