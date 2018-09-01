package com.twiceagain.selescript.runtime;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Base class for runtime.
 *
 * @author xavier
 */
abstract public class Base implements Scrapper {

    /**
     * Symbol table.
     */
    protected Map<String, String> symtab = new HashMap<>();
    /**
     * Stack(deque) holding current search context at the top, and wd at the
     * bottom.
     */
    protected Deque<SearchContext> wes = new ArrayDeque<>();

    /**
     * Shared logger for all runtime classes.
     */
    static final Logger LOG = LoggerFactory.getLogger("Selescript runtime");

    /**
     * The generix main method. It is non static, and will be called by the
     * static method from the final scrapper class.
     */
    public void main() {
        final WebDriver wd = new RemoteWebDriver(DesiredCapabilities.firefox());
        // wd.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);//DEBUG
        // wd.get("http://www.google.fr"); // DEBUG
        try {
            wes.clear();
            wes.add(wd);
            scrap(wd);
        } catch (Exception ex) {
            LOG.info(ex.getMessage());
        } finally {
            wes.clear();
            wd.quit();
        }
    }

}
