package com.scum.seg.ondemandhomerepairservices;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

// Required for firebase
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class SignInPageActivity extends AppCompatActivity {

    private static final String TAG = "SignInPageActivity";

    Button mSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_page);
        mSignIn = findViewById(R.id.sign_in_button);

    }

    public void signInUser(View view) {
        // Get editText reference
        final EditText mUsername = findViewById(R.id.username_signin_edittext);
        final EditText mPassword = findViewById(R.id.password_signin_edittext);

        // Get database reference
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Users");

        // Add on data change listener to database to fetch data
        ValueEventListener userListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    boolean found = false;
                    // Loop through list of users checking if the given username and password match an account
                    String database = dataSnapshot.getValue(String.class);
                    Log.d(TAG, "Database : " + database);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        mDatabase.addValueEventListener(userListener);
    }

    // Sends user to sign up page
    public void signUpUser(View view) {
        Intent intent = new Intent(this, SignUpPageActivity.class);
        startActivity(intent);

    }

    public void homeActivity() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }


}
