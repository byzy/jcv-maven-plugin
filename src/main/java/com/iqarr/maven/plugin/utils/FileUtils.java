package com.iqarr.maven.plugin.utils;

import static com.iqarr.maven.plugin.utils.BaseUtils.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.List;

/**
 * @author
 *         zhangyong
 */
public class FileUtils {
    
    /**
     * 
     * 遍历查找目标目录了中指定类型的文件，并保存在集合中
     * 
     * @param collected
     *            指定类型文件集合
     * @param file
     *            目标目录
     * @param includes
     *            文件后缀,不包含点
     */
    public static void collectFiles(List<File> collected, File file, List<String> includes) {
        if (file.isFile()) {
            // 如果是文件，则判断是否为指定类型
            
            for (String include : includes) {
                if (file.getName().endsWith("." + include)) {
                    collected.add(file);
                    break;
                }
            }
        } else {
            // 如果是目录，则遍历子文件或者子目录，递归查找
        	if(null==file.listFiles ()){
        		return;
        	}
            for (File sub : file.listFiles()) {
                collectFiles(collected, sub, includes);
            }
        }
    }
    
    /**
     * 
     * 读取file文件，将文件中的数据按照行读取到String数组中
     * 
     * 
     * @param file
     * @param sourceEncoding
     * @return
     * @throws IOException
     */
    public static String[] readToString(final File file, final String sourceEncoding) throws IOException {
        
        Long filelength = file.length();
        byte[] filecontent = new byte[filelength.intValue()];
        InputStream in = null;
        String[] fileContentArr = null;
        try {
            in = new FileInputStream(file);
            in.read(filecontent);
            String string = new String(filecontent, Charset.forName(sourceEncoding));
            char[] lfc = testFileLinefeed(string, sourceEncoding);
            String lf = "";
            if (lfc==null ||lfc.length == 0) {
                fileContentArr = new String[1];
                fileContentArr[0] = string;
                return fileContentArr;
            } else
                if (lfc.length == 1) {
                    lf += lfc[0];
                } else {
                    lf += lfc[0];
                    lf += lfc[1];
                }
            
            fileContentArr = string.split(lf);
        } catch (Exception e) {
            throw e;
        } finally {
            if (in != null) {
                in.close();
            }
        }
        
        return fileContentArr;
    }
    
    /**
     * 
     * 读取文件
     * 
     * @param file
     * @param sourceEncoding
     * @return
     * @throws Exception
     */
    public static String readToStr(final File file, final String sourceEncoding) throws Exception {
        Long filelength = file.length();
        byte[] filecontent = new byte[filelength.intValue()];
        InputStream in = null;
        String string = "";
        try {
            in = new FileInputStream(file);
            in.read(filecontent);
            string = new String(filecontent, Charset.forName(sourceEncoding));
            
        } catch (Exception e) {
            throw e;
        } finally {
            if (in != null) {
                in.close();
            }
        }
        return string;
    }
    
    /**
     * 
     * 写文件
     * 
     * @param file
     * @param sourceEncoding
     *            文件编码
     * @param strs
     * @throws IOException
     */
    public static void writeFile(final File file, final String sourceEncoding, final List<String> strs) throws IOException {
        
        OutputStream out = null;
        
        try {
            out = new FileOutputStream(file, false);
            for (String s : strs) {
                if (strs != null && !"".equals(s) && s.length() > 0) {   // && !"\r\n".equals(s)
                    out.write(s.getBytes(Charset.forName(sourceEncoding)));
                    out.write(getSystemLineSpearator().getBytes(Charset.forName(sourceEncoding)));
                }
            }
            
        } catch (IOException e) {
            throw e;
        } finally {
            if (out != null) {
                out.flush();
                out.close();
            }
            
        }
        
    }
    
    /**
     *    获取系统文件分割符
     * @return
     */
    public static String getSystemFileSeparator() {
        
        return System.getProperties().getProperty("file.separator");
    }
    
    /**
     * 
     * 获取路径是否linux
     * 
     * @return
     */
    public static boolean getSystemFileSeparatorIslinux() {
        String property = System.getProperties().getProperty("file.separator");
        if ("/".equals(property)) {
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * 
     * 文件复制
     * 
     * @param source
     * @param to
     * @throws IOException
     */
    public static void fileChannelCopy(final File source, final File to) throws IOException {
        FileInputStream fi = null;
        FileOutputStream fo = null;
        FileChannel in = null;
        FileChannel out = null;
        try {
            fi = new FileInputStream(source);
            fo = new FileOutputStream(to);
            in = fi.getChannel();
            out = fo.getChannel();
            in.transferTo(0, in.size(), out);
        } catch (IOException e) {
            throw e;
        } finally {
            try {
                fi.close();
                in.close();
                fo.close();
                out.close();
            } catch (IOException e) {
                throw e;
            }
        }
    }
    
    /**
     * 
     * 测试文件的换行符号
     * 
     * @param source
     * @return
     * @throws IOException
     */
    public static char[] testFileLinefeed(final File file, final String sourceEncoding) throws IOException {
       
        if (file == null) {
            throw new IOException();
        }
        
        Long filelength = file.length();
        byte[] filecontent = new byte[filelength.intValue()];
        InputStream in = null;
        try {
            in = new FileInputStream(file);
            in.read(filecontent);
            String str = new String(filecontent, Charset.forName(sourceEncoding));
           return testFileLinefeed(str, sourceEncoding);
        } catch (Exception e) {
            throw e;
        } finally {
            if (in != null) {
                in.close();
            }
        }
        
       
    }
    /**
     * 
     * 测试文件换行符号
     * @param str
     * @param sourceEncoding
     * @return
     * @throws IOException
     */
    public static char[] testFileLinefeed(final String str, final String sourceEncoding) throws IOException {
        char[] ret = null;
       if(null==str || str.length()==0){
           return null;
       }
        try {
            char[] charArray = str.toCharArray();
            int charslenth = charArray.length;
            char lf = '\r';
            int index = 0;
           
            
            /*
             * /r Mac
             * /r/n Windows
             * /n Unix/Linux
             */
            
           index = checkNextCharIndex(charArray, index, '\n');
            if (index != -1) {
                if ((index - 1) >= 0 && charArray[index - 1] == '\r') {
                    // windows
                    ret = new char[2];
                    ret[0] = '\r';
                    ret[1] = '\n';
                    return ret;
                } else {
                    
                    // linux
                    ret = new char[1];
                    ret[0] = '\n';
                    return ret;
                }
            }else {
                index=0;
                index = checkNextCharIndex(charArray, index, lf);
                if (index != -1) {
                    if (charslenth > (index+1)) {

                        if (charArray[index + 1] == '\n') {
                            //windows
                            ret = new char[2];
                            ret[0] = '\r';
                            ret[1] = '\n';
                            return ret;
                        } else {
                            // mac
                            ret = new char[1];
                            ret[0] = '\r';
                            return ret;
                        }
                        
                    } else {
                      //not find /r   return default linux
                        ret = new char[1];
                        ret[0] = '\n';
                        return ret;
                    }
                }else {
                    //not find /r   return default linux
                    ret = new char[1];
                    ret[0] = '\n';
                    return ret;
                }
            }
            
            
        } catch (Exception e) {
            throw e;
        }
        
    }
    
  
    
    /**
     * 
     * 系统换行符号
     * 
     * @return
     */
    public static String getSystemLineSpearator() {
        return System.getProperty("line.separator");
    }
    

    /**
     * 
     * 清除多余换行
     * @param sb
     * @throws IOException 
     */
    public static  void clearBlankLines(StringBuffer sb,final String sourceEncoding) throws IOException{
        if(sb==null || sb.length()==0){
            return ;
        }
        char[] chas = sb.toString().toCharArray();
        int chasLenth=chas.length;

        char[] testFileLinefeed = testFileLinefeed(sb.toString(),sourceEncoding);
        int testLF=testFileLinefeed.length;
     
      
        int delete_count=0;
        int st=0;
        int end=0;
       for(int index=0;index<chasLenth; index+=testLF){
             index = checkNextCharIndex(chas,index,testFileLinefeed);
            if(index==-1 ){
                return ;
            }
           int  index2 = checkNextCharIndex(chas,index+testLF,testFileLinefeed);
           if(index2==-1 ){
               return ;
           }
            if(index+testLF==index2){
                st=index-delete_count;
                end=index+testLF-delete_count;
                sb.delete(st, end);
                delete_count+=end-st;
                
            }else {
                
            }
          
            
            
        } 
    }
    
}
