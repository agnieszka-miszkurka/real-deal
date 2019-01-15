package com.example.misradbru.realdeal.searches;

import android.support.test.rule.ActivityTestRule;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.misradbru.realdeal.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class MainActivityTest {
    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule<>(MainActivity.class);

    private MainActivity mainActivity = null;
    @Before
    public void setUp() {
        mainActivity  = mActivityRule.getActivity();
    }
    @Test
    public void testLaunch() {
        View view = mainActivity.findViewById(R.id.products_list);
        assertNotNull(view);
    }

    @After
    public void tearDown() {
        mainActivity = null;
    }

    @Test
    public void setProgressIndicatorTest() {
        ProgressBar progressBar = mainActivity.findViewById(R.id.searches_progressbar);
        ListView searchesLV = mainActivity.findViewById(R.id.products_list);

        mainActivity.setProgressIndicator(true);

        assertNotNull("View is null.", progressBar);
        assertNotNull("View is null.", searchesLV);

    }

    @Test
    public void showNoSearchesMessageInitialTest() {
        TextView noSearchesTV = mainActivity.findViewById(R.id.no_searches_msg_textview);

        assertNotNull("View is null.", noSearchesTV);
        assertEquals(mainActivity.getString(R.string.no_searches_message), noSearchesTV.getText().toString());

    }
}
