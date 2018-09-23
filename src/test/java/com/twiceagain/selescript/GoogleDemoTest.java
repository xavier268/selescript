/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twiceagain.selescript;

import java.io.IOException;
import org.junit.Ignore;
import org.junit.Test;

/**
 *
 * @author xavier
 */
public class GoogleDemoTest extends DemoRunner {

    @Test
    @Ignore
    public void run1() throws IOException {
        runDebug("demos/demoGoogle1.ss");
    }

    @Test
    @Ignore
    public void run2() throws IOException {
        runDebug("demos/demoGoogle2.ss");
    }

    @Test
    @Ignore
    public void run3() throws IOException {
        runDebug("demos/demoGoogle3.ss");
    }

    @Test
    public void run4() throws IOException {
        runDebug("demos/demoGoogle4.ss");
    }

}
