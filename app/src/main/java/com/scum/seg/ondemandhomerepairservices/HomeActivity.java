package com.scum.seg.ondemandhomerepairservices;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {
    // Instance variable
    TextView mWelcomeMessage;
    String mWelcomeTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mWelcomeMessage = (TextView) findViewById(R.id.welcome_textview);

        mWelcomeTxt = "Welcome " + "FIRSTNAME"
        + ",\nYou are logged in as the " + "TYPE";

        mWelcomeMessage.setText(mWelcomeTxt);

    }
    //getString(R.string.INSERT ID)
}
