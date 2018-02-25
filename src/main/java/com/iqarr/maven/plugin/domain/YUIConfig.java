package com.iqarr.maven.plugin.domain;

/**  
 * yui全局配置文件
* @author 
*		zhangyong       
*/
public class YUIConfig {
    /** 只压缩, 不对局部变量进行混淆。**/
    private boolean nomunge=true ;
    
    /** 保留所有的分号**/
    private boolean preserveSemi=true;
    
    /** 禁止优化**/
    private boolean disableOptimizations =false;
    
    /**显示详细信息 **/
    private boolean  verbose=false;

    /**
     * 获取 只压缩 不对局部变量进行混淆。 
     * @return nomunge
     */
    public boolean isNomunge() {
        return nomunge;
    }

    /**
     * 设置 只压缩 不对局部变量进行混淆。
     * @param nomunge 只压缩 不对局部变量进行混淆。
     */
    public void setNomunge(boolean nomunge) {
        this.nomunge = nomunge;
    }

   
    /**
     * 获取 保留所有的分号 
     * @return preserveSemi
     */
    public boolean isPreserveSemi() {
        return preserveSemi;
    }

    /**
     * 设置 保留所有的分号
     * @param preserveSemi 保留所有的分号
     */
    public void setPreserveSemi(boolean preserveSemi) {
        this.preserveSemi = preserveSemi;
    }

    /**
     * 获取 禁止优化 
     * @return disableOptimizations
     */
    public boolean isDisableOptimizations() {
        return disableOptimizations;
    }

    /**
     * 设置 禁止优化
     * @param disableOptimizations 禁止优化
     */
    public void setDisableOptimizations(boolean disableOptimizations) {
        this.disableOptimizations = disableOptimizations;
    }

    /**
     * 获取 显示详细信息 
     * @return verbose
     */
    public boolean isVerbose() {
        return verbose;
    }

    /**
     * 设置 显示详细信息
     * @param verbose 显示详细信息
     */
    public void setVerbose(boolean verbose) {
        this.verbose = verbose;
    }
    
    
}
