/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twiceagain.selescript.interpreter.runtime;

import com.twiceagain.selescript.SSConfig;
import com.twiceagain.selescript.exceptions.SSException;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

/**
 * Manages writing to text, csv or html files.
 *
 * @author xavier
 */
public class SSOutput implements Closeable {

    private final SSConfig config;
    private PrintStream out;

    public SSOutput(SSConfig config) {
        this.config = config;
        this.out = System.out;
        reset(config.getOutputFileName());
    }

    @Override
    public final void close() {
        // Never close stdout !!
        if (out != null && out != System.out) {
            out.close();
        }
    }

    final void reset(String outFileName) {

        close();
        if (outFileName == null) {
            out = System.out;
            return;
        }

        new File(outFileName).mkdirs();
        try {
            out = new PrintStream(outFileName, StandardCharsets.UTF_8);            
        } catch (IOException ex) {
            throw new SSException("Cannot write to file : " + outFileName, ex);
        }

    }
    
    public void printf(String format, Object ... args) {
        out.printf(format, args);
    }
    
    
    
    

}
