
package com.twiceagain.selescript.runtime;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;


    /**
     * Iterates over the webelements obtained applying xpath to the provided
     * search context. Since some webelemnts can appear later, it refreshs
     * regularly while iterating.
     */
    public  class WebElementIterator implements Iterable<WebElement>, Iterator<WebElement> {

        private final String xpath;
        private final SearchContext sc;
        private final Deque<WebElement> tovisit = new ArrayDeque<>();
        private final Set<WebElement> visited = new HashSet<>();


        /**
         * Will iterate over all the WebElements found by applying the provided
         * xpath of the provided search context.
         *
         * @param sc
         * @param xpath
         */
        public WebElementIterator(SearchContext sc, String xpath) {            
            this.sc = sc;
            this.xpath = xpath;
        }

        /**
         * Updates the list of WebElements to visit, excluding the ones we
         * already got. It also puts the current search context at the end of
         * the queue.
         */
        protected void refresh() {
            sc.findElements(By.xpath(xpath)).forEach(
                    (WebElement w) -> {
                        if (!visited.contains(w)) {
                            tovisit.add(w);
                        }
                    });
        }

        @Override
        public boolean hasNext() {
            if (tovisit.isEmpty()) {
                refresh();
            }
            return !tovisit.isEmpty();
        }

        /**
         * Warning : calling next() will put the provided WebElement on top of
         * the stack. It is up to the program to remove it with removeLast();
         *
         * @return
         */
        @Override
        public WebElement next() {

            if (!hasNext()) {
                throw new java.util.NoSuchElementException();
            }
            WebElement w = tovisit.pop();
            visited.add(w);
            return w;
        }

        @Override
        public Iterator<WebElement> iterator() {
            return this;
        }

    }