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
 * Frame contains all the data and context specific to a loop. Set maxMillis or
 * maxCount to 0 for unlimited loops.
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
    /**
     * Last WebElement delivered by next(), or webElement from previous frame.
     */
    private WebElement current;
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
        maxMillis = max;
        return this;
    }

    public long getMaxMillis() {
        return maxMillis;
    }

    public Frame setMaxCount(long max) {
        maxCount = max;
        return this;
    }

    public long getMaxCount() {
        return maxCount;
    }

    public String getXpath() {
        return xpath;
    }

    public Frame setXpath(String x) {
        xpath = x;
        return this;
    }

    public long getCount() {
        return count;
    }

    public long incrementCount() {
        count++;
        return count;
    }

    /**
     * Return the search context used internally by this Frame. It is the
     * current WebElement of the enclosing frame, or the WebDriver if no
     * enclosing frame. Should never be null.
     *
     * @return
     */
    public SearchContext getSc() {
        if (previous == null) {
            return parent.getWd();
        } else {
            SearchContext s = previous.getCurrent();
            if (s == null) {
                return parent.getWd();
            } else {
                return s;
            }
        }
    }

    /**
     * Try to get the current WebElement within an instantiated loop. Should
     * normally never return null within a while(xx.loop()){ ... }. Could be
     * null if no xpath loops above.
     *
     * @return
     */
    public WebElement getCurrent() {
        if (current == null && previous != null) {
            current = previous.getCurrent();
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
        if (maxCount != 0 && count > maxCount) {
            return true;
        }
        return (maxMillis != 0 && System.currentTimeMillis() > startMillis + maxMillis);
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
     * Updates the list of WebElements to visit, excluding the ones we already
     * got, base upon wd or the current searchcontext of the previous frame. If
     * xpath is null does nothing.
     */
    void refresh() {
        if (xpath == null) {
            return;
        }
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
        current = w;
        return w;
    }

}
