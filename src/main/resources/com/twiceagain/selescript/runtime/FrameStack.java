package com.twiceagain.selescript.runtime;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
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

    private Scanner inputScanner;
    private String lastInputFileName;

    public FrameStack() {
    }

    public FrameStack setWd(WebDriver wd) {
        this.wd = wd;
        return this;
    }

    /**
     * Init the input scanner used to retrieve lines of inputs.
     *
     * @param fileName - the text file that contains the input lines.
     * @return
     */
    public FrameStack initInput(String fileName) {
        lastInputFileName = fileName;
        if (fileName == null || fileName.isEmpty()) {
            return null;
        }
        try {
            inputScanner = new Scanner(new File(fileName), "UTF-8");
        } catch (FileNotFoundException ex) {
            try {
                throw new RuntimeException("Could not read input from file : " + Paths.get(fileName).toFile().getCanonicalPath(), ex);
            } catch (IOException ex1) {
                throw new RuntimeException("Could not read input from file :" + fileName, ex1);
            }
        }
        return this;
    }

    /**
     * Reset input, reading again from the same file from the beginning.
     *
     * @return this
     */
    public FrameStack resetInput() {
        closeInput();
        initInput(lastInputFileName);
        return this;
    }

    /**
     * Retrieve next input line. Ignore comments and empty lines. If we are
     * inside a loop, request the loop to stop if a file was specified and no
     * more data is available. Also request a stop upon reading when no file was
     * specified.
     *
     * @return null if end of file reached or no scanner available.
     */
    public String getNextInput() {

        while (inputScanner != null && inputScanner.hasNextLine()) {
            String s = inputScanner.nextLine();
            if (!s.startsWith("#") && !s.isEmpty()) {
                return s;
            }
        }
        // If we are inside a loop, request the current loop to stop looping
        if (!frames.isEmpty()) {
            frames.getLast().requestStop();
        }
        
        // Either finished, or no scanner was set.
        return null;

    }

    /**
     * Closes the input scanner.
     *
     * @return this
     */
    public FrameStack closeInput() {
        if (inputScanner != null) {
            inputScanner.close();
        }
        return this;

    }

    public int size() {
        return frames.size();
    }

    /**
     * Prepare for a new loop, passing a list of alternating key, values.
     *
     * @param keyval The possible keys are : null or x for xpath, count for nbr
     * of loops, ms for millis, s for seconds, h for hour, m for minutes ...
     */
    public void prepare(String... keyval) {
        Map<String, String> h = new HashMap<>();
        for (int i = 0; i < keyval.length; i += 2) {
            h.put(keyval[i], keyval[i + 1]);
        }
        prepare(h);
    }

    /**
     * Prepare for a new loop, passing a map of options.
     *
     * @param params The possible keys are : null or x for xpath, count for nbr
     * of loops, ms for millis, s for seconds, h for hour, m for minutes ...
     */
    protected void prepare(Map<String, String> params) {

        Frame f;
        if (frames.isEmpty()) {
            f = new Frame(null, this);
        } else {
            f = new Frame(frames.getLast(), this);
        }

        for (String k : params.keySet()) {
            long l;
            // Null key means as xpath.
            if (k == null) {
                f.setXpath(params.get(k));
                continue;
            }

            switch (k) {
                case "x":
                case "xpath":
                    f.setXpath(params.get(k));
                    break;
                case "ms":
                case "ml":
                case "mls":
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
                case "mn":
                case "mns":
                case "min":
                case "mins":
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

                case "count":
                case "loops":
                case "loop":
                case "l":
                case "c":
                    if (params.get(k) == null) {
                        break;
                    }
                    l = Long.decode(params.get(k));
                    f.setMaxCount(l);
                    break;

                default:
                    throw new RuntimeException("Unregognized command loop parameter " + k + " in " + params);

            }
        }
        frames.add(f);
    }

    /**
     * Retrieve the next element, if available. If xpath is null, loop for ever,
     * until time out or count limit is reached.
     *
     * @return false if we shouldStop.
     */
    public boolean loop() {
        frames.getLast().incrementCount();
        if (frames.getLast().shouldStop()) {
            return false;
        }
        // Loop forever if no xpath specified.
        if (frames.getLast().getXpath() == null) {
            return true;
        }
        // Loop around xpath generated elements.
        if (frames.getLast().hasNext()) {
            frames.getLast().next();
            return true;
        } else {
            // xpath was specified, but thare are no (more) elements.
            return false;
        }
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
     * Get inner loop counter, or 0 if no loop.
     *
     * @return
     */
    public long getCount() {
        if (frames.isEmpty()) {
            return 0;
        }
        return frames.getLast().getCount();
    }

    /**
     * Get current search context. Can be called outside a loop. Should never
     * return null. If no search context can be found, return wd.
     *
     * @return
     */
    public SearchContext getSc() {
        WebElement w = getWe();

        if (w == null) {
            return getWd();
        } else {
            return w;
        }
    }

    public void cleanup() {
        frames.removeLast();
    }

    public WebDriver getWd() {
        return wd;
    }

}
