package com.example.androidassignment;

import static androidx.test.espresso.Espresso.*;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.assertion.ViewAssertions.*;
import static androidx.test.espresso.intent.Intents.intending;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.*;
import static androidx.test.platform.app.InstrumentationRegistry.*;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;

import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    Instrumentation.ActivityMonitor ListItemsActivityMonitor = getInstrumentation()
            .addMonitor(ListItemsActivity.class.getName(), null, false);

    Instrumentation.ActivityMonitor ChatWindowActivityMonitor = getInstrumentation()
            .addMonitor(ChatWindow.class.getName(), null, false);

    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule
            = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void checkAllItems() {
        onView(withId(R.id.activityMainTextViewHelloWorld)).check(matches(isDisplayed()));
        onView(withId(R.id.activityMainTextViewHelloWorld)).check(matches(withText("Hello World!")));

        onView(withId(R.id.activityMainStartChatButton)).check(matches(isDisplayed()));
        onView(withId(R.id.activityMainStartChatButton)).check(matches(withText("Start Chat")));

        onView(withId(R.id.activityMainButtonButton)).check(matches(isDisplayed()));
        onView(withId(R.id.activityMainButtonButton)).check(matches(withText("I'm a button")));
    }

    @Test
    public void onClickListItems() {
        // Click on Im a button
        onView(withId(R.id.activityMainButtonButton)).perform(click());
        Activity listItems = getInstrumentation()
                .waitForMonitorWithTimeout(ListItemsActivityMonitor, 5000);
        assertNotNull(listItems);

    }
    @Test
    public void onClickChatWindow() {
        // Click on Start Chat
        onView(withId(R.id.activityMainStartChatButton)).perform(click());
        Activity chatWindow = getInstrumentation()
                .waitForMonitorWithTimeout(ChatWindowActivityMonitor, 5000);
        assertNotNull(chatWindow);
    }
   @Test
    public void testStartActivityForResult() {
        Intents.init();
        Intent resultData = new Intent();
        resultData.putExtra("Response", "Here is my response");
        Instrumentation.ActivityResult result = new Instrumentation.ActivityResult(Activity.RESULT_OK, resultData);

        intending(hasComponent("com.example.androidassignment.ListItemsActivity")).respondWith(result);

        onView(withId(R.id.activityMainButtonButton)).perform(click());

       Intents.release();
    }
}
