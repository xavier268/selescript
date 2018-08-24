/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twiceagain.selescript.exceptions;

/**
 *
 * @author xavier
 */
public class SSUndefinedIDException extends SSException {

    public SSUndefinedIDException() {
    }

    public SSUndefinedIDException(String message) {
        super(message);
    }

    public SSUndefinedIDException(String message, Throwable cause) {
        super(message, cause);
    }

    public SSUndefinedIDException(Throwable cause) {
        super(cause);
    }

    public SSUndefinedIDException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
}
