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

    /**
     * Method triggered on creation of Activity.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barber_menu);
        initializeVarbles();
    }

    /**
     * Method to help initialize variables in the class to buttons and the
     * FirebaseAuth variable.
     */
    private void initializeVarbles() {
        logoutBtn = findViewById(R.id.logout);
        manageBtn = findViewById(R.id.manageSchedule);
        appBtn = findViewById(R.id.selectApp);
        mAuth = FirebaseAuth.getInstance();
    }


    /**
     * Method to handle user Logging out.
     * @param v
     */
    public  void onClickLogout(View v){
        mAuth.signOut();
        startActivity(new Intent(this,ActivityBarberLogin.class));
    }

    /**
     * Method to handle barber clicking manage schedule button.
     * @param v
     */
    public void onClickManageSchedule(View v){
        startActivity(new Intent(ActivityBarberMenu.this, ActivityManageSchedule.class));
    }

    /**
     * Method to handle barber selection of an Appointment.
     * @param v
     */
    public void onClickSelectAppointments(View v){
        startActivity(new Intent(ActivityBarberMenu.this, ActivityBarberSelectAppointment.class));

    }
}
