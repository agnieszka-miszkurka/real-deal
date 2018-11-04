package com.example.misradbru.realdeal.products;

import android.support.test.rule.ActivityTestRule;
import android.view.View;

import com.example.misradbru.realdeal.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class EmailVerfificationActivityTest {

    @Rule
    public ActivityTestRule<EmailVerfificationActivity> mActivityRule =
            new ActivityTestRule<>(EmailVerfificationActivity.class);

    private EmailVerfificationActivity emailVerificationActivity = null;

    @Before
    public void setUp() {
        emailVerificationActivity  = mActivityRule.getActivity();
    }

    @Test
    public void testLaunch() {
        View view = emailVerificationActivity.findViewById(R.id.send_email_btn);
        assertNotNull(view);
    }

    @After
    public void tearDown() {
        emailVerificationActivity = null;
    }


    @Test
    public void onBackPressed_finishesActivity() {
        emailVerificationActivity.onBackPressed();
        assertTrue(mActivityRule.getActivity().isFinishing());
    }
}