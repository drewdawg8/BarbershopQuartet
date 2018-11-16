package com.doublea.barbershopquartet;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class ActivityBarberRegister extends AppCompatActivity {
    
    private EditText firstName;
    private EditText lastName;
    private EditText phoneNumber;
    private EditText email;
    private EditText password;
    private EditText confirmPassWord;
    private EditText description;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barber_register);
        initializeVariables();
    }

    private void initializeVariables() {
        firstName = findViewById(R.id.register_edit_text_first_name);
        lastName = findViewById(R.id.register_edit_text_last_name);
        phoneNumber = findViewById(R.id.register_edit_text_phone_number);
        email = findViewById(R.id.register_edit_text_email);
        password = findViewById(R.id.register_edit_text_password);
        confirmPassWord = findViewById(R.id.register_edit_text_confirm_password);
        description = findViewById(R.id.register_edit_text_description);
    }

    public void onClickRegister(View view) {

    }

    public void onClickLogIn(View view) {
    }
}
