package com.twiceagain.selescript.runtime;

import java.util.Deque;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Abstract class for all builtin-variables;
 * @author xavier
 */
public  abstract class BaseVariable {
    
     public Deque<SearchContext> wes ;

    public BaseVariable(Deque<SearchContext> wes) {
        this.wes = wes;
    }       
    
    /**
     * Get the webdriver as the first element in the wes deque.
     * @return 
     */
    protected WebDriver getWd() {
        if(wes == null || wes.isEmpty()) throw new RuntimeException(
                "Unexpected null or empty wes. Cannot dind current webdriver.");
        return (WebDriver) wes.getFirst();
    
    }
    
     abstract public  String get() ;
     abstract public  void set(String value);
    
    
}
