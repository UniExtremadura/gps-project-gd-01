package es.unex.giiis.marvelbook

import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class SearchActivityTest {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(LoginActivity::class.java)

    @Test
    fun searchActivityTest() {
        val appCompatTextView = onView(
            withId(R.id.crearCuenta)
        )
        appCompatTextView.perform(click())

        val appCompatEditText = onView(
            withId(R.id.nombreRegistro)
        )
        appCompatEditText.perform(replaceText("ivan"), closeSoftKeyboard())

        val appCompatEditText2 = onView(
            withId(R.id.emailRegistro)
        )
        appCompatEditText2.perform(replaceText("ivan@gmail.com"), closeSoftKeyboard())

        val appCompatEditText3 = onView(
            withId(R.id.passwordRegistro)
        )
        appCompatEditText3.perform(replaceText("ivan12"), closeSoftKeyboard())

        val appCompatEditText4 = onView(
            withId(R.id.passwordRegistro2)
        )
        appCompatEditText4.perform(replaceText("ivan12"), closeSoftKeyboard())

        val appCompatButton = onView(
            withId(R.id.botonRegistro)
        )
        appCompatButton.perform(click())

        Thread.sleep(20000)
        val appCompatImageView = onView(
            allOf(
                withId(androidx.appcompat.R.id.search_button), withContentDescription("Search"),
                childAtPosition(
                    allOf(
                        withId(androidx.appcompat.R.id.search_bar),
                        childAtPosition(
                            withId(R.id.action_search),
                            0
                        )
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        appCompatImageView.perform(click())

        val searchAutoComplete3 = onView(
            allOf(
                withId(androidx.appcompat.R.id.search_src_text),
                childAtPosition(
                    allOf(
                        withId(androidx.appcompat.R.id.search_plate),
                        childAtPosition(
                            withId(androidx.appcompat.R.id.search_edit_frame),
                            1
                        )
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        searchAutoComplete3.perform(click())

        val searchAutoComplete4 = onView(
            allOf(
                withId(androidx.appcompat.R.id.search_src_text),
                childAtPosition(
                    allOf(
                        withId(androidx.appcompat.R.id.search_plate),
                        childAtPosition(
                            withId(androidx.appcompat.R.id.search_edit_frame),
                            1
                        )
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        searchAutoComplete4.perform(replaceText("3-d"))

        val searchAutoComplete5 = onView(
            allOf(
                withId(androidx.appcompat.R.id.search_src_text), withText("3-d"),
                childAtPosition(
                    allOf(
                        withId(androidx.appcompat.R.id.search_plate),
                        childAtPosition(
                            withId(androidx.appcompat.R.id.search_edit_frame),
                            1
                        )
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        searchAutoComplete5.perform(closeSoftKeyboard())

        val textView3 = onView(
            allOf(
                withId(R.id.nombrePersonajeLista), withText("3-D Man"),
                withParent(
                    allOf(
                        withId(R.id.cl_item_lista_coleccion),
                        withParent(withId(R.id.cl_item_lista))
                    )
                ),
                isDisplayed()
            )
        )
        textView3.check(matches(withText("3-D Man")))

    }


    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}
