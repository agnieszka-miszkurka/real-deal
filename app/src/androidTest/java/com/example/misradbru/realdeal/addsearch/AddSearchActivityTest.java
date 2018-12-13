package com.example.misradbru.realdeal.addsearch;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;

import com.example.misradbru.realdeal.R;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import static android.support.test.InstrumentationRegistry.getTargetContext;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.Matchers.allOf;

@RunWith(MockitoJUnitRunner.class)
public class AddSearchActivityTest {

    private static final String UID1 = "UID123";
    @Rule
    public ActivityTestRule<AddSearchActivity> mActivityRule =
            new ActivityTestRule<>(AddSearchActivity.class);

    @Mock
    AddSearchContract.UserActionsListener mAddSearchPresenter;

    private AddSearchActivity mAddSearchActivity = null;
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        Intent startIntent = new Intent();
        startIntent.putExtra(AddSearchActivity.UID, UID1);
        mActivityRule.launchActivity(startIntent);

        mAddSearchActivity = mActivityRule.getActivity();
    }

    @Test
    public void addSearch_DisplayedInUi() {
        onView(withId(R.id.basket_imageview)).check(matches(allOf(
                isDisplayed()
        )));
    }

    @Test
    public void errorShownOnEmptyMessage() {
        onView(withId(R.id.addNewProductBtn)).perform(click());
        // Add note title and description
        onView(withId(R.id.productName)).perform(typeText(""));
        onView(withId(R.id.searchPhrase)).perform(typeText(""),
                closeSoftKeyboard());
        // Save the note
        onView(withId(R.id.addNewProductBtn)).perform(click());

        String emptyNoteMessageText = getTargetContext().getString(R.string.empty_addproduct_message);
        onView(withText(emptyNoteMessageText)).check(matches(isDisplayed()));
    }

    @Test
    public void errorShownOnMinMaxPriceMismatch() {
        onView(withId(R.id.addNewProductBtn)).perform(click());
        // Add note title and description

        onView(withId(R.id.productName)).perform(typeText("Shoes"),
                closeSoftKeyboard());
        onView(withId(R.id.searchPhrase)).perform(typeText("New pink shoes"),
                closeSoftKeyboard());
        onView(withId(R.id.minPrice)).perform(typeText("100"),
                closeSoftKeyboard());
        onView(withId(R.id.maxPrice)).perform(typeText("10"),
                closeSoftKeyboard());

        // Save the note
        onView(withId(R.id.addNewProductBtn)).perform(click());

        String emptyNoteMessageText = getTargetContext().getString(R.string.pricemismatch_addproduct_message);
        onView(withText(emptyNoteMessageText)).check(matches(isDisplayed()));
    }

    @Test
    public void pressBackWorksProperly() {
        mAddSearchActivity.onSupportNavigateUp();
        assertTrue(mActivityRule.getActivity().isFinishing());
    }
}