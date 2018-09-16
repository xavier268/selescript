package com.twiceagain.selescript.runtime;

import com.mongodb.async.client.MongoClient;
import com.mongodb.async.client.MongoClients;
import com.mongodb.async.client.MongoCollection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import org.bson.Document;
import org.openqa.selenium.WebDriver;
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
    /**
     * Stack frame for the loops.
     */

    final protected FrameStack fs = new FrameStack();

    /**
     * Shared logger for all runtime classes.
     */
    static final Logger LOG = LoggerFactory.getLogger("Selescript runtime");

    /**
     * The client object to connect to Mongo. The getMongoConnectionString
     * should be defined in the generated class
     */
    protected MongoClient CLIENT = null;
    protected MongoCollection<Document> COLLECTION = null;
    

    /**
     * The generix main method.It is non static, and will be called by the
     * static method from the final scrapper class.
     *
     * @param rtc - the runtime Configuration object
     */
    public void main(RuntimeConfig rtc) {

        final WebDriver w;
        try {
            if (rtc.needsInitWebdriver()) {
                w = new RemoteWebDriver(new URL(rtc.getGridUrl()), rtc.getBrowserCapabilities());

                // Add the webdriver to the FrameStack
                fs.setWd(w);
            }
            // init the database
            if (rtc.needsInitMongoDb()) {
                initDb(rtc);
            }
            // init input file for external parameters.
            fs.initInput(rtc.getInputParameterFileName());

            scrap();
        } catch (Exception ex) {
            LOG.info(ex.getMessage(), ex);
        } finally {
            if (rtc.needsInitWebdriver()) {
                if (fs.getWd() != null) {
                    fs.getWd().quit();
                }
            }
            fs.closeInput();
            

        }
    }

    protected void initDb(RuntimeConfig rtc) {
        if (CLIENT == null) {
            CLIENT = MongoClients.create(rtc.getMongoConnectionString());
        }
        COLLECTION = CLIENT.getDatabase(rtc.getMongoDbName()).getCollection(rtc.getMongoColName());
    }

   
    

}
