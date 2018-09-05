package com.twiceagain.selescript.runtime;

import java.util.Deque;
import org.openqa.selenium.SearchContext;


/**
 *
 * @author xavier
 */
public class $millis extends BaseVariable {

    public $millis(Deque<SearchContext> wes) {
        super(wes);
    }   

   
    public  String get() {
        return "" + System.currentTimeMillis();
    }

   
    

}

