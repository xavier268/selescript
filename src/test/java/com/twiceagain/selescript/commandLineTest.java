/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twiceagain.selescript;

import java.io.IOException;
import java.nio.file.NoSuchFileException;
import org.junit.Test;

/**
 * Run demoHelloWorld.ss
 *
 * @author xavier
 */
public class commandLineTest extends demoRunner {

    @Test(expected = NoSuchFileException.class)
    public void runNonExistentFile() throws IOException {
        run("fileDoesNotExist.ss");
    }

    @Test
    public void runHelloWorld() throws IOException {
        run("demos/demoHelloWorld.ss");
    }


}
