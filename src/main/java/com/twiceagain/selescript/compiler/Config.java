/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twiceagain.selescript.compiler;

import java.util.Collection;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * Configuration information.
 *
 * @author xavier
 */
public class Config {
    
    public static String getVersion() {
        return "1.1";
    }
    
    public static String getHeaders() {
        return new Scanner(Config.class.getClassLoader().getResourceAsStream("builtins/headers"), "UTF-8")
                .useDelimiter("\\A")
                .next();
    }
    
    public static String getBuiltinsMethods() {
        return new Scanner(Config.class.getClassLoader().getResourceAsStream("builtins/builtins"), "UTF-8")
                .useDelimiter("\\A")
                .next();
    }
    
    public static Collection<String> getBuiltinsList() {
        Set<String> rr = new HashSet<>();
        new Scanner(Config.class.getClassLoader().getResourceAsStream("builtins/builtins.list"), "UTF-8")
                .tokens()
                .filter((String x)->{return x.startsWith("$");})
                .forEach(rr::add);               
        return rr;
        
    }
    
}


