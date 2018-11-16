package com.doublea.barbershopquartet;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.doublea.barbershopquartet.BackgroundTools.Barber;
import com.doublea.barbershopquartet.BackgroundTools.FirebaseInteraction;
import com.doublea.barbershopquartet.BackgroundTools.FirebaseReadListener;
import com.google.firebase.database.DataSnapshot;

public class ActivityBarberRegister extends AppCompatActivity {
    
    private EditText firstName;
    private EditText lastName;
    private EditText phoneNumber;
    private EditText email;
    private EditText password;
    private EditText confirmPassWord;
    private EditText description;
    private FirebaseInteraction firebase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barber_register);
        initializeVariables();
        Toast.makeText(ActivityBarberRegister.this, "Authentication failed.",
                Toast.LENGTH_SHORT).show();
        firebase.read("Barbers/first name/firstName", new FirebaseReadListener(){

            @Override
            public void onSuccess(DataSnapshot data) {
                Toast.makeText(ActivityBarberRegister.this, data.getValue().toString(),
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure() {

            }
        });
    }

    private void initializeVariables() {
        firstName = findViewById(R.id.register_edit_text_first_name);
        lastName = findViewById(R.id.register_edit_text_last_name);
        phoneNumber = findViewById(R.id.register_edit_text_phone_number);
        email = findViewById(R.id.register_edit_text_email);
        password = findViewById(R.id.register_edit_text_password);
        confirmPassWord = findViewById(R.id.register_edit_text_confirm_password);
        description = findViewById(R.id.register_edit_text_description);
        firebase = new FirebaseInteraction();
    }

    /**
     * @TODO Authenticate user and wire up to firebase authentication service
     * @TODO Authenticate valid input
     *
     * @param view
     */
    public void onClickRegister(View view) {
        Barber barber = new Barber(firstName.getText().toString(), lastName.getText().toString(),
                phoneNumber.getText().toString(), email.getText().toString(),
                description.getText().toString());
    }

    public void onClickLogIn(View view) {
    }
}
