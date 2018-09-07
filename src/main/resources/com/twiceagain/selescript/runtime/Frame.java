/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twiceagain.selescript.runtime;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.Set;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

/**
 * Frame contains all the data and context specific to a loop. Set maxMillis
 * or maxCount to 0 for unlimited loops.
 */
class Frame {

    private String xpath;
    private final long startMillis = System.currentTimeMillis();
    private long maxMillis = 0L;
    private long count = 0L;
    private long maxCount = 0L;
    private boolean shouldStop = false;
    private final Set<WebElement> visited = new HashSet<>();
    private final Deque<WebElement> toVisit = new ArrayDeque<>();
    private WebElement current; // Last WebElement delivered by next().
    private final Frame previous;
    private final FrameStack parent;

    public Frame(Frame previous, FrameStack parent) {        
        this.previous = previous;
        this.parent = parent;
    }

    /**
     * You set it only once. Further setting are ignored. Unset means 0.
     *
     * @param max
     * @return
     */
    public Frame setMaxMillis(long max) {
        if (maxMillis == 0) {
            maxMillis = max;
        }
        return this;
    }

    public long getMaxMillis() {
        return maxMillis;
    }

    public Frame setMaxCount(long max) {
        if (maxCount == 0) {
            maxCount = max;
        }
        return this;
    }

    public long getMaxCount() {
        return maxCount;
    }

    public Frame setXpath(String x) {
        if (xpath == null) {
            xpath = x;
        }
        return this;
    }

    public long getCount() {
        return count;
    }

    /**
     * Return the serach context used internally by this Frame. It is the
     * current WebElement of the enclosing frame, or the WebDriver if no
     * enclosing frame.
     *
     * @return
     */
    public SearchContext getSc() {
        if (previous == null) {
            return parent.getWd();
        } else {
            return previous.getCurrent();
        }
    }

    /**
     * Try to get the current WebElement within an instantiated loop. Should
     * normally never return within a while(xx.loop()){ ... }
     *
     * @return
     */
    public WebElement getCurrent() {
        if (current == null) {
            return next();
        }
        return current;
    }

    /**
     * Check time/count limits.
     *
     * @return
     */
    public boolean shouldStop() {
        if (shouldStop) {
            return true;
        }
        if (maxCount != 0 && count >= maxCount) {
            return true;
        }
        if (maxMillis != 0 && System.currentTimeMillis() > startMillis + maxMillis) {
            return true;
        }
        return false;
    }

    /**
     * Stop this frame.
     *
     * @return
     */
    public Frame requestStop() {
        shouldStop = true;
        return this;
    }

    /**
     * Updates the list of WebElements to visit, excluding the ones we
     * already got.
     */
    void refresh() {
        getSc().findElements(By.xpath(xpath)).forEach((WebElement w) -> {
            if (!visited.contains(w)) {
                toVisit.add(w);
            }
        });
    }

    /**
     * Do we have more ? If not, try refreshing before answering no.
     *
     * @return
     */
    public boolean hasNext() {
        if (toVisit.isEmpty()) {
            refresh();
        }
        return !toVisit.isEmpty();
    }

    /**
     * Next will generate the new WebElement. It will keep it in the current
     * variable for later retrieval.
     *
     * @return
     */
    public WebElement next() {
        if (!hasNext()) {
            throw new java.util.NoSuchElementException();
        }
        WebElement w = toVisit.pop();
        visited.add(w);
        count++;
        current = w;
        return w;
    }
    
}
