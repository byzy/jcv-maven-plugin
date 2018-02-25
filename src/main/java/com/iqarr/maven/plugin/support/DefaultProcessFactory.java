package com.iqarr.maven.plugin.support;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.iqarr.maven.plugin.constant.JCVConstant;
import com.iqarr.maven.plugin.domain.JCVConfig;
import com.iqarr.maven.plugin.domain.JCVFileInfo;
import com.iqarr.maven.plugin.domain.PageInfo;
import com.iqarr.maven.plugin.support.logger.LoggerFactory;
import com.iqarr.maven.plugin.utils.BaseUtils;
import com.iqarr.maven.plugin.utils.FileUtils;

/**
 * A factory for creating DefaultProcess objects.
 *
 * @author zhangyong
 */
public class DefaultProcessFactory extends AbstractProcessFactory {
    
    /**
     * 
     *
     * @param jCVConfig
     *            the jcv config
     */
    public DefaultProcessFactory(JCVConfig jCVConfig) {
        super(jCVConfig);
    }
    
    /*
     * <p>Title: initJcv</p>
     * <p>Description: 初始化</p>
     * 
     * @param webAppRoot
     * 
     * @see com.iqarr.maven.plugin.support.ProcessFactory#initJcv(java.lang.String)
     */
    
    @Override
    public void initJcv(String webAppRoot) {
        if (jcvFiles == null) {
            jcvFiles = new HashMap<String, JCVFileInfo>();
        }
        getAllCssFile(jcvFiles, webAppRoot, jCVConfig);
        getAllJsFile(jcvFiles, webAppRoot, jCVConfig);
        
        for (Entry<String, JCVFileInfo> f : jcvFiles.entrySet()) {
            LoggerFactory.debug("find type:" + f.getValue().getFileType() + " file:" + f.getKey() + " md5:" + f.getValue().getFileVersion());
        }
        if (pages == null) {
            pages = new ArrayList<PageInfo>();
        }
        getAllProcessFile(pages, webAppRoot, jCVConfig.getPageSuffixs());
        // webRootName
        String out = jCVConfig.getOutDirRoot();
        for (int i = 0; i < pages.size(); i++) {
            
            String path = pages.get(i).getFile().getPath();
            // path= path.replaceAll(webRoot, "");
            path = path.substring(webAppRoot.length(), path.length());
            String tm = "";
            if (path.endsWith(FileUtils.getSystemFileSeparator())) {
                tm = out + path;
            }
            else {
                tm = out + FileUtils.getSystemFileSeparator() + path;
            }
            int lastIndexOf = tm.lastIndexOf(FileUtils.getSystemFileSeparator());
            String sub = tm.substring(0, lastIndexOf);
            File f = new File(sub);
            if (!f.exists()) {
                f.mkdirs();
            }
            pages.get(i).setOutFile(new File(tm));
        }
    }
    
    /*
     * <p>Title: doProcessPageFile</p>
     * <p>Description: </p>
     * 
     * @param pages
     * 
     * @see com.iqarr.maven.plugin.support.ProcessFactory#doProcessPageFile(java.util.List)
     */
    
    @Override
    public void doProcessPageFile() {
        
        if (null == jCVConfig) {
            LoggerFactory.error("JcvConfig is null");
        }
        // 处理成功的js css文件
        List<JCVFileInfo> processSuccessFiles = new ArrayList<JCVFileInfo>();
        for (PageInfo pageInfo : pages) {
            LoggerFactory.debug("find page:" + pageInfo.getFile().getPath());
            
            try {
                String strAll = FileUtils.readToStr(pageInfo.getFile(), jCVConfig.getSourceEncoding());
                List<String> savehtml = new ArrayList<String>();
                if (strAll == null || strAll.length() == 0) {
                    continue;
                }
                StringBuffer sb = null;
                if (sb == null) {
                    sb = new StringBuffer(strAll);
                }
                int ret = processCSSVersion(sb, 0, processSuccessFiles, jCVConfig);
                int ret2 = processJSVersion(sb, 0, processSuccessFiles, jCVConfig);
                int ret3 = 0;
                if (jCVConfig.isClearPageComment()) {
                    ret3 = processPageComment(sb, 0, jCVConfig);
                    FileUtils.clearBlankLines(sb, jCVConfig.getSourceEncoding());
                }
                if (ret == 1 || ret2 == 1 || ret3 == 1) {
                    savehtml.add(sb.toString());
                    sb = null;
                    
                }
                else {
                    savehtml.add(sb.toString());
                    sb = null;
                    
                }
                
                LoggerFactory.debug(" page:" + pageInfo.getFile().getName() + " Processing is complete");
                FileUtils.writeFile(pageInfo.getOutFile(), jCVConfig.getSourceEncoding(), savehtml);
                
            }
            catch (Exception e) {
                LoggerFactory.error(" the file process error :" + pageInfo.getFile().getPath(), e);
            }
        } // for end
        
        // 复制md5文件
        processMd5FileCpoy(processSuccessFiles);
        
        // 开始压缩
        if (jCVConfig.isCompressionCss() == true || jCVConfig.isCompressionJs() == true) {
            
            processCompressionJsCss(processSuccessFiles, jCVConfig.getOutJSCSSDirPath(), jCVConfig);
        }
        // 处理未使用文件
        doCopyUntreatedFile(processSuccessFiles);
        displayInfo.add(DISPLAY_STR + "process success file size: " + processSuccessFiles.size());
    }
    
   
    
   
    
    /**
     * 
     * 获取全部js文件
     * 
     * @param collected
     */
    public void getAllJsFile(Map<String, JCVFileInfo> collected, final String rootPath, final JCVConfig jcvConfig) {
        if (collected == null) {
            collected = new HashMap<String, JCVFileInfo>();
        }
        String jsRoot = "";
        if (null != jcvConfig.getJsPhysicalRootPath() && !"".equals(jcvConfig.getJsPhysicalRootPath())) {
            jsRoot = jcvConfig.getJsPhysicalRootPath();
        }
        else {
            jsRoot = rootPath;
        }
        
        List<String> su = new ArrayList<String>();
        su.add("js");
        String globaJslPrefixPath = jCVConfig.getGlobaJslPrefixPath();
        if (globaJslPrefixPath != null && !"".equals(globaJslPrefixPath)) {
            if (jsRoot.endsWith(FileUtils.getSystemFileSeparator())) {
                jsRoot += globaJslPrefixPath;
            }
            else {
                jsRoot += FileUtils.getSystemFileSeparator() + globaJslPrefixPath;
            }
        }
        if (!jsRoot.endsWith(FileUtils.getSystemFileSeparator())) {
            jsRoot += FileUtils.getSystemFileSeparator();
        }
        List<File> listFile = new ArrayList<File>();
        FileUtils.collectFiles(listFile, new File(jsRoot), su);
        JCVFileInfo jcv = null;
        for (File f : listFile) {
            String path = f.getPath();
            path = path.substring(jsRoot.length(), path.length());
            if (jcv == null) {
                jcv = new JCVFileInfo();
            }
            if (!FileUtils.getSystemFileSeparatorIslinux()) {
                path = BaseUtils.replaceLinuxSystemLine(path);
            }
            jcv.setFileType(JCVFileInfo.JS);
            jcv.setFileVersion(getFileVersion(f, jCVConfig.getJsMethod()));
            jcv.setRelativelyFilePath(path);
            jcv.setFileName(f.getName());
            try {
                jcv.setFileHashKey(BaseUtils.getFileHashKey(f, JCVConstant.FILE_HASH_MD5));
            }
            catch (Exception e) {
                LoggerFactory.error(e);
            }
            jcv.setFile(f);
            // version 6.0
            if (null != jcvConfig.getJsConstantName() && !"".equals(jcvConfig.getJsConstantName())) {
                //path = jcvConfig.getJsConstantName() + JCVConstant.HTML_PATH_SEPARATED + path;
                path = jcvConfig.getJsConstantName()  + path;
            }
            collected.put(path, jcv);
            jcv = null;
        }
    }
    
    /**
     * 
     * 获取全部css文件
     * 
     * @param collected
     */
    public void getAllCssFile(Map<String, JCVFileInfo> collected, final String rootPath, final JCVConfig jcvConfig) {
        if (collected == null) {
            collected = new HashMap<String, JCVFileInfo>();
        }
        String cssRoot = "";
        if (null != jcvConfig.getCssPhysicalRootPath() && !"".equals(jcvConfig.getCssPhysicalRootPath())) {
            cssRoot = jcvConfig.getCssPhysicalRootPath();
        }
        else {
            cssRoot = rootPath;
        }
        List<String> su = new ArrayList<String>();
        su.add("css");
        String globaCsslPrefixPath = jCVConfig.getGlobaCsslPrefixPath();
        if (globaCsslPrefixPath != null && !"".equals(globaCsslPrefixPath)) {
            if (cssRoot.endsWith(FileUtils.getSystemFileSeparator())) {
                cssRoot += globaCsslPrefixPath;
            }
            else {
                cssRoot += FileUtils.getSystemFileSeparator() + globaCsslPrefixPath;
            }
        }
        if (!cssRoot.endsWith(FileUtils.getSystemFileSeparator())) {
            cssRoot += FileUtils.getSystemFileSeparator();
        }
        List<File> listFile = new ArrayList<File>();
        FileUtils.collectFiles(listFile, new File(cssRoot), su);
        JCVFileInfo jcv = null;
        for (File f : listFile) {
            String path = f.getPath();
            // path= path.replaceFirst(webRoot, "");
            path = path.substring(cssRoot.length(), path.length());
            if (jcv == null) {
                jcv = new JCVFileInfo();
            }
            if (!FileUtils.getSystemFileSeparatorIslinux()) {
                path = BaseUtils.replaceLinuxSystemLine(path);
            }
            jcv.setFileType(JCVFileInfo.CSS);
            jcv.setFileVersion(getFileVersion(f, jCVConfig.getCssMethod()));
            jcv.setRelativelyFilePath(path);
            jcv.setFileName(f.getName());
            jcv.setFile(f);
            try {
                jcv.setFileHashKey(BaseUtils.getFileHashKey(f, JCVConstant.FILE_HASH_MD5));
            }
            catch (Exception e) {
                LoggerFactory.error(e);
            }
            // version 6.0
            if (null != jcvConfig.getCssConstantName() && !"".equals(jcvConfig.getCssConstantName())) {
              //  path = jcvConfig.getCssConstantName() + JCVConstant.HTML_PATH_SEPARATED + path;
                path = jcvConfig.getCssConstantName() + path;
            }
            collected.put(path, jcv);
            jcv = null;
        }
    }
    
    /**
     * 
     * 获取全部的文件
     * 
     * @param files
     * @param suffix
     */
    public void getAllProcessFile(List<PageInfo> files, String webRoot, List<String> suffix) {
        if (files == null) {
            files = new ArrayList<PageInfo>();
        }
        List<File> fs = new ArrayList<File>();
        FileUtils.collectFiles(fs, new File(webRoot), suffix);
        PageInfo pi = null;
        for (File file : fs) {
            if (pi == null) {
                pi = new PageInfo();
            }
            pi.setFile(file);
            files.add(pi);
            pi = null;
        }
    }
    
    
    
    
    
}
