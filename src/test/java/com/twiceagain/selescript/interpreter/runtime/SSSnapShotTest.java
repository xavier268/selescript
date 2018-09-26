/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twiceagain.selescript.interpreter.runtime;

import com.twiceagain.selescript.SSConfig;
import static com.twiceagain.selescript.interpreter.runtime.SSSnapShot.base64toHtmlFragment;
import java.nio.file.Paths;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 *
 * @author xavier
 */
public class SSSnapShotTest {

    @Test
    public void test1() {

        try (SSRuntimeContext rtc = new SSRuntimeContext(new SSConfig())) {
            rtc.putBiid("$url", "https://www.google.com");
            rtc.screenshot2Path(Paths.get("target/testimages/test1.png"));
        }

    }

    @Test
    public void test2() {

        try (SSRuntimeContext rtc = new SSRuntimeContext(new SSConfig())) {
            rtc.putBiid("$url", "https://www.google.com");
            
            WebElement img = rtc.getWd().findElement(By.id("hplogo"));
            rtc.screenshot2Path(img, Paths.get("target/testimages/test2.png"));
            rtc.close();

        }

    }
    
    /**
     * Should not print/record anything.
     */
    @Test
    public void test3() {

        try (SSRuntimeContext rtc = new SSRuntimeContext(new SSConfig())) {
            rtc.putBiid("$url", "https://www.google.com");
            
            WebElement img = rtc.getWd().findElement(By.tagName("img"));
            // Should never appear, since too small.
            rtc.screenshot2Path(img, Paths.get("target/testimages/shouldNotAppear.png"));
            rtc.close();

        }

    }
    
    @Test
    public void test4() {

        try (SSRuntimeContext rtc = new SSRuntimeContext(new SSConfig())) {
            rtc.putBiid("$url", "https://www.google.com");
            
            WebElement img = rtc.getWd().findElement(By.id("hplogo"));
            System.out.println(base64toHtmlFragment(rtc.screenshot2B64(img)));
            rtc.close();

        }

    }

}
