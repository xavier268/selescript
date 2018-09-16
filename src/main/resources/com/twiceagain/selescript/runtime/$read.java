package com.twiceagain.selescript.runtime;


public class $read extends BaseVariable {
    
    public $read(FrameStack fs) {
        super(fs);
    }
    
    /**
     * Get the next input line.
     * @return null if nothing available.
     */
    public String get() {
        return fs.getNextInput();
    }
    
    /**
     * Used to reset the scanner. 
     * @param dummy is ignored.
     */
    public void set(String dummy) {
        fs.resetInput();
    }
    
}