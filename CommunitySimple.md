所用技术：

> `Thymeleaf    MyBatisPlus    Spring    SpringMVC    SpringBoot    Redis    RabbitMQ    ElasticSearch    SpringSecurity    SpringActuator`
>
> 附加：`Druid    Docker    Nginx    本地进程缓存    Nginx业务集群    Redis集群    RabbitMQ集群`

开发工具：

> `Maven    Git    IDEA    MySQL    Tomcat`

正式开发：

1. 引入依赖
2. 修改配置文件
3. 开发首页，数据库创建好对应的讨论帖子表`discuss_post` ---> `docker`安装数据库
4. 创建帖子实体类 ---> `pojo/DiscussPost`
5. 创建帖子映射接口 ---> `mapper/DiscussPostMapper`
6. 创建帖子服务类接口 ---> `service/DiscussPostService`

7. 创建帖子服务类接口实现类 ---> `service/impl/DiscussPostServiceImpl`
8. 创建帖子表现层类，实现分页查询 ---> `controller/HomeController`