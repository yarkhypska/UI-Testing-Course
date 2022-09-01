package com.example.uitestingproject

import android.content.Context
import android.content.Intent
import android.net.wifi.WifiManager
import android.widget.Switch
import androidx.test.core.app.ApplicationProvider
import androidx.test.uiautomator.*

object TestHelper {

    fun switchSelector(switchObject: UiObject, enable: Boolean, timeout: Long = 5 * 1000L) {
        if (switchObject.waitForExists(timeout) && switchObject.isEnabled) {
            if (switchObject.isChecked != enable) {
                switchObject.click()
            }
        }
    }

    fun waitForAppToStart(device: UiDevice, packageName: String) {
        device.wait(
            Until.hasObject(By.pkg(packageName).depth(0)),
            5 * 1000
        )
    }

    fun UiDevice.getObjectWithText(text: String) = findObject(UiSelector().text(text))

    fun UiDevice.clearAppFromRecent(swipeAttempts: Int = 5) {
        setOrientationNatural()
        pressRecentApps()

        if (getObjectWithText("No recent items").exists()) {
            pressBack()
            return
        }
        waitForIdle()

        var currentAttempt = 1
        val centerScreenX = displayWidth / 2
        val centerScreenY = displayHeight / 2

        fun findAndTapClearButton(startX: Int, startY: Int, endX: Int, endY: Int, ): Boolean {
            while (currentAttempt <= swipeAttempts) {
                swipe(startX, startY, endX, endY, 10)
                waitForIdle()
                val uiObject = getObjectWithText("CLEAR ALL").let {
                    if (!it.exists()) getObjectWithText("Clear All") else it
                }.let {
                    if (!it.exists()) getObjectWithText("Clear all") else it
                }.let {
                    if (!it.exists()) getObjectWithText("Clear") else it
                }
                if (uiObject.exists()) {
                    uiObject.click()
                    waitForIdle()
                    return true
                } else {
                    currentAttempt++
                }
            }
            waitForIdle()
            return false
        }

        if (findAndTapClearButton(centerScreenX, (displayHeight*0.4).toInt(), centerScreenX, (displayHeight*0.6).toInt())) {
            waitForIdle()
            return
        }

        currentAttempt = 1
        findAndTapClearButton((displayWidth*0.4).toInt(), centerScreenY, (displayWidth*0.6).toInt(), centerScreenY)
    }

    fun UiDevice.enableWifi() {
        val context = ApplicationProvider.getApplicationContext<Context>()

        //start wifi settings
        val wifiSettingsActivity = Intent(WifiManager.ACTION_PICK_WIFI_NETWORK).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        }
        context.startActivity(wifiSettingsActivity)

        //disable wifi
        val wifiSwitchSelector = UiSelector().className(Switch::class.java.name)
        switchSelector(findObject(wifiSwitchSelector), false)
        waitForIdle()
    }

    fun UiDevice.waitForObjectToAppear(stringRes: Int) {
        val context = ApplicationProvider.getApplicationContext<Context>()
        wait(
            Until.hasObject(By.textContains(context.getString(stringRes))),
            10*1000
        )
    }

    fun UiDevice.waitForObjectToDisappear(stringRes: Int) {
        val context = ApplicationProvider.getApplicationContext<Context>()
        wait(
            Until.gone(By.textContains(context.getString(stringRes))),
            10*1000
        )
    }
}