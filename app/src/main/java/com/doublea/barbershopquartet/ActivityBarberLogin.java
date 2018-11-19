package com.doublea.barbershopquartet;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.doublea.barbershopquartet.BackgroundTools.FirebaseInteraction;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

public class ActivityBarberLogin extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private FirebaseInteraction firebase;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barber_login);

        email = findViewById(R.id.login_edit_text_email);
        password = findViewById(R.id.login_edit_text_password);
        firebase = new FirebaseInteraction();
        mAuth = FirebaseAuth.getInstance();
    }

    public void onClickSwitchToRegister(View view) {
        startActivity(new Intent(this, ActivityBarberRegister.class));
    }

    public void onClickLogin(View view) {
        // TODO: Add error checking
        mAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString());

        if (mAuth.getCurrentUser() != null) {
            startActivity(new Intent(this, ActivityBarberMenu.class));
        }
    }
}
