package com.iqarr.maven.plugin.domain;

import java.io.File;

/**  
* @author 
*		zhangyong        
*/
public class JCVFileInfo {
    
    /**
     * css
     */
    public static final String CSS="css"; //css
    /** js**/
    public static final String JS="js";
    
    /**相对文件路径  去除了文件webroot 和全部前缀路径 **/
    private String relativelyFilePath;
    
    /** 文件版本号**/
    private String fileVersion;
    
    /**文件类型 **/
    private String fileType;
    /** 文件名称**/
    private String fileName;
    
    /** 文件对象**/
    private File file;
    
    /** 输出文件对象**/
   // private File outFile;
    
    /**输出最终文件名　**/
    private String finalFileName;
    
    /**文件是否被复制 **/
    private boolean isCopy=false;
    
    /** 文件hash 值**/
    private String fileHashKey;
    /**
     * 获取 相对文件路径 
     * @return relativelyFilePath
     */
    public String getRelativelyFilePath() {
        return relativelyFilePath;
    }

    /**
     * 设置 相对文件路径
     * @param relativelyFilePath 相对文件路径
     */
    public void setRelativelyFilePath(String relativelyFilePath) {
        this.relativelyFilePath = relativelyFilePath;
    }

    /**
     * 获取 文件版本号 
     * @return fileVersion
     */
    public String getFileVersion() {
        return fileVersion;
    }

    /**
     * 设置 文件版本号
     * @param fileVersion 文件版本号
     */
    public void setFileVersion(String fileVersion) {
        this.fileVersion = fileVersion;
    }

    /**
     * 获取 文件类型 
     * @return fileType
     */
    public String getFileType() {
        return fileType;
    }

    /**
     * 设置 文件类型
     * @param fileType 文件类型
     */
    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    /**
     * 获取 文件名称 
     * @return fileName
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * 设置 文件名称
     * @param fileName 文件名称
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * 获取 文件对象 
     * @return file
     */
    public File getFile() {
        return file;
    }

    /**
     * 设置 文件对象
     * @param file 文件对象
     */
    public void setFile(File file) {
        this.file = file;
    }

  
   

    /*
    * <p>Title: equals</p>  
    * <p>Description: </p>  
    * @param obj
    * @return  
    * @see java.lang.Object#equals(java.lang.Object)  
    */
    
    /**
     * 获取 文件是否被复制 
     * @return isCopy
     */
    public boolean isCopy() {
        return isCopy;
    }

    /**
     * 设置 文件是否已被复制
     * @param isCopy 文件是否已被复制
     */
    public void setCopy(boolean isCopy) {
        this.isCopy = isCopy;
    }

    /**
     * 获取 输出文件对象 
     * @return finalFileName
     */
    public String getFinalFileName() {
        return finalFileName;
    }

    /**
     * 设置 输出文件对象
     * @param finalFileName 输出文件对象
     */
    public void setFinalFileName(String finalFileName) {
        this.finalFileName = finalFileName;
    }
    
    

    /**
	 * 获取 文件hash 值 
	 * @return fileHashKey
	 */
	public String getFileHashKey() {
		return fileHashKey;
	}

	/**
	 * 设置 文件hash 值
	 * @param fileHashKey 文件hash 值
	 */
	public void setFileHashKey(String fileHashKey) {
		this.fileHashKey = fileHashKey;
	}

	
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((file == null) ? 0 : file.hashCode());
        result = prime * result + ((fileHashKey == null) ? 0 : fileHashKey.hashCode());
        result = prime * result + ((fileName == null) ? 0 : fileName.hashCode());
        result = prime * result + ((fileType == null) ? 0 : fileType.hashCode());
        result = prime * result + ((fileVersion == null) ? 0 : fileVersion.hashCode());
        result = prime * result + ((finalFileName == null) ? 0 : finalFileName.hashCode());
        result = prime * result + (isCopy ? 1231 : 1237);
        result = prime * result + ((relativelyFilePath == null) ? 0 : relativelyFilePath.hashCode());
        return result;
    }

  
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        JCVFileInfo other = (JCVFileInfo) obj;
        if (file == null) {
            if (other.file != null) return false;
        }
        else if (!file.equals(other.file)) return false;
        if (fileHashKey == null) {
            if (other.fileHashKey != null) return false;
        }
        else if (!fileHashKey.equals(other.fileHashKey)) return false;
        if (fileName == null) {
            if (other.fileName != null) return false;
        }
        else if (!fileName.equals(other.fileName)) return false;
        if (fileType == null) {
            if (other.fileType != null) return false;
        }
        else if (!fileType.equals(other.fileType)) return false;
        if (fileVersion == null) {
            if (other.fileVersion != null) return false;
        }
        else if (!fileVersion.equals(other.fileVersion)) return false;
        if (finalFileName == null) {
            if (other.finalFileName != null) return false;
        }
        else if (!finalFileName.equals(other.finalFileName)) return false;
        if (isCopy != other.isCopy) return false;
        if (relativelyFilePath == null) {
            if (other.relativelyFilePath != null) return false;
        }
        else if (!relativelyFilePath.equals(other.relativelyFilePath)) return false;
        return true;
    }

    @Override
	public String toString() {
		return "JCVFileInfo [relativelyFilePath=" + relativelyFilePath + ", fileVersion=" + fileVersion + ", fileType="
		                + fileType + ", fileName=" + fileName + ", file=" + file + ", finalFileName=" + finalFileName
		                + ", isCopy=" + isCopy + ", fileHashKey=" + fileHashKey + "]";
	}

  
    
}
