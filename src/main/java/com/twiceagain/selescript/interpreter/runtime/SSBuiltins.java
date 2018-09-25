/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twiceagain.selescript.interpreter.runtime;

import com.twiceagain.selescript.exceptions.SSSyntaxException;

/**
 * Processing for builtins ids.
 *
 * @author xavier
 */
public class SSBuiltins {

    private final SSRuntimeContext rtc;

    public SSBuiltins(SSRuntimeContext rtc) {
        this.rtc = rtc;
    }

    public String get(String biid) {

        if (biid == null) {
            throw new SSSyntaxException("The builtin id cannot be null !");
        }
        switch (biid) {

            case "$os":
                return System.getProperty("os.name").toLowerCase();
            case "$java":
                return rtc.getConfig().getJavaVersion();
            case "$selescript":
                return rtc.getConfig().getSelescriptVersion();
            case "$grid":
                return rtc.getConfig().getGridUrl();            
            case "$mongocol":
                return rtc.getConfig().getMongoCollection();
            case "$mongo":
                return rtc.getConfig().getMongoConnexionString();
            case "$mongodb":
                return rtc.getConfig().getMongoDatabase();
            case "$debug":
                return (rtc.getConfig().isDebug() == true) ? "true" : null;
            case "$browser":
                return rtc.getConfig().getBrowser();
            case "$url":
                return rtc.getWd().getCurrentUrl();
            case "$title":
                return rtc.getWd().getTitle();
            case "$count":
                return rtc.getCount();
            case "$elapsed":
                return rtc.getElapsed().toString();
            case "$read":
                return rtc.readInput();
            case "$source": 
                return rtc.getConfig().getScriptFileName();
            case "$input":
                return rtc.getConfig().getInputFileName();
            case "$nl":
                return System.lineSeparator();
            case "$tab":
                return "\t";
            case "$depth":
                return rtc.getDepth();
            case "$break":
                rtc.requestStopLocal();
                return "Breaking ...";
            case "$continue":
                rtc.requestStopContinue();
                return "Continuing ...";
            case "$abort":
            case "$quit":
                rtc.requestStopGlobal();
                return "Aborting ...";
            default:
                throw new SSSyntaxException("Builtin-Id cannot be read from : " + biid);

        }

    }

    public void put(String biid, String value) {

        if (biid == null) {
            throw new SSSyntaxException("The builtin id cannot be null !");
        }
        switch (biid) {            
            case "$url" :
                rtc.getWd().get(value);
                break;
            case "$read":   
            case "$input":
                rtc.resetInput(value);
                break;
            default:
                throw new SSSyntaxException("Builtin-Id cannot be written to : " + biid);

        }
    }

}
