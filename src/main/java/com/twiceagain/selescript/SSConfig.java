package com.twiceagain.selescript;

import java.nio.file.Paths;
import java.util.Arrays;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;

/**
 * Configuration information and related utilities. These information are
 * supposed to remain fixed when the program is running. Live informations can
 * be found in the RuntimeContext object.
 *
 * @author xavier
 */
public class SSConfig {

    static final String NL = System.lineSeparator();

    // Read-write
    private String gridUrl = "http://localhost:4444/wd/hub";
    private String browser = "firefox";

    private String mongoConnexionString = "mongodb://localhost:27017";
    private String mongoDatabase = "ssdb";
    private String mongoCollection = "sscol";

    private String scriptFileName = null;
    private String inputFileName = null;
    private String outputFileName = null;
    private boolean debug = false;

    // Read only
    private final String notice = "Selescript - (c) Xavier Gandillot 2018"
            + NL
            + "See https://xavier268.github.io/selescript/";
    private final String selescriptVersion = "1.2.2";
    private final String javaVersion = Runtime.version().toString();
    private final Long startMillis = System.currentTimeMillis();
    
    // Uiuqe ids.
    private static long uid = 0;

    public String getGridUrl() {
        return gridUrl;
    }

    public void setGridUrl(String gridUrl) {
        this.gridUrl = gridUrl;
    }

    public void setBrowserFirefox() {
        this.browser = "firefox";
    }

    public void setBrowserChrome() {
        this.browser = "chrome";
    }

    public Capabilities getBrowserOptions() {
        if ("firefox".equals(browser)) {
            return new FirefoxOptions();
        }
        return new ChromeOptions();
    }

    public String getMongoConnexionString() {
        return mongoConnexionString;
    }

    public String getInputFileName() {
        return inputFileName;
    }

    public void setInputFileName(String inputFileName) {
        this.inputFileName = inputFileName;
    }

    public void setMongoConnexionString(String mongoConnexionString) {
        this.mongoConnexionString = mongoConnexionString;
    }

    public String getMongoDatabase() {
        return mongoDatabase;
    }

    public void setMongoDatabase(String mongoDatabase) {
        this.mongoDatabase = mongoDatabase;
    }

    public String getMongoCollection() {
        return mongoCollection;
    }

    public void setMongoCollection(String mongoCollection) {
        this.mongoCollection = mongoCollection;
    }

    public String getScriptFileName() {
        return scriptFileName;
    }

    public void setScriptFileName(String scriptFileName) {
        this.scriptFileName = scriptFileName;
    }

    public String getSelescriptVersion() {
        return selescriptVersion;
    }

    public String getJavaVersion() {
        return javaVersion;
    }

    public Long getStartMillis() {
        return startMillis;
    }

    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public String getBrowser() {
        return browser;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append(NL)
                .append(getNotice()).append(NL)
                .append("Selescript version : ").append(getSelescriptVersion()).append(NL)
                .append("Java version : ").append(getJavaVersion()).append(NL)
                .append("Using : ").append(getBrowser()).append(NL)
                .append("Grid url : ").append(getGridUrl()).append(NL)
                .append("Mongo url : ").append(getMongoConnexionString()).append(NL)
                .append("Mongo db : ").append(getMongoDatabase()).append(NL)
                .append("Mongo col : ").append(getMongoCollection()).append(NL)
                .append("Script file : ").append(getAbsoluteScriptFileName()).append(NL)
                .append("Input file : ").append(getInputFileName()).append(NL)
                .append("Output file : ").append(getOutputFileName()).append(NL)
                .append("Debug mode : ").append(isDebug()).append(NL)
                .append(NL).toString();

    }

    public String getNotice() {
        return notice;
    }

    private String getAbsoluteScriptFileName() {
        String f = getScriptFileName();
        if (f == null) {
            return "no file script was set";
        }
        return Paths.get(getScriptFileName()).toAbsolutePath().toString();
    }

    public static SSConfig parseArgs(String[] args) {

        SSConfig config = new SSConfig();
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-d":
                case "--debug":
                    config.setDebug(true);
                    break;
                case "--firefox":
                    config.setBrowserFirefox();
                    break;
                case "--chrome":
                    config.setBrowserChrome();
                    break;
                case "--grid":
                    i++;
                    config.setGridUrl(args[i]);
                    break;
                case "--mongo":
                    i++;
                    config.setMongoConnexionString(args[i]);
                    break;
                case "--db":
                    i++;
                    config.setMongoDatabase(args[i]);
                    break;
                case "--col":
                    i++;
                    config.setMongoCollection(args[i]);
                    break;
                case "-s":
                    i++;
                    config.setScriptFileName(args[i]);
                    break;
                case "-i":
                    i++;
                    config.setInputFileName(args[i]);
                    break;                    
               case "-o":
                    i++;
                    config.setOutputFileName(args[i]);
                    break;                    
                case "-h":
                case "--help":
                    config.printHelp();
                    return null;                    
                default:
                    System.out.println(
                            "Command line arguments not recognized : " 
                            + args[i] 
                            + " in " 
                            + Arrays.toString(args));
                    config.printHelp();
                    return null;

            }
        }

        return config;
    }

    public void printHelp() {

        System.out.println(toString());

        System.out.println(String.join(NL, new String[]{
            "Recognized command lines options :",
            "",
            "-s FILE            : specify the FILE to run",
            "",
            "-i FILE            : requested input will be read from FILE",
            "",
            "-o FILE            : requested output (with $write) will be sent to FILE",
            "",
            " -d",
            "--debug            : set the debug mode (more verbose)",
            "",
            "--firefox          : use firefox (default)",
            "--chrome           : use chrome",
            "",
            "--grid URL         : set the Grid Url to use.",
            "",
            "--mongo    URL     : set the mongo connexion string to URL",
            "--db       DB      : set the mongo database to DB",
            "--col      COL     : set the mongo collection to COL",
            "",
            " -h",
            "--help             : print these messages and exit",
            ""}));

    }

    public String getOutputFileName() {
        return outputFileName;
    }

    public void setOutputFileName(String outputFileName) {
        this.outputFileName = outputFileName;
    }

    public String getUid() {
    uid++;
    return "" + uid;
    }

}
