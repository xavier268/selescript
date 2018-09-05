package com.twiceagain.selescript.runtime;

import java.util.Deque;
import org.openqa.selenium.SearchContext;

/**
 *
 * @author xavier
 */
public class $title extends BaseVariable {


    public $title(Deque<SearchContext> wes) {
        super(wes);
    }

    public  String get() {
        return getWd().getTitle();
    }

}

