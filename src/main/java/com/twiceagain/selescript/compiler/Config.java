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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Configuration information.
 *
 * @author xavier
 */
public class Config {

    protected static File TARGETDIR = new File("./target/scrapper-dist/");

    public static String getVersion() {
        return "1.1";
    }

    public static String getHeaders() {
        return new Scanner(Config.class.getClassLoader().getResourceAsStream("runtime/headers"), "UTF-8")
                .useDelimiter("\\A")
                .next();
    }

    public static String getBuiltinsMethods() {
        return new Scanner(Config.class.getClassLoader().getResourceAsStream("runtime/builtins"), "UTF-8")
                .useDelimiter("\\A")
                .next();
    }

    public static Collection<String> getBuiltinsList() {
        Set<String> rr = new HashSet<>();
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

    /**
     * Copy the file from the ressources to the target directory.
     *
     * @param source
     * @param target
     */
    protected static void copyFromResource(String source, String target) {
        
        new File(getTargetDir()).mkdirs();

        BufferedInputStream is = null;
        BufferedOutputStream os = null;

        try {
            is = new BufferedInputStream(Config.class
                    .getClassLoader()
                    .getResourceAsStream("runtime/" + source));

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

        copyFromResource("pom.xml", "pom.xml");
        copyFromResource("run.sh", "run.sh");

    }
}
