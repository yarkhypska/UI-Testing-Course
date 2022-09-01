package com.example.uitestingproject.ui

import android.content.Context
import android.content.Intent
import android.net.wifi.WifiManager
import android.widget.Switch
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.uiautomator.UiSelector
import com.example.uitestingproject.BaseTestCase
import com.example.uitestingproject.R
import com.example.uitestingproject.TestHelper
import com.example.uitestingproject.TestHelper.switchSelector
import com.example.uitestingproject.domain.user.UserInList
import com.example.uitestingproject.ui.main_screen.UserAdapter
import com.example.uitestingproject.ui.main_screen.UserAdapter.UserViewHolder
import org.junit.Test


class MainFlowWithInternetOnTest: BaseTestCase() {

    override fun onSetup() {
        Intents.init()
    }

    @Test
    fun runAppFromLauncher() {
        val context = getApplicationContext<Context>()

        //start wifi settings
        val wifiSettingsActivity = Intent(WifiManager.ACTION_PICK_WIFI_NETWORK).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        }
        context.startActivity(wifiSettingsActivity)

        //enable wifi
        val wifiSwitchSelector = UiSelector().className(Switch::class.java.name)
        switchSelector(device.findObject(wifiSwitchSelector), true)

        //start the app
        val packageName = getApplicationContext<Context>().packageName
        val appIntent = context.packageManager.getLaunchIntentForPackage(packageName)
        context.startActivity(appIntent)
        TestHelper.waitForAppToStart(device, packageName)

        waitForTopAlertToDisappear()

        device.setOrientationRight()
        device.waitForIdle()

        onView(withId(R.id.rv))
            .perform(RecyclerViewActions.scrollToPosition<UserAdapter.UserViewHolder>(20))
        onView(withId(R.id.rv)).perform(
            RecyclerViewActions.actionOnItemAtPosition<UserViewHolder>(20,click())
        )

        device.waitForIdle()

        onView(withId(R.id.container_detailed_info)).check(matches(isDisplayed()))

        device.setOrientationRight()
        device.waitForIdle()

        onView(withId(R.id.container_detailed_info)).check(matches(isDisplayed()))

        onView(withId(R.id.btn_go_to_git_hub)).perform(click())
        Intents.intended(IntentMatchers.hasAction(Intent.ACTION_VIEW))

        device.setOrientationLeft()
        device.waitForIdle()

        device.pressBack()
        device.waitForIdle()

        onView(withId(R.id.container_detailed_info)).check(matches(isDisplayed()))

        device.pressBack()
        device.waitForIdle()

        onView(withId(R.id.container_detailed_info)).check(doesNotExist())
        onView(withId(R.id.alert_offline_mode)).check(matches(
            ViewMatchers.withEffectiveVisibility(
                ViewMatchers.Visibility.GONE
            )
        ))

        device.setOrientationNatural()
        device.waitForIdle()
    }
}