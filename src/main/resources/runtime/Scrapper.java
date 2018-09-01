/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twiceagain.selescript.runtime;

import org.openqa.selenium.WebDriver;

/**
 * Base interface for all scrappers.
 * @author xavier
 */
public interface Scrapper {
    
    /**
     * Launch the scrapping.
     * @param wd
     */
    public void scrap(WebDriver wd);
}
