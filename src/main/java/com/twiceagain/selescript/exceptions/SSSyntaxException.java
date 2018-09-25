/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twiceagain.selescript.exceptions;

/**
 * Syntax error while parsing the script. Grammar errors are typically detected
 * during the initial parse of the script, when the abstract tree is
 * constructed.
 *
 * @author xavier
 */
public class SSSyntaxException extends SSException {

    public SSSyntaxException() {
    }

    public SSSyntaxException(String message) {
        super(message);
    }

    public SSSyntaxException(String message, Throwable cause) {
        super(message, cause);
    }

    public SSSyntaxException(Throwable cause) {
        super(cause);
    }

    public SSSyntaxException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
