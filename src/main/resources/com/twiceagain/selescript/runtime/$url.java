package com.twiceagain.selescript.runtime;

/**
 *
 * @author xavier
 */
public class $url extends BaseVariable {

    public $url(FrameStack fs) {
        super(fs);
    }

    public String get() {
        return getWd().getCurrentUrl();
    }

    public void set(String url) {
        getWd().get(url);
    }

}
