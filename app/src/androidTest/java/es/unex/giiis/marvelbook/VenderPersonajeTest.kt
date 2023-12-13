package es.unex.giiis.marvelbook


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
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
class VenderPersonajeTest {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(LoginActivity::class.java)

    @Test
    fun cU15ESPRESSOTest() {
        val appCompatTextView = onView(
            allOf(
                withId(R.id.crearCuenta), withText("¿No tienes cuenta? ¡Regístrate!"),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0
                    ),
                    5
                ),
                isDisplayed()
            )
        )
        appCompatTextView.perform(click())

        val appCompatEditText = onView(
            allOf(
                withId(R.id.nombreRegistro),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0
                    ),
                    3
                ),
                isDisplayed()
            )
        )
        appCompatEditText.perform(replaceText("Diego"), closeSoftKeyboard())

        val appCompatEditText2 = onView(
            allOf(
                withId(R.id.emailRegistro),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0
                    ),
                    4
                ),
                isDisplayed()
            )
        )
        appCompatEditText2.perform(replaceText("diego@gmail.com"), closeSoftKeyboard())

        val appCompatEditText3 = onView(
            allOf(
                withId(R.id.passwordRegistro),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0
                    ),
                    5
                ),
                isDisplayed()
            )
        )
        appCompatEditText3.perform(replaceText("Diego2001"), closeSoftKeyboard())

        val appCompatEditText4 = onView(
            allOf(
                withId(R.id.passwordRegistro), withText("Diego2001"),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0
                    ),
                    5
                ),
                isDisplayed()
            )
        )
        appCompatEditText4.perform(click())

        val appCompatEditText5 = onView(
            allOf(
                withId(R.id.passwordRegistro2),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0
                    ),
                    6
                ),
                isDisplayed()
            )
        )
        appCompatEditText5.perform(replaceText("Diego2001"), closeSoftKeyboard())

        val appCompatButton = onView(
            allOf(
                withId(R.id.botonRegistro), withText("¡REGISTRATE!"),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0
                    ),
                    7
                ),
                isDisplayed()
            )
        )
        appCompatButton.perform(click())

        Thread.sleep(20000)

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
            allOf(
                withId(R.id.coinAmmountUsuario), withText("100"),
                withParent(withParent(withId(R.id.nav_host_fragment_activity_main))),
                isDisplayed()
            )
        )
        textView.check(matches(withText("100")))

        val appCompatImageView = onView(
            allOf(
                withId(R.id.SobreBasico), withContentDescription("10"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.nav_host_fragment_activity_main),
                        0
                    ),
                    8
                ),
                isDisplayed()
            )
        )
        appCompatImageView.perform(click())

        val appCompatButton2 = onView(
            allOf(
                withId(R.id.bGuardarMazo), withText("¡CONTINUAR...!"),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        appCompatButton2.perform(click())

        pressBack()

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

        val viewGroup = onView(
            allOf(
                withId(R.id.cl_item_sobre),
                withParent(
                    allOf(
                        withId(R.id.cl_items_sobre_rv),
                        withParent(withId(R.id.listaMazo))
                    )
                ),
                isDisplayed()
            )
        )
        viewGroup.check(matches(isDisplayed()))

        val constraintLayout = onView(
            allOf(
                withId(R.id.cl_item_sobre),
                childAtPosition(
                    allOf(
                        withId(R.id.cl_items_sobre_rv),
                        childAtPosition(
                            withId(R.id.listaMazo),
                            0
                        )
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        constraintLayout.perform(click())

        val appCompatButton3 = onView(
            allOf(
                withId(R.id.botonVender), withText("¡Vender personaje!"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.nav_host_fragment_activity_main),
                        0
                    ),
                    12
                ),
                isDisplayed()
            )
        )
        appCompatButton3.perform(click())

        val bottomNavigationItemView3 = onView(
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
        bottomNavigationItemView3.perform(click())

        val textView2 = onView(
            allOf(
                withId(R.id.coinAmmountUsuario), withText("94"),
                withParent(withParent(withId(R.id.nav_host_fragment_activity_main))),
                isDisplayed()
            )
        )
        textView2.check(matches(withText("94")))
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
