package com.scum.seg.ondemandhomerepairservices;

import java.io.Serializable;

public class Availability implements Serializable {

    private String startDate;
    private String endDate;

    public Availability(){

    }

    public Availability(String startDate, String endDate){
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }
}
