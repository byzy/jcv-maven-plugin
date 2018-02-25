package com.iqarr.maven.plugin.domain;

/**
 * @author 		zhangyong   
 */
public class DocPosition {
    
    
    /** The start pos. */
    private  int startPos;
   
    /** The index pos. */
    private int indexPos;
    
    /** The end pos. */
    private int endPos;
    
    /** The start lab. */
    private String startLab;
    
    /**  检查标签. */
    private String checkEndLad;
    
    /** The end lad. */
    private String endLad;
    
    /**是否找到 **/
    private boolean findIt;

    /**
     * 获取  .
     *
     * @return startPos
     */
    public int getStartPos() {
        return startPos;
    }

    /**
     * 设置 .
     *
     * @param startPos the new start pos
     */
    public void setStartPos(int startPos) {
        this.startPos = startPos;
    }

    /**
     * 获取  .
     *
     * @return indexPos
     */
    public int getIndexPos() {
        return indexPos;
    }

    /**
     * 设置 .
     *
     * @param indexPos the new index pos
     */
    public void setIndexPos(int indexPos) {
        this.indexPos = indexPos;
    }

    /**
     * 获取  .
     *
     * @return endPos
     */
    public int getEndPos() {
        return endPos;
    }

    /**
     * 设置 .
     *
     * @param endPos the new end pos
     */
    public void setEndPos(int endPos) {
        this.endPos = endPos;
    }

    /**
     * 获取  .
     *
     * @return startLab
     */
    public String getStartLab() {
        return startLab;
    }

    /**
     * 设置 .
     *
     * @param startLab the new start lab
     */
    public void setStartLab(String startLab) {
        this.startLab = startLab;
    }

    /**
     * 获取 检查标签 .
     *
     * @return checkEndLad
     */
    public String getCheckEndLad() {
        return checkEndLad;
    }

    /**
     * 设置 检查标签.
     *
     * @param checkEndLad 检查标签
     */
    public void setCheckEndLad(String checkEndLad) {
        this.checkEndLad = checkEndLad;
    }

    /**
     * 获取  .
     *
     * @return endLad
     */
    public String getEndLad() {
        return endLad;
    }

    /**
     * 设置 .
     *
     * @param endLad the new end lad
     */
    public void setEndLad(String endLad) {
        this.endLad = endLad;
    }

    /**
     * 获取 是否找到 
     * @return findIt
     */
    public boolean isFindIt() {
        return findIt;
    }

    /**
     * 设置 是否找到
     * @param findIt 是否找到
     */
    public void setFindIt(boolean findIt) {
        this.findIt = findIt;
    }
    
    
    
    
}
