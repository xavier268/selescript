/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twiceagain.selescript.implementation;

import auto.SelescriptListener;
import auto.SelescriptParser;
import com.twiceagain.selescript.Config;
import static com.twiceagain.selescript.Config.NL;
import com.twiceagain.selescript.SSListener;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Date;
import org.antlr.v4.runtime.CharStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author xavier
 */
public class SSListener99Implementation extends SSListener09Statement implements SSListener, SelescriptListener {

    private final static Logger LOG = LoggerFactory.getLogger(SSListener99Implementation.class);

    public SSListener99Implementation(CharStream in) {
        super(in);
    }

    public SSListener99Implementation(String s) {
        super(s);
    }

    public SSListener99Implementation(Path path) throws IOException {
        super(path);
    }

    @Override
    public void enterUnit(SelescriptParser.UnitContext ctx) {
    }


    @Override
    public void enterAssign(SelescriptParser.AssignContext ctx) {
    }

    @Override
    public void exitAssign(SelescriptParser.AssignContext ctx) {

        StringBuilder r = new StringBuilder();
        if (ctx.BIID() != null) {
            r
                    .append("BUILTINS_")
                    .append(ctx.BIID().getText().substring(1))
                    .append("(")
                    .append(prop.get(ctx.stringval()))
                    .append(");")
                    .append(NL);
        } else {
            r
                    .append("symtab.put(\"")
                    .append(ctx.ID().getText())
                    .append("\",")
                    .append(prop.get(ctx.stringval()))
                    .append(");")
                    .append(NL);
        }
        prop.put(ctx, r.toString());
    }



   
    @Override
    public void exitUnit(SelescriptParser.UnitContext ctx) {
        StringBuilder sb = new StringBuilder();
        sb.append(config.getFileHeader()).append(config.getPackageDeclaration()).append(config.getImportsDeclarations()).append(Config.NL).append(Config.NL);

        // Define class
        sb.append("public class ").append(config.getTargetJavaClassName()).append(" {").append(Config.NL).append(NL);

        // define useful constants
        sb
                .append("public final static String VERSION = \"").append(config.getSelescriptVersion()).append("\";").append(Config.NL)
                .append("public final static String SELENIUMVERSION = \"").append(config.getSeleniumVersion()).append("\";").append(Config.NL)
                .append("public final static String BUILDDATE = \"").append(new Date()).append("\";").append(Config.NL)
                .append("public final static String BUILDMILLIS = \"").append(System.currentTimeMillis()).append("\";").append(Config.NL)
                .append("private final static Class CLASS = ").append(config.getTargetJavaClassName()).append(".class;").append(Config.NL)
                .append(NL);

        // create a newInstance() static method to construct easily
        sb
                .append("public static final ").append(config.getTargetJavaClassName()).append(" newInstance() {").append(Config.NL).append("return new ").append(config.getTargetJavaClassName()).append("();}").append(Config.NL)
                .append(Config.NL);

        // Add builtin methods
        sb
                .append(NL)
                .append(config.getBuiltinsMethods())
                .append(Config.NL);

        // Create the scrap method
        sb
                .append("/* Actual scrapping happens here */").append(Config.NL)
                .append("public void scrap(WebDriver wd){ ").append(Config.NL)
                .append("do { ")
                .append(NL);
        
        // Add code from statements.
        ctx.statement().forEach(
                (SelescriptParser.StatementContext c) -> {            
            sb.append(prop.get(c));
        });
        // close class definition
        sb
                .append("} while(false) ; // one time outer loop").append(Config.NL)
                .append("} // Scrap").append(Config.NL)
                .append("} // Class definition").append(Config.NL)
                .append(Config.NL);
        // Annotate tree
        prop.put(ctx, sb.toString());
    }


   


}
