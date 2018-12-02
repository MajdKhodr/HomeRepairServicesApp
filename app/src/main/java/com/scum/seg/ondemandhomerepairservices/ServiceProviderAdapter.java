package com.scum.seg.ondemandhomerepairservices;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class ServiceProviderAdapter extends RecyclerView.Adapter<ServiceProviderAdapter.ServiceHolder> {


    private static final String TAG = "ServiceProviderAdapter" ;
    private Context context;
    private List<User> serviceList;
    private Fragment fragment;
    private ServiceProvider provider = new ServiceProvider();

    public ServiceProviderAdapter(Context context, List<User> servicesList, Fragment fragment) {
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
        View view = inflater.inflate(R.layout.list_service_providers, null);

        return new ServiceHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final ServiceHolder serviceHolder, int i) {
        final User serviceProvider = serviceList.get(i);




        serviceHolder.mServiceProvider.setText(serviceProvider.getFirstName() + " " + serviceProvider.getLastName());
        serviceHolder.mDescription.setText(serviceProvider.getDescription());


        serviceHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (((User) ((Activity) (context)).getIntent().getSerializableExtra("User")).getType().equals("home owner")) {


                }


                return true;
            }


        });

    }

    public void addItem(User user) {
        serviceList.add(user);
        notifyItemInserted(serviceList.size() - 1);
    }

    @Override
    public int getItemCount() {
        return serviceList.size();
    }


    public class ServiceHolder extends RecyclerView.ViewHolder {
        TextView mServiceProvider;
        TextView mDescription;
        TextView mRating;


        public ServiceHolder(View itemView) {
            super(itemView);

            this.mServiceProvider = itemView.findViewById(R.id.service);
            this.mDescription = itemView.findViewById(R.id.description);
            this.mRating = itemView.findViewById(R.id.rating);
        }

    }


}
