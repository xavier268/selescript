/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twiceagain.selescript.compiler;

import com.twiceagain.selescript.configuration.Config;
import com.twiceagain.selescript.exceptions.SSException;
import org.junit.Test;

/**
 * test individual fragments (statements) compilation.
 *
 * @author xavier
 */
public class FragementCompilationTest {

    static Config config = new Config().setDryRunFlag(true).setDebugMode(false);

    @Test
    public void basic() {
        System.out.println("\n********* Basic Arithmetic and concatenation");
        t("          123;");
        t("      \"abc\";");
        t("        1 + 3;");
        t(" \"\" + 1 + 3;");
        t(" \"\" + 1 * 3;");
    }

    @Test
    public void testAt() {
        System.out.println("\n********* Dereferencing)");
        t("@       two ;");
        t("@     : two ;");
        t("@ one : two ;");
        t("@ one :     ;");
        t("@           ;");
    }

    /**
     * Generic test utility.
     *
     * @param s - source
     */
    public void t(String s) {
        SSCompiler comp = new SSCompiler(config, s);
        String c = comp.getCode();
        c = c.split("do \\{", 2)[1]; // after do {
        c = c.split("\\} while", 2)[0]; // before while
        c = c.replaceAll("\n\r", " ").trim(); // remove new lines
        System.out.printf("%s\t===>\t%s\t<===%n", s, c);
    }

}
