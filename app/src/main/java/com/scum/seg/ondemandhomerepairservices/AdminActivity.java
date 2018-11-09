package com.scum.seg.ondemandhomerepairservices;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class AdminActivity extends AppCompatActivity {
    private EditText ratePerHour;
    private String rate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        ratePerHour = findViewById(R.id.rate_editText);

        Spinner spinner = findViewById(R.id.services_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.services_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }


    public void addServiceToServices(View view) {
        rate = ratePerHour.getText().toString();

        if (!validateRate(rate)) {
            String toastMessage = "Please enter a valid Rate Per Hour.";

            Toast toast = Toast.makeText(getApplicationContext(), toastMessage, Toast.LENGTH_LONG);
        } else{
            Spinner servicesSpinner = findViewById(R.id.services_spinner);
            String service = servicesSpinner.getSelectedItem().toString();

            //Call alexi's method and pass rate and service
        }
    }

    public boolean validateRate(String rate) {
        // Checks if rate is a negative number
        if (Integer.parseInt(rate) < 0)
             return false;

        // Verifies all characters are digits
        for (int i = 0; i < rate.length(); i++) {
            if (!Character.isDigit(rate.charAt(i))) {
                return false;
            }
        }

        return true;
    }
}
