/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twiceagain.selescript.implementation;

import com.twiceagain.selescript.CommandLine;
import com.twiceagain.selescript.Config;
import com.twiceagain.selescript.SSListener;
import com.twiceagain.selescript.exceptions.SSConfigurationException;
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
        SSListener99Implementation ll = new SSListener99Implementation("");
        String s = "abcdef";
        assertEquals("bcdef", ll.trim1(s));
        assertEquals("bcde", ll.trim2(s));

    }

    @Test
    @Ignore
    public void testCompilerInfrastructure() {
        String source
                = "$url = \"http:www.google.fr\" ;"
                + "emit url : $url ;"
                + "emit lien :  @ href:\".//a\" ;"
                + "emit titre125 : toto ;"
                + "emit button : @ \".//body\" ;"
                + "emit titi:2 + 3 ;"
                + "emit;"
                + "emit toto: titi;";
        
        System.out.printf("%nTest-compiling : %s%n", source);
        SSListener ls = new SSListener99Implementation(source);
        ls.compile(config);
        ls.dump();

        String s = ls.getCode();
        if (ls.hasSyntaxError()) {
            fail(ls.getErrorMessage());
        }
        ls.saveCode();
        // System.out.printf("%n%s%n", s);
    }
    
    @Test(expected = NoSuchFileException.class)
    public void testCommandLineWithExteranlSourceAndWrongName() throws IOException {        
        
        CommandLine.main("--dryrun", "-s", "WrongNameWhichDoesNotExists.ss");
    }
    
   
    
    @Test (expected = SSConfigurationException.class)
    public void testInvalidCLIOption() throws IOException {
        CommandLine.main("--");
    }
}
