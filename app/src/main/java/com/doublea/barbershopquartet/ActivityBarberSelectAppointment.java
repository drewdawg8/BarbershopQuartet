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
import android.widget.Toast;

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

    /**
     * Methrod triggered on create of Activity.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barber_select_appointment);

        mAuth = FirebaseAuth.getInstance();
        firebase = new FirebaseInteraction();
    }

    /**
     * Method to overrid onStart netgid if activity.
     */
    @Override
    public void onStart() {
        super.onStart();
        timeSlots = new TimeSlot[16];
    }

    /**
     * Assures that pressing back returns to barber menu
     */
    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, ActivityBarberMenu.class));
    }

    /**
     * Method to handle the user selecting SelectDate to choose Appointments from.
     *
     * Borrowed from https://stackoverflow.com/a/39916305
     * and https://developer.android.com/reference/android/app/DatePickerDialog
     *
     * @param view
     */
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

    /**
     * Method to handle user clicking submit. It starts the new Activity for barber to manage
     * selected Appointment.
     * @param view
     */
    public void onClickSubmit(View view) {
        Spinner spinner = findViewById(R.id.select_appointment_spinner);
        selectedTimeSlot = (TimeSlot) spinner.getSelectedItem();
        startActivity(new Intent(this, ActivityBarberManageAppointment.class));
    }

    /**
     * Method to get all the TimeSlots for the Barber and load them for the barber to view.
     */
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

    /**
     * Method to extract the information from the DataSnapshot sent by database, and casting
     * that information into a TimeSlot object.
     * @param timeSlot DataSnapshot returned from database.
     * @return TimeSlot containing all the information from the database.
     */
    private TimeSlot extractTimeSlot(DataSnapshot timeSlot) {
        return new TimeSlot(timeSlot.child("month").getValue().toString(),
                timeSlot.child("day").getValue().toString(), timeSlot.child("hour").getValue().toString(),
                timeSlot.child("minute").getValue().toString(), extractAppointment(timeSlot.child("appointment")), (boolean)timeSlot.child("booked").getValue(),
                (boolean)timeSlot.child("unavailable").getValue());
    }

    /**
     * Method to extract the Appointment object from DataSnapshot from database into
     * Appointment object.
     * @param appointment DataSnapshot with Appointment information.
     * @return Appointment with database information.
     */
    private Appointment extractAppointment(DataSnapshot appointment) {
        return (appointment.getValue() == null)?null:new Appointment(appointment.child("notes").getValue().toString(),appointment.child("url").getValue().toString(),extractCustomer(appointment.child("customer")));
    }

    /**
     * Method to extract the Customer information from the DataSnapshot from databse.
     * @param customer DataSnapshot containing the customer information.
     * @return Customer with all the customer information.
     */
    private Customer extractCustomer(DataSnapshot customer) {
        return new Customer(customer.child("firstName").getValue().toString(), customer.child("lastName").getValue().toString(), customer.child("phoneNumber").getValue().toString(), customer.child("email").getValue().toString());
    }

    /**
     * Method to populate the spinner with Appointments for barber to view. It only populates that
     * are available and booked by a customer.
     *
     * Borrowed from https://stackoverflow.com/questions/17311335/how-to-populate-a-spinner-from-string-array
     */
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
