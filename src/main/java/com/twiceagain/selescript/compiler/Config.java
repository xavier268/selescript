/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twiceagain.selescript.compiler;

import com.twiceagain.selescript.compiler.exceptions.SSException;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Configuration information.
 *
 * @author xavier
 */
public class Config {

    /**
     * Target directory where the generated scrapper project will be put
     */
    protected static File TARGETDIR = new File("./target/scrapper-dist/");
    /**
     * Package for target generated scrapper.
     */
    protected static final List<String> TARGETPACKAGE = Arrays.asList("com", "twiceagain", "scrapper");
    /**
     * The name of the generated java class.
     */
    protected static final String JAVACLASSNAME = "AutoScrapper";
    /**
     * List of required imports.
     */
    protected static final List<String> TARGETIMPORTS = Arrays.asList(
            "java.util.*",
            "org.openqa.selenium",
            "org.openqa.selenium.remote.*");
    /**
     * File systems separator used.
     */
    public static final String FILESEPARATOR = FileSystems.getDefault().getSeparator();
    /**
     * New line separator used
     */
    public static final String NL = System.lineSeparator();

    public static String getVersion() {
        return "1.2";
    }

    public static String getPackageDeclaration() {
        return "package " + String.join(".", TARGETPACKAGE) + NL;
    }

    public static String getImportsDeclarations() {
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
    public static String getResourceAsString(String sourceName) {
        return new Scanner(Config.class.getClassLoader().getResourceAsStream(sourceName), "UTF-8")
                .useDelimiter("\\A")
                .next();
    }

    public static String getBuiltinsMethods() {
        return getResourceAsString("runtime/builtins");
    }

    /**
     * Reads a whitespace or nl separated list of tokens, keeping only those
     * prefixed with $.
     *
     * @return
     */
    public static Collection<String> getBuiltinsList() {
        SortedSet<String> rr = new TreeSet<>();
        new Scanner(Config.class.getClassLoader().getResourceAsStream("runtime/builtins.list"), "UTF-8")
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
    public static String getTargetDir() {
        try {
            return TARGETDIR.getCanonicalPath();
        } catch (IOException ex) {
            throw new SSException("Invalid default path : " + TARGETDIR.toString());
        }
    }

    public static final String getTargetJavaClassName() {
        return JAVACLASSNAME;
    }

    /**
     * The path to the directory where the generated java class will be.
     *
     * @return
     */
    public static Path getTargetJavaClassDirectory() {
        return Paths.get(getTargetDir(), "src", "main", "java", String.join(FILESEPARATOR, TARGETPACKAGE));
    }

    /**
     * The path to the default generated java class file.
     *
     * @return
     */
    public static Path getTargetJavaClassPath() {
        return getTargetJavaClassPath(getTargetJavaClassName());
    }

    /**
     * The path to any class name of the default package.
     *
     * @param className
     * @return
     */
    public static Path getTargetJavaClassPath(String className) {
        return Paths.get(getTargetJavaClassDirectory().toString(), className + ".java");

    }

    /**
     * Copy the file from the ressources to the target directory.
     *
     * @param source - relative to the root of the resources directory.
     * @param target - relative to the TARGETDIRECTORY
     */
    protected static void copyFromResource(String source, String target) {

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
                            Config.getTargetDir()
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

    public static void copyRuntimeFiles() {

        copyFromResource("runtime/pom.xml", "pom.xml");
        copyFromResource("runtime/run.sh", "run.sh");

    }

    /**
     * Save code to a java class in the default package location.
     *
     * @param code - the String reprensenting the entire code to be saved.
     * @param className - the className
     */
    public  static  void saveCode (String code, String className) {

        getTargetJavaClassDirectory().toFile().mkdirs();

        Config.copyRuntimeFiles();

        Path p = getTargetJavaClassPath(className);
        try {
            Files.write(p, code.toString().getBytes("UTF-8"));
        } catch (IOException ex) {
            throw new SSException(ex);
        }

    }

    public  static void saveCode(String code) {
        saveCode(code, getTargetJavaClassName());
    }
}
