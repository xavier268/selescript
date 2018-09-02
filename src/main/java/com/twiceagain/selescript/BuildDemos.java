package com.twiceagain.selescript;

import java.io.IOException;
import java.util.List;

/**
 * Make all the demos. Any files in the demos directory are assumed to be the
 * demos files to compile.
 *
 * @author xavier
 */
public class BuildDemos {
    /**
     * Main class for building demos.
     * @param args
     * @throws IOException 
     */
    public static void main(String ... args) throws IOException {

        List<String> dems = Config.listFilesFromDirectory("demos");

        System.out.println("Compiling all the files from the demos directory ");
        System.out.println("File list : " + dems);

        for (String d : dems) {
            CommandLine.main("-s", "demos" + Config.FILESEPARATOR + d, "--debug");
        }
    }
}
