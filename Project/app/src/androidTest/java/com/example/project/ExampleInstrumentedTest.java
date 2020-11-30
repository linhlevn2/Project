package com.example.project;

import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.FragmentTransaction;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

//import com.google.android.apps.common.testing.ui.espresso.Espresso;

import com.example.project.ui.gallery.GalleryFragment;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.project", appContext.getPackageName());
    }

    @Rule
    public ActivityTestRule mActivityRule = new ActivityTestRule<>(
            MainActivity.class);

    @Test
    public void activityLaunch() {
        onView(withId(R.id.button)).perform(click());
        onView(withId(R.id.textView6)).check(matches(isDisplayed()));
        onView(withId(R.id.button3)).perform(click());
        onView(withId(R.id.textView6)).check(matches(isDisplayed()));
    }

    @Test
    public void textInputOutput() {
        onView(withId(R.id.reviewTxt)).perform(typeText("This is a test review."));
        onView(withId(R.id.button)).perform(scrollTo()).perform(click());
        onView(withId(R.id.textView6)).check(matches(withText("Hotel#: 01\nReview: This is a test review.\n\n")));
    }

    @Test
    public void checkDatabaseRetrieval() {
        onView(withId(R.id.hotelTxt)).check(matches((isDisplayed())));
        onView(withId(R.id.textView7)).check(matches((isDisplayed())));
    }
}

