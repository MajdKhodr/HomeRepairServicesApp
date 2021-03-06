package com.scum.seg.ondemandhomerepairservices;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.scum.seg.ondemandhomerepairservices.Utils.AESCrypt;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpPageActivity extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth;

    private EditText mFirstName;
    private EditText mLastName;
    private EditText mEmail;
    private EditText mUsername;
    private EditText mPassword;
    private EditText mPhoneNumber;
    private EditText mAddress;
    private EditText mCompanyName;
    private EditText mDescription;
    private CheckBox mIsLicensed;
    private String type;
    private static final String TAG = "SignUpPageActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);
        mFirebaseAuth = FirebaseAuth.getInstance();
        type = (String) getIntent().getSerializableExtra("Type");


        mFirstName = findViewById(R.id.first_name_edittext);
        mLastName = findViewById(R.id.last_name_edittext);
        mEmail = findViewById(R.id.email_edittext);
        mUsername = findViewById(R.id.username_edittext);
        mPassword = findViewById(R.id.password_editText);
        mAddress = findViewById(R.id.address_editText);
        mPhoneNumber = findViewById(R.id.phoneNumber_editText);

        if (type.equals("service provider")) {
            mCompanyName = findViewById(R.id.company_name_editText);
            mDescription = findViewById(R.id.description_editText);
            mIsLicensed = findViewById(R.id.isLicensed_checkBox);

            mCompanyName.setVisibility(View.VISIBLE);
            mDescription.setVisibility(View.VISIBLE);
            mIsLicensed.setVisibility(View.VISIBLE);
        }

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

        if (type.equals("service provider")) {
            registerServiceProvider(view);
        }
        else {
            registerAdmin(view);
        }

    }


    public void registerServiceProvider(View view) {
        final String email = mEmail.getText().toString();
        final String firstname = mFirstName.getText().toString();
        final String lastname = mLastName.getText().toString();
        final String username = mUsername.getText().toString();
        final String phonenumber = mPhoneNumber.getText().toString();
        final String address = mAddress.getText().toString();
        final String companyName = mCompanyName.getText().toString();
        final String description = mDescription.getText().toString();
        final boolean isLicensed;

        if (mIsLicensed.isChecked()) {
            isLicensed = true;
        }
        else {
            isLicensed = false;
        }

        final String type = (String) getIntent().getSerializableExtra("Type");

        boolean firstNameIsValid = validateFirstName(firstname);
        boolean lastNameIsValid = validateLastName(lastname);
        boolean emailIsValid = validateEmail(email);
        boolean numberIsValid = validatePhoneNumber(phonenumber);
        boolean addressIsValid = validateAddress(address);
        boolean userNameIsValid = validateUsername(username);
        boolean companyNameIsValid = validateCompanyName(companyName);

        String toastMessage = "The following fields are either missing or the information you gave is not valid:\n";

        if (!firstNameIsValid) {
            toastMessage += "\nFirst Name (Make sure it includes only letters and no symbols and that it is not empty)\n";
        }

        if (!lastNameIsValid) {
            toastMessage += "\nLast Name (Make sure it includes only letters and no symbols and that it is not empty)\n";
        }

        if (!emailIsValid) {
            toastMessage += "\nEmail (Make sure it follows the following format \"abc@gmail.com\" and that it is not empty)\n";
        }

        if (!numberIsValid) {
            toastMessage += "\nPhone Number (Make sure it includes nothing but numbers and follows the following format \"1234567899\" and that it is not empty)";
        }

        if(!addressIsValid){
            toastMessage += "\nAddress (Make sure it is not empty)\n";
        }

        if(!userNameIsValid){
            toastMessage += "\nUsername (Make sure it's not empty)\n";
        }

        if(!companyNameIsValid) {
            toastMessage += "\nCompany Name (Make sure it's not empty)\n";
        }


        if (userNameIsValid && numberIsValid && firstNameIsValid && emailIsValid && lastNameIsValid && addressIsValid && companyNameIsValid && !mPassword.getText().toString().isEmpty()) {
            try {
                final String password = AESCrypt.encrypt(mPassword.getText().toString());
                mFirebaseAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(SignUpPageActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Log.d(TAG, "onComplete: I AM HERE");
                                if (!task.isSuccessful()) {
                                    Toast.makeText(SignUpPageActivity.this, "Authentication Failed." + task.getException(),
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(SignUpPageActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                                    generateServiceProvider(firstname, lastname, username, password, email, phonenumber, address, type, companyName, description, isLicensed);
                                    signInUser();
                                    finish();
                                }
                            }
                        });
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            Toast toast = Toast.makeText(getApplicationContext(),
                    toastMessage,
                    Toast.LENGTH_LONG);

            toast.show();
        }
    }

    public void registerAdmin(View view) {
        final String email = mEmail.getText().toString();
        final String firstname = mFirstName.getText().toString();
        final String lastname = mLastName.getText().toString();
        final String username = mUsername.getText().toString();
        final String phonenumber = mPhoneNumber.getText().toString();
        final String address = mAddress.getText().toString();
        final String type = (String) getIntent().getSerializableExtra("Type");

        boolean firstNameIsValid = validateFirstName(firstname);
        boolean lastNameIsValid = validateLastName(lastname);
        boolean emailIsValid = validateEmail(email);
        boolean numberIsValid = validatePhoneNumber(phonenumber);
        boolean addressIsValid = validateAddress(address);
        boolean userNameIsValid = validateUsername(username);

        String toastMessage = "The following fields are either missing or the information you gave is not valid:\n";

        if (!firstNameIsValid) {
            toastMessage += "\nFirst Name (Make sure it includes only letters and no symbols and that it is not empty)\n";
        }

        if (!lastNameIsValid) {
            toastMessage += "\nLast Name (Make sure it includes only letters and no symbols and that it is not empty)\n";
        }

        if (!emailIsValid) {
            toastMessage += "\nEmail (Make sure it follows the following format \"abc@gmail.com\" and that it is not empty)\n";
        }

        if (!numberIsValid) {
            toastMessage += "\nPhone Number (Make sure it includes nothing but numbers and follows the following format \"1234567899\" and that it is not empty)";
        }

        if(!addressIsValid){
            toastMessage += "\nAddress (Make sure it is not empty)\n";
        }

        if(!userNameIsValid){
            toastMessage += "\nUsername (Make sure it's not empty)\n";
        }


        if (userNameIsValid && numberIsValid && firstNameIsValid && emailIsValid && lastNameIsValid && addressIsValid && !mPassword.getText().toString().isEmpty()) {
            try {
                final String password = AESCrypt.encrypt(mPassword.getText().toString());
                mFirebaseAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(SignUpPageActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (!task.isSuccessful()) {
                                    Toast.makeText(SignUpPageActivity.this, "Authentication Failed." + task.getException(),
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(SignUpPageActivity.this, "Registration Successful" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                                    generateUser(firstname, lastname, username, password, email, phonenumber, address, type);
                                    signInUser();
                                    finish();
                                }
                            }
                        });
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            Toast toast = Toast.makeText(getApplicationContext(),
                    toastMessage,
                    Toast.LENGTH_LONG);

            toast.show();
        }
    }


    private boolean validateUsername(String username) {
        return !username.isEmpty();
    }

    private boolean validatePassword(String password) {
        return !password.isEmpty();
    }

    private boolean validateAddress(String address) {
        return !address.isEmpty();
    }

    private boolean validateLastName(String lastname) {
        boolean lastNameIsValid = true;

        if(lastname.isEmpty()){
            return false;
        }

        for (int i = 0; i < lastname.length(); i++) {
            if ((!Character.isLetter(lastname.charAt(i))) && (lastname.charAt(i) != ' ')) {
                lastNameIsValid = false;
            }
        }

        return lastNameIsValid;
    }

    private boolean validateFirstName(String firstname) {
        boolean firstNameIsValid = true;

        if(firstname.isEmpty()){
            return false;
        }

        for (int i = 0; i < firstname.length(); i++) {
            if ((!Character.isLetter(firstname.charAt(i))) && (firstname.charAt(i) != ' ')) {
                firstNameIsValid = false;
            }
        }

        return firstNameIsValid;
    }

    private boolean validatePhoneNumber(String phonenumber) {
        for (int i = 0; i < phonenumber.length(); i++) {
            if (!Character.isDigit(phonenumber.charAt(i))) {
                return false;
            }
        }

        return true;
    }

    private boolean validateEmail(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private boolean validateCompanyName(String companyName){ return !companyName.isEmpty(); }


    public void generateUser(String firstName, String lastName, String userName, String password,
                             String email, String phonenumber, String address, String type) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference users = database.getReference("Users");
        User user = new User(firstName, lastName, userName, password, email, phonenumber, address, type);
        users.push().setValue(user);
    }

    public void generateServiceProvider(String firstName, String lastName, String userName, String password,
                                        String email, String phonenumber, String address, String type,
                                        String companyName, String description, boolean isLicensed){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference users = database.getReference("Users");
        User user = new User(firstName, lastName, userName, password, email, phonenumber, address, type, companyName, description, isLicensed);
        users.push().setValue(user);
    }

    public void signInUser() {
        Intent intent = new Intent(this, SignInPageActivity.class);
        startActivity(intent);
    }
}
