/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twiceagain.selescript.compiler.structures;

/**
 *
 * @author xavier
 */
public class SSRuntimeException extends RuntimeException{

    public SSRuntimeException() {
    }

    public SSRuntimeException(String message) {
        super(message);
    }

    public SSRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public SSRuntimeException(Throwable cause) {
        super(cause);
    }

    public SSRuntimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    
    
    
    
}
