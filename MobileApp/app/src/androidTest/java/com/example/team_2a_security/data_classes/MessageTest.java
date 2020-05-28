package com.example.team_2a_security.data_classes;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MessageTest {

    Message message;
    @Before
    public void setUp() throws Exception {

        message = new Message();
    }

    @Test
    public void testDateSetterGetter() {
        message.setDate("25/05/20");
        assertEquals(message.getDate(),"25/05/20");

    }

    @Test
    public void testMessageSetterGetter() {
        message.setMessage("Hello There!");
        assertEquals(message.getMessage(),"Hello There!");

    }

    @Test
    public void testUsernameSetterGetter() {
        message.setUsername("User 1");
        assertEquals(message.getUsername(),"User 1");

    }

}