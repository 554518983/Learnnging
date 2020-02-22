## 加载resurces下的文件内容
#### 1.@value("${xxx}")单独使用
#### 2.@ConfigurationProperties(prefix = "xxx")与@Value("${xxx}")配合使用
#### 3.@ConfigurationProperties(prefix = "xxx")、@Value("${xxx}")以及 @PropertySource(value = {"classpath:xxx.yaml"}, factory = YamlPropertyConfig.class)配合使用
- ConfigurationProperties默认只能加载properties结尾的文件
- 如果加载yml或者yaml结尾的文件则需要制定自定义的factory
