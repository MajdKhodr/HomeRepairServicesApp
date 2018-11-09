package com.scum.seg.ondemandhomerepairservices;

import java.io.Serializable;

public class Service implements Serializable {

    private String serviceName;
    private float serviceRate;

    public Service(String serviceName, float serviceRate) {
        this.serviceName = serviceName;
        this.serviceRate = serviceRate;
    }

    public Service(){

    }

    public String getServiceName() {
        return serviceName;
    }

    public float getServiceRate() {
        return serviceRate;
    }
}
