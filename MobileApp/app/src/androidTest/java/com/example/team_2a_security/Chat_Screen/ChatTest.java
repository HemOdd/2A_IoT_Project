package com.example.team_2a_security.Chat_Screen;

import android.view.View;
import android.widget.RelativeLayout;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.example.team_2a_security.History_Screen.History;
import com.example.team_2a_security.R;
import com.example.team_2a_security.TestActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

public class ChatTest {

    @Rule
    public ActivityTestRule<TestActivity> mActivityTestRule = new ActivityTestRule<TestActivity>(TestActivity.class);
    private TestActivity mActivity = null;
    private Chat chatPage;
    @Before
    public void setUp() throws Exception {

        mActivity = mActivityTestRule.getActivity();
        RelativeLayout rlCont = mActivity.findViewById(R.id.test_container);
        chatPage = new Chat();
        mActivity.getSupportFragmentManager().beginTransaction().add(rlCont.getId(),chatPage).commitAllowingStateLoss();
        InstrumentationRegistry.getInstrumentation().waitForIdleSync();
    }

    @Test
    public void testRecyclerView(){

        View recyclerView = chatPage.getView().findViewById(R.id.rv_message_area);
        assertNotNull(recyclerView);
    }

    @Test
    public void testMessageBox(){

        View messageBox = chatPage.getView().findViewById(R.id.et_enteredMessage);
        assertNotNull(messageBox);
    }

    @Test
    public void testSendButton(){

        View sendButton = chatPage.getView().findViewById(R.id.btn_sendMessage);
        assertNotNull(sendButton);
    }



    @After
    public void tearDown() throws Exception {

        mActivity = null;
    }

}