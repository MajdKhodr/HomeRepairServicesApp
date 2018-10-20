package com.scum.seg.ondemandhomerepairservices;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpPageActivity extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth;

    private EditText mFirstName;
    private EditText mLastName;
    private EditText mEmail;
    private EditText mUserName;
    private EditText mPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);
        mFirebaseAuth = FirebaseAuth.getInstance();


        mFirstName = findViewById(R.id.first_name_edittext);
        mLastName = findViewById(R.id.last_name_edittext);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (mFirebaseAuth.getCurrentUser() != null) {
            //handle the already logged in user
            //TODO: implement
        }
    }


    private void registerUser() {
        // TODO: Add actual values and type
        final String email = "email2@gmail.com";
        final String firstname = "firstname2";
        final String lastname = "lastname2";
        final String username = "username2";
        final String phonenumber = "613-523-6763";
        final String address = "317 billings ave";
        final String type = "put enum here";

        try {
            final String password = AESCrypt.encrypt("password");

            mFirebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(SignUpPageActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(SignUpPageActivity.this, "Authentication failed." + task.getException(),
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(SignUpPageActivity.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                                generateUser(firstname, lastname, username, password, email, phonenumber, address, type);
                                finish();
                            }
                        }
                    });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void generateUser(String firstName, String lastName, String userName, String password,
                             String email, String phonenumber, String address, String type) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference users = database.getReference("Users");
        User user = new User(firstName, lastName, userName, password, email, phonenumber, address, type);
        users.push().setValue(user);
    }
}
