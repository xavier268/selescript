/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twiceagain.selescript.interpreter.runtime;

import com.twiceagain.selescript.SSConfig;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 * Input object providing line by line entries when $read is called. If file
 * name is null, does nothing.
 *
 * @author xavier
 */
public class SSInput {

    private Scanner inputScanner = null;
    private final SSConfig config;
    
    

    public SSInput(SSConfig config) {

        this.config = config;
        reset();

    }

    
    public final void reset() {
        if (config.getInputFileName() != null) {
            try {
                inputScanner = new Scanner(new File(config.getInputFileName()), "UTF-8");
            } catch (FileNotFoundException ex) {
                try {
                    throw new RuntimeException(
                            "Could not read input from file : "
                            + Paths.get(config.getInputFileName()).toFile().getCanonicalPath(),
                            ex);
                } catch (IOException ex1) {
                    throw new RuntimeException(
                            "Could not read input from file :"
                            + config.getInputFileName(),
                            ex1);
                }
            }
        }

    }

    /**
     * Read the next line (excluding comments).
     *
     * @return
     */
    public String read() {

        while (inputScanner != null && inputScanner.hasNextLine()) {
            String s = inputScanner.nextLine();
            if (!s.startsWith("#") && !s.isEmpty()) {
                return s;
            }
        }

        return null; // if no scanner set or eof

    }
}
