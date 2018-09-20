/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twiceagain.selescript.visitors;

import com.twiceagain.selescript.exceptions.SSSyntaxException;
import org.antlr.v4.runtime.ConsoleErrorListener;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;

/**
 * Same as console, but will throw an exception.
 * @author xavier
 */
public class SSDebugErrorListener extends ConsoleErrorListener {
    
    @Override
	public void syntaxError(Recognizer<?, ?> recognizer,
							Object offendingSymbol,
							int line,
							int charPositionInLine,
							String msg,
							RecognitionException e){
            super.syntaxError(recognizer, offendingSymbol, line, charPositionInLine, msg, e);
            throw new SSSyntaxException("Debug error : ", e);
        }
}
