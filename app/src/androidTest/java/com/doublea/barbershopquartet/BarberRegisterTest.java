package com.doublea.barbershopquartet;

import android.support.test.rule.ActivityTestRule;

import com.google.firebase.auth.FirebaseAuth;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertTrue;

public class BarberRegisterTest {

    public void sleep(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Rule
    public ActivityTestRule mActivityRule = new ActivityTestRule<>(ActivityMain.class);

    @Test
    public void testBarberRegister() {
        FirebaseAuth auth = FirebaseAuth.getInstance();

        if (auth != null) {
            auth.signOut();
        }

        onView(withId(R.id.barber)).perform(click());
        sleep();
        onView(withId(R.id.tv_register)).perform(click());
        sleep();
        onView(withId(R.id.register_edit_text_admin_password)).perform(replaceText("password"));
        onView(withId(R.id.register_edit_text_first_name)).perform(replaceText("Firstname"));
        onView(withId(R.id.register_edit_text_last_name)).perform(replaceText("Lastname"));
        onView(withId(R.id.register_edit_text_phone_number)).perform(replaceText("7035551234"));
        onView(withId(R.id.register_edit_text_email)).perform(replaceText("firstname@email.com"));
        onView(withId(R.id.register_edit_text_password)).perform(replaceText("test123"))
        onView(withId(R.id.register_edit_text_confirm_password)).perform(replaceText("test123"));
        onView(withId(R.id.register_edit_text_description)).perform(replaceText("This is a description"));
        sleep();
        onView(withId(R.id.register_button_register)).perform(click());

        assertTrue(auth != null);
    }
}
