/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twiceagain.selescript.listeners;

import auto.SelescriptBaseListener;
import com.twiceagain.selescript.configuration.Config;
import com.twiceagain.selescript.configuration.SSListener;
import com.twiceagain.selescript.exceptions.SSException;
import com.twiceagain.selescript.exceptions.SSUndefinedBuiltinException;
import org.antlr.v4.runtime.tree.ParseTreeProperty;

/**
 * Various utilities used by the listener implementation. Handles all the
 * loading/saving/compile tasks so that the implementation can concentrate on
 * the logic needed for compiling. All SSListeners should inherit from this base class.
 * Not private constructor to prevent direct instantiation.
 *
 * @author xavier
 */

public  class SSBaseListener extends SelescriptBaseListener implements SSListener {

    
    
    /**
     * The annotated tree to store the code as we compile the tree.
     */
    protected final ParseTreeProperty<String> prop ;  
    

    /**
     * The configuration object.
     */
    protected Config config ;

    protected SSBaseListener(Config config, ParseTreeProperty<String> prop) {
        this.prop = prop;
        this.config = config;
    }

    private SSBaseListener() {
       throw new SSException("SSBaseListener should not be initialized this way");
    }

    SSBaseListener(String string) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    

   

    /**
     * Check if built-in id provided is valid.Throw exception if not.
     *
     * @param biid
     * @return true or throws
     */
    protected boolean isValidBiids(String biid) {
        if (config.getBuiltinsSet().contains(biid)) {
            return true;
        }
        throw new SSUndefinedBuiltinException("Unrecognized built-in : "
                + biid
                + " . Recognized built-ins are : "
                + config.getBuiltinsSet());
    }

      
    /**
     * Remove first and last character
     * @param s
     * @return 
     */
    public static  String trim2(String s) {
        if(s==null || s.length() <2) return s;
        return s.substring(1, s.length()-1);
    }
    /**
     * Remove first char, if possible.
     * @param s
     * @return 
     */
    public static   String trim1(String s) {
        if(s==null || s.isEmpty()) return s;
        return s.substring(1);
    }

    
    
    
}
