package com.iqarr.maven.plugin.exception;

import org.mozilla.javascript.ErrorReporter;
import org.mozilla.javascript.EvaluatorException;

import com.iqarr.maven.plugin.support.logger.LoggerFactory;

/**  
* @author 
*		zhangyong         
*/
public class YUIException implements ErrorReporter {
    
    
    private String                   fileName;
    /**  
     * <p>Title: </p>  
     * <p>Description: </p>  
     * @param log  
     */
     public YUIException(String fileName) {
         
         this.fileName=fileName;
     }
    
    
    /*
    * <p>Title: error</p>  
    * <p>Description: </p>  
    * @param arg0
    * @param arg1
    * @param arg2
    * @param arg3
    * @param arg4  
    * @see org.mozilla.javascript.ErrorReporter#error(java.lang.String, java.lang.String, int, java.lang.String, int)  
    */
    @Override
    public void error(String message, String sourceName,
                    int line, String lineSource, int lineOffset) {
        
        	LoggerFactory.error("\n[ERROR] in  "+fileName);
            if (line < 0) {
            	LoggerFactory.error("  " + message);
            } else {
            	LoggerFactory.error("  " + line + ':' + lineOffset + ':' + message);
            }
        
    }
    
    /*
    * <p>Title: runtimeError</p>  
    * <p>Description: </p>  
    * @param arg0
    * @param arg1
    * @param arg2
    * @param arg3
    * @param arg4
    * @return  
    * @see org.mozilla.javascript.ErrorReporter#runtimeError(java.lang.String, java.lang.String, int, java.lang.String, int)  
    */
    
    @Override
    public EvaluatorException runtimeError(String message, String sourceName,
                    int line, String lineSource, int lineOffset) {
        
        error(message, sourceName, line, lineSource, lineOffset);
        return new EvaluatorException(message);
    }
    
    /*
    * <p>Title: warning</p>  
    * <p>Description: </p>  
    * @param arg0
    * @param arg1
    * @param arg2
    * @param arg3
    * @param arg4  
    * @see org.mozilla.javascript.ErrorReporter#warning(java.lang.String, java.lang.String, int, java.lang.String, int)  
    */
    
    @Override
    public void warning(String message, String sourceName,
                    int line, String lineSource, int lineOffset) {
    		LoggerFactory.warn("\n[WARNING] in  "+fileName);
            if (line < 0) {
            	LoggerFactory.warn("  " + message);
            } else {
            	LoggerFactory.warn("  " + line + ':' + lineOffset + ':' + message);
            }
        
        
    }
    
}
