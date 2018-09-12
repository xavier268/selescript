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
    protected MongoCollection<Document> COLLECTION;

    /**
     * The generix main method.It is non static, and will be called by the
     * static method from the final scrapper class.
     *
     * @param initWebdriver - do we need to init WebDriver ?
     * @param initMongo - do we need to init mongo ?
     */
    public void main(boolean initWebdriver, boolean initMongo) {

        final WebDriver w;
        try {
            if (initWebdriver) {
                w = new RemoteWebDriver(new URL(getGridUrl()), getBrowserCapabilities());

                // Add the webdriver to the FrameStack
                fs.setWd(w);
            }
            // init the database
            if (initMongo) {
                initDb();
            }

            scrap();
        } catch (Exception ex) {
            LOG.info(ex.getMessage());
        } finally {
            if (initWebdriver) {
                if (fs.getWd() != null) {
                    fs.getWd().quit();
                }
            }

        }
    }

    protected void initDb() {
        if (CLIENT == null) {
            CLIENT = MongoClients.create(getMongoConnectionString());
        }
        COLLECTION = CLIENT.getDatabase(getMongoDbName()).getCollection(getMongoColName());
    }

}
