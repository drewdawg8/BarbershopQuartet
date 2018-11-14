package com.doublea.barbershopquartet;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.Spinner;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class ActivityManageSchedule extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Spinner spinnerMonth;
    private Spinner spinnerDay;
    private RadioButton[] radioButtons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_schedule);

        spinnerMonth = findViewById(R.id.spinner_month);
        spinnerDay = findViewById(R.id.spinner_day);

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

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.months, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMonth.setAdapter(adapter);
        spinnerMonth.setOnItemSelectedListener(this);

        spinnerDay.setEnabled(false);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        switch(view.getId()) {
            case R.id.spinner_month:
                populateSpinnerDay(getDaysInMonth(i));
                break;
            case R.id.spinner_day:
                populateRadioButtons(spinnerMonth.getSelectedItem().toString(), i + 1);
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private int getDaysInMonth(int month) {
        int days = 0;
        switch(month) {
            case 0:  days = 31; break; // Jan
            case 1:  days = 28; break; // Feb
            case 2:  days = 31; break; // Mar
            case 3:  days = 30; break; // Apr
            case 4:  days = 31; break; // May
            case 5:  days = 30; break; // Jun
            case 6:  days = 31; break; // Jul
            case 7:  days = 31; break; // Aug
            case 8:  days = 30; break; // Sep
            case 9:  days = 31; break; // Oct
            case 10: days = 30; break; // Nov
            case 11: days = 31; break; // Dec
        }
        return days;
    }

    private void populateSpinnerDay(int daysInMonth) {
        List<String> days = new ArrayList<>();
        for (int i = 0; i < daysInMonth; i++)
            days.add(Integer.toString(i));

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, days);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDay.setAdapter(adapter);
        spinnerDay.setEnabled(true);
    }

    private void populateRadioButtons(String month, int date) {
        for (RadioButton rb : radioButtons) rb.setEnabled(true);
        // TODO load time slot data for given date and automatically check radio buttons
    }

    public void onClickRadioButton(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        switch(view.getId()) {
            case R.id.radio_timeslot0:
                // TODO update time slot availability when radio buttons are checked/unchecked
                if (checked) {
                    // mark time slot as available
                } else {
                    // mark time slot as unavailable
                }
                break;
            case R.id.radio_timeslot1:

                break;
            case R.id.radio_timeslot2:

                break;
            case R.id.radio_timeslot3:

                break;
            case R.id.radio_timeslot4:

                break;
            case R.id.radio_timeslot5:

                break;
            case R.id.radio_timeslot6:

                break;
            case R.id.radio_timeslot7:

                break;
            case R.id.radio_timeslot8:

                break;
            case R.id.radio_timeslot9:

                break;
            case R.id.radio_timeslot10:

                break;
            case R.id.radio_timeslot11:

                break;
            case R.id.radio_timeslot12:

                break;
            case R.id.radio_timeslot13:

                break;
            case R.id.radio_timeslot14:

                break;
            case R.id.radio_timeslot15:

                break;
        }
    }

    public void onClickSubmit(View view) {
        // TODO submit changes to database
    }
}
