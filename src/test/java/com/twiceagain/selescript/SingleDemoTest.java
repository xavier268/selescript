/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twiceagain.selescript;

import java.io.IOException;
import org.junit.Test;

/**
 * Currently tested demo.
 * @author xavier
 */
public class SingleDemoTest extends DemoRunner {

    @Test
    public void run1() throws IOException {
        run("demos/demoLoop.ss");
    }
}
