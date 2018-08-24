/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twiceagain.selescript.compiler;

import auto.SelescriptBaseListener;
import auto.SelescriptParser.AssignContext;
import auto.SelescriptParser.EmitContext;
import auto.SelescriptParser.ExistContext;
import auto.SelescriptParser.Go0Context;
import auto.SelescriptParser.GoContext;
import auto.SelescriptParser.LocatorContext;
import auto.SelescriptParser.UnitContext;
import com.twiceagain.selescript.compiler.exceptions.SSException;
import com.twiceagain.selescript.compiler.exceptions.SSUndefinedBuiltinException;
import com.twiceagain.selescript.compiler.exceptions.SSUndefinedIDException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *
 * @author xavier
 */
public class SSListener extends SelescriptBaseListener {

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

    public String getCode() {
        return code.toString();
    }

    /**
     * Save code to file. Using correct package name/folder.
     */
    public void saveCode() {

        new File(Config.getTargetDir() + "/com/twiceagain/scrapper").mkdirs();
        
        Config.copyRuntimeFiles();
        
        Path p = Paths.get(Config.getTargetDir(), "com/twiceagain/scrapper", "AutoScrapper.java");
        try {
            Files.write(p, code.toString().getBytes("UTF-8"));
        } catch (IOException ex) {
            throw new SSException(ex);
        }

    }

    public SSListener() {

        // Load predefined BIIDs.
        definedIds.addAll(Config.getBuiltinsList());

        // Load code headers.
        code
                .append("/****************************************************** ")
                .append(System.lineSeparator())
                .append("* Auto generated package - do not edit directly")
                .append(System.lineSeparator())
                .append("* Built ")
                .append(new Date().toString())
                .append(System.lineSeparator())
                .append("* Selescript version ")
                .append(Config.getVersion())
                .append(System.lineSeparator())
                .append("* For details, see https://github.com/xavier268/selescript")
                .append(System.lineSeparator())
                .append("*")
                .append(System.lineSeparator())
                .append("* (c) 2018 Xavier Gandillot")
                .append(System.lineSeparator())
                .append("*******************************************************/")
                .append(System.lineSeparator())
                .append(Config.getPackageDeclaration())
                .append(Config.getImportsDeclarations())
                .append(System.lineSeparator());

    }

    @Override
    public void enterUnit(UnitContext ctx) {
        code
                .append("public class AutoScrapper")
                .append(" { ")
                .append(System.lineSeparator())
                .append(Config.getBuiltinsMethods())
                .append(System.lineSeparator())
                .append("protected final Map<String,String> symtab = new HashMap();")
                .append(System.lineSeparator())
                .append("protected Map<String,String> toemit = new HashMap();")
                .append(System.lineSeparator())
                .append("public void scrap(WebDriver wd) { ")
                .append(System.lineSeparator())
                .append("SearchContext we").append(level).append("=wd;")
                .append(System.lineSeparator())
                .append("/* Temporary variables */")
                .append(System.lineSeparator())
                .append("List<WebElement> lwe;")
                .append(System.lineSeparator())
                .append("String s; ")
                .append(System.lineSeparator())
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
                .append("} while(true) ; // infinite loop")
                .append(System.lineSeparator());
    }

    @Override
    public void enterGo(GoContext ctx) {
        level++;
        code
                .append("for(WebElement we").append(level)
                .append(":we").append(level - 1).append(".findElements(By.xpath(");
        appendLocatorGetter(code, ctx.locator())
                .append("))) {")
                .append(System.lineSeparator());
    }

    @Override
    public void exitGo(GoContext ctx) {
        code
                .append("} // loop for ")
                .append(System.lineSeparator());
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
                    .append(System.lineSeparator());
            return;
        }

        if (ctx.BIID() != null) {
            if (!definedIds.contains(ctx.BIID().getText())) {
                throw new SSUndefinedBuiltinException(
                        "Error trying to use  "
                        + ctx.getText()
                        + " as a built-in variable, but it is not"
                        + System.lineSeparator()
                        + "Recognized built-in are : " + listBuiltIns());
            }
            code.append("BUILTIN_")
                    .append(ctx.BIID().getText().substring(1))
                    .append("_set(wd,");
            appendLocatorGetter(code, ctx.locator())
                    .append(");")
                    .append(System.lineSeparator());
            return;
        }

        throw new SSException("Unrecognized target for assignement ?");

    }

    @Override
    public void enterEmit(EmitContext ctx) {

        code.append("toemit.clear();")
                .append(System.lineSeparator());
        for (Integer i = 0; i < ctx.locator().size(); i++) {
            retrieveListWElemntsByLocator(code, ctx.locator(i))
                    .append(System.lineSeparator())
                    .append("s=\"\";if(!lwe.isEmpty()){s=lwe.get(0).getText();}")
                    .append(System.lineSeparator());
            if (ctx.locator(i).STRING() != null) {
                code.append("toemit.put(\"field")
                        .append(i)
                        .append("\",s);")
                        .append(System.lineSeparator());
                continue;
            }
            if (ctx.locator(i).ID() != null) {
                code.append("toemit.put(\"id_")
                        .append(ctx.locator(i).getText())
                        .append("\",s);")
                        .append(System.lineSeparator());
                continue;
            }
            if (ctx.locator(i).BIID() != null) {
                code.append("toemit.put(\"biid_")
                        .append(ctx.locator(i).getText().substring(1))
                        .append("\",s);")
                        .append(System.lineSeparator());
                continue;
            }
            throw new SSException("Unexpected locator type ?");
        }

        code
                .append("BUILTIN_emit(toemit);")
                .append(System.lineSeparator());

    }

    @Override
    public void enterExist(ExistContext ctx) {
        code
                .append("if(we")
                .append(level)
                .append(".findElements(By.xpath(");
        appendLocatorGetter(code, ctx.locator())
                .append(")).isEmpty()) { continue; }")
                .append(System.lineSeparator());

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
                        + System.lineSeparator()
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
                        + System.lineSeparator()
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
