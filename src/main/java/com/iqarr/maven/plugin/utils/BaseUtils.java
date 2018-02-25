package com.iqarr.maven.plugin.utils;

import java.io.File;
import java.util.List;

import com.iqarr.maven.plugin.constant.JCVConstant;
import com.iqarr.maven.plugin.domain.JCVConfig;
import com.iqarr.maven.plugin.domain.JCVFileInfo;
import com.iqarr.maven.plugin.domain.JCVMethodEnum;

/**
 * @author
 *         zhangyong
 */
public class BaseUtils {
    
    /**
     * 
     * 比较数组是否相等
     * 
     * @param source
     * @param startIndex
     * @param comparisonSource
     * @return
     */
    public static boolean comparisonCharArray(final char[] source, final int startIndex, final char[] comparisonSource) {
        
        int sourceLenth = source.length;
        int comLenth = comparisonSource.length;
        
        for (int i = 0; i < comLenth; i++) {
            if (sourceLenth <= (i + startIndex)) {
                return false;
            } else {
                if (source[startIndex + i] == comparisonSource[i]) {
                    if (i == (comLenth - 1)) {
                        return true;
                    } else {
                        continue;
                    }
                } else {
                    return false;
                }
            }
        }
        
        return false;
    }
    
    /**
     * 
     * 比较数组是否相等(向上比较)
     * 
     * @param source
     * @param startIndex
     * @param comparisonSource
     * @return
     */
    public static boolean comparisonUpCharArray(final char[] source, final int startIndex, final char[] comparisonSource) {
        
        int sourceLenth = source.length;
        int comLenth = comparisonSource.length;
        
        for (int i = 0,j=comLenth-1;(i<sourceLenth-startIndex && j>=0); i++,j--) {
          
                if (source[startIndex - i] == comparisonSource[j]) {
                    if (j==0) {
                        return true;
                    } else {
                        continue;
                    }
                } else {
                    return false;
                }
            }
        
        
        return false;
    }
    
    /**
     * 
     * 检查下一个字符串位置
     * 
     * @param cas
     * @param index
     * @param chars
     * @return
     */
    public static int checkNextStrIndex(final char[] cas, final int index, final String chars) {
        
        char[] sub = chars.toCharArray();
        if (sub.length < 0) {
            return -1;
        }
        for (int i = index; i < cas.length; i++) {
            
            if (cas[i] == sub[0]) {
                for (int subI = 0; subI < sub.length; subI++) {
                    if (cas[i + subI] != sub[subI]) {
                        break;
                    } else {
                        if (subI == sub.length - 1) {
                            return i;
                        }
                    }
                }
            }
        }
        
        return -1;
    }
    
    /**
     * 
     * 检查上一个字符的位置
     * 
     * @param cas
     * @param index
     * @param chars
     * @return
     */
    public static int checkUpStrIndex(final char[] cas, final int index, final String chars) {
        char[] sub = chars.toCharArray();
        if (sub.length < 0) {
            return -1;
        }
        for (int i = index; i >= 0; i--) {
            
            if (cas[i] == sub[0]) {
                for (int subI = 0; subI < sub.length; subI++) {
                    if (cas[i + subI] != sub[subI]) {
                        break;
                    } else {
                        if (subI == sub.length - 1) {
                            return i;
                        }
                    }
                }
            }
        }
        
        return -1;
    }
    
    public static int checkNextCharIndex(final char[] cas, final int index, final char[] sub) {
        
        for (int i = index; i < cas.length; i++) {
            if (cas[i] == sub[0]) {
                for (int subI = 0; subI < sub.length; subI++) {
                    if (cas[i + subI] != sub[subI]) {
                        break;
                    } else {
                        if (subI == sub.length - 1) {
                            return i;
                        }
                    }
                }
            }
        }
        
        return -1;
    }
    /**
     * 
     * 检查下一个字符位置
     * @param cas
     * @param index
     * @param cr
     * @return
     */
    public static int checkNextCharIndex(final char[] cas, final int index, final char cr) {
        char[] sub = new char[1];
        sub[0] = cr;
        return checkNextCharIndex(cas, index, sub);
    }
    
    /**
     * 
     * 检查str是否在list中
     * @param checkStr
     * @param list
     * @param isLike  是否模糊匹配（暂时未实现）
     * @return true 在
     */
    public static boolean checkStrIsInList(final String checkStr,final List<String> list,final boolean isLike){
        if(null==checkStr || "".equals(checkStr) ){
            return false;
        }
        if(list ==null){
            return false;
        }
        if(isLike){
            //模糊
            return list.contains(checkStr);
        }else {
            return list.contains(checkStr);
        }
        
        
    }
    
    /**
     * 
     * 替换字符串中的所有单个字符
     * @param str
     * @param hisStr
     * @param replacement
     * @return
     */
    public static String replaceAll(final String str,final char hisStr,final char replacement){
        char[] sub = str.toCharArray();
    
        StringBuilder sb=new StringBuilder();
        int size=sub.length;
        for(int i=0;i<size;i++){
           if(sub[i]==hisStr){
               sb.append(replacement);
           }else {
               sb.append(sub[i]);
           }
        }
        return sb.toString();
    }
    
    /**
     * 
     * <p>替换文件路径为linux 路径</p>
     * @param path
     * @return
     */
    public static String replaceLinuxSystemLine(final String path){
        if(FileUtils.getSystemFileSeparatorIslinux()){
            //linux
            return path;
        }else {
            if(path==null || "".equals(path)){
                return path;
            }
            //windows
            return replaceAll(path,'\\','/');
        }
      
    }
    /**
     * 
     * 转换为当前系统路径
     * @param path
     * @return
     */
    public static String replacecurrentSystemLine(final String path){
        if(FileUtils.getSystemFileSeparatorIslinux()){
            //linux
            return replaceAll(path,'\\','/'); 
        }else {
            //windows
            return replaceAll(path,'/','\\'); 
        }
      
    }
    /**
     * 
     * 获取文件输出路径
     * @param jcv
     * @param isCompression 是否压缩
     * @param meth 处理方法
     * @return
     */
    public static String getJSSCSSOutPath(final JCVFileInfo jcv,final boolean isCompression, final JCVMethodEnum meth,final String outDir ,final JCVConfig jCVConfig,final String ... userCompressionSuffix ) {
        
        StringBuilder tempPath=new StringBuilder();
        if(outDir!=null ){
            tempPath.append(outDir);
        }
        if (!outDir.endsWith(FileUtils.getSystemFileSeparator())) {
            tempPath.append(FileUtils.getSystemFileSeparator());
        }
        
      //添加alise
    	if(JCVFileInfo.CSS.equals(jcv.getFileType())
    					&& (null!=jCVConfig.getCssConstantName ()&& !"".equals (jCVConfig.getCssConstantName ()) )){
    		if(null!=jCVConfig.getCssConstantAliasPath () && !"".equals (jCVConfig.getCssConstantAliasPath ())){
    			if(jCVConfig.getCssConstantAliasPath ().startsWith ("/")){
    				tempPath.append(jCVConfig.getCssConstantAliasPath ().substring (1,jCVConfig.getCssConstantAliasPath ().length ()));
    			}else {
    				tempPath.append(jCVConfig.getCssConstantAliasPath ());
    			}
    			if(!jCVConfig.getCssConstantAliasPath ().endsWith ("/")){
    				tempPath.append(FileUtils.getSystemFileSeparator());
    			}
    			
    		}
    	}else if(JCVFileInfo.JS.equals(jcv.getFileType()) && 
    					(null!=jCVConfig.getCssConstantName () && !"".equals (jCVConfig.getCssConstantName ()))) {
    		if(null!=jCVConfig.getJsConstantAliasPath () && !"".equals (jCVConfig.getJsConstantAliasPath ())){
    			
    			if(jCVConfig.getJsConstantAliasPath ().startsWith ("/")){
    				tempPath.append(jCVConfig.getJsConstantAliasPath ().substring (1,jCVConfig.getJsConstantAliasPath ().length()));
    			}else {
    				tempPath.append(jCVConfig.getJsConstantAliasPath ());
    			}
    			if(!jCVConfig.getJsConstantAliasPath ().endsWith ("/")){
    				tempPath.append(FileUtils.getSystemFileSeparator());
    			 }
    		}
    	}

        if (meth == JCVMethodEnum.DEFAULTS_UNUSED) {
            if (outDir.endsWith(FileUtils.getSystemFileSeparator())) {
                tempPath.append(replacecurrentSystemLine(jcv.getRelativelyFilePath()));
            } else {
                tempPath.append(FileUtils.getSystemFileSeparator() + replacecurrentSystemLine(jcv.getRelativelyFilePath()));
                
            }
            return tempPath.toString();
        }
       
        if (JCVFileInfo.CSS.equals(jcv.getFileType()) || JCVFileInfo.JS.equals(jcv.getFileType())) {
            
            if (meth == JCVMethodEnum.MD5FileName_METHOD ||  isCompression) {
            	
            	
            	
                tempPath .append(replacecurrentSystemLine(jcv.getRelativelyFilePath()));
                int lastIndexOf = tempPath.lastIndexOf(FileUtils.getSystemFileSeparator());
                tempPath = tempPath.delete(lastIndexOf, tempPath.length()); //substring(0, lastIndexOf);
                
                tempPath.append(FileUtils.getSystemFileSeparator() +jcv.getFinalFileName()); //jcv.getFileVersion() + "." + userCompressionSuffix + "." + jcv.getFileType();
            } else {
                
                String fileName="";
                int indexSp = jcv.getFileName().lastIndexOf(".");
                if (indexSp > 0) {
                    fileName = jcv.getFileName().substring(0, indexSp);
                } else {
                    fileName = jcv.getFileName();
                }
                if(isCompression){
                    tempPath.append(fileName + "." +userCompressionSuffix[0]+"."+ jcv.getFileType() );
                }else {
                    tempPath.append(fileName + "." + jcv.getFileType() );// fielName + "." + userCompressionSuffix + "." + jcv.getFileType();
                }
            }
             
        } 
        
        return tempPath.toString();
    }
    
    
    /**
     * 
     * 获取文件的目录地址
     * @param path
     * @return
     */
    public static String getFilePathDir(String path){
        int lastIndexOf = path.lastIndexOf(FileUtils.getSystemFileSeparator());
        path = path.substring(0, lastIndexOf);
        
        return path;
    }
    
    /**
     * 
     * 获取文件hash
     * @param f
     * @param meth
     * @return
     * @throws Exception
     */
    public static String getFileHashKey(final File f,final String meth) throws Exception{
    	
    	if(JCVConstant.FILE_HASH_MD5.equals (meth)){
    		 return Md5Utils.getFileMD5(f);
    	}else {
    		throw new Exception ("error meth");
    	}
    }
    
    
    
    
}
