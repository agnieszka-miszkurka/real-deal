package com.example.misradbru.realdeal.products;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static com.google.common.truth.Truth.assertThat;

@RunWith(JUnit4.class)
public class AuthenticationTest {

    private static final String ANONYMOUS = "anonymous";
    private static final String USERNAME = "John Smith";

    @Test
    public void signOutCleanUp_isCorrect() {
        MainActivity mainActivity = new MainActivity();
        mainActivity.mUsername = USERNAME;
        mainActivity.onSignedOutCleanUp();
        assertThat(ANONYMOUS).isEqualTo(mainActivity.mUsername);
    }

    @Test
    public void onSignedInInitialize_isCorrect() {
        MainActivity mainActivity = new MainActivity();
        mainActivity.onSignedInInitialize("John Smith");
        assertThat(USERNAME).isEqualTo(mainActivity.mUsername);
    }

}
