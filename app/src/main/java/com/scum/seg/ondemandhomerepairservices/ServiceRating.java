package com.scum.seg.ondemandhomerepairservices;

import java.io.Serializable;

public class ServiceRating implements Serializable {

    private float rating;
    private String comment;

    public ServiceRating(float rating, String comment) {
        this.rating = rating;
        this.comment = comment;
    }

    public ServiceRating(){

    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public float getRating() { return rating; }

    public String getComment() {
        return comment;
    }
}
