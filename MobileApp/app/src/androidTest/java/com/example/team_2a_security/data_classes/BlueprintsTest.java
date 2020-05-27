package com.example.team_2a_security.data_classes;

import org.junit.Before;
import org.junit.Test;
import org.junit.rules.TestWatcher;

import static org.junit.Assert.*;

public class BlueprintsTest {

    Blueprints blueprint;
    @Before
    public void setUp() throws Exception {

        blueprint = new Blueprints();
    }

    @Test
    public void testURISetterGetter() {
        blueprint.setBlueprintURI("1234");
        assertEquals(blueprint.getBlueprintURI(),"1234");

    }
}