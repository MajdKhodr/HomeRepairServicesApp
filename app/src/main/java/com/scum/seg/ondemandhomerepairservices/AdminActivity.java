package com.scum.seg.ondemandhomerepairservices;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class AdminActivity extends AppCompatActivity {
    private EditText ratePerHour;
    private String rate;

    private static final String TAG = "EMPTYDATA";

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

        Spinner servicesSpinner = findViewById(R.id.services_spinner);
        String serviceName = servicesSpinner.getSelectedItem().toString();

        if (!rate.equals("")) {
            Service service = new Service(serviceName, Float.parseFloat(rate));

            Intent intent = new Intent();
            intent.putExtra("Service", service);
            intent.putExtra("User", getIntent().getSerializableExtra("User"));
            intent.putExtra("ServicePosition", getIntent().getSerializableExtra("ServicePosition"));
            setResult(RESULT_OK, intent);
            finish();
        } else {
            String toastMessage = "Please enter a Rate Per Hour";
            Toast toast = Toast.makeText(getApplicationContext(), toastMessage, Toast.LENGTH_LONG);
            toast.show();
        }
    }
}
