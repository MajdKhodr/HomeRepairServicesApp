package com.scum.seg.ondemandhomerepairservices;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.model.CalendarEvent;
import devs.mulham.horizontalcalendar.utils.CalendarEventsPredicate;
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener;

public class BookService extends AppCompatActivity {

    private static final String TAG = "BookService";
    private Service service;
    ArrayList<Availability> listOfServices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_service);

        // Get service extra
        service = (Service) (getIntent().getSerializableExtra("Service"));

        // Fetch service provider availability for specific service
        fetchService(this);
        listOfServices = new ArrayList<>();


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
                    services.add(childSnapshot.getValue().toString());
                }

                listOfServices = fetchServiceInfo(services);

                /* starts before 1 year from now */
                Calendar startDate = Calendar.getInstance();
                startDate.add(Calendar.YEAR, -1);

                /* ends after 1 year from now */
                Calendar endDate = Calendar.getInstance();
                endDate.add(Calendar.YEAR, 1);

                Calendar temp = Calendar.getInstance();
                updateScrollView(temp);

                HorizontalCalendar horizontalCalendar = new HorizontalCalendar.Builder((Activity) context, R.id.calendarView)
                        .range(startDate, endDate)
                        .datesNumberOnScreen(5)
                        .addEvents(new CalendarEventsPredicate() {

                            @Override
                            public List<CalendarEvent> events(Calendar date) {
                                List<CalendarEvent> events = new ArrayList<>();


                                try {
                                    for (Availability a : listOfServices) {
                                        SimpleDateFormat end = new SimpleDateFormat("HH:mm", Locale.getDefault());

                                        String endTime = a.getDesc().substring(26);

                                        Date endDate = end.parse(endTime);

                                        if (date.getTimeInMillis() == a.getTime() && endDate.getTime() <= 72000000) {
                                            events.add(new CalendarEvent(R.color.colorFAB, "event"));
                                        }
                                    }

                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                                return events;

                            }

                        })
                        .build();

                horizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {
                    @Override
                    public void onDateSelected(Calendar date, int position) {
                        updateScrollView(date);
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

    private void updateScrollView(Calendar date) {
        TextView eightam = findViewById(R.id.textView8am);
        TextView nineam = findViewById(R.id.textView9am);
        TextView tenam = findViewById(R.id.textView10am);
        TextView elevenam = findViewById(R.id.textView11am);
        TextView twelveam = findViewById(R.id.textView12am);
        TextView onepm = findViewById(R.id.textView1pm);
        TextView twopm = findViewById(R.id.textView2pm);
        TextView threepm = findViewById(R.id.textView3pm);
        TextView fourpm = findViewById(R.id.textView4pm);
        TextView fivepm = findViewById(R.id.textView5pm);
        TextView sixpm = findViewById(R.id.textView6pm);
        TextView sevenpm = findViewById(R.id.textView7pm);
        TextView eightpm = findViewById(R.id.textView8pm);

        eightam.setBackgroundColor(Color.parseColor("#ff6666"));
        nineam.setBackgroundColor(Color.parseColor("#ff6666"));
        tenam.setBackgroundColor(Color.parseColor("#ff6666"));
        elevenam.setBackgroundColor(Color.parseColor("#ff6666"));
        twelveam.setBackgroundColor(Color.parseColor("#ff6666"));
        onepm.setBackgroundColor(Color.parseColor("#ff6666"));
        twopm.setBackgroundColor(Color.parseColor("#ff6666"));
        threepm.setBackgroundColor(Color.parseColor("#ff6666"));
        fourpm.setBackgroundColor(Color.parseColor("#ff6666"));
        fivepm.setBackgroundColor(Color.parseColor("#ff6666"));
        sixpm.setBackgroundColor(Color.parseColor("#ff6666"));
        sevenpm.setBackgroundColor(Color.parseColor("#ff6666"));
        eightpm.setBackgroundColor(Color.parseColor("#ff6666"));

        eightam.setText("Unavailable");
        nineam.setText("Unavailable");
        tenam.setText("Unavailable");
        elevenam.setText("Unavailable");
        twelveam.setText("Unavailable");
        onepm.setText("Unavailable");
        twopm.setText("Unavailable");
        threepm.setText("Unavailable");
        fourpm.setText("Unavailable");
        fivepm.setText("Unavailable");
        sixpm.setText("Unavailable");
        sevenpm.setText("Unavailable");
        eightpm.setText("Unavailable");


        for (Availability a : listOfServices) {
            try {
                SimpleDateFormat start = new SimpleDateFormat("HH:mm", Locale.getDefault());
                SimpleDateFormat end = new SimpleDateFormat("HH:mm", Locale.getDefault());

                String startTime = a.getDesc().substring(16, 21);
                String endTime = a.getDesc().substring(26);

                Date startDate = start.parse(startTime);
                Date endDate = end.parse(endTime);


                if (a.getTime() == date.getTimeInMillis()) {
                    if (startDate.getTime() - 18000000 <= 28800000 && endDate.getTime() - 18000000 >= 28800000) {
                        eightam.setBackgroundColor(Color.parseColor("#007f00"));
                        eightam.setText("Available");
                    }
                    if (startDate.getTime() - 18000000 <= 32400000 && endDate.getTime() - 18000000 >= 32400000) {
                        nineam.setBackgroundColor(Color.parseColor("#007f00"));
                        nineam.setText("Available");

                    }
                    if (startDate.getTime() - 18000000 <= 36000000 && endDate.getTime() - 18000000 >= 36000000) {
                        tenam.setBackgroundColor(Color.parseColor("#007f00"));
                        tenam.setText("Available");

                    }
                    if (startDate.getTime() - 18000000 <= 39600000 && endDate.getTime() - 18000000 >= 39600000) {
                        elevenam.setBackgroundColor(Color.parseColor("#007f00"));
                        elevenam.setText("Available");

                    }
                    if (startDate.getTime() - 18000000 <= 43200000 && endDate.getTime() - 18000000 >= 43200000) {
                        twelveam.setBackgroundColor(Color.parseColor("#007f00"));
                        twelveam.setText("Available");

                    }
                    if (startDate.getTime() - 18000000 <= 46800000 && endDate.getTime() - 18000000 >= 46800000) {
                        onepm.setBackgroundColor(Color.parseColor("#007f00"));
                        onepm.setText("Available");

                    }
                    if (startDate.getTime() - 18000000 <= 50400000 && endDate.getTime() - 18000000 >= 50400000) {
                        twopm.setBackgroundColor(Color.parseColor("#007f00"));
                        twopm.setText("Available");

                    }
                    if (startDate.getTime() - 18000000 <= 54000000 && endDate.getTime() - 18000000 >= 54000000) {
                        threepm.setBackgroundColor(Color.parseColor("#007f00"));
                        threepm.setText("Available");

                    }
                    if (startDate.getTime() - 18000000 <= 57600000 && endDate.getTime() - 18000000 >= 57600000) {
                        fourpm.setBackgroundColor(Color.parseColor("#007f00"));
                        fourpm.setText("Available");

                    }
                    if (startDate.getTime() - 18000000 <= 61200000 && endDate.getTime() - 18000000 >= 61200000) {
                        fivepm.setBackgroundColor(Color.parseColor("#007f00"));
                        fivepm.setText("Available");

                    }
                    if (startDate.getTime() - 18000000 <= 64800000 && endDate.getTime() - 18000000 >= 64800000) {
                        sixpm.setBackgroundColor(Color.parseColor("#007f00"));
                        sixpm.setText("Available");

                    }
                    if (startDate.getTime() - 18000000 <= 68400000 && endDate.getTime() - 18000000 >= 68400000) {
                        sevenpm.setBackgroundColor(Color.parseColor("#007f00"));
                        sevenpm.setText("Available");

                    }
                    if (startDate.getTime() - 18000000 <= 72000000 && endDate.getTime() - 18000000 >= 72000000) {
                        eightpm.setBackgroundColor(Color.parseColor("#007f00"));
                        eightpm.setText("Available");

                    }

                }
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
    }

    public void book(View v) {

        TextView eightam = findViewById(R.id.textView8am);
        TextView nineam = findViewById(R.id.textView9am);
        TextView tenam = findViewById(R.id.textView10am);
        TextView elevenam = findViewById(R.id.textView11am);
        TextView twelveam = findViewById(R.id.textView12am);
        TextView onepm = findViewById(R.id.textView1pm);
        TextView twopm = findViewById(R.id.textView2pm);
        TextView threepm = findViewById(R.id.textView3pm);
        TextView fourpm = findViewById(R.id.textView4pm);
        TextView fivepm = findViewById(R.id.textView5pm);
        TextView sixpm = findViewById(R.id.textView6pm);
        TextView sevenpm = findViewById(R.id.textView7pm);
        TextView eightpm = findViewById(R.id.textView8pm);

        Button b = (Button) v;
        String buttonID = getResources().getResourceEntryName(v.getId());


        if (buttonID.contains("8am") && eightam.getText().equals("Available")) {
            eightam.setText("Booked");
            eightam.setBackgroundColor(Color.parseColor("#ff8000"));
        } else if (buttonID.contains("9am") && nineam.getText().equals("Available")) {
            nineam.setText("Booked");
            nineam.setBackgroundColor(Color.parseColor("#ff8000"));
        } else if (buttonID.contains("10am") && tenam.getText().equals("Available")) {
            tenam.setText("Booked");
            tenam.setBackgroundColor(Color.parseColor("#ff8000"));
        } else if (buttonID.contains("11am") && elevenam.getText().equals("Available")) {
            elevenam.setText("Booked");
            elevenam.setBackgroundColor(Color.parseColor("#ff8000"));
        } else if (buttonID.contains("12am") && twelveam.getText().equals("Available")) {
            twelveam.setText("Booked");
            twelveam.setBackgroundColor(Color.parseColor("#ff8000"));
        } else if (buttonID.contains("1pm") && onepm.getText().equals("Available")) {
            onepm.setText("Booked");
            onepm.setBackgroundColor(Color.parseColor("#ff8000"));
        } else if (buttonID.contains("2pm") && twopm.getText().equals("Available")) {
            twopm.setText("Booked");
            twopm.setBackgroundColor(Color.parseColor("#ff8000"));
        } else if (buttonID.contains("3pm") && threepm.getText().equals("Available")) {
            threepm.setText("Booked");
            threepm.setBackgroundColor(Color.parseColor("#ff8000"));
        } else if (buttonID.contains("4pm") && fourpm.getText().equals("Available")) {
            fourpm.setText("Booked");
            fourpm.setBackgroundColor(Color.parseColor("#ff8000"));
        } else if (buttonID.contains("5pm") && fivepm.getText().equals("Available")) {
            fivepm.setText("Booked");
            fivepm.setBackgroundColor(Color.parseColor("#ff8000"));
        } else if (buttonID.contains("6pm") && sixpm.getText().equals("Available")) {
            sixpm.setText("Booked");
            sixpm.setBackgroundColor(Color.parseColor("#ff8000"));
        }else if (buttonID.contains("7pm") && sevenpm.getText().equals("Available")) {
            sevenpm.setText("Booked");
            sevenpm.setBackgroundColor(Color.parseColor("#ff8000"));
        } else if (buttonID.contains("8pm") && eightpm.getText().equals("Available")) {
            eightpm.setText("Booked");
            eightpm.setBackgroundColor(Color.parseColor("#ff8000"));
        }else{
            Toast.makeText(this,"Booking is unavailable", Toast.LENGTH_SHORT).show();
        }

    }


}
