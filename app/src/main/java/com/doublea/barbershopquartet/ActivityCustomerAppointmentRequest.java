package com.doublea.barbershopquartet;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class ActivityCustomerAppointmentRequest extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_appointment_request);
    }

    public void onClickRegister(View view) {
        Intent startNewActivity = new Intent(this, activity_customer_fill_out_appointment.class);
    }
}
