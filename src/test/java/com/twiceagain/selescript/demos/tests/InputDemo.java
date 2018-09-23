/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twiceagain.selescript.demos.tests;

import com.twiceagain.selescript.demos.tests.DemoRunner;
import java.io.IOException;
import org.junit.Test;

/**
 * Currently tested demo.
 * @author xavier
 */
public class InputDemo extends DemoRunner {

    @Test    
    public void run1() throws IOException {
        runWithInput("demos/demoInput.ss", "demos/input/input.txt");
    }
    
    @Test
    public void run2() throws IOException {
        runDebug("demos/demoInput.ss");
    }
    
    
}
