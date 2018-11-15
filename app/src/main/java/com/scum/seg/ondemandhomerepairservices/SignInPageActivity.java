package com.scum.seg.ondemandhomerepairservices;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

// Required for firebase
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.scum.seg.ondemandhomerepairservices.Utils.AESCrypt;


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
        final EditText mUsername = findViewById(R.id.materialEditText);
        final EditText mPassword = findViewById(R.id.password_signin_edittext);

        // Get database reference
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Users");

        // Add on data change listener to database to fetch data
        ValueEventListener userListener = new ValueEventListener() {
            User user;
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    boolean found = false;
                    // Loop through list of users checking if the given username and password match an account
                    for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                        user = childSnapshot.getValue(User.class);
                        if (user.getUserName().equals(mUsername.getText().toString().trim())
                                && AESCrypt.decrypt(user.getPassword()).equals(mPassword.getText().toString().trim())) {
                            found = true;
                            user.setKey(childSnapshot.getKey());
                            break;
                        }
                    }
                    if (!found) {
                        Toast.makeText(SignInPageActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                    }else{
                        homeActivity(user);
                    }

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
        Intent intent = new Intent(this, TypeActivity.class);
        startActivity(intent);
    }

    public void homeActivity(User user) {
        Intent intent = new Intent(this, HomeActivity.class);
        intent.putExtra("User", user);
        startActivity(intent);
        finish();
    }


}
