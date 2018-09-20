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

    private boolean stopRequested = false;

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
     * Should we stop now ?
     *
     * @return
     */
    public boolean shouldStop() {

        if (stopRequested) {
            return true;
        }
        if (maxCount != null && count >= maxCount) {
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
            getNext();
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
            return false;
        }
        if (!tovisit.isEmpty()) {
            return true;
        }
        refresh();
        return !tovisit.isEmpty();
    }
    /**
     * Fetch the next element in the loop.
     */
    public void getNext() {
        if(!hasNext()) return;
        we = tovisit.pop();
    }

    private void refresh() {
        if (by == null) {
            return;
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