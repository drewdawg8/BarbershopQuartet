package com.doublea.barbershopquartet;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.doublea.barbershopquartet.BackgroundTools.Appointment;
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
    TimeSlot[] timeSlots;

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
                populateSpinner();
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
        TimeSlot choice = (TimeSlot) spinner.getSelectedItem();
        Appointment app = choice.getAppointment();

        // TODO pass appointment to ActivityBarberManageAppointment
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
                    Appointment app = d.child("appointment").getValue(Appointment.class);
                    TimeSlot timeSlot = new TimeSlot(d.child("month").getValue().toString(), d.child("day").getValue().toString(),
                            d.child("hour").getValue().toString(), d.child("minute").getValue().toString(), app);

                    timeSlot.setBooked((boolean) d.child("booked").getValue());
                    timeSlot.setUnavailable((boolean) d.child("unavailable").getValue());

                    timeSlots[i] = timeSlot;
                }
            }

            @Override
            public void onFailure() {

            }
        });
    }

    private void populateSpinner() {
        Spinner spinner = findViewById(R.id.select_appointment_spinner);

        ArrayList<TimeSlot> bookedSlots = new ArrayList<>();
        for (TimeSlot t : timeSlots) {
            if (!t.isUnavailable() && !t.isBooked()) {
                bookedSlots.add(t);
            }
        }

        ArrayAdapter<TimeSlot> adapter = new ArrayAdapter<TimeSlot>(
                this, android.R.layout.simple_spinner_item, bookedSlots);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

}
