package com.twiceagain.selescript.runtime;


public class $count extends BaseVariable {
    
    public $count(FrameStack fs) {
        super(fs);
    }
    
    public String get() {        
        return "" + fs.getCount() ;
    }
}