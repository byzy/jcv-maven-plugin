package com.iqarr.maven.plugin.domain;

import java.io.File;

/**  
* @author 
*		zhangyong         
*/
public class PageInfo {
    
    private File file;
    
    
    /**
     * 输出文件位置
     */
    private File  OutFile;

    /**
     * 获取  
     * @return file
     */
    public File getFile() {
        return file;
    }

    /**
     * 设置 
     * @param file 
     */
    public void setFile(File file) {
        this.file = file;
    }

    /**
     * 获取 输出文件位置 
     * @return outFile
     */
    public File getOutFile() {
        return OutFile;
    }

    /**
     * 设置 输出文件位置
     * @param outFile 输出文件位置
     */
    public void setOutFile(File outFile) {
        OutFile = outFile;
    }
    
    
    
    
}
