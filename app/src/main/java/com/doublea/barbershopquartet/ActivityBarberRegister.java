package com.doublea.barbershopquartet;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.doublea.barbershopquartet.BackgroundTools.Barber;
import com.doublea.barbershopquartet.BackgroundTools.FirebaseInteraction;
import com.doublea.barbershopquartet.BackgroundTools.TimeSlot;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Calendar;

/**
 * CLASS HEAVILY BARROWS FROM MY PREVIOUS APP: BINUTTON (Almost an exact copy) - Author: Ahmed Ali
 */
public class ActivityBarberRegister extends AppCompatActivity {
    
    private EditText firstName;
    private EditText lastName;
    private EditText phoneNumber;
    private EditText email;
    private EditText password;
    private EditText confirmPassWord;
    private EditText description;
    private EditText adminPassword;
    private FirebaseInteraction firebase;
    private int chances;
    private FirebaseAuth mAuth;

    /**
     * Method triggered on the initialization of the Activity.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barber_register);
        initializeVariables();
        //testInputs();
    }

    /**
     * Helper method initialize all the class variables to get information from the
     * textboxes.
     */
    private void initializeVariables() {
        firstName = findViewById(R.id.register_edit_text_first_name);
        lastName = findViewById(R.id.register_edit_text_last_name);
        phoneNumber = findViewById(R.id.register_edit_text_phone_number);
        email = findViewById(R.id.register_edit_text_email);
        password = findViewById(R.id.register_edit_text_password);
        confirmPassWord = findViewById(R.id.register_edit_text_confirm_password);
        description = findViewById(R.id.register_edit_text_description);
        adminPassword = findViewById(R.id.register_edit_text_admin_password);
        firebase = new FirebaseInteraction();
        mAuth = FirebaseAuth.getInstance();
        chances = 3;
    }

    /**
     * Method to handle user clicking register, ensuring user entered valid inputs.
     * @param view
     */
    public void onClickRegister(View view) {
        if (!validInputs())
            return;
        registerBarber(email.getText().toString(),password.getText().toString());
    }

    /**
     * Method that registers barber using the barber email and password. Communicates
     * with database.
     * @param email Email of the new barber.
     * @param password password of the new Barber.
     */
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
                            Toast.makeText(ActivityBarberRegister.this, "Sign up failed", Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }

    /**
     * Method that writes to database the new Barber information, generates TimeSlots for that
     * barber, and moves to the next Activity.
     * @param uid
     */
    private void updateUI(String uid) {
        Barber barber = new Barber(firstName.getText().toString(), lastName.getText().toString(),
                phoneNumber.getText().toString(), email.getText().toString(),
                description.getText().toString(), uid);
        firebase.writeBarber(barber);
        generateTimeSlots(barber);
        startActivity(new Intent(ActivityBarberRegister.this, ActivityBarberMenu.class));
    }

    /**
     * Method to generate TimeSlots for a barber in the database. These TimeSlots hold the barber
     * Appointments.
     * @param barber Barber that will have TimeSlots generated for.
     */
    private void generateTimeSlots(Barber barber) {
        Calendar calendar = Calendar.getInstance();
        TimeSlot timeSlot = null;
        String month;
        String day;
        String hour;
        String minute;
        for (int i = 1; i < 13; i++){
            month = Integer.toString(i);
            calendar.set(Calendar.MONTH, i - 1);
            for (int j = 1; j < calendar.getActualMaximum(Calendar.DAY_OF_MONTH) + 1; j++){
                day = Integer.toString(j);
                for (double h = 9; h < 17; h += 0.5){
                    hour = Integer.toString((int)Math.floor(h));
                    minute = ((h - Math.floor(h)) > 0)? "30":"0";
                    timeSlot = new TimeSlot(month,day,hour,minute,null);
                    firebase.writeTimeslot(timeSlot,barber);
                }
            }
        }
    }

    /**
     * Helper method to check that information entered is valid.
     * @return True if information is valid, false otherwise.
     */
    private boolean validInputs() {
        boolean valid = true;
        if (!correctAdminPassword()) valid = false;
        if (!checkEmptyTextboxes()) valid = false;
        if (!checkPasswordMatch()) valid = false;
        return valid;
    }

    /**
     * Method that checks if the Barber registering is allowed to register as a barber. Checks the
     * admin password.
     * @return True if the barber is allowed to register, false otherwise.
     */
    private boolean correctAdminPassword() {
        String adminPass = adminPassword.getText().toString();
        if(!adminPass.equals("shaveyougoat")){
            chances--;
            Toast.makeText(ActivityBarberRegister.this, "Wrong admin password!!! you have " + chances + " more chances!",
                    Toast.LENGTH_SHORT).show();
            if (chances == 0){
                Toast.makeText(ActivityBarberRegister.this, "Wrong!, Please see our support team!",
                        Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this,ActivityMain.class));
            }
            return false;
        }
        return true;
    }

    /**
     * Method to ensure password entered by user is correct.
     * @return True if password is accurate, false otherwise.
     */
    private boolean checkPasswordMatch() {
        if (password.getText().toString().equals(confirmPassWord.getText().toString()))
            return true;
        else
            Toast.makeText(ActivityBarberRegister.this, "Passwords don't match", Toast.LENGTH_SHORT).show();
            return false;
    }

    /**
     * Method to check if the any of the textBoxes for necessary information is empty.
     * @return True if all the text boxes are populated, false if they are not all populated.
     */
    private boolean checkEmptyTextboxes() {
        boolean valid = true;
        if (!checkEmptyEditText(firstName)) valid = false;
        if (!checkEmptyEditText(lastName)) valid =  false;
        if (!checkEmptyEditText(phoneNumber)) valid = false;
        if (!checkEmptyEditText(email)) valid = false;
        if (!checkEmptyEditText(password)) valid = false;
        if (!checkEmptyEditText(confirmPassWord)) valid = false;
        if (!checkEmptyEditText(description)) valid =  false;
        return valid;
    }

    /**
     * Method to check if each EditText is empty
     * @param element EditText element being checked if empty.
     * @return True if EditText has values, false if it is empty.
     */
    private boolean checkEmptyEditText(EditText element){
        if (element.getText().toString().equals("")){
            element.setBackgroundResource(R.drawable.background_error_txt_box);
            return false;
        }
        element.setBackgroundResource(R.drawable.background_txt_box);
        return true;
    }

    /**
     * Method to handle user clicking Log in.
     * @param view
     */
    public void onClickswitchToLogIn(View view) {
        startActivity(new Intent(this, ActivityBarberLogin.class));

    }
}
