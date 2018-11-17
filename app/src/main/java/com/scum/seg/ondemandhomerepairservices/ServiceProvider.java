package com.scum.seg.ondemandhomerepairservices;

import java.util.ArrayList;
import java.util.ServiceConfigurationError;

public class ServiceProvider extends User {

    private ArrayList<Service> services = new ArrayList<>();

    public void addService(Service service) {
        services.add(service);
    }

}
