/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twiceagain.selescript.compiler;

import com.twiceagain.selescript.configuration.Config;
import org.junit.Test;

/**
 * test individual fragments (statements) compilation.
 *
 * @author xavier
 */
public class FragmentCompilationTest {

    static Config config = new Config().setDryRunFlag(true).setDebugMode(false);

    @Test
    public void basic() {
        System.out.println("\n********* Basic Arithmetic and concatenation");
        ok("          123;");
        ok("      \"abc\";");
        ok("        1 + 3;");
        ok(" \"\" + 1 + 3;");
        ok(" \"\" + 1 * 3;");
    }

    @Test
    public void testAt() {
        System.out.println("\n********* Dereferencing");
        ok("@       two ;");
        ok("@     : two ;");
        ok("@ one : two ;");
        ok("@ one :     ;");
        ok("@           ;");
    }

    @Test
    public void testComments1() {
        System.out.println("\n********* Comments /**/ style");
        ok("go  { }                      ");
        ok("go  { /* khjkhk kjhk j  */ } ");
        ok("go /* hh */  { }             ");
        ok("/* ////***/go  {}            ");
    }

    @Test
    public void testComment2() {
        System.out.println("\n********* Comments // style");
        ok("45 ; // comment ; \n 11 ;                               ");
        ok("46 ; \"// not a comment\" ; // comment ; \n 11 ;        ");
        ok("47 ; \"// not a \n \r comment\" ; // comment ; \n 11 ;  ");
        ok("48 ; // \" string in comment \r                         ");
        ok("49 ; // \" string in comment \n                         ");

    }

    @Test
    public void testConstants() {
        System.out.println("\n********* Constants");

        ok("5;");
        ok("1+2;");
        ok("-(10);");
        ok("-(10 + 2);");
        ok("-11;");
        ok("1- 3;");
        ok("1-3;");
        ok("4--5;");
        ok("4-------5;");
        ok("6+-7;");
        ok("6/7;");
        ok("6/-7;");
        ok("-16/7;");
        ok("16/7;");
        ok("16/-7;");
        ok("-16/7;");
        ok("-16/-7;");
        ok("8+-9;");

        ok("  \"aaa\"           ;");
        ok("  \"aaa\" +2   ;");
        ok("  \"aaa\" + 2 + 1   ;");
        ok("  \"aaa\" + 2 * 3  ;");
        ok("  ! \"aaa\"    ;");
        ok("  \"aaa\"    ;");
        ok("  3 + \"aaa\"    ;");
        
        ok("'iuy';");
        ok("'i\"uy';");
        ok("\"i'uy\";");
        

    }

    @Test
    public void tesStringValEq() {
        System.out.println("\n********* Equals - not equal");

        ok(" toto == tata ;         ");
        ok(" toto == 22 ;           ");
        ok(" 22 == tata ;           ");
        ok(" 23 == -22 ;            ");
        ok(" 12 == 12 ;             ");

        ok(" toto != tata ;         ");
        ok(" toto != 22 ;           ");
        ok(" 22 != tata ;           ");
        ok(" 23 != -22 ;            ");
        ok(" 12 != 12 ;             ");

        ok(" titi = 45 == 45 ;      ");
        ok(" titi = (45 == 45) ;    ");
        ok(" titi = !(45 == 45) ;   ");

        ok(" titi = 46 == 45 ;      ");
        ok(" titi = (46 == 45) ;    ");
        ok(" titi = !(46 == 45) ;   ");

    }
    
     @Test
    public void tesStringValNull() {
        System.out.println("\n********* null constant ");

        ok(" null ;             ");
        ok(" null + \"aaa\" ;   ");
  
    }

    /**
     * Generic test utility.
     *
     * @param s - source
     */
    public void ok(String s) {
        SSCompiler comp = new SSCompiler(config, s);
        String c = comp.getCode();
        c = c.split("do \\{", 2)[1]; // after do {
        c = c.split("\\} while", 2)[0]; // before while
        c = c.replaceAll("\n\r", " ").trim(); // remove new lines
        System.out.printf("%s\t===>\t%s\t<===%n", pad(s), pad(c));
    }
    /**
     * Padd if short string.
     * @param s
     * @return 
     */
    public String pad(String s) {
        if(s==null || s.length() > 59 )return s;
        String p = "                                           ";
        p = p + p + p ;
        s = s+p;
        return s.substring(0, 60);        
    }

}
