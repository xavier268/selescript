/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twiceagain.selescript.compiler.exceptions;

/**
 *
 * @author xavier
 */
public class SSUndefinedBuiltinException extends SSException {

    public SSUndefinedBuiltinException() {
    }

    public SSUndefinedBuiltinException(String message) {
        super(message);
    }

    public SSUndefinedBuiltinException(String message, Throwable cause) {
        super(message, cause);
    }

    public SSUndefinedBuiltinException(Throwable cause) {
        super(cause);
    }

    public SSUndefinedBuiltinException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
}
