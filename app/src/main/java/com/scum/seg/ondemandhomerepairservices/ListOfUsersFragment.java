package com.scum.seg.ondemandhomerepairservices;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ListOfUsersFragment extends Fragment {

    private RecyclerView mUserRecyclerView;
    private UserAdapter mUserAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_users, container, false);

        if(getActivity().getIntent().getSerializableExtra("User").equals("admin")){
            TextView listofusers = v.findViewById(R.id.textView);
            listofusers.setVisibility(View.VISIBLE);
            mUserRecyclerView = v.findViewById(R.id.recycler_view);
            mUserRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            mUserRecyclerView.setHasFixedSize(true);

            updateUI();
        }

        return v;


    }

    private class UserHolder extends RecyclerView.ViewHolder {
        private TextView mUserName;
        private TextView mType;


        public UserHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_user, parent, false));
            mUserName = itemView.findViewById(R.id.user_name);
            mType = itemView.findViewById(R.id.user_type);
        }

        private User mUser;

        public void bind(User crime) {
            mUser = crime;
            mUserName.setText(mUser.getUserName());
            mType.setText(mUser.getType());
        }
    }

    private class UserAdapter extends RecyclerView.Adapter<UserHolder> {
        private List<User> mUsers;

        public UserAdapter(List<User> users) {
            mUsers = users;
        }

        @NonNull
        @Override
        public UserHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new UserHolder(layoutInflater, viewGroup);
        }

        @Override
        public void onBindViewHolder(@NonNull UserHolder userHolder, int i) {
            User user = mUsers.get(i);
            userHolder.bind(user);

        }

        @Override
        public int getItemCount() {
            return mUsers.size();
        }
    }

    private void updateUI() {
        // Get database reference
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Users");

        // Add on data change listener to database to fetch data
        ValueEventListener userListener = new ValueEventListener() {
            User user;

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<User> users = new ArrayList<>();
                // Loop through list of users checking if the given username and password match an account
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    user = childSnapshot.getValue(User.class);
                    users.add(user);
                }

                mUserAdapter = new UserAdapter(users);
                mUserRecyclerView.setAdapter(mUserAdapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        };
        mDatabase.addListenerForSingleValueEvent(userListener);
    }
}
