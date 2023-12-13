package es.unex.giiis.marvelbook

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.hamcrest.Matchers.allOf
import org.hamcrest.core.IsInstanceOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class RegisterActivityTest {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(LoginActivity::class.java)

    @Test
    fun registerActivityTest() {
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

        val editText = onView(
            withId(R.id.nombreRegistro)
        )
        editText.check(matches(withText("ivan")))

        val editText2 = onView(
            withId(R.id.emailRegistro)
        )
        editText2.check(matches(withText("ivan@gmail.com")))


        val appCompatButton = onView(
            withId(R.id.botonRegistro)
        )
        appCompatButton.perform(click())

        val frameLayout = onView(
                withId(R.id.nav_view)
        )
        frameLayout.check(matches(isDisplayed()))

        val imageView = onView(
            allOf(
                withId(com.google.android.material.R.id.navigation_bar_item_icon_view),
                withParent(
                    allOf(
                        withId(com.google.android.material.R.id.navigation_bar_item_icon_container),
                        withParent(
                            allOf(
                                withId(R.id.navigation_coleccion),
                                withContentDescription("Colección")
                            )
                        )
                    )
                ),
                isDisplayed()
            )
        )
        imageView.check(matches(isDisplayed()))

        val textView = onView(
            allOf(
                withId(com.google.android.material.R.id.navigation_bar_item_large_label_view),
                withText("Colección"),
                withParent(
                    allOf(
                        withId(com.google.android.material.R.id.navigation_bar_item_labels_group),
                        withParent(
                            allOf(
                                withId(R.id.navigation_coleccion),
                                withContentDescription("Colección")
                            )
                        )
                    )
                ),
                isDisplayed()
            )
        )
        textView.check(matches(withText("Colección")))

        val linearLayout = onView(
            withId(R.id.tabLayout)
        )
        linearLayout.check(matches(isDisplayed()))

        val textView2 = onView(
            allOf(
                withText("PERSONAJES"),
                withParent(
                    allOf(
                        withContentDescription("PERSONAJES"),
                        withParent(IsInstanceOf.instanceOf(android.widget.LinearLayout::class.java))
                    )
                ),
                isDisplayed()
            )
        )
        textView2.check(matches(withText("PERSONAJES")))
    }

}
