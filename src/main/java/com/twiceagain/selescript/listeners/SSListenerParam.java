/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twiceagain.selescript.listeners;

import auto.SelescriptParser;
import com.twiceagain.selescript.configuration.Config;
import static com.twiceagain.selescript.configuration.Config.AP;
import com.twiceagain.selescript.configuration.SSListener;
import org.antlr.v4.runtime.tree.ParseTreeProperty;

/**
 * Handles parameters for functions. Parameters are stored in the prop as a
 * comma separated list alternating fieldname and field content. When no field
 * name was specified, null is used. When no parametr at all, empty string.
 *
 * @author xavier
 */
public  class SSListenerParam extends SSBaseListener implements SSListener {

    public SSListenerParam(Config config, ParseTreeProperty<String> prop) {
        super(config, prop);
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
