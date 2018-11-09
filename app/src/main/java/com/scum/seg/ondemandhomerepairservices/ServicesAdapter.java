package com.scum.seg.ondemandhomerepairservices;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

public class ServicesAdapter extends RecyclerView.Adapter<ServicesAdapter.ServiceHolder> {


    private Context context;
    private List<Service> serviceList;

    public ServicesAdapter(Context context, List<Service> servicesList){
        this.serviceList = servicesList;
        this.context = context;
    }

    @NonNull
    @Override
    public ServiceHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_services, null);

        return new ServiceHolder(view);
       }

    @Override
    public void onBindViewHolder(@NonNull ServiceHolder serviceHolder, int i) {
        Service service = serviceList.get(i);

        double rate = service.getServiceRate();
        String stringRate = "";

        if(rate == (long) rate){
            stringRate = String.format("%d", (long) rate);
        }else{
            stringRate = String.format("%s", rate);
        }

        serviceHolder.mServiceRate.setText(stringRate + " $/h");
        serviceHolder.mService.setText(service.getServiceName());

    }

    @Override
    public int getItemCount() {
        return serviceList.size();
    }

    public void removeItem(int position){
        serviceList.remove(position);
        notifyItemRemoved(position);
    }

    public void addItem(int position, Service service){
        serviceList.add(service);
        notifyItemInserted(serviceList.size() - 1);
    }





    public class ServiceHolder extends RecyclerView.ViewHolder{
        TextView mService;
        TextView mServiceRate;


        public ServiceHolder (View itemView){
            super(itemView);

            mService = itemView.findViewById(R.id.textview_service_value);
            mServiceRate = itemView.findViewById(R.id.textview_servicerate_value);
        }

    }



}
