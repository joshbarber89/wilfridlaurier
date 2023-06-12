package com.example.androidassignment;


import androidx.test.espresso.DataInteraction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.matcher.BoundedMatcher;
import androidx.test.filters.LargeTest;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import static androidx.test.InstrumentationRegistry.getInstrumentation;
import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.assertion.ViewAssertions.*;
import static androidx.test.espresso.matcher.ViewMatchers.*;

import com.example.androidassignment.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class ChatWindowActivityTest {

    @Rule
    public ActivityScenarioRule<ChatWindow> mActivityScenarioRule =
            new ActivityScenarioRule<>(ChatWindow.class);

    @Test
    public void checkAllItems() {
        onView(withId(R.id.activityChatWindowTextMessageEditText)).check(matches(isDisplayed()));

        onView(withId(R.id.activityChatWindowSendButton)).check(matches(isDisplayed()));
        onView(withId(R.id.activityChatWindowSendButton)).check(matches(withText("Send")));

        onView(withId(R.id.activityChatWindowListView)).check(matches(isDisplayed()));
    }

    @Test
    public void chatWindowActivityTest() {
        onView(withId(R.id.activityChatWindowTextMessageEditText)).perform(typeText("test"));
        onView(withId(R.id.activityChatWindowSendButton)).perform(click());

        onView(withId(R.id.activityChatWindowTextMessageEditText)).perform(typeText("test2"));
        onView(withId(R.id.activityChatWindowSendButton)).perform(click());

        onData(new BoundedMatcher<Object, String>(String.class) {
            @Override
            public void describeTo(Description description) {
                description.appendText("Matching to String");
            }

            @Override
            protected boolean matchesSafely(String listItem) {
                return listItem.contains("test2");
            } }).inAdapterView(withId(R.id.activityChatWindowListView)).check(matches(isDisplayed()));
    }
}
