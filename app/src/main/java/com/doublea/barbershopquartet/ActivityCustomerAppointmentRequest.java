package com.doublea.barbershopquartet;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.doublea.barbershopquartet.BackgroundTools.Barber;
import com.doublea.barbershopquartet.BackgroundTools.FirebaseInteraction;
import com.doublea.barbershopquartet.BackgroundTools.FirebaseReadListener;
import com.doublea.barbershopquartet.BackgroundTools.TimeSlot;
import com.google.firebase.database.DataSnapshot;

import java.sql.Array;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class ActivityCustomerAppointmentRequest extends AppCompatActivity {

    private Spinner spinner;
    private TextView textView;
    private Button button;
    private FirebaseInteraction firebaseInteraction;
    private ArrayList<Barber> listOfBarbers;
    private ArrayList<String> barberNames;
    private ArrayList<TimeSlot> listOfTimeSlots;
    protected static Barber barber;
    protected static TimeSlot timeSlot;
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
        this.listOfTimeSlots = new ArrayList<TimeSlot>();
        barber = new Barber(null, null, null, null, null);
        timeSlot = new TimeSlot(null, null, null, null, null);
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
                getSelectedBarber();
                Toast.makeText(ActivityCustomerAppointmentRequest.this,"Pick your date",Toast.LENGTH_SHORT).show();
                getDate(view);
                break;
            case 1:
                this.stages++;
                getTimeSlot();
                startActivity(new Intent(this, ActivityCustomerFillOutAppointment.class));
                break;
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
        String choice = spinner.getSelectedItem().toString();
        String [] array = choice.split(":");
        String [] m = array[1].split(" ");
        if(array[0].equals("12")){
            timeSlot.setHour(array[0]);
        }
        else if(m[1].equals("AM")){
            timeSlot.setHour(array[0]);
        }
        else
            timeSlot.setHour(Integer.toString(Integer.parseInt(array[0]) + 12));
        if (m[0].equals("00")) {
            m[0] = "0";
        }
        timeSlot.setMinute(m[0]);
    }

    private void getDate(View view) {
        int initYear = Calendar.getInstance().get(Calendar.YEAR);
        int initMonth = Calendar.getInstance().get(Calendar.MONTH);
        int initDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(view.getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int y, int m, int d) {
                stages++;
                timeSlot.setMonth(Integer.toString(m + 1));
                timeSlot.setDay(Integer.toString(d));
                populateSpinnerTimeSlots(m + 1, d);
                textView.setText("Choose a Time Slot for your Appointment");
                button.setText("Submit");

                Calendar calendar = Calendar.getInstance();
                calendar.set(y, m, d);


            }
        }, initYear, initMonth, initDay);

        datePickerDialog.getDatePicker().setMinDate(Calendar.getInstance().getTimeInMillis());

        Calendar maxDate = Calendar.getInstance();
        maxDate.set(Calendar.YEAR, 2018);
        maxDate.set(Calendar.MONTH, 11);
        maxDate.set(Calendar.DAY_OF_MONTH, 31);
        datePickerDialog.getDatePicker().setMaxDate(maxDate.getTimeInMillis());
        datePickerDialog.show();
    }

    private void populateSpinnerTimeSlots(int m, int d) {
        String path = "Barbers/" + barber.getUid() + "/" + m + "/" + d;
        firebaseInteraction.read(path, new FirebaseReadListener() {
            @Override
            public void onSuccess(DataSnapshot data) {
                for(DataSnapshot timeSlot : data.getChildren()){
                    listOfTimeSlots.add(extractTimeSlot(timeSlot));
                }
                loadSpinnerWithTimeSlots(listOfTimeSlots);
            }

            @Override
            public void onFailure() {

            }
        });
    }

    private void loadSpinnerWithTimeSlots(ArrayList<TimeSlot> listOfTimeSlots) {
        ArrayList<String> datesOfTimeSlots = new ArrayList<String>();
        for(TimeSlot timeSlot : listOfTimeSlots){
            if(!timeSlot.isBooked() && !timeSlot.isUnavailable())
                datesOfTimeSlots.add(timeSlot.toString());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, datesOfTimeSlots);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    private TimeSlot extractTimeSlot(DataSnapshot timeSlot) {
        return new TimeSlot(timeSlot.child("month").getValue().toString(),
                timeSlot.child("day").getValue().toString(), timeSlot.child("hour").getValue().toString(),
                timeSlot.child("minute").getValue().toString(), null, (boolean)timeSlot.child("booked").getValue(),
                (boolean)timeSlot.child("unavailable").getValue());
    }


}
