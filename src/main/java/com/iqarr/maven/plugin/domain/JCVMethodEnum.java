package com.iqarr.maven.plugin.domain;

/**
 * @author
 *         zhangyong
 */
public enum JCVMethodEnum {
    
    /** 时间戳 **/
    TIMESTAMP_METHOD("timestamp",1),
    
    /** md5版本号 **/
    MD5_METHOD("md5",2),
    
    /** md5文件名 **/
    MD5FileName_METHOD("md5FileName",2),
    
    /** 未使用(内部标识) */
    DEFAULTS_UNUSED("defualts_unuser",3),
    
    ;
    
    private String method;
    
    private int    id;
    
    
    /* 
     * @param method
     * @param id
     */
    private JCVMethodEnum(String method, int id) {
        this.method = method;
        this.id = id;
    }
    
    /**
     * 获取
     * 
     * @return method
     */
    public String getMethod() {
        return method;
    }
    
    /**
     * 设置
     * 
     * @param method
     */
    public void setMethod(String method) {
        this.method = method;
    }
    
    /**
     * 获取
     * 
     * @return id
     */
    public int getId() {
        return id;
    }
    
    /**
     * 设置
     * 
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }
    
}
