# 基础介绍
smart-doc依赖java-doc生成接口文档
支持spring、dubbo等框架。

目前自测的结果，post的@RequestBody能够解析，
return的vo，哪怕是有泛型，也能很好的解析。

泛型部分，说明smart-doc不光依赖java-doc，还会解析源码。

使用上直接用命令可以，在IDEA/maven里找到当前项目/模块，Plugins/smart-doc/smart-doc:html点一下也可以。
可以使用
mvn -Dfile.encoding=UTF-8 smart-doc:markdown
mvn -Dfile.encoding=UTF-8 smart-doc:html

其他更多用法，参考官网，文档很详细。

# 工程应用

开发者不用在YAPI写一遍接口文档，再去controller以及对应reqVO类、resVO类里再写一遍。
直接用smart-doc即可通过java-doc直接生成html/md格式的接口文档。
但是，问题在于，缺一个平台/服务，因为你不能把md文件直接发给前端把。

所以有了torna。
smart-doc配置下torna的相关内容，生成的接口文档就会给到torna.

这部分未实际测试，因为需要在本地搭torna服务。

# 参考

[torna官网](https://www.torna.cn/)
[torna对接smart-doc](https://torna.cn/dev/smart-doc.html)

[smart-doc官网-1](https://github.com/TongchengOpenSource/smart-doc-maven-plugin)
[smart-doc-官网-2](https://smart-doc-group.github.io/zh/guide/plugins/maven)
[pdai博客](https://www.cnblogs.com/pengdai/p/16480004.html)


