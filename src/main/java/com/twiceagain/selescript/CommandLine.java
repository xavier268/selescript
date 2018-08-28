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

        if (config == null) {
            return;
        } else {
            printVersionInfo(config);
        }

        SSListener list;

        if (config.getSourceFileName() == null) {
            System.out.printf(
                    "%nReading the script from stdin."
                    + "%nTerminate with Ctl-D (linux) or Ctl-Z(windows)%n");
            list = new SSListenerImplementation(CharStreams.fromStream(System.in, Charset.forName("UTF-8")));

        } else {
            System.out.printf(
                    "%nReading the script from file : %s%n",
                    config.getSourceFileName());
            list = new SSListenerImplementation(config.getSourceFileName());
        }
        list.compile(config);
        list.dump(); // debug
        if (list.hasSyntaxError()) {
            System.out.printf("%n******** ERROR ***********%n%s%n",
                    list.getErrorMessage());
        } else {
            if (config.getDryRunFlag()) {
                System.out.printf("%n%n **** Nothing actually saved to file : dryrun flag was selected ***%n%n");
            } else {
                list.saveCode();
            }
            System.out.printf(
                    "%n====================="
                    + "%nThe generated project is ready in %s"
                    + "%nYou can go there and run : bash <run.sh%n",
                    config.getTargetDir());
        }
    }

    protected static Config parseArgs(String[] args) {

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
                
                case "-d":
                case "--debug": {
                    config.setDebugMode(true);
                    break;
                }
                
                case "-n":
                case "--nodebug": {
                    config.setDebugMode(false);
                    break;
                }

                case "--dryrun": {
                    config.setDryRunFlag(true);
                    break;
                }

                case "--help":
                case "-h": {
                    printHelp(config);
                    return null;
                }
                
                default: {
                    System.out.printf("%n : Unrecognized command line option : %s%n", command);
                    printHelp(config);
                    return null;
                }
            }

        }
        return config;

    }

    public static void printHelp(Config config) {
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
                "     -d",
                "     --debug        : set debug mode to true",
                "",
                "     -n",
                "     --nodebug      : set debug mode to false",
                "",
                "     --dryrun       : compile everything, but do not save to file",
                "",
                "     -s FILE",
                "     --source FILE : compile from specified FILE. Default is to read from stdin.",
                "",
                "",
                "     -c",
                "     --classname CLASSNAME : generated scrapper will be named CLASSNAME",
                ""
        );

        System.out.printf("%n%s%n", String.join(Config.NL, h));

    }

    public static void printVersionInfo(Config config) {

        System.out.printf("%nSelescript compiler."
                + "%n(c) Xavier Gandillot - 2018"
                + "%nSee details on https://github.com/xavier268/selescript"
                + "%nSelescript version : %s"
                + "%nUsing Selenium version : %s"
                + "%nCompiling for java version : %s"
                + "%nGenerated files will be saved in : %s%n",
                config.getSelescriptVersion(),
                config.getSeleniumVersion(),
                config.getTargetJavaVersion(),
                config.getTargetDir());
    }

}
