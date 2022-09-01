package com.example.uitestingproject.ui

import android.content.Context
import android.content.Intent
import android.net.wifi.WifiManager
import android.widget.Switch
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiSelector
import androidx.test.uiautomator.Until
import com.example.uitestingproject.BaseTestCase
import com.example.uitestingproject.R
import com.example.uitestingproject.TestHelper.switchSelector
import com.example.uitestingproject.TestHelper.waitForAppToStart
import org.junit.Test


class MainScreenUnderDifferentInternetConnectionTest: BaseTestCase() {

    @Test
    fun testDifferentInternetConditions() {
        val context = ApplicationProvider.getApplicationContext<Context>()

        //start wifi settings
        val wifiSettingsActivity = Intent(WifiManager.ACTION_PICK_WIFI_NETWORK).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        }
        context.startActivity(wifiSettingsActivity)

        //disable wifi
        val wifiSwitchSelector = UiSelector().className(Switch::class.java.name)
        switchSelector(device.findObject(wifiSwitchSelector), false)

        //start mobile data settings
        val mobileDataSettingsIntent = Intent(android.provider.Settings.ACTION_DATA_ROAMING_SETTINGS).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        }
        context.startActivity(mobileDataSettingsIntent)

        //disable mobile data
        val mobileSwitchSelector = UiSelector().className(Switch::class.java.name)
        switchSelector(device.findObject(mobileSwitchSelector), false)

        //start the app
        val packageName = ApplicationProvider.getApplicationContext<Context>().packageName
        val appIntent = context.packageManager.getLaunchIntentForPackage(packageName)
        context.startActivity(appIntent)
        waitForAppToStart(device, packageName)

        //verify the you're offline alert present
        onView(withId(R.id.alert_offline_mode)).check(matches(isDisplayed()))

        //enable wifi
        context.startActivity(wifiSettingsActivity)
        switchSelector(device.findObject(wifiSwitchSelector), true)

        //go to the app
        context.startActivity(appIntent)
        waitForAppToStart(device, packageName)

        //verify offline mode alert is not presented
        waitForTopAlertToDisappear()
        onView(withId(R.id.alert_offline_mode)).check(matches(withEffectiveVisibility(Visibility.GONE)))

        //disable wifi
        context.startActivity(wifiSettingsActivity)
        switchSelector(device.findObject(wifiSwitchSelector), false)

        //go to the app
        context.startActivity(appIntent)
        waitForAppToStart(device, packageName)

        //verify offline mode alert present
        waitForTopAlertToAppear()
        onView(withId(R.id.alert_offline_mode)).check(matches(isDisplayed()))

        //enable mobile data
        context.startActivity(mobileDataSettingsIntent)
        switchSelector(device.findObject(mobileSwitchSelector), true)

        //go to the app
        context.startActivity(appIntent)
        waitForAppToStart(device, packageName)

        //verify offline mode alert is not presented
        waitForTopAlertToDisappear()
        onView(withId(R.id.alert_offline_mode)).check(matches(withEffectiveVisibility(Visibility.GONE)))
    }
}