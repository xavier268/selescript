/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twiceagain.selescript.implementation;

import auto.SelescriptListener;
import auto.SelescriptParser;
import static com.twiceagain.selescript.Config.NL;
import com.twiceagain.selescript.SSListener;
import java.io.IOException;
import java.nio.file.Path;
import org.antlr.v4.runtime.CharStream;

/**
 * Handles the statements events.
 *
 * @author xavier
 */
public abstract class SSListener09Statement extends SSListener05StringVal implements SSListener, SelescriptListener {

    public SSListener09Statement(String s) {
        super(s);
    }

    public SSListener09Statement(Path path) throws IOException {
        super(path);
    }

    public SSListener09Statement(CharStream in) {
        super(in);
    }

    @Override
    public void enterGo(SelescriptParser.GoContext ctx) {
    }

    @Override
    public void enterGo0(SelescriptParser.Go0Context ctx) {
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
    public void enterCheck(SelescriptParser.CheckContext ctx) {
    }

    @Override
    public void exitCheck(SelescriptParser.CheckContext ctx) {
        // It was tempting to just continue when we know input is null,
        // but the compiler will refuse to compile unreacheable code ...

        String s = "if((" + prop.get(ctx.stringval()) + ")==null) continue;" + NL;

        prop.put(ctx, s);
    }

}
