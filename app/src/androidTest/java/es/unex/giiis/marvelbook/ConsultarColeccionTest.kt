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
import org.hamcrest.core.IsInstanceOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class ConsultarColeccionTest {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(LoginActivity::class.java)

    @Test
    fun cU10ESPRESSOTest() {
        val appCompatTextView = onView(
            withId(R.id.crearCuenta)
        )
        appCompatTextView.perform(click())

        val appCompatEditText = onView(
            withId(R.id.nombreRegistro),
        )
        appCompatEditText.perform(replaceText("Diego"), closeSoftKeyboard())

        val appCompatEditText2 = onView(
            withId(R.id.emailRegistro),
        )
        appCompatEditText2.perform(replaceText("diego@gmail.com"), closeSoftKeyboard())

        val appCompatEditText3 = onView(
            withId(R.id.passwordRegistro),
        )
        appCompatEditText3.perform(replaceText("Diego2001"), closeSoftKeyboard())

        val appCompatEditText4 = onView(
            withId(R.id.passwordRegistro2),
        )
        appCompatEditText4.perform(replaceText("Diego2001"), closeSoftKeyboard())

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

        val tabView = onView(
            allOf(
                withContentDescription("COMICS"),
                childAtPosition(
                    childAtPosition(
                        allOf(withId(R.id.tabLayout), withContentDescription("Personajes")),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        tabView.perform(click())

        val tabView2 = onView(
            allOf(
                withContentDescription("CREADOR"),
                childAtPosition(
                    childAtPosition(
                        allOf(withId(R.id.tabLayout), withContentDescription("Personajes")),
                        0
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        tabView2.perform(click())

        val tabView3 = onView(
            allOf(
                withContentDescription("PERSONAJES"),
                childAtPosition(
                    childAtPosition(
                        allOf(withId(R.id.tabLayout), withContentDescription("Personajes")),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        tabView3.perform(click())

        Thread.sleep(20000)

        val textView = onView(
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
        textView.check(matches(withText("PERSONAJES")))

        val textView2 = onView(
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
        textView2.check(matches(withText("3-D Man")))

        val tabView4 = onView(
            allOf(
                withContentDescription("CREADOR"),
                childAtPosition(
                    childAtPosition(
                        allOf(withId(R.id.tabLayout), withContentDescription("Personajes")),
                        0
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        tabView4.perform(click())

        val textView3 = onView(
            allOf(
                withText("CREADOR"),
                withParent(
                    allOf(
                        withContentDescription("CREADOR"),
                        withParent(IsInstanceOf.instanceOf(android.widget.LinearLayout::class.java))
                    )
                ),
                isDisplayed()
            )
        )
        textView3.check(matches(withText("CREADOR")))

        val textView4 = onView(
            allOf(
                withId(R.id.nombrePersonajeLista), withText("#O"),
                withParent(
                    allOf(
                        withId(R.id.cl_item_lista_coleccion),
                        withParent(withId(R.id.cl_item_lista))
                    )
                ),
                isDisplayed()
            )
        )
        textView4.check(matches(withText("#O")))

        val tabView5 = onView(
            allOf(
                withContentDescription("COMICS"),
                childAtPosition(
                    childAtPosition(
                        allOf(withId(R.id.tabLayout), withContentDescription("Personajes")),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        tabView5.perform(click())

        val textView5 = onView(
            allOf(
                withText("COMICS"),
                withParent(
                    allOf(
                        withContentDescription("COMICS"),
                        withParent(IsInstanceOf.instanceOf(android.widget.LinearLayout::class.java))
                    )
                ),
                isDisplayed()
            )
        )
        textView5.check(matches(withText("COMICS")))

        val textView6 = onView(
            allOf(
                withId(R.id.nombrePersonajeLista),
                withText("AMAZING SPIDER-MAN BY ZEB WELLS VOL. 8: SPIDER-MAN'S FIRST HUNT (Trade Paperback)"),
                withParent(
                    allOf(
                        withId(R.id.cl_item_lista_coleccion),
                        withParent(withId(R.id.cl_item_lista))
                    )
                ),
                isDisplayed()
            )
        )
        textView6.check(matches(withText("AMAZING SPIDER-MAN BY ZEB WELLS VOL. 8: SPIDER-MAN'S FIRST HUNT (Trade Paperback)")))
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
