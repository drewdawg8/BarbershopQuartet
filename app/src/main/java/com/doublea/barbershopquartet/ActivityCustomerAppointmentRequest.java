package com.doublea.barbershopquartet;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.doublea.barbershopquartet.BackgroundTools.Barber;
import com.doublea.barbershopquartet.BackgroundTools.FirebaseInteraction;
import com.doublea.barbershopquartet.BackgroundTools.FirebaseReadListener;
import com.doublea.barbershopquartet.BackgroundTools.TimeSlot;
import com.google.firebase.database.DataSnapshot;

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

    /**
     * Method triggered on start of activity.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_appointment_request);
        intializeVariables();
    }

    /**
     * Method that initializes all variables.
     */
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
        Toast.makeText(ActivityCustomerAppointmentRequest.this,"Loading Barbers from database",Toast.LENGTH_SHORT).show();
    }

    /**
     * Method to override the onStart method. It empties the spinner, and reads the barbers
     * from database and loads them into spinner for user to select from.
     */
    @Override
    protected void onStart() {
        super.onStart();
        emptySpiner();
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

    /**
     * Method that Empties the spinners by placing an empty array in the spinner.
     *
     * Borrowed from https://stackoverflow.com/questions/17311335/how-to-populate-a-spinner-from-string-array
     */
    private void emptySpiner() {
        ArrayList<String> list = new ArrayList<String>();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, list);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    /**
     * Helper method that populates the Spinner with Barbers for user to choose from.
     *
     * Borrowed from https://stackoverflow.com/questions/17311335/how-to-populate-a-spinner-from-string-array
     */
    private void populateFirstSpinner() {
        barberNames = new ArrayList<String>();
        for(Barber b : this.listOfBarbers){
            barberNames.add(b.getFirstName() + " " + b.getLastName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, barberNames);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        Toast.makeText(ActivityCustomerAppointmentRequest.this,"Barbers loaded",Toast.LENGTH_SHORT).show();
    }

    /**
     * Method to extract Barbers from the data from database, and loads into Barber object.
     * @param barber DataSnapshot with barber information.
     * @return Barber with all the data information.
     */
    private Barber extractBarber(DataSnapshot barber) {
        return new Barber(barber.child("firstName").getValue().toString(),barber.child("lastName").getValue().toString(),
                barber.child("phoneNumber").getValue().toString(), barber.child("email").getValue().toString(),
                barber.child("description").getValue().toString(), barber.child("uid").getValue().toString());
    }

    /**
     * Method to handle user pressing next, moving them to the next stage from selecting barber,
     * to selecting date, to Selecting a TimeSlot to book an appointment.
     * @param view
     */
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

    /**
     * Method to get Selected Barber from the Spinner.
     */
    private void getSelectedBarber() {
        String name = spinner.getSelectedItem().toString();
        int index = barberNames.indexOf(name);
        barber = listOfBarbers.get(index);
    }

    /**
     * A Method that gets the TimeSlot from user Selection, and translates it
     * so that it is converted into the TimeSlot fields for hours and minutes.
     */
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

    /**
     * Method that gets the date from the user using the Calendar API. It stores the user selection,
     * and using that selection and the Barber selection, the TimeSlots are loaded from the database.
     *
     * Borrowed from https://stackoverflow.com/a/39916305
     * and https://developer.android.com/reference/android/app/DatePickerDialog
     *
     * @param view
     */
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

    /**
     * Method to populate the Spinner with the TimeSlots from the database.
     * @param m Month that TimeSlot will be extracted from
     * @param d Day that TimeSlots will be extracted from.
     */
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

    /**
     * Method to load the Spinner with the TimeSlots with the give listOfTimeSlots. Only
     * shows available and not booked TimeSlots.
     *
     * Borrowed from https://stackoverflow.com/questions/17311335/how-to-populate-a-spinner-from-string-array
     *
     * @param listOfTimeSlots ArrayList with all the TimeSlots that the customer can book.
     */
    private void loadSpinnerWithTimeSlots(ArrayList<TimeSlot> listOfTimeSlots) {
        ArrayList<String> datesOfTimeSlots = new ArrayList<String>();
        for(TimeSlot timeSlot : listOfTimeSlots){
            // from stack overflow
          /*  String currentTime = new SimpleDateFormat("HH mm").format(Calendar.getInstance().getTime());
            String [] cTime = currentTime.split(" ");
            String [] tTime = timeSlot.toString().split(":");*/
            if(!timeSlot.isBooked() && !timeSlot.isUnavailable() && isSufficientlyAfterCurrentTime(timeSlot))
                datesOfTimeSlots.add(timeSlot.toString());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, datesOfTimeSlots);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        // This causes the format to become weird. not sure why.
        final int id = 77;
        spinner.setId(id);
    }

    /**
     * Method to check if the TimeSlot is within the range that is allowed to be booked for the day.
     * If a TimeSlot is after current time and within sufficient range it is shown to customer.
     * @param timeSlot TimeSlot to be checked.
     * @return True if that TimeSlot is within range to show to user, false otherwise.
     */
    private boolean isSufficientlyAfterCurrentTime(TimeSlot timeSlot) {

        Calendar currentTime = Calendar.getInstance();

        int month = Integer.parseInt(timeSlot.getMonth()) - 1;
        int day = Integer.parseInt(timeSlot.getDay());

        if (month == currentTime.get(Calendar.MONTH) && day == currentTime.get(Calendar.DAY_OF_MONTH)) {

            int hour = Integer.parseInt(timeSlot.getHour());
            int minute = Integer.parseInt(timeSlot.getMinute());

            int currentHour = currentTime.get(Calendar.HOUR);

            if (currentTime.get(Calendar.AM_PM) == Calendar.PM && currentHour < 12) currentHour += 12;

            if (hour > currentHour) {
                return true;
            } else if (hour == currentHour) {
                int currentMinute = currentTime.get(Calendar.MINUTE);
                if (minute - currentMinute < 20) return false;
                else return true;
            } else return false;
        } else {
            return true;
        }
    }

    /**
     * Method to get TimeSlot from DataSnapshot and convert it to TimeSlot object
     * @param timeSlot DataSnaphot with data
     * @return TimeSlot containing TimeSlot data.
     */
    private TimeSlot extractTimeSlot(DataSnapshot timeSlot) {
        return new TimeSlot(timeSlot.child("month").getValue().toString(),
                timeSlot.child("day").getValue().toString(), timeSlot.child("hour").getValue().toString(),
                timeSlot.child("minute").getValue().toString(), null, (boolean)timeSlot.child("booked").getValue(),
                (boolean)timeSlot.child("unavailable").getValue());
    }


}
