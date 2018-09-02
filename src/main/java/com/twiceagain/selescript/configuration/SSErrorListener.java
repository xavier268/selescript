/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twiceagain.selescript.configuration;

import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;

/**
 * My own implementation of an error listener. Just adds a flag that can get the
 * result and store the first errors.
 *
 * @author xavier
 */
public class SSErrorListener extends BaseErrorListener {

    private String firstErrorMessage = null;
    private int firstErrorLine;
    private int firstErrorCharPositionInLine;
    private boolean syntaxError = false;

    @Override
    public void syntaxError(Recognizer<?, ?> recognizer,
            Object offendingSymbol,
            int line,
            int charPositionInLine,
            String msg,
            RecognitionException e) {
        if (!syntaxError) {
            syntaxError = true;
            firstErrorMessage = msg;
            firstErrorLine = line;
            firstErrorCharPositionInLine = charPositionInLine;
        }
        super.syntaxError(recognizer, offendingSymbol, line, charPositionInLine, msg, e);
    }

    /**
     * True if an syntaxError has occured.
     * @return 
     */
    public boolean isSyntaxError() {
        return syntaxError;
    }

    public String getFirstErrorMessage() {
        return firstErrorMessage;
    }

    public int getFirstErrorLine() {
        return firstErrorLine;
    }

    public int getFirstErrorCharPositionInLine() {
        return firstErrorCharPositionInLine;
    }

}
