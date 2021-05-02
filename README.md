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
- 打包时将项目和所依赖的jar分开，最终生成一个依赖文件夹（intellij中打包时选择copy to the output directory and link via manifest）
- 在需要使用该SDK的项目中创建lib文件夹（如果没有的话）
- 将打包出来的依赖文件夹拷贝到lib文件夹下
- 将该文件夹添加成项目的依赖库（intellij上为add as library）