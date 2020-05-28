package com.example.team_2a_security.Account_Screen;

import android.view.View;
import android.widget.RelativeLayout;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.example.team_2a_security.Map_Screen.Map;
import com.example.team_2a_security.R;
import com.example.team_2a_security.TestActivity;
import com.google.firebase.firestore.auth.User;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserAccountTest {

    @Rule
    public ActivityTestRule<TestActivity> mActivityTestRule = new ActivityTestRule<TestActivity>(TestActivity.class);
    private TestActivity mActivity = null;
    private UserAccount accPage;
    @Before
    public void setUp() throws Exception {

        mActivity = mActivityTestRule.getActivity();
        RelativeLayout rlCont = mActivity.findViewById(R.id.test_container);
        accPage = new UserAccount();
        mActivity.getSupportFragmentManager().beginTransaction().add(rlCont.getId(),accPage).commitAllowingStateLoss();
        InstrumentationRegistry.getInstrumentation().waitForIdleSync();
    }

    @Test
    public void testrecyclerView(){

        View recyclerView = accPage.getView().findViewById(R.id.rv_UsersOnline);
        assertNotNull(recyclerView);
    }


    @After
    public void tearDown() throws Exception {

        mActivity = null;
    }

}