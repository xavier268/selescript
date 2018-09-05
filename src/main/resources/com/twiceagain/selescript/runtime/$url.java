package com.twiceagain.selescript.runtime;

import java.util.Deque;
import org.openqa.selenium.SearchContext;

/**
 *
 * @author xavier
 */
public class $url extends BaseVariable {

    
    public $url(Deque<SearchContext> wes) {
        super(wes);
    }
   
    public  String get() {
        return getWd().getCurrentUrl();
    }

   
    public  void set(String url) {
        getWd().get(url);
    }

  

}
