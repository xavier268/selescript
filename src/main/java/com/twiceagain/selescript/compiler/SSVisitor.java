/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twiceagain.selescript.compiler;

import auto.SelescriptBaseVisitor;
import auto.SelescriptParser;
import auto.SelescriptParser.BassignContext;
import auto.SelescriptParser.BlocContext;
import auto.SelescriptParser.UnitContext;

/**
 * My visitor class will generate the java code.
 *
 * @author xavier
 */
public class SSVisitor extends SelescriptBaseVisitor<String> {

    @Override
    public String visitUnit(UnitContext ctx) {

        StringBuilder sb = new StringBuilder()
                .append("/** bla bla bla ... */")
                .append(System.lineSeparator())
                .append("public class Unit_")
                .append(ctx.ID().getText())
                .append(" {\n WebDriver wd = new RemoteWebDriver();\n");

        // Now, we need to visit the inside blocs.
        // First, collect and write all assignements.
        StringBuilder sbass = new StringBuilder("\n/* Assignement bloc */\n");

        // Loop through assignements
        for (BlocContext bctx : ctx.bloc()) {
            if (bctx instanceof BassignContext) {
                // found an assignement bloc
                sbass.append(visitBassign((BassignContext)bctx));
                    };
        }

        sb.append(
                "\n}\n");
        return sb.toString();

    }

}
