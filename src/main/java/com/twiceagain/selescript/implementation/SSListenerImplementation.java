/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twiceagain.selescript.implementation;

import auto.SelescriptListener;
import auto.SelescriptParser;
import auto.SelescriptParser.*;
import com.twiceagain.selescript.Config;
import static com.twiceagain.selescript.Config.NL;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author xavier
 */
public class SSListenerImplementation extends SSAbstractListener implements SelescriptListener {
    
    
    private  final static Logger LOG = LoggerFactory.getLogger(SSListenerImplementation.class);
    
    @Override
    public void enterUnit(SelescriptParser.UnitContext ctx) {
    }
    
    @Override
    public void exitUnit(SelescriptParser.UnitContext ctx) {
        StringBuilder sb = new StringBuilder();
        sb
                .append(Config.getFileHeader())
                .append(Config.getPackageDeclaration())
                .append(Config.getImportsDeclarations())
                .append(NL).append(NL);
        // Define class
        sb.append("public class ").append(Config.getTargetJavaClassName())
                .append(" {").append(NL);
        // define useful constants
        sb.append("public final static String VERSION = \"")
                .append(Config.getVersion()).append("\";")
                .append(NL)
                .append("public final static String SELENIUMVERSION = \"")
                .append(Config.getSeleniumVersion()).append("\";")
                .append(NL)
                .append("public final static String BUILDDATE = \"")
                .append(new Date()).append("\";")
                .append(NL)
                .append("public final static String BUILDMILLIS = \"")
                .append(System.currentTimeMillis()).append("\";")
                .append(NL)
                .append(NL);
        // Define class field variables
        sb.append("protected Map<String,String> symtab = new HashMap<String,String>();")
                .append(NL);
        sb.append("private final static Logger LOG = LoggerFactory.getLogger(")
                .append(Config.getTargetJavaClassName())
                .append(".class);")
                .append(NL)
                .append(NL);

        // Add builtin methods
        sb.append(Config.getBuiltinsMethods())
                .append(NL);

        // Create the scrap method
        sb.append("/* This is the main method for scrapping */").append(NL);
        sb.append("public void scrap(WebDriver wd){ ").append(NL);
        sb.append("do { ").append(NL);

        // Add code from statements.
        for (StatementContext c : ctx.statement()) {
            
            sb.append("/* ").append(c.getText()).append(" */").append(NL);
            sb.append(prop.get(c));
        }
        // close class definition
        sb.append(NL)
                .append("} while(false) ; // one time outer loop").append(NL)
                .append("} // Scrap").append(NL)
                .append("} // Class definition").append(NL)
                .append(NL);

        // Annotate tree
        prop.put(ctx, sb.toString());
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
        String s = "if((" + prop.get(ctx.stringval()) + ")==null) break;" + NL;
        prop.put(ctx, s);
    }
    
    @Override
    public void enterEmitparam(SelescriptParser.EmitparamContext ctx) {
    }
    
    @Override
    public void exitEmitparam(SelescriptParser.EmitparamContext ctx) {
    }
    
    
    
    @Override
    public void enterNot(SelescriptParser.NotContext ctx) {
    }
    
    @Override
    public void exitNot(SelescriptParser.NotContext ctx) {
        String s = "not(" + prop.get(ctx.stringval()) + ")";        
        prop.put(ctx, s);
    }
    
    @Override
    public void enterAt(SelescriptParser.AtContext ctx) {
    }
    
    @Override
    public void exitAt(SelescriptParser.AtContext ctx) {
    }
    
    
    
    @Override
    public void enterId(SelescriptParser.IdContext ctx) {
    }
    
    @Override
    public void exitId(SelescriptParser.IdContext ctx) {
        // isValidId(ctx.getText()); // Defaut to null (ie false) if not defined
        prop.put(ctx, "symtab.get(\"" + ctx.getText() + "\")");
    }
    
    @Override
    public void enterConcat(SelescriptParser.ConcatContext ctx) {
    }
    
    @Override
    public void exitConcat(SelescriptParser.ConcatContext ctx) {
        
        String s = "plus("
                + prop.get(ctx.stringval(0))
                + ","
                + prop.get(ctx.stringval(1))
                + ")";
        prop.put(ctx, s);
        
    }
    
    @Override
    public void enterBiid(SelescriptParser.BiidContext ctx) {
    }
    
    @Override
    public void exitBiid(SelescriptParser.BiidContext ctx) {
        isValidBiids(ctx.getText());
        prop.put(ctx, "BIID_" + ctx.getText().substring(1) + "()");
    }
    
    @Override
    public void enterEq(SelescriptParser.EqContext ctx) {
    }
    
    @Override
    public void exitEq(SelescriptParser.EqContext ctx) {
        String s = "eq(" + prop.get(ctx.stringval(0)) + ","
                + prop.get(ctx.stringval(1)) + ")";
        prop.put(ctx, s);
    }

    @Override
    public void enterPar(ParContext ctx) {
    }

    @Override
    public void exitPar(ParContext ctx) {
        prop.put(ctx, prop.get(ctx.stringval()));
    }

    @Override
    public void enterCpar(CparContext ctx) {
    }

    @Override
    public void exitCpar(CparContext ctx) {
    }

    @Override
    public void enterCtimes(CtimesContext ctx) {
    }

    @Override
    public void exitCtimes(CtimesContext ctx) {
    }

    @Override
    public void enterCdiv(CdivContext ctx) {
    }

    @Override
    public void exitCdiv(CdivContext ctx) {
    }

    @Override
    public void enterUminus(UminusContext ctx) {
    }

    @Override
    public void exitUminus(UminusContext ctx) {
    }

    @Override
    public void enterCplus(CplusContext ctx) {
    }

    @Override
    public void exitCplus(CplusContext ctx) {
    }

    @Override
    public void enterCminus(CminusContext ctx) {
    }

    @Override
    public void exitCminus(CminusContext ctx) {
    }

    @Override
    public void enterCnumber(CnumberContext ctx) {
    }

    @Override
    public void exitCnumber(CnumberContext ctx) {
    }

    @Override
    public void enterSstring(SstringContext ctx) {
    }

    @Override
    public void exitSstring(SstringContext ctx) {
    }

    @Override
    public void enterCsc(CscContext ctx) {
    }

    @Override
    public void exitCsc(CscContext ctx) {
    }

    @Override
    public void enterCsplus(CsplusContext ctx) {
    }

    @Override
    public void exitCsplus(CsplusContext ctx) {
    }

    @Override
    public void enterCsstring(CsstringContext ctx) {
    }

    @Override
    public void exitCsstring(CsstringContext ctx) {
    }

    @Override
    public void enterCspar(CsparContext ctx) {
    }

    @Override
    public void exitCspar(CsparContext ctx) {
    }

    @Override
    public void enterCsnot(CsnotContext ctx) {
    }

    @Override
    public void exitCsnot(CsnotContext ctx) {
    }
    
}