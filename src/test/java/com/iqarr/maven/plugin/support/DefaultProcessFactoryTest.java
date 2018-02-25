package com.iqarr.maven.plugin.support;

import java.net.URL;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import com.iqarr.maven.plugin.domain.JCVConfig;
import com.iqarr.maven.plugin.domain.JCVMethodEnum;
import com.iqarr.maven.plugin.domain.YUIConfig;
import com.iqarr.maven.plugin.support.logger.LoggerFactory;
import com.iqarr.maven.plugin.support.logger.SystemLogger;

/**  
*		zhangyong   
* @version 
*		V1.0      
*/
public class DefaultProcessFactoryTest {
	
	private  JCVConfig jcvConfig;
	
	@Before
	public void initConfig(){
		jcvConfig=new JCVConfig ();
		jcvConfig.setPageSuffixs (Arrays.asList ("html") );
        jcvConfig.setJsMethod (JCVMethodEnum.MD5FileName_METHOD);
        jcvConfig.setCssMethod (JCVMethodEnum.MD5FileName_METHOD);
        jcvConfig.setVersionLable ("version");
        jcvConfig.setBaseCssDomin (null);
        jcvConfig.setBaseJsDomin (null);
        jcvConfig.setGlobaCsslPrefixPath ("");
        jcvConfig.setGlobaJslPrefixPath ("");
        jcvConfig.setSourceEncoding ("utf-8");
        jcvConfig.setClearPageComment (true);
        jcvConfig.setOutJSCSSDirPath ("/tmp/jcv/cssjs");
        jcvConfig.setCompressionCss (true);
        jcvConfig.setCompressionJs (true);
        jcvConfig.setUserCompressionSuffix ("min");
        jcvConfig.setExcludesCss (null);
        jcvConfig.setExcludesJs (null);
        
       YUIConfig  yuiConfig=new YUIConfig();
        
        jcvConfig.setYuiConfig (yuiConfig);
        jcvConfig.setSkipFileNameSuffix (".min");
        String out= "/tmp/jcv/cssjs";
        jcvConfig.setOutDirRoot (out);
	}
	
	/**
	 * Test method for {@link com.iqarr.maven.plugin.support.DefaultProcessFactory#processCompressionJsCss(java.util.List, java.lang.String, com.iqarr.maven.plugin.domain.JCVConfig)}.
	 */
	@Test
	public void testProcessCompressionJsCss() {
		 URL url =DefaultProcessFactoryTest.class.getResource ("/");
		 ProcessFactory processFactory=new DefaultProcessFactory(jcvConfig);
		 SystemLogger.LOG_LEVEL=SystemLogger.LOG_LEVEL_DEBUG;
		 LoggerFactory.buildLogerFactory (new SystemLogger ());
		 processFactory.initDisplayInfo ();
		 processFactory.initJcv (url.getPath ());
		 processFactory.doProcessPageFile ();
		 processFactory.displaySuccessInfo ();
		
	}
	

	
}
