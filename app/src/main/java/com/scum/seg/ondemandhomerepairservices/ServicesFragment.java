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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;


public class ServicesFragment extends Fragment {

    private RecyclerView mServiceRecyclerView;
    private ServicesAdapter mServicesAdapter;

    private List<Service> mServiceList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mServiceList = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragment = inflater.inflate(R.layout.fragment_services, container, false);

        //Checks if the user is an admin to display the add button
        if (((User) getActivity().getIntent().getSerializableExtra("User")).getType().equals("admin")) {
            FloatingActionButton addButton = fragment.findViewById(R.id.floatingActionButton);
            addButton.setVisibility(View.VISIBLE);
            addButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    addService();
                }
            });
        }

        mServiceRecyclerView = fragment.findViewById(R.id.services_recyclerview);
        mServiceRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        if (((User) getActivity().getIntent().getSerializableExtra("User")).getType().equals("admin")) {
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
                            c.drawRoundRect((float) itemView.getLeft() , (float) itemView.getTop(), dX,
                                    (float) itemView.getBottom(),16,16,p);

                        } else {
                            p.setColor(Color.parseColor("#CC0000"));
                            c.drawRoundRect((float) itemView.getRight() + dX, (float) itemView.getTop(),
                                    (float) itemView.getRight(), (float) itemView.getBottom(), 16,16,p);
                        }

                        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

                    }
                }
            };


            ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);


            itemTouchHelper.attachToRecyclerView(mServiceRecyclerView);
        }

        mServiceList.add(new Service("Service Name", 21331));
        mServiceList.add(new Service("Service Name", 21331));
        mServiceList.add(new Service("Service Name", 21331));
        mServiceList.add(new Service("Service Name", 21331));
        mServiceList.add(new Service("Service Name", 21331));
        mServiceList.add(new Service("Service Name", 21331));
        mServiceList.add(new Service("Service Name", 21331));
        mServiceList.add(new Service("Service Name", 21331));
        mServiceList.add(new Service("Service Name", 21331));


        mServicesAdapter = new ServicesAdapter(getActivity(), mServiceList);
        mServiceRecyclerView.setAdapter(mServicesAdapter);

        return fragment;
    }

    public void addService() {
        Intent intent = new Intent(getActivity(), AdminActivity.class);
        //intent.putExtra("User", user);
        getActivity().startActivity(intent);
    }
}
