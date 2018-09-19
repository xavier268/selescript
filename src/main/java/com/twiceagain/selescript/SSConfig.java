package com.twiceagain.selescript;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;

/**
 * Configuration information and related utilities. These information are
 * supposed to remain fixed when the program is running. Live informations can
 * be found in the RuntimeContext object.
 *
 * @author xavier
 */
public class SSConfig {

    // Read-write
    private String gridUrl = "http://localhost:4444/wd/hub";
    private String browser = "firefox";

    private String mongoConnexionString = "mongodb://localhost:27017";
    private String mongoDatabase = "mongodb";
    private String mongoCollection = "mongocol";

    private String scriptFileName = null;
    // Read only
    private final String selescriptVersion = "1.0";
    private final String javaVersion = Runtime.version().toString();
    private final Long startMillis = System.currentTimeMillis();

    public String getGridUrl() {
        return gridUrl;
    }

    public void setGridUrl(String gridUrl) {
        this.gridUrl = gridUrl;
    }

    public void setBrowserFirefox() {
        this.browser = "firefox";
    }

    public void setBrowserChrome() {
        this.browser = "chrome";
    }

    public Capabilities getBrowserOptions() {
        if ("firefox".equals(browser)) {
            return new FirefoxOptions();
        }
        return new ChromeOptions();
    }

    public String getMongoConnexionString() {
        return mongoConnexionString;
    }

    public void setMongoConnexionString(String mongoConnexionString) {
        this.mongoConnexionString = mongoConnexionString;
    }

    public String getMongoDatabase() {
        return mongoDatabase;
    }

    public void setMongoDatabase(String mongoDatabase) {
        this.mongoDatabase = mongoDatabase;
    }

    public String getMongoCollection() {
        return mongoCollection;
    }

    public void setMongoCollection(String mongoCollection) {
        this.mongoCollection = mongoCollection;
    }

    public String getScriptFileName() {
        return scriptFileName;
    }

    public void setScriptFileName(String scriptFileName) {
        this.scriptFileName = scriptFileName;
    }

    public String getSelescriptVersion() {
        return selescriptVersion;
    }

    public String getJavaVersion() {
        return javaVersion;
    }

    public Long getStartMillis() {
        return startMillis;
    }

}
