package com.twiceagain.selescript.runtime;

import org.openqa.selenium.WebDriver;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Base class for all builtin-variables;
 *
 * @author xavier
 */
public class BaseVariable {

    public FrameStack fs;

    protected BaseVariable(FrameStack fs) {
        this.fs = fs;
    }

    /**
     * Get the webdriver as the first element in the wes deque.
     *
     * @return
     */
    protected WebDriver getWd() {
        if (fs == null) {
            throw new RuntimeException(
                    "Unexpected null  frameStack. Cannot find current webdriver.");
        }
        return (WebDriver) fs.getWd();
    }

}
