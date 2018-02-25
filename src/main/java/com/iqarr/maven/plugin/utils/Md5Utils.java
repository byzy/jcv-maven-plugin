package com.iqarr.maven.plugin.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;

/**  
* @author 
*		zhangyong       
*/
public class Md5Utils {

    /**
     * 
     * 计算文件md5
     * @param file
     * @return
     */
    public static String getFileMD5(File file) throws Exception {  
        if (!file.exists() || !file.isFile()) {  
            return null;  
        }  
        MessageDigest digest = null;  
        FileInputStream in = null;  
        byte buffer[] = new byte[1024];  
        int len;  
        try {  
            digest = MessageDigest.getInstance("MD5");  
            in = new FileInputStream(file);  
            while ((len = in.read(buffer, 0, 1024)) != -1) {  
                digest.update(buffer, 0, len);  
            }  
            
        } catch (IOException e) {  
            throw e;
        }finally{
        	if(null!=in){
        		in.close();
        	}
        }  
        BigInteger bigInt = new BigInteger(1, digest.digest());  
        return bigInt.toString(16);  
    }  
  
}
