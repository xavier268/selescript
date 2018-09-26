/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twiceagain.selescript.interpreter.runtime;

import com.twiceagain.selescript.SSConfig;
import com.twiceagain.selescript.exceptions.SSException;
import java.io.Closeable;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;

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
        reopen(config.getOutputFileName(), true);
    }

    @Override
    public final void close() {
        // Never close stdout !!
        if (out != null && out != System.out) {
            out.close();
        }
    }

    /**
     * Reopen of output file, even if already open and no filename change. This
     * will append to an existing file. It is up to the caller to avoid calling
     * reopen on an open file for obvious performance reasons..
     *
     * @param outFileName
     */
    final void reopen(String outFileName, boolean append) {
        config.setOutputFileName(outFileName);
        close();
        if (outFileName == null) {
            out = System.out;
            return;
        }

        Paths.get(outFileName).toAbsolutePath().getParent().toFile().mkdirs();
        try {
            // Opening for APPENDING
            out = new PrintStream(new FileOutputStream(outFileName, append));
        } catch (IOException ex) {
            throw new SSException("Cannot write to file : " + outFileName, ex);
        }

    }

    public void printf(String format, Object... args) {
        out.printf(format, args);
    }

}
