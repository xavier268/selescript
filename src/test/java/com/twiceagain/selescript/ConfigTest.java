/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twiceagain.selescript;

import com.twiceagain.selescript.configuration.Config;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;

/**
 *
 * @author xavier
 */
// @Ignore
public class ConfigTest {
    
    public Config config = new Config("example").setTargetPackage("com","test");

    public ConfigTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getVersion method, of class Config.
     */
    @Test
    public void testGetVersion() {
        assertNotNull(config.getSelescriptVersion());
    }
    
    @Test
    public void testReadingDemosSOurcefiles() {
        List<String> ls = Config.listFilesFromDirectory("demos");
        System.out.println("Demo file list : " + ls.toString());
        assertTrue("There should be at least 3 files", ls.size() >= 3);
        assertTrue("The first file sohould have a .ss ending", ls.get(0).endsWith(".ss"));
    }

    /**
     * Ensure resources are available.
     */
    @Test
    public void testResourcesAvailability() {

        assertFalse(config.getBuiltinsSet().isEmpty());
    }

    @Test(expected = NullPointerException.class)
    public void testGettingNonExistentResourceFile() {
        config.getResourceAsString("invalidName");
    }    
    
    @Test
    public void uidTest1() {
        List<String> ss = new ArrayList<>(12);
        for(int i=0;i<10; i++) {
            ss.add(config.createUniqueId());
        }
        assertEquals(10, ss.size());
    }
    
    
    @Test
    public void md5test() {
        String s;
        
        s="lkj";
        assertEquals("48E2E79FEC9BC01D9A0E0A8FA68B289",config.getMd5Hash(s));
    }
    
    
    
    
}
