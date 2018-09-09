/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twiceagain.selescript.runtime;

import org.openqa.selenium.Capabilities;

/**
 * Base interface for all scrappers.
 * @author xavier
 */
public interface Scrapper {
    
    /**
     * Launch the scrapping.     * 
     */
    public void scrap();
    
    public String getGridUrl();
    public Capabilities getBrowserCapabilities();
}
