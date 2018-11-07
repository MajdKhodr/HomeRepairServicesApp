package com.scum.seg.ondemandhomerepairservices;

public class Service {

    private String serviceName;
    private float serviceRate;

    public Service(String serviceName, float serviceRate) {
        this.serviceName = serviceName;
        this.serviceRate = serviceRate;
    }

    public String getServiceName() {
        return serviceName;
    }

    public float getServiceRate() {
        return serviceRate;
    }
}
