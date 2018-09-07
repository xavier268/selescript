package com.twiceagain.selescript.runtime;


/**
 * Get total elapsed time in millis.
 * @author xavier
 */
public class $millis extends BaseVariable {
    
    private static final long START = System.currentTimeMillis();

    public $millis(FrameStack fs) {
        super(fs);
    }

    

   
    public  String get() {
        return "" + (System.currentTimeMillis() - START);
    }

   
    

}

