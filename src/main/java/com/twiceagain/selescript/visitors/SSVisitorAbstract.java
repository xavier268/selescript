/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twiceagain.selescript.visitors;

import auto.SelescriptBaseVisitor;
import com.twiceagain.selescript.exceptions.SSSyntaxException;
import com.twiceagain.selescript.interpreter.runtime.SSRuntimeContext;

/**
 * Structure of a visitor. All specific visitor inherit from this structure.
 * Visitors are constructed and rely on the provided RuntimeContext object to
 * run the script.
 *
 * @author xavier
 */
public class SSVisitorAbstract extends SelescriptBaseVisitor<Object> {

    protected SSRuntimeContext rtc;

    public SSVisitorAbstract(SSRuntimeContext rtc) {
        this.rtc = rtc;
    }

    /**
     * Remove leading and ending caracters.
     *
     * @param s
     * @return
     */
    public static String trim2(String s) {
        if (s == null || s.length() < 2) {
            throw new SSSyntaxException("Litteral string should end and start with ' or \"");
        } else {
            return s.substring(1, s.length() - 1);
        }
    }

    /**
     * Remove the last character.
     *
     * @param s - a new String
     * @return
     */
    public static String trim1(String s) {
        if (s == null || s.length() < 2) {
            throw new SSSyntaxException("Tags string should have length of at least 2 and end with ':' ");
        } else {
            return s.substring(0, s.length() - 1);
        }

    }

}
