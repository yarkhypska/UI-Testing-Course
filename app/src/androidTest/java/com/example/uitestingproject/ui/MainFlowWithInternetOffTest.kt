package com.example.uitestingproject.ui

import android.content.Context
import android.content.Intent
import android.net.wifi.WifiManager
import android.widget.Switch
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.uiautomator.UiSelector
import com.example.uitestingproject.BaseTestCase
import com.example.uitestingproject.R
import com.example.uitestingproject.TestHelper
import com.example.uitestingproject.TestHelper.clearAppFromRecent
import com.example.uitestingproject.ui.main_screen.UserAdapter
import org.hamcrest.Matchers.containsString
import org.junit.Test

class MainFlowWithInternetOffTest: BaseTestCase() {

    @Test
    fun runAppFromLauncher() {
        //device.clearAppFromRecent()
        /*val context = ApplicationProvider.getApplicationContext<Context>()

        //start wifi settings
        val wifiSettingsActivity = Intent(WifiManager.ACTION_PICK_WIFI_NETWORK).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        }
        context.startActivity(wifiSettingsActivity)

        //disable wifi
        val wifiSwitchSelector = UiSelector().className(Switch::class.java.name)
        TestHelper.switchSelector(device.findObject(wifiSwitchSelector), false)

        //start mobile data settings
        val mobileDataSettingsIntent = Intent(android.provider.Settings.ACTION_DATA_ROAMING_SETTINGS).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        }
        context.startActivity(mobileDataSettingsIntent)

        //disable mobile data
        val mobileSwitchSelector = UiSelector().className(Switch::class.java.name)
        TestHelper.switchSelector(device.findObject(mobileSwitchSelector), false)

        //start the app
        val packageName = ApplicationProvider.getApplicationContext<Context>().packageName
        val appIntent = context.packageManager.getLaunchIntentForPackage(packageName)
        context.startActivity(appIntent)
        TestHelper.waitForAppToStart(device, packageName)

        waitForTopAlertToAppear()

        device.setOrientationRight()
        device.waitForIdle()

        Espresso.onView(ViewMatchers.withId(R.id.rv))
            .perform(RecyclerViewActions.scrollToPosition<UserAdapter.UserViewHolder>(20))

        device.setOrientationLeft()
        device.waitForIdle()

        Espresso.onView(ViewMatchers.withId(R.id.rv))
            .perform(RecyclerViewActions.scrollToPosition<UserAdapter.UserViewHolder>(50))

        Espresso.onView(ViewMatchers.withId(R.id.rv)).perform(
            RecyclerViewActions.actionOnItemAtPosition<UserAdapter.UserViewHolder>(50,
                ViewActions.click()
            )
        )
        device.waitForIdle()

        Espresso.onView(ViewMatchers.withId(R.id.container_detailed_info))
            .check(doesNotExist())

        Espresso.onView(ViewMatchers.withText(containsString("offline")))
            .check(matches(isDisplayed()))

        device.setOrientationRight()
        device.waitForIdle()

        Espresso.onView(ViewMatchers.withText(containsString("offline")))
            .check(matches(isDisplayed()))


        device.pressBack()
        device.setOrientationNatural()
        device.waitForIdle()

        Espresso.onView(ViewMatchers.withId(R.id.alert_offline_mode)).check(
            matches(isDisplayed())
        )*/
    }
}