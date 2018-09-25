/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twiceagain.selescript.exceptions;

/**
 * Both number format conversion errors or arithmetic exceptions, such as divide
 * by zero. For instance null or too long number.
 *
 * @author xavier
 */
public class SSNumberException extends SSException {

    public SSNumberException() {
    }

    public SSNumberException(String message) {
        super(message);
    }

    public SSNumberException(String message, Throwable cause) {
        super(message, cause);
    }

    public SSNumberException(Throwable cause) {
        super(cause);
    }

    public SSNumberException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
