/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twiceagain.selescript.compiler;

import com.twiceagain.selescript.configuration.Config;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Detect changes in the way demos or other fragment are compiled. When a change
 * is detected, human review is expected to confirme generated code is correct
 * by manually setting the expected md5 signature.
 *
 * @author xavier
 */
public class StabilityTest {
    
    @Test
    public void testSignatures() {
    st("demos/demoHelloWorld.ss", "2F3250D51DE2D2E42B8949A9E8CABA9");
    }

    
    
    /**
     * Stability Testing :  performing the actual testing.
     *
     * @param fileName
     * @param expectedSignature
     */
    public void st(String fileName, String expectedSignature) {

        Config config = new Config()
                .setSourceFileName(fileName)
                .setDebugMode(false)
                .setDryRunFlag(true);

        SSCompiler comp = new SSCompiler(config);
        if (comp.hasSyntaxError()) {
            fail("Syntax error while comiling " + fileName);
        }
        String ch = comp.getCodeHash();
        if (!ch.equals(expectedSignature)) {
            System.out.printf("%nThe hashcode did not match."
                    + "%nThe computed md5 code is : %s"
                    + "%nPlease review carefully the code below, "
                    + "and if it is correct, use the above value "
                    + "to test for stability"
                    + "%nFile : %s"
                    + "%n=================================================="
                    + "%n%s"
                    + "%n==================================================%n",
                    
                    ch, fileName, comp.getCode());
            assertEquals("The signature of the generated file has not been set or has changed", expectedSignature, ch);
            
        }

    }

}
