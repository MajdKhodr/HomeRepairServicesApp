package com.scum.seg.ondemandhomerepairservices;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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
    private EditText mUsername;
    private EditText mPassword;
    private EditText mPhoneNumber;
    private EditText mAddress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);
        mFirebaseAuth = FirebaseAuth.getInstance();


        mFirstName = findViewById(R.id.first_name_edittext);
        mLastName = findViewById(R.id.last_name_edittext);
        mEmail = findViewById(R.id.email_edittext);
        mUsername = findViewById(R.id.username_edittext);
        mPassword = findViewById(R.id.password_editText);
        mAddress = findViewById(R.id.address_editText);
        mPhoneNumber = findViewById(R.id.phoneNumber_editText);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (mFirebaseAuth.getCurrentUser() != null) {
            //handle the already logged in user
            //TODO: implement
        }

    }


    public void registerUser(View view) {
        final String email = mEmail.getText().toString();
        final String firstname = mFirstName.getText().toString();
        final String lastname = mLastName.getText().toString();
        final String username = mUsername.getText().toString();
        final String phonenumber = mPhoneNumber.getText().toString();
        final String address = mAddress.getText().toString();
        final String type = (String) getIntent().getSerializableExtra("Type");

        try {
            final String password = AESCrypt.encrypt(mPassword.getText().toString());

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
                                signInUser();
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

    public void signInUser() {
        Intent intent = new Intent(this, SignInPageActivity.class);
        startActivity(intent);
    }
}
