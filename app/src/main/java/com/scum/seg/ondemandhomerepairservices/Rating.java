package com.scum.seg.ondemandhomerepairservices;

import java.io.Serializable;

public class Rating implements Serializable {

    private int rating;
    private String commmet;

    public Rating(int rating, String comment) {
        this.rating = rating;
        this.commmet = comment;
    }

    public Rating(){

    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setCommmet(String commmet) {
        this.commmet = commmet;
    }

    public int getRating() { return rating; }

    public String getCommmet() {
        return commmet;
    }
}
