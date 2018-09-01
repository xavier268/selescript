/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twiceagain.selescript;

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
@Ignore
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

    /**
     * Ensure resources are available.
     */
    @Test
    public void testResourcesAvailability() {

        assertFalse(config.getBuiltinsList().isEmpty());

        System.out.printf("%nThe recognized builtins tokens are : %s%n", config.getBuiltinsList());
    }

    @Test(expected = NullPointerException.class)
    public void testGettingNonExistentResourceFile() {
        config.getResourceAsString("invalidName");
    }

    @Test
    public void testHeader() {
        System.out.printf("%s", config.getPackageDeclaration());
        System.out.printf("%s", config.getImportsDeclarations());
    }

    @Test
    public void testPathConfig() {
        System.out.printf("%nCurrent defaut target path : %s%n",
                config.getTargetDir());
System.out.printf("%nCurrent defaut target java class path : %s%n",
                config.getTargetJavaClassDirectory().toString());

System.out.printf("%nCurrent defaut target java class file name : %s%n",
                config.getTargetJavaClassPath().toString());


    }

    @Test
    public void testCopyFiles() {
        config.createAllRuntimeSupportFiles();
    }
    
    @Test
    public void uidTest1() {
        List<String> ss = new ArrayList<>(12);
        for(int i=0;i<10; i++) {
            ss.add(config.createUniqueId());
        }
        System.out.printf("%nUnique ids test : %s%n", ss.toString());
        assertEquals(10, ss.size());
    }
    
    
    @Test
    public void md5test() {
        String s;
        
        s="lkj";
        assertEquals("48E2E79FEC9BC01D9A0E0A8FA68B289",config.getMd5Hash(s));
    }
    
    
}
