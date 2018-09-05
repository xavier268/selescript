package com.twiceagain.selescript.runtime;

import java.util.Deque;
import org.openqa.selenium.SearchContext;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author xavier
 */
public class $url extends BaseVariable {

    public $url(Deque<SearchContext> wes) {
        super(wes);
    }

    @Override
    public String get() {
        return getWd().getCurrentUrl();
    }

    @Override
    public void set(String url) {
        getWd().get(url);
    }

}
