package com.scum.seg.ondemandhomerepairservices;


public class Availability {
    private long time;
    private String desc;

    public Availability(){

    }

    public Availability(long time, String desc){
        this.time = time;
        this.desc = desc;

    }

    public long getTime() {
        return time;
    }


    public String getDesc() {
        return desc;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
