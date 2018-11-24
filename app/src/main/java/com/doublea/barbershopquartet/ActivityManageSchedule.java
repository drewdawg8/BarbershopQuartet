package com.doublea.barbershopquartet;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.doublea.barbershopquartet.BackgroundTools.Appointment;
import com.doublea.barbershopquartet.BackgroundTools.Customer;
import com.doublea.barbershopquartet.BackgroundTools.FirebaseInteraction;
import com.doublea.barbershopquartet.BackgroundTools.FirebaseReadListener;
import com.doublea.barbershopquartet.BackgroundTools.TimeSlot;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ActivityManageSchedule extends AppCompatActivity{

    private CheckBox[] checkBoxes;
    private FirebaseInteraction firebase;
    private FirebaseAuth mAuth;
    private Calendar scheduleDate; // date to edit
    private TimeSlot[] timeSlots;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_schedule);

        checkBoxes = new CheckBox[]{
                findViewById(R.id.schedule_checkbox0),
                findViewById(R.id.schedule_checkbox1),
                findViewById(R.id.schedule_checkbox2),
                findViewById(R.id.schedule_checkbox3),
                findViewById(R.id.schedule_checkbox4),
                findViewById(R.id.schedule_checkbox5),
                findViewById(R.id.schedule_checkbox6),
                findViewById(R.id.schedule_checkbox7),
                findViewById(R.id.schedule_checkbox8),
                findViewById(R.id.schedule_checkbox9),
                findViewById(R.id.schedule_checkbox10),
                findViewById(R.id.schedule_checkbox11),
                findViewById(R.id.schedule_checkbox12),
                findViewById(R.id.schedule_checkbox13),
                findViewById(R.id.schedule_checkbox14),
                findViewById(R.id.schedule_checkbox15)};

        firebase = new FirebaseInteraction();
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onStart() {
        super.onStart();

        for (CheckBox c : checkBoxes) c.setEnabled(false);
        findViewById(R.id.schedule_button_submit).setEnabled(false);
        EditText t = findViewById(R.id.schedule_edit_text_display_date);
        t.setText("");
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
                EditText e = findViewById(R.id.schedule_edit_text_display_date);
                SimpleDateFormat sdf = new SimpleDateFormat("EEE, MMM dd");
                e.setText(sdf.format(calendar.getTime()));
                scheduleDate = calendar;
                populateCheckBoxs();
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

    private void populateCheckBoxs() {
        clearCheckBoxes();
        for (CheckBox c : checkBoxes) c.setEnabled(true);

        String month = Integer.toString(scheduleDate.get(Calendar.MONTH) + 1);
        String day = Integer.toString(scheduleDate.get(Calendar.DAY_OF_MONTH));

        String UID = mAuth.getUid();
        String path = "Barbers/" + UID + "/" + month + "/" + day;
        firebase.read(path, new FirebaseReadListener() {
            @Override
            public void onSuccess(DataSnapshot data) {
                for (DataSnapshot d: data.getChildren()){
                    int i = Integer.parseInt(d.getKey());

                    TimeSlot timeSlot = extractTimeSlot(d);

                    timeSlots[i] = timeSlot;

                    if (!timeSlot.isUnavailable()) checkBoxes[i].setChecked(true);
                }
            }

            @Override
            public void onFailure() {
                Toast.makeText(ActivityManageSchedule.this, "Could not load timeslots.", Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.schedule_button_submit).setEnabled(true);
    }

    public void onClickCheckBox(View view) {
        boolean checked = ((CheckBox) view).isChecked();
        TimeSlot timeSlot;

        switch(view.getId()) {
            case R.id.schedule_checkbox0:
                timeSlot = timeSlots[0];
                break;
            case R.id.schedule_checkbox1:
                timeSlot = timeSlots[1];
                break;
            case R.id.schedule_checkbox2:
                timeSlot = timeSlots[2];
                break;
            case R.id.schedule_checkbox3:
                timeSlot = timeSlots[3];
                break;
            case R.id.schedule_checkbox4:
                timeSlot = timeSlots[4];
                break;
            case R.id.schedule_checkbox5:
                timeSlot = timeSlots[5];
                break;
            case R.id.schedule_checkbox6:
                timeSlot = timeSlots[6];
                break;
            case R.id.schedule_checkbox7:
                timeSlot = timeSlots[7];
                break;
            case R.id.schedule_checkbox8:
                timeSlot = timeSlots[8];
                break;
            case R.id.schedule_checkbox9:
                timeSlot = timeSlots[9];
                break;
            case R.id.schedule_checkbox10:
                timeSlot = timeSlots[10];
                break;
            case R.id.schedule_checkbox11:
                timeSlot = timeSlots[11];
                break;
            case R.id.schedule_checkbox12:
                timeSlot = timeSlots[12];
                break;
            case R.id.schedule_checkbox13:
                timeSlot = timeSlots[13];
                break;
            case R.id.schedule_checkbox14:
                timeSlot = timeSlots[14];
                break;
            default:
                timeSlot = timeSlots[15];
                break;
        }

        if (checked) {
            timeSlot.setUnavailable(false);
        } else {
            timeSlot.setUnavailable(true);
        }
    }

    public void onClickSubmit(View view) {

        clearCheckBoxes();
        for (int i = 0; i < 16; i++) {
            TimeSlot t = timeSlots[i];
            if (t.isUnavailable() && t.isBooked()) {
                t.setBooked(false);
                t.setAppointment(null);
            }
            firebase.writeTimeslot(t, mAuth.getUid());
        }
        this.onStart();
    }
    
    private void clearCheckBoxes() {
        for (CheckBox c : checkBoxes) c.setChecked(false);
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
}
