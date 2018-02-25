
## <center>jcv-maven-plugin



***

###  参数说明：

1. `outputDirectory`
 * 输出文件目录
 * 默认`${project.build.directory}`

2. `webappDirectory`
 * webapp目录
 * 默认值:`${basedir}/src/main/webapp`

3. `suffixs`
 * 检查文件的后缀
 * 默认`jsp`
 * 参数：

 ```xml
 <suffixs>
	<param>html</param>
	<param>jsp</param>
</suffixs>
 ```

4. `baseJsDomin`
 * 基本js域名,在使用`<script src="http://script.iqarr.com/js/jquery/jquery/1.8.3/jquery.js"></script>`这种方式需要配置
 * 参数:
```xml
  <baseJsDomin>
	<param>http://script.iqarr.com</param>
  </baseJsDomin>
```
5. `baseCssDomin`
 * 基本css域名,在使用`<link rel="stylesheet" type="text/css" href="http://style.iqarr.com/css/public.css?" />`这种方式需要配置
 * 参数:
```xml
   <baseCssDomin>
	<param>http://style.iqarr.com</param>
  </baseCssDomin>
```
6. `globaJslPrefixPath`
 * 全局js path路径
7. `globaCsslPrefixPath`
 * 全局css path路径
8. `globaJsMethod`
 * 全局js版本号使用方法(单个参数)
 * `TIMESTAMP_METHOD`
   > 时间戳方式:　该方式生成为打包时间的时间戳(不建议使用),会在最后添加?`versionLable=`值

 * `MD5_METHOD`
   > md5做为版本号:会在最后添加?`versionLable=`md5值

 * `MD5FileName_METHOD`
  >  md5文件名方式：该方式会将js文件替换为该文件的md5

9. `globaCssMethod`
 * 参考globaJsMethod方式

10. `webRootName`
 * 最终项目名称
 * 默认值:`${project.build.finalName}`

11. `versionLable`
	* 版本号标签:
	* 默认:`version`
12. `sourceEncoding`
	* 文件编码
	* 默认:`UTF-8`

13. `clearPageComment`
	* 清除页面注释，该注释为html `<!-- -->`标准注释的清除
	* 默认:`false`

14. `outJSCSSDirPath`
	* 在使用md5文件名时候使用数据全修改文件后的文件目录(不配置就默认替换当前的文件位置)
15. `compressionCss`

	* 清除css注释，并清理换行
	* 默认:`false`

16. `compressionJs`

	* 清除css注释，并清理换行
	* 默认:`false`

17. `userCompressionSuffix`

	* 压缩文件后缀
	* 默认: min

18. `excludesJs`

	* 排除js文件(只支持全路径匹配)
```xml
   <excludesJs>
	<param>js/dome.js</param>
  </excludesJs>
```

19. `excludesCss`

	* 排除css文件(只支持全路径匹配)
```xml
   <excludesCss>
	<param>css/dome.css</param>
   </excludesCss>
```

20. `yuiConfig`

	* 配置压缩选项
```xml
	<yuiConfig>
		  <!-- 禁止优化(默认false) -->
		  <disableOptimizations></disableOptimizations>
		 <!-- 只压缩, 不对局部变量进行混淆(默认true) -->
		 <nomunge></nomunge>
		 <!-- 保留所有的分号(默认true) -->
		<preserveSemi></preserveSemi>
		<!-- 显示详细信息(默认false) -->
		<verbose></verbose>
	</yuiConfig>
```

21. `skipFileNameSuffix`

	* 跳过文件名后缀(后缀之前的名称)，例如：　ok.min.js 如果想跳过就需要配置`.min`
	* 默认:`.min`
22. `jsPhysicalRootPath`

  * js物理路径目录，该目录是指js路径的root
23. `cssPhysicalRootPath`

  * cssPhysicalRootPath
24. `jsConstantName`

  * js常量名称 `jsConstantName/jspath`
25. `cssConstantName`

  * css 常量名称  `cssConstantName/csspath`
26. webAppRoot

  * app root目录
27. `jsConstantAliasPath`

  * js(注意该目录不是全路径，该路径是指在outJssCssPath+this) 常量对应的输出目录
28. `cssConstantAliasPath`

  * css (注意该目录不是全路径，该路径是指在outJssCssPath+this) 常量输出目录
  
29. `versionValLenth`

  * 版本号长度，该参数会截取版本号从第一位开始指定长度
