/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twiceagain.selescript.visitors;

import com.twiceagain.selescript.SSConfig;
import org.junit.Test;

/**
 *
 * @author xavier
 */
public class SSConfigDisplayTest {

    @Test
    public void displayConfig() {
        System.out.printf("%nDefault configuration : %n%s%n%n", new SSConfig());
    }
}
