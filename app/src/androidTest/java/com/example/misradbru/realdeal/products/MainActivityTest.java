package com.example.misradbru.realdeal.products;

import android.support.test.rule.ActivityTestRule;
import android.view.View;

import com.example.misradbru.realdeal.R;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static junit.framework.TestCase.assertNotNull;

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
}
