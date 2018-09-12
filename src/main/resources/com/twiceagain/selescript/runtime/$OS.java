package com.twiceagain.selescript.runtime;



public class $OS extends BaseVariable{
    
    public $OS(FrameStack fs) {
        super(fs);
    }
    
    public String get() {
        return System.getProperty("os.name");
    }
    
}
