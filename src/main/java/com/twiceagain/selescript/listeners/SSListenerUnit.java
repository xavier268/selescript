/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twiceagain.selescript.listeners;

import auto.SelescriptParser;
import com.twiceagain.selescript.configuration.Config;
import static com.twiceagain.selescript.configuration.Config.NL;
import com.twiceagain.selescript.configuration.SSListener;
import org.antlr.v4.runtime.tree.ParseTreeProperty;

/**
 *
 * @author xavier
 */
public class SSListenerUnit extends SSBaseListener implements SSListener {

    public SSListenerUnit(Config config, ParseTreeProperty<String> prop) {
        super(config, prop);
    }

    
   
    @Override
    public void exitUnit(SelescriptParser.UnitContext ctx) {
        StringBuilder sb = new StringBuilder();
        
        sb
                .append(config.getFileHeader())
                .append(config.getPackageDeclaration())
                .append(config.getImportsDeclarations())
                .append(NL).append(NL);

        // Define class
        sb
                .append("public class ")
                .append(config.getTargetJavaClassName())
                .append(" extends Methods implements Scrapper {")
                .append(NL)
                .append(NL);
        
        // Add useful constants and create builtins
        sb
                .append(config.getConstantDeclarations())
                .append(config.getBuiltinsDeclarations())
                .append(NL);

        // create a basic main method calling predefined non-static main
        sb
                .append("public static void main(String ... args ) {")
                .append(NL)
                .append("new ")
                .append(config.getTargetJavaClassName()).append("().main() ;}")
                .append(Config.NL);
        

        // Create the scrap method
        sb
                .append("/* Actual scrapping happens here */").append(Config.NL)
                .append("@Override").append(NL)
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
