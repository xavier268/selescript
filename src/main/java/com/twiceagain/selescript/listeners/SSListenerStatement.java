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
import java.util.ArrayList;
import java.util.List;
import org.antlr.v4.runtime.tree.ParseTreeProperty;

/**
 * Handles the statements events.
 *
 * @author xavier
 */
public class SSListenerStatement extends SSListenerParam implements SSListener {

    public SSListenerStatement(Config config, ParseTreeProperty<String> prop) {
        super(config, prop);
    }

    @Override
    public void exitGo(SelescriptParser.GoContext ctx) {

        String uid = config.createUniqueId();
        String puid = "p_" + uid;
        String luid = "l_" + uid;

        StringBuilder sb = new StringBuilder();

        sb
                .append("/* loop */")
                .append(NL)
                .append("Map<String,String> ")
                .append(puid)
                .append("= new HashMap<>();")
                .append(NL);

        ctx.param().forEach((p) -> {
            String s1 = (p.ID() == null) ? null : p.ID().getText();
            String s2 = prop.get(p.stringval()) ;
            sb
                    .append(puid)
                    .append(".put(")
                    .append(s1)
                    .append(",")
                    .append(s2)
                    .append(");")
                    .append(NL);
        });

        sb
                .append("fs.prepare(")
                .append(puid)
                .append(");")
                .append(NL)
                .append("while(fs.loop()){")
                .append(NL);

        ctx.statement().forEach((s) -> {
            sb.append(prop.get(s));
        });
        sb
                .append("};")
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

        StringBuilder sb = new StringBuilder();

        sb.append("emit(");
        List<String> ls = new ArrayList<>();
        ctx.param().forEach((pc) -> {
            ls.add(prop.get(pc));
        });
        sb
                .append(String.join(",", ls))
                .append(" );")
                .append(NL);

        prop.put(ctx, sb.toString());
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
        String s = "click(wd," + prop.get(ctx.stringval()) + ");" + NL;
        prop.put(ctx, s);
    }

    @Override
    public void exitClickw(SelescriptParser.ClickwContext ctx) {
        String s = "clickw(wd," + prop.get(ctx.stringval()) + ");" + NL;
        prop.put(ctx, s);
    }

    @Override
    public void exitPrint(SelescriptParser.PrintContext ctx) {
        prop.put(ctx, "System.out.println(" + prop.get(ctx.stringval()) + ");" + NL);
    }

}
