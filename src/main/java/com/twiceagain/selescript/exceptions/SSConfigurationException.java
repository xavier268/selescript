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
public class SSConfigurationException extends SSException {

    public SSConfigurationException() {
    }

    public SSConfigurationException(String message) {
        super(message);
    }

    public SSConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }

    public SSConfigurationException(Throwable cause) {
        super(cause);
    }

    public SSConfigurationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
}
