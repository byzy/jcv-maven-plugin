package com.iqarr.maven.plugin.utils;

import static com.iqarr.maven.plugin.utils.BaseUtils.checkNextStrIndex;
import static com.iqarr.maven.plugin.utils.BaseUtils.comparisonCharArray;
import static com.iqarr.maven.plugin.utils.BaseUtils.comparisonUpCharArray;

import java.io.IOException;

import com.iqarr.maven.plugin.domain.DocPosition;

/**  
* @author 
*		zhangyong        
*/
public class HtmlUtils {
    
    
    /**
     * 
     * 获取网页指定标签位置
     * @param sb
     * @param dp
     */
    public static void getHtmllabDocposition(final StringBuffer sb,DocPosition dp){
        char[] cas = sb.toString().toCharArray();
        int startLadlenth=dp.getStartLab().length();
        int endlabLenth=dp.getEndLad().length();
        dp.setStartPos(checkNextStrIndex(cas, dp.getIndexPos(),  dp.getStartLab()));
        
        if (dp.getStartPos() == -1) {
            dp.setFindIt(false);
            return ;
        }
       
        dp.setEndPos(checkNextStrIndex(cas, dp.getStartPos()+startLadlenth, dp.getEndLad()));
        
        if(dp.getEndPos()!=-1){
            dp.setEndPos(dp.getEndPos()+endlabLenth);
          
        }
        
        if(null!=dp.getCheckEndLad() && !"".equals(dp.getCheckEndLad())){
            int checkEnd=checkNextStrIndex(cas, dp.getStartPos()+startLadlenth, dp.getCheckEndLad());
            if(dp.getEndPos()==-1 || checkEnd==-1){
                dp.setFindIt(false);
                return ;
            }else   if(dp.getEndPos()>checkEnd ){
                dp.setFindIt(false);
                return ;
            }
           
        }
        dp.setFindIt(true);
        
    }
   
   
   
    
    /**
     * 
     *清理换行注释   /r /t /n
     * @param sb
     * @param chars
     */
    public static void cleanLineFeedComments(StringBuffer sb, final String chars){
        if(sb==null){
            return;
        }
        char[] charArray = sb.toString().toCharArray();
        int size=charArray.length;
        int index=0;
        int delete_count=0;
        int start=0;
        do {
            if(index>=size){
                return ;
            }
            if(charArray[index]=='\n'  || charArray[index]=='\t' || charArray[index]=='\r'){
                start=index-delete_count;
                
              //  sb.replace(start, start+1, " ");
                sb.delete(start, start+1);
                delete_count+=1;
            }else if(charArray[index]==' '&& charArray[index+1]==' ' ){
               
                start=index-delete_count;
                //sb.replace(index, index+1, " ");
                sb.delete(start, start+1);
                delete_count+=1;
            }
            
            index++;
            
            
        } while (true);
    }
    
    /**
     * 
     * 清理全部注释
     * @param sb
     * @param chars
     * @param isCssFile css文件
     * @throws IOException
     */
    @Deprecated
    public static void cleanBaseAllComments(StringBuffer sb, final String chars,final boolean isCssFile) throws IOException{
        char[] chas = sb.toString().toCharArray();
        int chasLenth=chas.length;
        int delete_count=0;
        int quotationMarksStart=-1; //引号
        boolean isInComment=false; //是否在注释中
        int comment1Start=-1; //注释开始 /*
        int comment2Start=-1; //注释开始 //
        int commentType=0; //注释类型　1 /*   ,2 //
        int st=0;
        int end=0;
        
       
        char startquotationMarks=' '; //检查结束的标记
        
        char[] flf = FileUtils.testFileLinefeed(sb.toString(), chars);
        int flflenth=flf.length;
        
        for (int i = 0; i < chasLenth; i++) {
         
             if(i+1<chasLenth && ((chas[i]=='/' && chas[i+1]=='*') )
                             && (  i==0 || (i-1>=0  && chas[i-1]!='\\' )     ) ) {
               if(!isInComment ){
                if(quotationMarksStart==-1 && commentType==0){
                    comment1Start=comment1Start==-1?i:-1;
                    commentType=1;
                    isInComment=true;
                    i++;
                }
                }
            }else  if(i+1<chasLenth && ( chas[i]=='/' && chas[i+1]=='/') 
                            && (  i==0 || (i-1>=0  && chas[i-1]!='\\' )  )) {
                if (!isInComment && !isCssFile) {
                        if (quotationMarksStart == -1 && commentType == 0) { // && commentType==2
                            comment2Start = comment2Start == -1 ? i : -1;
                            commentType = 2;
                            isInComment=true;
                            i++;
                        }
                    }
            }else if( (chas[i]=='\'' || chas[i]=='"')  ){ // ' "
                        if (!isInComment && !isCssFile) {
                            if (comment2Start == -1 || comment1Start ==-1 ) {
                                if(quotationMarksStart==-1 && startquotationMarks==' ' && ( checkUpStrIsMarkIgnoreSpaces(chas,i,flf)) ){  //检查上一个位置是不是 ( ,+ 忽略空格
                                    quotationMarksStart=i;
                                    startquotationMarks=chas[i];
                                }else  if(quotationMarksStart!=-1 && startquotationMarks==chas[i]   ){   //  checkUpStrIsMarkIgnoreSpaces(chas,i) && (  )
                                    
                                    if( i-2>=0  && chas[i-1]=='\\'){
                                        if(  chas[i-2]!='\\'){
                                            continue;
                                        }
                                    }
                         
                                    startquotationMarks=' ';
                                    quotationMarksStart=-1;
                                    
                                  
                                    
                                }
                            }
                        }
                
            }else if(i+1<chasLenth && ((chas[i]=='*' && chas[i+1]=='/')  ) 
                            && ( (chas[i+1]=='/') && (i-1>=0 && chas[i-1]!='\\' )) ) {  
                if(isInComment ){
                    if(quotationMarksStart==-1 && comment1Start!=-1 && commentType==1){
                     // 数组值换空格
                        for(int j=comment1Start;j<=i+1;j++){
                            chas[j]=' ';
                        }
                    
                    //发现注释
                    st=comment1Start-delete_count;
                    end=i+2-delete_count;
                    if(st>=end){
                        continue;
                    }
                    sb.delete(st, end);
                    delete_count+=end-st;
                    comment1Start=-1;
                    commentType=0;
                    isInComment=false;
                    i++;
                    }
                }
            }else if(comparisonCharArray(chas,i,flf)){   // '\n' 
                
                if(isInComment && !isCssFile){
                    if(quotationMarksStart==-1 && comment2Start!=-1 && commentType==2){
                        // 数组值换空格
                        for(int j=comment2Start;j<i;j++){
                            chas[j]=' ';
                        }
                        //发现注释
                        st=comment2Start-delete_count;
                        end=i+flflenth-delete_count;
                          
                        sb.delete(st, end);

                        delete_count+=end-st;
                        comment2Start=-1;
                        commentType=0;
                        isInComment=false;
                        i=i+flflenth-1;
                        
                        
                    }
                }
            } 
                            
        }
    }
   
    /**
     * 
     * 检查数组当前index上一个字符是否为开始字符(忽略空格)  ( + , = { : [ return  case 
     * @param cas
     * @param index
     * @return true  表示是+ (
     */
    public static boolean checkUpStrIsMarkIgnoreSpaces(final char[] cas, final int index ,final char[] flf){
       char[] retbyte = "return".toCharArray();
       char[] casebyte = "case".toCharArray();
        if(index==-1){
            return false;
        }
      
        for(int i=index-1;i>=0;i-- ){
            
            if(comparisonUpCharArray(cas,i,casebyte)){
                i-=casebyte.length-1;
                continue;
            }else  if(comparisonUpCharArray(cas,i,retbyte)){
                i-=retbyte.length-1;
                continue;
            }else  if(comparisonUpCharArray(cas,i,flf)){
                i-=flf.length-1;
                continue;
            }  else  if(cas[i]==' ' ||   cas[i]=='\t'){
                continue;
            }else if(cas[i]=='(' || cas[i]=='+' || cas[i]==','|| cas[i]=='=' ||cas[i]=='{' || cas[i]==':' | cas[i]=='['){
                return true;
            }else  if(cas[i]!=' ') {
                return false;
            }
        }
        return false;
    }
    
}
