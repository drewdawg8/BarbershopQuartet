package com.doublea.barbershopquartet;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.doublea.barbershopquartet.BackgroundTools.FirebaseInteraction;
import com.doublea.barbershopquartet.BackgroundTools.TimeSlot;
import com.google.firebase.auth.FirebaseAuth;

import java.io.InputStream;

public class ActivityBarberManageAppointment extends AppCompatActivity {

    private TimeSlot localTimeSlot = ActivityBarberSelectAppointment.selectedTimeSlot;
    private FirebaseInteraction firebaseInteraction;
    private ImageView haircutPicture;

    /**
     * Method triggerd on initialization of Activity.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barber_manage_appointment);
    }

    /**
     * Method to override the onStart method of the activity. Here, we initialize the
     * the class variables.
     */
    @Override
    protected void onStart() {
        super.onStart();
        initializeInformation();
        firebaseInteraction = new FirebaseInteraction();
        /**
         * Stack overflow, URL: https://stackoverflow.com/questions/5776851/load-image-from-url
         */
        new DownloadImageTask((ImageView) findViewById(R.id.appointment_iv_haircut_picture))
                .execute(localTimeSlot.getAppointment().getURL());
    }


    /**
     * Okay I really love stack overflow:
     * URL: https://stackoverflow.com/questions/5776851/load-image-from-url
     */
    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }

    /**
     * Method to handle removing an appointment from Barber schedules.
     * @param view
     */
    public void onClickRemoveAppointment(View view) {
        TimeSlot slot = new TimeSlot(localTimeSlot.getMonth(), localTimeSlot.getDay(), localTimeSlot.getHour(),
                localTimeSlot.getMinute(), null);
        firebaseInteraction.writeTimeslot(slot, FirebaseAuth.getInstance().getUid());
        startActivity(new Intent(this,ActivityBarberSelectAppointment.class));
    }

    /**
     * Helper method to grab all the TextViews and initialize their values.
     */
    private void initializeInformation(){
        TextView textView = (TextView)findViewById(R.id.edit_text_name);
        textView.setText(ActivityBarberSelectAppointment.selectedTimeSlot.getAppointment().getCustomer().toString());
        textView = (TextView)findViewById(R.id.edit_text_email);
        textView.setText(ActivityBarberSelectAppointment.selectedTimeSlot.getAppointment().getCustomer().getEmail());
        textView = (TextView)findViewById(R.id.edit_text_phone_number);
        textView.setText(ActivityBarberSelectAppointment.selectedTimeSlot.getAppointment().getCustomer().getPhoneNumber());
        textView = (TextView)findViewById(R.id.edit_text_time_slot);
        textView.setText(ActivityBarberSelectAppointment.selectedTimeSlot.toString());
        textView = (TextView)findViewById(R.id.edit_text_notes);
        textView.setText(ActivityBarberSelectAppointment.selectedTimeSlot.getAppointment().getNotes());
        haircutPicture = (ImageView) findViewById(R.id.appointment_iv_haircut_picture);
    }
}