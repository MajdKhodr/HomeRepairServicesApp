package com.scum.seg.ondemandhomerepairservices;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AvailabilityActivity extends AppCompatActivity {

    private String date;
    private int mYear;
    private int mMonth;
    private int mDay;
    private int mHourStart;
    private int mMinuteStart;
    private int mHourEnd;
    private int mMinuteEnd;
    private List<Availability> mAvailabilityList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_availability);


        FloatingActionButton fab = findViewById(R.id.floatingActionButton2);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendarDialog();
            }
        });

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

    private void dateDialogEnd(){
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

    private void parseDate(){
        String startDate = date + ":" + mHourStart + ":" + mMinuteStart;
        String endDate = date + ":" + mHourEnd + ":" + mMinuteEnd;
    }


    private void setupCalendar() {
        User user = (User) getIntent().getSerializableExtra("User");
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Users/" + user.getKey());

        mDatabase.push().setValue(user);

        ValueEventListener serviceListener = new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                // Loop through list of availabilities
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    mAvailabilityList.add(childSnapshot.getValue(Availability.class));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        mDatabase.addValueEventListener(serviceListener);
    }
}
