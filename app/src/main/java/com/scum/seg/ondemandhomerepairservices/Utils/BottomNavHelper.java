package com.scum.seg.ondemandhomerepairservices.Utils;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.scum.seg.ondemandhomerepairservices.ListOfUsersFragment;
import com.scum.seg.ondemandhomerepairservices.MainFragment;
import com.scum.seg.ondemandhomerepairservices.R;
import com.scum.seg.ondemandhomerepairservices.ServicesFragment;
import com.scum.seg.ondemandhomerepairservices.UserProfileFragment;

public class BottomNavHelper {

    AHBottomNavigation bottomNavigation;


    public BottomNavHelper(AHBottomNavigation bottomNavigation) {

        this.bottomNavigation = bottomNavigation;

        // Creating the items
        AHBottomNavigationItem item1 = new AHBottomNavigationItem(R.string.home, R.drawable.baseline_android_24, R.color.colorAccent);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem(R.string.services, R.drawable.services, R.color.colorAccent);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem(R.string.settings, R.drawable.baseline_account_box_24, R.color.colorAccent);

        // Adding the items
        bottomNavigation.addItem(item1);
        bottomNavigation.addItem(item2);
        bottomNavigation.addItem(item3);


        // Configurations
        bottomNavigation.setBehaviorTranslationEnabled(false);
        bottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);

        // Theme
        bottomNavigation.setAccentColor(Color.parseColor("#6CABDD"));
        bottomNavigation.setDefaultBackgroundColor(Color.parseColor("#f8f8f8"));

    }

    public AHBottomNavigation getBottomNavigation() {
        return bottomNavigation;
    }

    public void setListeners(final AppCompatActivity activity) {
        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                // Main Page
                if (position == 0) {
                    Fragment fragment = new MainFragment();
                    Fragment fragment1 = new ListOfUsersFragment();

                    FragmentManager fm = activity.getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fm.beginTransaction();

                    fragmentTransaction.replace(R.id.fragment, fragment);
                    fragmentTransaction.replace(R.id.users, fragment1);

                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
                // Services Tab
                if (position == 1) {
                    Fragment fragment = new ServicesFragment();

                    FragmentManager fm = activity.getSupportFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    Fragment users = fm.findFragmentById(R.id.users);

                    ft.replace(R.id.fragment, fragment);
                    ft.addToBackStack(null);
                    if (users != null) {
                        ft.hide(users);
                    }

                    ft.commit();
                }
                // Settings Tab
                if(position == 2){
                    Fragment fragment = new UserProfileFragment();

                    FragmentManager fm = activity.getSupportFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();

                    Fragment users = fm.findFragmentById(R.id.users);

                    ft.replace(R.id.fragment,fragment);
                    ft.addToBackStack(null);
                    if (users != null) {
                        ft.hide(users);
                    }
                    ft.commit();

                }
                return true;
            }
        });

    }
}