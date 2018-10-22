package com.scum.seg.ondemandhomerepairservices;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TypeActivity extends AppCompatActivity {

    Button mAdminType;
    Button mHomeOwnerType;
    Button mServiceProviderType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type);

        mAdminType = findViewById(R.id.type_admin);
        mHomeOwnerType = findViewById(R.id.type_homeowner);
        mServiceProviderType = findViewById(R.id.type_serviceprovider);

        checkAdmin();


    }


    public void checkAdmin() {
        // Get database reference
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Users");

        // Add on data change listener to database to fetch data
        ValueEventListener userListener = new ValueEventListener() {
            User user;

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                boolean found = false;
                // Loop through list of users checking if the given username and password match an account
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    user = childSnapshot.getValue(User.class);
                    if (user.getType().equals("admin")) {
                        found = true;
                        break;
                    }
                }
                if (found) {
                    mAdminType.setEnabled(false);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        };
        mDatabase.addValueEventListener(userListener);
    }


    public void typeChosen(View view){
        Button b = (Button) view;
        String type = b.getText().toString();

        Intent intent = new Intent(this, SignUpPageActivity.class);
        intent.putExtra("Type", type);
        startActivity(intent);
    }
}


