package com.scum.seg.ondemandhomerepairservices;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public class UserProfileFragment extends Fragment {

    //Instance variables
    private TextView mDisplayTextView;
    private String mDisplayString;
    private User user;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_user_profile, container, false);


        user = (User) getActivity().getIntent().getSerializableExtra("User");
        mDisplayTextView = v.findViewById(R.id.displayUser_textView);


        mDisplayString =
                "First Name: " + user.getFirstName() +
                        "\nLast Name: " + user.getLastName() + "\n" +
                        "\nUser Name: " + user.getUserName() +
                        "\nEmail Address: " + user.getEmail() +
                        "\nAddress: " + user.getAddress() +
                        "\nPhone Number: " + user.getPhonenumber() + "\n";

        if (user.getType().equals("service provider")) {
            String licensed = "No";
            if (user.getIsLicensed()){
                licensed = "Yes";
            }
            mDisplayString += "\nCompany Name: " + user.getCompanyName() +
                             "\nDescription: " + user.getDescription() +
                             "\nLicensed: " + licensed;
        }

        mDisplayTextView.setText(mDisplayString);

        Button signOutButton = v.findViewById(R.id.signout_button);
        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });
        return v;

    }

    public void signOut() {
        Intent intent = new Intent(getActivity(), SignInPageActivity.class);
        startActivity(intent);
        getActivity().finish();
    }
}
