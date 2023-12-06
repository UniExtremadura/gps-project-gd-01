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
class SobreActivityTest {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(LoginActivity::class.java)

    @Test
    fun sobreActivityTest() {
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
            withId(R.id.emailRegistro)
        )
        appCompatEditText3.perform(click())

        val appCompatEditText4 = onView(
            withId(R.id.passwordRegistro)
        )
        appCompatEditText4.perform(replaceText("ivan12"), closeSoftKeyboard())

        val appCompatEditText5 = onView(
            withId(R.id.passwordRegistro2)
        )
        appCompatEditText5.perform(replaceText("ivan12"), closeSoftKeyboard())

        val appCompatButton = onView(
            withId(R.id.botonRegistro)
        )
        appCompatButton.perform(click())

        Thread.sleep(10000)
        val bottomNavigationItemView = onView(
            allOf(
                withId(R.id.navigation_tienda), withContentDescription("Tienda"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.nav_view),
                        0
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        bottomNavigationItemView.perform(click())

        val textView = onView(
            withId(R.id.coinAmmountUsuario)
        )
        textView.check(matches(withText("100")))

        val textView2 = onView(
            withId(R.id.coinAmmount1)
        )
        textView2.check(matches(withText("10")))

        val textView3 = onView(
            withId(R.id.coinAmmount2)
        )
        textView3.check(matches(withText("20")))

        val textView4 = onView(
            withId(R.id.tiendaTitulo)
        )
        textView4.check(matches(withText("SOBRES DISPONIBLES")))

        val appCompatImageView = onView(
            withId(R.id.SobreBasico)
        )
        appCompatImageView.perform(click())

        val viewGroup = onView(
            allOf(
                withId(R.id.cl_item_sobre),
                withParent(
                    allOf(
                        withId(R.id.cl_items_sobre_rv),
                        withParent(withId(R.id.listadoSobre))
                    )
                ),
                isDisplayed()
            )
        )
        viewGroup.check(matches(isDisplayed()))

        val appCompatButton2 = onView(
            withId(R.id.bGuardarMazo)
        )
        appCompatButton2.perform(click())


        val textView5 = onView(
            withId(R.id.coinAmmountUsuario)
        )
        textView5.check(matches(withText("90")))

        val appCompatImageView2 = onView(
            withId(R.id.SobreEspecial)
        )
        appCompatImageView2.perform(click())


        val appCompatButton3 = onView(
            withId(R.id.bGuardarMazo)
        )
        appCompatButton3.perform(click())


        val textView6 = onView(
            withId(R.id.coinAmmountUsuario)
        )
        textView6.check(matches(withText("70")))

        val bottomNavigationItemView2 = onView(
            allOf(
                withId(R.id.navigation_mazo), withContentDescription("Mazo"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.nav_view),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        bottomNavigationItemView2.perform(click())


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
