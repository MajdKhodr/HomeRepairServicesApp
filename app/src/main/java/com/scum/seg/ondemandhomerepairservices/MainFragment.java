package com.scum.seg.ondemandhomerepairservices;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MainFragment extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    //Instance variables
    TextView mWelcomeTextView;
    String mWelcomeString;
    User user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_main, container, false);

        //Get's user information from Sign in Activity
        user = (User) getActivity().getIntent().getSerializableExtra("User");

        mWelcomeTextView = (TextView) v.findViewById(R.id.welcomeMSG_textView);

        mWelcomeString = "Welcome " + user.getFirstName()
          + ",\nYou are logged in as the " + user.getType();

        mWelcomeTextView.setText(mWelcomeString);

        return v;


    }

}
