/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twiceagain.selescript.implementation;

import com.twiceagain.selescript.CommandLine;
import com.twiceagain.selescript.compiler.SSCompiler;
import com.twiceagain.selescript.configuration.Config;
import com.twiceagain.selescript.configuration.SSListener;
import com.twiceagain.selescript.exceptions.SSConfigurationException;
import com.twiceagain.selescript.exceptions.SSException;
import java.io.IOException;
import java.nio.file.NoSuchFileException;
import static org.junit.Assert.*;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Used for debugging while developping.
 * @author xavier
 */
// @Ignore
public class CompleteSourceCompilerTest {

    Config config = new Config("example").setTargetPackage("com", "test");

    @Test
    public void trimTest() {
        String s = "abcdef";
        assertEquals("bcdef", SSBaseListener.trim1(s));
        assertEquals("bcde", SSBaseListener.trim2(s));

    }    
    
    @Test(expected = NoSuchFileException.class)
    public void testCommandLineWithExteranlSourceAndWrongName() throws IOException {        
        
        CommandLine.main("--dryrun", "-s", "WrongNameWhichDoesNotExists.ss");
    }
    
   
    
    @Test (expected = SSConfigurationException.class)
    public void testInvalidCLIOption() throws IOException {
        CommandLine.main("--");
    }
    
    @Test(expected = SSException.class)
    public void demoSyntaxError() throws IOException {
        CommandLine.main("-s", "demos/erroneous/demoSyntaxError.ss", "--dryrun");
    }
}
