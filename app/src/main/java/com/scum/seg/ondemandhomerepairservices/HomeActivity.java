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

        mWelcomeTxt = "Welcome " + getString(R.string.app_name)
        + ",\nYou are logged in as the " + getString(R.string.app_name);

        mWelcomeMessage.setText(mWelcomeTxt);

    }
}
