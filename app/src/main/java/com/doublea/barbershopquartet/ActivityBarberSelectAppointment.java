package com.doublea.barbershopquartet;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.doublea.barbershopquartet.BackgroundTools.Appointment;
import com.doublea.barbershopquartet.BackgroundTools.Customer;
import com.doublea.barbershopquartet.BackgroundTools.FirebaseInteraction;
import com.doublea.barbershopquartet.BackgroundTools.FirebaseReadListener;
import com.doublea.barbershopquartet.BackgroundTools.TimeSlot;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class ActivityBarberSelectAppointment extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseInteraction firebase;
    private Calendar appointmentDate;
    private TimeSlot[] timeSlots;

    protected static TimeSlot selectedTimeSlot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barber_select_appointment);

        mAuth = FirebaseAuth.getInstance();
        firebase = new FirebaseInteraction();
    }

    @Override
    public void onStart() {
        super.onStart();
        timeSlots = new TimeSlot[16];
    }

    public void onClickSelectDate(View view) {
        int initYear = Calendar.getInstance().get(Calendar.YEAR);
        int initMonth = Calendar.getInstance().get(Calendar.MONTH);
        int initDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(view.getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int y, int m, int d) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(y, m, d);
                EditText e = findViewById(R.id.select_appointment_edit_text);
                SimpleDateFormat sdf = new SimpleDateFormat("EEE, MMM dd");
                e.setText(sdf.format(calendar.getTime()));
                appointmentDate = calendar;
                getTimeSlots();
                findViewById(R.id.select_appointment_submit).setEnabled(true);
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

    public void onClickSubmit(View view) {
        Spinner spinner = findViewById(R.id.select_appointment_spinner);
        selectedTimeSlot = (TimeSlot) spinner.getSelectedItem();
        startActivity(new Intent(this, ActivityBarberManageAppointment.class));
    }

    private void getTimeSlots() {
        String month = Integer.toString(appointmentDate.get(Calendar.MONTH) + 1);
        String day = Integer.toString(appointmentDate.get(Calendar.DAY_OF_MONTH));

        String path = "Barbers/" + mAuth.getUid() + "/" + month + "/" + day;

        firebase.read(path, new FirebaseReadListener() {
            @Override
            public void onSuccess(DataSnapshot data) {

                for (DataSnapshot d: data.getChildren()) {
                    int i = Integer.parseInt(d.getKey());

                    TimeSlot timeSlot = extractTimeSlot(d);

                    timeSlots[i] = timeSlot;
                }
                populateSpinner();
            }

            @Override
            public void onFailure() {

            }
        });
    }

    private TimeSlot extractTimeSlot(DataSnapshot timeSlot) {
        return new TimeSlot(timeSlot.child("month").getValue().toString(),
                timeSlot.child("day").getValue().toString(), timeSlot.child("hour").getValue().toString(),
                timeSlot.child("minute").getValue().toString(), extractAppointment(timeSlot.child("appointment")), (boolean)timeSlot.child("booked").getValue(),
                (boolean)timeSlot.child("unavailable").getValue());
    }

    private Appointment extractAppointment(DataSnapshot appointment) {
        return (appointment.getValue() == null)?null:new Appointment(appointment.child("notes").getValue().toString(),appointment.child("url").getValue().toString(),extractCustomer(appointment.child("customer")));
    }

    private Customer extractCustomer(DataSnapshot customer) {
        return new Customer(customer.child("firstName").getValue().toString(), customer.child("lastName").getValue().toString(), customer.child("phoneNumber").getValue().toString(), customer.child("email").getValue().toString());
    }

    private void populateSpinner() {
        Spinner spinner = findViewById(R.id.select_appointment_spinner);

        ArrayList<TimeSlot> bookedSlots = new ArrayList<>();
        for (TimeSlot t : timeSlots) {
            if (!t.isUnavailable() && t.isBooked()) {
                bookedSlots.add(t);
            }
        }

        ArrayAdapter<TimeSlot> adapter = new ArrayAdapter<TimeSlot>(
                this, android.R.layout.simple_spinner_item, bookedSlots);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

}
