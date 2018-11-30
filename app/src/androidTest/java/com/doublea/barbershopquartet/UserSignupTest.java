package com.doublea.barbershopquartet;

import android.support.test.espresso.contrib.PickerActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

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

public class UserSignupTest {
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
    public void selectBarber1() {
        // Launch Main Activity
        // click the customer button
        delay(3000);
        onView(withId(R.id.customer)).perform(click());
        //wait
        delay(3000);
        //Click the spinner
        onView(withId(R.id.spinner)).perform(click());
        delay(3000);
        //Click the 3rd option in the drop down
        onData(anything()).atPosition(2).perform(click());
        //Check it matches
        onView(withId(R.id.spinner)).check(matches(withSpinnerText(containsString("Drew Stewart"))));
        delay(3000);
        //Click Next
        onView(withId(R.id.next_button)).perform(click());
        delay(3000);
        //Select the current date
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)));
        // Press Okay
        onView(ViewMatchers.withText("OK")).perform(click());
        // Select time slot
        onView(withId(77)).perform(click());
        onData(anything()).atPosition(8).perform(click());
        delay(2000);
        onView(ViewMatchers.withText("SUBMIT")).perform(click());
//        delay(2000);
        onView(withId(R.id.edit_text_first_name)).perform( replaceText("John"),closeSoftKeyboard());
        onView(withId(R.id.edit_text_last_name)).perform(clearText(),replaceText("Doe"),closeSoftKeyboard());
        onView(withId(R.id.edit_text_phone_number)).perform(clearText(),replaceText("555-757-9999"),closeSoftKeyboard());
        onView(withId(R.id.edit_text_email)).perform(clearText(),replaceText("JohnDoe@gmail.com"),closeSoftKeyboard());
        onView(withId(R.id.edit_text_notes)).perform(clearText(),replaceText("Mohawk please"),closeSoftKeyboard());
        onView(withId(R.id.button_reserve_appointment)).perform(click());
        delay(1000);

    }

    @Test
    public void selectBarber2() {
        // Launch Main Activity
        // click the customer button
        delay(3000);
        onView(withId(R.id.customer)).perform(click());
        //wait
        delay(3000);
        //Click the spinner
        onView(withId(R.id.spinner)).perform(click());
        delay(3000);
        //Click the 3rd option in the drop down
        onData(anything()).atPosition(0).perform(click());
        delay(3000);
        //Check it matches
        onView(withId(R.id.spinner)).check(matches(withSpinnerText(containsString("Ahmed Ali"))));
        delay(3000);
        //Click Next
        onView(withId(R.id.next_button)).perform(click());
        delay(3000);
    }



}

