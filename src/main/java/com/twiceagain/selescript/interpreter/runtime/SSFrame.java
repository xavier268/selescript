/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twiceagain.selescript.interpreter.runtime;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.Set;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

/**
 * A SSFrame contains the context specific to a loop.
 *
 * @author xavier
 */
class SSFrame {

    /**
     * Previous frame, null if first frame.
     */
    private final SSFrame previous;
    private final SSRuntimeContext parent;
    /**
     * Locator.
     */
    private By by = null;

    private final Long startMillis = System.currentTimeMillis();
    private Long maxMillis = null;

    private Integer count = 0;
    private Integer maxCount = null;

    private boolean stopLocal = false;

    private final Deque<WebElement> tovisit = new ArrayDeque<>();
    private final Set<WebElement> visited = new HashSet<>();

    /**
     * Current WebElement inside the loop. Can be null, if none specified.
     */
    private WebElement we = null;

    /**
     * Create a new SSFrame.
     *
     * @param previous - the previous frame, null if none.
     * @param parent - the owning SSRuntimecontext
     */
    public SSFrame(SSFrame previous, SSRuntimeContext parent) {
        this.previous = previous;
        this.parent = parent;

    }

    /**
     * Request exit from local loop as soon as possible.
     */
    public void requestStopLocal() {
        stopLocal = true;
    }

    /**
     * Should we stop now ?
     *
     * @return
     */
    public boolean shouldStop() {

        if (stopLocal) {
            return true;
        }
        if (maxCount != null && count > maxCount) {
            return true;
        }
        if (maxMillis != null && System.currentTimeMillis() > startMillis + maxMillis) {
            return true;
        }

        return false;
    }

    public Integer getCount() {
        return count;
    }

    public WebElement getWe() {
        if (we == null && hasNext()) {
            fetchNext();
        }
        return we;
    }

    public void setBy(By by) {
        this.by = by;
    }

    public void setMaxMillis(Long maxMillis) {
        this.maxMillis = maxMillis;
    }

    public void setMaxCount(Integer maxCount) {
        this.maxCount = maxCount;
    }

    public boolean hasNext() {
        if (by == null) {
            return true; // Should never happen ...
        }
        if (!tovisit.isEmpty()) {
            return true;
        }
        refresh();
        return !tovisit.isEmpty();
    }

    /**
     * Fetch the next element in the loop, if a By was specified. Does nothing
     * otherwise. Increments counts.
     *
     * @return true if success in fetching new element, of BY was null (infinite
     * loop).
     */
    public boolean fetchNext() {
        count++;
        if (by == null) {
            return true;
        }
        if (!hasNext()) {
            return false;
        }
        we = tovisit.pop();
        visited.add(we);
        return true;
    }

    /**
     * Tries to fetch another batch of elements to visit, excluding those
     * already seen.
     */
    private void refresh() {
        if (by == null) {
            return; // should never happen ...
        }
        SearchContext sc;
        if (previous == null) {
            sc = parent.getWd();
        } else {
            sc = previous.getSc();
        }
        sc.findElements(by)
                .stream()
                .filter((WebElement w) -> {
                    return !visited.contains(w);
                })
                .forEach((w) -> {
                    tovisit.add(w);
                });
    }

    /**
     * Get the search context for this frame. Typically used inside the loop, or
     * for the next inner loop. frames.
     *
     * @return - should never be null.
     */
    public SearchContext getSc() {
        if (getWe() != null) {
            return getWe();
        }
        if (previous == null) {
            return parent.getWd();
        } else {
            return previous.getSc();
        }

    }

}
