/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twiceagain.selescript.interpreter.runtime;

import com.twiceagain.selescript.exceptions.SSException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import java.util.List;
import javax.imageio.ImageIO;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Snapshoot utilities.
 *
 * @author xavier
 */
public abstract class SSSnapShot {

    public abstract WebDriver getWd();

    /**
     * Takes a (full view) Base64 screenshoot from the provided webdriver.
     *
     * @return Base64 string. You can use it directly to display inline images.
     */
    public String screenshot2B64() {
        return ((TakesScreenshot) getWd()).getScreenshotAs(OutputType.BASE64);
    }

    /**
     * Screen shot of selected element only. Return null if image is too small
     * to be visible.
     *
     * @param ele
     * @return
     * @throws IOException
     */
    public File screenshot2TempFile(WebElement ele)
            throws IOException {

        if (ele == null) {
            return null;
        }

        // Get width and height of the element
        int eleWidth = ele.getSize().getWidth();
        int eleHeight = ele.getSize().getHeight();

        // Get the location of element on the page
        Point point = ele.getLocation();

        if (eleWidth * eleHeight == 0) {
            return null;
        }

        // Process ...
        scrollElementIntoView(ele);
        File fi = ((TakesScreenshot) getWd()).getScreenshotAs(OutputType.FILE);
        BufferedImage img = ImageIO.read(fi);

        // Crop the entire page screenshot to get only element screenshot
        BufferedImage eleScreenshot = img.getSubimage(point.getX(), point.getY(),
                eleWidth, eleHeight);
        ImageIO.write(eleScreenshot, "png", fi);
        return fi;
    }

    /**
     * Take a snashot of the selected element
     *
     * @param ele
     * @param target
     */
    public void screenshot2Path(WebElement ele, Path target) {

        try {
            File fi = screenshot2TempFile(ele);
            if (fi == null) {
                return;
            }
            target.toFile().mkdirs();
            Files.move(fi.toPath(), target, StandardCopyOption.REPLACE_EXISTING );
        } catch (IOException ex) {
            try {
                throw new SSException("Cannot create a snapshot in "
                        + target.toFile().getCanonicalPath(), ex);
            } catch (IOException ex1) {
                throw new SSException("Cannot create a snapshot in "
                        + target, ex);

            }
        }

    }

    /**
     * ScreenShot into a temporary file.The temp file might disappear when the
     * jvm stops.
     *
     * @return
     */
    public File screenshot2TempFile() {
        return ((TakesScreenshot) getWd()).getScreenshotAs(OutputType.FILE);
    }

    /**
     * ScreenShot into a file with the target Path.
     *
     * @param target - Path of the target file.
     */
    public void screenshot2Path(Path target) {

        try {
            target.toFile().mkdirs();
            Files.move(screenshot2TempFile().toPath(), target, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {
            try {
                throw new SSException("Cannot create a snapshot in "
                        + target.toFile().getCanonicalPath(), ex);
            } catch (IOException ex1) {
                throw new SSException("Cannot create a snapshot in "
                        + target, ex);

            }
        }

    }

    /**
     * Visualy mark a list of elements on the webpage.
     *
     * @param lwe
     * @param message
     * @param backrgba
     * @param frontrgba
     */
    public void highlightElements(
            List<WebElement> lwe,
            String message,
            String backrgba,
            String frontrgba) {
        int i = 0;
        for (WebElement we : lwe) {
            i++;
            highlightElement(we, String.format("#%d %s", i, message, frontrgba, backrgba));
        }
    }

    /**
     * Highlight selected elements.
     *
     * @param lwe
     * @param message
     */
    public void highlightElements(
            List<WebElement> lwe,
            String message) {
        int i = 0;
        for (WebElement we : lwe) {
            i++;
            highlightElement(we, String.format("#%d %s", i, message));
        }

    }

    /**
     * Visualy mark an element on the webPage.
     *
     * @param we
     * @param innerHtml - content of message div
     */
    public void highlightElement(
            WebElement we,
            String innerHtml) {

        highlightElement(we, innerHtml, "rgba(64,64,64,0.2)", "rgba(255,0,0,1.0)");
    }

    /**
     * Visualy mark an element on the webPage.
     *
     * @param we
     * @param innerHtml - content of message dv
     * @param backrgba - background color string : try 'red' or
     * 'rgba(64,0,0,0.3)'
     * @param frontrgba - front color for border and text
     */
    public void highlightElement(
            WebElement we,
            String innerHtml,
            String backrgba,
            String frontrgba) {

        if (getWd() == null || we == null) {
            return;
        }

        // scrollElementIntoView(wd, we);
        // Get element size and location
        int left = we.getLocation().x;
        int top = we.getLocation().y;
        int width = we.getSize().width;
        int height = we.getSize().height;

        final String css = String.format("color:%s;"
                + "background-color:%s;"
                + "border-style:dashed;border-width:2px;border-color:%s; "
                + "position:absolute;"
                + "top:%dpx;left:%dpx;"
                + "width:%dpx;height:%dpx;"
                + "z-index:1000;",
                frontrgba, backrgba, frontrgba,
                top, left, width, height);

        final String script = String.format("elem = document.createElement('div');"
                + "elem.innerHTML='%s';"
                + "elem.setAttribute('style','%s');"
                + "document.body.appendChild(elem);",
                innerHtml, css);

        ((JavascriptExecutor) getWd()).executeScript(script);
    }

    /**
     * Scroll to make element visible.
     *
     * @param we
     */
    public void scrollElementIntoView(WebElement we) {
        if (getWd() == null || we == null) {
            return;
        }
        String script = "arguments[0].scrollIntoView();";
        ((JavascriptExecutor) getWd()).executeScript(script, we);
    }

}
