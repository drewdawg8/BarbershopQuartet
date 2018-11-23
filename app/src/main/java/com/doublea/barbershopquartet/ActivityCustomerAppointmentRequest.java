package com.doublea.barbershopquartet;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.doublea.barbershopquartet.BackgroundTools.Barber;
import com.doublea.barbershopquartet.BackgroundTools.FirebaseInteraction;
import com.doublea.barbershopquartet.BackgroundTools.FirebaseReadListener;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;

public class ActivityCustomerAppointmentRequest extends AppCompatActivity {

    private Spinner spinner;
    private TextView textView;
    private Button button;
    private FirebaseInteraction firebaseInteraction;
    private ArrayList<Barber> listOfBarbers;
    private ArrayList<String> barberNames;
    protected static Barber barber;
    private int stages;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_appointment_request);
        intializeVariables();
    }

    private void intializeVariables() {
        this.spinner = (Spinner)findViewById(R.id.spinner);
        this.textView = (TextView)findViewById(R.id.text_view);
        this.button = (Button) findViewById(R.id.next_button);
        this.firebaseInteraction = new FirebaseInteraction();
        this.listOfBarbers = new ArrayList<Barber>();
        this.stages = 0;
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
        barberNames = new ArrayList<String>();
        for(Barber b : this.listOfBarbers){
            barberNames.add(b.getFirstName() + " " + b.getLastName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, barberNames);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    private Barber extractBarber(DataSnapshot barber) {
        return new Barber(barber.child("firstName").getValue().toString(),barber.child("lastName").getValue().toString(),
                barber.child("phoneNumber").getValue().toString(), barber.child("email").getValue().toString(),
                barber.child("description").getValue().toString(), barber.child("uid").getValue().toString());
    }

    public void onClickNext(View view) {
        switch(stages){
            case 0:
                this.stages++;
                getSelectedBarber();
                this.textView.setText("Choose a Date:");
                getDate();
                break;
            case 1:
                this.stages++;
                this.textView.setText("Choose a Time Slot for your Appointment");
                getTimeSlot();
                button.setText("Submit");
                break;
            case 2:
                startActivity(new Intent(this, ActivityCustomerFillOutAppointment.class));
        }
    }

    private void loadTimeSlots() {

    }

    private void getSelectedBarber() {
        String name = spinner.getSelectedItem().toString();
        int index = barberNames.indexOf(name);
        barber = listOfBarbers.get(index);
    }

    private void getTimeSlot() {
        String timeSlot = spinner.getSelectedItem().toString();

    }

    private void getDate() {

    }


}
