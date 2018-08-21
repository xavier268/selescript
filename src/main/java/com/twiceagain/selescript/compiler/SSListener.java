/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twiceagain.selescript.compiler;

import auto.SelescriptBaseListener;
import auto.SelescriptParser.Go0Context;
import auto.SelescriptParser.GoContext;
import auto.SelescriptParser.UnitContext;
import org.antlr.v4.runtime.tree.ParseTreeProperty;

/**
 *
 * @author xavier
 */
public class SSListener extends SelescriptBaseListener {
    
    private final ParseTreeProperty<String> annotatedTree = new ParseTreeProperty<>();

    /**
     * The code being constructed.
     */
    private final StringBuilder code = new StringBuilder();
    /**
     * The current context webElement are called we1, we2, we3, ... based upon
     * the nesting level. At any moment, "we + level" represents the current
     * webelement pointed to in the DOM.
     */
    private Integer level = 1;
    
    public String getCode() {
        return code.toString();
    }
    
    public SSListener() {
        
        code
                .append("/* Autogenerated file - do not edit */")
                .append(System.lineSeparator())
                .append("protected Map<String,String> symtab = new HashMap();")
                .append(System.lineSeparator());
    }
    
    @Override
    public void enterUnit(UnitContext ctx) {
        code
                .append("public class AutoScrapper")
                .append(" { ")
                .append(System.lineSeparator())
                .append("public void scrap(WebDriver wd) { ")
                .append(System.lineSeparator())
                .append("SearchContext we").append(level).append("=wd;")
                .append(System.lineSeparator());
        
    }
    
    @Override
    public void exitUnit(UnitContext ctx) {
        
        code
                .append(System.lineSeparator())
                .append(" } // scrap method")
                .append(System.lineSeparator())
                .append(" } // AutoScrapper class")
                .append(System.lineSeparator());
    }
    
    @Override
    public void enterGo0(Go0Context ctx) {
        
        code
                .append("do { ")
                .append(System.lineSeparator());
        
    }
    
    @Override
    public void exitGo0(Go0Context ctx) {
        
        code
                .append("} // infinite loop")
                .append(System.lineSeparator());
    }
    
    @Override
    public void enterGo(GoContext ctx) {
        level++;
        code
                .append("for(WebElement we").append(level)
                .append(":we").append(level - 1).append(".findElements(By.xpath(");
        if (ctx.locator().STRING() != null) {
            code.append(ctx.locator().getText());
        }
        if (ctx.locator().ID() != null) {
            code.append("symtab.get(\"")
                    .append(ctx.locator().getText())
                    .append("\")");
        }
        if (ctx.locator().BIID() != null) {
            code.append("builtin_")
                    .append(ctx.locator().getText().substring(1))
                    .append(".get()");
        }
        code
                .append("))) {")
                .append(System.lineSeparator());
    }
    
    @Override
    public void exitGo(GoContext ctx) {
        code
                .append("} // loop for ")
                .append(ctx.locator().getText())
                .append(System.lineSeparator());
        level--;
    }
    
}
