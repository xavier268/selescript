/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twiceagain.selescript.demos.tests;

import java.io.IOException;
import org.junit.Test;

/**
 *
 * @author xavier
 */
public class SnapshotDemo extends DemoRunner {
    
    @Test
    public void run() throws IOException {
        runDebug("demos/demoSnapshot.ss");
    }
}
