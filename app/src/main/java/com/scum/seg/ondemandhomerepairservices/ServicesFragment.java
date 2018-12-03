package com.scum.seg.ondemandhomerepairservices;

import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.github.clans.fab.FloatingActionMenu;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;


public class ServicesFragment extends Fragment {

    private static final String TAG = "ServicesFragment";
    private RecyclerView mServiceRecyclerView;
    private ServicesAdapter mServicesAdapter;

    private SearchView searchView = null;
    private SearchView.OnQueryTextListener queryTextListener;

    private List<Service> mServiceList;
    private User user;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mServiceList = new ArrayList<>();
        user = ((User) getActivity().getIntent().getSerializableExtra("User"));
    }

    @SuppressLint("RestrictedApi")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: Passed by onCreate");
        View fragment = inflater.inflate(R.layout.fragment_services, container, false);
        Log.d(TAG, "onCreateView: Didnt crash before getting here");
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
        } else if (user.getType().equals("service provider")) {
            FloatingActionMenu menu = fragment.findViewById(R.id.floatingMenu);
            menu.setVisibility(View.VISIBLE);
            com.github.clans.fab.FloatingActionButton fabCal = fragment.findViewById(R.id.fabCal);
            fabCal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openCalendar();
                }
            });
        }else if(user.getType().equals("home owner")){
            setHasOptionsMenu(true);
        }


        return fragment;
    }


    public void addService() {
        Intent intent = new Intent(getActivity(), ServiceActivity.class);
        intent.putExtra("User", user);
        startActivityForResult(intent, 0);
    }

    public void openCalendar() {
        Intent intent = new Intent(getActivity(), AvailabilityActivity.class);
        intent.putExtra("User", user);
        startActivity(intent);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                Service service = (Service) data.getSerializableExtra("Service");
                mServicesAdapter.addItem(service);
            }
        } else if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Service service = (Service) data.getSerializableExtra("Service");
                mServicesAdapter.replaceItem((int) data.getSerializableExtra("ServicePosition"), service);
            }
        }
    }


    private void setupRecyclerView(final View fragment) {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Services");
        final View v = fragment;
        ValueEventListener serviceListener = new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                mServiceList = new ArrayList<>();
                // Loop through list of services
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    Service service = childSnapshot.getValue(Service.class);
                    service.setKey(childSnapshot.getKey());
                    mServiceList.add(service);

                    int counter = 0;
                    for (DataSnapshot child : childSnapshot.getChildren()) {

                        if (counter == 0 && child.hasChildren()) {
                            for(DataSnapshot children : child.getChildren()){
                                if (children.getValue().equals(user.getKey())) {
                                    service.setAssigned(true);
                                }
                            }

                        }
                        counter++;

                    }
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
        mDatabase.addListenerForSingleValueEvent(serviceListener);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.toolbar_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);

        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));

            queryTextListener = new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextChange(String newText) {

                    List<Service> searchedServiceList = new ArrayList<>();

                    if(!newText.equals("")){
                        for(Service service : mServiceList){
                            Log.d(TAG, service.getServiceName() + "Is currently in the list");
                            if(service.getServiceName().toLowerCase().equals(newText.toLowerCase())){
                                searchedServiceList.add(service);
                            }

                        }

                        if(!searchedServiceList.isEmpty()){

                            mServicesAdapter.replaceAll(searchedServiceList);
                        }

                    }

                    else{
                        mServicesAdapter.returnToOriginal();

                    }



                    return true;
                }
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return true;
                }
            };
            searchView.setOnQueryTextListener(queryTextListener);
        }
        super.onCreateOptionsMenu(menu, inflater);

    }


}
