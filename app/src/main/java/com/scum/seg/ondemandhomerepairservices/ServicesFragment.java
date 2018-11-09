package com.scum.seg.ondemandhomerepairservices;

import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

        mServiceRecyclerView = fragment.findViewById(R.id.services_recyclerview);
        mServiceRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


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
                View itemView = viewHolder.itemView;
                int itemHeight = itemView.getBottom() - itemView.getTop();

                itemView.setBackgroundColor(Color.parseColor("#ff0000"));
                itemView.draw(c);

                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);

        itemTouchHelper.attachToRecyclerView(mServiceRecyclerView);

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
}
