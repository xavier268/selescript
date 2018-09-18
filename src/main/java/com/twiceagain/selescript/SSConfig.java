package com.twiceagain.selescript;

/**
 * Configuration information and related utilities. These information are
 * supposed to remain fixed when the program is running. Live informations can
 * be found in the RuntimeContext object.
 *
 * @author xavier
 */

public class SSConfig {

    private String scriptFileName = null;
    private final String selescriptVersion = "1.0";
    private final String javaVersion = Runtime.version().toString();
    private final Long startMillis = System.currentTimeMillis();
    
    

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
