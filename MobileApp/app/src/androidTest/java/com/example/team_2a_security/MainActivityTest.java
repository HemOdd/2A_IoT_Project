package com.example.team_2a_security;

import android.view.View;
import android.widget.EditText;

import androidx.test.rule.ActivityTestRule;

import com.google.firebase.database.DatabaseReference;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;



public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule(MainActivity.class);
    private MainActivity mActivity = null;
    @Before
    public void setUp() throws Exception {
        mActivity = mActivityRule.getActivity();
    }

    @Test
    public void testUsernameField() {

        View usernameField = mActivity.findViewById(R.id.et_Username);
        assertNotNull(usernameField);

    }

    @Test
    public void testPasswordField() {

        View passwordField = mActivity.findViewById(R.id.et_password);
        assertNotNull(passwordField);

    }

    @Test
    public void testLoginButton() {

        View loginButton = mActivity.findViewById(R.id.btn_login);
        assertNotNull(loginButton);

    }


    @After
    public void tearDown() throws Exception {

        mActivity = null;
    }

}