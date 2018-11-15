package com.doublea.barbershopquartet.BackgroundTools;

import android.util.Log;

import org.junit.Test;

import static org.junit.Assert.*;

public class FirebaseInteractionTest {
    FirebaseInteraction firebase = new FirebaseInteraction();
    @Test
    public void read() {
    }

    @Test
    public void readOnUpdate() {
    }

    @Test
    public void write() {
        Log.d("myoutput","test");
        firebase.write("Chicken/dogs/cats/meow", "Animals");
    }
}