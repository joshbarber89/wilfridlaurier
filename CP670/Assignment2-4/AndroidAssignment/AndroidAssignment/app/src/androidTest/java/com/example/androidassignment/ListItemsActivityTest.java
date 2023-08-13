package com.example.androidassignment;


import androidx.test.espresso.DataInteraction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.espresso.matcher.BoundedMatcher;
import androidx.test.filters.LargeTest;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageButton;

import static androidx.test.InstrumentationRegistry.getInstrumentation;
import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.assertion.ViewAssertions.*;
import static androidx.test.espresso.intent.Intents.intending;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.*;

import com.example.androidassignment.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;

import java.util.List;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class ListItemsActivityTest {

    Instrumentation.ActivityMonitor checkboxActivityMonitor = InstrumentationRegistry.getInstrumentation()
            .addMonitor(MainActivity.class.getName(), null, false);

    @Rule
    public ActivityScenarioRule<ListItemsActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(ListItemsActivity.class);


    @Before
    public void before() {
        Intents.init();
    }

    @Test
    public void checkAllItemsThere() {

        onView(withId(R.id.activityListItemsRadioButton)).check(matches(isDisplayed()));
        onView(withId(R.id.activityListItemsRadioButton)).check(matches(withText("RadioButton")));

        onView(withId(R.id.activityListItemsCheckbox)).check(matches(isDisplayed()));

        onView(withId(R.id.activityListItemsSwitcher)).check(matches(isDisplayed()));
        onView(withId(R.id.activityListItemsSwitcher)).check(matches(withText("What does this do?")));

        onView(withId(R.id.activityListItemsImageButton)).check(matches(isDisplayed()));
    }

    @Test
    public void testImageButton() {
        Intent resultData = new Intent();

        resultData.putExtra("data", BitmapFactory.decodeResource(InstrumentationRegistry.getInstrumentation().getTargetContext().getResources(), R.mipmap.ic_launcher));

        Instrumentation.ActivityResult result = new Instrumentation.ActivityResult(Activity.RESULT_OK, resultData);

        intending(hasAction(MediaStore.ACTION_IMAGE_CAPTURE)).respondWith(result);

        onView(withId(R.id.activityListItemsImageButton)).perform(click());
    }

    @Test
    public void testSwitchButton() {
        onView(withId(R.id.activityListItemsSwitcher)).perform(click());
    }
    @Test
    public void testCheckboxButton() {
        onView(withId(R.id.activityListItemsCheckbox)).perform(click());
        onView(withId(android.R.id.button1)).perform(click());
    }

    @After
    public void after() {
        Intents.release();
    }
}
