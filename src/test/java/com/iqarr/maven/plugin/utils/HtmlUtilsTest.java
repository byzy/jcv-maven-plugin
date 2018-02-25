package com.iqarr.maven.plugin.utils;

import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Test;

/**  
* @author 
*		zhangyong      
*/
public class HtmlUtilsTest {
    
    /**
     * Test method for {@link com.iqarr.maven.plugin.utils.HtmlUtils#htmlTolinks(java.lang.String)}.
     */
    @Test
    public void testHtmlTolinks() {
    
       
    }
    
    
  
    /**
     * Test method for {@link com.iqarr.maven.plugin.utils.HtmlUtils#cleanBase2Comments(StringBuffer, String)}.
     * @throws Exception 
     */
    @SuppressWarnings("unused")
	@Test
    public void testCleanBase2Comments() throws Exception {
        String readToStr = FileUtils.readToStr(new File(this.getClass().getClassLoader().getResource("demo.js").getPath()) , "utf-8");
        StringBuffer sb=new StringBuffer(readToStr);
       // HtmlUtils.cleanBase2Comments(sb, "utf-8");
       // int indexOf = sb.indexOf("//", 458);
        //System.out.println(sb.toString());
       // assertTrue(indexOf==-1);
    }
    /**
     * Test method for {@link com.iqarr.maven.plugin.utils.HtmlUtils#checkJsMarkClosed(char[], int, int, char)}.
     * @throws Exception 
     */
    @Test
    public void testCheckJsMarkClosed() throws Exception{
        String readToStr = FileUtils.readToStr(new File(this.getClass().getClassLoader().getResource("demo.js").getPath()) , "utf-8");
        StringBuffer sb=new StringBuffer(readToStr); 
        
       // boolean checkJsMarkClosed = HtmlUtils.checkJsMarkClosed(sb.toString().toCharArray(), 101, 190, '"');
       // assertTrue(checkJsMarkClosed);
    }
    
    /**
     * Test method for {@link com.iqarr.maven.plugin.utils.HtmlUtils#cleanLineFeedComments(StringBuffer, String)}.
     * @throws Exception 
     */
    @Test
    public void testCleanLineFeedComments() throws Exception{
        String readToStr = FileUtils.readToStr(new File(this.getClass().getClassLoader().getResource("demo.js").getPath()) , "utf-8");
        StringBuffer sb=new StringBuffer(readToStr); 
        
        HtmlUtils.cleanLineFeedComments(sb, "utf-8");
        int indexOf = sb.indexOf("   ");
        assertTrue(indexOf==-1);
       // System.out.println(sb.toString());
    }
    
    @Test
    public void testCompression() throws Exception{
        String readToStr = FileUtils.readToStr(new File(this.getClass().getClassLoader().getResource("demo.js").getPath()) , "utf-8");
        StringBuffer sb=new StringBuffer(readToStr);
      //  HtmlUtils.cleanBaseComments(sb, "utf-8");
       // HtmlUtils.cleanBase2Comments(sb, "utf-8");
         HtmlUtils.cleanLineFeedComments(sb, "utf-8");
         int indexOf = sb.indexOf("/r");
         assertTrue(indexOf==-1);
       // System.out.println(sb.toString());
    }
    
    @Test
    public void testcleanBaseAllComments() throws Exception{
       String readToStr = FileUtils.readToStr(new File(this.getClass().getClassLoader().getResource("demo.js").getPath()) , "utf-8");;
        StringBuffer sb=new StringBuffer(readToStr);
         HtmlUtils.cleanBaseAllComments(sb, "utf-8",false);
        HtmlUtils.cleanLineFeedComments(sb, "utf-8");
       // int indexOf = sb.indexOf("/*");
       // assertTrue(indexOf==-1);
    }
   
    
}
