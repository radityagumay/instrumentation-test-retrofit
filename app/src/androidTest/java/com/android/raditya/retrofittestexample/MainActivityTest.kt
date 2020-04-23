package com.android.raditya.retrofittestexample

import android.content.Intent
import androidx.test.InstrumentationRegistry.getInstrumentation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.Visibility.GONE
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.android.raditya.retrofittestexample.R.id
import com.android.raditya.retrofittestexample.RestServiceTestHelper.getStringFromFile
import com.squareup.okhttp.mockwebserver.MockResponse
import com.squareup.okhttp.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class MainActivityTest {
    @get:Rule
    val mActivityRule = ActivityTestRule(MainActivity::class.java, true, false)
    private var server: MockWebServer? = null

    @Before
    @Throws(Exception::class)
    fun setUp() {
        server = MockWebServer()
        server!!.start()
        QuoteOfTheDayConstants.BASE_URL = server!!.url("/").toString()
    }

    @After
    @Throws(Exception::class)
    fun tearDown() {
        server!!.shutdown()
    }

    @Test
    @Throws(Exception::class)
    fun testQuoteIsShown() {
        val fileName = "quote_200_ok_response.json"
        server!!.enqueue(MockResponse()
            .setResponseCode(200)
            .setBody(getStringFromFile(
                getInstrumentation().context,
                fileName))
        )
        val intent = Intent()
        mActivityRule.launchActivity(intent)

        onView(withId(id.button_retry)).check(matches(withEffectiveVisibility(GONE)))
        onView(withText("I came from a real tough neighborhood. Once a guy pulled a knife on me. I knew he wasn't a professional, the knife had butter on it.")).check(matches(isDisplayed()))
    }
}