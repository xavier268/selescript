/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twiceagain.selescript;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Caution : running all the demos in debug mode. May take a while ...
 * @author xavier
 */
public class RunAllDemosTest extends DemoRunner {
    
    @Test
    public void listDemos() throws IOException {
        System.out.println("Available demos: ");
        File ddir = Paths.get("demos").toFile();
        assertTrue(ddir.isDirectory());
        for(File f : ddir.listFiles()) {
            String fn = f.getCanonicalPath();
            if(fn.endsWith(".ss")) {
                System.out.println("\t" + fn);
            }
        }
        System.out.println();        
    }
    
    @Test
    public void runDemos() throws IOException {
        
        File ddir = Paths.get("demos").toFile();
        assertTrue(ddir.isDirectory());
        for(File f : ddir.listFiles()) {
            String fn = f.getCanonicalPath();
            if(fn.endsWith(".ss")) {
                runDebug(fn);
            }
        }
    }
    
    
    
}
