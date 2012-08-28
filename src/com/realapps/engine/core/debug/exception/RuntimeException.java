package com.realapps.engine.core.debug.exception;

import android.util.Log;

public class RuntimeException extends java.lang.RuntimeException {
	protected static final long serialVersionUID = -1461680430227531905L;
	
	public RuntimeException(String message) {
		super(message);
		Log.w("RUNTIME-ERROR", message);
		Log.w("RUNTIME-ERROR", "");
		
		StackTraceElement process = getStackTrace()[1];
		Log.w("RUNTIME-ERROR", process.getFileName()+" >> "+process.getMethodName()+" >> Line: "+process.getLineNumber());
	}
}
