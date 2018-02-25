package com.iqarr.maven.plugin.utils;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

/**  

* @author 
*		zhangyong         
*/
public class FileUtilsTest {
    
    
    /**
     * Test method for {@link com.iqarr.maven.plugin.utils.FileUtils#readToString(File, String)}.
     * @throws IOException 
     *  
     */
    @Test
    public void testReadToString() throws IOException{
        String[] readToString = FileUtils.readToString(new File(this.getClass().getClassLoader().getResource("test.html").getPath()) , "utf-8");
        
        assertNotNull(readToString);
       
        
    }
    
    /**
     * Test method for {@link com.iqarr.maven.plugin.utils.FileUtils#testFileLinefeed(java.io.File, java.lang.String)}.
     * @throws IOException 
     */
    @Test
    public void testTestFileLinefeed() throws IOException {
        char[] testFileLinefeed = FileUtils.testFileLinefeed(new File(this.getClass().getClassLoader().getResource("mac.txt").getPath()) , "utf-8");
        char [] test=new char[1];
        test[0]='\n';
        assertArrayEquals(test , testFileLinefeed);
    }
    
    /**
     * Test method for {@link com.iqarr.maven.plugin.utils.FileUtils#readToString(File, String)}.
     * @throws Exception 
     *  
     */
    @Test
    public void testClearBlankLines() throws Exception{
        String readToStr = FileUtils.readToStr(new File(this.getClass().getClassLoader().getResource("test.html").getPath()) , "utf-8");
        StringBuffer sb=new StringBuffer(readToStr);
        FileUtils.clearBlankLines(sb,  "utf-8");
        
        //System.out.println(sb.toString());
    }
    
}
