package com.scum.seg.ondemandhomerepairservices.Utils;

import android.graphics.Color;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.scum.seg.ondemandhomerepairservices.R;

public class BottomNavHelper {

    AHBottomNavigation bottomNavigation;


    public BottomNavHelper(AHBottomNavigation bottomNavigation) {

        this.bottomNavigation = bottomNavigation;

        // Creating the items
        AHBottomNavigationItem item1 = new AHBottomNavigationItem(R.string.placeholder,R.drawable.baseline_android_24, R.color.colorAccent);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem(R.string.placeholder,R.drawable.baseline_android_24, R.color.colorAccent);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem(R.string.placeholder,R.drawable.baseline_android_24, R.color.colorAccent);
        AHBottomNavigationItem item4 = new AHBottomNavigationItem(R.string.placeholder,R.drawable.baseline_android_24, R.color.colorAccent);
        AHBottomNavigationItem item5 = new AHBottomNavigationItem(R.string.placeholder,R.drawable.baseline_android_24, R.color.colorAccent);

        // Adding the items
        bottomNavigation.addItem(item1);
        bottomNavigation.addItem(item2);
        bottomNavigation.addItem(item3);
        bottomNavigation.addItem(item4);
        bottomNavigation.addItem(item5);


        // Configurations
        bottomNavigation.setBehaviorTranslationEnabled(false);
        bottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_HIDE);

        // Theme
        bottomNavigation.setAccentColor(Color.parseColor("#6CABDD"));
        bottomNavigation.setDefaultBackgroundColor(Color.parseColor("#f8f8f8"));

        // Init
        bottomNavigation.setCurrentItem(0);
    }

    public AHBottomNavigation getBottomNavigation() {
        return bottomNavigation;
    }

//    public void setListeners(final AppCompatActivity activity) {
//        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
//            @Override
//            public boolean onTabSelected(int position, boolean wasSelected) {
//
//            }
}
