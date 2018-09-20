/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twiceagain.selescript;

import com.twiceagain.selescript.interpreter.SSInterpreter;
import java.io.IOException;
import org.antlr.v4.runtime.CharStreams;

/**
 * Main command line entry point.
 *
 * @author xavier
 */
public class CommandLine {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {

        SSConfig config = SSConfig.parseArgs(args);

        if (config.isDebug()) {
            System.out.println(config.toString());
        }

        if (config.getScriptFileName() == null) {
            System.out.println("Please, type script (Ctl-D to finish) :");
            SSInterpreter ssInterpreter = new SSInterpreter(config, CharStreams.fromStream(System.in));
        } else {
            SSInterpreter ssInterpreter = new SSInterpreter(config);
        }
    }

}
