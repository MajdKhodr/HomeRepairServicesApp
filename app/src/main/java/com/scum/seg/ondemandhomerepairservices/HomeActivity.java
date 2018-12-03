package com.scum.seg.ondemandhomerepairservices;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.Spinner;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.scum.seg.ondemandhomerepairservices.Utils.BottomNavHelper;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity{

    private User user;
    private static final String TAG = "MAINACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        setupBottomNav();

        user = (User) getIntent().getSerializableExtra("User");

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragmentMain = fm.findFragmentById(R.id.fragment);
        Fragment fragmentUsers = fm.findFragmentById(R.id.users);

        if (fragmentMain == null) {
            fragmentMain = new MainFragment();
            fm.beginTransaction().add(R.id.fragment, fragmentMain).commit();
        }
        if (fragmentUsers == null && (user.getType().equals("admin"))) {
            fragmentUsers = new ListOfUsersFragment();
            fm.beginTransaction().add(R.id.users, fragmentUsers).commit();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void setupBottomNav() {
        AHBottomNavigation bottomNavigation = findViewById(R.id.bottom_navigation);
        final BottomNavHelper bottomNavHelper = new BottomNavHelper(bottomNavigation);
        bottomNavHelper.setListeners(this);

    }

    public void signOut(View view) {
        Intent intent = new Intent(this, SignInPageActivity.class);
        startActivity(intent);
        finish();
    }


}
