package com.doublea.barbershopquartet;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.doublea.barbershopquartet.BackgroundTools.Appointment;
import com.doublea.barbershopquartet.BackgroundTools.Barber;
import com.doublea.barbershopquartet.BackgroundTools.Customer;
import com.doublea.barbershopquartet.BackgroundTools.FirebaseInteraction;
import com.doublea.barbershopquartet.BackgroundTools.TimeSlot;

import static com.doublea.barbershopquartet.ActivityCustomerAppointmentRequest.barber;
import static com.doublea.barbershopquartet.ActivityCustomerAppointmentRequest.timeSlot;

public class ActivityCustomerFillOutAppointment extends AppCompatActivity {

    FirebaseInteraction firebaseInteraction = new FirebaseInteraction();
    private Appointment appointment;
    private Customer customer;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_fill_out_appointment);
        initializeVariables();
    }

    private void initializeVariables(){
        appointment = new Appointment(null, null, null);
        customer = new Customer(null, null, null, null);
        url = "";
    }

    private boolean initializeAndCheckAppointmentValues(){
        boolean entered = true;
        EditText edit = (EditText)findViewById(R.id.edit_text_first_name);
        if(edit.getText().toString().equals("")){
            entered = false;
           edit.setHintTextColor(Color.RED);
        }
        customer.setFirstName(edit.getText().toString());
        edit = (EditText)findViewById(R.id.edit_text_last_name);
        if(edit.getText().toString().equals("")){
            entered = false;
            edit.setHintTextColor(Color.RED);
        }
        customer.setLastName(edit.getText().toString());
        edit = (EditText)findViewById(R.id.edit_text_email);
        if(edit.getText().toString().equals("")){
            entered = false;
            edit.setHintTextColor(Color.RED);
        }
        customer.setEmail(edit.getText().toString());
        edit = (EditText)findViewById(R.id.edit_text_phone_number);
        if(edit.getText().toString().equals("")){
            entered = false;
            edit.setHintTextColor(Color.RED);
        }
        customer.setPhoneNumber(edit.getText().toString());
        edit = (EditText)findViewById(R.id.edit_text_notes);
        appointment.setCustomer(customer);
        appointment.setNotes(edit.getText().toString());
        appointment.setURL(this.url);
        return entered;
    }

    public void onClickReserveAppointment(View view) {
        if(initializeAndCheckAppointmentValues()){
            // send TimeSlot with appointment and image information
            initialzeTimeSlotAndSendToFirebase();
            // go to next activity
            startActivity(new Intent(this, ActivityMain.class));
        }

    }

    private void initialzeTimeSlotAndSendToFirebase() {
        timeSlot.setAppointment(appointment);
        timeSlot.setBooked(true);
        // send to firebase with precise mapping
        firebaseInteraction.writeTimeslot(timeSlot, barber);
    }


    public void onClickUploadPhoto(View view) {

        this.url = "";
        // set the class variable url to obtained URL
    }
}
