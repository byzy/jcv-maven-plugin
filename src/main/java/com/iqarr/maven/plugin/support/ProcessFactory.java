package com.iqarr.maven.plugin.support;

/**
 * @author
 *         zhangyong
 */
public interface ProcessFactory {
	
	public void initDisplayInfo();
	
	/**
	 * 
	 * init
	 * @param webAppRoot　source path
	 */
	public void initJcv(final String webAppRoot);
	
	/**
	 * 
	 * 处理页面
	 * 
	 * @param pages
	 */
	public void doProcessPageFile();
	
	/**
	 * 显示成功信息
	 */
	public void displaySuccessInfo();
	
}
