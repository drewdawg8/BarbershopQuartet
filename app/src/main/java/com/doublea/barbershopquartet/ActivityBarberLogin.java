package com.doublea.barbershopquartet;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.doublea.barbershopquartet.BackgroundTools.FirebaseInteraction;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * CLASS HEAVILY BARROWS FROM MY PREVIOUS APP: BINUTTON (Almost an exact copy) - Author: Ahmed Ali
 */
public class ActivityBarberLogin extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private FirebaseInteraction firebase;
    private FirebaseAuth mAuth;

    /**
     * Method triggered on start of the Activity. The class variables are initialized
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barber_login);

        email = findViewById(R.id.login_edit_text_email);
        password = findViewById(R.id.login_edit_text_password);
        firebase = new FirebaseInteraction();
        mAuth = FirebaseAuth.getInstance();
    }

    /**
     * Method to override the onBackPressed() event to prevent user from going back after
     * loggin out.
     */
    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, ActivityMain.class));
    }

    /**
     * Method to override onStart method of ActivtyBatberLogin. Checks if user is signed in already.
     */
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser user = mAuth.getCurrentUser();
        if (mAuth.getCurrentUser() != null)
            updateUI();
    }

    /**
     * Method to handle the click of register, and enables user to register himself as a barber.
     * @param view
     */
    public void onClickSwitchToRegister(View view) {
        startActivity(new Intent(this, ActivityBarberRegister.class));
    }

    /**
     * Method to handle the login of the barber.
     * Code borrowed from Firebase assistant
     * @param view
     */
    public void onClickLogin(View view) {
        mAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful())
                    updateUI();
                else
                    Toast.makeText(ActivityBarberLogin.this, "Authentication failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Method to move to the next Activity.
     */
    private void updateUI() {
        startActivity(new Intent(this, ActivityBarberMenu.class));
    }
}
