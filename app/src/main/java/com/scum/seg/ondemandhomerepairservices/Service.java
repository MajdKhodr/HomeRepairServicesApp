package com.scum.seg.ondemandhomerepairservices;

import java.io.Serializable;

public class Service implements Serializable {

    private String serviceName;
    private float serviceRate;
    private String key;
    private boolean assigned;

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

    public void setServiceName(String name){serviceName = name;}

    public void setServiceRate(float rate){serviceRate = rate;}

    public boolean isAssigned() {
        return assigned;
    }

    public void setAssigned(boolean assigned) {
        this.assigned = assigned;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
