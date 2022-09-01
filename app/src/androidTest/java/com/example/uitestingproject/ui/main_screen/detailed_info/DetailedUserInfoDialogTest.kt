package com.example.uitestingproject.ui.main_screen.detailed_info

import android.content.Intent
import androidx.core.os.bundleOf
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragment
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasAction
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import androidx.test.uiautomator.UiDevice
import com.example.uitestingproject.BaseTestCase
import com.example.uitestingproject.R
import com.example.uitestingproject.TestHelper.enableWifi
import com.example.uitestingproject.ui.MainActivity
import com.example.uitestingproject.ui.main_screen.BaseTestClass
import org.junit.*
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class DetailedUserInfoDialogTest: BaseTestClass() {
    @get:Rule
    var activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    private lateinit var fragmentScenario: FragmentScenario<DetailedUserInfoDialog>
    private lateinit var currentDevice: UiDevice

    @Before
    fun setup(){
        Intents.init()
        currentDevice = UiDevice.getInstance(getInstrumentation())
        launchDetailedUserInfoDialog()
    }

    @After
    fun tearDown() {
        Intents.release()
        fragmentScenario.close()
    }

    @Test
    fun onCloseClickDismissDialog() {
        onView(withId(R.id.btn_close)).perform(click())
        onView(withId(R.id.container_detailed_info)).check(doesNotExist())
    }

    @Test
    fun changeOrientationAndPressGitHubButton() {
        currentDevice.setOrientationLeft()

        onView(withId(R.id.btn_go_to_git_hub)).perform(click())
        intended(hasAction(Intent.ACTION_VIEW))

        currentDevice.setOrientationRight()
        currentDevice.pressBack()

        onView(withId(R.id.btn_go_to_git_hub)).check(matches(isDisplayed()))
    }

    @Test
    fun onGitHubBtnClickLaunchBrowser() {
        onView(withId(R.id.btn_go_to_git_hub)).perform(click())
        intended(hasAction(Intent.ACTION_VIEW))
    }

    private fun launchDetailedUserInfoDialog(userLogin: String = "mojombo") {
        val fragmentArgs = bundleOf(DetailedUserInfoDialog.ARG_USER_NAME to userLogin)
        fragmentScenario = launchFragment(fragmentArgs, themeResId = R.style.Theme_UITestingProject)
    }
}