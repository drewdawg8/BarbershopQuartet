package com.doublea.barbershopquartet;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

public class activity_customer_fill_out_appointment extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_fill_out_appointment);

    }


    public void onClickReserveAppointment(View view) {
        Intent goToMain;
        EditText edit = (EditText)findViewById(R.id.edit_text_first_name);
        String firstName = edit.getText().toString();
        edit = (EditText)findViewById(R.id.edit_text_last_name);
        String lastName = edit.getText().toString();
        edit = (EditText)findViewById(R.id.edit_text_email);
        String email = edit.getText().toString();
        edit = (EditText)findViewById(R.id.edit_text_phone_number);
        String phoneNumber = edit.getText().toString();

        // we need to update this so that it ensures email is a valid email.
        if(!firstName.matches("") && !lastName.matches("") && !email.matches("") && !phoneNumber.matches( "")) {
            goToMain = new Intent(this, ActivityMain.class);
            startActivity(goToMain);
        }
        else
        {
            if(firstName == ""){
                edit = (EditText)findViewById(R.id.edit_text_first_name);
                edit.setHintTextColor(Color.RED);
            }
            if(lastName == ""){
                edit = (EditText)findViewById(R.id.edit_text_last_name);
                edit.setHintTextColor(Color.RED);
            }
            if(email == ""){
                edit = (EditText)findViewById(R.id.edit_text_email);
                edit.setHintTextColor(Color.RED);
            }
            if(phoneNumber == ""){
                edit = (EditText)findViewById(R.id.edit_text_phone_number);
                edit.setHintTextColor(Color.RED);
            }
        }
    }


}
