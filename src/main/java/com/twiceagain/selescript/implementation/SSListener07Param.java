/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twiceagain.selescript.implementation;

import auto.SelescriptListener;
import auto.SelescriptParser;
import static com.twiceagain.selescript.Config.AP;
import com.twiceagain.selescript.SSListener;
import java.io.IOException;
import java.nio.file.Path;
import org.antlr.v4.runtime.CharStream;

/**
 * Handles parameters for functions. Parameters are stored in the prop as a
 * comma separated list alternating fieldname and field content. When no field
 * name was specified, null is used. When no parametr at all, empty string.
 *
 * @author xavier
 */
public abstract class SSListener07Param extends SSListener05StringVal implements SSListener, SelescriptListener {

    public SSListener07Param(String s) {
        super(s);
    }

    public SSListener07Param(Path path) throws IOException {
        super(path);
    }

    public SSListener07Param(CharStream in) {
        super(in);
    }

    @Override
    public void enterParam(SelescriptParser.ParamContext ctx) {
    }

    @Override
    public void exitParam(SelescriptParser.ParamContext ctx) {
        
        StringBuilder sb = new StringBuilder();
        
        if(ctx.ID() == null ) {
            sb
                    .append(" null , ");
        } else {
            sb
                    .append(AP)
                    .append(ctx.ID().getText())
                    .append(AP).append(" , ");
        }
        
       sb.append( prop.get(ctx.stringval()));
       prop.put(ctx,sb.toString()); 
    }

}
