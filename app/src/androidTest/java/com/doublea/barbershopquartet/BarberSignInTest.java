package com.doublea.barbershopquartet;

import android.support.test.espresso.contrib.PickerActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;

import java.util.Calendar;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.core.AllOf.allOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class BarberSignInTest {
    public void delay(int x){
        try {
            Thread.sleep(x);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    @Rule
    public ActivityTestRule mActivityRule = new ActivityTestRule<>(ActivityMain.class);

    @Test
    public void barberLogsIn() {
        // Launch Main Activity
        // click the customer button
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        if (mAuth != null)
            mAuth.signOut();
        delay(3000);
        onView(withId(R.id.main_btn_barber)).perform(click());
        //wait
        delay(3000);
        //Fill email
        onView(withId(R.id.login_edit_text_email)).perform( replaceText("ahmedhhw@gmail.com"),closeSoftKeyboard());
        //wait
        delay(3000);
        //Fill pass
        onView(withId(R.id.login_edit_text_password)).perform( replaceText("ahmedmo"),closeSoftKeyboard());
        //Click sign in
        onView(withId(R.id.btn_login)).perform(click());
        assertTrue(mAuth != null);
    }

}

