package com.twiceagain.selescript.runtime;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * A class that maintains state within loops and provide loop management
 * utilities. You typically have a single FrameStack per program. To start a new
 * loop, you call prepare(). While loop(), you loop through the statements. When
 * you are finished, you call cleanUp().
 *
 * During the loop, you may start /cleanup nested loops.
 *
 * At anytime, you may get contextual info about the current WebElement, count,
 * ...
 *
 * @author xavier
 */
public class FrameStack {

    private final Deque<Frame> frames = new ArrayDeque<>();

    private WebDriver wd;

    public FrameStack() {
    }

    public FrameStack setWd(WebDriver wd) {
        this.wd = wd;
        return this;
    }

    public int size() {
        return frames.size();
    }

    /**
     * Prepare for a new loop.
     *
     * @param params The possible keys are : null or x for xpath, count for nbr
     * of loops, ms for millis, s for seconds, h for hour, m for minutes ...
     */
    public void prepare(Map<String, String> params) {
        Frame f = new Frame();
        for (String k : params.keySet()) {
            if (k == null) {
                k = "x";
            }
            long l;
            switch (k) {
                case "x":
                case "xpath":
                    f.setXpath(params.get(k));
                    break;
                case "ms":
                case "millis":
                case "millisecond":
                case "milliseconds":
                    if (params.get(k) == null) {
                        break;
                    }
                    l = Long.decode(params.get(k));
                    f.setMaxMillis(l + f.getMaxMillis());
                    break;
                case "s":
                case "sec":
                case "second":
                case "seconds":
                    if (params.get(k) == null) {
                        break;
                    }
                    l = 1000 * Long.decode(params.get(k));
                    f.setMaxMillis(l + f.getMaxMillis());
                    break;
                case "m":
                case "min":
                case "minute":
                case "minutes":
                    if (params.get(k) == null) {
                        break;
                    }
                    l = 1000 * 60 * Long.decode(params.get(k));
                    f.setMaxMillis(l + f.getMaxMillis());
                    break;
                case "h":
                case "hour":
                case "hours":
                    if (params.get(k) == null) {
                        break;
                    }
                    l = 1000 * 60 * 60 * Long.decode(params.get(k));
                    f.setMaxMillis(l + f.getMaxMillis());
                    break;

            }
        }
        frames.add(f);
    }

    /**
     * Retrieve the next element, if available.
     *
     * @return false if we shouldStop.
     */
    public boolean loop() {
        if (frames.getLast().shouldStop()) {
            return false;
        }
        return frames.getLast().hasNext();
    }

    /**
     * Can only be called inside a loop.
     *
     * @return
     */
    public WebElement getWe() {
        return frames.getLast().getCurrent();
    }

    /**
     * Get current search context. Can be called outside a loop.
     *
     * @return
     */
    public SearchContext getSc() {
        if (frames.isEmpty()) {
            return getWd();
        }
        return getWe();
    }

    public void cleanup() {
        frames.removeLast();
    }

    public WebDriver getWd() {
        return wd;
    }

    /**
     * Frame contains all the data and context specific to a loop. Set maxMillis
     * or maxCount to 0 for unlimited loops.
     */
    protected static class Frame {

        private SearchContext sc;
        private String xpath;
        private final long startMillis = System.currentTimeMillis();
        private long maxMillis = 0L;
        private long count = 0L;
        private long maxCount = 0L;
        private boolean shouldStop = false;
        private final Set<WebElement> visited = new HashSet<>();
        private final Deque<WebElement> toVisit = new ArrayDeque<>();
        private WebElement current; // Last WebElement delivered by next().

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
            if (maxCount != 0 && count > maxCount) {
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
            sc.findElements(By.xpath(xpath)).forEach(
                    (WebElement w) -> {
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

}
