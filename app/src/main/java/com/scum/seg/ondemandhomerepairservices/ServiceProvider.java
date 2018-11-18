package com.scum.seg.ondemandhomerepairservices;

import java.util.ArrayList;
import java.util.ServiceConfigurationError;

public class ServiceProvider extends User {

    private ArrayList<Service> services = new ArrayList<>();

    public void addService(Service service) {
        services.add(service);
    }

    public ArrayList<Service> getServices() {
        return services;
    }

    public void removeService(Service service){
        services.remove(service);

    }
}
