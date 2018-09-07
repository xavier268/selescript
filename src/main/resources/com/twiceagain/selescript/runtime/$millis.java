package com.twiceagain.selescript.runtime;

/**
 * Get current system time in millis.
 *
 * @author xavier
 */
public class $millis extends BaseVariable {

    public $millis(FrameStack fs) {
        super(fs);
    }

    public String get() {
        return "" + System.currentTimeMillis();
    }

}
