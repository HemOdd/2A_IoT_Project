package com.example.team_2a_security;

import android.view.View;

import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

public class Home_ScreenTest {

    @Rule
    public ActivityTestRule<Home_Screen> homeActivityRule = new ActivityTestRule(Home_Screen.class);
    private Home_Screen home = null;

    @Before
    public void setUp() throws Exception {
        home = homeActivityRule.getActivity();
    }

    @Test
    public void testNavigationBar() {

        View navBar = home.findViewById(R.id.bnv_mainMenuItems);
        assertNotNull(navBar);

    }

    @After
    public void tearDown() throws Exception {

        home = null;
    }
}