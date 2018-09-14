/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twiceagain.selescript.listeners;

import auto.SelescriptParser;
import com.twiceagain.selescript.compiler.SSProperties;
import com.twiceagain.selescript.configuration.Config;
import static com.twiceagain.selescript.configuration.Config.AP;
import static com.twiceagain.selescript.configuration.Config.NL;
import com.twiceagain.selescript.configuration.SSListener;
import com.twiceagain.selescript.exceptions.SSSyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles the statements events.
 *
 * @author xavier
 */
public class SSListenerStatement extends SSListenerParam implements SSListener {

    public SSListenerStatement(Config config, SSProperties prop) {
        super(config, prop);
    }

    /**
     * Creates a fragment of code with comma separated values, alternating
     * between key, the value.Use when order matters, or when duplication is
     * encourraged.
     *
     * @param params
     * @param throwOnNull - should we throw on null keys ?
     * @return
     */
    protected String parseParams(List<SelescriptParser.ParamContext> params, boolean throwOnNull) {
        List<String> sl = new ArrayList<>();
        params.forEach((SelescriptParser.ParamContext p) -> {
            if (throwOnNull) {
                if (p.ID() == null) {
                    throw new SSSyntaxException("A tag is missing,when it is required");
                }
            }
            String s1 = (p.ID() == null) ? null : AP + p.ID().getText() + AP;
            String s2 = prop.get(p.stringval());
            sl.add(s1);
            sl.add(s2);
        });
        return String.join(",", sl);
    }

    @Override
    public void exitGo(SelescriptParser.GoContext ctx) {

        prop.needsWebdriver = true;
        String uid = config.createUniqueId();

        StringBuilder sb = new StringBuilder();

        sb
                .append("/* loop */")
                .append(NL)
                // start loop
                .append("fs.prepare(")
                .append(parseParams(ctx.param(), false))
                .append(");")
                .append(NL)
                .append("while(fs.loop()){")
                .append(NL);

        ctx.statement().forEach((s) -> {
            sb.append(prop.get(s));
        });
        sb
                .append("}")
                .append(NL)
                .append("fs.cleanup();")
                .append(NL);

        prop.put(ctx, sb.toString());

    }

    @Override
    public void exitCheck(SelescriptParser.CheckContext ctx) {
        // It was tempting to just continue when we know input is null,
        // but the compiler will refuse to compile unreacheable code ...

        String s = "if((" + prop.get(ctx.stringval()) + ")==null) continue;" + NL;

        prop.put(ctx, s);
    }

    @Override
    public void exitEmit(SelescriptParser.EmitContext ctx) {
        String s = "emit(" + parseParams(ctx.param(), true) + ");" + NL;
        prop.put(ctx, s);
    }

    @Override
    public void exitDb(SelescriptParser.DbContext ctx) {
        prop.needsMongo = true;
        String s = "mongo(" + parseParams(ctx.param(), true) + ");" + NL;
        prop.put(ctx, s);
    }

    @Override
    public void exitAssign(SelescriptParser.AssignContext ctx) {
        StringBuilder r = new StringBuilder();
        if (ctx.BIID() != null) {
            r
                    .append("_")
                    .append(ctx.BIID().getText())
                    .append(".set(")
                    .append(prop.get(ctx.stringval()))
                    .append(");")
                    .append(NL);
        } else {
            r.append("symtab.put(\"").append(ctx.ID().getText()).append("\",").append(prop.get(ctx.stringval())).append(");").append(NL);
        }
        prop.put(ctx, r.toString());
    }

    @Override
    public void exitClick(SelescriptParser.ClickContext ctx) {
        prop.needsWebdriver = true;
        String s = (ctx.stringval() == null) ? null : prop.get(ctx.stringval());
        String ss = "click(" + s + ");" + NL;
        prop.put(ctx, ss);
    }

    @Override
    public void exitClickw(SelescriptParser.ClickwContext ctx) {
        prop.needsWebdriver = true;
        String s = (ctx.stringval() == null) ? null : prop.get(ctx.stringval());
        String ss = "clickw(" + s + ");" + NL;
        prop.put(ctx, ss);
    }

    @Override
    public void exitPrint(SelescriptParser.PrintContext ctx) {
        if (ctx.stringval() == null) {
            prop.put(ctx, "System.out.println();" + NL);
        } else {
            prop.put(ctx, "System.out.println(" + prop.get(ctx.stringval()) + ");" + NL);
        }
    }

}
