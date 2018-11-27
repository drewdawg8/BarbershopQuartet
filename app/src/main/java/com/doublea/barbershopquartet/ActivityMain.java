package com.doublea.barbershopquartet;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class ActivityMain extends AppCompatActivity {

    /**
     * Method triggered on start of Activity.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    /**
     * Method to handle user Menu.
     * @param menu
     * @return True
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     * Method to handle selecting an item from the Menu.
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Method to move to Barber login activity if that is selected by user.
     * @param view
     */
    public void onClickOpenBarberLogIn(View view) {
        Intent startNewActivity = new Intent(this, ActivityBarberLogin.class);
        startActivity(startNewActivity);
    }

    /**
     * Method to move to Customer sign up if that is selected by user.
     * @param view
     */
    public void onClickCustomerSignUp(View view) {
        Intent startNewActivity = new Intent(this, ActivityCustomerAppointmentRequest.class);
        startActivity(startNewActivity);
    }
}
