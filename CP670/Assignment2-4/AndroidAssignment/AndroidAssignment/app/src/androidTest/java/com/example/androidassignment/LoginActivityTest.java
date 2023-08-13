package com.example.androidassignment;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.hamcrest.Matchers.allOf;
import static org.junit.Assert.assertNotNull;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import androidx.test.InstrumentationRegistry;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class LoginActivityTest {

    Instrumentation.ActivityMonitor MainActivityActivityMonitor = getInstrumentation()
            .addMonitor(MainActivity.class.getName(), null, false);

    @Rule
    public ActivityScenarioRule<LoginActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(LoginActivity.class);

    @Rule
    public ActivityTestRule<LoginActivity> activityTestRule =
            new ActivityTestRule<LoginActivity>(LoginActivity.class) {
                @Override
                protected void beforeActivityLaunched() {
                    clearSharedPrefs(InstrumentationRegistry.getTargetContext());
                    super.beforeActivityLaunched();
                }
            };
    private void clearSharedPrefs(Context context) {
        SharedPreferences prefs =
                context.getSharedPreferences("preferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.commit();
    }

    @Test
    public void checkAllItemsThere() {
        onView(withId(R.id.activityLoginTextViewLogin)).check(matches(isDisplayed()));
        onView(withId(R.id.activityLoginTextViewLogin)).check(matches(withText("Login")));

        onView(withId(R.id.activityLoginEditTextLogin)).check(matches(isDisplayed()));
        onView(withId(R.id.activityLoginEditTextPassword)).check(matches(isDisplayed()));

        onView(withId(R.id.activityLoginTextViewPassword)).check(matches(isDisplayed()));
        onView(withId(R.id.activityLoginTextViewPassword)).check(matches(withText("password")));
    }

    @Test
    public void loginActivityTest() {
        onView(withId(R.id.activityLoginEditTextLogin)).perform(typeText("test"), closeSoftKeyboard());
        onView(withId(R.id.activityLoginEditTextPassword)).perform(typeText("test"), closeSoftKeyboard());
        onView(withId(R.id.activityLoginButtonLogin)).perform(click());

        Activity mainActivity = getInstrumentation()
                .waitForMonitorWithTimeout(MainActivityActivityMonitor, 5000);
        assertNotNull(mainActivity);
        Espresso.pressBack();
        onView(withId(R.id.activityLoginEditTextLogin)).check(matches(withText("test")));
    }
}
