/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twiceagain.selescript.implementation;

import com.twiceagain.selescript.CommandLine;
import com.twiceagain.selescript.exceptions.SSException;
import java.io.IOException;
import org.junit.Test;

/**
 * Make all the demos.
 *
 * @author xavier
 */
public class MakeDemos {

    @Test
    public void demoHelloWorld() throws IOException {
        CommandLine.main("-s", "demos/demoHelloWorld.ss", "-c", "HelloWorld", "-debug");
    }

    @Test
    public void demo1() throws IOException {
        CommandLine.main("-s", "demos/demo1.ss", "-c", "demo1");
    }
    
    @Test(expected = SSException.class)
    public void demoSyntaxError() throws IOException {
        CommandLine.main("-s","demos/demoSyntaxError.ss","--dryrun");
    }

}
