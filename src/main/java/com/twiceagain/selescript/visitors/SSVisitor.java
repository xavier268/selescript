/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twiceagain.selescript.visitors;

import auto.SelescriptParser.*;
import com.twiceagain.selescript.exceptions.SSNumberException;
import com.twiceagain.selescript.exceptions.SSSyntaxException;
import com.twiceagain.selescript.interpreter.runtime.SSRuntimeContext;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Visit a Unit (ie, the full script).
 *
 * @author xavier
 */
public class SSVisitor extends SSVisitorAbstract {

    public SSVisitor(SSRuntimeContext rtc) {
        super(rtc);
    }
    // =======================================
    //  unit is the top level syntax concept
    // =======================================

    /**
     * Main interpreter entry point.
     *
     * @param ctx
     * @return
     */
    @Override
    public Object visitUnit(UnitContext ctx) {
        for (StatementContext s : ctx.statement()) {
            visit(s);
            if (rtc.shouldStop()) {
                break;
            }
        }
        rtc.close(); // cleanup
        return null;
    }

    // ==========================================
    //  stringval represented as string, or null.
    // ==========================================
    @Override
    public String visitSvid(SvidContext ctx) {
        return rtc.getId(ctx.ID().getText());
    }

    @Override
    public String visitSvbiid(SvbiidContext ctx) {
        return rtc.getBiid(ctx.BIID().getText());
    }

    @Override
    public String visitSvand(SvandContext ctx) {
        String s = (String) visit(ctx.stringval(0));
        if (s == null) {
            return null; // false;
        }
        s = (String) visit(ctx.stringval(1));
        return s;
    }

    @Override
    public String visitSvor(SvorContext ctx) {
        String s = (String) visit(ctx.stringval(0));
        if (s != null) {
            return s; // true;
        }
        s = (String) visit(ctx.stringval(1));
        return s;
    }

    @Override
    public String visitSvpar(SvparContext ctx) {
        return (String) visit(ctx.stringval());
    }

    @Override
    public String visitSvat(SvatContext ctx) {
        String xpath = ".";
        String attr = null;
        if (ctx.TAG() != null) {
            attr = trim1(ctx.TAG().getText());
        }
        if (ctx.stringval() != null) {
            xpath = (String) visit(ctx.stringval());
        }
        List<WebElement> lwe = rtc.getSc().findElements(By.xpath(xpath));
        if (lwe.isEmpty()) {
            return null;
        }
        if (attr == null) {
            return lwe.get(0).getText();
        } else {
            return lwe.get(0).getAttribute(attr);
        }

    }

    @Override
    public String visitSveq(SveqContext ctx) {
        String s = (String) visit(ctx.stringval(0));
        if (s != null) {
            if (s.equals((String) visit(ctx.stringval(1)))) {
                return s;
            } else {
                return null;
            }
        } else {
            String ss = (String) visit(ctx.stringval(1));
            if (ss == null) {
                return "true";
            } else {
                return null;
            }
        }
    }

    @Override
    public String visitSvneq(SvneqContext ctx) {
        String s = (String) visit(ctx.stringval(0));
        if (s != null) {
            if (s.equals((String) visit(ctx.stringval(1)))) {
                return null;
            } else {
                return "true";
            }
        } else {
            String ss = (String) visit(ctx.stringval(1));
            if (ss == null) {
                return null;
            } else {
                return "true";
            }
        }
    }

    @Override
    public String visitSvfind(SvfindContext ctx) {
        String s = (String) visit(ctx.stringval(0));
        String p = (String) visit(ctx.stringval(1));

        if (s == null) {
            return null;
        }
        if (p == null) {
            return s;
        }

        Matcher m = Pattern.compile(p).matcher(s);
        if (m.find()) {
            return m.group();
        }

        return null;
    }

    @Override
    public String visitSvreplace(SvreplaceContext ctx) {

        String s = (String) visit(ctx.stringval(0));
        String p = (String) visit(ctx.stringval(1));
        String r = (String) visit(ctx.stringval(2));

        if (s == null) {
            return null;
        }
        if (p == null) {
            return s;
        }

        if (r == null) {
            r = "";
        }

        Matcher m = Pattern.compile(p).matcher(s);
        return m.replaceAll(r);

    }

    @Override
    public String visitSvconcat(SvconcatContext ctx) {
        String s = (String) visit(ctx.stringval(0));
        String p = (String) visit(ctx.stringval(1));
        if (s == null) {
            return p;
        }
        if (p == null) {
            return s;
        }
        return s + p;

    }

    @Override
    public String visitSvnot(SvnotContext ctx) {
        String s = (String) visit(ctx.stringval());
        if (s == null) {
            return "true";
        } else {
            return null;
        }
    }

    @Override
    public String visitSvstring(SvstringContext ctx) {
        return (trim2(ctx.STRING().getText()));
    }

    @Override
    public String visitSvuminus(SvuminusContext ctx) {
        try {
            Long l = -Long.valueOf((String) visit(ctx.stringval()));
            return l.toString();
        } catch (NumberFormatException ex) {
            throw new SSNumberException(ex);
        }

    }

    @Override
    public String visitSvminus(SvminusContext ctx) {
        try {
            Long l = Long.valueOf((String) visit(ctx.stringval(0)));
            l = l - Long.valueOf((String) visit(ctx.stringval(1)));
            return l.toString();
        } catch (NumberFormatException ex) {
            throw new SSNumberException("Problem with : "
                    + ctx.stringval(0).getText() + " - "
                    + ctx.stringval(1).getText(), ex);
        }
    }

    @Override
    public String visitSvtimes(SvtimesContext ctx) {
        try {
            Long l = Long.valueOf((String) visit(ctx.stringval(0)));
            l *= Long.valueOf((String) visit(ctx.stringval(1)));
            return l.toString();
        } catch (NumberFormatException ex) {
            throw new SSNumberException("Problem with : "
                    + ctx.stringval(0).getText() + " * "
                    + ctx.stringval(1).getText(), ex);
        }
    }

    @Override
    public String visitSvdiv(SvdivContext ctx) {
        try {
            Long l = Long.valueOf((String) visit(ctx.stringval(0)));
            l /= Long.valueOf((String) visit(ctx.stringval(1)));
            return l.toString();
        } catch (ArithmeticException | IllegalArgumentException ex) {
            throw new SSNumberException("Problem with : "
                    + ctx.stringval(0).getText() + " / "
                    + ctx.stringval(1).getText(), ex);
        }
    }

    @Override
    public String visitSvplus(SvplusContext ctx) {
        try {
            Long l = Long.valueOf((String) visit(ctx.stringval(0)));
            l += Long.valueOf((String) visit(ctx.stringval(1)));
            return l.toString();
        } catch (NumberFormatException ex) {
            throw new SSNumberException("Problem with : "
                    + ctx.stringval(0).getText() + " ++ "
                    + ctx.stringval(1).getText(), ex);
        }
    }

    @Override
    public String visitSvnull(SvnullContext ctx) {
        return null;
    }

    @Override
    public String visitSvnumber(SvnumberContext ctx) {
        try {
            Long l = Long.valueOf(ctx.NUMBER().getText());
            return l.toString();
        } catch (NumberFormatException ex) {
            throw new SSNumberException("Problem with number : "
                    + ctx.NUMBER().getText(), ex);
        }
    }

    // ===========================================
    //    Statements, represented as String, usually null.
    // ===========================================
    @Override
    public String visitStcheck(StcheckContext ctx) {
        if (visit(ctx.stringval()) == null) {
            rtc.requestStopLocal();
        }
        return null;
    }

    @Override
    public String visitStsubmit(StsubmitContext ctx) {

        // Set parameters
        Boolean wait = false;
        String xpath = ".";
        if (ctx.param() != null) {
            if ((ctx.param().TAG() != null) && ("w:".equals(ctx.param().TAG().getText()))) {
                wait = true;
            }
            if (ctx.param().stringval() != null) {
                xpath = (String) visit(ctx.param().stringval());
            }
        }

        // Do submit
        List<WebElement> lwe = rtc.getSc().findElements(By.xpath(xpath));
        if (lwe.isEmpty()) {
            return null;
        }
        WebElement w = lwe.get(0);
        w.submit();

        // If :w tag, wait for element to disappear.
        if (wait) {
            rtc.wait(ExpectedConditions.stalenessOf(w));
        }
        return null;
    }

    @Override
    public String visitStclick(StclickContext ctx) {

        // Set parameters
        Boolean wait = false;
        String xpath = ".";
        if (ctx.param() != null) {
            if ((ctx.param().TAG() != null) && ("w:".equals(ctx.param().TAG().getText()))) {
                wait = true;
            }
            if (ctx.param().stringval() != null) {
                xpath = (String) visit(ctx.param().stringval());
            }
        }

        // Do click
        List<WebElement> lwe = rtc.getSc().findElements(By.xpath(xpath));
        if (lwe.isEmpty()) {
            return null;
        }
        WebElement w = lwe.get(0);
        w.click();

        // If w: tag, wait for element to disappear.
        if (wait) {
            rtc.wait(ExpectedConditions.stalenessOf(w));
        }
        return null;
    }

    @Override
    public String visitStprint(StprintContext ctx) {

        StringBuilder sb = new StringBuilder();
        String outf = null;
        boolean append = true;

        for (ParamContext p : ctx.param()) {
            if ((p.TAG() == null) || trim1(p.TAG().getText()).isEmpty()) {
                if (p.stringval() != null) {
                    sb.append((String) visit(p.stringval()));
                }
            } else {
                switch (trim1(p.TAG().getText())) {
                    case "t": // raw text - this is the default
                        if (p.stringval() != null) {
                            sb.append((String) visit(p.stringval()));
                        }
                        break;
                    case "u":  // url encoded
                        if (p.stringval() != null) {
                            try {
                                sb.append(URLEncoder.encode((String) visit(p.stringval()), "UTF-8"));
                            } catch (UnsupportedEncodingException ex) {
                                throw new SSSyntaxException("Encoding error", ex);
                            }
                        }
                        break;
                    case "f":
                    case "file":
                        if (p.stringval() != null) {
                            outf = (String) visit(p.stringval());
                            append =true;
                        }
                        break;
                    case "nf":
                    case "nfile":
                        if (p.stringval() != null) {
                            outf = (String) visit(p.stringval());
                            append =false;
                        }
                        break;
                    default:
                        throw new SSSyntaxException("Unknown tag in print " + p.TAG().getText() + ":");
                }
            }
        }
        String s = sb.toString();
        if (outf == null) {
            System.out.println(s);
        } else {
            if (!append || !outf.equals(rtc.getConfig().getOutputFileName())) {
                // file changed or new file explicitely requested.
                rtc.reopen(outf, append);
            }
            rtc.println(outf,append, sb);

        }
        return null;
    }

    @Override
    public String visitSttype(SttypeContext ctx) {

        String s = ""; // text to type
        String x = "."; // xpath to use

        for (ParamContext p : ctx.param()) {
            String k;
            if ((p.TAG() == null) || trim1(p.TAG().getText()).isEmpty()) {
                k = "t:";
            } else {
                k = p.TAG().getText();
            }

            switch (k) {
                case "t:": // raw text - this is the default
                    if (p.stringval() != null) {
                        s = s + visit(p.stringval());
                    }
                    break;
                case "u:":  // url encoded
                    if (p.stringval() != null) {
                        try {
                            s = s + URLEncoder.encode((String) visit(p.stringval()), "UTF-8");
                        } catch (UnsupportedEncodingException ex) {
                            throw new SSSyntaxException("Encoding error", ex);
                        }
                    }
                    break;
                case "x:":
                case "xpath:":
                    if (p.stringval() != null) {
                        x = (String) visit(p.stringval());
                    }
                    break;
                default:
                    throw new SSSyntaxException("Unknown tag in type " + p.TAG().getText());
            }
        }

        // Do the actual typing
        List<WebElement> lwe = rtc.getSc().findElements(By.xpath(x));

        if (lwe.isEmpty()) {
            return null;
        }
        WebElement w = lwe.get(0);

        w.clear();
        w.sendKeys(s);

        return null;
    }

    @Override
    public String visitStdb(StdbContext ctx) {

        Map<String, String> pp = new HashMap<>();
        // Prepare parameters
        ctx.param().forEach((ParamContext p) -> {
            if (p.TAG() == null) {
                throw new SSSyntaxException("Tags are mandatory to send data to database in "
                        + ctx.parent.getText());
            } else {
                pp.put(trim1(p.TAG().getText()), (String) visit(p.stringval()));
            }
        });
        rtc.dbWrite(pp);
        return null;
    }

    @Override
    public String visitStassignb(StassignbContext ctx) {
        rtc.putBiid(ctx.BIID().getText(), (String) visit(ctx.stringval()));
        return null;
    }

    @Override
    public String visitStassigni(StassigniContext ctx) {
        rtc.putId(ctx.ID().getText(), (String) visit(ctx.stringval()));
        return null;
    }

    /**
     * Specify what to shoot and where to put it. See reference documentation
     * page or source code.
     *
     * @param ctx
     * @return
     */
    @Override
    public String visitStshot(StshotContext ctx) {

        String xpath = null;
        String file = null;
        String mem = null;
        String html = null;

        for (ParamContext p : ctx.param()) {

            String k = "x:";

            if (p.TAG() != null) {
                k = p.TAG().getText();
            }

            switch (k) {
                case "x:":
                case "xpath:":
                    if (p.stringval() != null) {
                        xpath = (String) visit(p.stringval());
                    }
                    break;
                case "f:":
                case "file:":
                    if (p.stringval() != null) {
                        file = (String) visit(p.stringval());
                    }
                    break;
                case "mem:":
                    if (p.stringval() != null) {
                        mem = (String) visit(p.stringval());
                        if(mem == null) {
                            throw new SSSyntaxException(
                                    "You are trying to set a variable called null in "
                            + p.getText() + "\n Did you forget the quotes ?" );
                        }
                    }
                    break;
                case "html:":
                    if (p.stringval() != null) {
                        html = (String) visit(p.stringval());
                        if(html == null) {
                            throw new SSSyntaxException(
                                    "You are trying to set a variable called null in "
                            + p.getText() + "\n Did you forget the quotes ?" );
                        }
                    }
                    break;
                default:
                    throw new SSSyntaxException("Invalid syntax calling shot : " + p.getText());
            }

        }

        // Process
        if (xpath == null) {
            if (file != null) {
                rtc.screenshot2Path(Paths.get(file));
            }
            if (mem != null) {
                rtc.putId(mem, rtc.screenshot2B64());
            }
            if (html != null) {
                rtc.putId(html, SSRuntimeContext.base64toHtmlFragment(rtc.screenshot2B64()));
            }
        } else {
            List<WebElement> lwe = rtc.getSc().findElements(By.xpath(xpath));
            if (lwe.isEmpty()) {
                return null;
            }
            WebElement w = lwe.get(0);
            if (file != null) {
                rtc.screenshot2Path(w, Paths.get(file));
            }
            if (mem != null) {
                rtc.putId(mem, rtc.screenshot2B64(w));
            }
            if (html != null) {
                rtc.putId(html, SSRuntimeContext.base64toHtmlFragment(rtc.screenshot2B64(w)));
            }

        }

        return null;
    }

    @Override
    public String visitStgo(StgoContext ctx) {

        By by = null;
        Integer count = null;
        Long dm, millis = null;

        // parse  params
        for (ParamContext p : ctx.param()) {
            String k, x;
            if (p.TAG() == null) {
                k = "x:";
            } else {
                k = p.TAG().getText();
            }

            switch (k) {
                case "c:":
                case "count:":
                    count = Integer.decode((String) visit(p.stringval()));
                    break;
                case "ms:":
                    dm = Long.decode((String) visit(p.stringval()));
                    if (millis == null) {
                        millis = dm;
                    } else {
                        millis += dm;
                    }
                    break;
                case "sec:":
                    dm = Long.decode((String) visit(p.stringval())) * 1000;
                    if (millis == null) {
                        millis = dm;
                    } else {
                        millis += dm;
                    }
                    break;
                case "min:":
                    dm = Long.decode((String) visit(p.stringval())) * 60 * 1000;
                    if (millis == null) {
                        millis = dm;
                    } else {
                        millis += dm;
                    }
                    break;
                case "hour:":
                    dm = Long.decode((String) visit(p.stringval())) * 60 * 60 * 1000;
                    if (millis == null) {
                        millis = dm;
                    } else {
                        millis += dm;
                    }
                    break;

                case "day:":
                    dm = Long.decode((String) visit(p.stringval())) * 24 * 60 * 60 * 1000;
                    if (millis == null) {
                        millis = dm;
                    } else {
                        millis += dm;
                    }
                    break;
                case "x:":
                case "xpath:":
                    x = (String) visit(p.stringval());
                    if (x == null) {
                        by = null;
                    } else {
                        by = By.xpath(x);
                    }
                    break;
                case "id:":
                    x = (String) visit(p.stringval());
                    if (x == null) {
                        by = null;
                    } else {
                        by = By.id(x);
                    }
                    break;
                case "class:":
                    x = (String) visit(p.stringval());
                    if (x == null) {
                        by = null;
                    } else {
                        by = By.className(x);
                    }
                    break;
                case "linktext:":
                    x = (String) visit(p.stringval());
                    if (x == null) {
                        by = null;
                    } else {
                        by = By.linkText(x);
                    }
                    break;
                case "name:":
                    x = (String) visit(p
                            .stringval());
                    if (x == null) {
                        by = null;
                    } else {
                        by = By.name(x);
                    }
                    break;

                case "plinktext:":
                    x = (String) visit(p.stringval());
                    if (x == null) {
                        by = null;

                    } else {
                        by = By.partialLinkText(x);
                    }
                    break;

                case "tag:":
                    x = (String) visit(p.stringval());
                    if (x == null) {
                        by = null;
                    } else {
                        by = By.tagName(x);
                    }
                    break;
                default:
                    throw new SSSyntaxException("Urecognized TAG for go loop  " + k);
            }
        }

        // startloop
        rtc.loopStart(by, millis, count);

        try {
            // main loop body
            while (rtc.loopWhile()) {
                for (StatementContext s : ctx.statement()) {
                    if (rtc.shouldStop()) {
                        break;
                    }
                    visit(s);
                    if (rtc.shouldStop()) {
                        break;
                    }
                }
            }
        } catch (StaleElementReferenceException ex) {

        }
        // cleanup
        rtc.loopEnd();
        return null;
    }

}
