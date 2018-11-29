package com.scum.seg.ondemandhomerepairservices;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.model.CalendarEvent;
import devs.mulham.horizontalcalendar.utils.CalendarEventsPredicate;
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener;

public class BookService extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private AvailabilityAdapter mAdapter;
    private ArrayList<Availability> availabilities;
    private static final String TAG = "BookService";
    private Service service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_service);
        service = (Service) (getIntent().getSerializableExtra("Service"));

        fetchService(this);

        mRecyclerView = findViewById(R.id.avail_rec);
        availabilities = new ArrayList<>();

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        availabilities.add(new Availability(12, "Hey"));
        mAdapter = new AvailabilityAdapter(availabilities, this);
        mRecyclerView.setAdapter(mAdapter);



    }

    private ArrayList<String> fetchService(final Context context) {
        // Get database reference
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Services/" + service.getKey() + "/ServiceProviders");
        final ArrayList<String> services = new ArrayList<>();

        // Add on data change listener to database to fetch data
        ValueEventListener userListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    Log.d(TAG, "onDataChange: Key" + childSnapshot.getValue());
                    services.add(childSnapshot.getValue().toString());
                }

                final ArrayList<Availability> temp = fetchServiceInfo(services);

                /* starts before 1 year from now */
                Calendar startDate = Calendar.getInstance();
                startDate.add(Calendar.YEAR, -1);

                /* ends after 1 year from now */
                Calendar endDate = Calendar.getInstance();
                endDate.add(Calendar.YEAR, 1);

                HorizontalCalendar horizontalCalendar = new HorizontalCalendar.Builder((Activity)context, R.id.calendarView)
                        .range(startDate, endDate)
                        .datesNumberOnScreen(5)
                        .addEvents(new CalendarEventsPredicate() {

                            @Override
                            public List<CalendarEvent> events(Calendar date) {
                                List<CalendarEvent> events = new ArrayList<>();

                                for (Availability a : temp) {
                                    if (date.getTimeInMillis() == a.getTime())

                                        events.add(new CalendarEvent(R.color.colorFAB, "event"));

                                }
                                return events;

                            }
                        })
                        .build();

                horizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {
                    @Override
                    public void onDateSelected(Calendar date, int position) {

                    }

                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        mDatabase.addValueEventListener(userListener);

        return services;
    }

    private ArrayList<Availability> fetchServiceInfo(ArrayList<String> strings) {
        final ArrayList<Availability> availableDates = new ArrayList<>();

        for (String s : strings) {

            // Get database reference
            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Users/" + s + "/Availability");

            // Add on data change listener to database to fetch data
            ValueEventListener userListener = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                        availableDates.add(childSnapshot.getValue(Availability.class));
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            };
            mDatabase.addValueEventListener(userListener);
        }
        return availableDates;
    }

}
