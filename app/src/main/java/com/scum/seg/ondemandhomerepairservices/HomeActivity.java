package com.scum.seg.ondemandhomerepairservices;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment);

        if (fragment == null){
            fragment = new MainFragment();
            fm.beginTransaction().add(R.id.fragment, fragment).commit();
        }





    }
}
