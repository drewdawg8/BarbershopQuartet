package com.doublea.barbershopquartet;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.doublea.barbershopquartet.BackgroundTools.FirebaseInteraction;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;

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
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser user = mAuth.getCurrentUser();
        if (mAuth.getCurrentUser() != null)
            updateUI();
    }
    public void onClickSwitchToRegister(View view) {
        startActivity(new Intent(this, ActivityBarberRegister.class));
    }

    public void onClickLogin(View view) {
        // TODO: Add error checking
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

    private void updateUI() {
        startActivity(new Intent(this, ActivityBarberMenu.class));
    }
}
