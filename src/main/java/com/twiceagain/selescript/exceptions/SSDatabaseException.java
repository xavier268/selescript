/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twiceagain.selescript.exceptions;

/**
 * Database (mongo) related exceptions.
 * @author xavier
 */
public class SSDatabaseException extends SSException{

    public SSDatabaseException() {
    }

    public SSDatabaseException(String message) {
        super(message);
    }

    public SSDatabaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public SSDatabaseException(Throwable cause) {
        super(cause);
    }

    public SSDatabaseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
}
