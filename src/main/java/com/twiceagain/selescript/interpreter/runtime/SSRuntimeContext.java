/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twiceagain.selescript.interpreter.runtime;

import com.twiceagain.selescript.SSConfig;
import com.twiceagain.selescript.exceptions.SSConfigurationException;
import com.twiceagain.selescript.exceptions.SSSyntaxException;
import java.io.Closeable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

/**
 * The class that maintains the context of the current running program. There
 * should be exactly one RuntimeContext per running script. It is created by
 * providing the existing SSConfig object.
 *
 * @author xavier
 */
public class SSRuntimeContext implements Closeable {

    private final SSConfig config;
    private WebDriver wd = null;
    private final Deque<SSFrame> frames = new ArrayDeque<>();
    private final Map<String, String> symbols = new HashMap<>();
    private final SSBuiltins biids = new SSBuiltins(this);
    private boolean stopGlobal = false;

    public SSRuntimeContext(final SSConfig config) {
        this.config = config;
    }

    /**
     * Request global interpreter stop as soon as possible.
     */
    public void requestStopGlobal() {
        stopGlobal = true;
    }

    public boolean shouldStop() {
        if (stopGlobal) {
            return true;
        }
        if (frames.isEmpty()) {
            return false;
        }
        return frames.getLast().shouldStop();
    }

    /**
     * Lazy getter for the webdriver.
     *
     * @return
     */
    public WebDriver getWd() {
        if (wd != null) {
            return wd;
        }
        try {
            wd = new RemoteWebDriver(new URL(config.getGridUrl()), config.getBrowserOptions());
        } catch (MalformedURLException ex) {
            throw new SSConfigurationException(
                    "Could not create remote driver with url:"
                    + config.getGridUrl(), ex);
        }
        return wd;
    }

    /**
     * Get serach context.
     *
     * @return - never null.
     */
    public SearchContext getSc() {
        if (frames.isEmpty()) {
            return getWd();
        }
        return frames.getLast().getSc();
    }

    /**
     * Cleanup before exit.
     *
     */
    @Override
    public void close() {
        if (wd != null) {
            wd.quit();
        }
    }

    /**
     * Initialize a new loop. The first element is NOT fetched.
     *
     * @param by
     * @param maxMillis
     * @param maxCount
     */
    public void loopStart(By by, Long maxMillis, Integer maxCount) {
        SSFrame p = frames.isEmpty() ? null : frames.getLast();

        SSFrame f = new SSFrame(p, this);
        f.setBy(by);
        f.setMaxCount(maxCount);
        f.setMaxMillis(maxMillis);
        frames.add(f);

    }

    /**
     * Should be called in a while loop. Fetch the next element. Throws if
     * called without loopStart.
     *
     * @return true if we should proceed. False to exit the while loop.
     */
    public boolean loopWhile() {
        if (shouldStop()) {
            return false;
        }
        if (frames.isEmpty()) {
            throw new SSSyntaxException(
                    "You should not call loop() outisde of an initialized loop.");
        }
        frames.getLast().getNext();
        return frames.getLast().hasNext();
    }

    /**
     * Cleanup loop context.
     */
    public void loopEnd() {
        if (frames.isEmpty()) {
            throw new SSSyntaxException(
                    "Trying to close a loop that never started ?!");
        }
        frames.removeLast();
    }

    public String getId(String id) {
        return symbols.get(id);
    }

    public void putId(String id, String value) {
        symbols.put(id, value);
    }

    public String getBiid(String id) {
        return biids.get(id);
    }

    public void putBiid(String biid, String value) {
        biids.put(biid, value);
    }

    public SSConfig getConfig() {
        return config;
    }

    public String getCount() {
        Integer c = 0;
        if (!frames.isEmpty()) {
            c = frames.getLast().getCount();
        }
        return c.toString();
    }

}
