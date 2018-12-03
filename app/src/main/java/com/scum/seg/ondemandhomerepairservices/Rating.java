package com.scum.seg.ondemandhomerepairservices;

import java.io.Serializable;

public class Rating implements Serializable {

    private float rating;
    private String commmet;

    public Rating(float rating, String comment) {
        this.rating = rating;
        this.commmet = comment;
    }

    public Rating(){

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
