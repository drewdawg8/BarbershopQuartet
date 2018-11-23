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

public class ActivityCustomerFillOutAppointment extends AppCompatActivity {

    FirebaseInteraction firebaseInteraction = new FirebaseInteraction();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_fill_out_appointment);

    }

    private Appointment appointment;
    private String url;

    public void onClickReserveAppointment(View view) {
        String time = getIntent().getExtras().getParcelable("timeSlot");
        String barber = getIntent().getExtras().getParcelable("barber");
        Intent goToMain;
        EditText edit = (EditText)findViewById(R.id.edit_text_first_name);
        String firstName = edit.getText().toString();
        edit = (EditText)findViewById(R.id.edit_text_last_name);
        String lastName = edit.getText().toString();
        edit = (EditText)findViewById(R.id.edit_text_email);
        String email = edit.getText().toString();
        edit = (EditText)findViewById(R.id.edit_text_phone_number);
        String phoneNumber = edit.getText().toString();
        edit = (EditText)findViewById(R.id.edit_text_notes);
        String notes = edit.getText().toString();

        // we need to update this so that it ensures email is a valid email.
        if(!firstName.matches("") && !lastName.matches("") && !email.matches("")
                && !phoneNumber.matches( "")) {
            // create appointment object with url, notes, and customer
            appointment = new Appointment(notes, this.url, new Customer(firstName, lastName, phoneNumber, email));
            String timeInformation[] = time.split(":");
            TimeSlot timeSlot = new TimeSlot(timeInformation[0],timeInformation[1],timeInformation[2],timeInformation[3], appointment);

            // write the new TimeSlot to the specific Barber and replace the current timeSlot
            //firebaseInteraction.writeTimeslot(timeSlot, new );
            goToMain = new Intent(this, ActivityMain.class);
            startActivity(goToMain);
        }
        else
        {
            if(firstName == ""){
                edit = (EditText)findViewById(R.id.edit_text_first_name);
                edit.setHintTextColor(Color.RED);
            }
            if(lastName == ""){
                edit = (EditText)findViewById(R.id.edit_text_last_name);
                edit.setHintTextColor(Color.RED);
            }
            if(email == ""){
                edit = (EditText)findViewById(R.id.edit_text_email);
                edit.setHintTextColor(Color.RED);
            }
            if(phoneNumber == ""){
                edit = (EditText)findViewById(R.id.edit_text_phone_number);
                edit.setHintTextColor(Color.RED);
            }
        }
    }


    public void onClickUploadPhoto(View view) {

    }
}
