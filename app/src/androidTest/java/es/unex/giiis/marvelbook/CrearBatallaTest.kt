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
class CrearBatallaTest {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(LoginActivity::class.java)

    @Test
    fun batallaActivityTest() {
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
        appCompatEditText3.perform(replaceText("Diego01"), closeSoftKeyboard())

        val appCompatEditText4 = onView(
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
        appCompatEditText4.perform(replaceText("Diego01"), closeSoftKeyboard())

        val editText = onView(
            allOf(
                withId(R.id.nombreRegistro), withText("Diego"),
                withParent(withParent(withId(android.R.id.content))),
                isDisplayed()
            )
        )
        editText.check(matches(withText("Diego")))

        val editText2 = onView(
            allOf(
                withId(R.id.emailRegistro), withText("diego@gmail.com"),
                withParent(withParent(withId(android.R.id.content))),
                isDisplayed()
            )
        )
        editText2.check(matches(withText("diego@gmail.com")))

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

        Thread.sleep(25000)


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

        val viewGroup2 = onView(
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
        viewGroup2.check(matches(isDisplayed()))

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
                withId(R.id.botonBatalla), withText("¡Batalla!"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.nav_host_fragment_activity_main),
                        0
                    ),
                    11
                ),
                isDisplayed()
            )
        )
        appCompatButton3.perform(click())

        val imageView = onView(
            allOf(
                withId(R.id.imagenPersonaje1), withContentDescription("player1"),
                withParent(withParent(withId(android.R.id.content))),
                isDisplayed()
            )
        )
        imageView.check(matches(isDisplayed()))

        val imageView2 = onView(
            allOf(
                withId(R.id.imagenPersonaje2), withContentDescription("player1"),
                withParent(withParent(withId(android.R.id.content))),
                isDisplayed()
            )
        )
        imageView2.check(matches(isDisplayed()))

        val appCompatImageView2 = onView(
            allOf(
                withId(R.id.imagenFight), withContentDescription("Logo de registro marvel"),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0
                    ),
                    14
                ),
                isDisplayed()
            )
        )
        appCompatImageView2.perform(click())

        val imageView3 = onView(
            allOf(
                withId(R.id.imagenPersonajeGanador), withContentDescription("Foto del personaje"),
                withParent(withParent(withId(android.R.id.content))),
                isDisplayed()
            )
        )
        imageView3.check(matches(isDisplayed()))

        val appCompatButton4 = onView(
            allOf(
                withId(R.id.botonMazo), withText("CONTINUAR"),
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
        appCompatButton4.perform(click())
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
