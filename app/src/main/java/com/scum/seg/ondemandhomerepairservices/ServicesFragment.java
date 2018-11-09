package com.scum.seg.ondemandhomerepairservices;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import static android.content.Intent.getIntent;


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
            Button addButton = fragment.findViewById(R.id.add_button);
            addButton.setVisibility(View.VISIBLE);
        }

        mServiceRecyclerView = fragment.findViewById(R.id.services_recyclerview);
        mServiceRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mServiceRecyclerView.setHasFixedSize(true);

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

    public void addService(View view) {
        Intent intent = new Intent(getActivity(), AdminActivity.class);
        //intent.putExtra("User", user);
        getActivity().startActivity(intent);
    }
}
