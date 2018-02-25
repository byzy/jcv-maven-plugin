package com.iqarr.maven.main;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.mozilla.javascript.EvaluatorException;

import com.iqarr.maven.plugin.domain.JCVConfig;
import com.iqarr.maven.plugin.domain.JCVMethodEnum;
import com.iqarr.maven.plugin.domain.YUIConfig;
import com.iqarr.maven.plugin.support.DefaultProcessFactory;
import com.iqarr.maven.plugin.support.ProcessFactory;
import com.iqarr.maven.plugin.support.logger.LoggerFactory;
import com.iqarr.maven.plugin.support.logger.SystemLogger;

/**
 * @author
 *         zhangyong
 */
public class StartMain {
	
	/**
	 * 
	 * @param args
	 * @throws IOException
	 * @throws EvaluatorException
	 */
	
	public static void main(String[] args) throws EvaluatorException, IOException {
		
	
		LoggerFactory.buildLogerFactory (new SystemLogger ());

		JCVConfig jcvConfig = new JCVConfig ();
		processArge (args,jcvConfig);
		LoggerFactory.info ("\n");
		LoggerFactory.info ("config is:" + jcvConfig.toString ());
		
		ProcessFactory processFactory = new DefaultProcessFactory (jcvConfig);
		processFactory.initDisplayInfo ();
		processFactory.initJcv (jcvConfig.getWebAppRoot ());
		processFactory.doProcessPageFile ();
		processFactory.displaySuccessInfo ();
		
	}
	
	public static void processArge(String[] args, JCVConfig jcvConfig) {
		
		if (args == null || args.length == 0) {
			throw new IllegalArgumentException ("arge is null");
		}
		Map<String, String> pars = new HashMap<String, String> (args.length);
		String key = "";
		String value = "";
		for (String ag : args) {
			if (ag == null || "".equals (ag)) {
				continue;
			}
			int indexOf = ag.indexOf (":");
			key = ag.substring (0,indexOf);
			value = ag.substring (indexOf + 1,ag.length ());
			pars.put (key,value);
		}
		String suffixs = pars.get ("suffixs");
		if (suffixs == null || "".equals (suffixs)) {
			suffixs = "html,jsp,";
		}
		jcvConfig.setPageSuffixs (Arrays.asList (suffixs.split (",")));
		JCVMethodEnum meth = JCVMethodEnum.MD5_METHOD;
		String globaJsMethod = pars.get ("globaJsMethod");
		if ("MD5_METHOD".equals (globaJsMethod)) {
			meth = JCVMethodEnum.MD5_METHOD;
		}
		else if ("MD5FileName_METHOD".equals (globaJsMethod)) {
			meth = JCVMethodEnum.MD5FileName_METHOD;
		}
		else if ("TIMESTAMP_METHOD".equals (globaJsMethod)) {
			meth = JCVMethodEnum.TIMESTAMP_METHOD;
		}
		jcvConfig.setJsMethod (meth);
		String globaCSSMethod = pars.get ("globaJsMethod");
		if ("MD5_METHOD".equals (globaCSSMethod)) {
			meth = JCVMethodEnum.MD5_METHOD;
		}
		else if ("MD5FileName_METHOD".equals (globaCSSMethod)) {
			meth = JCVMethodEnum.MD5FileName_METHOD;
		}
		else if ("TIMESTAMP_METHOD".equals (globaCSSMethod)) {
			meth = JCVMethodEnum.TIMESTAMP_METHOD;
		}
		
		jcvConfig.setCssMethod (meth);
		
		String versionLable = pars.get ("versionLable");
		if (versionLable == null || "".equals (versionLable)) {
			versionLable = "version";
		}
		jcvConfig.setVersionLable (versionLable);
		String baseCssDomin = pars.get ("baseCssDomin");
		if (baseCssDomin != null || !"".equals (baseCssDomin)) {
			jcvConfig.setBaseCssDomin (Arrays.asList (baseCssDomin.split (",")));
		}
		String baseJsDomin = pars.get ("baseJsDomin");
		if (baseJsDomin != null || !"".equals (baseJsDomin)) {
			jcvConfig.setBaseJsDomin (Arrays.asList (baseJsDomin.split (",")));
		}
		String globaCsslPrefixPath = pars.get ("globaCsslPrefixPath");
		jcvConfig.setGlobaCsslPrefixPath (globaCsslPrefixPath);
		String globaJslPrefixPath = pars.get ("globaJslPrefixPath");
		jcvConfig.setGlobaJslPrefixPath (globaJslPrefixPath);
		String sourceEncoding = pars.get ("sourceEncoding");
		jcvConfig.setSourceEncoding (sourceEncoding == null ? "UTF-8" : sourceEncoding);
		String clearPageCommentStr = pars.get ("clearPageComment");
		boolean clearPageComment = false;
		if ("true".equals (clearPageCommentStr)) {
			clearPageComment = true;
		}
		
		jcvConfig.setClearPageComment (clearPageComment);
		
		String outJsCssRoot = pars.get ("outJsCssRoot");
		jcvConfig.setOutJSCSSDirPath (outJsCssRoot);
		
		String compressionCssStr = pars.get ("compressionCss");
		boolean compressionCss = false;
		if ("true".equals (compressionCssStr)) {
			compressionCss = true;
		}
		jcvConfig.setCompressionCss (compressionCss);
		
		String compressionJsStr = pars.get ("compressionJs");
		boolean compressionJs = false;
		if ("true".equals (compressionJsStr)) {
			compressionJs = true;
		}
		
		jcvConfig.setCompressionJs (compressionJs);
		
		String userCompressionSuffix = pars.get ("userCompressionSuffix");
		if (null == userCompressionSuffix || "".equals (userCompressionSuffix)) {
			userCompressionSuffix = "min";
		}
		jcvConfig.setUserCompressionSuffix (userCompressionSuffix);
		String excludesCss = pars.get ("excludesCss");
		if (null != excludesCss && !"".equals (excludesCss)) {
			jcvConfig.setExcludesCss (Arrays.asList (excludesCss.split (",")));
		}
		
		String excludesJs = pars.get ("excludesJs");
		if (null != excludesJs && !"".equals (excludesJs)) {
			jcvConfig.setExcludesJs (Arrays.asList (excludesJs.split (",")));
		}
		
		YUIConfig yuiConfig = new YUIConfig ();
		
		jcvConfig.setYuiConfig (yuiConfig);
		
		String skipFileNameSuffix = pars.get ("skipFileNameSuffix");
		if (skipFileNameSuffix == null) {
			skipFileNameSuffix = ".min";
		}
		jcvConfig.setSkipFileNameSuffix (skipFileNameSuffix);
		
		// version 6
		String jsPhysicalRootPath = pars.get ("jsPhysicalRootPath");
		
		jcvConfig.setJsPhysicalRootPath (jsPhysicalRootPath);
		
		String cssPhysicalRootPath = pars.get ("cssPhysicalRootPath");
		jcvConfig.setCssPhysicalRootPath (cssPhysicalRootPath);
		
		String jsConstantName = pars.get ("jsConstantName");
		
		jcvConfig.setJsConstantName (jsConstantName);
		
		String cssConstantName = pars.get ("cssConstantName");
		
		jcvConfig.setCssConstantName (cssConstantName);
		
		String webAppRoot = pars.get ("webAppRoot");
		
		jcvConfig.setWebAppRoot (webAppRoot);
		
		String outDirRoot = pars.get ("outDirRoot");
		jcvConfig.setOutDirRoot (outDirRoot);
		
		String jsConstantAliasPath = pars.get ("jsConstantAliasPath");
		jcvConfig.setJsConstantAliasPath (jsConstantAliasPath);
		
		String cssConstantAliasPath = pars.get ("cssConstantAliasPath");
		jcvConfig.setCssConstantAliasPath (cssConstantAliasPath);
	}
	
}
