package com.scum.seg.ondemandhomerepairservices;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.scum.seg.ondemandhomerepairservices.Utils.AESCrypt;

public class ViewRatings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_ratings);

        User user = (User) getIntent().getSerializableExtra("ServiceProvider");

        final TextView textView = findViewById(R.id.textView7);

        // Get database reference
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Users/" + user.getKey() + "/ServiceRating");

        // Add on data change listener to database to fetch data
        ValueEventListener userListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    ServiceRating rating = childSnapshot.getValue(ServiceRating.class);
                    textView.setText(textView.getText() + "\nRating " + rating.getRating()+"/5.0" + "\nComment \n" + rating.getCommmet());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        mDatabase.addValueEventListener(userListener);

    }
}
