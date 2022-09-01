package com.example.uitestingproject.ui.main_screen

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.UiDevice
import com.example.uitestingproject.TestHelper.enableWifi
import org.junit.BeforeClass

open class BaseTestClass {

    companion object {
        @BeforeClass
        fun enableInternet() {
            val  device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
            device.enableWifi()
        }
    }
}