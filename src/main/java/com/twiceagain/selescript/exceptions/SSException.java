/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twiceagain.selescript.exceptions;

/**
 * Base class for all generated exceptions.
 * @author xavier
 */
public class SSException extends RuntimeException{

    public SSException() {
    }

    public SSException(String message) {
        super(message);
    }

    public SSException(String message, Throwable cause) {
        super(message, cause);
    }

    public SSException(Throwable cause) {
        super(cause);
    }

    public SSException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    
    
    
    
}
