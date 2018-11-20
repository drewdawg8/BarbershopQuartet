package com.doublea.barbershopquartet;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class ActivityBarberMenu extends AppCompatActivity {

    private Button logoutBtn;
    private Button manageBtn;
    private Button appBtn;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barber_menu);
        initializeVarbles();
    }

    private void initializeVarbles() {
        logoutBtn = findViewById(R.id.logout);
        manageBtn = findViewById(R.id.manageSchedule);
        appBtn = findViewById(R.id.selectApp);
        mAuth = FirebaseAuth.getInstance();
    }



    public  void onClickLogout(View v){
        mAuth.signOut();
        startActivity(new Intent(this,ActivityBarberLogin.class));
    }

    public void onClickManageSchedule(View v){
        startActivity(new Intent(ActivityBarberMenu.this, ActivityManageSchedule.class));
    }

    public void onClickSelectAppointments(View v){
        startActivity(new Intent(ActivityBarberMenu.this, ActivityBarberSelectAppointment.class));

    }
}
