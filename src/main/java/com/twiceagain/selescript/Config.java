/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twiceagain.selescript;

import com.twiceagain.selescript.exceptions.SSException;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.SortedSet;
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
    private List<String> TargetPackage = Arrays.asList("com", "twiceagain", "scrapper");
    /**
     * The name of the generated java class.
     */
    private String JavaClassName = "AutoScrapper";

    private String SOURCEFILENAME = null;
    private String PARAMETERFILENAME = null;

    private boolean DEBUGMODE = true;

    /**
     * List of required imports.
     */
    private static final List<String> TARGETIMPORTS = Arrays.asList(
            "java.util.*",
            "java.util.concurrent.*",
            "org.openqa.selenium.*",
            "org.openqa.selenium.remote.*",
            "org.openqa.selenium.support.ui.*",
            "org.slf4j.*"
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
    private transient static long UID = 0L;

    private static final String SELESCRIPTVERSION = "0.3.1";
    private static final String SELENIUMVERSION = "3.14.0";
    private static final String TARGETJAVAVERSION = "10";

    private boolean DRYRUNFLAG = false;

    private static String TARGETPROJECTVERSION_CACHED = null;

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

    public Config setSourceFileName(String source) {
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
        List<String> content = Arrays.asList("===================================================",
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

    public String getBuiltinCode() {
        return getResourceAsString("rt/builtins.methods")
                + getResourceAsString("rt/builtins.variables");
    }

    /**
     * Reads a whitespace or nl separated list of tokens, keeping only those
     * prefixed with $.
     *
     * @return
     */
    public Collection<String> getBuiltinsList() {
        SortedSet<String> rr = new TreeSet<>();
        new Scanner(Config.class.getClassLoader().getResourceAsStream("rt/builtins.list"), "UTF-8")
                .tokens()
                .filter((String x) -> {
                    return x.startsWith("$");
                })
                .forEach(rr::add);
        return rr;

    }

    /**
     * Retrieve the target directory.
     *
     * @return
     */
    public String getTargetDir() {
        try {
            return new File("target/" + getTargetJavaClassName() + "-dist").getCanonicalPath();
        } catch (IOException ex) {
            throw new SSException("Invalid default path : " + "target/" + getTargetJavaClassName() + "-dist");
        }
    }

    public String getTargetJavaClassName() {
        return JavaClassName;
    }

    /**
     * The path to the directory where the generated java class will be.
     *
     * @return
     */
    public Path getTargetJavaClassDirectory() {
        return Paths.get(getTargetDir(), "src", "main", "java", String.join(FILESEPARATOR, TargetPackage));
    }

    /**
     * The path to the default generated java class file.
     *
     * @return
     */
    public Path getTargetJavaClassPath() {
        return Paths.get(getTargetJavaClassDirectory().toString(), getTargetJavaClassName() + ".java");
    }

    /**
     * Copy the file from the ressources to the target directory.
     *
     * @param source - relative to the root of the resources directory.
     * @param target - relative to the TARGETDIRECTORY
     */
    protected void copyFromResource(String source, String target) {

        new File(getTargetDir()).mkdirs();

        BufferedInputStream is = null;
        BufferedOutputStream os = null;

        try {
            is = new BufferedInputStream(Config.class
                    .getClassLoader()
                    .getResourceAsStream(source));

            if (is == null) {
                throw new SSException("Could not open requestde resources : " + source);
            }
            os = new BufferedOutputStream(
                    new FileOutputStream(
                            getTargetDir()
                            + "/" + target));

            byte[] readBytes = new byte[1024 * 8];
            int read;

            while ((read = is.read(readBytes)) != -1) {
                os.write(readBytes, 0, read);

            }

        } catch (IOException ex) {
            throw new SSException("IOException copying from  " + source + " to " + target, ex);
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

        Path p = Paths.get(getTargetDir(), "run.sh");
        try {
            Files.write(p, r.getBytes("UTF-8")
            );
        } catch (IOException ex) {
            throw new SSException(ex);
        }
    }

    /**
     * Create the target pom.xml file in the target directory.
     */
    protected void createPomFile() {
        // Creating the pom content
        List<String> pom = Arrays.asList("<?xml version=\"1.0\" encoding=\"UTF-8\"?>",
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
                "         <mainClass>" + String.join(".", TargetPackage) + "." + getTargetJavaClassName() + "</mainClass>",
                "         <buildtime>" + new Date().toString() + "</buildtime>",
                "         <copyright>(c) 2018 Xavier Gandillot</copyright>",
                "</properties>",
                NL, NL
        );

        // Add the remaining part of the pom file
        String poms = String.join(NL, pom) + getResourceAsString("rt/pom.part.xml");

        // Now, lets save to pom.xml
        Path p = Paths.get(getTargetDir(), "pom.xml");
        try {
            Files.write(p, poms.getBytes("UTF-8")
            );
        } catch (IOException ex) {
            throw new SSException(ex);
        }
    }

    /**
     * Will create all the files, execpt for the java code. Called by the
     * saveCode method.
     */
    protected void createAllRuntimeSupportFiles() {

        // Copy relevant resources
        copyFromResource("rt/selgrid.yaml", "selgrid.yaml");
        copyFromResource("rt/selgrid.start.sh", "selgrid.start.sh");
        copyFromResource("rt/selgrid.stop.sh", "selgrid.stop.sh");
        copyFromResource("rt/README.txt", "README.txt");

        // Create custom files
        createPomFile();
        createRunFiles();

    }

    /**
     * Save code to a java class in the default package location.
     *
     * @param code - the String reprensenting the entire code to be saved.
     */
    public void saveCode(String code) {

        getTargetJavaClassDirectory().toFile().mkdirs();
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
        UID++;
        return String.format("uid_%d", UID);
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
        TARGETPROJECTVERSION_CACHED = ""
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
                sb.append(String.format("%X", b));
            }
            return sb.toString();

        } catch (NoSuchAlgorithmException ex) {
            System.err.printf("%nErreor while computing MD5 hash for %s", s);
            throw new SSException(ex);
        }

    }

}
