package es.unex.giiis.marvelbook

import androidx.test.filters.LargeTest
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import android.view.View
import android.view.ViewGroup

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.ViewMatchers.*

import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

import org.hamcrest.Matchers.allOf

@LargeTest
@RunWith(AndroidJUnit4::class)
class ModificarUsuarioTest {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(LoginActivity::class.java)

    @Test
    fun modificarUsuarioTest() {
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
        appCompatEditText.perform(replaceText("Sergio"), closeSoftKeyboard())

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
        appCompatEditText2.perform(replaceText("sergio@gmail.com"), closeSoftKeyboard())

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
        appCompatEditText3.perform(replaceText("Sergio01"), closeSoftKeyboard())

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
        appCompatEditText4.perform(replaceText("Sergio01"), closeSoftKeyboard())

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

        val button = onView(
            allOf(
                withId(R.id.action_settings), withContentDescription("Ajustes"),
                withParent(withParent(withId(R.id.toolbar))),
                isDisplayed()
            )
        )
        button.check(matches(isDisplayed()))

        val actionMenuItemView = onView(
            allOf(
                withId(R.id.action_settings), withContentDescription("Ajustes"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.toolbar),
                        1
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        actionMenuItemView.perform(click())

        val textView = onView(
            allOf(
                withId(androidx.core.R.id.title), withText("Cuenta"),
                withParent(withParent(withId(androidx.appcompat.R.id.content))),
                isDisplayed()
            )
        )
        textView.check(matches(withText("Cuenta")))

        val appCompatTextView2 = onView(
            allOf(
                withId(androidx.core.R.id.title), withText("Cuenta"),
                childAtPosition(
                    childAtPosition(
                        withId(androidx.appcompat.R.id.content),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        appCompatTextView2.perform(click())

        val appCompatEditText5 = onView(
            allOf(
                withId(R.id.emailSettings), withText("sergio@gmail.com"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.nav_host_fragment_activity_main),
                        0
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        appCompatEditText5.perform(replaceText("s@gmail.com"))

        val appCompatEditText6 = onView(
            allOf(
                withId(R.id.emailSettings), withText("s@gmail.com"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.nav_host_fragment_activity_main),
                        0
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        appCompatEditText6.perform(closeSoftKeyboard())

        val appCompatButton2 = onView(
            allOf(
                withId(R.id.botonModificarUsuario), withText("MODIFICAR USUARIO"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.nav_host_fragment_activity_main),
                        0
                    ),
                    5
                ),
                isDisplayed()
            )
        )
        appCompatButton2.perform(click())

        val frameLayout = onView(
            allOf(
                withId(R.id.nav_view),
                withParent(
                    allOf(
                        withId(R.id.container),
                        withParent(withId(android.R.id.content))
                    )
                ),
                isDisplayed()
            )
        )
        frameLayout.check(matches(isDisplayed()))

        val actionMenuItemView2 = onView(
            allOf(
                withId(R.id.action_settings), withContentDescription("Ajustes"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.toolbar),
                        1
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        actionMenuItemView2.perform(click())

        val appCompatTextView3 = onView(
            allOf(
                withId(androidx.core.R.id.title), withText("Cerrar sesión\n"),
                childAtPosition(
                    childAtPosition(
                        withId(androidx.appcompat.R.id.content),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        appCompatTextView3.perform(click())

        val appCompatEditText7 = onView(
            allOf(
                withId(R.id.emailLogin),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        appCompatEditText7.perform(replaceText("s@gmail.com"), closeSoftKeyboard())

        val appCompatEditText8 = onView(
            allOf(
                withId(R.id.passwordLogin),
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
        appCompatEditText8.perform(replaceText("Sergio01"), closeSoftKeyboard())

        val appCompatButton3 = onView(
            allOf(
                withId(R.id.botonInicio), withText("Iniciar sesión"),
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
        appCompatButton3.perform(click())

        val frameLayout2 = onView(
            allOf(
                withId(R.id.nav_view),
                withParent(
                    allOf(
                        withId(R.id.container),
                        withParent(withId(android.R.id.content))
                    )
                ),
                isDisplayed()
            )
        )
        frameLayout2.check(matches(isDisplayed()))

        val actionMenuItemView3 = onView(
            allOf(
                withId(R.id.action_settings), withContentDescription("Ajustes"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.toolbar),
                        1
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        actionMenuItemView3.perform(click())

        val appCompatTextView4 = onView(
            allOf(
                withId(androidx.core.R.id.title), withText("Cuenta"),
                childAtPosition(
                    childAtPosition(
                        withId(androidx.appcompat.R.id.content),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        appCompatTextView4.perform(click())

        val appCompatEditText9 = onView(
            allOf(
                withId(R.id.emailSettings), withText("s@gmail.com"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.nav_host_fragment_activity_main),
                        0
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        appCompatEditText9.perform(click())
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
