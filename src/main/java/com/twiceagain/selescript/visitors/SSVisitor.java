/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twiceagain.selescript.visitors;

import auto.SelescriptParser.*;
import com.twiceagain.selescript.interpreter.runtime.SSRuntimeContext;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * Visit a Unit (ie, the full script).
 *
 * @author xavier
 */
public class SSVisitor extends SSVisitorAbstract {

    public SSVisitor(SSRuntimeContext rtc) {
        super(rtc);
    }
/**
 * Main interpreter entry point.
 * @param ctx
 * @return 
 */
    @Override
    public Object visitUnit(UnitContext ctx) {
        System.out.println("Visiting unit !");
        for (StatementContext s : ctx.statement()) {
            visit(s);
            if (rtc.shouldStop()) {
                break;
            }
        }
        rtc.close(); // cleanup
        return null;
    }

    

    // ===========================================
    //    Constant numbers represented as Long
    // ===========================================
    @Override
    public Long visitCnnumber(CnnumberContext ctx) {
        return Long.decode(ctx.NUMBER().getText());
    }

    @Override
    public Long visitCnuminus(CnuminusContext ctx) {
        return -(Long) visit(ctx.constantnumber());
    }

    @Override
    public Long visitCnminus(CnminusContext ctx) {
        return (Long) visit(ctx.constantnumber(0)) - (Long) visit(ctx.constantnumber(1));
    }

    @Override
    public Long visitCnplus(CnplusContext ctx) {
        return (Long) visit(ctx.constantnumber(0)) + (Long) visit(ctx.constantnumber(1));
    }

    @Override
    public Long visitCntimes(CntimesContext ctx) {
        return (Long) visit(ctx.constantnumber(0)) * (Long) visit(ctx.constantnumber(1));
    }

    @Override
    public Long visitCndiv(CndivContext ctx) {
        return (Long) visit(ctx.constantnumber(0)) / (Long) visit(ctx.constantnumber(1));
    }

    @Override
    public Long visitCnpar(CnparContext ctx) {
        return (Long) visit(ctx.constantnumber());
    }

    // ================================================
    // Constant strings represented as String, potentially null
    // ================================================
    @Override
    public String visitCsstring(CsstringContext ctx) {
        return trim2(ctx.STRING().getText());
    }

    @Override
    public String visitCsnumber(CsnumberContext ctx) {
        return "" + (Long) visit(ctx.constantnumber());
    }

    @Override
    public String visitCsconcat(CsconcatContext ctx) {
        String s0 = (String) visit(ctx.constantstring(0));
        String s1 = (String) visit(ctx.constantstring(1));
        if (s1 == null) {
            return s0;
        }
        if (s0 == null) {
            return s1;
        }
        return s0 + s1;
    }

    @Override
    public String visitCsnot(CsnotContext ctx) {
        String s = (String) visit(ctx.constantstring());
        if (s == null) {
            return "true";
        } else {
            return null;
        }
    }

    @Override
    public String visitCspar(CsparContext ctx) {
        return (String) visit(ctx.constantstring());
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
    public String visitSvnull(SvnullContext ctx) {
        return null;
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
            attr = ctx.TAG().getText().substring(1);
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
                return "true";
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
            return "true";
        }

        if (Pattern.compile(p).matcher(s).find()) {
            return "true";
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
    public String visitSvstring(SvstringContext ctx){
        return (String) visit(ctx.constantstring());
    }

    // ===========================================
    //    Statements
    // ===========================================
    
    @Override
    public Object visitCheck(CheckContext ctx) {        
        if (rtc.shouldStop()) {
            return null;
        }
        String s = (String) visit(ctx.stringval());
        return null;
    }
}
