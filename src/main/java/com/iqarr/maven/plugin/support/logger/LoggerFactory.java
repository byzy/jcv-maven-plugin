package com.iqarr.maven.plugin.support.logger;

/**
 * @author
 *         zhangyong
 */
public class LoggerFactory {
	
	private static Logger logger;
	
	public static boolean isDebugEnabled() {
		
		return logger.isDebugEnabled ();
		
	}
	
	public static void debug(CharSequence content) {
		
		logger.debug (content);
		
	}
	
	public static void debug(CharSequence content, Throwable error) {
		
		logger.debug (content,error);
		
	}
	
	public static void debug(Throwable error) {
		
		logger.debug (error);
		
	}
	
	public static boolean isInfoEnabled() {
		
		return logger.isInfoEnabled ();
		
	}
	
	public static void info(CharSequence content) {
		
		logger.info (content);
		
	}
	
	public static void info(CharSequence content, Throwable error) {
		
		logger.info (content,error);
		
	}
	
	public static void info(Throwable error) {
		
		logger.info (error);
		
	}
	
	public static boolean isWarnEnabled() {
		
		return logger.isWarnEnabled ();
		
	}
	
	public static void warn(CharSequence content) {
		
		logger.warn (content);
		
	}
	
	public static void warn(CharSequence content, Throwable error) {
		
		logger.warn (content,error);
		
	}
	
	public static void warn(Throwable error) {
		
		logger.warn (error);
		
	}
	
	public static boolean isErrorEnabled() {
		
		return logger.isErrorEnabled ();
		
	}
	
	public static void error(CharSequence content) {
		
		logger.error (content);
		
	}
	
	public static void error(CharSequence content, Throwable error) {
		
		logger.error (content,error);
		
	}
	
	public static void error(Throwable error) {
		
		logger.error (error);
		
	}
	
	public static void buildLogerFactory(Logger log) {
		logger = log;
	}
	
}
