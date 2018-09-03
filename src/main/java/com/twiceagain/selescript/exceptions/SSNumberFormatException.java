/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twiceagain.selescript.exceptions;

/**
 * Number format conversion. Typically null or too long number.
 * @author xavier
 */
public class SSNumberFormatException extends SSException {

    public SSNumberFormatException() {
    }

    public SSNumberFormatException(String message) {
        super(message);
    }

    public SSNumberFormatException(String message, Throwable cause) {
        super(message, cause);
    }

    public SSNumberFormatException(Throwable cause) {
        super(cause);
    }

    public SSNumberFormatException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
}
