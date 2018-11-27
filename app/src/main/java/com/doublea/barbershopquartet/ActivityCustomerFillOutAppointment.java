package com.doublea.barbershopquartet;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.doublea.barbershopquartet.BackgroundTools.Appointment;
import com.doublea.barbershopquartet.BackgroundTools.Customer;
import com.doublea.barbershopquartet.BackgroundTools.FileUploadListener;
import com.doublea.barbershopquartet.BackgroundTools.FirebaseInteraction;

import static com.doublea.barbershopquartet.ActivityCustomerAppointmentRequest.barber;
import static com.doublea.barbershopquartet.ActivityCustomerAppointmentRequest.timeSlot;

public class ActivityCustomerFillOutAppointment extends AppCompatActivity {


    private static final int READ_REQUEST_CODE = 66 ;
    FirebaseInteraction firebaseInteraction = new FirebaseInteraction();
    private Appointment appointment;
    private Customer customer;
    private String url;
    private Button reserve;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_fill_out_appointment);
        initializeVariables();
    }


    private void initializeVariables(){
        appointment = new Appointment(null, null, null);
        customer = new Customer(null, null, null, null);
        reserve = (Button) findViewById(R.id.button_reserve_appointment);
        url = "";
    }

    private boolean initializeAndCheckAppointmentValues(){
        boolean entered = true;
        EditText edit = (EditText)findViewById(R.id.edit_text_first_name);
        if(edit.getText().toString().equals("") || edit.getText().toString().length() >= 20){
            entered = false;
           edit.setHintTextColor(Color.RED);
            Toast.makeText(ActivityCustomerFillOutAppointment.this, "Make sure your input is between 1 and 20 characters", Toast.LENGTH_SHORT).show();
        }
        customer.setFirstName(edit.getText().toString());
        edit = (EditText)findViewById(R.id.edit_text_last_name);
        if(edit.getText().toString().equals("")  || edit.getText().toString().length() >= 20){
            entered = false;
            edit.setHintTextColor(Color.RED);
            Toast.makeText(ActivityCustomerFillOutAppointment.this, "Make sure your input is between 1 and 20 characters", Toast.LENGTH_SHORT).show();
        }
        customer.setLastName(edit.getText().toString());
        edit = (EditText)findViewById(R.id.edit_text_email);
        if(edit.getText().toString().equals("")  || edit.getText().toString().length() >= 20){
            entered = false;
            edit.setHintTextColor(Color.RED);
            Toast.makeText(ActivityCustomerFillOutAppointment.this, "Make sure your input is between 1 and 20 characters", Toast.LENGTH_SHORT).show();
        }
        customer.setEmail(edit.getText().toString());
        edit = (EditText)findViewById(R.id.edit_text_phone_number);
        if(edit.getText().toString().equals("")  || edit.getText().toString().length() >= 20 ){
            entered = false;
            edit.setHintTextColor(Color.RED);
            Toast.makeText(ActivityCustomerFillOutAppointment.this, "Make sure your input is between 1 and 20 characters", Toast.LENGTH_SHORT).show();
        }
        customer.setPhoneNumber(edit.getText().toString());
        edit = (EditText)findViewById(R.id.edit_text_notes);
        appointment.setCustomer(customer);
        appointment.setNotes(edit.getText().toString());
        appointment.setURL(this.url);
        return entered;
    }

    public void onClickReserveAppointment(View view) {
        if(initializeAndCheckAppointmentValues()){
            // send TimeSlot with appointment and image information
            initialzeTimeSlotAndSendToFirebase();
            // go to next activity
            startActivity(new Intent(this, ActivityMain.class));
        }

    }

    private void initialzeTimeSlotAndSendToFirebase() {
        timeSlot.setAppointment(appointment);
        timeSlot.setBooked(true);
        // send to firebase with precise mapping
        firebaseInteraction.writeTimeslot(timeSlot, barber);
    }


    public void onClickUploadPhoto(View view) {
        performFileSearch();
        // set the class variable url to obtained URL
    }

    /**
     * From android documentation:
     * URL: https://developer.android.com/guide/topics/providers/document-provider
     */
    public void performFileSearch() {

        // ACTION_OPEN_DOCUMENT is the intent to choose a file via the system's file
        // browser.
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        reserve.setEnabled(false);
        // Filter to only show results that can be "opened", such as a
        // file (as opposed to a list of contacts or timezones)
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        // Filter to show only images, using the image MIME data type.
        // If one wanted to search for ogg vorbis files, the type would be "audio/ogg".
        // To search for all documents available via installed storage providers,
        // it would be "*/*".
        intent.setType("image/*");

        startActivityForResult(intent, READ_REQUEST_CODE);
    }

    /**
     * From android documentation:
     * URL: https://developer.android.com/guide/topics/providers/document-provider
     * @param requestCode
     * @param resultCode
     * @param resultData
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent resultData) {

        // The ACTION_OPEN_DOCUMENT intent was sent with the request code
        // READ_REQUEST_CODE. If the request code seen here doesn't match, it's the
        // response to some other intent, and the code below shouldn't run at all.

        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            // The document selected by the user won't be returned in the intent.
            // Instead, a URI to that document will be contained in the return intent
            // provided to this method as a parameter.
            // Pull that URI using resultData.getData().

            Uri uri = null;
            Toast.makeText(ActivityCustomerFillOutAppointment.this, "Picture Chosen", Toast.LENGTH_SHORT).show();
            if (resultData != null) {
                uri = resultData.getData();
                firebaseInteraction.uploadFile(uri, new FileUploadListener() {
                    @Override
                    public void onSuccess(String newUrl) {
                        Toast.makeText(ActivityCustomerFillOutAppointment.this, "Upload successful", Toast.LENGTH_SHORT).show();
                        url = newUrl;
                        reserve.setEnabled(true);
                    }

                    @Override
                    public void onFailure() {
                        Toast.makeText(ActivityCustomerFillOutAppointment.this, "Upload Failed", Toast.LENGTH_SHORT).show();
                        reserve.setEnabled(true);
                    }
                });

            }
        }
    }
}
