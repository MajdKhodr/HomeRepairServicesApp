package com.scum.seg.ondemandhomerepairservices;

import java.io.Serializable;
import java.util.Date;

public class Availability implements Serializable {

    private Date date;

    public Availability(){

    }

    public Availability(Date date){
        this.date = date;
    }

    public Date getDate() {
        return date;
    }
}
