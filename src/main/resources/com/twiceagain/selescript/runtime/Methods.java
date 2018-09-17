package com.twiceagain.selescript.runtime;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.bson.Document;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Defines access to builtin Methods
 *
 * @author xavier
 */
abstract public class Methods extends Base implements Scrapper {

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
                if (fs.size() == 0) { // If we are at the root, default is body.
                    s = ".//body";
                }
            }
            // If xpath not found, return null.
            List<WebElement> lwe = fs.getSc().findElements(By.xpath(s));
            if (lwe.isEmpty()) {
                return null;
            }

            if (attr == null) {
                return lwe.get(0).getText();
            } else {
                return lwe.get(0).getAttribute(attr);
            }
        } catch (Exception ex) {
            //LOG.info("Error while dereferencing xpath : " + s + " with attribute " + attr, ex);
            return null;
        }
    }

    /**
     * --------------------------------------------------------------
     *
     * Click on the first WebElement that matches the provided xpath. No path
     * means using current context (error if current context is not a WebElement
     * ).
     *
     * @param xpath
     * @param linkShouldGo - Set to true if the input we click is expected to
     * trigger a page reload.
     *
     */
    protected void click(String xpath, boolean linkShouldGo) {

        WebElement link;

        if (xpath != null) {
            List<WebElement> lwe = fs.getSc().findElements(By.xpath(xpath));
            if (lwe.isEmpty()) {
                return;
            } else {
                link = lwe.get(0);
            }
        } else {
            link = fs.getWe();
        }

        if (link != null) {
            link.click();
        }

        if (linkShouldGo) {
            // Wait up to 2 seconds for the page to disappear.
            (new WebDriverWait(fs.getWd(), 2)).until(ExpectedConditions.stalenessOf(link));
        }
    }

    /**
     * Submit the form enclosing the target element.
     *
     * @param xpath - xpath for target element, or current element if null
     * @param linkShouldGo - if true, will wait for target element to become
     * stale.
     */
    protected void submit(String xpath, boolean linkShouldGo) {

        WebElement link;

        if (xpath != null) {
            List<WebElement> lwe = fs.getSc().findElements(By.xpath(xpath));
            if (lwe.isEmpty()) {
                return;
            } else {
                link = lwe.get(0);
            }
        } else {
            link = fs.getWe();
        }

        if (link != null) {
            link.submit();
        }

        if (linkShouldGo) {
            // Wait up to 2 seconds for the page to disappear.
            (new WebDriverWait(fs.getWd(), 2)).until(ExpectedConditions.stalenessOf(link));
        }
    }

    /*----------------------------------------------------
     * Emits the provided fields to stout.
     **/
    protected void emit(String... par) {
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

    /**
     * Type the text in the provided element. The xpath is 'x' or the default key. The
     * raw text is provided with the 't' key. The 'u' key will url encode the
     * text. Multiple text fragement is okay. Mixing encoded and raw text is
     * okay. Only the last xpath is kept. Xpath defaults to current webelement.
     * Text defaults to empty string.
     *
     * @param par - an even number of string aruments, alternating key and
     * value.
     */
    protected void send(String... par) {
        String xpath = null;
        String t = "";
        for (int i = 0; i < par.length; i += 2) {
            if (par[i] == null || "x".equals(par[i])) {
                xpath = par[i + 1];
                continue;
            }
            if ("t".equals(par[i])) {
                t = t + par[i + 1];
                continue;
            }
            if ("u".equals(par[i])) {
                try {
                    t = t + URLEncoder.encode(par[i + 1], "UTF-8");
                } catch (UnsupportedEncodingException ex) {
                    throw new RuntimeException("Unexpected runtime exception", ex);
                }
                continue;
            }
            throw new RuntimeException("Unrecognized key parameter in type statement : " + par[i]);
        }

        WebElement input;

        if (xpath != null) {
            List<WebElement> lwe = fs.getSc().findElements(By.xpath(xpath));
            if (lwe.isEmpty()) {
                return;
            } else {
                input = lwe.get(0);
            }
        } else {
            input = fs.getWe();
        }

        if (input != null) {
            try {
            input.submit();
            } catch(NoSuchElementException ex) {
                // When there are no enclosing form, log error and continue.
                LOG.error("Trying to submit a forma that could not be found");
            }
        }

    }

    protected void mongo(String... par) {
        Document doc = new Document();
        for (int i = 0; i < par.length; i += 2) {
            if (par[i] == null) {
                // Should never happen, since check done at compile time.                
                throw new RuntimeException("You must tag all fields when sending to db ");
            }
            if (par[i + 1] == null) {
                continue;
            }
            doc.append(par[i], par[i + 1]);
        }
        Document dd = new Document("SSRecord", doc);

        COLLECTION.insertOne(dd, (Void t, Throwable ex) -> {
            if (ex != null) {
                throw new RuntimeException(
                        "Error after insertiing a document in mongo db",
                        ex);
            }
        });

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

    /**
     * Check if pattern can be found. By convention, found(null,xx) is always
     * null, found (xx,null) is always true and found (null,null) is null.
     *
     * @param s
     * @param pattern
     * @return - found text, or null if not found.
     */
    protected String find(String s, String pattern) {

        if (s == null) {
            return null;
        }
        if (pattern == null) {
            return s;
        }

        Matcher m = Pattern.compile(pattern).matcher(s);
        if (m.find()) {
            return m.group();
        } else {
            return null;
        }

    }

    /**
     * Replace ALL pattern occurences with replacement string.
     *
     * r(null,p,r) is null, r(s,null,r) is s, r(s,p,null) is r(s,p,"").
     * r(null,null,_) is null.
     *
     * @param s
     * @param pattern
     * @param replacement
     * @return see above
     */
    protected String replace(String s, String pattern, String replacement) {

        if (s == null) {
            return null;
        }
        if (pattern == null) {
            return s;
        }
        String r = (replacement == null) ? "" : replacement;

        Matcher m = Pattern.compile(pattern).matcher(s);
        if (m.find()) {
            return m.replaceAll(replacement);
        } else {
            return null;
        }

    }

}
