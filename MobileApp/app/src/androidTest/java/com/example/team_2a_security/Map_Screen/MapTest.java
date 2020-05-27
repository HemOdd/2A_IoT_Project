package com.example.team_2a_security.Map_Screen;

import android.view.View;
import android.widget.RelativeLayout;

import androidx.test.internal.runner.listener.InstrumentationResultPrinter;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;


import com.example.team_2a_security.R;
import com.example.team_2a_security.TestActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

public class MapTest {

    @Rule
    public ActivityTestRule<TestActivity> mActivityTestRule = new ActivityTestRule<TestActivity>(TestActivity.class);
    private TestActivity mActivity = null;
    private Map mappage;
    @Before
    public void setUp() throws Exception {

        mActivity = mActivityTestRule.getActivity();
        RelativeLayout rlCont = mActivity.findViewById(R.id.test_container);
        mappage = new Map();
        mActivity.getSupportFragmentManager().beginTransaction().add(rlCont.getId(),mappage).commitAllowingStateLoss();
        InstrumentationRegistry.getInstrumentation().waitForIdleSync();
    }

    @Test
    public void testSensor1Indicator(){

        View sensor1Indicator = mappage.getView().findViewById(R.id.btn_sensor1);
        assertNotNull(sensor1Indicator);
    }

    @Test
    public void testSensor2Indicator(){

        View sensor2Indicator = mappage.getView().findViewById(R.id.btn_sensor2);
        assertNotNull(sensor2Indicator);
    }

    @Test
    public void testImageView(){

        View iv = mappage.getView().findViewById(R.id.iv_blueprint);
        assertNotNull(iv);
    }

    @Test
    public void testSensor1Label(){

        View lb1 = mappage.getView().findViewById(R.id.textView);
        assertNotNull(lb1);
    }

    @Test
    public void testSensor2Label(){

        View lb2 = mappage.getView().findViewById(R.id.textView2);
        assertNotNull(lb2);
    }

    @Test
    public void checkDateTimeFunc(){

        Date current = new Date();
        String givenDate = "27/05/30T19:18:22";
        boolean result = mappage.checkDateTime(givenDate);
        assertEquals(result,true);
    }

    @After
    public void tearDown() throws Exception {

        mActivity = null;
    }
}