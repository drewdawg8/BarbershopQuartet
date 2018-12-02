package com.doublea.barbershopquartet;

import android.support.test.rule.ActivityTestRule;

import com.doublea.barbershopquartet.BackgroundTools.FirebaseInteraction;
import com.doublea.barbershopquartet.BackgroundTools.FirebaseReadListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class BarberRegisterTest {

    private FirebaseAuth auth;
    private FirebaseInteraction firebase;

    @Rule
    public ActivityTestRule mActivityRule = new ActivityTestRule<>(ActivityMain.class);

    @Before
    public void initializeFirebaseObjects() {
        auth = FirebaseAuth.getInstance();
        firebase = new FirebaseInteraction();
    }

    @Test
    public void testBarberRegister() {
        if (auth != null) {
            auth.signOut();
        }

        onView(withId(R.id.main_button_barber)).perform(click());
        sleep();
        onView(withId(R.id.tv_register)).perform(click());
        sleep();
        onView(withId(R.id.register_edit_text_admin_password)).perform(replaceText("password"));
        onView(withId(R.id.register_edit_text_first_name)).perform(replaceText("Firstname"));
        onView(withId(R.id.register_edit_text_last_name)).perform(replaceText("Lastname"));
        onView(withId(R.id.register_edit_text_phone_number)).perform(replaceText("7035551234"));
        onView(withId(R.id.register_edit_text_email)).perform(replaceText("firstname@email.com"));
        onView(withId(R.id.register_edit_text_password)).perform(replaceText("test123"));
        onView(withId(R.id.register_edit_text_confirm_password)).perform(replaceText("test123"));
        onView(withId(R.id.register_edit_text_description)).perform(replaceText("This is a description"), closeSoftKeyboard());
        sleep();
        onView(withId(R.id.register_button_register)).perform(click());
        sleep(10);
        assertTrue(auth != null);
        onView(withId(R.id.logout)).perform(click());
    }

    @Test
    public void testNewBarberLogin() {
        if (auth != null) {
            auth.signOut();
        }

        onView(withId(R.id.main_button_barber)).perform(click());
        sleep();
        onView(withId(R.id.login_edit_text_email)).perform(replaceText("firstname@email.com"));
        onView(withId(R.id.login_edit_text_password)).perform(replaceText("test123"));
        onView(withId(R.id.btn_login)).perform(click());
        sleep();
        assertTrue(auth != null);
        assertTrue(auth.getCurrentUser().getEmail().equals("firstname@email.com"));
        //onView(withId(R.id.logout)).perform(click());
    }

    @Test
    public void testDatabaseInitialization() {
        String uid = auth.getUid();

        String path = "Barbers/"+ uid;
        firebase.read(path, new FirebaseReadListener() {
            @Override
            public void onSuccess(DataSnapshot data) {
                assertEquals("Firstname", data.child("firstName").getValue().toString());
                assertEquals("Lastname", data.child("lastName").getValue().toString());
                assertEquals("7035551234", data.child("phoneNumber").getValue().toString());
                assertEquals("firstname@email.com", data.child("email").getValue().toString());
                assertEquals("This is a description", data.child("description").getValue().toString());

                for (int i = 1; i <= 12; i++) {
                    assertTrue(data.child(Integer.toString(i)).exists());
                }
            }

            @Override
            public void onFailure() {
                fail("Could not read from database");
            }
        });
    }

    @After
    public void deleteTestBarber() {
        String uid = auth.getUid();
        String path = "Barbers/" + uid;
        firebase.write(path, null);
        auth.getCurrentUser().delete();
        sleep(3);
    }

    // sleep for 1 second
    private void sleep(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // sleep for x seconds
    private void sleep(int x){
        try {
            Thread.sleep(1000 * x);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
