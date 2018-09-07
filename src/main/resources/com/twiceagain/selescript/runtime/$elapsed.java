package com.twiceagain.selescript.runtime;


public class $elapsed extends BaseVariable {
    
    private final long START = System.currentTimeMillis();
    
    public $elapsed(FrameStack fs) {
        super(fs);
    }
    
    public String get() {
        return "" + ( System.currentTimeMillis() - START);
    }
}