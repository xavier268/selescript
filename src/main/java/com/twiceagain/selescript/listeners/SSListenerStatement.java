/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twiceagain.selescript.listeners;

import auto.SelescriptParser;
import com.twiceagain.selescript.configuration.Config;
import static com.twiceagain.selescript.configuration.Config.AP;
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

        String wename = config.createUniqueId();
        StringBuilder sb = new StringBuilder();

        sb
                .append("/* go loop */").append(NL)
                .append("for(WebElement ")
                .append(wename)
                .append(" : new WebElementIterator(wes.getLast(),")
                .append(prop.get(ctx.stringval())).append(")){").append(NL)
                .append("wes.add(").append(wename).
                append(");").append(NL)
                .append("do{").append(NL);

        ctx.statement().forEach((s) -> {
            sb.append(prop.get(s));
        });
        sb
                .append("} while(false);").append(NL)
                .append("wes.removeLast();").append(NL)
                .append("}").append(NL);

        prop.put(ctx, sb.toString());

    }

    /**
     * The Go0 loop will lopp once, unconditionnaly.
     *
     * @param ctx
     */
    @Override
    public void exitGo0(SelescriptParser.Go0Context ctx
    ) {
        StringBuilder sb
                = new StringBuilder();
        sb
                .append("/* Go0 loop */").append(NL
        )
                .append("do {").append(NL
        );

        ctx
                .statement().forEach((s) -> {
                    sb
                            .append(prop
                                    .get(s
                                    ));

                });

        sb
                .append("} while(false); // go0 loop").append(NL
        );

        prop
                .put(ctx,
                        sb
                                .toString());

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
    public void enterAssign(SelescriptParser.AssignContext ctx) {
    }

    @Override
    public void exitAssign(SelescriptParser.AssignContext ctx) {
        StringBuilder r = new StringBuilder();
        if (ctx.BIID() != null) {
            r
                    .append("biidSet(wd,wes.getLast(),")
                    .append(AP)
                    .append(ctx.BIID().getText())
                    .append(AP)
                    .append(",")
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
