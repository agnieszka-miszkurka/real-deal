package com.example.misradbru.realdeal.foundproducts;

import android.app.Instrumentation;
import android.content.Intent;
import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;

import com.example.misradbru.realdeal.R;
import com.example.misradbru.realdeal.data.FoundProduct;

import junit.framework.AssertionFailedError;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasData;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.Matchers.allOf;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class FoundProductsActivityTest {

    private static final String SEARCHID123 = "SEARCHID123";
    private static final String LEATHER_SHOES = "leather shoes";
    private static final String UID1234 = "UID1234";
    private static final String GOOGLE_URL = "www.google.pl";

    @Rule
    public ActivityTestRule<FoundProductsActivity> mActivityRule =
            new ActivityTestRule<>(FoundProductsActivity.class);

    @Mock
    private
    FoundProductsContract.UserActionsListener mFoundProductsPresenter;

    private FoundProductsFragment mFoundProductsFragment = null;
    private FoundProductsAdapter foundProductsAdapter;


    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) {
                Object[] args = invocation.getArguments();
                System.out.println("called with arguments: " + Arrays.toString(args));
                return null;
            }
        }).when(mFoundProductsPresenter).deleteProduct(anyString());

        Intent startIntent = new Intent();
        startIntent.putExtra(FoundProductsActivity.UID, UID1234);
        startIntent.putExtra(FoundProductsActivity.PRODUCT_NAME, LEATHER_SHOES);
        startIntent.putExtra(FoundProductsActivity.SEARCH_ID, SEARCHID123);
        mActivityRule.launchActivity(startIntent);

        FoundProductsActivity mFoundProductsActivity = mActivityRule.getActivity();

        FoundProduct foundShoe1 = new FoundProduct(
                "Beautiful shoe",
                "www.allgero.pl/beautifulshoe1",
                "200",
                "PLN",
                "allegro"
        );

        FoundProduct foundShoe2 = new FoundProduct(
                "Beautiful shoe2",
                "www.allgero.pl/beautifulshoe2",
                "250",
                "PLN",
                "allegro"
        );

        FoundProduct foundShoe3 = new FoundProduct(
                "Beautiful shoe3",
                "www.allgero.pl/beautifulshoe3",
                "300",
                "PLN",
                "allegro"
        );

        final List<FoundProduct> foundProductsList = new ArrayList<>();
        foundProductsList.add(foundShoe1);
        foundProductsList.add(foundShoe2);
        foundProductsList.add(foundShoe3);

        mFoundProductsFragment = (FoundProductsFragment)
                mFoundProductsActivity.getSupportFragmentManager().getFragments().get(0);

        mFoundProductsFragment.mActionsListener = mFoundProductsPresenter;
        foundProductsAdapter = (FoundProductsAdapter) mFoundProductsFragment.mFoundProductsList.getAdapter();

        doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) {
                Object[] args = invocation.getArguments();
                System.out.println("called with arguments: " + Arrays.toString(args));
                foundProductsAdapter.addAll(foundProductsList);
                foundProductsAdapter.notifyDataSetChanged();
                return null;
            }
        }).when(mFoundProductsPresenter).showFoundProducts(LEATHER_SHOES, foundProductsAdapter);
    }

    @Test
    public void foundProduct_displaysUI() {
        try {
            onView(withId(R.id.delete_btn)).perform(click());
            // View is in hierarchy

        } catch (NoMatchingViewException e) {
            // View is not in hierarchy
            fail("View is not in hierarchy");
        }

        try {
            onView(withId(R.id.delete_btn)).check(matches(isDisplayed()));
            // View is displayed
        } catch (AssertionFailedError e) {
            // View not displayed
            fail("View not displayed");
        }

    }

    @Test
    public void foundProduct_clickOnDeleteButtonTriggersDeletion() {
        onView(withId(R.id.delete_btn)).perform(click());
        verify(mFoundProductsPresenter).deleteProduct(SEARCHID123);
    }

    @Test
    public void foundProduct_showSearches() {
        mFoundProductsFragment.showSearches();
        assertTrue(mActivityRule.getActivity().isFinishing());
    }

    @Test
    public void foundProduct_obenBrowserWhenProductClicked() {
        Intents.init();
        org.hamcrest.Matcher<Intent> expectedIntent = allOf(hasAction(Intent.ACTION_VIEW), hasData(GOOGLE_URL));
        intending(expectedIntent).respondWith(new Instrumentation.ActivityResult(0, null));
        mFoundProductsFragment.openFoundProductPage(GOOGLE_URL);
        intended(expectedIntent);
        Intents.release();
    }
}