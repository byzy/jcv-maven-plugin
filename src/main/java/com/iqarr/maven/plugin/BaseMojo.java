package com.iqarr.maven.plugin;

import java.io.File;
import java.util.List;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Parameter;

import com.iqarr.maven.plugin.domain.JCVMethodEnum;
import com.iqarr.maven.plugin.domain.YUIConfig;

/**  
* @author 
*		zhangyong       
*/
public abstract class BaseMojo extends AbstractMojo{
    
    /**
     * 输出文件目录
     */
    @Parameter( defaultValue = "${project.build.directory}", property = "outputDir", required = true )
    protected File outputDirectory;
    
   
    
    /**
     * 默认检查文件后缀
     */
    @Parameter(defaultValue ="jsp")
    protected List<String> suffixs;
    
    /**
     * 基本的域名　script标签的src的前缀 
     * 该配置适合采用动静分离等方法，相对路径不需要配置
     */
    @Parameter(defaultValue ="http://script.iqarr.com")
    protected List<String> baseJsDomin;
    
    /**
     * 基本的域名　css 标签的link的前缀
     * 该配置适合采用动静分离等方法，相对路径不需要配置
     */
    @Parameter(defaultValue ="http://style.iqarr.com")
    protected List<String> baseCssDomin;
    
    /**
     * 全局js文件前缀  最中计算路径 baseCssDomin+globaJslPrefixPath+实际地址
     *    该地址指物理路径
     * 不配置该属性，就从根目录全部扫描
     */
    @Parameter(defaultValue ="")
    protected String globaJslPrefixPath="";
    
    /**
     * 全局css文件前缀
     *   该地址指物理路径 该地址指物理路径
     * 不配置该属性，就从根目录全部扫描
     */
    @Parameter(defaultValue ="")
    protected String globaCsslPrefixPath="";
    
    /**
     * js 使用方法
     */
    @Parameter(defaultValue ="MD5_METHOD")
    protected JCVMethodEnum globaJsMethod;
    
    /**
     * css 使用方法
     */
    @Parameter(defaultValue ="MD5_METHOD")
    protected JCVMethodEnum globaCssMethod;
    
    
    /**根目录名称 **/
    @Parameter(defaultValue ="${project.build.finalName}")
    protected String webRootName;
    
    /**版本号标签 **/
    @Parameter(defaultValue ="version")
    protected String versionLable;
    /**文件编码 **/
    @Parameter(defaultValue ="UTF-8")
    protected String sourceEncoding;
    /**清除页面注释 **/
    @Parameter(defaultValue ="false")
    protected boolean clearPageComment;
    
    /** 使用md5文件名输出js css 指定目录**/
    @Parameter(defaultValue ="")
    protected String outJSCSSDirPath;
    
    //version 0.0.2
    
    /**压缩css **/
    @Parameter(defaultValue ="false")
    protected boolean compressionCss;
    
    /** 压缩js**/
    @Parameter(defaultValue ="false")
    protected boolean compressionJs;
    
    /**压缩文件后缀 **/
    @Parameter(defaultValue ="min")
    protected String userCompressionSuffix;
    
    /** 排除js文件(只支持全路径匹配)**/
    @Parameter
    protected List<String> excludesJs;
    
    /** 排除css文件(只支持全路径匹配)**/
    @Parameter(defaultValue ="")
    protected List<String> excludesCss;
    
    /** yui config**/
    @Parameter
    protected YUIConfig yuiConfig;
    
    /**跳过文件名后缀(后缀之前的名称) **/
    @Parameter(defaultValue =".min")
    protected String skipFileNameSuffix;
    
    // version 6.0.1,兼容通用处理
    
    
    /**
     * js常量名称 jsConstantName/jspath
     *  \ 转义 ${parar}
     */
    @Parameter
    protected String jsConstantName;
    
    /**
     * css 常量名称  cssConstantName/csspath
     */
    @Parameter
    protected String cssConstantName;
    
    
    /** js(注意该目录不是全路径，该路径是指在outJssCssPath+jsConstantAliasPath) 常量对应的输出目录**/
    @Parameter
    protected String jsConstantAliasPath;
    
    /**  css (注意该目录不是全路径，该路径是指在outJssCssPath+cssConstantAliasPath) 常量输出目录**/
    @Parameter
    protected String cssConstantAliasPath;
    
    /**
     * 版本号长度
     */
    @Parameter(defaultValue ="-1")
    protected  int versionValLenth=-1;
    
    
}
