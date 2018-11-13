package com.doublea.barbershopquartet;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ActivityBarberMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barber_menu);
    }
    final Button logoutBtn = findViewById(R.id.logout);
    final Button manageBtn = findViewById(R.id.manageSchedule);
    final Button appBtn = findViewById(R.id.selectApp);

    public  void onClickLogout(View v){

    }

    public void onClickManageSchedule(View v){

    }

    public void onClickSelectAppointments(View v){

    }
}
