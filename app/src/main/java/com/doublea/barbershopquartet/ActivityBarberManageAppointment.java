package com.doublea.barbershopquartet;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.doublea.barbershopquartet.BackgroundTools.FirebaseInteraction;
import com.doublea.barbershopquartet.BackgroundTools.TimeSlot;
import com.google.firebase.auth.FirebaseAuth;

import static com.doublea.barbershopquartet.ActivityCustomerAppointmentRequest.timeSlot;

public class ActivityBarberManageAppointment extends AppCompatActivity {

    private TimeSlot localTimeSlot = ActivityBarberSelectAppointment.selectedTimeSlot;
    private FirebaseInteraction firebaseInteraction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barber_manage_appointment);
    }


    @Override
    protected void onStart() {
        super.onStart();
        initializeInformation();
        firebaseInteraction = new FirebaseInteraction();
    }

    public void onClickRemoveAppointment(View view) {
        TimeSlot slot = new TimeSlot(localTimeSlot.getMonth(), localTimeSlot.getDay(), localTimeSlot.getHour(),
                localTimeSlot.getMinute(), null);
        firebaseInteraction.writeTimeslot(slot, FirebaseAuth.getInstance().getUid());
        startActivity(new Intent(this,ActivityBarberSelectAppointment.class));
    }

    public void initializeInformation(){
        TextView textView = (TextView)findViewById(R.id.edit_text_name);
        textView.setText(ActivityBarberSelectAppointment.selectedTimeSlot.getAppointment().getCustomer().toString());
        textView = (TextView)findViewById(R.id.edit_text_email);
        textView.setText(ActivityBarberSelectAppointment.selectedTimeSlot.getAppointment().getCustomer().getEmail());
        textView = (TextView)findViewById(R.id.edit_text_phone_number);
        textView.setText(ActivityBarberSelectAppointment.selectedTimeSlot.getAppointment().getCustomer().getPhoneNumber());
        textView = (TextView)findViewById(R.id.edit_text_time_slot);
        textView.setText(ActivityBarberSelectAppointment.selectedTimeSlot.toString());
        textView = (TextView)findViewById(R.id.edit_text_notes);
        textView.setText(ActivityBarberSelectAppointment.selectedTimeSlot.getAppointment().getNotes());
    }
}
