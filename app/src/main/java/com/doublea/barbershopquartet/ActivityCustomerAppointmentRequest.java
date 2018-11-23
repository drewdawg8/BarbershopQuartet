package com.doublea.barbershopquartet;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.doublea.barbershopquartet.BackgroundTools.Barber;
import com.doublea.barbershopquartet.BackgroundTools.FirebaseInteraction;
import com.doublea.barbershopquartet.BackgroundTools.FirebaseReadListener;
import com.doublea.barbershopquartet.BackgroundTools.TimeSlot;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;

public class ActivityCustomerAppointmentRequest extends AppCompatActivity {

    private Spinner firstSpinner;
    private Spinner secondSpinner;
    private FirebaseInteraction firebaseInteraction;
    private ArrayList<Barber> listOfBarbers;
    protected static Barber barber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_appointment_request);
        intializeVariables();
    }

    private void intializeVariables() {
        this.firstSpinner = (Spinner)findViewById(R.id.spinner1);
        this.secondSpinner = (Spinner)findViewById(R.id.spinner2);
        firebaseInteraction = new FirebaseInteraction();
        this.listOfBarbers = new ArrayList<Barber>();
    }

    public void onClickRegister(View view) {

        Object firstSpinnerSelection = firstSpinner.getSelectedItem(); // for now until we load data to firebase
        Object secondSpinnerSelection = secondSpinner.getSelectedItem(); // for now until we load data to firebase

        String barber = firstSpinnerSelection.toString();
        String timeSlot = secondSpinnerSelection.toString();
        //if(firstSpinnerSelection != null || secondSpinnerSelection != null){
            Intent startNewActivity = new Intent(this, ActivityCustomerFillOutAppointment.class);
            startNewActivity.putExtra("timeSlot", timeSlot);
            startNewActivity.putExtra("barber", barber);
            startActivity(startNewActivity);
       // }

    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseInteraction.read("Barbers", new FirebaseReadListener() {
            @Override
            public void onSuccess(DataSnapshot data) {
                for(DataSnapshot barber : data.getChildren()){
                    listOfBarbers.add(extractBarber(barber));
                }
                populateFirstSpinner();
            }

            @Override
            public void onFailure() {

            }
        });
    }

    private void populateFirstSpinner() {
        ArrayList<String> barberNames = new ArrayList<String>();
        for(Barber b : this.listOfBarbers){
            barberNames.add(b.getFirstName() + " " + b.getLastName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, barberNames);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        firstSpinner.setAdapter(adapter);
    }

    private Barber extractBarber(DataSnapshot barber) {
        return new Barber(barber.child("firstName").getValue().toString(),barber.child("lastName").getValue().toString(),
                barber.child("phoneNumber").getValue().toString(), barber.child("email").getValue().toString(),
                barber.child("description").getValue().toString(), barber.child("uid").getValue().toString());
    }
}
