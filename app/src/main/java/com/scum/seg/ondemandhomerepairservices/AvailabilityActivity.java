package com.scum.seg.ondemandhomerepairservices;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class AvailabilityActivity extends AppCompatActivity {

    private String date;
    private int mYear;
    private int mMonth;
    private int mDay;
    private int mHourStart;
    private int mMinuteStart;
    private int mHourEnd;
    private int mMinuteEnd;

    private CompactCalendarView compactCalendarView;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_availability);


        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setTitle(null);

        compactCalendarView = findViewById(R.id.compactcalendar_view);
        compactCalendarView.setUseThreeLetterAbbreviation(true);

        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                if (compactCalendarView.getEvents(dateClicked).size() > 0) {
                    Toast.makeText(getApplicationContext(), compactCalendarView.getEvents(dateClicked).get(0).getData().toString(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "No Event", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                actionBar.setTitle(simpleDateFormat.format(firstDayOfNewMonth));
            }
        });

        FloatingActionButton fab = findViewById(R.id.floatingActionButton2);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendarDialog();
            }
        });

        loadCalendar();


    }

    private void calendarDialog() {
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        date = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                        dateDialogStart();
                    }
                }, mYear, mMonth, mDay);

        datePickerDialog.show();
    }

    private void dateDialogStart() {

        final Calendar c = Calendar.getInstance();
        mHourStart = c.get(Calendar.HOUR_OF_DAY);
        mMinuteStart = c.get(Calendar.MINUTE);


        final TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        mHourStart = hourOfDay;
                        mMinuteStart = minute;
                        dateDialogEnd();
                    }
                }, mHourStart, mMinuteStart, false);
        timePickerDialog.show();
    }

    private void dateDialogEnd() {
        final Calendar c = Calendar.getInstance();
        mHourEnd = c.get(Calendar.HOUR_OF_DAY);
        mMinuteEnd = c.get(Calendar.MINUTE);

        final TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        mHourEnd = hourOfDay;
                        mMinuteEnd = minute;
                        parseDate();
                    }
                }, mHourStart, mMinuteStart, false);
        timePickerDialog.show();
    }

    private void parseDate() {
        String startDate = String.format("%02d:%02d", mHourStart, mMinuteStart);
        String endDate = String.format("%02d:%02d", mHourEnd, mMinuteEnd);

        SimpleDateFormat hourmin = new SimpleDateFormat("HH:mm", Locale.getDefault());
        SimpleDateFormat daymonthyear = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        Log.d("Alexi", "Current time is : " + System.currentTimeMillis());

        try {
            Log.d("Alexi", "Chosen time is : " + daymonthyear.parse(date).getTime());

            if (hourmin.parse(startDate).getTime() < hourmin.parse(endDate).getTime() && daymonthyear.parse(date).getTime() >= System.currentTimeMillis()) {
                createEvent(startDate, endDate);
            } else {
                Toast.makeText(this, "The end time is smaller than the start time. Please try again", Toast.LENGTH_SHORT).show();
            }
        }catch (ParseException e){
            Log.d("Error","Error");
        }

    }

    private void createEvent(String startDate, String endDate) {
        Event newEvent;
        try {
            newEvent = new Event(R.color.colorFAB, simpleDateFormat.parse(date).getTime(), "Available from:\n" + startDate + " to: " + endDate);
            compactCalendarView.addEvent(newEvent);

            User user = (User) getIntent().getSerializableExtra("User");
            DatabaseReference users = FirebaseDatabase.getInstance().getReference("Users/" + user.getKey());
            Availability availability = new Availability(simpleDateFormat.parse(date).getTime(), "Available from:\n" + startDate + " to: " + endDate);
            users.child("Availability").push().setValue(availability);

        } catch (ParseException e) {
            Log.d("AvailableActivity", "Error");
        }

    }

    private void loadCalendar() {
        User user = (User) getIntent().getSerializableExtra("User");
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Users/" + user.getKey() + "/Availability");

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                        Availability availability = childSnapshot.getValue(Availability.class);
                        compactCalendarView.addEvent(new Event(R.color.colorFAB
                                , availability.getTime(), availability.getDesc()));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        mDatabase.addListenerForSingleValueEvent(valueEventListener);

    }
}
