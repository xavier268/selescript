/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twiceagain.selescript.compiler.implementation;

import auto.SelescriptBaseListener;
import auto.SelescriptParser.AssignContext;
import auto.SelescriptParser.EmitContext;
import auto.SelescriptParser.ExistContext;
import auto.SelescriptParser.Go0Context;
import auto.SelescriptParser.GoContext;
import auto.SelescriptParser.LocatorContext;
import auto.SelescriptParser.UnitContext;
import com.twiceagain.selescript.compiler.Config;
import com.twiceagain.selescript.compiler.SSListener;
import com.twiceagain.selescript.compiler.exceptions.SSException;
import com.twiceagain.selescript.compiler.exceptions.SSUndefinedBuiltinException;
import com.twiceagain.selescript.compiler.exceptions.SSUndefinedIDException;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *
 * @author xavier
 */
public class SSListener_Old extends SelescriptBaseListener implements SSListener{

    /**
     * The set of IDs that have been defined so far.
     */
    protected final Set<String> definedIds = new HashSet<>();
    /**
     * The code being constructed.
     */
    protected final StringBuilder code = new StringBuilder();
    /**
     * The current context webElement are called we1, we2, we3, ... based upon
     * the nesting level. At any moment, "we + level" represents the current
     * webelement pointed to in the DOM.
     */
    private Integer level = 1;

    @Override
    public String getCode() {
        return code.toString();
    }

    

    public SSListener_Old() {

        // Load predefined BIIDs.
        definedIds.addAll(Config.getBuiltinsList());

        // Load code headers.
        code
                .append(Config.getFileHeader())
                .append(Config.getPackageDeclaration())
                .append(Config.getImportsDeclarations())
                .append(Config.NL);

    }

    @Override
    public void enterUnit(UnitContext ctx) {
        code
                .append("public class ").append(Config.getTargetJavaClassName())
                .append(" { ")
                .append(Config.NL)
                .append(Config.getBuiltinsMethods())
                .append(Config.NL)
                .append("protected final Map<String,String> symtab = new HashMap();")
                .append(Config.NL)
                .append("protected Map<String,String> toemit = new HashMap();")
                .append(Config.NL)
                .append("public void scrap(WebDriver wd) { ")
                .append(Config.NL)
                .append("SearchContext we").append(level).append("=wd;")
                .append(Config.NL)
                .append("/* Temporary variables */")
                .append(Config.NL)
                .append("List<WebElement> lwe;")
                .append(Config.NL)
                .append("String s; ")
                .append(Config.NL)
                .append(Config.NL);

    }

    @Override
    public void exitUnit(UnitContext ctx) {

        code
                .append(Config.NL)
                .append(" } // scrap method")
                .append(Config.NL)
                .append(" } // AutoScrapper class")
                .append(Config.NL);
    }

    @Override
    public void enterGo0(Go0Context ctx) {

        code
                .append("do { ")
                .append(Config.NL);

    }

    @Override
    public void exitGo0(Go0Context ctx) {
        code
                .append("} while(true) ; // infinite loop")
                .append(Config.NL);
    }

    @Override
    public void enterGo(GoContext ctx) {
        level++;
        code
                .append("for(WebElement we").append(level)
                .append(":we").append(level - 1).append(".findElements(By.xpath(");
        appendLocatorGetter(code, ctx.locator())
                .append("))) {")
                .append(Config.NL);
    }

    @Override
    public void exitGo(GoContext ctx) {
        code
                .append("} // loop for ")
                .append(Config.NL);
        level--;
    }

    @Override
    public void enterAssign(AssignContext ctx) {

        if (ctx.ID() != null) {
            definedIds.add(ctx.ID().getText());
            code.append("symtab.put(\"")
                    .append(ctx.ID().getText())
                    .append("\",");
            appendLocatorGetter(code, ctx.locator())
                    .append(");")
                    .append(Config.NL);
            return;
        }

        if (ctx.BIID() != null) {
            if (!definedIds.contains(ctx.BIID().getText())) {
                throw new SSUndefinedBuiltinException(
                        "Error trying to use  "
                        + ctx.getText()
                        + " as a built-in variable, but it is not"
                        + Config.NL
                        + "Recognized built-in are : " + listBuiltIns());
            }
            code.append("BUILTIN_")
                    .append(ctx.BIID().getText().substring(1))
                    .append("_set(wd,");
            appendLocatorGetter(code, ctx.locator())
                    .append(");")
                    .append(Config.NL);
            return;
        }

        throw new SSException("Unrecognized target for assignement ?");

    }

    @Override
    public void enterEmit(EmitContext ctx) {

        code.append("toemit.clear();")
                .append(Config.NL);
        for (Integer i = 0; i < ctx.locator().size(); i++) {
            retrieveListWElemntsByLocator(code, ctx.locator(i))
                    .append(Config.NL)
                    .append("s=\"\";if(!lwe.isEmpty()){s=lwe.get(0).getText();}")
                    .append(Config.NL);
            if (ctx.locator(i).STRING() != null) {
                code.append("toemit.put(\"field")
                        .append(i)
                        .append("\",s);")
                        .append(Config.NL);
                continue;
            }
            if (ctx.locator(i).ID() != null) {
                code.append("toemit.put(\"id_")
                        .append(ctx.locator(i).getText())
                        .append("\",s);")
                        .append(Config.NL);
                continue;
            }
            if (ctx.locator(i).BIID() != null) {
                code.append("toemit.put(\"biid_")
                        .append(ctx.locator(i).getText().substring(1))
                        .append("\",s);")
                        .append(Config.NL);
                continue;
            }
            throw new SSException("Unexpected locator type ?");
        }

        code
                .append("BUILTIN_emit(toemit);")
                .append(Config.NL);

    }

    @Override
    public void enterExist(ExistContext ctx) {
        code
                .append("if(we")
                .append(level)
                .append(".findElements(By.xpath(");
        appendLocatorGetter(code, ctx.locator())
                .append(")).isEmpty()) { continue; }")
                .append(Config.NL);

    }

    // ========= utilities =============
    /**
     * Will write the code to retrieve the list of elements specified by
     * locator, and store it in the global variable lwe.
     *
     * @param code
     * @param lctx
     * @return
     */
    protected StringBuilder retrieveListWElemntsByLocator(StringBuilder code, LocatorContext lctx) {
        if (lctx == null) {
            throw new SSException("Trying to decode a null locator ?");
        }
        code.append("lwe = we")
                .append(level)
                .append(".findElements(By.xpath(");
        appendLocatorGetter(code, lctx)
                .append("));");
        return code;
    }

    /**
     * Added the getter associated with a locator.
     *
     * @param code
     * @param lctx
     * @return
     */
    protected StringBuilder appendLocatorGetter(StringBuilder code, LocatorContext lctx) {
        if (lctx == null) {
            throw new SSException("Trying to decode a null locator ?");
        }
        if (lctx.STRING() != null) {
            code.append(lctx.getText());
            return code;
        }
        if (lctx.ID() != null) {
            if (!definedIds.contains(lctx.getText())) {
                throw new SSUndefinedIDException(
                        "Error trying to use ID "
                        + lctx.getText()
                        + " before it has been assigned a value."
                        + Config.NL
                        + "Known IDs are : "
                        + listIds());
            }
            code.append("symtab.get(\"")
                    .append(lctx.getText())
                    .append("\")");
            return code;
        }
        if (lctx.BIID() != null) {
            if (!definedIds.contains(lctx.getText())) {
                throw new SSUndefinedBuiltinException(
                        "Error trying to use  "
                        + lctx.getText()
                        + " as a built-in variable, but it is not"
                        + Config.NL
                        + "Recognized built-in are : " + listBuiltIns());
            }
            code.append("BUILTIN_")
                    .append(lctx.getText().substring(1))
                    .append("_get(wd)");
            return code;
        }
        throw new SSException("Internal SSListener eror : locator is neither STRING, ID nor BIID ?");

    }

    /**
     * List built-ins.
     *
     * @return
     */
    protected String listBuiltIns() {
        return Config.getBuiltinsList().toString();
    }

    /**
     * List known IDs.
     *
     * @return
     */
    protected String listIds() {
        return definedIds.stream()
                .filter((String x) -> {
                    return !x.startsWith("$");
                })
                .collect(Collectors.joining(","));
    }

}
