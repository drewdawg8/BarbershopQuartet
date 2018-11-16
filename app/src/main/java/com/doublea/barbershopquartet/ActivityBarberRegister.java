package com.doublea.barbershopquartet;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.doublea.barbershopquartet.BackgroundTools.Barber;
import com.doublea.barbershopquartet.BackgroundTools.FirebaseInteraction;
import com.doublea.barbershopquartet.BackgroundTools.FirebaseReadListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
    private FirebaseAuth mAuth;
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
        mAuth = FirebaseAuth.getInstance();
    }

    /**
     * @TODO Authenticate user and wire up to firebase authentication service
     * @TODO Authenticate valid input
     *
     * @param view
     */
    public void onClickRegister(View view) {
        if (!validInputs())
            return;
        registerBarber(email.getText().toString(),password.getText().toString());
    }

    private void registerBarber(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user.getUid());
                        } else {
                            // If sign in fails, display a message to the user.
                        }

                        // ...
                    }
                });
    }

    private void updateUI(String uid) {
        Barber barber = new Barber(firstName.getText().toString(), lastName.getText().toString(),
                phoneNumber.getText().toString(), email.getText().toString(),
                description.getText().toString());
        firebase.write("Barbers/" + uid, barber);
        startActivity(new Intent(this, ActivityBarberMenu.class));

    }

    private boolean validInputs() {
        boolean valid = true;
        valid = checkEmptyTextboxes();
        valid = checkPasswordMatch();
        return valid;
    }

    private boolean checkPasswordMatch() {
        if (password.getText().toString().equals(confirmPassWord.getText().toString()))
            return true;
        else
            return false;
    }

    private boolean checkEmptyTextboxes() {
        boolean valid = true;
        valid = checkEmptyEditText(firstName);
        valid = checkEmptyEditText(lastName);
        valid = checkEmptyEditText(phoneNumber);
        valid = checkEmptyEditText(email);
        valid = checkEmptyEditText(password);
        valid = checkEmptyEditText(confirmPassWord);
        valid = checkEmptyEditText(description);
        return  valid;
    }
    private boolean checkEmptyEditText(EditText element){
        if (element.getText().toString() == ""){
            element.setBackgroundResource(R.drawable.background_error_txt_box);
            return false;
        }
        return true;
    }

    public void onClickswitchToLogIn(View view) {
        startActivity(new Intent(this, ActivityBarberLogin.class));

    }
}
