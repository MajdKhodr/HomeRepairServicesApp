package com.scum.seg.ondemandhomerepairservices;

import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class test {

    private static final String TAG = "test";

    public test(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");
        myRef.setValue("Hello World");
        Log.d(TAG,"here");

    }

}
