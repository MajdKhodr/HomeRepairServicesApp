package com.scum.seg.ondemandhomerepairservices;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class ServiceProviderAdapter extends RecyclerView.Adapter<ServiceProviderAdapter.ServiceHolder> {


    private static final String TAG = "ServiceProviderAdapter";
    private Context context;
    private List<User> serviceList;
    private Fragment fragment;
    private ServiceProvider provider = new ServiceProvider();
    private Service service;

    public ServiceProviderAdapter(Context context, List<User> servicesList, Fragment fragment, Service service) {
        this.serviceList = servicesList;
        this.context = context;
        this.fragment = fragment;
        this.service = service;

        User user = (User) ((Activity) context).getIntent().getSerializableExtra("User");
        provider.setKey(user.getKey());

    }

    @NonNull
    @Override
    public ServiceHolder onCreateViewHolder(@NonNull final ViewGroup viewGroup, final int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_service_providers, null);


        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent intent;
                intent = new Intent(context, BookService.class);
                intent.putExtra("Service", service);
                intent.putExtra("User", serviceList.get(i));
                fragment.startActivity(intent);
            }
        });


        return new ServiceHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final ServiceHolder serviceHolder, int i) {
        final User serviceProvider = serviceList.get(i);

        serviceHolder.mServiceProvider.setText(serviceProvider.getFirstName() + " " + serviceProvider.getLastName());
        serviceHolder.mDescription.setText(serviceProvider.getDescription());
        serviceHolder.mRating.setText(String.valueOf(serviceProvider.getRating()));


        serviceHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (((User) ((Activity) (context)).getIntent().getSerializableExtra("User")).getType().equals("home owner")) {
                    AlertDialog.Builder selectRateAndServiceDialog = new AlertDialog.Builder(context);
                    selectRateAndServiceDialog.setTitle("Select Action");

                    final CharSequence[] rateAndComment = {"Add Rate and Comment", "View Rates and Comments"};

                    selectRateAndServiceDialog.setItems(rateAndComment, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (which == 0) {
                                AlertDialog.Builder addRateAndServiceDialog = new AlertDialog.Builder(context);

                                LinearLayout mainLayout = new LinearLayout(context);
                                mainLayout.setOrientation(LinearLayout.VERTICAL);
                                LinearLayout ratingLayout = new LinearLayout(context);
                                LinearLayout commentLayout = new LinearLayout(context);

                                final RatingBar ratingBar = new RatingBar(context);
                                ratingBar.setNumStars(5);
                                ratingLayout.addView(ratingBar);

                                final EditText comment = new EditText(context);
                                comment.setHint("Comment");
                                commentLayout.addView(comment);

                                mainLayout.addView(ratingLayout);
                                mainLayout.addView(commentLayout);

                                addRateAndServiceDialog.setView(mainLayout).
                                        setPositiveButton(R.string.add_button, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                // User clicked ADD button

                                                float numberOfStars = ratingBar.getRating();
                                                String userComment = comment.getText().toString();
                                                Rating rating = new Rating(numberOfStars, userComment);

                                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                                DatabaseReference serviceRating = database.getReference("Users/" + serviceProvider.getKey() + "/ServiceRating");
                                                serviceRating.push().setValue(rating);
                                            }
                                        }).setNegativeButton(R.string.cancel_dialog, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        // User cancelled the dialog
                                    }
                                });

                                addRateAndServiceDialog.show();

                            } else {
                                // To be filled out by goerges to lead to next activity listing ratings and comments
                                //Intent intent = new Intent(this, );
                                //startActivity(intent);
                            }

                        }
                    });

                    selectRateAndServiceDialog.show();

                }


                return true;
            }


        });

    }

    public void addItem(User user) {
        serviceList.add(user);
        notifyItemInserted(serviceList.size() - 1);
    }

    @Override
    public int getItemCount() {
        return serviceList.size();
    }


    public class ServiceHolder extends RecyclerView.ViewHolder {
        TextView mServiceProvider;
        TextView mDescription;
        TextView mRating;


        public ServiceHolder(View itemView) {
            super(itemView);

            this.mServiceProvider = itemView.findViewById(R.id.service);
            this.mDescription = itemView.findViewById(R.id.description);
            this.mRating = itemView.findViewById(R.id.rating);
        }

    }


}
