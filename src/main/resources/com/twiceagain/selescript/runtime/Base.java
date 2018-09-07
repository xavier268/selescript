package com.twiceagain.selescript.runtime;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
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
    final protected Map<String, String> symtab = new HashMap<>();

    final protected FrameStack fs = new FrameStack();

    /**
     * Shared logger for all runtime classes.
     */
    static final Logger LOG = LoggerFactory.getLogger("Selescript runtime");
    

 

    /**
     * The generix main method. It is non static, and will be called by the
     * static method from the final scrapper class.
     */
    public void main() {

        final WebDriver w ;
        try {
            w = new RemoteWebDriver(new URL(getGridUrl()), DesiredCapabilities.firefox());
       
            // Add the webdriver to the FrameStack
            fs.setWd(w);
            scrap();
        } catch (Exception ex) {
            LOG.info(ex.getMessage());
        } finally {
            if (fs.getWd() != null) {
                fs.getWd().quit();
            }

        }
    }

}
