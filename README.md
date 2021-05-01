# 打包成jar
- 在guid文件夹中运行`mvn package`，会在`/guid/target/`目录下生成`guid-1.0-SNAPSHOT.jar`

# restful部署方法
- `java -jar guid-1.0-SNAPSHOT.jar`
- 如果想修改运行时变量，可以将`guid/src/main/resources/`中的application.properties拷贝到jar同目录，然后修改里面的值。
- parse demo: `152.136.134.100:1026/parse?prefixCheckID=UniTimDev1f7930000000900000179029edc2a01`
- generate demo: `152.136.134.100:1026/generate`，请求的body为json字符串：
    ```JSON
    {
	"prefix":"UNITIM1",
	"content":[
		{"type":"timeInfo"}
	]
    }
    ```

# SDK使用
    