package com.twiceagain.selescript.runtime;

import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;

/**
 * Defines access to Builtin Variables.
 *
 * @author xavier
 */
abstract public class Variables extends Base {

    /**
     * Getting the value (StringVal) of a Builtin Variable.
     *
     * @param wd - WebDriver for the window.
     * @param sc - WebElement currently targeted (can be null )
     * @param varName - Name of the BIID (prefixed with the dollar sign).
     * @return - A stringVal value.
     *
     */
    protected static String biidGet(WebDriver wd, SearchContext sc, String varName) {
        if (varName == null || varName.isEmpty()) {
            throw new RuntimeException("Calling a built-in method with no name ?!");
        }
        String sw = varName;
        if (!sw.startsWith("$")) {
            sw = "$" + sw;
        }
        switch (sw) {
            case "$url":
                if (wd == null) {
                    throw new RuntimeException("Cannot read $url from an empty window");
                }
                return wd.getCurrentUrl();
            default:
                throw new RuntimeException("Trying to get unrecogized built-in method name : " + sw);
        }
    }

    /**
     * Setting the value(s) of a builtin variable.
     *
     * @param wd - WebDriver
     * @param sc - Search Context, can be null.
     * @param par - list of input parameters.
     * @param varName - the builtin method name (prefixed with $ sign)
     *
     */
    protected static void biidSet(WebDriver wd, SearchContext sc, String varName, String par) {
        if (varName == null || varName.isEmpty()) {
            throw new RuntimeException("Calling a built-in method with no name ?!");
        }
        String sw = varName;
        if (!sw.startsWith("$")) {
            sw = "$" + sw;
        }
        switch (sw) {
            case "$url":
                if (wd == null) {
                    throw new RuntimeException("Cannot set an url on an empty window");
                }
                if (par == null || par.isEmpty()) {
                    throw new RuntimeException("Trying to set $url to a null value");
                }
                try {
                    wd.get(par);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
                break;
            default:
                throw new RuntimeException("Trying to set an unrecogized built-in method name : " + sw);
        }
    }

}
