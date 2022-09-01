package com.example.uitestingproject.ui.main_screen

import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.UiDevice
import com.example.uitestingproject.BaseTestCase
import com.example.uitestingproject.R
import com.example.uitestingproject.TestHelper.enableWifi
import com.example.uitestingproject.ui.MainActivity
import junit.framework.Assert.assertEquals
import org.junit.*
import org.junit.runner.RunWith
import kotlin.random.Random


@RunWith(AndroidJUnit4::class)
class MainFragmentTest: BaseTestClass() {

    @get:Rule
    var activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    private lateinit var fragmentScenario: FragmentScenario<MainFragment>
    private lateinit var navController: TestNavHostController

    @Before
    fun setup() {
        Intents.init()
        launchMainFragment()
    }

    @After
    fun tearDown() {
        Intents.release()
        fragmentScenario.close()
    }

    @Test
    fun onTapOnUserItemShowDialogScreen() {
        onView(withId(R.id.rv))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<UserAdapter.UserViewHolder>(
                    0, click()
                )
            )

        assertEquals(navController.currentDestination?.id, R.id.detailedUserInfoDialog)
    }

    @Test
    fun monkey_scrollDownAndUpManyTimes() {
        fun getNextPosition() = Random.nextInt(0, 200)
        onView(withId(R.id.rv))
            .apply {
                for (i in 0..20) {
                    perform(
                        RecyclerViewActions.scrollToPosition<UserAdapter.UserViewHolder>(
                            getNextPosition()
                        )
                    )
                }
            }
    }

    private fun launchMainFragment() {
        navController = TestNavHostController(ApplicationProvider.getApplicationContext())

        fragmentScenario = launchFragmentInContainer(themeResId = R.style.Theme_UITestingProject)
        fragmentScenario.onFragment { fragment ->
            navController.setGraph(R.navigation.nav_graph)

            // Make the NavController available via the findNavController() APIs
            Navigation.setViewNavController(fragment.requireView(), navController)
        }
    }
}