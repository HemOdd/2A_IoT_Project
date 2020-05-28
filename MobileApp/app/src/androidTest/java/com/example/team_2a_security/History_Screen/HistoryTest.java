package com.example.team_2a_security.History_Screen;

import android.view.View;
import android.widget.RelativeLayout;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.example.team_2a_security.Map_Screen.Map;
import com.example.team_2a_security.R;
import com.example.team_2a_security.TestActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

public class HistoryTest {

    @Rule
    public ActivityTestRule<TestActivity> mActivityTestRule = new ActivityTestRule<TestActivity>(TestActivity.class);
    private TestActivity mActivity = null;
    private History historyPage;
    @Before
    public void setUp() throws Exception {

        mActivity = mActivityTestRule.getActivity();
        RelativeLayout rlCont = mActivity.findViewById(R.id.test_container);
        historyPage = new History();
        mActivity.getSupportFragmentManager().beginTransaction().add(rlCont.getId(),historyPage).commitAllowingStateLoss();
        InstrumentationRegistry.getInstrumentation().waitForIdleSync();
    }

    @Test
    public void testRecyclerView(){

        View recyclerView = historyPage.getView().findViewById(R.id.history_rv);
        assertNotNull(recyclerView);
    }


    @After
    public void tearDown() throws Exception {

        mActivity = null;
    }

}