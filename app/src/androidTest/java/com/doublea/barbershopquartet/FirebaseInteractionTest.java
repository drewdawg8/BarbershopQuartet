package com.doublea.barbershopquartet;

import android.support.test.rule.ActivityTestRule;

import com.doublea.barbershopquartet.BackgroundTools.FirebaseInteraction;
import com.doublea.barbershopquartet.BackgroundTools.FirebaseReadListener;
import com.google.firebase.database.DataSnapshot;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.core.AllOf.allOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class FirebaseInteractionTest {
    public void delay(int x){
        try {
            Thread.sleep(x);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    @Rule
    public ActivityTestRule mActivityRule = new ActivityTestRule<>(ActivityMain.class);
    FirebaseInteraction firebase;
    @Test
    public void barberLogsIn() {
        // Launch Main Activity
        // click the customer button
        firebase = new FirebaseInteraction();
        final Car buggatti = new Car("Buggatti", 4);
        firebase.write("Cars/" + buggatti.getName(), buggatti);
        delay(3000);
        firebase.read("Cars/" + buggatti.getName(), new FirebaseReadListener() {
            @Override
            public void onSuccess(DataSnapshot data) {
                Car newCar = new Car(data.child("name").getValue().toString(),Integer.parseInt(data.child("numOfWheels").getValue().toString()));
                firebase.write("Cars",null);
                assertEquals(newCar.getName(), "Buggatti");
                assertEquals(newCar.getNumOfWheels(), 4);
                assertTrue(buggatti.isEqual((newCar)));
            }

            @Override
            public void onFailure() {

            }
        });
    }
    class Car {
        String name = "";
        int numOfWheels = 0;
        public Car(String name, int numOfWheels){
            this.name = name;
            this.numOfWheels = numOfWheels;
        }
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getNumOfWheels() {
            return numOfWheels;
        }

        public void setNumOfWheels(int numOfWheels) {
            this.numOfWheels = numOfWheels;
        }

        public boolean isEqual(Car car) {
            return (car.name.equals(this.name) && car.numOfWheels == this.numOfWheels);
        }
    }
}

