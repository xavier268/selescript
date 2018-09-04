package com.twiceagain.selescript.runtime;

import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Defines access to builtin Methods
 *
 * @author xavier
 */
abstract public class Methods extends Variables {

    /*--------------------------------------------------
     * A dedicated equal that behave well with null values.
     * null eq null = ""
     * null eq a = null
     * a eq null = null
     * a eq b = a.equals(b)
     **/
    protected String eq(String s1, String s2) {
        if (s1 == null && s2 == null) {
            return ""; // true
        }
        if (s1 == null || s2 == null) {
            return null; // false
        }
        if (s1.equals(s2)) {
            return ""; // true
        }
        return null; // false
    }

    /**
     * Not equals.
     *
     * @param s1
     * @param s2
     * @return null(false) or emptyString(true)
     */
    protected String neq(String s1, String s2) {
        return not(eq(s1, s2));
    }

    /*     
     * null ~ null -> null.
     * null ~ p -> null. 
     * s ~ p -> s if matched, null if no match.
     */
    /**
     * Test pattern matching.
     *
     * @param s
     * @param pattern
     * @return
     */
    protected String match(String s, String pattern) {

        if (s == null) {
            return null;
        }
        return (s.matches(pattern)) ? "" : null;

    }

    protected String nomatch(String s1, String s2) {
        return not(match(s1, s2));
    }

    protected String or(String s1, String s2) {
        if (s1 != null) {
            return s1;
        }
        return s2;
    }

    protected String and(String s1, String s2) {
        if (s1 == null) {
            return null;
        }
        return s2;
    }

    /**
     * ---------------------------------------------------------------------
     * Implements the dereferencing operator. Default(null) xpath is current
     * element. Default attribute(null) get the text. If xpth generates multiple
     * elements, keep the first one only.
     *
     * @param attr
     * @param s
     * @return
     *
     */
    protected String at(String attr, String s) {
        try {
            // Default xpath is current element.
            if (s == null) {
                s = ".";
                if (wes.size() <= 1) { // If we are at the root, default to body.
                    s = ".//body";
                }
            }
            // If xpath not found, return null.
            List<WebElement> lwe = wes.getLast().findElements(By.xpath(s));
            if (lwe.isEmpty()) {
                return null;
            }

            if (attr == null) {
                return lwe.get(0).getText();
            } else {
                return lwe.get(0).getAttribute(attr);
            }
        } catch (Exception ex) {
            LOG.info("Error while dereferencing xpath : " + s + " with attribute " + attr, ex);
            return null;
        }
    }

    /**
     * ---------------------------------------------------------------
     *
     * Click on the first WebElement that matches the provided xpath.
     *
     * @param wd
     * @param xpath
     */
    protected void click(WebDriver wd, String xpath) {
        click(wd, xpath, false);
    }

    /**
     * --------------------------------------------------------------
     *
     * Click on the first WebElement that matches the provided xpath.
     *
     * @param wd
     * @param xpath
     * @param linkShouldGo - Set to true if the link we click is expected to
     * trigger a page reload.
     *
     */
    protected void click(WebDriver wd, String xpath, boolean linkShouldGo) {
        if (xpath == null) {
            return;
        }
        List<WebElement> lwe = wes.getLast().findElements(By.xpath(xpath));
        if (lwe.isEmpty()) {
            return;
        }
        lwe.get(0).click();
        if (linkShouldGo) {
            // Wait up to 5 seconds for the page to disappear.
            (new WebDriverWait(wd, 5)).until(ExpectedConditions.stalenessOf(lwe.get(0)));
        }
    }

    protected void clickw(WebDriver wd, String xpath) {
        click(wd, xpath, true);
    }

    /*----------------------------------------------------
     * Emits the provided fields to stout.
     **/
    public void emit(String... par) {
        Integer count = 0;
        List<String> ls = new ArrayList<>();
        for (int i = 0; i < par.length; i += 2) {
            count++;
            StringBuilder sb = new StringBuilder();
            if (par[i] == null) {
                sb.append("\"F_").append(count).append("\":");
            } else {
                sb.append("\"").append(par[i]).append("\":");
            }
            sb.append("\"").append(par[i + 1]).append("\"");
            ls.add(sb.toString());
        }
        System.out.printf("%n{%s}%n", String.join(",", ls));
    }

    /*---------------------------------------------------
     * A dedicated not that behaves well will null values
     * ! null = ""
     * ! a = null
     **/
    protected String not(String s) {
        return (s == null) ? "" : null;
    }

    /*------------------------------------------------------------
     * A dedicated concatenation that behaves well with null values.
     * null + null = null
     * null + a = a
     * a + null = a
     * a + b = ab
     **/
    protected String plus(String s1, String s2) {
        if (s1 == null) {
            return s2;
        }
        if (s2 == null) {
            return s1;
        }
        return s1 + s2;
    }

}
