package com.example.team_2a_security.data_classes;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UsersTest {

    Users user;
    @Before
    public void setUp() throws Exception {

        user = new Users();
    }

    @Test
    public void testDateSetterGetter() {
        user.setStatus(true);
        assertEquals(user.getStatus(),true);

    }

    @Test
    public void testPasswordSetterGetter() {
        user.setPassword("abc@");
        assertEquals(user.getPassword(),"abc@");

    }

    @Test
    public void testUsernameSetterGetter() {
        user.setUsername("User 1");
        assertEquals(user.getUsername(),"User 1");

    }

}