package com.doublea.barbershopquartet;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class ActivityBarberLogin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barber_login);

    }

    public void onClickSwitchToRegister(View view) {
        startActivity(new Intent(this, ActivityBarberRegister.class));
    }

    public void onClickLogin(View view) {

    }
}
