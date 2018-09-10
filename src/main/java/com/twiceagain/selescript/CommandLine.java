package com.twiceagain.selescript;

import com.twiceagain.selescript.compiler.SSCompiler;
import com.twiceagain.selescript.configuration.Config;
import static com.twiceagain.selescript.configuration.Config.FILESEPARATOR;
import com.twiceagain.selescript.exceptions.SSConfigurationException;
import com.twiceagain.selescript.exceptions.SSException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

/**
 * Main entry point for command line use.
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
    public static void main(String... args) throws IOException {

        Config config = parseArgs(args);

        if (config == null) {
            throw new SSConfigurationException("Could not configure based on provided cli arguments");
        } else {
            printVersionInfo(config);
            // If debug mode, we dump the configuration first.
            if (config.getDebugMode()) {
                config.dump();
            }
        }

        SSCompiler comp = new SSCompiler(config);

        if (config.getDryRunFlag()) {
            System.out.printf("%n%n **** Nothing actually saved to file : dryrun flag was selected ***%n%n");
        } else {
            comp.saveCode();

            if (config.getExecuteFlag()) {
                System.out.printf("%n====================="
                        + "%nThe generated project is ready in %s"
                        + "%nCompiling and running it now"
                        + "%n=====================",
                        config.getTargetDir());
                run(config);
            } else {
                System.out.printf(
                        "%n====================="
                        + "%nThe generated project is ready in %s"
                        + "%nYou can go there and run : bash <run.sh%n"
                        + "%n=====================",
                        config.getTargetDir());
            }

        }

    }

    /**
     * Ask bash to run the command provided.Warning, this is lWarning, this is
     * linux specific.
     *
     * @param config
     */
    public static void run(Config config) {
        if (config != null && !config.isLinux()) {
            throw new SSException("Execute option is not yet available on windows.");
        }
        if (config == null || config.getDryRunFlag() || !config.getExecuteFlag()) {
            return;
        }

        String rf = "bash <" + config.getTargetDir() + FILESEPARATOR + "run.sh";
        System.out.printf("%nPreparing to execute : %s%n", rf);

        // Launch a new process ..
        ProcessBuilder builder = new ProcessBuilder()
                .command("/usr/bin/bash", "-c", rf)
                .directory(new File(config.getTargetDir())) // current working dir
                // todo redirect error to log file ...
                .inheritIO(); // redirect all IO to same as current process
        try {
            Process p = builder.start(); // Launch, and let run ...
            // p.waitFor(); // debug - synchroneous wait ...
        } catch (IOException /* | InterruptedException */ ex) {
            throw new SSException("Exception while running process", ex);
        }

    }

    /**
     * Parse the command line arguments.
     *
     * @param args
     * @return
     */
    protected static Config parseArgs(String[] args) {

        Config config = new Config();

        for (int i = 0; i < args.length; i++) {
            String command = args[i];
            switch (command) {

                case "-c":
                case "--class":
                case "--name":
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

                case "-g":
                case "--grid":
                case "--grid-url":
                case "--gridurl": {
                    i++;
                    String u = args[i];
                    config.setGridUrl(u);
                    break;
                }

                case "mongo-url": {
                    i++;
                    String u = args[i];
                    config.setMongoConnectionString(u);
                    break;
                }

                case "mongo-db": {
                    i++;
                    String u = args[i];
                    config.setMongoDbName(u);
                    break;
                }

                case "mongo-col": {
                    i++;
                    String u = args[i];
                    config.setMongoColName(u);
                    break;
                }

                case "-v":
                case "--version": {
                    printVersionInfo(config);
                    System.exit(0);
                }

                case "-d":
                case "--debug": {
                    config.setDebugMode(true);
                    break;
                }

                case "--dryrun":
                case "--dry-run": {
                    config.setDryRunFlag(true);
                    break;
                }

                case "-x":
                case "--execute":
                case "--run": {
                    config.setExecuteFlag(true);
                    break;
                }

                case "--help":
                case "-h": {
                    printHelp(config);
                    System.exit(0);
                }

                case "--firefox":
                    config.setFirefox();
                    break;

                case "--chrome":
                    config.setChrome();
                    break;

                default: {
                    System.out.printf("%n : Unrecognized command line option : %s%n", command);
                    printHelp(config);
                    return null;
                }
            }

        }

        // Adjust class name if not set but source file was set.
        if (config.getSourceFileName() != null
                && !config.getSourceFileName().isEmpty()
                && config.getTargetJavaClassDefault().equals(config.getTargetJavaClassName())) {

            String c = Paths.get(config.getSourceFileName()).getFileName().toString().replace(".ss", "");
            c = c.substring(0, 1).toUpperCase() + c.substring(1);
            config.setTargetJavaClassName(c);
            System.out.println("Classname was not specified, setting it to : " + c);
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
                "     --debug        : set debug mode to true, default is false",
                "",
                "     --dry-run",
                "     --dryrun       : compile everything, but do not save to file.",
                "                      Default is to actually save to file",
                "",
                "     -s FILE",
                "     --source FILE  : compile from specified FILE.",
                "                      Default is to read from stdin.",
                "",
                "     -x",
                "     --execute",
                "     --run          : execute immediately the compiled script.",
                "                      Default is not to run immediately.",
                "                      Execute has no effect if in --dry-run mode",
                "                      WARNING : this is LINUX specific",
                "",
                "     -g",
                "     --grid",
                "     --grid-url",
                "     --gridurl      : specify the full url to the grid.",
                "                      Default : http://localhost:4444/wd/hub",
                "",
                "     --mongo-url    : mongo connexion string (host & port).",
                "                      Default to localhost:27017",
                "     --mongo-db     : mongo database name. Default selescriptdb",
                "     --mongo-col    : mongo collection. Default selescriptcol.",
                "",
                "      --firefox",
                "      --chrome      : select browser type. Default is firefox.",
                "",
                "     -c",
                "     --class",
                "     --name",
                "     --classname CLASSNAME : generated scrapper will be named CLASSNAME.",
                "                             Default will be derived from source file if",
                "                             available, or 'NoName' if reading from stdin.",
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
