package com.twiceagain.selescript.runtime;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;

/**
 * Runtime configuration object. It is creted and configureted at compiletime,
 * then some options can be overriden with runtime cli options.
 *
 * @author xavier
 */
public class RuntimeConfig {

    private boolean initWebdriver = false;
    private boolean initMongoDb = false;
    private String gridUrl;
    private String browser;
    private String inputParameterFileName;
    private String mongoConnectionString;
    private String mongoDbName;
    private String mongoColName;
    private String selescriptVersion;
    private long buildMillis;
    private String seleniumVersion;
    private String buildDate;

    public boolean needsInitWebdriver() {
        return initWebdriver;
    }

    public RuntimeConfig setInitWebdriver(boolean iinitWebdriver) {
        this.initWebdriver = iinitWebdriver;
        return this;
    }

    public boolean needsInitMongoDb() {
        return initMongoDb;
    }

    public RuntimeConfig setInitMongoDb(boolean initMongoDb) {
        this.initMongoDb = initMongoDb;
        return this;
    }

    public String getGridUrl() {
        return gridUrl;
    }

    public RuntimeConfig setGridUrl(String g) {
        gridUrl = g;
        return this;
    }

    public RuntimeConfig setBrowser(String browser) {
        this.browser = browser;
        return this;
    }

    public Capabilities getBrowserCapabilities() {

        if ("chrome".equals(browser)) {
            return new ChromeOptions();
        }
        return new FirefoxOptions();

    }

    public String getInputParameterFileName() {
        return inputParameterFileName;
    }

    public RuntimeConfig setInputParameterFileName(String name) {
        inputParameterFileName = name;
        return this;
    }

    public String getMongoConnectionString() {
        return mongoConnectionString;
    }

    public String getMongoDbName() {
        return mongoDbName;
    }

    public String getMongoColName() {
        return mongoColName;
    }

    public RuntimeConfig  setMongoConnectionString(String mongoConnectionString) {
        this.mongoConnectionString = mongoConnectionString;
        return this;
    }

    public RuntimeConfig setMongoDbName(String mongoDbName) {
        this.mongoDbName = mongoDbName;
        return this;
    }

    public RuntimeConfig setMongoColName(String mongoColName) {
        this.mongoColName = mongoColName;
        return this;
    }
    
    public RuntimeConfig setSelescriptVersion(String v) {
        selescriptVersion = v;
        return this;
    }
    
    public String getSelescriptVersion() {
        return selescriptVersion;
    }
    
    public RuntimeConfig setBuildMillis(long t) {
        buildMillis = t;
        return this;
    }
    
    public long getBuildMillis() {
        return buildMillis;
    }
    
    public RuntimeConfig setSeleniumVersion(String v) {
        seleniumVersion = v;
        return this;
    }
    
    public String getSeleniumVersion() {
        return seleniumVersion;
    }
    
    public RuntimeConfig setBuildDate(String d){
        buildDate = d;
        return this;
        
    }
    
    public String getBuildDate() {
        return buildDate;
    }
    
    public RuntimeConfig parseArgs(String[] args){
        // TODO 
        return this;
    }

}
