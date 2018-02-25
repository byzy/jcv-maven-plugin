package com.iqarr.maven.plugin.support.logger;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @author
 *         zhangyong
 */
public class SystemLogger implements Logger {
	
	public static final int LOG_LEVEL_DEBUG = 3;
	
	public static final int LOG_LEVEL_INFO = 2;
	
	public static final int LOG_LEVEL_WARN = 1;
	
	public static final int LOG_LEVEL_ERROR = 0;
	
	/** 日志等级. */
	public static int LOG_LEVEL = LOG_LEVEL_INFO;
	
	@Override
	public void debug(CharSequence content) {
		print ("debug",content);
	}
	
	@Override
	public void debug(CharSequence content, Throwable error) {
		print ("debug",content,error);
	}
	
	@Override
	public void debug(Throwable error) {
		print ("debug",error);
	}
	
	@Override
	public void info(CharSequence content) {
		print ("info",content);
	}
	
	@Override
	public void info(CharSequence content, Throwable error) {
		print ("info",content,error);
	}
	
	@Override
	public void info(Throwable error) {
		print ("info",error);
	}
	
	@Override
	public void warn(CharSequence content) {
		print ("warn",content);
	}
	
	@Override
	public void warn(CharSequence content, Throwable error) {
		print ("warn",content,error);
	}
	
	@Override
	public void warn(Throwable error) {
		print ("warn",error);
	}
	
	@Override
	public void error(CharSequence content) {
		System.err.println ("[error] " + content.toString ());
	}
	
	@Override
	public void error(CharSequence content, Throwable error) {
		StringWriter sWriter = new StringWriter ();
		PrintWriter pWriter = new PrintWriter (sWriter);
		
		error.printStackTrace (pWriter);
		
		System.err.println ("[error] " + content.toString () + "\n\n" + sWriter.toString ());
	}
	
	@Override
	public void error(Throwable error) {
		StringWriter sWriter = new StringWriter ();
		PrintWriter pWriter = new PrintWriter (sWriter);
		
		error.printStackTrace (pWriter);
		
		System.err.println ("[error] " + sWriter.toString ());
	}
	
	private void print(String prefix, CharSequence content) {
		System.out.println ("[" + prefix + "] " + content.toString ());
	}
	
	private void print(String prefix, Throwable error) {
		StringWriter sWriter = new StringWriter ();
		PrintWriter pWriter = new PrintWriter (sWriter);
		
		error.printStackTrace (pWriter);
		
		System.out.println ("[" + prefix + "] " + sWriter.toString ());
	}
	
	private void print(String prefix, CharSequence content, Throwable error) {
		StringWriter sWriter = new StringWriter ();
		PrintWriter pWriter = new PrintWriter (sWriter);
		
		error.printStackTrace (pWriter);
		
		System.out.println ("[" + prefix + "] " + content.toString () + "\n\n" + sWriter.toString ());
	}
	
	@Override
	public boolean isDebugEnabled() {
		
		return false;
	}
	
	@Override
	public boolean isInfoEnabled() {
		return true;
	}
	
	@Override
	public boolean isWarnEnabled() {
		return true;
	}
	
	@Override
	public boolean isErrorEnabled() {
		return true;
	}
	
}
