package com.example.testuicourse

import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.runner.lifecycle.ActivityLifecycleMonitorRegistry
import androidx.test.runner.lifecycle.Stage
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class MainActivityAndroidTest {

    @get:Rule
    val mActivityRule = ActivityScenarioRule(
        MainActivity::class.java
    )

    @Test
    fun ensureTestChangesWork() {
        onView(withId(R.id.inputField))
            .perform(typeText("HELLO"), closeSoftKeyboard())

        onView(withId(R.id.changeText))
            .perform(click())

        onView(withId(R.id.inputField))
            .check(matches(withText("Lalala")))

        InstrumentationRegistry.getInstrumentation().runOnMainSync {
            ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(Stage.RESUMED).last()
        }
    }

}