/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twiceagain.selescript.demos;

import com.twiceagain.selescript.demos.tests.*;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Adjustable test suite for time consuming demo tests.
 * Adjust classes as needed.
 * @author xavier
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
    DemoArithmetics.class,
    // GoogleDemo.class,
    CommandLineT.class,
    InputDemo.class,
    LoopDemo.class,
   // DbDemo.class,
   // RunAllDemos.class,
})
public class SuiteForDemosTest {   

}
