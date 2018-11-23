package com.doublea.barbershopquartet;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;

import com.doublea.barbershopquartet.BackgroundTools.Appointment;
import com.doublea.barbershopquartet.BackgroundTools.Barber;
import com.doublea.barbershopquartet.BackgroundTools.FirebaseInteraction;
import com.doublea.barbershopquartet.BackgroundTools.FirebaseReadListener;
import com.doublea.barbershopquartet.BackgroundTools.TimeSlot;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ActivityManageSchedule extends AppCompatActivity{

    private RadioButton[] radioButtons;
    private FirebaseInteraction firebase;
    private Calendar scheduleDate; // date to edit
    private TimeSlot[] timeSlots;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_schedule);

        radioButtons = new RadioButton[]{
                findViewById(R.id.radio_timeslot0),
                findViewById(R.id.radio_timeslot1),
                findViewById(R.id.radio_timeslot2),
                findViewById(R.id.radio_timeslot3),
                findViewById(R.id.radio_timeslot4),
                findViewById(R.id.radio_timeslot5),
                findViewById(R.id.radio_timeslot6),
                findViewById(R.id.radio_timeslot7),
                findViewById(R.id.radio_timeslot8),
                findViewById(R.id.radio_timeslot9),
                findViewById(R.id.radio_timeslot10),
                findViewById(R.id.radio_timeslot11),
                findViewById(R.id.radio_timeslot12),
                findViewById(R.id.radio_timeslot13),
                findViewById(R.id.radio_timeslot14),
                findViewById(R.id.radio_timeslot15)};

        for (RadioButton rb : radioButtons) rb.setEnabled(false);

        firebase = new FirebaseInteraction();
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
                populateRadioButtons();
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

    private void populateRadioButtons() {
        for (RadioButton rb : radioButtons) rb.setEnabled(true);

        String month = Integer.toString(scheduleDate.get(Calendar.MONTH)) + 1;
        String day = Integer.toString(scheduleDate.get(Calendar.DAY_OF_MONTH));

        Barber barber = ActivityBarberLogin.barber;
        String UID = barber.getUid();
        String path = "Barbers/" + UID + "/" + month + "/" + day;
        firebase.read(path, new FirebaseReadListener() {
            @Override
            public void onSuccess(DataSnapshot data) {
                for (DataSnapshot d: data.getChildren()){
                    int i = Integer.parseInt(d.getKey());
                    Appointment app = d.child("appointment").getValue(Appointment.class);
                    TimeSlot timeSlot = new TimeSlot(d.child("month").getValue().toString(), d.child("day").getValue().toString(),
                            d.child("hour").getValue().toString(), d.child("minute").getValue().toString(), app);

                    timeSlot.setBooked(Boolean.parseBoolean(d.child("booked").getValue().toString()));
                    timeSlot.setUnavailable(Boolean.parseBoolean(d.child("unavailable").getValue().toString()));

                    timeSlots[i] = timeSlot;

                    if (!timeSlot.isUnavailable()) radioButtons[i].setChecked(true);
                }
            }

            @Override
            public void onFailure() {

            }
        });

        findViewById(R.id.schedule_button_submit).setEnabled(true);
    }

    public void onClickRadioButton(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        TimeSlot timeSlot;

        switch(view.getId()) {
            case R.id.radio_timeslot0:
                timeSlot = timeSlots[0];
                break;
            case R.id.radio_timeslot1:
                timeSlot = timeSlots[1];
                break;
            case R.id.radio_timeslot2:
                timeSlot = timeSlots[2];
                break;
            case R.id.radio_timeslot3:
                timeSlot = timeSlots[3];
                break;
            case R.id.radio_timeslot4:
                timeSlot = timeSlots[4];
                break;
            case R.id.radio_timeslot5:
                timeSlot = timeSlots[5];
                break;
            case R.id.radio_timeslot6:
                timeSlot = timeSlots[6];
                break;
            case R.id.radio_timeslot7:
                timeSlot = timeSlots[7];
                break;
            case R.id.radio_timeslot8:
                timeSlot = timeSlots[8];
                break;
            case R.id.radio_timeslot9:
                timeSlot = timeSlots[9];
                break;
            case R.id.radio_timeslot10:
                timeSlot = timeSlots[10];
                break;
            case R.id.radio_timeslot11:
                timeSlot = timeSlots[11];
                break;
            case R.id.radio_timeslot12:
                timeSlot = timeSlots[12];
                break;
            case R.id.radio_timeslot13:
                timeSlot = timeSlots[13];
                break;
            case R.id.radio_timeslot14:
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
        // TODO write timeSlots array to database; check if booked appointments need to be removed?

        this.recreate(); // relaunch the activity to select another date
    }
}
