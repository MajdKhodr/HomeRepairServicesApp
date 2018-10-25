package com.scum.seg.ondemandhomerepairservices;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.scum.seg.ondemandhomerepairservices.Utils.BottomNavHelper;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        setupBottomNav();


        FragmentManager fm = getSupportFragmentManager();
        Fragment fragmentMain = fm.findFragmentById(R.id.fragment);
        Fragment fragmentUsers = fm.findFragmentById(R.id.users);

        if (fragmentMain == null) {
            fragmentMain = new MainFragment();
            fm.beginTransaction().add(R.id.fragment, fragmentMain).commit();
        }
        if (fragmentUsers == null && ((User) getIntent().getSerializableExtra("User")).getType().equals("admin")) {
            fragmentUsers = new ListOfUsersFragment();
            fm.beginTransaction().add(R.id.users, fragmentUsers).commit();
        }
    }

    private void setupBottomNav() {
        AHBottomNavigation bottomNavigation = findViewById(R.id.bottom_navigation);
        final BottomNavHelper bottomNavHelper = new BottomNavHelper(bottomNavigation);


//        bottomNavHelper.setListeners(this);

    }

    public void signOut(View view) {
        Intent intent = new Intent(this, SignInPageActivity.class);
        startActivity(intent);
        finish();
    }
}
