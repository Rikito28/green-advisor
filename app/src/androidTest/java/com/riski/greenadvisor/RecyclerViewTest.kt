package com.riski.greenadvisor

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

// Black Box Testing
@RunWith(AndroidJUnit4::class)
class RecyclerViewTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun testRecyclerViewItemClick() {

        Thread.sleep(9000)
        // Melakukan klik pada item di posisi tertentu dalam RecyclerView
        onView(withId(R.id.home_recycler_any_plant))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        Thread.sleep(9000)
        // Verifikasi tindakan setelah mengklik item RecyclerView
        onView(withId(R.id.habitat_text))
            .check(matches(isDisplayed()))
    }
}
