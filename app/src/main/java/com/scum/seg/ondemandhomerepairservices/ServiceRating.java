package com.scum.seg.ondemandhomerepairservices;

import java.io.Serializable;

public class ServiceRating implements Serializable {

    private float rating;
    private String commmet;

    public ServiceRating(float rating, String comment) {
        this.rating = rating;
        this.commmet = comment;
    }

    public ServiceRating(){

    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public void setCommmet(String commmet) {
        this.commmet = commmet;
    }

    public float getRating() { return rating; }

    public String getCommmet() {
        return commmet;
    }
}
