/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twiceagain.selescript.compiler;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author xavier
 */
public class ConfigTest {

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
        assertNotNull(Config.getVersion());
    }

    /**
     * Ensure resources are available.
     */
    @Test
    public void testResourcesAvailability() {

        assertNotNull(Config.getBuiltinsMethods());
        assertFalse(Config.getBuiltinsList().isEmpty());

        System.out.printf("%nThe recognized builtins tokens are : %s%n", Config.getBuiltinsList());
    }

    @Test(expected = NullPointerException.class)
    public void testGettingNonExistentResourceFile() {
        Config.getResourceAsString("invalidName");
    }

    @Test
    public void testHeader() {
        System.out.printf("%s", Config.getPackageDeclaration());
        System.out.printf("%s", Config.getImportsDeclarations());
    }

    @Test
    public void testPathConfig() {
        System.out.printf("%nCurrent defaut target path : %s%n",
                Config.getTargetDir());
System.out.printf("%nCurrent defaut target java class path : %s%n",
                Config.getTargetJavaClassDirectory().toString());

System.out.printf("%nCurrent defaut target java class file name : %s%n",
                Config.getTargetJavaClassPath().toString());


    }

    @Test
    public void testCopyFiles() {
        Config.copyRuntimeFiles();
    }

}
