/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twiceagain.selescript.interpreter.runtime;

import com.twiceagain.selescript.SSConfig;
import org.junit.Ignore;
import org.junit.Test;

/**
 *
 * @author xavier
 */
public class SSInputTest {

    @Test
    @Ignore
    public void testRead() {
        SSConfig config = new SSConfig();
        config.setInputFileName("demos/input/input.txt");
        SSInput i = new SSInput(config);
        String r;
        System.out.printf("Reading input file : " + config.getInputFileName());
        int ii = 0;
        do {
            ii++;
            r = i.read();
            System.out.printf("%n%03d\t<%s>", ii,r);
        } while (r != null);
        
        System.out.printf("%nResetting with default file name");
        i.reset(null);
        
        ii = 0;
        do {
            ii++;
            r = i.read();
            System.out.printf("%n%03d\t<%s>", ii,r);
        } while (r != null);
        
        System.out.printf("%nResetting with source file name");
        i.reset("demos/demoInput.ss");
        ii = 0;
        do {
            ii++;
            r = i.read();
            System.out.printf("%n%03d\t<%s>", ii,r);
        } while (r != null);
        
        
        System.out.println();
    }
}
