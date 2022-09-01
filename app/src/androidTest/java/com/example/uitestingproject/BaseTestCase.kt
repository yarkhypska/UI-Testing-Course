package com.example.uitestingproject

import android.app.Instrumentation
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.UiDevice
import com.example.uitestingproject.TestHelper.clearAppFromRecent
import com.example.uitestingproject.TestHelper.waitForObjectToAppear
import com.example.uitestingproject.TestHelper.waitForObjectToDisappear
import org.junit.After
import org.junit.Before

open class BaseTestCase {

    protected lateinit var device: UiDevice
    protected lateinit var instrumentation: Instrumentation

    @Before
    fun setup() {
        instrumentation = InstrumentationRegistry.getInstrumentation()
        device = UiDevice.getInstance(instrumentation)
        onSetup()
    }

    open fun onSetup() {}

    @After
    fun tearDown() {
        device.clearAppFromRecent()
        onTearDown()
    }

    open fun onTearDown() {}

    fun waitForTopAlertToAppear() {
        device.waitForObjectToAppear(R.string.fragment_main_top_alert_offline_mode)
    }

    fun waitForTopAlertToDisappear() {
        device.waitForObjectToDisappear(R.string.fragment_main_top_alert_offline_mode)
    }
}