package com.twiceagain.selescript.configuration;

import com.twiceagain.selescript.exceptions.SSConfigurationException;
import com.twiceagain.selescript.exceptions.SSException;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

/**
 * Configuration information and various tools.
 *
 * @author xavier
 */
public class Config {

    /**
     * Package for target generated scrapper.
     */
    private List<String> TargetPackage = Arrays.asList(
            "com",
            "twiceagain",
            "scrapper");
    /**
     * Package for the runtime class librairy.
     */
    private static final List<String> RUNTIMEPACKAGE = Arrays.asList(
            "com",
            "twiceagain",
            "selescript",
            "runtime");

    /**
     * The default name of the generated java class.
     */
    private static final String JAVACLASSNAME_DEFAULT = "NoName";
    /**
     * Tghe configurated class name.
     */
    private String JavaClassName = JAVACLASSNAME_DEFAULT;

    private String SOURCEFILENAME = null;

    private String PARAMETERFILENAME = null;

    private boolean DEBUGMODE = false;

    static final boolean ISWINDOWS = System.getProperty("os.name")
            .toLowerCase().startsWith("windows");
    private final static String GRIDURL_DEFAULT = "http://localhost:4444/wd/hub";
    private URL gridUrl;

    private String BROWSER = "firefox";
    private String MONGOCONNECTIONSTRING = "";
    private String MONGODBNAME = "selescriptdb";
    private String MONGOCOLNAME = "selescriptcol";

    /**
     * List of required imports.
     */
    private static final List<String> TARGETIMPORTS = Arrays.asList(
            "java.util.*",
            "org.openqa.selenium.*",
            "org.openqa.selenium.firefox.*",
            "org.openqa.selenium.chrome.*",
            String.join(".", RUNTIMEPACKAGE) + ".*"
    );

    /**
     * File systems separator used.
     */
    public static final String FILESEPARATOR = FileSystems.getDefault().getSeparator();
    /**
     * New line separator used
     */
    public static final String NL = System.lineSeparator();
    public static final String AP = "\"";

    /**
     * Unique ID generation.
     */
    private transient long uid = 0L;

    private static final String SELESCRIPTVERSION = "0.5.2";
    private static final String SELENIUMVERSION = "3.14.0";
    private static final String TARGETJAVAVERSION = "10";

    private boolean DRYRUNFLAG = false;
    private boolean EXECUTEFLAG = false;

    private static String TARGETPROJECTVERSION_CACHED = null;

    private static final String TARGETRUNTIMEDIRECTORY
            = "src" + FILESEPARATOR
            + "main" + FILESEPARATOR
            + "java" + FILESEPARATOR
            + String.join(FILESEPARATOR, RUNTIMEPACKAGE) + FILESEPARATOR;

    private static final String SOURCERUNTIMEDIRECTORY
            = String.join(FILESEPARATOR, RUNTIMEPACKAGE) + FILESEPARATOR;
    /**
     * Cache builtin scan for performance reason.
     */
    private Set<String> builtinSetCached;

    public Config() {
        incTargetProjectVersion();

    }

    public Config(String className) {
        this.JavaClassName = className;
        incTargetProjectVersion();
    }

    public Config setTargetJavaClassName(String className) {
        this.JavaClassName = className;
        incTargetProjectVersion();
        return this;
    }

    public Config setExecuteFlag(boolean f) {
        EXECUTEFLAG = f;
        return this;
    }

    public Config setGridUrl(String url) {
        try {
            this.gridUrl = new URL(url);
        } catch (MalformedURLException ex) {
            throw new SSConfigurationException(
                    "Malformed url : " + url
                    + "It should look lie : " + GRIDURL_DEFAULT,
                    ex);
        }
        return this;
    }

    public URL getGridUrl() {
        if (gridUrl == null) {
            try {
                return new URL(GRIDURL_DEFAULT);
            } catch (MalformedURLException ex) {
                throw new SSConfigurationException("Could not convert GRIDURL_DEFAULT to URL ?!", ex);
            }
        }
        return gridUrl;
    }

    public boolean getExecuteFlag() {
        return EXECUTEFLAG;
    }

    /**
     * Fluent API to set the pachage as an array of String
     *
     * @param args
     * @return
     */
    public Config setTargetPackage(String... args) {
        TargetPackage = Arrays.asList(args);
        incTargetProjectVersion();
        return this;
    }

    /**
     * Set the source file to either null (means stdin) or a valid, readable
     * file name. If file name is invalid, throw exception.
     *
     * @param source
     * @return
     */
    public Config setSourceFileName(String source) {
        // Check validity
        if (source != null) {
            Path s = Paths.get(source);
            if (!Files.exists(s) || !Files.isReadable(s) || !Files.isRegularFile(s)) {
                throw new SSConfigurationException("Trying to read non valid file at :" + s.toAbsolutePath());
            } else {
                System.out.println("Reading script from " + s.toAbsolutePath());
            }
        } else {
            System.out.println("Reading script from stdin");
        }
        SOURCEFILENAME = source;
        incTargetProjectVersion();
        return this;
    }

    public String getSourceFileName() {
        return SOURCEFILENAME;
    }

    public Config setInputParameterFileName(String params) {
        PARAMETERFILENAME = params;
        incTargetProjectVersion();
        return this;
    }

    public String getInputParameterFileName() {
        return PARAMETERFILENAME;
    }

    public String getSelescriptVersion() {
        return SELESCRIPTVERSION;
    }

    public String getSeleniumVersion() {
        return SELENIUMVERSION;
    }

    public String getTargetJavaVersion() {
        return TARGETJAVAVERSION;
    }

    public Config setDryRunFlag(boolean flag) {
        DRYRUNFLAG = flag;
        return this;
    }

    public Boolean getDryRunFlag() {
        return DRYRUNFLAG;
    }

    public Config setDebugMode(boolean mode) {
        DEBUGMODE = mode;
        return this;
    }

    public Boolean getDebugMode() {
        return DEBUGMODE;
    }

    /**
     * Get the comment / licence bloc from the file header.
     *
     * @return
     */
    public String getFileHeader() {
        List<String> content = Arrays.asList(
                "===================================================",
                "Autogenerated file - DO NOT EDIT DIRECTLY",
                " (c) 2018 - Xavier Gandillot",
                " Generated on " + new Date().toString(),
                " by selescript version : " + getSelescriptVersion(),
                " target selenium version : " + getSeleniumVersion(),
                "",
                " See https://github.com/xavier268/selescript for details",
                "===================================================="
        );
        return "/*" + String.join(NL + "* ", content) + NL + "*/" + NL;

    }

    public String getPackageDeclaration() {
        return "package " + String.join(".", TargetPackage) + ";" + NL;
    }

    public String getImportsDeclarations() {
        StringBuilder sb = new StringBuilder();
        TARGETIMPORTS.forEach((s) -> {
            sb.append("import ")
                    .append(s)
                    .append(" ;")
                    .append(NL);
        });
        return sb.toString();
    }

    /**
     * Reads a UTF-8 text resource file into memory as a String.
     *
     * @param sourceName : relative to the ressource directory.
     * @return
     */
    public String getResourceAsString(String sourceName) {
        return new Scanner(Config.class.getClassLoader().getResourceAsStream(sourceName), "UTF-8")
                .useDelimiter("\\A")
                .next();
    }

    /**
     * Reads a whitespace or nl separated list of tokens, keeping only those
     * prefixed with $.
     *
     * @return
     */
    public Set<String> getBuiltinsSet() {
        if (builtinSetCached == null) {
            builtinSetCached = new TreeSet<>();
            new Scanner(Config.class.getClassLoader()
                    .getResourceAsStream("rt/builtins.list"), "UTF-8")
                    .tokens()
                    .filter((String x) -> {
                        return x.startsWith("$");
                    })
                    .forEach(builtinSetCached::add);
        }
        return builtinSetCached;
    }

    /**
     * Retrieve the target directory.
     *
     * @return
     */
    public String getTargetDir() {
        try {
            return new File("target" + FILESEPARATOR + "dist-" + getTargetJavaClassName())
                    .getCanonicalPath();

        } catch (IOException ex) {
            throw new SSException("Invalid default path : " + "target/" + getTargetJavaClassName() + "-dist");

        }
    }

    /**
     * Linux specific.
     *
     * @return
     */
    public String getTargetRunFile() {
        return getTargetDir() + FILESEPARATOR + "run.sh";
    }

    public String getTargetJavaClassName() {
        return JavaClassName;

    }

    public String getTargetJavaClassDefault() {
        return JAVACLASSNAME_DEFAULT;

    }

    /**
     * The path to the directory where the generated java class will be.
     *
     * @return
     */
    public Path getTargetJavaClassDirectory() {
        return Paths
                .get(getTargetDir(), "src", "main", "java", String
                        .join(FILESEPARATOR, TargetPackage));

    }

    public Path getTargetJavaClassRuntimeDirectory() {
        return Paths
                .get(getTargetDir(),
                        "src",
                        "main",
                        "java",
                        "com",
                        "twiceagain",
                        "selescript",
                        "runtime");

    }

    /**
     * The path to the default generated java class file.
     *
     * @return
     */
    public Path getTargetJavaClassPath() {
        return Paths
                .get(getTargetJavaClassDirectory().toString(),
                        getTargetJavaClassName() + ".java");
    }

    protected void copyFromSourceDirToTargetDir(String source, String target) {
        new File(getTargetDir()).mkdirs();
        Path to = Paths.get(getTargetDir(), target);
        Path from = Paths.get(source);
        try {
            Files.copy(from, to, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {
            throw new SSConfigurationException(
                    "Could not copy from " + from.toAbsolutePath()
                    + " to " + to.toAbsolutePath(), ex);
        }
    }

    /**
     * Copy the file from the ressources to the target directory.
     *
     * @param source - relative to the root of the resources directory.
     * @param target - relative to the TARGETDIRECTORY
     */
    protected void copyFromResourceToTargetDir(String source, String target) {

        new File(getTargetDir()).mkdirs();
        BufferedInputStream is = null;
        BufferedOutputStream os = null;

        try {
            is = new BufferedInputStream(
                    Config.class
                            .getClassLoader()
                            .getResourceAsStream(source));

            if (is == null) {
                throw new SSException(
                        "Could not open requested resources : " + source);

            }
            os = new BufferedOutputStream(
                    new FileOutputStream(getTargetDir() + "/" + target));

            byte[] readBytes = new byte[1024 * 8];
            int read;
            while ((read = is.read(readBytes)) != -1) {
                os.write(readBytes, 0, read);
            }

        } catch (IOException ex) {
            throw new SSException(
                    "IOException copying from  " + source
                    + " to " + target,
                    ex);

        } finally {
            try {
                if (is != null) {
                    is.close();
                }
                if (os != null) {
                    os.close();
                }
            } catch (IOException ex) {
                throw new SSException(ex);
            }
        }

    }

    /**
     * Are we running on windows ?
     *
     * @return
     */
    public boolean isWindows() {
        return ISWINDOWS;
    }

    /**
     * Are we running on Linux ? Assume non windows means Linux ;-)
     *
     * @return
     */
    public boolean isLinux() {
        return !isWindows();
    }

    /**
     * Create the run files to launch the java executable.
     *
     * @TODO create a .bat idf needed for windows ?
     */
    protected void createRunFiles() {
        String r = "#!/bin/bash"
                + NL
                + "JAR=" + getTargetProjetExecutableJarName()
                + NL
                + getResourceAsString("rt/run.part.sh");

        Path p = Paths
                .get(getTargetDir(), "run.sh");

        try {

            Files.write(p, r.getBytes("UTF-8"));

        } catch (IOException ex) {

            throw new SSException(ex);

        }
    }

    /**
     * Create the target pom.xml file in the target directory.
     */
    protected void createPomFile() {
        // Creating the pom content
        List<String> pom = Arrays.asList(
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>",
                "<project xmlns=\"http://maven.apache.org/POM/4.0.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd\">",
                "<modelVersion>4.0.0</modelVersion>",
                "",
                "<groupId>com.twiceagain.selescript.autogenerated.scrappers</groupId>",
                "<artifactId>" + getTargetProjectName() + "</artifactId>",
                "<version>" + getTargetProjectVersion() + "</version>",
                "<name>" + getTargetProjectName() + "</name>",
                "<url>https://github.com/xavier268/selescript</url>",
                "<packaging>jar</packaging>",
                "<properties>",
                "         <project.build.sourceEncoding> UTF-8</project.build.sourceEncoding>",
                "         <java.version>" + getTargetJavaVersion() + "</java.version>",
                "         <mainClass>" + String
                        .join(".", TargetPackage
                        ) + "." + getTargetJavaClassName() + "</mainClass>",
                "         <buildtime>" + new Date().toString() + "</buildtime>",
                "         <copyright>(c) 2018 Xavier Gandillot</copyright>",
                "</properties>",
                NL,
                NL
        );

        // Add the remaining part of the pom file
        String poms = String
                .join(NL, pom) + getResourceAsString("rt/pom.part.xml");

        // Now, lets save to pom.xml
        Path p = Paths.get(getTargetDir(), "pom.xml");

        try {

            Files.write(p, poms.getBytes("UTF-8"));

        } catch (IOException ex) {

            throw new SSException(ex);

        }
    }

    /**
     * Will create all the files, including java generic runtime classes, but
     * excluding the generated java code. Called by the saveCode method.
     */
    protected void createAllRuntimeSupportFiles() {

        // Copy relevant resources
        copyFromResourceToTargetDir("rt/README.txt", "README.txt");
        copyFromResourceToTargetDir("rt/LICENSE.txt", "LICENSE.txt");

        // Copy useful scripts
        copyFromSourceDirToTargetDir("selgrid.yaml", "selgrid.yaml");
        copyFromSourceDirToTargetDir("selgrid.start.sh", "selgrid.start.sh");
        copyFromSourceDirToTargetDir("selgrid.stop.sh", "selgrid.stop.sh");

        //copy source file to tagert dir (for reference & debugging - not used)
        if (getSourceFileName() != null) {
            String target = Paths.get(getSourceFileName()).getFileName().toString();
            copyFromSourceDirToTargetDir(getSourceFileName(), target);
        }

        // Copy runtime librairy classes 
        copyRuntimeJavaClass("Base");
        copyRuntimeJavaClass("Methods");
        copyRuntimeJavaClass("Scrapper");
        copyRuntimeJavaClass("FrameStack");
        copyRuntimeJavaClass("Frame");

        // Builtins
        copyRuntimeJavaClass("BaseVariable");
        getBuiltinsSet().forEach((s) -> {
            copyRuntimeJavaClass(s);
        });

        // Create custom files
        createPomFile();
        createRunFiles();

    }

    private void copyRuntimeJavaClass(String className
    ) {
        copyFromResourceToTargetDir(SOURCERUNTIMEDIRECTORY
                + className
                + ".java",
                TARGETRUNTIMEDIRECTORY
                + className
                + ".java");

    }

    /**
     * Save code to a java class in the default package location.
     *
     * @param code - the String reprensenting the entire code to be saved.
     */
    public void saveCode(String code) {

        if (getDryRunFlag()) {
            System.out
                    .printf(
                            "%n**** You are running in  DRYRUN mode ****"
                            + "%nNothing will be saved ...%n");

            return;
        }

        getTargetJavaClassDirectory().toFile().mkdirs();
        getTargetJavaClassRuntimeDirectory().toFile().mkdirs();

        createAllRuntimeSupportFiles();

        Path p = getTargetJavaClassPath();

        try {

            Files.write(p, code.getBytes("UTF-8"));

        } catch (IOException ex) {

            throw new SSException(ex);

        }

    }

    public void saveCode(StringBuilder code) {
        saveCode(code.toString());

    }

    /**
     * Generates a unique, valid java identifier.
     *
     * @return
     */
    public String createUniqueId() {
        uid++;
        return String.format("uid_%d", uid);

    }

    /**
     * Autogenerated project version. Based on actual time, it will only changed
     * when incremented. There is excatly one target version per Config object
     * created. They are garanteed to be ordered by time of creation
     * (milliseconds)
     *
     * @return
     */
    public String getTargetProjectVersion() {
        if (TARGETPROJECTVERSION_CACHED == null) {
            incTargetProjectVersion();
        }
        return TARGETPROJECTVERSION_CACHED;

    }

    private void incTargetProjectVersion() {
        TARGETPROJECTVERSION_CACHED
                = ""
                + System.currentTimeMillis()
                + "-SNAPSHOT";

    }

    public String getTargetProjectName() {
        return "scrapper-" + getTargetJavaClassName();

    }

    public String getTargetProjetExecutableJarName() {
        return getTargetProjectName()
                + "-"
                + getTargetProjectVersion()
                + "-jar-with-dependencies.jar";

    }

    public String getMd5Hash(String s) {

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(s.getBytes(Charset.forName("UTF-8")));

            byte[] d = md.digest();
            StringBuilder sb = new StringBuilder();

            for (byte b : d) {
                sb
                        .append(String
                                .format("%X", b
                                ));

            }
            return sb.toString();

        } catch (NoSuchAlgorithmException ex) {
            System.err.printf("%nErreor while computing MD5 hash for %s", s
            );

            throw new SSException(ex);

        }

    }

    /**
     * Will display detailled info about the configuration.
     *
     * @return
     */
    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb
                .append("DEBUGMODE : ").append(DEBUGMODE).append(NL)
                .append("BROWSER : ").append(BROWSER).append(NL)
                .append("MONGOCONNECTIONSTRING : ").append(MONGOCONNECTIONSTRING).append(NL)
                .append("MONGODBNAME : ").append(MONGODBNAME).append(NL)
                .append("MONGOCOLNAME : ").append(MONGOCOLNAME).append(NL)
                .append("DRYRUNFLAG : ").append(DRYRUNFLAG).append(NL)
                .append("GRIDURL_DEFAULT : ").append(GRIDURL_DEFAULT).append(NL)
                .append("gridUrl : ").append(gridUrl).append(NL)
                .append("EXECUTEFLAG : ").append(EXECUTEFLAG).append(NL)
                .append("FILESEPARATOR : ").append(FILESEPARATOR).append(NL)
                .append("JavaClassName : ").append(JavaClassName).append(NL)
                .append("PARAMETERFILENAME : ").append(PARAMETERFILENAME).append(NL)
                .append("RUNTIMEPACKAGE : ").append(RUNTIMEPACKAGE).append(NL)
                .append("SELENIUMVERSION : ").append(SELENIUMVERSION).append(NL)
                .append("SELESCRIPTVERSION : ").append(SELESCRIPTVERSION).append(NL)
                .append("SOURCEFILENAME : ").append(SOURCEFILENAME).append(NL)
                .append("SOURCERUNTIMEDIRECTORY : ").append(SOURCERUNTIMEDIRECTORY).append(NL)
                .append("TARGETIMPORTS : ").append(TARGETIMPORTS).append(NL)
                .append("TARGETJAVAVERSION : ").append(TARGETJAVAVERSION).append(NL)
                .append("TARGETPROJECTVERSION_CACHED : ").append(TARGETPROJECTVERSION_CACHED).append(NL)
                .append("TARGETRUNTIMEDIRECTORY : ").append(TARGETRUNTIMEDIRECTORY).append(NL)
                .append("TargetPackage : ").append(TargetPackage).append(NL)
                .append("uid : ").append(uid).append(NL)
                .append("getBuiltinsSet : ").append(getBuiltinsSet()).append(NL)
                .append("getGridUrl : ").append(getGridUrl()).append(NL)
                .append("getDebugMode : ").append(getDebugMode()).append(NL)
                .append("getDryRunFlag : ").append(getDryRunFlag()).append(NL)
                .append("getExecuteFlag : ").append(getExecuteFlag()).append(NL)
                .append("getFileHeader : ").append(getFileHeader()).append(NL)
                .append("getImportsDeclarations : ").append(getImportsDeclarations()).append(NL)
                .append("getInputParameterFileName : ").append(getInputParameterFileName()).append(NL)
                .append("getPackageDeclaration : ").append(getPackageDeclaration()).append(NL)
                .append("getSeleniumVersion : ").append(getSeleniumVersion()).append(NL)
                .append("getSelescriptVersion : ").append(getSelescriptVersion()).append(NL)
                .append("getSourceFileName : ").append(getSourceFileName()).append(NL)
                .append("getTargetDir : ").append(getTargetDir()).append(NL)
                .append("gettargetJavaClassDirectory : ").append(getTargetJavaClassDirectory()).append(NL)
                .append("getTargetJavaClassName : ").append(getTargetJavaClassName()).append(NL)
                .append("getTargetJavaClassPath : ").append(getTargetJavaClassPath()).append(NL)
                .append("getTargetJavaClassRuntimeDirectory : ").append(getTargetJavaClassRuntimeDirectory()).append(NL)
                .append("getTargetJavaVersion : ").append(getTargetJavaVersion()).append(NL)
                .append("getTargetRunFile : ").append(getTargetRunFile()).append(NL)
                .append("getTargetProjectName : ").append(getTargetProjectName()).append(NL)
                .append("gettargetProjectVersion : ").append(getTargetProjectVersion()).append(NL)
                .append("getTargetProjectExecutableJarName : ").append(getTargetProjetExecutableJarName()).append(NL)
                .append("getConstantDeclarations : ").append(getConstantDeclarations()).append(NL)
                .append(NL);

        return sb.toString();

    }

    public void dump() {
        System.out
                .printf(""
                        + "%n====================================="
                        + "%n===  Detailled configuration dump ==="
                        + "%n====================================="
                        + "%n%s"
                        + "%n====================================="
                        + "%n===   end of configuration dump  ===="
                        + "%n====================================="
                        + "%n", toString());

    }

    /**
     * Return the code that declares various useful constants.
     *
     * @return
     */
    public String getConstantDeclarations() {
        // define useful constants
        return new StringBuilder()
                .append("public final static String VERSION = \"").append(getSelescriptVersion()).append("\";").append(NL)
                .append("public final static String SELENIUMVERSION = \"").append(getSeleniumVersion()).append("\";").append(NL)
                .append("public final static String BUILDDATE = \"").append(new Date()).append("\";").append(NL)
                .append("public final static String BUILDMILLIS = \"").append(System.currentTimeMillis()).append("\";").append(NL)
                .append("private final static Class CLASS = ").append(getTargetJavaClassName()).append(".class;").append(NL)
                .append(NL)
                .append("@Override").append(NL)
                .append("public String getGridUrl() { return ").append(AP).append(getGridUrl()).append(AP).append(";}").append(NL)
                .append(NL)
                .append("@Override").append(NL)
                .append("public String getMongoConnectionString() { return ").append(AP).append(MONGOCONNECTIONSTRING).append(AP).append(";}").append(NL)
                .append(NL)
                .append("@Override").append(NL)
                .append("public String getMongoDbName() { return ").append(AP).append(MONGODBNAME).append(AP).append(";}").append(NL)
                .append(NL)
                .append("@Override").append(NL)
                .append("public String getMongoColName() { return ").append(AP).append(MONGOCOLNAME).append(AP).append(";}").append(NL)
                .append(NL)
                .append(getBrowserCapabilitiesDeclaration()).append(NL)
                .append("public static final String $$NULL = null;")
                .append(NL)
                .toString();

    }

    /**
     * Utility to list all files in a given directory.
     *
     * @param directory - relative to the calling program, or the pom location.
     * @return - the list comprising only the filenames and their extensions.
     */
    public static List<String> listFilesFromDirectory(String directory
    ) {
        List<String> fileNames
                = new ArrayList<>();

        try (DirectoryStream<Path> directoryStream
                = Files
                        .newDirectoryStream(Paths
                                .get(directory
                                ))) {
                    for (Path path
                            : directoryStream) {
                        // Ignore directories and special files.
                        if (Files
                                .isRegularFile(path
                                )) {
                            fileNames
                                    .add(path
                                            .getFileName().toString());

                        }
                    }
                } catch (IOException ex) {
                    throw new SSConfigurationException("Could not list the files in " + directory,
                            ex
                    );

                }
                return fileNames;

    }

    /**
     * Generates an object for each builtin variable.
     *
     * @return
     */
    public String getBuiltinsDeclarations() {
        final StringBuilder sb = new StringBuilder(NL);

        getBuiltinsSet().forEach((b) -> {
            sb
                    .append(b)
                    .append(" _") // object is callsname prefixed with _
                    .append(b)
                    .append("= new ")
                    .append(b)
                    .append("(fs);")
                    .append(NL);
        });
        return sb.append(NL).toString();
    }

    public String getBrowserCapabilitiesDeclaration() {
        StringBuilder sb = new StringBuilder("@Override")
                .append(NL)
                .append("public Capabilities getBrowserCapabilities() { return ");
        switch (BROWSER) {
            case "firefox":
                sb.append("new FirefoxOptions()");
                break;
            case "chrome":
                sb.append("new ChromeOptions()");
                break;
            default:
                throw new SSConfigurationException("Unknown browser type : " + BROWSER);
        }
        sb.append(" ; }").append(NL);
        return sb.toString();
    }

    public Config setFirefox() {
        BROWSER = "firefox";
        return this;
    }

    public Config setChrome() {
        BROWSER = "chrome";
        return this;
    }

    public Config setMongoColName(String u) {
        MONGOCOLNAME = u;
        return this;
    }

    public Config setMongoDbName(String u) {
        MONGODBNAME = u;
        return this;
    }

    public Config setMongoConnectionString(String u) {
        MONGOCONNECTIONSTRING = u;
        return this;
    }

}
