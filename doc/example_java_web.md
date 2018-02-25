## <center>java web 示例

### 基本环境信息
1. 项目名称test-web
2. jdk 1.7+ (如果需要使用1.6自编安装本地)
3. maven 3.x.x 以上
4. `static/js/index.js` 文件完整目录`src/main/webapp/static/js/index.js`
5. `static/css/index.css` 文件完整目录`src/main/webapp/static/css/index.css`

### 一 、使用相对路径变项名称

1. 页面使用标签示例(${basePath} 表示项目名称动态传入)：
```html
<script  src="${basePath}static/js/index.js"></script>
<link type="text/css" rel="stylesheet" href="${basePath}static/css/index.css" />
```

2. 使用配置文件
```xml
<!--jcv-maven-plugin -->
<plugin>
  <groupId>com.iqarr.maven.plugin</groupId>
  <artifactId>jcv-maven-plugin</artifactId>
  <version>${last.released}</version>
  <executions>
     <execution>  
              <id>process</id>  
              <phase>process-resources</phase>  
              <goals>  
                  <goal>process</goal>                            
              </goals>  
          </execution>  
  </executions>
  <configuration>
    <!--如果项目名称使用EL表达式 那么必须加\ 转义 -->
    <jsConstantName>\${basePath}</jsConstantName>
    <cssConstantName>\${basePath}</cssConstantName>
    <suffixs>
      <param>html</param>
      <param>ftl</param>
    </suffixs>
   <!-- 清理html 页面注释-->
    <clearPageComment>true</clearPageComment>
    <globaJsMethod>MD5_METHOD</globaJsMethod>
    <globaCssMethod>MD5_METHOD</globaCssMethod>
    <!-- 压缩js-->
    <compressionJs>false</compressionJs>
    <!-- 压缩css-->
    <compressionCss>false</compressionCss>
  </configuration>
</plugin>
<!--maven-war-plugin -->
<plugin>
    <artifactId>maven-war-plugin</artifactId>
    <version>3.1.0</version>
    <configuration>
        <warSourceDirectory>src/main/webapp</warSourceDirectory>
        <warSourceExcludes>**/*.html,**/*.ftl</warSourceExcludes>
    </configuration>
</plugin>
```

### 二 、使用cdn或域名形式

1. 页面使用标签示例(注意：该形式使用了动静分离，但是处理js和cs必须在 `src/main/webapp`中)：
```html
<script  src="http://script.iqarr.com/static/js/index.js"></script>`
<link type="text/css" rel="stylesheet" href="http://style.iqarr.com/static/css/index.css" />
```

2. 使用配置文件
```xml
<!--jcv-maven-plugin -->
<plugin>
  <groupId>com.iqarr.maven.plugin</groupId>
  <artifactId>jcv-maven-plugin</artifactId>
  <version>${last.released}</version>
  <executions>
     <execution>  
              <id>process</id>  
              <phase>process-resources</phase>  
              <goals>  
                  <goal>process</goal>                            
              </goals>  
          </execution>  
  </executions>
  <configuration>
    <baseJsDomin>
         <!--允许多个 -->
				 <param>http://script.iqarr.com</param>
		</baseJsDomin>
		<baseCssDomin>
          <!--允许多个 -->
				 <param>http://style.iqarr.com</param>
		</baseCssDomin>
    <suffixs>
      <param>html</param>
      <param>ftl</param>
    </suffixs>
   <!-- 清理html 页面注释-->
    <clearPageComment>true</clearPageComment>
    <globaJsMethod>MD5_METHOD</globaJsMethod>
    <globaCssMethod>MD5_METHOD</globaCssMethod>
    <!-- 排除文件列表 或者使用 min 后缀 会自动排除-->
    <excludesJs>
    						<param>plugin/kindeditor/kindeditor.js</param>
    						<param>js/My97DatePicker/WdatePicker.js</param>
    						<param>js/My97DatePicker/config.js</param>
    						<param>js/My97DatePicker/calendar.js</param>
    </excludesJs>
    <!-- 排除文件列表 或者使用 min 后缀 会自动排除-->
    <excludesCss>
    						<param>plugin/kindeditor/themes/default/default.css</param>
    						<param>js/layer/layer/skin/layer.css</param>
    						<param>js/My97DatePicker/skin/WdatePicker.css</param>
    						<param>js/My97DatePicker/skin/whyGreen/datepicker.css</param>
    						<param>js/My97DatePicker/skin/default/datepicker.css</param>
    </excludesCss>
    <!-- 压缩js-->
    <compressionJs>false</compressionJs>
    <!-- 压缩css-->
    <compressionCss>false</compressionCss>
  </configuration>
</plugin>
<!--maven-war-plugin -->
<plugin>
    <artifactId>maven-war-plugin</artifactId>
    <version>3.1.0</version>
    <configuration>
        <warSourceDirectory>src/main/webapp</warSourceDirectory>
        <warSourceExcludes>**/*.html,**/*.ftl</warSourceExcludes>
    </configuration>
</plugin>
```
