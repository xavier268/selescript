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
 *
 * @author xavier
 */

public class GoogleDemo extends DemoRunner {

    @Test
    public void run1() throws IOException {
        runDebug("demos/demoGoogle1.ss");
    }

    @Test
    public void run2() throws IOException {
        runDebug("demos/demoGoogle2.ss");
    }

    @Test
    public void run3() throws IOException {
        runDebug("demos/demoGoogle3.ss");
    }

    @Test
    public void run4() throws IOException {
        runDebug("demos/demoGoogle4.ss");
    }

}
