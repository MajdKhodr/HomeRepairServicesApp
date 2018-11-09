package com.scum.seg.ondemandhomerepairservices;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
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
import java.util.List;

import static android.app.Activity.RESULT_OK;


public class ServicesFragment extends Fragment {

    private RecyclerView mServiceRecyclerView;
    private ServicesAdapter mServicesAdapter;

    private List<Service> mServiceList;
    private User user;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mServiceList = new ArrayList<>();
        user = ((User) getActivity().getIntent().getSerializableExtra("User"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragment = inflater.inflate(R.layout.fragment_services, container, false);
        setupRecyclerView(fragment);

        //Checks if the user is an admin to display the add button
        if (user.getType().equals("admin")) {
            FloatingActionButton addButton = fragment.findViewById(R.id.floatingActionButton);
            addButton.setVisibility(View.VISIBLE);
            addButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    addService();
                }
            });
        }


        return fragment;
    }


    public void addService() {
        Intent intent = new Intent(getActivity(), ServiceActivity.class);
        intent.putExtra("User", user);
        startActivityForResult(intent, 0);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                Service service = (Service) data.getSerializableExtra("Service");
                mServicesAdapter.addItem(service);
            }
        }else if (requestCode == 1){
            if(resultCode == RESULT_OK){
                Service service = (Service) data.getSerializableExtra("Service");
                mServicesAdapter.replaceItem((int) data.getSerializableExtra("ServicePosition"), service);
            }
        }
        Log.d("OnActivityResult", "I AM IN THE FRAGMENT");
    }

    private void setupRecyclerView(View fragment) {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Services");
        final View v = fragment;
        ValueEventListener serviceListener = new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("setupRecyclerView", "Here");

                mServiceList = new ArrayList<>();
                // Loop through list of services
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    mServiceList.add(childSnapshot.getValue(Service.class));
                }

                mServiceRecyclerView = v.findViewById(R.id.services_recyclerview);
                mServiceRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


                if (user.getType().equals("admin")) {
                    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                        @Override
                        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                            return false;
                        }

                        @Override
                        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                            mServicesAdapter.removeItem(viewHolder.getAdapterPosition());
                        }

                        @Override
                        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

                            if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                                // Get RecyclerView item from the ViewHolder
                                View itemView = viewHolder.itemView;

                                Paint p = new Paint();
                                if (dX > 0) {
                                    p.setColor(Color.parseColor("#CC0000"));
                                    c.drawRoundRect((float) itemView.getLeft(), (float) itemView.getTop(), dX,
                                            (float) itemView.getBottom(), 16, 16, p);

                                } else {
                                    p.setColor(Color.parseColor("#CC0000"));
                                    c.drawRoundRect((float) itemView.getRight() + dX, (float) itemView.getTop(),
                                            (float) itemView.getRight(), (float) itemView.getBottom(), 16, 16, p);
                                }

                                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

                            }
                        }
                    };


                    ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);


                    itemTouchHelper.attachToRecyclerView(mServiceRecyclerView);
                }
                Fragment fragment1 = getActivity().getSupportFragmentManager().findFragmentById(R.id.fragment);
                mServicesAdapter = new ServicesAdapter(getActivity(), mServiceList, fragment1);
                mServiceRecyclerView.setAdapter(mServicesAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        mDatabase.addValueEventListener(serviceListener);
    }
}
