## <center>spring boot  示例

### 基本环境信息
1. 项目名称test-web
2. jdk 1.7+ (如果需要使用1.6自编安装本地)
3. maven 3.x.x 以上
4. `/static/js/index.js` 文件完整目录`src/main/resources/static/js/index.js`
5. `/static/css/index.css` 文件完整目录`src/main/resources/static/css/index.css`

1. 页面使用标签示例：
```html
<script  src="/static/js/index.js"></script>
<link type="text/css" rel="stylesheet" href="/static/css/index.css" />
```

2. 使用配置文件

```xml
<!-- jcv-maven-plugin -->
<plugin>
  <groupId>com.iqarr.maven.plugin</groupId>
    <artifactId>jcv-maven-plugin</artifactId>
      <version>${last.released}</version>
        <executions>
				   <execution>  
              <id>process</id>  
              <phase>process-resources</phase>  
              <goals>  
                  <goal>process-springboot</goal>                            
              </goals>  
            </execution>  
				</executions>
		<configuration>
			<suffixs>
						<param>html</param>
			</suffixs>
			<clearPageComment>true</clearPageComment>
			<globaJsMethod>MD5_METHOD</globaJsMethod>
			<globaCssMethod>MD5_METHOD</globaCssMethod>
			<compressionJs>true</compressionJs>
		  <compressionCss>true</compressionCss>
		</configuration>
</plugin>
```
