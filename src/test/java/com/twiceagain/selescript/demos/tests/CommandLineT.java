/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twiceagain.selescript.demos.tests;

import com.twiceagain.selescript.demos.tests.DemoRunner;
import com.twiceagain.selescript.exceptions.SSSyntaxException;
import java.io.IOException;
import java.nio.file.NoSuchFileException;
import org.junit.Test;

/**
 * Run all demos (and more)
 *
 * @author xavier
 */
public class CommandLineT extends DemoRunner {

    @Test(expected = NoSuchFileException.class)
    public void runNonExistentFile() throws IOException {
        run("fileDoesNotExist.ss");
    }

    /**
     * Will not throw, despite error, because debug mode is not activated.
     *
     * @throws IOException
     */
    @Test()
    public void runSyntaxErrorFile() throws IOException {
        run("demos/erroneous/demoSyntaxError.ss");
    }

    /**
     * Will throw because debug mode activated.
     *
     * @throws IOException
     */
    @Test(expected = SSSyntaxException.class)
    public void runSyntaxErrorFile2() throws IOException {
        runDebug("demos/erroneous/demoSyntaxError.ss");
    }

    @Test
    public void runHelloWorld() throws IOException {
        run("demos/demoHelloWorld.ss");
    }

   

}
