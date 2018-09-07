package com.twiceagain.selescript.runtime;

/**
 *
 * @author xavier
 */
public class $title extends BaseVariable {

    public $title(FrameStack fs) {
        super(fs);
    }

    public String get() {
        return getWd().getTitle();
    }

}
