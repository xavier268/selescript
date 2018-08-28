/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twiceagain.selescript;

import com.twiceagain.selescript.implementation.SSListenerImplementation;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;

/**
 * A main function to handle command line parameters and provide a related
 * Config object.
 *
 * @author xavier
 */
public class CommandLine {

    /**
     * Compile a selecript source.
     *
     * @throws java.io.IOException
     * @See #parseArgs(String[]) code for commandline parameters and
     * configuration.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {

        Config config = parseArgs(args);

        SSListener list;

        if (config.getSourceFileName() == null) {
            list = new SSListenerImplementation(CharStreams.fromStream(System.in, Charset.forName("UTF-8")));
        } else {
            list = new SSListenerImplementation(config.getSourceFileName());
        }
        list.compile(config);
        System.out.printf(
                "%n====================="
                + "%nThe generated project is ready in %s"
                + "%n You can go there and run : bash <run.sh%n",
                 config.getTargetDir());
    }

    private static Config parseArgs(String[] args) {

        Config config = new Config();

        for (int i = 0; i < args.length; i++) {
            String command = args[i];
            switch (command) {

                case "-c":
                case "--classname": {
                    i++;
                    String param = args[i];
                    config.setTargetJavaClassName(param);
                    break;
                }

                case "-s":
                case "--source": {
                    i++;
                    String param = args[i];
                    config.setSourceFileName(param);
                    break;
                }

                case "-v":
                case "--version": {
                    printVersionInfo(config);
                    return null;
                }
                case "--help":
                case "-h":
                default: {
                    printHelp(config);
                    return null;
                }
            }

        }
        return config;

    }

    private static void printHelp(Config config) {
        printVersionInfo(config);
        List<String> h = Arrays.asList(
                "Available commands : ",
                "",
                "     -h",
                "     --help        : print this help message and exit",
                "",
                "     -v",
                "     --version     : print version information and exit",
                "",
                "     -s FILE",
                "     --source FILE : compile from specified FILE. Default is to read from stdin.",
                "",
                "",
                "     -c",
                "     --classname CALSSNAME : naming generated script class",
                ""
        );

        System.out.printf("%n%s%n", String.join(Config.NL, h));

    }

    private static void printVersionInfo(Config config) {

        System.out.printf("%nSelescript version : %s%nUning Selenium version : %s%n",
                config.getSelescriptVersion(),
                config.getSeleniumVersion());

    }

}
