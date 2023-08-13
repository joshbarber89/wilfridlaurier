package com.example.androidassignment;


import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.assertion.ViewAssertions.*;
import static androidx.test.espresso.matcher.ViewMatchers.*;

import androidx.lifecycle.Lifecycle;
import androidx.test.InstrumentationRegistry;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.matcher.BoundedMatcher;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.hamcrest.Description;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class ChatWindowActivityTest {

    @Rule
    public ActivityScenarioRule<ChatWindow> mActivityScenarioRule =
            new ActivityScenarioRule<>(ChatWindow.class);

    @BeforeClass
    public static void beforeClass() {
        InstrumentationRegistry.getTargetContext().deleteDatabase("Messages.db");
    }

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

        onView(withId(R.id.activityChatWindowTextMessageEditText)).perform(typeText("test2complete"));
        onView(withId(R.id.activityChatWindowSendButton)).perform(click());

        onData(new BoundedMatcher<Object, String>(String.class) {
            @Override
            public void describeTo(Description description) {
                description.appendText("Matching to String");
            }

            @Override
            protected boolean matchesSafely(String listItem) {
                return listItem.contains("test2complete");
            } }).inAdapterView(withId(R.id.activityChatWindowListView)).check(matches(isDisplayed()));
    }
    @Test
    public void chatWindowSQLActivityTest() {
        onView(withId(R.id.activityChatWindowTextMessageEditText)).perform(typeText("test"));
        onView(withId(R.id.activityChatWindowSendButton)).perform(click());

        onView(withId(R.id.activityChatWindowTextMessageEditText)).perform(typeText("test3complete"));
        onView(withId(R.id.activityChatWindowSendButton)).perform(click());

        ActivityScenario scenario = mActivityScenarioRule.getScenario();
        scenario.moveToState(Lifecycle.State.RESUMED);
        scenario.recreate();

        onData(new BoundedMatcher<Object, String>(String.class) {
            @Override
            public void describeTo(Description description) {
                description.appendText("Matching to String");
            }

            @Override
            protected boolean matchesSafely(String listItem) {
                return listItem.contains("test3complete");
            }
        }).inAdapterView(withId(R.id.activityChatWindowListView)).check(matches(isDisplayed()));
    }
}
