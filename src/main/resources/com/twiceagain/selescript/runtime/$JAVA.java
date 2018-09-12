package com.twiceagain.selescript.runtime;




public class $JAVA extends BaseVariable {
    
    public $JAVA(FrameStack fs) {
        super(fs);
    }   
    
    public String get() {
        return Runtime.version().toString();
    }
}