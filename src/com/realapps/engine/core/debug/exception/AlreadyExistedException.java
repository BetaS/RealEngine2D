package com.realapps.engine.core.debug.exception;

import android.util.Log;

public class AlreadyExistedException extends RuntimeException {
	protected static final long serialVersionUID = -1461680430227531905L;
	
	public AlreadyExistedException() {
		super("asd");
		
		StackTraceElement process = getStackTrace()[1];
		Log.w("EXCEPTION", process.getFileName()+" >> "+process.getMethodName()+" >> Line: "+process.getLineNumber());
	}
}
