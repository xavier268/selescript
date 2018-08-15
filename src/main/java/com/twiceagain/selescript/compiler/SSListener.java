/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twiceagain.selescript.compiler;

import auto.SelescriptBaseListener;
import auto.SelescriptParser;
import auto.SelescriptParser.UnitContext;
import org.antlr.v4.runtime.ParserRuleContext;

/**
 *
 * @author xavier
 */
public class SSListener extends SelescriptBaseListener {

    /**
     * The code being constructed.
     */
    private StringBuilder code = new StringBuilder();

    public SSListener() {

        code
                .append("/* messages, headers, packages declarations ...*/")
                .append(System.lineSeparator());
    }

    @Override
    public void enterUnit(UnitContext ctx) {
        code
                .append("public class Unit_")
                .append(ctx.ID().getText())
                .append(" { ");
    }

    @Override
    public void exitUnit(UnitContext ctx) {
        code
                .append(" } ")
                .append(System.lineSeparator());
    }

    @Override
    public void exitAnumber(SelescriptParser.AnumberContext ctx) {      

    }

    @Override
    public void enterEveryRule(ParserRuleContext ctx) {

    }

    @Override
    public void exitEveryRule(ParserRuleContext ctx) {

        // Debug
        System.out.printf(
                "\nExiting rule %s\n====CODE====\n%s\n=================",
                ctx.getText(),
                code.toString()
        );
    }
    
    public String getCode() {
        return code.toString();
    }

}
