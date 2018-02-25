package com.iqarr.maven.plugin.support;

import static com.iqarr.maven.plugin.utils.BaseUtils.checkStrIsInList;
import static com.iqarr.maven.plugin.utils.HtmlUtils.getHtmllabDocposition;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.mozilla.javascript.EvaluatorException;

import com.iqarr.maven.plugin.constant.JCVConstant;
import com.iqarr.maven.plugin.domain.DocPosition;
import com.iqarr.maven.plugin.domain.JCVConfig;
import com.iqarr.maven.plugin.domain.JCVFileInfo;
import com.iqarr.maven.plugin.domain.JCVMethodEnum;
import com.iqarr.maven.plugin.domain.PageInfo;
import com.iqarr.maven.plugin.exception.YUIException;
import com.iqarr.maven.plugin.support.logger.LoggerFactory;
import com.iqarr.maven.plugin.utils.BaseUtils;
import com.iqarr.maven.plugin.utils.FileUtils;
import com.iqarr.maven.plugin.utils.Md5Utils;
import com.yahoo.platform.yui.compressor.CssCompressor;
import com.yahoo.platform.yui.compressor.JavaScriptCompressor;

/**
 * @author
 *         zhangyong
 */
public abstract class AbstractProcessFactory implements ProcessFactory {
	
	protected final static String HTML_URL_SEPARATOR = "/";
	
	protected final static String HTML_JAVASCRIPT_LABLE_START = "<script";
	protected final static String HTML_JAVASCRIPT_SRC = "src=";
	protected final static String HTML_JAVASCRIPT_END = ">";
	
	// css
	protected final static String HTML_CSS_LABLE_START = "<link";
	protected final static String HTML_CSS_LABLE_SRC = "href=";
	protected final static String HTML_CSS_LABLE_END = ">";
	
	// comment
	protected final static String HTML_COMMENT_LABLE_START = "<!--";
	protected final static String HTML_COMMENT_LABLE_END = "-->";
	
	protected final static String DISPLAY_STR="--------------------------------- ";
	
	private long  timeStart;
	
	/**
	 * 配置信息
	 */
	protected JCVConfig jCVConfig;
	
	/**
     * 所有的js css文件信息　link:fileInfo
     */
	protected Map<String, JCVFileInfo> jcvFiles;
	
	/**
	 * 所有的页面
	 */
	protected List<PageInfo> pages;
	
	protected List<String> displayInfo=new ArrayList<String>();
	
	
	
	
	public AbstractProcessFactory( JCVConfig jCVConfig){
		this.jCVConfig=jCVConfig;
	}
	
	
	 /*
	* Title: initDisplayInfo  
	* Description:     
	* @see com.iqarr.maven.plugin.support.ProcessFactory#initDisplayInfo()  
	*/
	@Override
	public void initDisplayInfo() {
		  LoggerFactory.info("=================================JCV====================================");
		  LoggerFactory.info("      _  _______      __ ");
		  LoggerFactory.info("     | |/ ____\\ \\    / / ");
		  LoggerFactory.info("     | | |     \\ \\  / /  ");
		  LoggerFactory.info(" _   | | |      \\ \\/ /   ");
		  LoggerFactory.info("| |__| | |____   \\  /    ");
		  LoggerFactory.info(" \\____/ \\_____|   \\/    ");
		  LoggerFactory.info("                         ");
		  LoggerFactory.info("                         ");
	        
		  LoggerFactory.info("find suffixs size:"+jCVConfig.getPageSuffixs ().size ());
		 // LoggetFactory.info("build webRootName:"+jCVConfig.getOutDirRoot ());
		  LoggerFactory.info("build sourceEncoding:"+jCVConfig.getSourceEncoding ());
	        timeStart=new Date ().getTime ();
	        
	        //显示日志
	        //LoggetFactory.info("web app Dir:"+webappDirectory.getPath());
	        LoggerFactory.info("out Dir:"+jCVConfig.getOutDirRoot ());
	        LoggerFactory.info("system is linux:"+FileUtils.getSystemFileSeparatorIslinux());
	        LoggerFactory.info("css method is :"+jCVConfig.getCssMethod ());
	        LoggerFactory.info("js method is :"+jCVConfig.getJsMethod ());
	}
	
	
   
	
	
	@Override
	public void displaySuccessInfo(){
		for(String info:displayInfo){
			LoggerFactory.info (info);
		}
		
		LoggerFactory.info("===============  Total time ["+(new Date().getTime()-timeStart)+" millisecond]===========================");
		LoggerFactory.info("========================================================================");
		
	}
	
	
	
	
  
	/**
	 * 
	 * 压缩css js
	 * 
	 * @param processFiles
	 * @param outDir
	 * @param jCVConfig
	 */
    protected void processCompressionJsCss(List<JCVFileInfo> processFiles, String outDir, JCVConfig jCVConfig) {
        
        LoggerFactory.debug ("CompressionJsCss outDir:" + outDir);
        if (processFiles == null) {
            LoggerFactory.debug ("Compression processFiles is null ");
            return;
        }
        LoggerFactory.debug ("Compression find file size:" + processFiles.size ());
        
        boolean compression = false;
        
        if (jCVConfig.isCompressionCss () == true || jCVConfig.isCompressionJs ()) {
            compression = true;
        }
        String skipFileNameSuffix = jCVConfig.getSkipFileNameSuffix ();
        
        if (compression) {
            //保存以处理的文件
            Map<JCVFileInfo, Boolean> isProcessFile=new HashMap<JCVFileInfo, Boolean> (processFiles.size ());
            for (int i = 0; i < processFiles.size (); i++) {
                
                JCVFileInfo jcv = processFiles.get (i);
                
                // 检查是否以处理
                Boolean boolean1 = isProcessFile.get (jcv);
                if (null != boolean1 && true == boolean1) {
                    continue;
                }
                
                // 移除未处理文件
                if (checkJcvIsSkip (jcv,jCVConfig)) {
                    // 当该文件未md5 filename 时候需要复制
                    if ((JCVFileInfo.CSS.equals (jcv.getFileType ())
                                    && jCVConfig.getCssMethod () == JCVMethodEnum.MD5FileName_METHOD)
                                    || (JCVFileInfo.JS.equals (jcv.getFileType ())
                                                    && jCVConfig.getJsMethod () == JCVMethodEnum.MD5FileName_METHOD)) {
                        //processFiles.get (i).setCopy (false);
                        processFiles.remove (i);
                        i--;
                    }
                    else {
                        processFiles.remove (i);
                        i--;
                        //processFiles.get (i).setCopy (true);
                        //i++;
                    }
                    isProcessFile.put (jcv,true);
                    continue;
                }
                
                LoggerFactory.debug ("process file:" + jcv.getFileName () + "   index:" + i);
                doProcessCompressionJsCss (jcv,skipFileNameSuffix,jCVConfig.getSourceEncoding (),outDir,jCVConfig);
                isProcessFile.put (jcv,true);
                
            } // for end
            
        }
        
    }
    
    
    /**
     * Check jcv is skip.
     *
     * @param jcv the jcv
     * @param jCVConfig the j cv config
     * @return true, if successful
     */
    private boolean checkJcvIsSkip(final JCVFileInfo jcv, final JCVConfig jCVConfig) {
        
        
        // 不处理后缀为.min.*的文件
        if (jcv.getFileName ().indexOf (jCVConfig.getSkipFileNameSuffix () + "." + jcv.getFileType ()) != -1) {
            LoggerFactory.info ("The suffix is "+jCVConfig.getSkipFileNameSuffix ()+" ,not processed:" + jcv.getFileName ());
            return true;
        }
        
        if (JCVFileInfo.CSS.equals (jcv.getFileType ())) {
            
            if (jCVConfig.getExcludesCss () != null
                            && checkStrIsInList (jcv.getRelativelyFilePath (),jCVConfig.getExcludesCss (),true)) {
                
                LoggerFactory.info ("The file  is not processed:" + jcv.getFileName ());
                return true;
            }
            
        }
        else if (JCVFileInfo.JS.equals (jcv.getFileType ())) {
            if (jCVConfig.getExcludesJs () != null
                            && checkStrIsInList (jcv.getRelativelyFilePath (),jCVConfig.getExcludesJs (),true)) {
                LoggerFactory.info ("The file  is not processed:" + jcv.getFileName ());
                return true;
            }
        }
        else {
            LoggerFactory.error ("file type error :" + jcv.getFileType ());
            return true;
        }
        
        return false;
    }
    
    /**
     * Do process compression js css.
     *  
     * @param jcv the jcv
     * @param skipFileNameSuffix the skip file name suffix
     * @param sourceEncoding the source encoding
     * @param outDir the out dir
     * @param jCVConfig the j cv config
     */
    private void doProcessCompressionJsCss(JCVFileInfo jcv, final String skipFileNameSuffix, final String sourceEncoding, final String outDir, final JCVConfig jCVConfig) {
        // yui
        Reader in = null;
        Writer out = null;
        try {
            
            if (JCVFileInfo.CSS.equals (jcv.getFileType ())) {
                
                String tempPath = BaseUtils.getJSSCSSOutPath (jcv,true,jCVConfig.getCssMethod (),outDir,jCVConfig);
                File f = new File (BaseUtils.getFilePathDir (tempPath));
                if (!f.exists ()) {
                    f.mkdirs ();
                }
                // YUI
                in = new InputStreamReader (new FileInputStream (jcv.getFile ()));
                CssCompressor compressor = new CssCompressor (in);
                out = new OutputStreamWriter (new FileOutputStream (tempPath), sourceEncoding);
                compressor.compress (out,-1);
                
            }
            else if (JCVFileInfo.JS.equals (jcv.getFileType ())) {
                String tempPath = BaseUtils.getJSSCSSOutPath (jcv,true,jCVConfig.getJsMethod (),outDir,jCVConfig);
                File f = new File (BaseUtils.getFilePathDir (tempPath));
                if (!f.exists ()) {
                    f.mkdirs ();
                }
                // yui start
                in = new InputStreamReader (new FileInputStream (jcv.getFile ()));
                JavaScriptCompressor compressor = new JavaScriptCompressor (in, new YUIException (jcv.getFileName ()));
                out = new OutputStreamWriter (new FileOutputStream (tempPath), sourceEncoding);
                compressor.compress (out,-1,jCVConfig.getYuiConfig ().isNomunge (),jCVConfig.getYuiConfig ()
                                .isVerbose (),jCVConfig.getYuiConfig ()
                                                .isPreserveSemi (),jCVConfig.getYuiConfig ().isDisableOptimizations ());
                
            }
            else {
                LoggerFactory.error ("file type error :" + jcv.getFileType () + " fileIfo:" + jcv.toString ());
            }
            
        }
        catch (IOException | EvaluatorException e) {
            
            LoggerFactory.error ("file is "+jcv.toString ()+" \n ",e);
            
        }
        finally {
            try {
                if (in != null) {
                    in.close ();
                    in = null;
                }
                if (out != null) {
                    out.close ();
                    out = null;
                }
            }
            catch (IOException e) {
                LoggerFactory.error (e);
            }
        }
    }
    
    

    /**
     * 处理css版本号
     * 
     * @param html
     * @param index
     * @param processSuccessFiles
     * @param jCVConfig
     * @return
     */
    protected int processCSSVersion(StringBuffer html, int index, List<JCVFileInfo> processSuccessFiles, JCVConfig jCVConfig) {
        
        return processVersion (html,index,processSuccessFiles,JCVFileInfo.CSS,jCVConfig);
    }
    
    
    
   /**
    * 
    * 处理js版本号 
    * @param html
    * @param index
    * @param processSuccessFiles
    * @param jCVConfig
    * @return
    */
    protected int processJSVersion(StringBuffer html, int index, List<JCVFileInfo> processSuccessFiles, JCVConfig jCVConfig) {
        
        return processVersion (html,index,processSuccessFiles,JCVFileInfo.JS,jCVConfig);
    }
    
    
  
    /**
     * 
     * 处理页面注释
     * @param sb
     * @param index
     * @param jCVConfig
     * @return
     */
    protected int processPageComment(StringBuffer sb, int index, JCVConfig jCVConfig) {
        
        if (index == -1) {
            return 1;
        }
        DocPosition dp = new DocPosition ();
        if (index == 0) {
            dp.setIndexPos (index);
        }
        else {
            dp.setIndexPos (index);
        }
        dp.setStartLab (HTML_COMMENT_LABLE_START); // HTML_COMMENT_LABLE_START "<!--"
        dp.setEndLad (HTML_COMMENT_LABLE_END); // "-->"
        getHtmllabDocposition (sb,dp);
        if (!dp.isFindIt ()) {
            return -1;
        }
        
        if (dp.getStartPos () == -1) {
            return -1;
        }
        
        // check
        /**
         * <!--[if lt IE 9]>
         * <script type="text/javascript">
         * 
         * </script>
         * <![endif]-->
         * <!--[if IE 9]>
         * <script type="text/javascript">
         * 
         * </script>
         * <![endif]-->
         */
        // if(dp.getStartPos())
        int checkIndex = dp.getStartPos () + HTML_COMMENT_LABLE_START.length ();
        String substring = sb.substring (checkIndex,checkIndex + 1);
        if (substring != null && "[".equals (substring)) {
            index = dp.getEndPos () == -1 ? -1 : dp.getEndPos ();
            return processPageComment (sb,index,jCVConfig);
        }
        if (dp.getEndPos () == -1) {
            sb.delete (dp.getStartPos (),sb.length ());
            index = -1;
        }
        else {
            sb.delete (dp.getStartPos (),dp.getEndPos ());
            index = dp.getEndPos ();
        }
        
        return processPageComment (sb,0,jCVConfig);
    }
    
    

    /**
     * 
     * 合并页面css <暂未实现>
     * @param html
     * @param index
     * @param jCVConfig
     * @return
     */
    protected int mergePageCss(StringBuffer html, int index, JCVConfig jCVConfig) {
        // TODO Auto-generated method stub
        return 0;
    }
    
 
    
   /**
    * 
    * 合并页面css <暂未实现>
    * @param html
    * @param index
    * @param jCVConfig
    * @return
    */
    protected int mergePageJS(StringBuilder html, int index, JCVConfig jCVConfig) {
        // TODO Auto-generated method stub
        return 0;
    }
    
    /**
     * Process version.
     *
     * @param html the html
     * @param index the index
     * @param processSuccessFiles the process success files
     * @param fileType the file type
     * @param jCVConfig the j cv config
     * @return the int
     */
    private int processVersion(StringBuffer html, int index, List<JCVFileInfo> processSuccessFiles, final String fileType, final JCVConfig jCVConfig) {
        
        DocPosition dp = new DocPosition ();
        if (index == 0 || index == -1) {
            dp.setIndexPos (index);
        }
        else {
            dp.setIndexPos (index);
        }
        
        int heardLenth = 0;
        String checkEndLable = "";
        if (JCVFileInfo.CSS.equals (fileType)) {
            dp.setStartLab (HTML_CSS_LABLE_START);
            dp.setEndLad (HTML_CSS_LABLE_SRC);
            dp.setCheckEndLad (HTML_CSS_LABLE_END);
            heardLenth = HTML_CSS_LABLE_START.length ();
            checkEndLable = HTML_CSS_LABLE_END;
        }
        else if (JCVFileInfo.JS.equals (fileType)) {
            dp.setStartLab (HTML_JAVASCRIPT_LABLE_START); // "<script"
            dp.setEndLad (HTML_JAVASCRIPT_SRC); // src=
            dp.setCheckEndLad (HTML_JAVASCRIPT_END); // >
            heardLenth = HTML_JAVASCRIPT_LABLE_START.length ();
            checkEndLable = HTML_JAVASCRIPT_END;
        }
        else {
            LoggerFactory.error ("file type error :" + fileType);
        }
        
        getHtmllabDocposition (html,dp);
        if (dp.getEndPos () == -1 || !dp.isFindIt ()) {
            if (index < html.length () && dp.getStartPos () != -1) {
                return  processVersion (html,dp.getStartPos () + heardLenth + 1,processSuccessFiles,fileType,jCVConfig);
            }
            else {
                return -1;
            }
        }
        char[] cas = html.toString ().toCharArray ();
        /*
         * int size=cas.length;
         * if(dp.getEndPos()==-1 || dp.getEndPos()>size){
         * return 1;
         * }
         */
        
        char endChar = cas[dp.getEndPos ()];
        
        if (endChar != '\'' && endChar != '"') {
            return -1;
        }
        DocPosition dpsrc = new DocPosition ();
        dpsrc.setIndexPos (dp.getEndPos () - 1);
        dpsrc.setStartLab (endChar + "");
        dpsrc.setEndLad (endChar + "");
        dpsrc.setCheckEndLad (checkEndLable);
        getHtmllabDocposition (html,dpsrc);
        if (!dpsrc.isFindIt ()) {
            return -1;
        }
        int length = dpsrc.getEndPos () - dpsrc.getStartPos () - 2;
        if (length < 0) {
            return -1;
        }
        char[] links = new char[length];
        System.arraycopy (cas,dpsrc.getStartPos () + 1,links,0,length);
        String link = new String (links);
        
        LoggerFactory.debug ("find " + fileType + " link:" + link);
        
        processlink (html,dpsrc.getStartPos () - 1,dpsrc.getEndPos () - 1,link,fileType,processSuccessFiles,jCVConfig);
        
        int res = processVersion (html,dpsrc.getEndPos (),processSuccessFiles,fileType,jCVConfig);
        
        return res;
    }
    
    /**
     * Processlink.
     *
     * @param sb the sb
     * @param start the start
     * @param end the end
     * @param historylink the historylink
     * @param fileType the file type
     * @param processSuccessFiles the process success files
     * @param jCVConfig the j cv config
     * @return the int
     */
    private int processlink(StringBuffer sb, final int start, final int end, final String historylink, final String fileType, List<JCVFileInfo> processSuccessFiles, final JCVConfig jCVConfig) {
        
        JCVFileInfo jcvFileInfo = null;
        StringBuilder fullLink = new StringBuilder ();
        
      //  String globalPrefixPath = "";
        List<String> baseDomin = null;
        if (JCVFileInfo.CSS.equals (fileType)) {
          //  globalPrefixPath = jCVConfig.getGlobaCsslPrefixPath ();
            baseDomin = jCVConfig.getBaseCssDomin ();
        }
        else if (JCVFileInfo.JS.equals (fileType)) {
         //   globalPrefixPath = jCVConfig.getGlobaJslPrefixPath ();
            baseDomin = jCVConfig.getBaseJsDomin ();
        }
        else {
            LoggerFactory.error ("file type error :" + fileType);
        }
        
        if (historylink.startsWith (JCVConstant.HTTP_START_HEARD)
                        || historylink.startsWith (JCVConstant.HTTPS_START_HEARD)) {
            // 绝对
            for (String domain : baseDomin) {
                fullLink.append (domain);
                /*if (null != globalPrefixPath && !"".equals (globalPrefixPath)) {
                    if (!fullLink.toString ().endsWith (HTML_URL_SEPARATOR)) {
                        fullLink.append (HTML_URL_SEPARATOR);
                    }
                    if (globalPrefixPath.startsWith (HTML_URL_SEPARATOR)) {
                        fullLink.append (globalPrefixPath.replaceFirst (HTML_URL_SEPARATOR,""));
                    }
                    else {
                        fullLink.append (globalPrefixPath);
                    }
                    
                }*/
                String tempUrl = historylink.replaceFirst (fullLink.toString (),"");
                if (tempUrl != null && tempUrl.startsWith (HTML_URL_SEPARATOR)) {
                    tempUrl = tempUrl.replaceFirst (HTML_URL_SEPARATOR,"");
                }
                
                tempUrl = removeUrlPar (tempUrl);
                jcvFileInfo = jcvFiles.get (tempUrl);
                if (jcvFileInfo != null) {
                    break;
                }
            }
            
        }
        else {
            // 相对
            //支持常量名称 version
            fullLink.append (historylink);
          /*  if (globalPrefixPath.startsWith (HTML_URL_SEPARATOR)) {
                fullLink.append (globalPrefixPath.replaceFirst (HTML_URL_SEPARATOR,""));
            }
            else {
                fullLink.append (globalPrefixPath);
            }*/
            fullLink = new StringBuilder (removeUrlPar (fullLink.toString ()));
            if (fullLink.indexOf (HTML_URL_SEPARATOR,0) == 0) {
                fullLink.delete (0,1);
            }
            jcvFileInfo = jcvFiles.get (fullLink.toString ());
            
        }
        
        if (jcvFileInfo != null && null != fullLink && !"".equals (fullLink.toString ())) {
            instatVersion (sb,start,end,historylink,fullLink.toString (),jcvFileInfo,processSuccessFiles,jCVConfig);
        }
        
        return 0;
    }
    
  
    
    
    /**
     * 插入版本号.
     *
     * @param sb the sb
     * @param start the start
     * @param end the end
     * @param historylink the historylink
     * @param fullLink the full link
     * @param jcvFileInfo the jcv file info
     * @param processSuccessFiles the process success files
     * @param jCVConfig the j cv config
     */
    private void instatVersion(StringBuffer sb, final int start, final int end, final String historylink, String fullLink, JCVFileInfo jcvFileInfo, List<JCVFileInfo> processSuccessFiles, JCVConfig jCVConfig) {
        if (jcvFileInfo != null) {
            
            LoggerFactory.debug ("process link:" + historylink);
            
            // version 0.0.2
            boolean isReplace = false;
            String versionStr = "";
            
            if (JCVFileInfo.CSS.equals (jcvFileInfo.getFileType ())) {
                //if (!checkStrIsInList (jcvFileInfo.getRelativelyFilePath (),jCVConfig.getExcludesCss (),true)) {
                if(!checkJcvIsSkip(jcvFileInfo,jCVConfig)){
                    if (jCVConfig.isCompressionCss () == true) {
                        if ((jcvFileInfo.getFileName ().indexOf (jCVConfig.getSkipFileNameSuffix () + "."
                                        + jcvFileInfo.getFileType ()) != -1)) {
                            if (jCVConfig.getCssMethod () == JCVMethodEnum.MD5_METHOD
                                            || jCVConfig.getCssMethod () == JCVMethodEnum.TIMESTAMP_METHOD) {
                                versionStr = getVersionStr (jcvFileInfo,false,false,jCVConfig
                                                .getUserCompressionSuffix (),historylink,processSuccessFiles,jCVConfig);
                            }
                            else if (jCVConfig.getCssMethod () == JCVMethodEnum.MD5FileName_METHOD) {
                                versionStr = getVersionStr (jcvFileInfo,true,false,jCVConfig
                                                .getUserCompressionSuffix (),historylink,processSuccessFiles,jCVConfig);
                                isReplace = true;
                            }
                            else {
                                LoggerFactory.warn (" not support method method:" + jCVConfig.getCssMethod ().name ());
                                return ;
                            }
                        }
                        else {
                            
                            // 压缩
                            if (jCVConfig.getCssMethod () == JCVMethodEnum.MD5_METHOD
                                            || jCVConfig.getCssMethod () == JCVMethodEnum.TIMESTAMP_METHOD) {
                                versionStr = getVersionStr (jcvFileInfo,false,true,jCVConfig
                                                .getUserCompressionSuffix (),historylink,processSuccessFiles,jCVConfig);
                            }
                            else if (jCVConfig.getCssMethod () == JCVMethodEnum.MD5FileName_METHOD) {
                                versionStr = getVersionStr (jcvFileInfo,true,true,jCVConfig
                                                .getUserCompressionSuffix (),historylink,processSuccessFiles,jCVConfig);
                            }
                            else {
                                LoggerFactory.warn (" not support method method:" + jCVConfig.getCssMethod ().name ());
                                return ;
                            }
                            isReplace = true;
                        }
                        
                    }
                    else {
                        if (jCVConfig.getCssMethod () == JCVMethodEnum.MD5_METHOD
                                        || jCVConfig.getCssMethod () == JCVMethodEnum.TIMESTAMP_METHOD) {
                            versionStr = getVersionStr (jcvFileInfo,false,false,jCVConfig
                                            .getUserCompressionSuffix (),historylink,processSuccessFiles,jCVConfig);
                        }
                        else if (jCVConfig.getCssMethod () == JCVMethodEnum.MD5FileName_METHOD) {
                            versionStr = getVersionStr (jcvFileInfo,true,false,jCVConfig
                                            .getUserCompressionSuffix (),historylink,processSuccessFiles,jCVConfig);
                            isReplace = true;
                        }
                        else {
                            LoggerFactory.warn (" not support method method:" + jCVConfig.getCssMethod ().name ());
                            return ;
                        }
                    }
                }
                else {
                    
                    LoggerFactory.debug (" skip file :" + jcvFileInfo.getFileType ());
                    return ;
                    
                }
            }
            else if (JCVFileInfo.JS.equals (jcvFileInfo.getFileType ())) {
                //if (!checkStrIsInList (jcvFileInfo.getRelativelyFilePath (),jCVConfig.getExcludesJs (),true)) {
                if(!checkJcvIsSkip(jcvFileInfo,jCVConfig)){
                    if (jCVConfig.isCompressionJs () == true) {
                        if ((jcvFileInfo.getFileName ().indexOf (jCVConfig.getSkipFileNameSuffix () + "."
                                        + jcvFileInfo.getFileType ()) != -1)) {
                            if (jCVConfig.getJsMethod () == JCVMethodEnum.MD5_METHOD
                                            || jCVConfig.getJsMethod () == JCVMethodEnum.TIMESTAMP_METHOD) {
                                versionStr = getVersionStr (jcvFileInfo,false,false,jCVConfig
                                                .getUserCompressionSuffix (),historylink,processSuccessFiles,jCVConfig);
                            }
                            else if (jCVConfig.getJsMethod () == JCVMethodEnum.MD5FileName_METHOD) {
                                versionStr = getVersionStr (jcvFileInfo,true,false,jCVConfig
                                                .getUserCompressionSuffix (),historylink,processSuccessFiles,jCVConfig);
                                isReplace = true;
                            }
                            else {
                                LoggerFactory.warn (" not support method method:" + jCVConfig.getJsMethod ().name ());
                                return ;
                            }
                        }
                        else {
                            
                            // 压缩
                            if (jCVConfig.getJsMethod () == JCVMethodEnum.MD5_METHOD
                                            || jCVConfig.getJsMethod () == JCVMethodEnum.TIMESTAMP_METHOD) {
                                versionStr = getVersionStr (jcvFileInfo,false,true,jCVConfig
                                                .getUserCompressionSuffix (),historylink,processSuccessFiles,jCVConfig);
                            }
                            else if (jCVConfig.getJsMethod () == JCVMethodEnum.MD5FileName_METHOD) {
                                versionStr = getVersionStr (jcvFileInfo,true,true,jCVConfig
                                                .getUserCompressionSuffix (),historylink,processSuccessFiles,jCVConfig);
                            }
                            else {
                                LoggerFactory.warn (" not support method method:" + jCVConfig.getJsMethod ().name ());
                                return ;
                            }
                            isReplace = true;
                        }
                        
                    }
                    else {
                        if (jCVConfig.getJsMethod () == JCVMethodEnum.MD5_METHOD
                                        || jCVConfig.getJsMethod () == JCVMethodEnum.TIMESTAMP_METHOD) {
                            versionStr = getVersionStr (jcvFileInfo,false,false,jCVConfig
                                            .getUserCompressionSuffix (),historylink,processSuccessFiles,jCVConfig);
                        }
                        else if (jCVConfig.getJsMethod () == JCVMethodEnum.MD5FileName_METHOD) {
                            versionStr = getVersionStr (jcvFileInfo,true,false,jCVConfig
                                            .getUserCompressionSuffix (),historylink,processSuccessFiles,jCVConfig);
                            isReplace = true;
                        }
                        else {
                            
                            LoggerFactory.warn (" not support method method:" + jCVConfig.getJsMethod ().name ());
                            return ;
                            
                        }
                    }
                }
                else {
                    
                    LoggerFactory.debug (" break file :" + jcvFileInfo.getFileType ());
                    return ;
                    
                }
            }
            else {
                
                LoggerFactory.warn (" not support file type:" + jcvFileInfo.getFileType ());
                return ;
                
            }
            
            processSuccessFiles.add (jcvFileInfo);
            if (isReplace) {
                // 替换
                int fileNamelenth = jcvFileInfo.getFileName ().length ();
                int parLenth = 0;
                String par = "";
                if (historylink.indexOf ("?") > 0) {
                    
                    par = getUrlpPar (historylink);
                    parLenth = par.length ();
                    parLenth++;
                    versionStr += "?" + par;
                }
                sb.replace (end - fileNamelenth - parLenth,end,"");
                sb.insert (end - fileNamelenth - parLenth,versionStr);
            }
            else {
                sb.insert (end,versionStr);
            }
            
        }
        
    }
    
    /**
     * 获取版本号字符串.
     *
     * @param jcvFileInfo the jcv file info
     * @param isMd5FileName the is md5 file name
     * @param isCompression            压缩
     * @param suffix the suffix
     * @param historylink the historylink
     * @param processSuccessFiles the process success files
     * @param jCVConfig the j cv config
     * @return the version str
     */
    private String getVersionStr(JCVFileInfo jcvFileInfo, final boolean isMd5FileName, final boolean isCompression, final String suffix, final String historylink, List<JCVFileInfo> processSuccessFiles, JCVConfig jCVConfig) {
        String versionStr = "";
        if (isCompression) {
            // 压缩
            if (isMd5FileName == false) {
                
                int indexlastOf = jcvFileInfo.getFileName ().lastIndexOf ('.');
                String fileName = "";
                if (indexlastOf != -1) {
                    fileName = jcvFileInfo.getFileName ().substring (0,indexlastOf);
                }
                else {
                    fileName = jcvFileInfo.getFileName ();
                }
                
                versionStr = fileName + "." + suffix + "." + jcvFileInfo.getFileType ();// +
                                                                                        // jcvFileInfo.getFileVersion();
                jcvFileInfo.setFinalFileName (versionStr);
                String par = "";
                if (historylink.indexOf ("?") > 0) {
                    
                    par = getUrlpPar (historylink);
                    
                    versionStr += "?" + jCVConfig.getVersionLable () + "=" + jcvFileInfo.getFileVersion () + "&" + par;
                }
                else {
                    versionStr += "?" + jCVConfig.getVersionLable () + "=" + jcvFileInfo.getFileVersion ();
                }
                
            }
            else {
                
                String par = "";
                versionStr = jcvFileInfo.getFileVersion () + "." + suffix + "." + jcvFileInfo.getFileType ();
                jcvFileInfo.setFinalFileName (versionStr);
                if (historylink.indexOf ("?") > 0) {
                    
                    par = getUrlpPar (historylink);
                    
                    versionStr += "?" + par;
                }
            }
            
        }
        else {
            if (isMd5FileName) {
                
                String par = "";
                versionStr = jcvFileInfo.getFileVersion () + "." + jcvFileInfo.getFileType ();
                jcvFileInfo.setFinalFileName (versionStr);
                if (historylink.indexOf ("?") > 0) {
                    
                    par = getUrlpPar (historylink);
                    
                    versionStr += "?" + par;
                }
            }
            else {
                if (historylink.indexOf ("?") > 0) {
                    versionStr = "&" + jCVConfig.getVersionLable () + "=" + jcvFileInfo.getFileVersion ();
                }
                else {
                    versionStr = "?" + jCVConfig.getVersionLable () + "=" + jcvFileInfo.getFileVersion ();
                }
            }
        }
        
        if(jCVConfig.getVersionValLenth()!=-1){
            if(versionStr.length()>jCVConfig.getVersionValLenth()){
                versionStr=versionStr.substring(0, jCVConfig.getVersionValLenth()-1<0?0:jCVConfig.getVersionValLenth()-1);
            }
        }
        
        return versionStr;
    }
    
    /**
     * Removes the url par.
     *
     * @param tempUrl the temp url
     * @return the string
     */
    private String removeUrlPar(String tempUrl) {
        if (tempUrl.indexOf ("?") > 0) {
            String[] split = tempUrl.split ("\\?");
            if (split.length == 2) {
                tempUrl = split[0];
            }
        }
        
        return tempUrl;
    }
    
    /**
     * Gets the urlp par.
     *
     * @param tempUrl the temp url
     * @return the urlp par
     */
    private String getUrlpPar(String tempUrl) {
        if (tempUrl.indexOf ("?") > 0) {
            String[] split = tempUrl.split ("\\?");
            if (split.length == 2) {
                // tempUrl=split[0];
                return split[1];
            }
            else {
                return "";
            }
        }
        return "";
        
    }
    
    
    /**
     * 
     * 获取文件版本信息
     * 
     * @param f
     * @param en
     * @return
     */
    protected String getFileVersion(File f, JCVMethodEnum en) {
        try {
            switch (en) {
                case MD5_METHOD:
                    return Md5Utils.getFileMD5(f);
                
                case MD5FileName_METHOD:
                    return Md5Utils.getFileMD5(f);
                
                case TIMESTAMP_METHOD:
                    return new Date().getTime() + "";
                
                default:
                    return Md5Utils.getFileMD5(f);
                
            }
        }
        catch (Exception e) {
            LoggerFactory.info(e.getMessage());
        }
        return new Date().getTime() + "";
        
    }
    
    
    /**
     * 
     * 复制md5文件处理
     * 
     * @param jcf
     * @param outJSCSSDirPath
     */
    protected void copyMd5FileNameJssCss(JCVFileInfo jcf, final String outJSCSSDirPath) {
        
        String tempPath = BaseUtils.getJSSCSSOutPath(jcf, true, JCVMethodEnum.MD5FileName_METHOD, outJSCSSDirPath, jCVConfig);
        File f = new File(BaseUtils.getFilePathDir(tempPath));
        if (!f.exists()) {
            f.mkdirs();
        }
        
        try {
            if (null == jcf.getFinalFileName() || "".equals(jcf.getFinalFileName())) {
                return;
            }
            LoggerFactory.debug("copy md5 name  file:" + tempPath);
            FileUtils.fileChannelCopy(jcf.getFile(), new File(tempPath));
        }
        catch (IOException e) {
            LoggerFactory.error("copy file error:", e);
        }
        
    }
    
    /**
     * 
     * 复制未处理文件
     * 
     * @param jcf
     */
    protected void copyFileJssCss(JCVFileInfo jcf, final String outJSCSSDirPath) {
        
        String tempPath = BaseUtils.getJSSCSSOutPath(jcf, false, JCVMethodEnum.DEFAULTS_UNUSED, outJSCSSDirPath, jCVConfig);
        File f = new File(BaseUtils.getFilePathDir(tempPath));
        if (!f.exists()) {
            f.mkdirs();
        }
        try {
            LoggerFactory.debug("copy Untreated file:" + tempPath);
            FileUtils.fileChannelCopy(jcf.getFile(), new File(tempPath));
        }
        catch (IOException e) {
            LoggerFactory.error("copy file error:", e);
        }
        
    }
    
    /**
     * 
     * 复制MD5FileName_METHOD 文件
     * 
     * @param processSuccessFiles
     */
    protected void processMd5FileCpoy(List<JCVFileInfo> processSuccessFiles) {
        
        if (jCVConfig.isCompressionCss() == false && jCVConfig.isCompressionJs() == false) {
            return;
        }
        
        // 复制MD5FileName_METHOD 文件
        if (jCVConfig.getCssMethod() == JCVMethodEnum.MD5FileName_METHOD || jCVConfig.getJsMethod() == JCVMethodEnum.MD5FileName_METHOD) {
            
            for (JCVFileInfo info : processSuccessFiles) {
                if (jCVConfig.getCssMethod() == JCVMethodEnum.MD5FileName_METHOD && JCVFileInfo.CSS.equals(info.getFileType()) && jCVConfig.isCompressionCss() == false) {
                    copyMd5FileNameJssCss(info, jCVConfig.getOutJSCSSDirPath());
                }
                if (jCVConfig.getJsMethod() == JCVMethodEnum.MD5FileName_METHOD && JCVFileInfo.JS.equals(info.getFileType()) && jCVConfig.isCompressionJs() == false) {
                    copyMd5FileNameJssCss(info, jCVConfig.getOutJSCSSDirPath());
                }
            }
        }
        
    }
    
    /**
     * 
     * 复制未处理文件
     * 
     * @param processSuccessFiles
     */
    protected void doCopyUntreatedFile(List<JCVFileInfo> processSuccessFiles) {
        // 复制未处理文件
        if (jCVConfig.isCompressionJs() == true || jCVConfig.isCompressionCss() == true || jCVConfig.getCssMethod() == JCVMethodEnum.MD5FileName_METHOD || jCVConfig.getJsMethod() == JCVMethodEnum.MD5FileName_METHOD) {
            
            final int size = processSuccessFiles.size();
            List<JCVFileInfo> copyFiles = new ArrayList<JCVFileInfo>(size);
            Map<JCVFileInfo, String> processFilesMap = new HashMap<JCVFileInfo, String>((int) ((float) size / 0.75F + 1.00F));
            for (JCVFileInfo info : processSuccessFiles) {
                processFilesMap.put(info, "1");
            }
            
            for (Entry<String, JCVFileInfo> map : jcvFiles.entrySet()) {
                String string = processFilesMap.get(map.getValue());
                if (string == null && map.getValue().isCopy() == false) {
                    if (map.getValue().getFileType().equals(JCVFileInfo.CSS) && (jCVConfig.isCompressionCss() == true || jCVConfig.getCssMethod() == JCVMethodEnum.MD5FileName_METHOD)) {
                        copyFiles.add(map.getValue());
                    }
                    else if (map.getValue().getFileType().equals(JCVFileInfo.JS) && (jCVConfig.isCompressionJs() == true || jCVConfig.getJsMethod() == JCVMethodEnum.MD5FileName_METHOD)) {
                        copyFiles.add(map.getValue());
                    }
                    else {
                        LoggerFactory.warn("fiel type error :" + map.getValue().getFileType());
                    }
                    
                }
                
            }
            for (JCVFileInfo info : copyFiles) {
                copyFileJssCss(info, jCVConfig.getOutJSCSSDirPath());
            }
            displayInfo.add(DISPLAY_STR + "copy untreated file file size: " + copyFiles.size());
        }
        else {
            // 不是使用文件名md5方式,复制全部
            for (Entry<String, JCVFileInfo> map : jcvFiles.entrySet()) {
                JCVFileInfo info = map.getValue();
                if (info == null) {
                    continue;
                }
                copyFileJssCss(info, jCVConfig.getOutJSCSSDirPath());
                
            }
        }
    }
    
    
    /*
     * <p>Title: initJcv</p>  
     * <p>Description: 初始化</p>  
     * @param webAppRoot  
     * @see com.iqarr.maven.plugin.support.ProcessFactory#initJcv(java.lang.String)  
     */
     
     @Override
     public abstract void initJcv(String webAppRoot) ;
     
      
     /*
      * <p>Title: doProcessPageFile</p>
      * <p>Description: </p>
      * 
      * @param pages
      * 
      * @see com.iqarr.maven.plugin.support.ProcessFactory#doProcessPageFile(java.util.List)
      */
     
     @Override
     public abstract void doProcessPageFile() ;
	
}
