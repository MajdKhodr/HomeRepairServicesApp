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
        registerUser();

        mFirstName = findViewById(R.id.firstname_signup_edittext);
        mLastName = findViewById(R.id.lastname_signup_edittext);
        mEmail = findViewById(R.id.email_signup_edittext);
        mUserName = findViewById(R.id.username_signup_edittext);
        mPassword = findViewById(R.id.password_signup_editText);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if(mFirebaseAuth.getCurrentUser() != null){
            //handle the already logged in user
            //TODO: implement
        }
    }



    private void registerUser(){
        // TODO: Add actual values and type
        final String email = mEmail.getText().toString();
        final String password = mPassword.getText().toString();
        final String firstname = mFirstName.getText().toString();
        final String lastname = mLastName.getText().toString();
        final String username = mUserName.getText().toString();


        mFirebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(SignUpPageActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Toast.makeText(SignUpPageActivity.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();

                        if (!task.isSuccessful()) {
                            Toast.makeText(SignUpPageActivity.this, "Authentication failed." + task.getException(),
                                    Toast.LENGTH_SHORT).show();
                            generateUser(firstname,lastname, username, password, email);
                        } else {
                            startActivity(new Intent(SignUpPageActivity.this, MainActivity.class));
                            finish();
                        }
                    }
                });
    }

    public void generateUser(String firstName, String lastName, String userName, String passsord, String email){
        //TODO Hash Passwords
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference users = database.getReference("users");
        User user = new User(firstName, lastName, userName, passsord, email);
        users.push().setValue(user);
    }
}
