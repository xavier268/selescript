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
public class SSListenerImplementation extends SSListenerSringVal implements SelescriptListener {

    private final static Logger LOG = LoggerFactory.getLogger(SSListenerImplementation.class);

    public SSListenerImplementation(CharStream in) {
        super(in);
    }

    public SSListenerImplementation(String s) {
        super(s);
    }

    public SSListenerImplementation(Path path) throws IOException {
        super(path);
    }

    @Override
    public void enterUnit(SelescriptParser.UnitContext ctx) {
    }


    @Override
    public void enterGo0(SelescriptParser.Go0Context ctx) {
    }

    @Override
    public void exitGo0(SelescriptParser.Go0Context ctx) {
    }

    @Override
    public void enterGo(SelescriptParser.GoContext ctx) {
    }

    @Override
    public void exitGo(SelescriptParser.GoContext ctx) {
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
    public void enterEmit(SelescriptParser.EmitContext ctx) {
    }

    @Override
    public void exitEmit(SelescriptParser.EmitContext ctx) {
    }

    @Override
    public void enterCheck(SelescriptParser.CheckContext ctx) {
    }

    @Override
    public void exitCheck(SelescriptParser.CheckContext ctx) {
        // It was tempting to just continue when we know input is null, 
        // but the compiler will refuse to compile unreacheable code ...
        String s = "if((" + prop.get(ctx.stringval()) + ")==null) continue;" + NL;
        prop.put(ctx, s);
    }

    @Override
    public void enterEmitparam(SelescriptParser.EmitparamContext ctx) {
    }

    @Override
    public void exitEmitparam(SelescriptParser.EmitparamContext ctx) {
    }

    public void exitUnit(SelescriptParser.UnitContext ctx) {
        StringBuilder sb = new StringBuilder();
        sb.append(config.getFileHeader()).append(config.getPackageDeclaration()).append(config.getImportsDeclarations()).append(Config.NL).append(Config.NL);
        // Define class
        sb.append("public class ").append(config.getTargetJavaClassName()).append(" {").append(Config.NL);
        // define useful constants
        sb.append("public final static String VERSION = \"").append(config.getSelescriptVersion()).append("\";").append(Config.NL).append("public final static String SELENIUMVERSION = \"").append(config.getSeleniumVersion()).append("\";").append(Config.NL).append("public final static String BUILDDATE = \"").append(new Date()).append("\";").append(Config.NL).append("public final static String BUILDMILLIS = \"").append(System.currentTimeMillis()).append("\";").append(Config.NL).append(Config.NL);
        // Define class field variables
        sb.append("protected Map<String,String> symtab = new HashMap<>();").append(Config.NL);
        sb.append("private final static Logger LOG = LoggerFactory.getLogger(").append(config.getTargetJavaClassName()).append(".class);").append(Config.NL).append(Config.NL);
        // Add builtin methods
        sb.append(config.getBuiltinsMethods()).append(Config.NL);
        // create a newInstance() static method to construct easily
        sb.append("public static final ").append(config.getTargetJavaClassName()).append(" newInstance() {").append(Config.NL).append("return new ").append(config.getTargetJavaClassName()).append("();}").append(Config.NL).append(Config.NL);
        // Create the scrap method
        sb.append("/* This is the main method for scrapping */").append(Config.NL);
        sb.append("public void scrap(WebDriver wd){ ").append(Config.NL);
        sb.append("do { ").append(Config.NL);
        // Add code from statements.
        for (SelescriptParser.StatementContext c : ctx.statement()) {
            sb.append("/* ").append(c.getText()).append(" */").append(Config.NL);
            sb.append(prop.get(c));
        }
        // close class definition
        sb.append(Config.NL).append("} while(false) ; // one time outer loop").append(Config.NL).append("} // Scrap").append(Config.NL).append("} // Class definition").append(Config.NL).append(Config.NL);
        // Annotate tree
        prop.put(ctx, sb.toString());
    }



}
