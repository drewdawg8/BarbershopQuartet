package com.doublea.barbershopquartet;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;

public class ActivityCustomerAppointmentRequest extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_appointment_request);
    }

    public void onClickRegister(View view) {
        Spinner firstSpinner = (Spinner)findViewById(R.id.spinner1);
        Object firstSpinnerSelection = firstSpinner.getSelectedItem(); // for now until we load data to firebase
        Spinner secondSpinner = (Spinner)findViewById(R.id.spinner2);
        Object secondSpinnerSelection = secondSpinner.getSelectedItem(); // for now until we load data to firebase
        //if(firstSpinnerSelection != null || secondSpinnerSelection != null){
            Intent startNewActivity = new Intent(this, ActivityCustomerFillOutAppointment.class);
            startActivity(startNewActivity);
       // }

    }
}
