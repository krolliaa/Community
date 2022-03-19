# 讨论社区项目

## 一、学习目标

1. 学会主流的`Web`开发技术和框架
2. 积累一个真实的`Web`项目的开发经验
3. 掌握热点面试题的答题策略

## 二、技术选型

> `SpringBoot`
>
> `Spring、SpringMVC、MyBatis`
>
> `Redis、Kafka、ElasticSearch`
>
> `Spring Security、Spring Actuator`

## 三、开发环境

- 构建工具：`Apache Maven`
- 集成开发工具：`IntelliJ IDEA`
- 数据库：`MySQL Redis`
- 应用服务器：`Apache Tomcat`
- 版本控制工具：`Git`

## 四、安装`Apache Maven`+使用`SpringBoot`

- 为了加快访问速度可以使用镜像仓库 ---> 阿里的镜像仓库
- 配置环境变量 ---> `mvn --version`
- `IDEA`开局设置`Build Tools ---> Maven`
- 使用`https://start.spring.io`创建`SpringBoot`项目

## 五、正式开发

### 5.1 开发社区首页

- 开发社区首页，显示前`10`个帖子
- 分页组件，分页显示所有的帖子

#### 5.1.1 帖子

1. 创建帖子表：`discuss_post`，帖子表的字段有：

   ```
   主键id + 用户user_id + 帖子标题 +  帖子内容
   帖子类型 + 帖子状态 + 帖子评论数量 + 帖子发表时间 + 帖子分数
   ```
   
   |      字段       |    类型     |                备注                 |
   | :-------------: | :---------: | :---------------------------------: |
   |      `id`       |    `int`    |             主键、自增              |
   |    `user_id`    |    `int`    |      发帖的用户`id`，创建索引       |
   |     `title`     |  `varchar`  |             帖子表标题              |
   |    `content`    |   `text`    |              帖子内容               |
   |     `type`      |    `int`    |     帖子类型：`0`普通、`1`置顶      |
   | `comment_count` |    `int`    |              评论数量               |
   |    `status`     |    `int`    | 帖子状态：`0`普通、`1`精华、`2`拉黑 |
   |  `create_time`  | `timestamp` |            评论发表时间             |
   |     `score`     |  `double`   |           分数，用于排名            |
   
   `SQL`语句如下：
   
   ```sql
   DROP TABLE IF EXISTS `discuss_post`;
   CREATE TABLE `discuss_post` (
     `id` int(11) NOT NULL AUTO_INCREMENT,
     `user_id` varchar(45) DEFAULT NULL,
     `title` varchar(100) DEFAULT NULL,
     `content` text,
     `type` int(11) DEFAULT NULL COMMENT '0-普通; 1-置顶;',
     `status` int(11) DEFAULT NULL COMMENT '0-正常; 1-精华; 2-拉黑;',
     `create_time` timestamp NULL DEFAULT NULL,
     `comment_count` int(11) DEFAULT NULL,
     `score` double DEFAULT NULL,
     PRIMARY KEY (`id`),
     KEY `index_user_id` (`user_id`)
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
   
   INSERT INTO `discuss_post` VALUES (109,'101','震惊！一小孩竟然做出这种动作！','你被骗了，这是个假的内容，哈哈哈哈哈！',0,0,'2019-04-04 03:53:36',0,0),(110,'101','震惊！一小孩竟然做出这种动作！','你被骗了，这是个假的内容，哈哈哈哈哈！',0,0,'2019-04-04 03:53:36',0,0),(111,'101','震惊！一小孩竟然做出这种动作！','你被骗了，这是个假的内容，哈哈哈哈哈！',0,0,'2019-04-04 03:53:36',0,0),(112,'101','震惊！一小孩竟然做出这种动作！','你被骗了，这是个假的内容，哈哈哈哈哈！',0,0,'2019-04-04 03:53:36',0,0),(113,'101','震惊！一小孩竟然做出这种动作！','你被骗了，这是个假的内容，哈哈哈哈哈！',0,0,'2019-04-04 03:53:36',0,0),(114,'101','震惊！一小孩竟然做出这种动作！','你被骗了，这是个假的内容，哈哈哈哈哈！',0,0,'2019-04-04 03:53:36',0,0),(115,'101','震惊！一小孩竟然做出这种动作！','你被骗了，这是个假的内容，哈哈哈哈哈！',0,0,'2019-04-04 03:53:36',0,0),(116,'101','震惊！一小孩竟然做出这种动作！','你被骗了，这是个假的内容，哈哈哈哈哈！',0,0,'2019-04-04 03:53:36',0,0),(117,'101','震惊！一小孩竟然做出这种动作！','你被骗了，这是个假的内容，哈哈哈哈哈！',0,0,'2019-04-04 03:53:36',0,0),(118,'101','震惊！一小孩竟然做出这种动作！','你被骗了，这是个假的内容，哈哈哈哈哈！',0,0,'2019-04-04 03:53:36',0,0),(119,'101','震惊！一小孩竟然做出这种动作！','你被骗了，这是个假的内容，哈哈哈哈哈！',0,0,'2019-04-04 03:53:36',0,0),(120,'101','震惊！一小孩竟然做出这种动作！','你被骗了，这是个假的内容，哈哈哈哈哈！',0,0,'2019-04-04 03:53:36',0,0),(121,'102','震惊！一小孩竟然做出这种动作！','你被骗了，这是个假的内容，哈哈哈哈哈！',0,0,'2019-04-04 03:53:36',0,0),(122,'102','震惊！一小孩竟然做出这种动作！','你被骗了，这是个假的内容，哈哈哈哈哈！',0,0,'2019-04-04 03:53:36',0,0),(123,'102','震惊！一小孩竟然做出这种动作！','你被骗了，这是个假的内容，哈哈哈哈哈！',0,0,'2019-04-04 03:53:36',0,0),(124,'102','震惊！一小孩竟然做出这种动作！','你被骗了，这是个假的内容，哈哈哈哈哈！',0,0,'2019-04-04 03:53:36',0,0),(125,'103','震惊！一小孩竟然做出这种动作！','你被骗了，这是个假的内容，哈哈哈哈哈！',0,0,'2019-04-04 03:53:36',0,0),(126,'103','震惊！一小孩竟然做出这种动作！','你被骗了，这是个假的内容，哈哈哈哈哈！',0,0,'2019-04-04 03:53:36',0,0),(127,'103','震惊！一小孩竟然做出这种动作！','你被骗了，这是个假的内容，哈哈哈哈哈！',0,0,'2019-04-04 03:53:36',0,0),(128,'103','震惊！一小孩竟然做出这种动作！','你被骗了，这是个假的内容，哈哈哈哈哈！',0,0,'2019-04-04 03:53:36',0,0),(129,'103','震惊！一小孩竟然做出这种动作！','你被骗了，这是个假的内容，哈哈哈哈哈！',0,0,'2019-04-04 03:53:36',0,0),(130,'103','震惊！一小孩竟然做出这种动作！','你被骗了，这是个假的内容，哈哈哈哈哈！',0,0,'2019-04-04 03:53:36',0,0),(131,'103','震惊！一小孩竟然做出这种动作！','你被骗了，这是个假的内容，哈哈哈哈哈！',0,0,'2019-04-04 03:53:36',0,0),(132,'103','震惊！一小孩竟然做出这种动作！','你被骗了，这是个假的内容，哈哈哈哈哈！',0,0,'2019-04-04 03:53:36',0,0),(133,'103','震惊！一小孩竟然做出这种动作！','你被骗了，这是个假的内容，哈哈哈哈哈！',0,0,'2019-04-04 03:53:36',0,0),(134,'103','互联网求职暖春计划','你被骗了，这是个假的内容，哈哈哈哈哈！',0,0,'2019-04-04 03:53:36',0,0),(135,'103','互联网求职暖春计划','你被骗了，这是个假的内容，哈哈哈哈哈！',0,0,'2019-04-04 03:53:36',0,0),(136,'103','互联网求职暖春计划','你被骗了，这是个假的内容，哈哈哈哈哈！',0,0,'2019-04-04 03:53:36',0,0),(137,'103','互联网求职暖春计划','你被骗了，这是个假的内容，哈哈哈哈哈！',0,0,'2019-04-04 03:53:36',0,0),(138,'103','互联网求职暖春计划','你被骗了，这是个假的内容，哈哈哈哈哈！',0,0,'2019-04-04 03:53:36',0,0),(139,'103','互联网求职暖春计划','你被骗了，这是个假的内容，哈哈哈哈哈！',0,0,'2019-04-04 03:53:36',0,0),(140,'103','互联网求职暖春计划','你被骗了，这是个假的内容，哈哈哈哈哈！',0,0,'2019-04-04 03:53:36',0,0),(141,'103','互联网求职暖春计划','你被骗了，这是个假的内容，哈哈哈哈哈！',0,0,'2019-04-04 03:53:36',0,0),(142,'103','互联网求职暖春计划','你被骗了，这是个假的内容，哈哈哈哈哈！',0,0,'2019-04-04 03:53:36',0,0),(143,'103','互联网求职暖春计划','你被骗了，这是个假的内容，哈哈哈哈哈！',0,0,'2019-04-04 03:53:36',0,0),(144,'103','互联网求职暖春计划','你被骗了，这是个假的内容，哈哈哈哈哈！',0,0,'2019-04-04 03:53:36',0,0),(145,'103','互联网求职暖春计划','你被骗了，这是个假的内容，哈哈哈哈哈！',0,0,'2019-04-04 03:53:36',0,0),(146,'103','互联网求职暖春计划','你被骗了，这是个假的内容，哈哈哈哈哈！',0,0,'2019-04-04 03:53:36',0,0),(147,'103','互联网求职暖春计划','你被骗了，这是个假的内容，哈哈哈哈哈！',0,0,'2019-04-04 03:53:36',0,0),(148,'103','互联网求职暖春计划','你被骗了，这是个假的内容，哈哈哈哈哈！',0,0,'2019-04-04 03:53:36',0,0),(149,'103','互联网求职暖春计划','你被骗了，这是个假的内容，哈哈哈哈哈！',0,0,'2019-04-04 03:53:36',0,0),(150,'103','互联网求职暖春计划','你被骗了，这是个假的内容，哈哈哈哈哈！',0,0,'2019-04-04 03:53:36',0,0),(151,'103','互联网求职暖春计划','你被骗了，这是个假的内容，哈哈哈哈哈！',0,0,'2019-04-04 03:53:36',0,0),(152,'103','互联网求职暖春计划','你被骗了，这是个假的内容，哈哈哈哈哈！',0,0,'2019-04-04 03:53:36',0,0),(153,'103','互联网求职暖春计划','你被骗了，这是个假的内容，哈哈哈哈哈！',0,0,'2019-04-04 03:53:36',0,0),(154,'103','互联网求职暖春计划','你被骗了，这是个假的内容，哈哈哈哈哈！',0,0,'2019-04-04 03:53:36',0,0),(155,'103','互联网求职暖春计划','你被骗了，这是个假的内容，哈哈哈哈哈！',0,0,'2019-04-04 03:53:36',0,0),(156,'103','互联网求职暖春计划','你被骗了，这是个假的内容，哈哈哈哈哈！',0,0,'2019-04-04 03:53:36',0,0),(157,'103','互联网求职暖春计划','你被骗了，这是个假的内容，哈哈哈哈哈！',0,0,'2019-04-04 03:53:36',0,0),(158,'103','互联网求职暖春计划','你被骗了，这是个假的内容，哈哈哈哈哈！',0,0,'2019-04-04 03:53:36',0,0),(159,'103','互联网求职暖春计划','你被骗了，这是个假的内容，哈哈哈哈哈！',0,0,'2019-04-04 03:53:36',0,0),(160,'103','互联网求职暖春计划','你被骗了，这是个假的内容，哈哈哈哈哈！',0,0,'2019-04-04 03:53:36',0,0),(161,'103','互联网求职暖春计划','你被骗了，这是个假的内容，哈哈哈哈哈！',0,0,'2019-04-04 03:53:36',0,0),(162,'103','互联网求职暖春计划','你被骗了，这是个假的内容，哈哈哈哈哈！',0,0,'2019-04-04 03:53:36',0,0),(163,'103','互联网求职暖春计划','你被骗了，这是个假的内容，哈哈哈哈哈！',0,0,'2019-04-04 03:53:36',0,0),(164,'103','互联网求职暖春计划','你被骗了，这是个假的内容，哈哈哈哈哈！',0,0,'2019-04-04 03:53:36',0,0),(165,'103','互联网求职暖春计划','你被骗了，这是个假的内容，哈哈哈哈哈！',0,0,'2019-04-04 03:53:36',0,0),(166,'103','互联网求职暖春计划','你被骗了，这是个假的内容，哈哈哈哈哈！',0,0,'2019-04-04 03:53:36',0,0),(167,'103','互联网求职暖春计划','你被骗了，这是个假的内容，哈哈哈哈哈！',0,0,'2019-04-04 03:53:36',0,0),(168,'103','互联网求职暖春计划','你被骗了，这是个假的内容，哈哈哈哈哈！',0,0,'2019-04-04 03:53:36',0,0),(169,'103','互联网求职暖春计划','你被骗了，这是个假的内容，哈哈哈哈哈！',0,0,'2019-04-04 03:53:36',0,0),(170,'103','互联网求职暖春计划','你被骗了，这是个假的内容，哈哈哈哈哈！',0,0,'2019-04-04 03:53:36',0,0),(171,'103','互联网求职暖春计划','你被骗了，这是个假的内容，哈哈哈哈哈！',0,0,'2019-04-04 03:53:36',0,0),(172,'103','互联网求职暖春计划','你被骗了，这是个假的内容，哈哈哈哈哈！',0,0,'2019-04-04 03:53:36',0,0),(173,'103','互联网求职暖春计划','你被骗了，这是个假的内容，哈哈哈哈哈！',0,0,'2019-04-04 03:53:36',0,0),(174,'103','互联网求职暖春计划','你被骗了，这是个假的内容，哈哈哈哈哈！',0,0,'2019-04-04 03:53:36',0,0),(175,'103','互联网求职暖春计划','你被骗了，这是个假的内容，哈哈哈哈哈！',0,0,'2019-04-04 03:53:36',0,0),(176,'103','互联网求职暖春计划','你被骗了，这是个假的内容，哈哈哈哈哈！',0,0,'2019-04-04 03:53:36',0,0),(177,'103','互联网求职暖春计划','你被骗了，这是个假的内容，哈哈哈哈哈！',0,0,'2019-04-04 03:53:37',0,0),(178,'103','互联网求职暖春计划','你被骗了，这是个假的内容，哈哈哈哈哈！',0,0,'2019-04-04 03:53:37',0,0),(179,'103','互联网求职暖春计划','你被骗了，这是个假的内容，哈哈哈哈哈！',0,0,'2019-04-04 03:53:37',0,0),(180,'103','互联网求职暖春计划','你被骗了，这是个假的内容，哈哈哈哈哈！',0,0,'2019-04-04 03:53:37',0,0),(181,'103','互联网求职暖春计划','你被骗了，这是个假的内容，哈哈哈哈哈！',0,0,'2019-04-04 03:53:37',0,0),(182,'103','互联网求职暖春计划','你被骗了，这是个假的内容，哈哈哈哈哈！',0,0,'2019-04-04 03:53:37',0,0),(183,'103','互联网求职暖春计划','你被骗了，这是个假的内容，哈哈哈哈哈！',0,0,'2019-04-04 03:53:37',0,0),(184,'103','互联网求职暖春计划','你被骗了，这是个假的内容，哈哈哈哈哈！',0,0,'2019-04-04 03:53:37',0,0),(185,'103','互联网求职暖春计划','你被骗了，这是个假的内容，哈哈哈哈哈！',0,0,'2019-04-04 03:53:37',0,0),(186,'103','互联网求职暖春计划','你被骗了，这是个假的内容，哈哈哈哈哈！',0,0,'2019-04-04 03:53:37',0,0),(187,'103','互联网求职暖春计划','你被骗了，这是个假的内容，哈哈哈哈哈！',0,0,'2019-04-04 03:53:37',0,0),(188,'103','互联网求职暖春计划','你被骗了，这是个假的内容，哈哈哈哈哈！',0,0,'2019-04-04 03:53:37',0,0),(189,'103','互联网求职暖春计划','你被骗了，这是个假的内容，哈哈哈哈哈！',0,0,'2019-04-04 03:53:37',0,0),(190,'103','互联网求职暖春计划','你被骗了，这是个假的内容，哈哈哈哈哈！',0,0,'2019-04-04 03:53:37',0,0),(191,'103','互联网求职暖春计划','你被骗了，这是个假的内容，哈哈哈哈哈！',0,0,'2019-04-04 03:53:37',0,0),(192,'103','互联网求职暖春计划','你被骗了，这是个假的内容，哈哈哈哈哈！',0,0,'2019-04-04 03:53:37',0,0),(193,'103','互联网求职暖春计划','你被骗了，这是个假的内容，哈哈哈哈哈！',0,0,'2019-04-04 03:53:37',0,0),(194,'103','互联网求职暖春计划','你被骗了，这是个假的内容，哈哈哈哈哈！',0,0,'2019-04-04 03:53:37',0,0),(195,'103','互联网求职暖春计划','你被骗了，这是个假的内容，哈哈哈哈哈！',0,0,'2019-04-04 03:53:37',0,0),(196,'103','互联网求职暖春计划','你被骗了，这是个假的内容，哈哈哈哈哈！',0,0,'2019-04-04 03:53:37',0,0),(197,'103','互联网求职暖春计划','你被骗了，这是个假的内容，哈哈哈哈哈！',0,0,'2019-04-04 03:53:37',0,0),(198,'103','互联网求职暖春计划','你被骗了，这是个假的内容，哈哈哈哈哈！',0,0,'2019-04-04 03:53:37',0,0),(199,'103','互联网求职暖春计划','你被骗了，这是个假的内容，哈哈哈哈哈！',0,0,'2019-04-04 03:53:37',0,0),(200,'103','互联网求职暖春计划','你被骗了，这是个假的内容，哈哈哈哈哈！',0,0,'2019-04-04 03:53:37',0,0),(201,'103','互联网求职暖春计划','你被骗了，这是个假的内容，哈哈哈哈哈！',0,0,'2019-04-04 03:53:37',0,0),(202,'103','互联网求职暖春计划','你被骗了，这是个假的内容，哈哈哈哈哈！',0,0,'2019-04-04 03:53:37',0,0),(203,'103','互联网求职暖春计划','你被骗了，这是个假的内容，哈哈哈哈哈！',0,0,'2019-04-04 03:53:37',0,0),(204,'103','互联网求职暖春计划','你被骗了，这是个假的内容，哈哈哈哈哈！',0,0,'2019-04-04 03:53:37',0,0),(205,'103','互联网求职暖春计划','你被骗了，这是个假的内容，哈哈哈哈哈！',0,0,'2019-04-04 03:53:37',0,0),(206,'103','互联网求职暖春计划','你被骗了，这是个假的内容，哈哈哈哈哈！',0,0,'2019-04-04 03:53:37',0,0),(207,'103','互联网求职暖春计划','你被骗了，这是个假的内容，哈哈哈哈哈！',0,0,'2019-04-04 03:53:37',0,0),(208,'103','互联网求职暖春计划','你被骗了，这是个假的内容，哈哈哈哈哈！',0,0,'2019-04-04 03:53:37',0,0),(209,'103','互联网求职暖春计划','你被骗了，这是个假的内容，哈哈哈哈哈！',0,0,'2019-04-04 03:53:37',0,0),(210,'103','互联网求职暖春计划','你被骗了，这是个假的内容，哈哈哈哈哈！',0,0,'2019-04-04 03:53:37',0,0),(211,'103','互联网求职暖春计划','今年的就业形势，确实不容乐观。过了个年，仿佛跳水一般，整个讨论区哀鸿遍野！19届真的没人要了吗？！18届被优化真的没有出路了吗？！大家的“哀嚎”与“悲惨遭遇”牵动了每日潜伏于讨论区的牛客小哥哥小姐姐们的心，于是牛客决定：是时候为大家做点什么了！为了帮助大家度过“艰难”，牛客网特别联合60+家企业，开启互联网求职暖春计划，面向18届&19届，拯救0 offer！',0,0,'2019-04-04 03:53:37',0,0),(212,'103','互联网求职暖春计划','今年的就业形势，确实不容乐观。过了个年，仿佛跳水一般，整个讨论区哀鸿遍野！19届真的没人要了吗？！18届被优化真的没有出路了吗？！大家的“哀嚎”与“悲惨遭遇”牵动了每日潜伏于讨论区的牛客小哥哥小姐姐们的心，于是牛客决定：是时候为大家做点什么了！为了帮助大家度过“艰难”，牛客网特别联合60+家企业，开启互联网求职暖春计划，面向18届&19届，拯救0 offer！',0,0,'2019-04-04 03:53:37',0,0),(213,'103','互联网求职暖春计划','今年的就业形势，确实不容乐观。过了个年，仿佛跳水一般，整个讨论区哀鸿遍野！19届真的没人要了吗？！18届被优化真的没有出路了吗？！大家的“哀嚎”与“悲惨遭遇”牵动了每日潜伏于讨论区的牛客小哥哥小姐姐们的心，于是牛客决定：是时候为大家做点什么了！为了帮助大家度过“艰难”，牛客网特别联合60+家企业，开启互联网求职暖春计划，面向18届&19届，拯救0 offer！',0,0,'2019-04-04 03:53:37',0,0),(214,'103','互联网求职暖春计划','今年的就业形势，确实不容乐观。过了个年，仿佛跳水一般，整个讨论区哀鸿遍野！19届真的没人要了吗？！18届被优化真的没有出路了吗？！大家的“哀嚎”与“悲惨遭遇”牵动了每日潜伏于讨论区的牛客小哥哥小姐姐们的心，于是牛客决定：是时候为大家做点什么了！为了帮助大家度过“艰难”，牛客网特别联合60+家企业，开启互联网求职暖春计划，面向18届&19届，拯救0 offer！',0,0,'2019-04-04 03:53:37',0,0),(215,'103','互联网求职暖春计划','今年的就业形势，确实不容乐观。过了个年，仿佛跳水一般，整个讨论区哀鸿遍野！19届真的没人要了吗？！18届被优化真的没有出路了吗？！大家的“哀嚎”与“悲惨遭遇”牵动了每日潜伏于讨论区的牛客小哥哥小姐姐们的心，于是牛客决定：是时候为大家做点什么了！为了帮助大家度过“艰难”，牛客网特别联合60+家企业，开启互联网求职暖春计划，面向18届&19届，拯救0 offer！',0,0,'2019-04-04 03:53:37',0,0),(216,'103','互联网求职暖春计划','今年的就业形势，确实不容乐观。过了个年，仿佛跳水一般，整个讨论区哀鸿遍野！19届真的没人要了吗？！18届被优化真的没有出路了吗？！大家的“哀嚎”与“悲惨遭遇”牵动了每日潜伏于讨论区的牛客小哥哥小姐姐们的心，于是牛客决定：是时候为大家做点什么了！为了帮助大家度过“艰难”，牛客网特别联合60+家企业，开启互联网求职暖春计划，面向18届&19届，拯救0 offer！',0,0,'2019-04-04 03:53:37',0,0),(217,'103','互联网求职暖春计划','今年的就业形势，确实不容乐观。过了个年，仿佛跳水一般，整个讨论区哀鸿遍野！19届真的没人要了吗？！18届被优化真的没有出路了吗？！大家的“哀嚎”与“悲惨遭遇”牵动了每日潜伏于讨论区的牛客小哥哥小姐姐们的心，于是牛客决定：是时候为大家做点什么了！为了帮助大家度过“艰难”，牛客网特别联合60+家企业，开启互联网求职暖春计划，面向18届&19届，拯救0 offer！',0,0,'2019-04-04 03:53:37',0,0),(218,'111','haha','hahahaha',0,0,'2019-04-04 09:10:38',0,0),(219,'111','hehe','hehe',0,0,'2019-04-04 09:12:47',0,0),(220,'111','heihei','heihei',0,0,'2019-04-04 09:16:10',0,0),(221,'111','ddd','ddd',0,0,'2019-04-04 09:17:24',0,0),(222,'111','www','www',0,0,'2019-04-04 09:18:20',0,0),(223,'111','qqq','qqq',0,0,'2019-04-04 09:19:44',0,0),(224,'111','ggg','ggg',0,0,'2019-04-04 09:20:16',0,0),(225,'111','kkk','kkk',0,0,'2019-04-04 09:20:37',0,0),(226,'111','&lt;script&gt;alert(1);&lt;/script&gt;','&lt;script&gt;alert(1);&lt;/script&gt;',0,0,'2019-04-04 09:21:04',0,0),(227,'111','哈哈','***, ***, 快来呀!',0,2,'2019-04-04 09:21:42',0,0),(228,'111','ccc','ccc',0,0,'2019-04-04 09:37:38',9,0),(229,'112','lala','lalalalala',0,0,'2019-04-05 14:33:00',1,1709.0791812460477),(230,'131','新人报道','新人报道,请多关照!',0,0,'2019-04-08 04:09:17',0,0),(231,'132','灌水','新人灌水',0,0,'2019-04-08 06:08:11',1,0),(232,'132','发一个','发一个帖子, 哈哈!',0,1,'2019-04-08 07:57:57',7,1713.167317334748),(233,'111','Hello','Hello World',0,0,'2019-04-10 03:10:00',15,1715.2741578492637),(234,'111','玄学帖','据说玄学贴很灵验，求大佬捞捞我这个菜鸡给个机会！',1,0,'2019-04-13 09:54:04',13,1718.1335389083702),(235,'111','揭秘时尚科技的力量','它是最时尚的互联网公司之一，\n致力于帮助人们发现流行趋势；\n它是专注于科技的电商企业，\n力图让人们享受更优质的购物体验。',0,1,'2019-04-13 10:24:41',1,0),(236,'111','测试1','测试1',0,0,'2019-04-13 10:33:39',2,0),(237,'111','测试2','测试2',0,0,'2019-04-13 14:20:31',7,1717.9242792860618),(238,'111','中国','中华人民共和国',0,0,'2019-04-16 13:50:33',0,0),(239,'111','美国','美利坚合众国',0,0,'2019-04-16 13:50:44',0,0),(240,'111','日本','大日本帝国',0,0,'2019-04-16 13:51:01',0,0),(241,'111','华人','我爱中华人民共和国',0,0,'2019-04-16 14:22:19',0,0),(242,'111','爱国','我叫王爱国, 我爱中华人民共和国!',0,0,'2019-04-16 14:22:38',0,0),(243,'111','因特网哈哈','好啊',0,0,'2019-04-17 03:58:43',0,0),(244,'111','哎呀','好艰难啊好艰难!',0,0,'2019-04-17 03:59:00',0,0),(245,'111','Spring Boot整合Elasticsearch','Elasticsearch是一款分布式搜索引擎框架',0,0,'2019-04-17 08:27:58',1,0),(246,'133','Good','Good Morning',0,0,'2019-04-19 03:37:23',3,1723.5314789170423),(247,'133','haha','haha',0,0,'2019-04-19 09:25:27',0,0),(248,'134','lhh','aaa',0,0,'2019-04-19 10:15:25',0,0),(249,'134','&lt;script&gt;alert(1);&lt;/script&gt;','&lt;script&gt;alert(1);&lt;/script&gt;',0,0,'2019-04-19 10:16:02',1,0),(265,'103','互联网求职暖春计划','今年的就业形势，确实不容乐观。过了个年，仿佛跳水一般，整个讨论区哀鸿遍野！19届真的没人要了吗？！18届被优化真的没有出路了吗？！大家的&ldquo;哀嚎&rdquo;与&ldquo;悲惨遭遇&rdquo;牵动了每日潜伏于讨论区的牛客小哥哥小姐姐们的心，于是牛客决定：是时候为大家做点什么了！为了帮助大家度过&ldquo;寒冬&rdquo;，牛客网特别联合60+家企业，开启互联网求职暖春计划，面向18届&amp;19届，拯救0 offer！',0,0,'2019-04-25 02:14:05',0,0),(270,'138','xxx','xxx',0,0,'2019-04-25 06:45:18',6,1729.7923916894983),(271,'138','public','public static void main',0,0,'2019-04-25 07:22:16',2,1729.3802112417115),(272,'111','天涯','天呀',0,2,'2019-04-26 07:42:31',2,1730.3802112417115),(273,'145','哈哈','哈哈哈哈',0,0,'2019-04-28 07:32:45',2,1732.3802112417115),(274,'146','我要offer','跪求offer~~~',0,1,'2019-05-15 03:34:14',37,1750.6522463410033),(275,'11','我是管理员','我是管理员，你们都老实点！',1,1,'2019-05-16 10:58:44',12,1751.2900346113624),(276,'149','新人报道','新人报道，请多关照！',0,0,'2019-05-17 07:50:18',6,1751.806179973984),(277,'149','Spring Cache','Spring Cache RedisCacheManager',0,0,'2019-05-17 09:06:54',38,1752.5797835966168),(280,'149','事务','事务的4个特性，包括原子性、一致性、隔离性、持久性。',0,0,'2019-05-20 09:41:30',16,1755.2095150145426);
   ```
   
2. 创建`DiscussPost`帖子实体类：
   
      ```java
      package com.zwm.entity;
      
      import lombok.AllArgsConstructor;
      import lombok.Data;
      import lombok.NoArgsConstructor;
      
      import java.util.Date;
      
      @Data
      @AllArgsConstructor
      @NoArgsConstructor
      public class DiscussPost {
          private int id;//帖子ID
          private int userId;//用户ID
          private String title;//帖子标题
          private String content;//帖子内容
          private int type;//帖子类型：0-普通 + 1-置顶
          private int status;//帖子状态：0-普通 + 1-精华 + 2-拉黑
          private int commentCount;//帖子评论数量
          private Date createTime;//帖子创建日期
          private Double score;//帖子获得的分数
      
          @Override
          public String toString() {
              return "DiscussPost{" +
                      "id=" + id +
                      ", userId=" + userId +
                      ", title='" + title + '\'' +
                      ", content='" + content + '\'' +
                      ", type=" + type +
                      ", status=" + status +
                      ", commentCount=" + commentCount +
                      ", createTime=" + createTime +
                      ", score=" + score +
                      '}';
          }
      }
      ```

3.  `application.properties`配置文件：

   ```properties
   server.port=80
   spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
   spring.datasource.url=jdbc:mysql://localhost:3306/community?useSSL=false&serverTimezone=Asia/Shanghai
   spring.datasource.username=root
   spring.datasource.password=123456
   mybatis.mapper-locations=classpath*:com/zwm/dao/*.xml
   mybatis.type-aliases-package=com.zwm.entity
   mybatis.configuration.log-impl=org.apache.ibatis.logging.stdout.StdOutImpl
   mybatis.configuration.use-generated-keys=true
   mybatis.configuration.map-underscore-to-camel-case=true
   ```

4. 创建`dao/DiscussPostMapper.interface`和`dao/DiscussPostMapper.xml`

   ```java
   package com.zwm.dao;
   
   import com.zwm.entity.DiscussPost;
   import org.apache.ibatis.annotations.Mapper;
   
   import java.util.List;
   
   @Mapper
   public interface DiscussPostMapper {
       //根据 userId 分页查询状态为没有拉黑的帖子，如果是 userId = 0 代表查询所有
       public abstract List<DiscussPost> selectDiscussPosts(int userId, int start, int end);
   
       //根据 userId 分页查询状态为没有拉黑的帖子数量
       public abstract int selectDiscussPostsCount(int userId);
   }
   ```

   ```xml
   <?xml version="1.0" encoding="UTF-8" ?>
   <!DOCTYPE mapper
           PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
           "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
   <mapper namespace="com.zwm.dao.DiscussPostMapper">
       <sql id="selectFields">
           id, user_id, title, content, type, status, comment_count, create_time, score
       </sql>
   
       <select id="selectDiscussPosts" resultType="discussPost">
           select <include refid="selectFields"/>
           from discuss_post
           where status != 2
           <if test="userId != 0">and user_id = #{userId}</if>
           limit #{start}, #{end};
       </select>
   
       <select id="selectDiscussPostsCount" resultType="java.lang.Integer">
           select count(*)
           from discuss_post
           where status != 2
           <if test="userId != 0">and user_id = #{userId}</if>
       </select>
   </mapper>
   ```

5. **<font color="red">遇到`bug`</font>**

   导入`mybatis-spring-boot 1.3.2`的时候出现错误：`Clean up the broken artifacts data (.lastUpdated files) and reload the project.`

   原因：我的`Maven`是`3.8.2`版本的，这里引入的是`http`源，需要把`settings.xml`中的`https`注释掉即可解决

   解决方法：换成`mybatis-spring-boot-stater`

6. 为了更好的测试链接数据库这里直接一套打到`service`、`controller`

   `service`如下：

   ```java
   package com.zwm.service;
   
   import com.zwm.entity.DiscussPost;
   import org.springframework.stereotype.Service;
   
   import java.util.List;
   
   public interface DiscussPostService {
       //根据 userId 分页查询状态为没有拉黑的帖子
       public abstract List<DiscussPost> findDiscussPosts(int userId, int start, int end);
   
       //根据 userId 分页查询状态为没有拉黑的帖子数量
       public abstract int findDiscussPostsCount(int userId);
   }
   ```

   ```java
   package com.zwm.service.impl;
   
   import com.zwm.dao.DiscussPostMapper;
   import com.zwm.entity.DiscussPost;
   import com.zwm.service.DiscussPostService;
   import org.springframework.beans.factory.annotation.Autowired;
   import org.springframework.stereotype.Service;
   
   import java.util.List;
   
   @Service
   public class DiscussPostServiceImpl implements DiscussPostService {
   
       @Autowired
       private DiscussPostMapper discussPostMapper;
   
       @Override
       public List<DiscussPost> findDiscussPosts(int userId, int start, int end) {
           return discussPostMapper.selectDiscussPosts(userId, start, end);
       }
   
       @Override
       public int findDiscussPostsCount(int userId) {
           return discussPostMapper.selectDiscussPostsCount(userId);
       }
   }
   ```

   `controller`如下：

   ```java
   package com.zwm.controller;
   
   import com.zwm.service.impl.DiscussPostServiceImpl;
   import org.springframework.beans.factory.annotation.Autowired;
   import org.springframework.web.bind.annotation.GetMapping;
   import org.springframework.web.bind.annotation.RequestMapping;
   import org.springframework.web.bind.annotation.RequestMethod;
   import org.springframework.web.bind.annotation.RestController;
   
   @RestController
   @RequestMapping(value = "/discussPost", method = RequestMethod.GET)
   public class DiscussPostController {
   
       @Autowired
       DiscussPostServiceImpl discussPostService;
   
       @RequestMapping(value = "/select1")
       public Object findDiscussPosts() {
           return discussPostService.findDiscussPosts(1, 0, 10);
       }
   
       @RequestMapping(value = "/select2")
       public Object findDiscussPostsCount() {
           return discussPostService.findDiscussPostsCount(1);
       }
   }
   ```

7. **<font color="red">遇到`bug`</font>**

   准备完毕启动项目之后遇到`bug`：

   > Invalid bound statement (not found)

   解决办法：在`pom.xml`加入：

   ```xml
   <resources>
       <resource>
           <directory>src/main/java</directory>
           <includes>
               <include>**/*.properties</include>
               <include>**/*.xml</include>
           </includes>
           <filtering>false</filtering>
       </resource>
   </resources>
   ```

   这时候再使用`mvn.clean`然后再运行项目即可，`classes`就出现了

8. 静态资源`css`、`html`、`img`、`js` 放到`static`目录下，模板`mail`、`site`、`index.html`放到`template`目录下
9. 开发视图层 ---> `com/zwm/controller/HomeController.java`，为了在首页正确显示用户的头像和姓名，所以首先需要有`User`信息

#### 5.1.2 用户

1. 创建用户表 user【用户名 + 用户`id`都是唯一的】

   |       字段        |    类型     |                备注                |
   | :---------------: | :---------: | :--------------------------------: |
   |       `id`        |    `int`    |             主键、自增             |
   |    `username`     |  `varchar`  |          用户名，创建索引          |
   |    `password`     |  `varchar`  |              用户密码              |
   |      `salt`       |  `varchar`  |              加密盐值              |
   |      `email`      |  `varchar`  |         用户邮箱，创建索引         |
   |      `type`       |    `int`    | 用户类型：0 普通、1 管理员、2 版主 |
   |     `status`      |    `int`    |    用户状态：0 未激活、1 已激活    |
   | `activation_code` |  `varchar`  |               激活码               |
   |   `header_url`    |  `varchar`  |            用户头像地址            |
   |   `create_time`   | `timestamp` |              注册时间              |

   ```sql
   DROP TABLE IF EXISTS `user`;
   SET character_set_client = utf8mb4 ;
   CREATE TABLE `user` (
     `id` int(11) NOT NULL AUTO_INCREMENT,
     `username` varchar(50) DEFAULT NULL,
     `password` varchar(50) DEFAULT NULL,
     `salt` varchar(50) DEFAULT NULL,
     `email` varchar(100) DEFAULT NULL,
     `type` int(11) DEFAULT NULL COMMENT '0-普通用户; 1-超级管理员; 2-版主;',
     `status` int(11) DEFAULT NULL COMMENT '0-未激活; 1-已激活;',
     `activation_code` varchar(100) DEFAULT NULL,
     `header_url` varchar(200) DEFAULT NULL,
     `create_time` timestamp NULL DEFAULT NULL,
     PRIMARY KEY (`id`),
     KEY `index_username` (`username`(20)),
     KEY `index_email` (`email`(20))
   ) ENGINE=InnoDB AUTO_INCREMENT=101 DEFAULT CHARSET=utf8;
   
   INSERT INTO `user` VALUES (1,'SYSTEM','SYSTEM','SYSTEM','nowcoder1@sina.com',0,1,NULL,'http://static.nowcoder.com/images/head/notify.png','2019-04-13 02:11:03'),(11,'nowcoder11','25ac0a2e8bd0f28928de3c56149283d6','49f10','nowcoder11@sina.com',1,1,NULL,'http://images.nowcoder.com/head/11t.png','2019-04-17 09:11:27'),(12,'nowcoder12','25ac0a2e8bd0f28928de3c56149283d6','49f10','nowcoder12@sina.com',1,1,NULL,'http://images.nowcoder.com/head/12t.png','2019-04-17 09:11:28'),(13,'nowcoder13','25ac0a2e8bd0f28928de3c56149283d6','49f10','nowcoder13@sina.com',1,1,NULL,'http://images.nowcoder.com/head/13t.png','2019-04-17 09:11:28'),(21,'nowcoder21','25ac0a2e8bd0f28928de3c56149283d6','49f10','nowcoder21@sina.com',2,1,NULL,'http://images.nowcoder.com/head/21t.png','2019-04-17 09:11:28'),(22,'nowcoder22','25ac0a2e8bd0f28928de3c56149283d6','49f10','nowcoder22@sina.com',2,1,NULL,'http://images.nowcoder.com/head/22t.png','2019-04-17 09:11:28'),(23,'nowcoder23','25ac0a2e8bd0f28928de3c56149283d6','49f10','nowcoder23@sina.com',2,1,NULL,'http://images.nowcoder.com/head/23t.png','2019-04-17 09:11:28'),(24,'nowcoder24','25ac0a2e8bd0f28928de3c56149283d6','49f10','nowcoder24@sina.com',2,1,NULL,'http://images.nowcoder.com/head/24t.png','2019-04-17 09:11:28'),(25,'nowcoder25','25ac0a2e8bd0f28928de3c56149283d6','49f10','nowcoder25@sina.com',2,1,NULL,'http://images.nowcoder.com/head/25t.png','2019-04-17 09:11:28'),(101,'liubei','390ba5f6b5f18dd4c63d7cda170a0c74','12345','nowcoder101@sina.com',0,1,NULL,'http://images.nowcoder.com/head/100t.png','2019-04-03 04:04:55'),(102,'guanyu','390ba5f6b5f18dd4c63d7cda170a0c74','12345','nowcoder102@sina.com',0,1,NULL,'http://images.nowcoder.com/head/200t.png','2019-04-03 04:04:55'),(103,'zhangfei','390ba5f6b5f18dd4c63d7cda170a0c74','12345','nowcoder103@sina.com',0,1,NULL,'http://images.nowcoder.com/head/100t.png','2019-04-03 04:04:55'),(111,'aaa','b8ca3cbc6fd57c78736c66611a7e7044','167f9','nowcoder111@sina.com',0,1,NULL,'http://images.nowcoder.com/head/111t.png','2019-04-03 07:31:19'),(112,'bbb','216dc48902665b6a6dba534717d49592','90ad0','nowcoder112@sina.com',0,1,NULL,'http://images.nowcoder.com/head/112t.png','2019-04-04 10:36:24'),(113,'ccc','511247cf6bf9cf3d37aef555d0dd9b75','fe290','nowcoder113@sina.com',0,1,NULL,'http://images.nowcoder.com/head/705t.png','2019-04-06 13:29:52'),(114,'ddd','d432b1aaec9197cb6e23ed8e335fe42f','fd1b1','nowcoder114@sina.com',0,1,NULL,'http://images.nowcoder.com/head/972t.png','2019-04-06 13:30:36'),(115,'eee','caca1fe634005d505afd82ef7874fc4f','0c8d2','nowcoder115@sina.com',0,1,NULL,'http://images.nowcoder.com/head/316t.png','2019-04-06 13:30:48'),(116,'fff','deda51913a3ae16d9915fc4c520ac4b6','19341','nowcoder116@sina.com',0,1,NULL,'http://images.nowcoder.com/head/180t.png','2019-04-06 13:31:00'),(117,'ggg','4e85bff4afbb34b2dd676f5e5737050f','9931d','nowcoder117@sina.com',0,1,NULL,'http://images.nowcoder.com/head/896t.png','2019-04-06 13:31:19'),(118,'hhh','8d4c0d490ea1585cd7973bb55bd39d07','e123d','nowcoder118@sina.com',0,1,NULL,'http://images.nowcoder.com/head/834t.png','2019-04-06 13:38:18'),(119,'iii','2214de584a27c7c28dd46a9505bfdc8b','f8880','nowcoder119@sina.com',0,1,NULL,'http://images.nowcoder.com/head/240t.png','2019-04-06 13:38:33'),(120,'jjj','9522866891d647323a7fb5c640b8fa37','12c25','nowcoder120@sina.com',0,1,NULL,'http://images.nowcoder.com/head/760t.png','2019-04-06 13:39:45'),(121,'kkk','5a80e7d897dec9b0aec2919fb42a158e','b8710','nowcoder121@sina.com',0,1,NULL,'http://images.nowcoder.com/head/358t.png','2019-04-06 13:41:34'),(122,'lll','fdc3616df634614bc0ffacee17f735bd','b067f','nowcoder122@sina.com',0,1,NULL,'http://images.nowcoder.com/head/70t.png','2019-04-06 13:45:36'),(123,'mmm','d9b57ddfa9faa06c581c803dc2811edb','f7014','nowcoder123@sina.com',0,1,NULL,'http://images.nowcoder.com/head/160t.png','2019-04-06 13:48:34'),(124,'nnn','f878b7181a95a3330d90198deab16aca','bbf47','nowcoder124@sina.com',0,1,NULL,'http://images.nowcoder.com/head/506t.png','2019-04-06 13:49:39'),(125,'ooo','f71e07cc9c6ebb9e8cfc1fc58265ff33','ff72a','nowcoder125@sina.com',0,1,NULL,'http://images.nowcoder.com/head/45t.png','2019-04-06 13:50:35'),(126,'ppp','e2f178c5076dabbb35b73da19774b271','5027b','nowcoder126.sina.com',0,1,NULL,'http://images.nowcoder.com/head/771t.png','2019-04-06 13:51:42'),(127,'qqq','d209b28b19fdcb4aafc3a795157a4651','3aebf','nowcoder127@sina.com',0,1,NULL,'http://images.nowcoder.com/head/492t.png','2019-04-06 13:52:29'),(128,'rrr','a6043995e741593540687d87c1ce40e8','c543c','nowcoder128@sina.com',0,1,NULL,'http://images.nowcoder.com/head/779t.png','2019-04-06 13:53:19'),(129,'sss','ae8754f0d791f9fea1627a1862c4de5f','d3641','nowcoder129@sina.com',0,1,NULL,'http://images.nowcoder.com/head/977t.png','2019-04-06 13:57:34'),(131,'ttt','d50960f067142c59cc3bdac61b87759f','72450','nowcoder131@sina.com',0,1,NULL,'http://images.nowcoder.com/head/677t.png','2019-04-08 04:05:49'),(132,'uuu','a80ba77157d2fd9c67dd3187907cef42','f1113','nowcoder132@sina.com',0,1,NULL,'http://images.nowcoder.com/head/278t.png','2019-04-08 06:07:04'),(133,'vvv','252c226ba0a601ec3fa4d7c58b2291d9','13211','nowcoder133@sina.com',0,1,NULL,'http://images.nowcoder.com/head/133t.png','2019-04-19 03:08:55'),(134,'www','3d3aeebb9cd302ae581dfa8fedd86ceb','dfc00','nowcoder134@sina.com',0,1,NULL,'http://images.nowcoder.com/head/134t.png','2019-04-19 10:13:57'),(137,'xxx','046291c11cdfb896aa7cd48714bb6352','968fc','nowcoder137@sina.com',0,1,NULL,'http://images.nowcoder.com/head/677t.png','2019-04-26 11:48:31'),(138,'yyy','046291c11cdfb896aa7cd48714bb6352','968fc','nowcoder138@sina.com',0,1,'69dcd69f4c0145058df820e90820ba1e','http://images.nowcoder.com/head/138t.png','2019-04-25 07:18:07'),(144,'zzz','07b83b7747ca08bc4b0153d5b8ce7519','21e8b','nowcoder144@sina.com',0,1,'107eb2cbb8454fbe96848790e6a730b1','http://images.nowcoder.com/head/144t.png','2019-04-26 08:59:50'),(145,'lhh','d980a16ea0b3c8a81062ee806e65a4bc','5abfc','nowcoder145@sina.com',0,1,'f217b637e9544e2a9b4a88f78c583d03','http://images.nowcoder.com/head/145t.png','2019-04-28 07:30:36'),(146,'lihonghe','100489ece9bacfa4d57eb5777b4d4643','00d7a','nowcoder146@sina.com',0,1,'5a61faee7af94e89ba7237b1277c9fed','http://images.nowcoder.com/head/146t.png','2019-04-29 03:47:24'),(149,'niuke','ebce124c4ba2de3be92dc9a3bc1ea75b','90196','nowcoder149@sina.com',0,1,'d7a5714a145b4461b5a4199cc5a0014f','http://images.nowcoder.com/head/149t.png','2019-05-17 07:48:11');
   ```

2. 创建`User.java + UserMapper.java + UserMapper.xml + UserService.java + UserServiceImp.java + HomeController.java`

   ```java
   package com.zwm.entity;
   
   import lombok.AllArgsConstructor;
   import lombok.Data;
   import lombok.NoArgsConstructor;
   
   import java.util.Date;
   
   @Data
   @AllArgsConstructor
   @NoArgsConstructor
   public class User {
       private int id;//主键
       private String username;//用户名
       private String password;//密码
       private String salt;//加密盐值
       private String email;//邮箱地址
       private String activationCode;//激活码
       private Date createTime;//注册时间
       private int type;//用户类型 0-普通 1-管理员 2-版主
       private int status;//用户状态 0-已激活 1-未激活
       private String headerUrl;//头像Url地址
   
       @Override
       public String toString() {
           return "User{" +
                   "id=" + id +
                   ", username='" + username + '\'' +
                   ", password='" + password + '\'' +
                   ", salt='" + salt + '\'' +
                   ", email='" + email + '\'' +
                   ", activeCode='" + activationCode + '\'' +
                   ", createTime=" + createTime +
                   ", type=" + type +
                   ", status=" + status +
                   '}';
       }
   }
   ```

   ```java
   package com.zwm.dao;
   
   import com.zwm.entity.User;
   import org.apache.ibatis.annotations.Mapper;
   
   @Mapper
   public interface UserMapper {
       //根据 Id 查询用户
       public abstract User selectUserById(int id);
   
       //根据姓名查询用户
       public abstract User selectUserByName(String username);
   
       //根据邮箱查询用户
       public abstract User selectUserByEmail(String email);
   
       //插入用户
       public abstract int insertUser(User user);
   
       //更新用户状态
       public abstract int updateUserStatus(int id, int status);
   
       //更新用户头像
       public abstract int updateUserHeader(int id, String headUrl);
   
       //更新用户密码
       public abstract int updateUserPassword(int id, String password);
   }
   ```

   ```java
   package com.zwm.service;
   
   import com.zwm.entity.User;
   
   public interface UserService {
       public abstract User findUserById(int id);
   }
   ```

   ```java
   package com.zwm.service.impl;
   
   import com.zwm.dao.UserMapper;
   import com.zwm.entity.User;
   import com.zwm.service.UserService;
   import org.springframework.beans.factory.annotation.Autowired;
   import org.springframework.stereotype.Service;
   
   @Service
   public class UserServiceImpl implements UserService {
   
       @Autowired
       private UserMapper userMapper;
   
       @Override
       public User findUserById(int id) {
           System.out.println("111");
           return userMapper.selectUserById(id);
       }
   }
   ```

   ```java
   package com.zwm.controller;
   
   import com.zwm.service.impl.UserServiceImpl;
   import org.springframework.beans.factory.annotation.Autowired;
   import org.springframework.stereotype.Controller;
   import org.springframework.web.bind.annotation.RequestMapping;
   import org.springframework.web.bind.annotation.ResponseBody;
   
   @Controller
   public class HomeController {
   
       @Autowired
       private UserServiceImpl userService;
   
       @RequestMapping(value = "/selectUser")
       @ResponseBody
       public Object findUserById() {
           return userService.findUserById(122);
       }
   }
   ```

   ```xml
   <?xml version="1.0" encoding="UTF-8" ?>
   <!DOCTYPE mapper
           PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
           "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
   <mapper namespace="com.zwm.dao.UserMapper">
       <sql id="insertFields">
           username
           , password, salt, email, type, status, activation_code, header_url, create_time
       </sql>
   
       <sql id="selectFields">
           id
           , username, password, salt, email, type, status, activation_code, header_url, create_time
       </sql>
   
       <select id="selectUserById" resultType="user">
           select
           <include refid="selectFields"/>
           from user where id = #{id};
       </select>
   
       <select id="selectUserByName" resultType="user">
           select
           <include refid="selectFields"/>
           from user where username = #{username}
       </select>
   
       <select id="selectUserByEmail" resultType="user">
           select
           <include refid="selectFields"/>
           from user where email = #{email}
       </select>
   
       <insert id="insertUser" parameterType="user" keyProperty="id">
           insert into user (<include refid="insertFields"></include>)
           values(#{username}, #{password}, #{salt}, #{email}, #{type}, #{status}, #{activationCode}, #{headerUrl},
           #{createTime})
       </insert>
   
       <update id="updateStatus">
           update user
           set status = #{status}
           where id = #{id}
       </update>
   
       <update id="updateHeader">
           update user
           set header_url = #{headerUrl}
           where id = #{id}
       </update>
   
       <update id="updatePassword">
           update user
           set password = #{password}
           where id = #{id}
       </update>
   </mapper>
   ```

   浏览器中测试：`http://localhost/selectUser`

#### 5.1.3 首页环境

##### 5.1.3.1 显示页面

在`HomeController.java`中创建`getIndexPage()`方法，将帖子和对应发表的帖子用户放置到一个`map`中，因为有多个帖子，可能就有多个用户这样就产生了多个`map`，所以返回的应该是一个装`map`的`List`集合：

```java
 @RequestMapping(value = "/index", method = RequestMethod.GET)
 public String getIndexPage(Model model) {
     List<DiscussPost> discussPostList = discussPostService.findDiscussPosts(0, 0, 10);
     List<Map<String, Object>> discussPosts = new ArrayList<>();
     for (DiscussPost discussPost : discussPostList) {
         Map<String, Object> map = new HashMap<>();
         map.put("discussPost", discussPost);
         User user = userService.findUserById(discussPost.getUserId());
         map.put("user", user);
         discussPosts.add(map);
     }
     model.addAttribute("discussPosts", discussPosts);
     return "/index";
 }
```

`pom.xml`引入`spring-boot-starter-thymeleaf`：

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-thymeleaf</artifactId>
</dependency>
```

`index.html`如下：【这里简单看看界面就好，不用过于关注】

```html
<!doctype html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="icon" href="https://static.nowcoder.com/images/logo_87_87.png"/>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          crossorigin="anonymous">
    <link rel="stylesheet" th:href="@{/css/global.css}"/>
    <title>牛客网-首页</title>
</head>
<body>
<div class="nk-container">
    <!-- 头部 -->
    <header class="bg-dark sticky-top">
        <div class="container">
            <!-- 导航 -->
            <nav class="navbar navbar-expand-lg navbar-dark">
                <!-- logo -->
                <a class="navbar-brand" href="#"></a>
                <button class="navbar-toggler" type="button" data-toggle="collapse"
                        data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent"
                        aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <!-- 功能 -->
                <div class="collapse navbar-collapse" id="navbarSupportedContent">
                    <ul class="navbar-nav mr-auto">
                        <li class="nav-item ml-3 btn-group-vertical">
                            <a class="nav-link" href="index.html">首页</a>
                        </li>
                        <li class="nav-item ml-3 btn-group-vertical">
                            <a class="nav-link position-relative" href="site/letter.html">消息<span
                                    class="badge badge-danger">12</span></a>
                        </li>
                        <li class="nav-item ml-3 btn-group-vertical">
                            <a class="nav-link" href="site/register.html">注册</a>
                        </li>
                        <li class="nav-item ml-3 btn-group-vertical">
                            <a class="nav-link" href="site/login.html">登录</a>
                        </li>
                        <li class="nav-item ml-3 btn-group-vertical dropdown">
                            <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button"
                               data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                <img src="http://images.nowcoder.com/head/1t.png" class="rounded-circle"
                                     style="width:30px;"/>
                            </a>
                            <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                                <a class="dropdown-item text-center" href="site/profile.html">个人主页</a>
                                <a class="dropdown-item text-center" href="site/setting.html">账号设置</a>
                                <a class="dropdown-item text-center" href="site/login.html">退出登录</a>
                                <div class="dropdown-divider"></div>
                                <span class="dropdown-item text-center text-secondary">nowcoder</span>
                            </div>
                        </li>
                    </ul>
                    <!-- 搜索 -->
                    <form class="form-inline my-2 my-lg-0" action="site/search.html">
                        <input class="form-control mr-sm-2" type="search" aria-label="Search"/>
                        <button class="btn btn-outline-light my-2 my-sm-0" type="submit">搜索</button>
                    </form>
                </div>
            </nav>
        </div>
    </header>

    <!-- 内容 -->
    <div class="main">
        <div class="container">
            <div class="position-relative">
                <!-- 筛选条件 -->
                <ul class="nav nav-tabs mb-3">
                    <li class="nav-item">
                        <a class="nav-link active" href="#">最新</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#">最热</a>
                    </li>
                </ul>
                <button type="button" class="btn btn-primary btn-sm position-absolute rt-0" data-toggle="modal"
                        data-target="#publishModal">我要发布
                </button>
            </div>
            <!-- 弹出框 -->
            <div class="modal fade" id="publishModal" tabindex="-1" role="dialog" aria-labelledby="publishModalLabel"
                 aria-hidden="true">
                <div class="modal-dialog modal-lg" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="publishModalLabel">新帖发布</h5>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div class="modal-body">
                            <form>
                                <div class="form-group">
                                    <label for="recipient-name" class="col-form-label">标题：</label>
                                    <input type="text" class="form-control" id="recipient-name">
                                </div>
                                <div class="form-group">
                                    <label for="message-text" class="col-form-label">正文：</label>
                                    <textarea class="form-control" id="message-text" rows="15"></textarea>
                                </div>
                            </form>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-dismiss="modal">取消</button>
                            <button type="button" class="btn btn-primary" id="publishBtn">发布</button>
                        </div>
                    </div>
                </div>
            </div>
            <!-- 提示框 -->
            <div class="modal fade" id="hintModal" tabindex="-1" role="dialog" aria-labelledby="hintModalLabel"
                 aria-hidden="true">
                <div class="modal-dialog modal-lg" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="hintModalLabel">提示</h5>
                        </div>
                        <div class="modal-body" id="hintBody">
                            发布完毕!
                        </div>
                    </div>
                </div>
            </div>

            <!-- 帖子列表 -->
            <ul class="list-unstyled">
                <li class="media pb-3 pt-3 mb-3 border-bottom" th:each="map:${discussPosts}">
                    <a href="site/profile.html">
                        <img th:src="${map.user.headerUrl}" class="mr-4 rounded-circle" alt="用户头像"
                             style="width:50px;height:50px;">
                    </a>
                    <div class="media-body">
                        <h6 class="mt-0 mb-3">
                            <a href="#" th:utext="${map.post.title}">备战春招，面试刷题跟他复习，一个月全搞定！</a>
                            <span class="badge badge-secondary bg-primary" th:if="${map.post.type==1}">置顶</span>
                            <span class="badge badge-secondary bg-danger" th:if="${map.post.status==1}">精华</span>
                        </h6>
                        <div class="text-muted font-size-12">
                            <u class="mr-3" th:utext="${map.user.username}">寒江雪</u> 发布于 <b
                                th:text="${#dates.format(map.post.createTime,'yyyy-MM-dd HH:mm:ss')}">2019-04-15
                            15:32:18</b>
                            <ul class="d-inline float-right">
                                <li class="d-inline ml-2">赞 11</li>
                                <li class="d-inline ml-2">|</li>
                                <li class="d-inline ml-2">回帖 7</li>
                            </ul>
                        </div>
                    </div>
                </li>
            </ul>
            <!-- 分页 -->
            <nav class="mt-5">
                <ul class="pagination justify-content-center">
                    <li class="page-item"><a class="page-link" href="#">首页</a></li>
                    <li class="page-item disabled"><a class="page-link" href="#">上一页</a></li>
                    <li class="page-item active"><a class="page-link" href="#">1</a></li>
                    <li class="page-item"><a class="page-link" href="#">2</a></li>
                    <li class="page-item"><a class="page-link" href="#">3</a></li>
                    <li class="page-item"><a class="page-link" href="#">4</a></li>
                    <li class="page-item"><a class="page-link" href="#">5</a></li>
                    <li class="page-item"><a class="page-link" href="#">下一页</a></li>
                    <li class="page-item"><a class="page-link" href="#">末页</a></li>
                </ul>
            </nav>
        </div>
    </div>

    <!-- 尾部 -->
    <footer class="bg-dark">
        <div class="container">
            <div class="row">
                <!-- 二维码 -->
                <div class="col-4 qrcode">
                    <img src="https://uploadfiles.nowcoder.com/app/app_download.png" class="img-thumbnail"
                         style="width:136px;"/>
                </div>
                <!-- 公司信息 -->
                <div class="col-8 detail-info">
                    <div class="row">
                        <div class="col">
                            <ul class="nav">
                                <li class="nav-item">
                                    <a class="nav-link text-light" href="#">关于我们</a>
                                </li>
                                <li class="nav-item">
                                    <a class="nav-link text-light" href="#">加入我们</a>
                                </li>
                                <li class="nav-item">
                                    <a class="nav-link text-light" href="#">意见反馈</a>
                                </li>
                                <li class="nav-item">
                                    <a class="nav-link text-light" href="#">企业服务</a>
                                </li>
                                <li class="nav-item">
                                    <a class="nav-link text-light" href="#">联系我们</a>
                                </li>
                                <li class="nav-item">
                                    <a class="nav-link text-light" href="#">免责声明</a>
                                </li>
                                <li class="nav-item">
                                    <a class="nav-link text-light" href="#">友情链接</a>
                                </li>
                            </ul>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col">
                            <ul class="nav btn-group-vertical company-info">
                                <li class="nav-item text-white-50">
                                    公司地址：北京市朝阳区大屯路东金泉时代3-2708北京牛客科技有限公司
                                </li>
                                <li class="nav-item text-white-50">
                                    联系方式：010-60728802(电话)&nbsp;&nbsp;&nbsp;&nbsp;admin@nowcoder.com
                                </li>
                                <li class="nav-item text-white-50">
                                    牛客科技©2018 All rights reserved
                                </li>
                                <li class="nav-item text-white-50">
                                    京ICP备14055008号-4 &nbsp;&nbsp;&nbsp;&nbsp;
                                    <img src="http://static.nowcoder.com/company/images/res/ghs.png"
                                         style="width:18px;"/>
                                    京公网安备 11010502036488号
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </footer>
</div>

<script src="https://code.jquery.com/jquery-3.3.1.min.js" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"
        crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" crossorigin="anonymous"></script>
<script th:src="@{/js/global.js}"></script>
<script th:src="@{js/index.js}"></script>
</body>
</html>
```

##### 5.1.3.2 分页功能

`Page.java`：

```java
package com.zwm.entity;

public class Page {
    private int current = 1;//当前页码
    private int limit = 10;//当前页显示上线
    private int rows;//数据总数
    private String path;//查询路径

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        //只有 current > 0 的时候才返回
        if (current > 0) this.current = current;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        //上限规定在 1 - 100 之间
        if (limit >= 1 && limit <= 100) this.limit = limit;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        //总数需 >= 0
        if (rows >= 0) this.rows = rows;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    //根据当前页码获取起始行
    public int getStart() {
        return (current - 1) * limit;
    }

    //获取总页数
    public int getTotal() {
        //如果总条数除得尽每页显示上线则返回 rows / limit
        if(rows % limit ==  0) return rows / limit;
        else return rows / limit + 1;
    }

    //页面不可能一共有多少页就显示多少页，为了美观这里显示当前页码的前两个页码
    public int getFrom() {
        int from = current - 3;
        return from < 1 ? 1 : from;
    }

    //显示当前页码的后两个页码
    public int getTo() {
        int to = current + 3;
        int total = getTotal();
        return to > total ? total : to;
    }
}
```

`HomeController.java`：

```java
//page 需要知道总数是和路径
page.setRows(discussPostService.findDiscussPostsCount(0));
page.setPath("/index");
```

`index.html`：

```html
<nav class="mt-5" th:if="${page.rows>0}">
    <ul class="pagination justify-content-center">
        <li class="page-item">
            <a class="page-link" th:href="@{${page.path}(current=1)}">首页</a>
        </li>
        <li th:class="|page-item ${page.current==1?'disabled':''}|">
            <a class="page-link" th:href="@{${page.path}(current=${page.current-1})}">上一页</a></li>
        <li th:class="|page-item ${i==page.current?'active':''}|"
            th:each="i:${#numbers.sequence(page.from,page.to)}">
            <a class="page-link" th:href="@{${page.path}(current=${i})}" th:text="${i}">1</a>
        </li>
        <li th:class="|page-item ${page.current==page.total?'disabled':''}|">
            <a class="page-link" th:href="@{${page.path}(current=${page.current+1})}">下一页</a>
        </li>
        <li class="page-item">
            <a class="page-link" th:href="@{${page.path}(current=${page.total})}">末页</a>
        </li>
    </ul>
</nav>
```

#### 5.1.4 小结

主要就是完成了传递帖子+创建帖子的用户+分页功能，整个一概括就是`HomeController.java`：【暂时不管前端】

```java
@RequestMapping(path = "/index", method = RequestMethod.GET)
public String getIndexPage(Model model, Page page) {
    //page 需要知道总数是和路径
    page.setRows(discussPostService.findDiscussPostsCount(0));
    page.setPath("/index");
    List<DiscussPost> discussPostList = discussPostService.findDiscussPosts(0, page.getStart(), page.getLimit());
    List<Map<String, Object>> discussPosts = new ArrayList<>();
    if (discussPostList != null) {
        for (DiscussPost discussPost : discussPostList) {
            Map<String, Object> map = new HashMap<>();
            map.put("post", discussPost);
            User user = userService.findUserById(discussPost.getUserId());
            map.put("user", user);
            discussPosts.add(map);
        }
    }
    model.addAttribute("discussPosts", discussPosts);
    return "/index";
}
```

### 5.2 项目调试技巧

- 响应状态码的含义【根据响应状态码得出到底是客户端还是服务端出现了错误】

  > 200 OK：请求成功
  >
  > 302：临时重定向
  >
  > 301：永久重定向
  >
  > 404 NOT FOUNT：没有找到资源
  >
  > 500：服务器错误

- 服务端断点调试技巧

  > `F7`：进入方法内部
  >
  > `F8`：逐行运行
  >
  > `F9`：到达下一个断点处，没有就运行完毕结束调试
  >
  > `IDEA`可以直接管理断点


- 设置日志级别：
  - `LogBack`支持`5`种日志级别：`trace debug info warn error`
  - `application.properties: logging.level.com.zwm=debug`
  - `application.properties: logging.file.path=e:/java/community.log`

注：有问题就看日志如果看日志都搞不定就跟踪`debug`

### 5.3 版本控制

这里使用`git`工具来做版本控制：

```
git --version：查询 git 版本
git config --list：查询配置列表
git config --global user.name "xxx"
git config --global user.email "xxx@xxx.com"
git init：初始化本地仓库
git status：查看本地仓库文件状态即是否提交
git add *：增添所有文件到本地仓库
git commit -m "第一次提交"：提交到远程仓库

后续只是需要结合`github`
```

`IDEA`整合`git`：

`Settings` ---> 找到`version control` ---> 找到`git`

### 5.4 社区登录模块

#### 5.4.1 开发邮件功能

​	作用：用于发送注册成功信息给注册用户

1. 导入配置信息到`pom.xml`

   ```xml
   <dependency>
       <groupId>org.springframework.boot</groupId>
       <artifactId>spring-boot-starter-mail</artifactId>
       <version>2.2.0.RELEASE</version>
   </dependency>
   ```

2. 在`application.properties`配置相关邮箱信息包括：邮件协议、端口、邮件发送者账号、邮件发送者密码、邮件协议、是否使用加密模式【需要提前先开启`SMTP`服务，然后复制密码注意这里不是邮箱的密码是授权密码】

   ```properties
   spring.mail.host=smtp.163.com
   spring.mail.port=465
   spring.mail.username=xxx@qq.com
   spring.mail.password=xxxxxxxxxxxxxx
   spring.mail.protocol=smtps
   spring.mail.properties.mail.smtp.ssl.enable=true
   ```

3. 创建发送邮件的服务端【其实也是客户端，只不过用来发送邮件给注册账号的用户】

   ```java
   package com.zwm.util;
   
   import org.slf4j.Logger;
   import org.slf4j.LoggerFactory;
   import org.springframework.beans.factory.annotation.Autowired;
   import org.springframework.beans.factory.annotation.Value;
   import org.springframework.mail.javamail.JavaMailSender;
   import org.springframework.mail.javamail.MimeMessageHelper;
   import org.springframework.stereotype.Component;
   
   import javax.mail.MessagingException;
   import javax.mail.internet.MimeMessage;
   
   @Component
   public class MailServer {
       //创建日志对象用于打印错误信息
       private static final Logger logger = LoggerFactory.getLogger(MailServer.class);
       
       //邮件发送对象
       @Autowired
       private JavaMailSender javaMailSender;
       
       //获取发送方邮件地址【已经配置在 application.properties 中】
       @Value(${spring.mail.username})
       private String from;
       
       //发送邮件
       public void senMail(String to, String subject, String context) {
           try {
               MimeMessage mimeMessage = javaMailSender.createMimeMessage();
          		MimeMessageHelper mimeMessageHelper = new MimeMessageHelper();
   	        mimeMessageHelper.setFrom(from);
       	    mimeMessageHelper.setTo(to);
           	mimeMessageHelper.setSubject(subject);
       	    mimeMessageHelper.setContext(context);
           	javaMailSender.send(mimeMessageHelper.getMessage());
           } catch (MessagingException e) {
               logger.error("邮件发送失败：" + e.getMessage());
           }
       }
   }
   ```

4. 测试邮件是否发送正常：文本邮件 + `HTML`邮件【这里邮箱显示的均为文本，很纳闷...】

   ```java
   package com.zwm;
   
   import com.zwm.util.MailServer;
   import org.junit.jupiter.api.Test;
   import org.springframework.beans.factory.annotation.Autowired;
   import org.springframework.boot.test.context.SpringBootTest;
   import org.thymeleaf.TemplateEngine;
   import org.thymeleaf.context.Context;
   
   @SpringBootTest
   public class MailClient {
   
       @Autowired
       private MailServer mailServer;
   
       @Autowired
       private TemplateEngine templateEngine;
   
       @Test
       public void sendTextMail() {
           mailServer.sendMessage("xxxx@163.com", "I Love You", "Hello World!");
       }
   
       @Test
       public void sendHtmlMail() {
           Context context = new Context();
           context.setVariable("username", "Lucky");
           String content = templateEngine.process("/mail/demo", context);
           System.out.println(content);
           mailServer.sendMessage("xxxx@qq.com", "This is HTML!", content);
       }
   }
   ```

5. `/mail/demo`

   ```html
   <!DOCTYPE html>
   <html lang="en" xmlns:th="http://www.thymeleaf.org">
   <head>
       <meta charset="UTF-8">
       <title>邮件示例</title>
   </head>
   <body>
       <p>欢迎你, <span style="color:red;" th:text="${username}"></span>!</p>
   </body>
   </html>
   ```


#### 5.4.2 开发注册功能

一个大功能不断拆分形成小功能，从而一步步解决做出大功能，常用的拆分标的就是根据请求来拆分，这里也是一样的。

- 访问注册页面需要向服务器发送一次请求
- 注册好的数据需要向服务器发送一次请求
  - 通过表单提交数据
  - 服务端验证账号是否已经存在/邮箱是否已经注册
  - 服务端发送激活邮件
- 激活注册账号
  - 点击邮件中的链接，访问服务端的激活服务

##### 5.4.2.1 显示注册页面

1. `LoginController.java`用于处理用户请求

   ```java
   package com.zwm.controller;
   
   import org.springframework.stereotype.Controller;
   import org.springframework.web.bind.annotation.RequestMapping;
   import org.springframework.web.bind.annotation.RequestMethod;
   
   @Controller
   public class LoginController {
       @RequestMapping(value = "/register", method = RequestMethod.GET)
       public String getLoginPage() {
           return "/site/register";
       }
   }
   ```

2. 处理注册页面`register.html` ---> 注意这里的`header`部分引用了`index.html`中的`header`部分，因为上标题栏都是不变的，点击首页去到首页，点击注册去到注册页面，点击登录去到登录页面

   ```html
   <!doctype html>
   <html lang="en" xmlns:th="http://www.thymeleaf.org">
   <head>
   	<meta charset="utf-8">
   	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
   	<link rel="icon" href="https://static.nowcoder.com/images/logo_87_87.png"/>
   	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" crossorigin="anonymous">
   	<link rel="stylesheet" th:href="@{/css/global.css}" />
   	<link rel="stylesheet" th:href="@{/css/login.css}" />
   	<title>牛客网-注册</title>
   </head>
   <body>
   <div class="nk-container">
   	<!-- 头部 -->
   	<header class="bg-dark sticky-top" th:replace="index::header">
   		<div class="container">
   			<!-- 导航 -->
   			<nav class="navbar navbar-expand-lg navbar-dark">
   				<!-- logo -->
   				<a class="navbar-brand" href="#"></a>
   				<button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
   					<span class="navbar-toggler-icon"></span>
   				</button>
   				<!-- 功能 -->
   				<div class="collapse navbar-collapse" id="navbarSupportedContent">
   					<ul class="navbar-nav mr-auto">
   						<li class="nav-item ml-3 btn-group-vertical">
   							<a class="nav-link" href="../index.html">首页</a>
   						</li>
   						<li class="nav-item ml-3 btn-group-vertical">
   							<a class="nav-link position-relative" href="letter.html">消息<span class="badge badge-danger">12</span></a>
   						</li>
   						<li class="nav-item ml-3 btn-group-vertical">
   							<a class="nav-link" href="register.html">注册</a>
   						</li>
   						<li class="nav-item ml-3 btn-group-vertical">
   							<a class="nav-link" href="login.html">登录</a>
   						</li>
   						<li class="nav-item ml-3 btn-group-vertical dropdown">
   							<a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
   								<img src="http://images.nowcoder.com/head/1t.png" class="rounded-circle" style="width:30px;"/>
   							</a>
   							<div class="dropdown-menu" aria-labelledby="navbarDropdown">
   								<a class="dropdown-item text-center" href="profile.html">个人主页</a>
   								<a class="dropdown-item text-center" href="setting.html">账号设置</a>
   								<a class="dropdown-item text-center" href="login.html">退出登录</a>
   								<div class="dropdown-divider"></div>
   								<span class="dropdown-item text-center text-secondary">nowcoder</span>
   							</div>
   						</li>
   					</ul>
   					<!-- 搜索 -->
   					<form class="form-inline my-2 my-lg-0" action="search.html">
   						<input class="form-control mr-sm-2" type="search" aria-label="Search" />
   						<button class="btn btn-outline-light my-2 my-sm-0" type="submit">搜索</button>
   					</form>
   				</div>
   			</nav>
   		</div>
   	</header>
   
   	<!-- 内容 -->
   	<div class="main">
   		<div class="container pl-5 pr-5 pt-3 pb-3 mt-3 mb-3">
   			<h3 class="text-center text-info border-bottom pb-3">注&nbsp;&nbsp;册</h3>
   			<form class="mt-5" method="post" th:action="@{/register}">
   				<div class="form-group row">
   					<label for="username" class="col-sm-2 col-form-label text-right">账号:</label>
   					<div class="col-sm-10">
   						<input type="text"
   							   th:class="|form-control ${usernameMsg!=null?'is-invalid':''}|"
   							   th:value="${user!=null?user.username:''}"
   							   id="username" name="username" placeholder="请输入您的账号!" required>
   						<div class="invalid-feedback" th:text="${usernameMsg}">
   							该账号已存在!
   						</div>
   					</div>
   				</div>
   				<div class="form-group row mt-4">
   					<label for="password" class="col-sm-2 col-form-label text-right">密码:</label>
   					<div class="col-sm-10">
   						<input type="password"
   							   th:class="|form-control ${passwordMsg!=null?'is-invalid':''}|"
   							   th:value="${user!=null?user.password:''}"
   							   id="password" name="password" placeholder="请输入您的密码!" required>
   						<div class="invalid-feedback" th:text="${passwordMsg}">
   							密码长度不能小于8位!
   						</div>
   					</div>
   				</div>
   				<div class="form-group row mt-4">
   					<label for="confirm-password" class="col-sm-2 col-form-label text-right">确认密码:</label>
   					<div class="col-sm-10">
   						<input type="password" class="form-control"
   							   th:value="${user!=null?user.password:''}"
   							   id="confirm-password" placeholder="请再次输入密码!" required>
   						<div class="invalid-feedback">
   							两次输入的密码不一致!
   						</div>
   					</div>
   				</div>
   				<div class="form-group row">
   					<label for="email" class="col-sm-2 col-form-label text-right">邮箱:</label>
   					<div class="col-sm-10">
   						<input type="email"
   							   th:class="|form-control ${emailMsg!=null?'is-invalid':''}|"
   							   th:value="${user!=null?user.email:''}"
   							   id="email" name="email" placeholder="请输入您的邮箱!" required>
   						<div class="invalid-feedback" th:text="${emailMsg}">
   							该邮箱已注册!
   						</div>
   					</div>
   				</div>
   				<div class="form-group row mt-4">
   					<div class="col-sm-2"></div>
   					<div class="col-sm-10 text-center">
   						<button type="submit" class="btn btn-info text-white form-control">立即注册</button>
   					</div>
   				</div>
   			</form>
   		</div>
   	</div>
   
   	<!-- 尾部 -->
   	<footer class="bg-dark">
   		<div class="container">
   			<div class="row">
   				<!-- 二维码 -->
   				<div class="col-4 qrcode">
   					<img src="https://uploadfiles.nowcoder.com/app/app_download.png" class="img-thumbnail" style="width:136px;" />
   				</div>
   				<!-- 公司信息 -->
   				<div class="col-8 detail-info">
   					<div class="row">
   						<div class="col">
   							<ul class="nav">
   								<li class="nav-item">
   									<a class="nav-link text-light" href="#">关于我们</a>
   								</li>
   								<li class="nav-item">
   									<a class="nav-link text-light" href="#">加入我们</a>
   								</li>
   								<li class="nav-item">
   									<a class="nav-link text-light" href="#">意见反馈</a>
   								</li>
   								<li class="nav-item">
   									<a class="nav-link text-light" href="#">企业服务</a>
   								</li>
   								<li class="nav-item">
   									<a class="nav-link text-light" href="#">联系我们</a>
   								</li>
   								<li class="nav-item">
   									<a class="nav-link text-light" href="#">免责声明</a>
   								</li>
   								<li class="nav-item">
   									<a class="nav-link text-light" href="#">友情链接</a>
   								</li>
   							</ul>
   						</div>
   					</div>
   					<div class="row">
   						<div class="col">
   							<ul class="nav btn-group-vertical company-info">
   								<li class="nav-item text-white-50">
   									公司地址：北京市朝阳区大屯路东金泉时代3-2708北京牛客科技有限公司
   								</li>
   								<li class="nav-item text-white-50">
   									联系方式：010-60728802(电话)&nbsp;&nbsp;&nbsp;&nbsp;admin@nowcoder.com
   								</li>
   								<li class="nav-item text-white-50">
   									牛客科技©2018 All rights reserved
   								</li>
   								<li class="nav-item text-white-50">
   									京ICP备14055008号-4 &nbsp;&nbsp;&nbsp;&nbsp;
   									<img src="http://static.nowcoder.com/company/images/res/ghs.png" style="width:18px;" />
   									京公网安备 11010502036488号
   								</li>
   							</ul>
   						</div>
   					</div>
   				</div>
   			</div>
   		</div>
   	</footer>
   </div>
   
   <script src="https://code.jquery.com/jquery-3.3.1.min.js" crossorigin="anonymous"></script>
   <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" crossorigin="anonymous"></script>
   <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" crossorigin="anonymous"></script>
   <script th:src="@{/js/global.js}"></script>
   <script th:src="@{/js/register.js}"></script>
   </body>
   </html>
   ```

   页面显示如图所示：

   ![](https://img-blog.csdnimg.cn/d51c8b59135f42c3bda156b4132d9006.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBAQ3JBY0tlUi0x,size_20,color_FFFFFF,t_70,g_se,x_16)		

##### 5.4.2.2 注册提交数据+激活

为了制作两个工具：[引入`Apache Commons Lang`]

1. 生成随机数`UUID`，有生成随机注册激活码、随机盐等作用
2. `MD5`加密[后面加随机盐再一起生成] ---> 使用`Spring`自带的加密工具`DegistUtils.md5DegistAsHex(key.getBytes())`

```java
package com.zwm.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.DigestUtils;

import java.util.UUID;

public class CommunityUtils {
    //生成随机数
    public static String generateUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    //MD5加密
    public static String md5(String key) {
        if (StringUtils.isBlank(key)) return null;
        return DigestUtils.md5DigestAsHex(key.getBytes());
    }
}
```

配置文件`application.properties`：

```properties
server.port=8080
server.servlet.context-path=/community

spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.url=jdbc:mysql://localhost:3306/community?useSSL=false&serverTimezone=Asia/Shanghai
spring.datasource.username=root
spring.datasource.password=123456
mybatis.mapper-locations=classpath*:mapper/*.xml
mybatis.type-aliases-package=com.zwm.entity
mybatis.configuration.log-impl=org.apache.ibatis.logging.stdout.StdOutImpl
mybatis.configuration.use-generated-keys=true
mybatis.configuration.map-underscore-to-camel-case=true

logging.level.com.zwm=debug
logging.file.path=e:/java/community

spring.mail.host=smtp.163.com
spring.mail.port=465
spring.mail.username=xx@xx.com
spring.mail.password=xxxxxxxxxxxxxxxxxx
spring.mail.protocol=smtps
spring.mail.properties.mail.smtp.ssl.enable=true

community.path.domain=http://localhost:8080
```

`UserService.java`：

```java
package com.zwm.service;

import com.zwm.entity.User;
import com.zwm.util.CommunityConstant;

import java.util.Map;

public interface UserService {
    public abstract User findUserById(int id);

    //前端传递注册数据过来，后端接收
    public abstract Map<String, String> register(User user);

    //查询激活码与账户 ID 是否正确
    public abstract CommunityConstant activation(int id, String activationCode);
}
```

`UserServiceImpl.java`：

```java
package com.zwm.service.impl;

import com.zwm.dao.UserMapper;
import com.zwm.entity.User;
import com.zwm.service.UserService;
import com.zwm.util.CommunityConstant;
import com.zwm.util.CommunityUtils;
import com.zwm.util.MailServer;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static com.zwm.util.CommunityConstant.*;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;


    @Value("${community.path.domain}")
    private String domain;


    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Autowired
    private MailServer mailServer;

    @Autowired
    private TemplateEngine templateEngine;

    @Override
    public User findUserById(int id) {
        System.out.println("111");
        return userMapper.selectUserById(id);
    }

    @Override
    public Map<String, String> register(User user) {
        Map<String, String> map = new HashMap<>();
        if (user == null) throw new IllegalArgumentException("参数不能为空");
        //获取前端传递的用户名、密码和邮箱
        String username = user.getUsername();
        String password = user.getPassword();
        String email = user.getEmail();
        //map集合用于放置错误信息
        //用户名不能为空否则报错
        if (StringUtils.isBlank(username)) {
            map.put("usernameMsg", "用户名不能为空");
            return map;
        }
        //密码不能为空否则报错
        if (StringUtils.isBlank(password)) {
            map.put("passwordMsg", "密码不能为空");
            return map;
        }
        //邮箱不能为空否则报错
        if (StringUtils.isBlank(email)) {
            map.put("emailMsg", "邮箱不能为空");
            return map;
        }
        //验证账号，用户名不能是使用过的
        User selectUser = userMapper.selectUserByName(username);
        if (selectUser != null) {
            map.put("usernameMsg", "该账号已存在");
            return map;
        }
        selectUser = userMapper.selectUserByEmail(email);
        if (selectUser != null) {
            map.put("emailMsg", "该邮箱已被注册");
            return map;
        }
        //到这里都没有问题的话，表示可以正常创建用户了
        //User: id username password email headerUrl status type salt activeCode createTime
        //id 会自动生成，因为在 application.properties 添加了：mybatis.configuration.use-generated-keys=true 配置
        //username password email 都有了，但是这里 password 要存储到数据库中需要加盐加密
        //生成随机盐然后跟密码合并进行MD5加密 ---> 存储盐 存储密码
        String salt = CommunityUtils.generateUUID().substring(0, 5);
        user.setSalt(salt);
        user.setPassword(CommunityUtils.md5(user.getPassword() + user.getSalt()));
        //以当前时间为基准作为创建时间
        user.setCreateTime(new Date());
        //牛客网自带随机头像可以直接拿来使用，只需要换数字即可
        user.setHeaderUrl(String.format("https://images.nowcoder.com/head/%dt.png", new Random().nextInt(1000)));
        //设置用户 type 类型：0-普通 1-管理员 2-版主 ---> 设置为普通用户
        user.setType(0);
        //设置用户 status 状态：0-未激活 1-激活 ---> 设置为未激活用户
        user.setStatus(0);
        //设置激活码，发送激活邮件，这里需要获取到当前网站的域名，已经写在了 application.properties 里头
        //community.path.domain=http://localhost:8080
        user.setActivationCode(CommunityUtils.generateUUID());
        //向数据库中插入用户数据
        userMapper.insertUser(user);

        //发送激活邮件
        Context context = new Context();
        context.setVariable("email", user.getEmail());
        //http:localhost:8080/community/activation/{id}/code
        String url = domain + contextPath + "/activation/" + user.getId() + "/" + user.getActivationCode();
        context.setVariable("url", url);
        String content = templateEngine.process("/mail/activation", context);
        mailServer.sendMessage(user.getEmail(), "激活账号", content);
        return map;
    }

    @Override
    public CommunityConstant activation(int id, String activationCode) {
        User user = userMapper.selectUserById(id);
        if (user.getStatus() == 1) {
            return ACTIVATION_REPEAT;
        } else if (user.getStatus() == 0 && user.getActivationCode().equals(activationCode)) {
            //只有状态为 0 即未激活并且激活码跟链接相同才准许激活
            int result = userMapper.updateUserStatus(id);
            if(result == 1) return ACTIVATION_SUCCESS;
            else return ACTIVATION_FAILURE;
        } else {
            return ACTIVATION_FAILURE;
        }
    }
}
```

枚举类`CommunitConstant.java`：

```java
package com.zwm.util;

public enum CommunityConstant {
    //激活成功 重复激活 激活失败
    ACTIVATION_SUCCESS, ACTIVATION_REPEAT, ACTIVATION_FAILURE;
}
```

遇到的问题：拉取代码出现`Failed to configure a DataSource: 'url' attribute is not specified and no embedd`错误：

1. `ctrl + shift + alt + s`选择`resources`置于`root`类
2. 橘红色`pom`---> 右键`add as maven`

不小心将重要信息上传到了`github`，为了以防万一，将源代码拷贝一份然后：

1. 拉取项目：`git clone git@github.com:xxx@xxx.com`
2. 查看获取版本`hash`值：`git log`
3. 回退：`git reset --hard 9d933fda2d7ea1091b887d3d35078b02a8818c39`
4. `push`到`github`：`git push origin main --force`

#### 5.4.3 开发登录功能

##### 5.4.3.1 会话管理

1. `HTTP`是简单、无状态、可扩展的

   这是什么意思呢？指的就是请求和请求之间是没有关系的，比如你打开了一个鼠标链接下了一个订单然后又打开了一个键盘购物链接下单，但是最后面结算的时候，是看不到组合的一个订单的，因为这两次请求是毫无关系的。这就是说`HTTP`是无状态的，是协议本身决定的。那么如何解决呢？使用`HTTP COOKIE`就可以解决掉这个问题，建立起有状态的会话。【浏览器无法识别你自己是谁】

   注：`HTTP`本质是无状态的，借助`cookie`可以建立起有状态的会话

2. `HTTP COOKIE`保存在`HTTP`响应头中

3. `Cookie`是服务器发送到客户端也就是浏览器，并保存在服务端的一小块数据，

   浏览器下次访问该服务器的时候，会自动携带该数据一起带到服务器。

   可以使用代码实地演示，浏览器发送请求到服务器，服务器将`cookie`放到响应头里，设置好生存时间和生效范围，当浏览器再访问生效范围内的请求时就会携带这个`cookie`：

   ```java
   @ResponseBody
   @RequestMapping(value = "/getCookie", method = RequestMethod.GET)
   public String sendCookie(HttpServletResponse httpServletResponse) {
       Cookie cookie = new Cookie("code", CommunityUtils.generateUUID());
       cookie.setPath("/community/discussPost");
       cookie.setMaxAge(60 * 10);
       httpServletResponse.addCookie(cookie);
       return "add Cookie!";
   }
   ```

   再次访问相同的生效范围内的路径，会带着`cookie`给到服务器：

   ```java
   @RequestMapping(value = "/sendCookieToServer", method = RequestMethod.GET)
   @ResponseBody
   public String getCookie(@CookieValue(value = "code") String code) {
       return "get Cookie: code ---> " + code;
   }
   ```

   过程还是非常有趣的，但是`cookie`本身是由缺陷的：

   1. 不安全，你不能把密码等等很重要的东西放到客户端，万一被偷走损失可就大了
   2. 每次访问都带着`cookie`，如果里面有数据，还增加了数据量，对请求/访问等性能上也造成了很大的影响

4. 那么有什么办法可以解决`cookie`的问题呢？ ---> `session`，它是可以解决`http`无状态和`cookie`安全和性能的问题的，它不是`HTTP`协议里面的内容，而是`Java EE`中的内容，它是将重要的数据保存在服务端中，用于记录客户端的信息，数据存放在服务端确实更安全但是会增加服务端内存上的压力

   究竟使用`cookie`还是`session`，并不一定是一刀切的，若是不重要的数据，可以存放到`cookie`若是极其隐私的数据可以存到`session`中

   `session`需要结合`cookie`，每次传递给客户端都会在`cookie`放置`sessionId`，然后数据密码这些都放到服务器，每次客户端请求都带着有`sessionId`的`cookie`返还回来

5. `session`适用于单体架构的应用，如果是分布式就很不好解决了，都会带来各种各样的问题所以`session`在当前主流的分布式架构下用到的地方很少，共享`session`依靠的是`cookie`中存放：`JSESSIONID`，并且`SESSION`可以存放任意类型的数据

   ```java
    @RequestMapping(value = "/setSession", method = RequestMethod.GET)
    @ResponseBody
    public String testSession(HttpSession httpSession) {
        //Session 比 Cookie 强大，它可以设置任何类型的数据
        httpSession.setAttribute("id", 888);
        httpSession.setAttribute("name", "wow");
        return "Set Session Ok";
    }
    @RequestMapping(value = "/getSession", method = RequestMethod.GET)
    @ResponseBody
    public String testSession(@CookieValue(value = "JSESSIONID") String JSESSIONID) {
        return "Get Session OK: " + JSESSIONID;
    }
   ```

##### 5.4.3.2 验证码功能

1. 首先创建`KaptchaConfig`配置类，内置对象`Producer ---> kaptchaProducer`：

   ```java
   package com.zwm.config;
   
   import com.google.code.kaptcha.Producer;
   import com.google.code.kaptcha.impl.DefaultKaptcha;
   import com.google.code.kaptcha.util.Config;
   import org.springframework.context.annotation.Bean;
   import org.springframework.context.annotation.Configuration;
   
   import java.util.Properties;
   
   @Configuration
   public class KaptchaConfig {
       @Bean
       public Producer kaptchaProducer() {
           DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
           Properties properties = new Properties();
           //设置图片大小
           properties.setProperty("kaptcha.image.width", "100");
           properties.setProperty("kaptcha.image.height", "40");
           properties.setProperty("kaptcha.textproducer.font.size", "32");
           properties.setProperty("kaptcha.textproducer.font.color", "0,0,0");
           properties.setProperty("kaptcha.textproducer.char.string", "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYAZ");
           properties.setProperty("kaptcha.textproducer.char.length", "4");
           properties.setProperty("kaptcha.noise.impl", "com.google.code.kaptcha.impl.NoNoise");
           Config config = new Config(properties);
           defaultKaptcha.setConfig(config);
           return defaultKaptcha;
       }
   }
   ```

2. 在`LoginController.java`中使用验证码功能，利用`HttpServletResponse`返回图片，使用`HttpSession`存放数据：

   ```java
   //验证码功能 ---> 返回图片
   @RequestMapping(value = "/kaptcha", method = RequestMethod.GET)
   public void getKaptcha(HttpServletResponse httpServletResponse, HttpSession httpSession) {
       //生成验证码
       String text = kaptchaProducer.createText();
       BufferedImage bufferedImage = kaptchaProducer.createImage(text);
       //保存验证码文件到本地
       httpSession.setAttribute("kaptcha", text);
       //设置返回的格式
       httpServletResponse.setContentType("image/png");
       try {
           OutputStream outputStream = httpServletResponse.getOutputStream();
           ImageIO.write(bufferedImage, "png", outputStream);
       } catch (IOException e) {
           e.printStackTrace();
       }
   }
   ```

3. 结合页面`HTML`使用：

   ```javascript
   <script>
       function refresh_kaptcha() {
           var path = CONTEXT_PATH + "/kaptcha?p=" + Math.random();
           $("#kaptcha").attr("src", path);
       }
   </script>
   ```

   ```html
   <div class="col-sm-4">
       <img th:src="@{/kaptcha}" id="kaptcha" style="width:100px;height:40px;" class="mr-2"/>
       <a href="javascript:refresh_kaptcha();" class="font-size-12 align-bottom">刷新验证码</a>
   </div>
   ```

##### 5.4.3.3 登录功能

实现登录有一张用户登录凭证表：`login_ticket`，为什么要有这个表？因为这个凭证表可以在多个业务中连续地验证当前用户的登录状态，其实就是为了保持在同一个会话，登陆凭证信息存储在`login_ticket`数据库表中：【登录一次即保存登陆凭证，退出登录时将登录状态置为无效即可】

|   字段    |    类型     |              备注              |
| :-------: | :---------: | :----------------------------: |
|   `id`    |    `int`    |           主键、自增           |
| `user_id` |    `int`    |          登录用户`id`          |
| `ticket`  |  `varchar`  |      登陆凭证，随机字符串      |
| `status`  |    `int`    | 登录状态：`0`-有效 || `1`-无效 |
| `expired` | `timestamp` |            过期时间            |

1. 创建`login_ticket`表，当用户登录时就存储一条记录在这张表中：

```sql

DROP TABLE IF EXISTS `login_ticket`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `login_ticket` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `ticket` varchar(45) NOT NULL,
  `status` int(11) DEFAULT '0' COMMENT '0-有效; 1-无效;',
  `expired` timestamp NOT NULL,
  PRIMARY KEY (`id`),
  KEY `index_ticket` (`ticket`(20))
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
```

2. 创建`LoginTicket.java`实体类：

   ```java
   package com.zwm.entity;
   
   import lombok.AllArgsConstructor;
   import lombok.Data;
   import lombok.NoArgsConstructor;
   
   import java.util.Date;
   
   @Data
   @AllArgsConstructor
   @NoArgsConstructor
   public class LoginTicket {
       private int id;
       private int user_id;//用户id
       private String ticket;//凭证
       private int status;//状态：0-无效 1-有效
       private Date expired;//过期时间
   
       @Override
       public String toString() {
           return "LoginTicket{" +
                   "id=" + id +
                   ", user_id=" + user_id +
                   ", ticket='" + ticket + '\'' +
                   ", status=" + status +
                   ", expired=" + expired +
                   '}';
       }
   }
   ```

3. 创建`LoginTicketMapper.interface`：【这里使用新的技术姿势写`SQL` --> 注解】

   **<font color="red">注意：这里用的是`SQL`注解开发，虽然在配置文件里头已经配置了`mybatis.configuration.useGeneratedKeys=true`但是注解跟配置是两回事，配置对注解形式的`SQL`是不生效的</font>**

   ```java
   package com.zwm.dao;
   
   import com.zwm.entity.LoginTicket;
   import org.apache.ibatis.annotations.Insert;
   import org.apache.ibatis.annotations.Options;
   import org.apache.ibatis.annotations.Select;
   import org.apache.ibatis.annotations.Update;
   
   public interface LoginTicketMapper {
       //插入登录凭证
       @Insert({
               "insert into login_ticket(user_id, ticket, status, expired) values(#{user_id}, #{ticket}, #{status}, #{expired})"
       })
       @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
       public abstract int insertLoginTicket(LoginTicket loginTicket);
   
       //查询登陆凭证
       @Select({
               "select id, user_id, ticket, status, expired from login_ticket where ticket = #{ticket}"
       })
       public abstract LoginTicket selectLoginTicketByTicket(String ticket);
   
   
       //更改登陆凭证状态
       @Update({
               "update login_ticket set status = #{status} where ticket = #{ticket}"
       })
       public abstract int updateStatusByLoginTicket(String ticket, int status);
   }
   ```

4. 制作`LoginController`通过从前端获取`code`与服务器`session`所保存的值作比较，比较验证码是否相同，验证码为空或者`session`没有存放验证码【验证码没显示，传输失败】、传递给服务器的验证码为空的情况都一律返回错误信息并且返回到登录页面

   ```java
   //登录功能
   @RequestMapping(value = "/login", method = RequestMethod.POST)
   public String userLogin(Model model, HttpServletResponse httpServletResponse, HttpSession httpSession, String username, String password, String code, boolean rememberMe) {
       //参数分别为：数据 + 响应类型 + 会话 + 用户名 + 密码 + 验证码 + 是否记住我
       //session保存在服务器，内置属性：kaptcha
       String kaptcha = (String) httpSession.getAttribute("kaptcha");
       //如果验证码为空或者不匹配直接返回到登录页面
       if (StringUtils.isBlank(kaptcha) || StringUtils.isBlank(code) || !kaptcha.equals(code)) {
           model.addAttribute("codeMsg", "验证码不正确!");
           return "/site/login";
       }   
       return "/index";
   }
   ```

5. 验证码比对通过以后，验证用户名和密码。 ---> `UserService + UserServiceImpl`

   1. 包括验证用户名是否为空

      ```java
      if(StringUtils.isBlank(username)) {
          map.put("usernameMsg", "用户名不能为空！");
          return map;
      }
      ```

   2. 密码是否为空

      ```java
      if(StringUtils.isBlank(password)) {
          map.put("passwordMsg", "密码不能为空！");
          return map;
      }
      ```

   3. 该用户名的用户是否存在在数据库中【查询语句】

      ```java
      User user = userMapper.selectUserByName(username);
      //如果 user 查询没有直接返回
      if (user == null) {
      	map.put("usernameMsg", "该账号不存在!");
      	return map;
      }
      ```

   4. 该用户名的用户是否已经激活，未激活不可使用即`status = 0`无法使用

      ```java
       //未激活账号无法使用
       if (user.getStatus() == 1) {
           map.put("usernameMsg", "未激活账号无法使用！");
           return map;
       }
      ```

   5. 验证密码是否正确

      ```java
       //验证密码是否正确
       if (user.getPassword().equals(CommunityUtils.md5(password + user.getSalt()))) {
           map.put("passwordMsg", "密码不正确！");
           return map;
       }
      ```

6. 前面验证通过表明账号可以正常使用 ---> 制作登录凭证

   ```java
   //前面都顺利执行过来表示用户可以正常登录使用，制作登录凭证
   LoginTicket loginTicket = new LoginTicket();
   loginTicket.setUser_id(user.getId());
   loginTicket.setTicket(CommunityUtils.generateUUID());
   loginTicket.setStatus(1);
   //生存时间根据是否记住决定 ---> 这里由 LoginController 完成传递过来，Service 这边直接赋值即可
   loginTicket.setExpired(System.currentTimeMillis() + expiredSeconds * 1000);
   //创建登陆凭证记录存储到数据库中
   loginTicketMapper.insertLoginTicket(loginTicket);
   //前端 cookie 需要保存登录凭证，所以需要使用 map 传递回去
   map.put("loginTicket", loginTicket.getTicket());
   return map;
   ```

7. 在`UserServiceImpl.java`中制作登出功能 ---> 修改当前状态为无效 ---> 1

   ```java
   //用户登出 ---> 设置当前票据的状态为无效 0-有效 1-无效
   public void logout(String ticket) {
   	loginTicketMapper.updateStatusByLoginTicket(ticket, 1);
   }
   ```

8. `LoginController.java`：

   ```java
   //登录功能
   @RequestMapping(value = "/login", method = RequestMethod.POST)
   public String userLogin(Model model, HttpServletResponse httpServletResponse, HttpSession httpSession, String username, String password, String code, boolean rememberMe) {
       //参数分别为：数据 + 响应类型 + 会话 + 用户名 + 密码 + 验证码 + 是否记住我
       //session保存在服务器，内置属性：kaptcha
       String kaptcha = (String) httpSession.getAttribute("kaptcha");
       //如果验证码为空或者不匹配直接返回到登录页面
       if (StringUtils.isBlank(kaptcha) || StringUtils.isBlank(code) || !kaptcha.equals(code)) {
           model.addAttribute("codeMsg", "验证码不正确!");
           return "/site/login";
       }
       //查询用户名和密码匹配的用户信息 ---> UserService
       //设置存活时间：要么半天要么100天
       int expiredSeconds = rememberMe ? REMEMBER_EXPIRED_SECONDS : DEFAULT_EXPIRED_SECONDS;
       Map<String, String> map = userService.login(username, password, expiredSeconds);
       //验证map，如果包含`ticket`表明账户验证通过，否则表示不通过
       if (map.containsKey("ticket")) {
           Cookie cookie = new Cookie("ticket", map.get("ticket").toString());
           cookie.setPath(contextPath);
           cookie.setMaxAge(expiredSeconds);
           httpServletResponse.addCookie(cookie);
           //重定向到首页
           return "redirect:/index";
       } else {
           model.addAttribute("usernameMsg", map.get("usernameMsg"));
           model.addAttribute("passwordMsg", map.get("passwordMsg"));
           return "/site/login";
       }
   }
   ```

9. 常量类`CommunityConstantTwo.java`：

   ```java
   package com.zwm.util;
   
   public interface CommunityConstantTwo {
       //默认状态的登录凭证的超时时间 12 个小时
       int DEFAULT_EXPIRED_SECONDS = 3600 * 12;
   
       //记住状态的登录凭证超时时间 100天
       int REMEMBER_EXPIRED_SECONDS = 3600 * 24 * 100;
   }
   ```

10. 修改`login.html`文件：

    ```html
    <!doctype html>
    <html lang="en" xmlns:th="http://www.thymeleaf.org">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <link rel="icon" href="https://static.nowcoder.com/images/logo_87_87.png"/>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" crossorigin="anonymous">
        <link rel="stylesheet" th:href="@{/css/global.css}" />
        <link rel="stylesheet" th:href="@{/css/login.css}" />
        <title>牛客网-登录</title>
    </head>
    <body>
    <div class="nk-container">
        <!-- 头部 -->
        <header class="bg-dark sticky-top" th:replace="index::header">
            <div class="container">
                <!-- 导航 -->
                <nav class="navbar navbar-expand-lg navbar-dark">
                    <!-- logo -->
                    <a class="navbar-brand" href="#"></a>
                    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                        <span class="navbar-toggler-icon"></span>
                    </button>
                    <!-- 功能 -->
                    <div class="collapse navbar-collapse" id="navbarSupportedContent">
                        <ul class="navbar-nav mr-auto">
                            <li class="nav-item ml-3 btn-group-vertical">
                                <a class="nav-link" href="../index.html">首页</a>
                            </li>
                            <li class="nav-item ml-3 btn-group-vertical">
                                <a class="nav-link position-relative" href="letter.html">消息<span class="badge badge-danger">12</span></a>
                            </li>
                            <li class="nav-item ml-3 btn-group-vertical">
                                <a class="nav-link" href="register.html">注册</a>
                            </li>
                            <li class="nav-item ml-3 btn-group-vertical">
                                <a class="nav-link" href="login.html">登录</a>
                            </li>
                            <li class="nav-item ml-3 btn-group-vertical dropdown">
                                <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                    <img src="http://images.nowcoder.com/head/1t.png" class="rounded-circle" style="width:30px;"/>
                                </a>
                                <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                                    <a class="dropdown-item text-center" href="profile.html">个人主页</a>
                                    <a class="dropdown-item text-center" href="setting.html">账号设置</a>
                                    <a class="dropdown-item text-center" href="login.html">退出登录</a>
                                    <div class="dropdown-divider"></div>
                                    <span class="dropdown-item text-center text-secondary">nowcoder</span>
                                </div>
                            </li>
                        </ul>
                        <!-- 搜索 -->
                        <form class="form-inline my-2 my-lg-0" action="search.html">
                            <input class="form-control mr-sm-2" type="search" aria-label="Search" />
                            <button class="btn btn-outline-light my-2 my-sm-0" type="submit">搜索</button>
                        </form>
                    </div>
                </nav>
            </div>
        </header>
    
        <!-- 内容 -->
        <div class="main">
            <div class="container pl-5 pr-5 pt-3 pb-3 mt-3 mb-3">
                <h3 class="text-center text-info border-bottom pb-3">登&nbsp;&nbsp;录</h3>
                <form class="mt-5" method="post" th:action="@{/login}">
                    <div class="form-group row">
                        <label for="username" class="col-sm-2 col-form-label text-right">账号:</label>
                        <div class="col-sm-10">
                            <input type="text" th:class="|form-control ${usernameMsg!=null?'is-invalid':''}|"
                                   th:value="${param.username}"
                                   id="username" name="username" placeholder="请输入您的账号!" required>
                            <div class="invalid-feedback" th:text="${usernameMsg}">
                                该账号不存在!
                            </div>
                        </div>
                    </div>
                    <div class="form-group row mt-4">
                        <label for="password" class="col-sm-2 col-form-label text-right">密码:</label>
                        <div class="col-sm-10">
                            <input type="password" th:class="|form-control ${passwordMsg!=null?'is-invalid':''}|"
                                   th:value="${param.password}"
                                   id="password" name="password" placeholder="请输入您的密码!" required>
                            <div class="invalid-feedback" th:text="${passwordMsg}">
                                密码长度不能小于8位!
                            </div>
                        </div>
                    </div>
                    <div class="form-group row mt-4">
                        <label for="verifycode" class="col-sm-2 col-form-label text-right">验证码:</label>
                        <div class="col-sm-6">
                            <input type="text" th:class="|form-control ${codeMsg!=null?'is-invalid':''}|"
                                   id="verifycode" name="code" placeholder="请输入验证码!">
                            <div class="invalid-feedback" th:text="${codeMsg}">
                                验证码不正确!
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <img th:src="@{/kaptcha}" id="kaptcha" style="width:100px;height:40px;" class="mr-2"/>
                            <a href="javascript:refresh_kaptcha();" class="font-size-12 align-bottom">刷新验证码</a>
                        </div>
                    </div>
                    <div class="form-group row mt-4">
                        <div class="col-sm-2"></div>
                        <div class="col-sm-10">
                            <input type="checkbox" id="remember-me" name="rememberme"
                                   th:checked="${param.rememberme}">
                            <label class="form-check-label" for="remember-me">记住我</label>
                            <a href="forget.html" class="text-danger float-right">忘记密码?</a>
                        </div>
                    </div>
                    <div class="form-group row mt-4">
                        <div class="col-sm-2"></div>
                        <div class="col-sm-10 text-center">
                            <button type="submit" class="btn btn-info text-white form-control">立即登录</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    
        <!-- 尾部 -->
        <footer class="bg-dark">
            <div class="container">
                <div class="row">
                    <!-- 二维码 -->
                    <div class="col-4 qrcode">
                        <img src="https://uploadfiles.nowcoder.com/app/app_download.png" class="img-thumbnail" style="width:136px;" />
                    </div>
                    <!-- 公司信息 -->
                    <div class="col-8 detail-info">
                        <div class="row">
                            <div class="col">
                                <ul class="nav">
                                    <li class="nav-item">
                                        <a class="nav-link text-light" href="#">关于我们</a>
                                    </li>
                                    <li class="nav-item">
                                        <a class="nav-link text-light" href="#">加入我们</a>
                                    </li>
                                    <li class="nav-item">
                                        <a class="nav-link text-light" href="#">意见反馈</a>
                                    </li>
                                    <li class="nav-item">
                                        <a class="nav-link text-light" href="#">企业服务</a>
                                    </li>
                                    <li class="nav-item">
                                        <a class="nav-link text-light" href="#">联系我们</a>
                                    </li>
                                    <li class="nav-item">
                                        <a class="nav-link text-light" href="#">免责声明</a>
                                    </li>
                                    <li class="nav-item">
                                        <a class="nav-link text-light" href="#">友情链接</a>
                                    </li>
                                </ul>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col">
                                <ul class="nav btn-group-vertical company-info">
                                    <li class="nav-item text-white-50">
                                        公司地址：北京市朝阳区大屯路东金泉时代3-2708北京牛客科技有限公司
                                    </li>
                                    <li class="nav-item text-white-50">
                                        联系方式：010-60728802(电话)&nbsp;&nbsp;&nbsp;&nbsp;admin@nowcoder.com
                                    </li>
                                    <li class="nav-item text-white-50">
                                        牛客科技©2018 All rights reserved
                                    </li>
                                    <li class="nav-item text-white-50">
                                        京ICP备14055008号-4 &nbsp;&nbsp;&nbsp;&nbsp;
                                        <img src="http://static.nowcoder.com/company/images/res/ghs.png" style="width:18px;" />
                                        京公网安备 11010502036488号
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </footer>
    </div>
    <script src="https://code.jquery.com/jquery-3.3.1.min.js" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" crossorigin="anonymous"></script>
    <script th:src="@{/js/global.js}"></script>
    <script>
        function refresh_kaptcha() {
            var path = CONTEXT_PATH + "/kaptcha?p=" + Math.random();
            $("#kaptcha").attr("src", path);
        }
    </script>
    </body>
    </html>
    ```

11. 退出功能`LoginController.java`

    ```java
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String userLogout() {
        return "/site/index";
    }
    ```

12. `UserServiceImpl.java`

    ```java
    //用户登出 ---> 设置当前票据的状态为无效 0-有效 1-无效
    public void logout(String ticket) {
        loginTicketMapper.updateStatusByLoginTicket(ticket, 1);
    }
    ```

##### 5.4.3.4 退出功能

##### 5.4.3.5 忘记密码

##### 5.4.3.6 显示登录信息

需要判断登录与否显示首页展示的内容：---> 拦截器 ---> 要完成的功能

1. 如果是未登录的就展示首页、登录、注册
2. 如果是已登录的就展示首页、消息、个人信息

学习拦截器：【因为拦截器是拦截请求的，所以也是属于表现层上面的内容】

**<font color="red">这里重点说说 `preHandle`、`postHandle`、`afterCompletion`三个执行时机，第一个在`Controller`请求之前执行，中间的在`Controller`请求之后执行，最后一个在`TemplateEngine`之后执行。</font>**

1. 定义拦截器，实现`HandlerInterceptor`，可以在`Controller`执行之前，`Controller`执行之后，`ThemplateEngine`之后执行

   ```java
   package com.zwm.controller.interceptor;
   
   import org.slf4j.Logger;
   import org.slf4j.LoggerFactory;
   import org.springframework.web.servlet.HandlerInterceptor;
   import org.springframework.web.servlet.ModelAndView;
   
   import javax.servlet.http.HttpServletRequest;
   import javax.servlet.http.HttpServletResponse;
   
   public class AlphaInterceptor implements HandlerInterceptor {
   
       private static Logger logger = LoggerFactory.getLogger(AlphaInterceptor.class);
   
       @Override
       public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
           logger.debug("preHandle: " + handler.toString());
           return true;
       }
   
       @Override
       public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
           logger.debug("postHandle: " + handler.toString());
       }
   
       @Override
       public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
           logger.debug("afterCompletion: " + handler.toString());
       }
   }
   ```

2. 配置拦截器，配置拦截路径：`WebMVCConfig.java`

   ```java
   package com.zwm.config;
   
   import com.zwm.controller.interceptor.AlphaInterceptor;
   import org.springframework.beans.factory.annotation.Autowired;
   import org.springframework.context.annotation.Configuration;
   import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
   import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
   
   @Configuration
   public class WebMVCConfig implements WebMvcConfigurer {
   
       @Autowired
       private AlphaInterceptor alphaInterceptor;
   
       @Override
       public void addInterceptors(InterceptorRegistry registry) {
           registry.addInterceptor(alphaInterceptor).addPathPatterns("/register", "/login").excludePathPatterns("/**/*.css", "/**/*.js", "/**/*.jpg", "/**/*.png", "/**/*.jpeg");
       }
   }
   ```

   <hr/>

   拦截器的应用：

   - 在请求开始时查询登录用户
   - 在本次请求中持有用户数据
   - 在模板视图上显示用户数据
   - 在请求结束时清理用户数据

   ![](https://img-blog.csdnimg.cn/9a4926605ca3465e82da081ef618a455.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBAQ3JBY0tlUi0x,size_20,color_FFFFFF,t_70,g_se,x_16)

   可以发现这套逻辑请求是每次客户端发送请求给服务端的时候都需要验证的【只要登录了】，因为是反复都要使用的，什么东西可以在一开始就开始做这些工作呢？---> 拦截器

3. 创建`LoginInterceptor.java`

   - 创建`CookieUtils`工具，用于返回请求头中的`cookie`中的`ticket`

     ```java
     package com.zwm.util;
     
     import javax.servlet.http.Cookie;
     import javax.servlet.http.HttpServletRequest;
     
     public class CookieUtils {
         public static String getCookieValue(HttpServletRequest httpServletRequest, String cookieName) {
             //根据请求request的cookies ---> 封装成为一套工具 ---> CookieUtils
             Cookie[] cookies = httpServletRequest.getCookies();
             if (cookies != null) {
                 for (Cookie cookie : cookies) {
                     if (cookie.getName().equals(cookieName)) {
                         //获取然后直接返回当前凭证
                         return cookie.getValue();
                     }
                 }
             }
             return null;
         }
     }
     ```

   - 创建`HostHolder.java`工具类用于将登录用户存储在每个请求中：

     ```java
     package com.zwm.util;
     
     import com.zwm.entity.User;
     import org.springframework.stereotype.Component;
     
     @Component
     public class HostHolder {
         private ThreadLocal<User> users = new ThreadLocal<User>();
     
         public User getUser() {
             return users.get();
         }
     
         public void setUser(User user) {
             users.set(user);
         }
     
         public void clear() {
             users.remove();
         }
     }
     ```

   - `LoginInterceptor`拦截登陆器最终版：

     ```java
     package com.zwm.controller.interceptor;
     
     import com.zwm.entity.LoginTicket;
     import com.zwm.entity.User;
     import com.zwm.service.impl.UserServiceImpl;
     import com.zwm.util.CookieUtils;
     import com.zwm.util.HostHolder;
     import org.springframework.beans.factory.annotation.Autowired;
     import org.springframework.stereotype.Component;
     import org.springframework.web.servlet.HandlerInterceptor;
     import org.springframework.web.servlet.ModelAndView;
     
     import javax.servlet.http.HttpServletRequest;
     import javax.servlet.http.HttpServletResponse;
     import java.util.Date;
     
     @Component
     public class LoginInterceptor implements HandlerInterceptor {
     
         @Autowired
         private UserServiceImpl userService;
     
         @Autowired
         private HostHolder hostHolder;
     
         @Override
         public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
             //该方法作用：将用户存放到整个请求当中
             //获取cookie中的ticket进而直接获取 LoginTicket 对象
             LoginTicket loginTicket = userService.selectLoginTicketByTicket(CookieUtils.getCookieValue(request, "ticket"));
             //在 loginTicket 不为空并且登陆状态有效以及生存时间在当前时间之后情况下，说明有登录用户存在
             if (loginTicket != null && loginTicket.getStatus() == 0 && loginTicket.getExpired().after(new Date())) {
                 //根据用户凭证查询当前用户
                 User user = userService.findUserById(loginTicket.getUserId());
                 //现在考虑到服务器是并发的，需要将当前的用户一并发送给服务器
                 //只要这个线程还在，请求还在那么这个用户就一直还在
                 hostHolder.setUser(user);
             }
             return true;
         }
     
         @Override
         public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
             //获取当前线程的用户
             User loginUser = hostHolder.getUser();
             if (loginUser != null && !modelAndView.isEmpty()) {
                 modelAndView.addObject("loginUser", loginUser);
             }
         }
     
         @Override
         public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
             //模板展示之后将数据清除掉
             hostHolder.clear();
         }
     }
     ```

4. 配置类中加入拦截器 ---> `com.zwm.config.WebMVCConfig`

   ```java
   package com.zwm.config;
   
   import com.zwm.controller.interceptor.AlphaInterceptor;
   import com.zwm.controller.interceptor.LoginInterceptor;
   import org.springframework.beans.factory.annotation.Autowired;
   import org.springframework.context.annotation.Configuration;
   import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
   import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
   
   @Configuration
   public class WebMVCConfig implements WebMvcConfigurer {
   
       @Autowired
       private AlphaInterceptor alphaInterceptor;
   
       @Autowired
       private LoginInterceptor loginInterceptor;
   
       @Override
       public void addInterceptors(InterceptorRegistry registry) {
           //registry.addInterceptor(alphaInterceptor).addPathPatterns("/register", "/login").excludePathPatterns("/**/*.css", "/**/*.js", "/**/*.jpg", "/**/*.png", "/**/*.jpeg");
           registry.addInterceptor(loginInterceptor).excludePathPatterns("/**/*.css", "/**/*.js", "/**/*.jpg", "/**/*.png", "/**/*.jpeg");
       }
   }
   ```

3. 对`index.html`做一定的修改，因为请求中涵盖了当前用户，如果处于登录状态的用户可以看到首页消息和个人，如果看不到，那就显示首页注册和消息：

   ```html
   <li class="nav-item ml-3 btn-group-vertical">
       <a class="nav-link" th:href="@{/index}">首页</a>
   </li>
   <li class="nav-item ml-3 btn-group-vertical" th:if="${loginUser!=null}">
       <a class="nav-link position-relative" href="site/letter.html">消息<span class="badge badge-danger">12</span></a>
   </li>
   <li class="nav-item ml-3 btn-group-vertical" th:if="${loginUser==null}">
       <a class="nav-link" th:href="@{/register}">注册</a>
   </li>
   <li class="nav-item ml-3 btn-group-vertical" th:if="${loginUser==null}">
       <a class="nav-link" th:href="@{/login}">登录</a>
   </li>
   <li class="nav-item ml-3 btn-group-vertical dropdown" th:if="${loginUser!=null}">
   ```

**<font color="red">对于`ThreadLocal`特此说明：它采用线程隔离的方式存储数据，可以避免多线程之间数据访问的冲突，常用`set get remove`方法处理数据</font>**

### 5.5 账号设置模块

#### 5.5.1 上传头像功能

> 请求：必须是 `POST` 请求
>
> 表单：`enctype = "multipart/form-data"`
>
> `Spring MVC`：通过`MultipartFile`处理上传文件 ---> 1. 存到服务器硬盘 2. 存储到云服务器
>
> 一个`MultipartFile`只能封装一个文件

开发步骤：

- 访问账号设置页面

  - 新建`UserController.java`使得用户访问时可以访问到：

    ```java
    package com.zwm.controller;
    
    import org.springframework.stereotype.Controller;
    import org.springframework.web.bind.annotation.RequestMapping;
    import org.springframework.web.bind.annotation.RequestMethod;
    
    @Controller
    @RequestMapping(value = "/user")
    public class UserController {
    
        @RequestMapping(value = "/setting", method = RequestMethod.GET)
        public String getSettingPage() {
            return "/site/setting";
        }
    }
    ```

  - 设置`index.html`文件：

    ![](https://img-blog.csdnimg.cn/58d990d84aa248d0b42ebbd3e85cd7d3.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBAQ3JBY0tlUi0x,size_20,color_FFFFFF,t_70,g_se,x_16)

  - 登录用户，然后点击账号设置显示页面如下：

    ![](https://img-blog.csdnimg.cn/54b2b3f717f94fec88757f7af10072c3.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBAQ3JBY0tlUi0x,size_20,color_FFFFFF,t_70,g_se,x_16)

- 上传头像

  - 配置文件配置上传的资源存放的位置

    ```properties
    community.path.update=e:/Java/community/update
    ```

  - 用户选择资源文件后点击立即上传 ---> 修改用户头像`headerUrl` ---> 请求路径：`/user/upload` ---> `UserController.java` ---> 更新头像`headerURL`成功后重定向到首页

    ```java
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String uploadHeader(MultipartFile uploadImage, Model model) {
        return "redirect:/index";
    }
    ```

  - 如果上传文件为空就返回：

    ```java
    //如果上传文件为空就返回
    if (uploadImage.isEmpty()) {
        model.addAttribute("error", "您还没有选择图片!");
        return "/site/setting";
    }
    ```

  - 上传，但是发现上传的文件一直为`null`，原来是前端`setting.html`页面和`UserController`的`MultipartFile`中的变量名不同 ---> 如图所示：

    ![](https://img-blog.csdnimg.cn/a013b338eb66455ea2f86ddc048b82fd.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBAQ3JBY0tlUi0x,size_20,color_FFFFFF,t_70,g_se,x_16)

    ![](https://img-blog.csdnimg.cn/48d3d26fb5d449d8bee789caa24c9589.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBAQ3JBY0tlUi0x,size_20,color_FFFFFF,t_70,g_se,x_16)

    解决办法：更改任一名称即可

  - 获取上传文件的全称紧接着获取文件名后缀，为了放置上传重复的名称，这里给存放到本地的图片一个随机变量名：

    ```java
    //获取图片文件名
    String fileName = headerImage.getOriginalFilename();
    //获取图片文件名后缀
    String suffix = fileName.substring(fileName.lastIndexOf('.'));
    //检测后缀是否符合图片格式
    if(!StringUtils.isBlank(suffix) || ".jpg".equals(suffix) || ".png".equals(suffix) || ".jpeg".equals(suffix)) {
        String uploadFileName = CommunityUtils.generateUUID() + suffix;
        return "redirect:/index";
    } else {
        model.addAttribute("error", "文件格式不正确！请选择：jpg png jpeg 格式文件！");
        return "/site/setting";
    }
    ```

  - 存储文件到本地服务器中

    ```java
     private static Logger logger = LoggerFactory.getLogger(UserController.class);
    
     @Value("${community.path.update}")
     private String uploadPath;
    
    
    String uploadFileName = CommunityUtils.generateUUID() + suffix;
     //存放文件到服务器中
     File uploadToServerFile = new File(uploadPath + uploadFileName);
     try {
         headerImage.transferTo(uploadToServerFile);
     } catch (IOException e) {
         logger.error("上传文件失败: " + e.getMessage());
         throw new RuntimeException("上传文件失败,服务器发生异常!", e);
     }
     return "redirect:/index";
    ```

    可以查看后台是否存放到图片 ---> 存放成功：

    ![](https://img-blog.csdnimg.cn/fcd8d848ab094430bf880448562e43a8.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBAQ3JBY0tlUi0x,size_20,color_FFFFFF,t_70,g_se,x_16)

  - 更新用户头像`URL`信息 ---> `UserService.java`：

    ```java
    //跟新用户头像URL地址
    //1. 获取当前线程的用户
    User user = hostHolder.getUser();
    int id = user.getId();
    //2. 制作当前上传头像的URL地址
    //http://localhost/community/user/header/xxx.jpg
    String headerUrl = domain + contextPath + "/user/header/" + uploadFileName;
    //3. 更新用户的头像URL地址
    userService.updateUserHeaderUrl(id, headerUrl);
    return "redirect:/index";
    ```

- 获取头像

  - 采用`RESTFUL`方式 + 使用`HttpServletResponse`方式返回图片

    ```java
    //获取头像，访问头像URL地址 ---> RESTFUL 形式 ---> 从 response 中返回回去
    @RequestMapping(value = "/header/{fileName}", method = RequestMethod.GET)
    public void getUserHeader(@PathVariable(value = "fileName") String fileName, HttpServletResponse httpServletResponse) {
        OutputStream outputStream = null;
        FileInputStream fileInputStream = null;
        try {
            //获取后缀名
            httpServletResponse.setContentType("image/" + fileName.substring(fileName.lastIndexOf(".") + 1));
            outputStream = httpServletResponse.getOutputStream();
            fileInputStream = new FileInputStream(uploadPath + fileName);
            int b = 0;
            byte[] bytes = new byte[1024];
            while ((b = fileInputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, b);
            }
        } catch (FileNotFoundException e) {
            logger.error("读取头像失败: " + e.getMessage());
        } catch (IOException e) {
            logger.error("读取头像失败: " + e.getMessage());
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    logger.error("读取头像失败: " + e.getMessage());
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    logger.error("读取头像失败: " + e.getMessage());
                }
            }
        }
    }
    ```

  - 修改`index.html`页面，显示用户头像：

    ![](https://img-blog.csdnimg.cn/adf9454782914a718ee2ba8853cbabb5.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBAQ3JBY0tlUi0x,size_20,color_FFFFFF,t_70,g_se,x_16)

    ![](https://img-blog.csdnimg.cn/8c08eb5445104de28614df9dacd09ba9.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBAQ3JBY0tlUi0x,size_20,color_FFFFFF,t_70,g_se,x_16)

#### 5.5.2 修改密码功能

### 5.6 验证登陆状态

- 只有登陆了以后才可以访问其它路径，否则只能看到首页/注册/登录页面，访问其它页面将直接跳转到登陆页面 ---> 可想而知需要使用拦截器 ---> 保障安全

  例子：比如说`http://localhost/community/user/setting`这个按理说没有登录的时候是无法访问的，但是现在却可以直接访问，这明显有问题，所以需要做验证，验证当前是否已经登录，登录了才能访问一些页面

- 可以跟注解相结合，只有在有注解的请求路径才做拦截【自定义注解方式】

  - 制作注解：`annotation/LoginRequired.java`

    ```java
    package com.zwm.annotation;
    
    import java.lang.annotation.ElementType;
    import java.lang.annotation.Retention;
    import java.lang.annotation.RetentionPolicy;
    import java.lang.annotation.Target;
    
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface LoginRequired {
        //注解只有在运行时状态的方法有效
    }
    ```

  - 给方法增添注解，目前只有：`/setting`和`/upload`需要在登录状态时才可以访问

    ![](https://img-blog.csdnimg.cn/d2758228cecd4895b497497fcaca6908.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBAQ3JBY0tlUi0x,size_20,color_FFFFFF,t_70,g_se,x_16)

  - 拦截器拦截带有`LoginRequired`注解的方法 ---> 通过反射获取

    ```java
    package com.zwm.controller.interceptor;
    
    import com.zwm.annotation.LoginRequired;
    import com.zwm.util.HostHolder;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.stereotype.Component;
    import org.springframework.web.method.HandlerMethod;
    import org.springframework.web.servlet.HandlerInterceptor;
    
    import javax.servlet.http.HttpServletRequest;
    import javax.servlet.http.HttpServletResponse;
    import java.lang.reflect.Method;
    
    @Component
    public class LoginRequiredInterceptor implements HandlerInterceptor {
    
        @Autowired
        private HostHolder hostHolder;
    
        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
            //判断当前请求的是不是一个方法【避免静态资源】
            if (handler instanceof HandlerMethod) {
                HandlerMethod handlerMethod = (HandlerMethod) handler;
                Method method = handlerMethod.getMethod();
                LoginRequired loginRequired =  method.getAnnotation(LoginRequired.class);
                //如果该注解存在但是却没有登陆，需要重定向到登录页面
                if(loginRequired != null && hostHolder.getUser() == null) {
                    response.sendRedirect( request.getContextPath() + "/login");
                    return false;
                }
            }
            return true;
        }
    }
    ```

  - 配置拦截器使其生效

    ![](https://img-blog.csdnimg.cn/3d07489e44684dbf966eed43cf2be56b.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBAQ3JBY0tlUi0x,size_20,color_FFFFFF,t_70,g_se,x_16)

  - 验证，浏览器访问：`http://localhost/community/user/setting`会跳转到`http://localhost/community/login`页面

### 5.7 开发社区核心功能

#### 5.7.1 过滤敏感词

使用前缀树算法过滤敏感词：<a href="https://www.cnblogs.com/hd-zg/p/10591065.html">前缀树算法详解</a> ---> `SensitiveFilter.java`创建过滤敏感词类

1. 创建前缀树节点类 ---> `TreeNode` ---> 因为是用于内部所以这里做成一个内部类即可，不用创建新的公共类

   ```java
   package com.zwm.util;
   
   import java.util.HashMap;
   import java.util.Map;
   
   public class SensitiveFilter {
       private class TrieNode {
           //是否为最后一个字符
           private boolean isKeywordEnd = false;
           //子节点集合
           Map<Character, TrieNode> subNodes = new HashMap<>();
   
           //获取是否为最后一个字符
           public boolean isKeywordEnd() {
               return isKeywordEnd;
           }
   
           //设置是否为最后一个字符
           public void setKeywordEnd(boolean isKeywordEnd) {
               this.isKeywordEnd = isKeywordEnd;
           }
   
           //添加子节点
           public void addSubNode(Character character, TrieNode subNode) {
               subNodes.put(character, subNode);
           }
   
           //获取子节点
           public TrieNode getSubNode(Character character) {
               return subNodes.get(character);
           }
       }
   }
   ```
   
2. 构建前缀树

   ```java
   @PostConstruct
   public void init() {
       try (
               //获取敏感词列表信息
               InputStream inputStream = this.getClass().getClassLoader().ge
               BufferedReader bufferedReader = new BufferedReader(new InputS
       ) {
           //读取敏感词 ---> 字符流
           String keyword;
           while ((keyword = bufferedReader.readLine()) != null) {
               //添加到前缀树当中 ---> 根据敏感词列表初始化前缀树
               this.addKeyword(keyword);
           }
       } catch (Exception e) {
           logger.error("加载敏感词文件失败：" + e.getMessage());
       }
   }
   //将字符添加到前缀树中
   public void addKeyword(String keyword) {
       //每来一个字符串都将其挂在到根节点
       TrieNode tempNode = rootNode;
       //遍历字符串中的字符，将其挂载到前缀树上
       for (int i = 0; i < keyword.length(); i++) {
           char c = keyword.charAt(i);
           //判断当前字符是否已经存在在该节点上，如果不存在直接挂载
           TrieNode subNode = tempNode.getSubNode(c);
           if (subNode == null) {
               subNode = new TrieNode();
               tempNode.addSubNode(c, subNode);
           }
           //遍历下一个节点
           tempNode = subNode;
           //如果是最后一个字符则将判断最后一个字符的布尔型设置为true
           if (i == keyword.length() - 1) subNode.setKeywordEnd(true);
       }
   }
   ```
   
3. 过滤敏感词

   ```java
   //过滤敏感词
   public String filter(String text) {
       //如果文本是空的，直接返回null
       if (StringUtils.isBlank(text)) return null;
       //获取三个指针
       TrieNode tempNode = rootNode;
       int begin = 0;
       int position = begin;
       //存储结果集
       StringBuilder stringBuilder = new StringBuilder();
       //只要 position 不到最后一个位置说明一直要检测
       while (position < text.length()) {
           //获取文本的字符
           Character character = text.charAt(position);
           //判断是不是字符，如果是字符就跳过
           if (isSymbol(character)) {
               //如果是根节点
               if (tempNode == rootNode) {
                   begin++;
               }
               position++;
               continue;
           }
           //文本字符跟节点的子节点进行比对看有是不是一样的字符
           tempNode = tempNode.getSubNode(character);
           //如果获取得到的结果为空，表示子节点不存在该字符
           if (tempNode == null && begin == position) {
               //将字符添加到结果集中
               stringBuilder.append(character);
               position = ++begin;
               //tempNode回到根节点的位置上
               tempNode = rootNode;
           } else if (tempNode == null && position > begin) {
               //还有一种情况就是：比对到一半但是没有比对完全，没有全部对上，比如赌博，赌它干嘛，只匹配了一个赌字，第二个就不匹配了
               //需要拼接到stringBuilder中
               stringBuilder.append(text.substring(begin, position) + character);
               begin = ++position;
               tempNode = rootNode;
           } else if (tempNode.isKeywordEnd()) {
               //如果获取到的结果不为空并且在前缀树中的位置是最后一个字符，替换字符
               for (int i = 0; i < position - begin + 1; i++) stringBuilder.append("*");
               //进入到下一个非敏感词的范围职位
               begin = ++position;
               tempNode = rootNode;
           } else {
               //最后一种情况就是收到结果不为空但是不是前缀树最后一个节点
               position++;
           }
       }
       //如果最后 begin 还在范围之内，表示从 begin 开始的字符不是组合不成敏感词，直接放到stringBuilder中去
       if (begin < text.length()) stringBuilder.append(text.substring(begin));
       return stringBuilder.toString();
   }
   //判断是否是字符，如果是字符就往前面判断
   public boolean isSymbol(Character character) {
       //当前字符不在A-Za-z并且不是东亚文字就跳过
       return !CharUtils.isAsciiAlphanumeric(character) && (character < 0x2E80 || character > 0x9FFF);
   }
   ```

#### 5.7.2 发布帖子

发布帖子因为是局部更新页面，所以使用的是：`AJAX`技术

1. 引入`fastjson`依赖

   ```xml
   <dependency>
       <groupId>com.alibaba</groupId>
       <artifactId>fastjson</artifactId>
       <version>1.2.79</version>
   </dependency>
   ```

2. 创建`JSON`示例：

   - 制作`JSON`工具，将数据转换为`JSON`字符串

     ```java
     package com.zwm.util;
     
     import com.alibaba.fastjson.JSONObject;
     import org.apache.commons.lang3.StringUtils;
     import org.springframework.util.DigestUtils;
     
     import java.util.Map;
     import java.util.UUID;
     
     public class CommunityUtils {
     
         //code 编码
         //msg 提示信息
         //map 业务数据
         public static String getJsonString(int code, String msg, Map<String, Object> map) {
             JSONObject jsonObject = new JSONObject();
             jsonObject.put("code", code);
             jsonObject.put("msg", msg);
             if (map != null) {
                 for (String key : map.keySet()) {
                     jsonObject.put(key, map.get(key));
                 }
             }
             return jsonObject.toJSONString();
         }
     
         public static String getJsonString(int code, String msg) {
             return getJsonString(code, msg, null);
         }
     
         public static String getJsonString(int code) {
             return getJsonString(code, null, null);
         }
     }
     ```

   - 创建`Controller`业务层，当用户访问`/ajax`时返回`JSON`字符串

     ```java
     @RequestMapping(value = "/ajax", method = RequestMethod.POST)
     public String testAjax(String name, int age, String text) {
         Map<String, Object> map = new HashMap<>();
         map.put("name", name);
         map.put("age", age);
         map.put("text", text);
         return CommunityUtils.getJsonString(0, "SendSuccess", map);
     }
     ```

   - 制作前端`html`，使用`jQuery`发送异步请求，参数有三，一为请求路径，二为要发送的数据，三为回调函数 ---> 即请求完毕之后对数据的处理程序

     ```html
     <!DOCTYPE html>
     <html lang="en">
     <head>
         <meta charset="UTF-8">
         <title>AJAX</title>
     </head>
     <body>
     <p><input type="button" value="发送" onclick="send();"></p>
     <script src="https://code.jquery.com/jquery-3.3.1.min.js" crossorigin="anonymous"></script>
     <script>
         function send() {
             $.post(
                 "/community/discussPost/ajax",
                 {"name": "zhangSan", "age": 18, "text": "HelloWorld"},
                 function (data) {
                     console.log(typeof (data));
                     console.log(data);
                     data = JSON.parse(data);
                     console.log(typeof (data));
                     console.log(data);
                 }
             )
         }
     </script>
     </body>
     </html>
     ```

   - 访问页面：http://localhost/community/html/ajax-demo.html ---> 点击发送

     ![](https://img-blog.csdnimg.cn/b5c3287f4d574bc3a4fb4f6355e83fab.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBAQ3JBY0tlUi0x,size_18,color_FFFFFF,t_70,g_se,x_16)

   三层架构实现发帖功能：

   1. `DiscussPostMapper + DiscussPostMapper.xml`：实现插入帖子 --->`insertDiscussPost(DiscussPost discussPost)`

      ```java
      package com.zwm.dao;
      
      import com.zwm.entity.DiscussPost;
      import org.apache.ibatis.annotations.Mapper;
      
      import java.util.List;
      
      @Mapper
      public interface DiscussPostMapper {
          //根据 userId 分页查询状态为没有拉黑的帖子，如果是 userId = 0 代表查询所有
          public abstract List<DiscussPost> selectDiscussPosts(int userId, int start, int end);
      
          //根据 userId 分页查询状态为没有拉黑的帖子数量
          public abstract int selectDiscussPostsCount(int userId);
      
          //插入新的帖子
          public abstract int insertDiscussPost(DiscussPost discussPost);
      }
      ```

      ```sql
      <insert id="insertDiscussPost">
          insert into discuss_post(<include refid="insertFields"/>) values(#{user_id}, #{title}, #{content}, #{type}, #{status}, #{comment_count}, #{create_time}, #{score});
      </insert>
      ```

   2. `DiscussPostService + DiscussPostServiceImpl`：实现添加帖子 ---> `addDiscussPost(DiscussPost discussPost)`

      ```java
      package com.zwm.service;
      
      import com.zwm.entity.DiscussPost;
      
      import java.util.List;
      
      public interface DiscussPostService {
          //根据 userId 分页查询状态为没有拉黑的帖子
          public abstract List<DiscussPost> findDiscussPosts(int userId, int start, int end);
      
          //根据 userId 分页查询状态为没有拉黑的帖子数量
          public abstract int findDiscussPostsCount(int userId);
      
          //插入新的帖子
          public abstract int addDiscussPost(DiscussPost discussPost);
      }
      ```

      需要对内容做判断：判断是否为空 + 防范恶意用户的`XSS`攻击+过滤敏感词

      ```java
      package com.zwm.service.impl;
      
      import com.zwm.dao.DiscussPostMapper;
      import com.zwm.entity.DiscussPost;
      import com.zwm.service.DiscussPostService;
      import com.zwm.util.SensitiveFilter;
      import org.springframework.beans.factory.annotation.Autowired;
      import org.springframework.stereotype.Service;
      import org.springframework.web.util.HtmlUtils;
      
      import java.util.List;
      
      @Service
      public class DiscussPostServiceImpl implements DiscussPostService {
      
          @Autowired
          private DiscussPostMapper discussPostMapper;
      
          @Autowired
          private SensitiveFilter sensitiveFilter;
      
          @Override
          public List<DiscussPost> findDiscussPosts(int userId, int start, int end) {
              return discussPostMapper.selectDiscussPosts(userId, start, end);
          }
      
          @Override
          public int findDiscussPostsCount(int userId) {
              return discussPostMapper.selectDiscussPostsCount(userId);
          }
      
          @Override
          public int addDiscussPost(DiscussPost discussPost) {
              //如果discussPost参数为空，抛出异常
              if (discussPost == null) throw new IllegalArgumentException("参数不能为空！");
              //为了防止XSS攻击，需要将帖子标题 + 内容做一个HTML转换
              discussPost.setTitle(HtmlUtils.htmlEscape(discussPost.getTitle()));
              discussPost.setContent(HtmlUtils.htmlEscape(discussPost.getContent()));
              //过滤敏感词
              discussPost.setTitle(sensitiveFilter.filter(discussPost.getTitle()));
              discussPost.setContent(sensitiveFilter.filter(discussPost.getContent()));
              return discussPostMapper.insertDiscussPost(discussPost);
          }
      }
      ```

   3. `DiscussPostController`：实现添加帖子 ---> `addDiscussPost(String title, String content)` 请求路径为：`/add`

      ```java
      @RequestMapping(value = "/add", method = RequestMethod.POST)
      @ResponseBody
      public String addDiscussPost(String title, String content) {
          //获取当前线程的账号信息
          User user = hostHolder.getUser();
          //如果当前没有登录用户返回未登录错误信息
          if (user == null) {
              return CommunityUtils.getJsonString(403, "你还没有登录哦！");
          }
          DiscussPost discussPost = new DiscussPost();
          discussPost.setUserId(user.getId());
          discussPost.setTitle(title);
          discussPost.setContent(content);
          discussPost.setCreateTime(new Date());
          discussPostService.addDiscussPost(discussPost);
          // 报错的情况,将来统一处理.
          return CommunityUtils.getJsonString(0, "发布成功!");
      }
      ```

   4. `index.html`：实现点击发帖跳转到发帖页面

      ![](https://img-blog.csdnimg.cn/6cb233c65b4349ce9c81155c1a36ce60.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBAQ3JBY0tlUi0x,size_20,color_FFFFFF,t_70,g_se,x_16)

      ![](https://img-blog.csdnimg.cn/f99cef0eb4894decaf7c22fa2dd1dc2c.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBAQ3JBY0tlUi0x,size_20,color_FFFFFF,t_70,g_se,x_16)

      点击发布将隐藏整个发布框，然后将标题和内容通过异步请求传输给`community/discussPost/add`请求路径：

      ```javascript
      function publish() {
          $("#publishModal").modal("hide");
          
          var title = $("#recipient-name").val();
          var content = $("#message-text").val();
         	$.post(
          	CONTEXT_PATH + "/discuss/add",
              {"title":title, "content":content},
              function (data) {
                  data = JSON.parse(data);
                  //提示框中显示返回消息
                  $("#hintBody").text(data.msg);
                  //显示提示框
                  $("#hintBody").modal("show");
                  //2s 后自动隐藏提示框
                  setTimeout(function(){
                      $("#hintModal").modal("hide");
                      // 刷新页面
                      if (data.code == 0) {
                          window.location.reload();
                      }
                  }, 2000)
              }
          )
      }
      ```

#### 5.7.3 帖子详情

`discuss-detail.html`：

- 处理静态资源的访问路径
- 复用`index.html`的`header`区域
- 显示标题、作者、发布时间、帖子正文等内容

显示帖子详情的流程：用户点击帖子链接，跳转到帖子详情页显示详情即可

1. `DiscussPostMapper.java + DiscussPostMapper.xml`

   ```java
    //根据帖子id查询出帖子
    public abstract DiscussPost selectDiscussPostById(int id);
   ```

   ```xml
   <select id="selectDiscussPostById" resultType="discussPost">
       select <include refid="selectFields"/> from discuss_post where id = #{id}
   </select>
   ```

2. `DiscussPostService + DiscussPostServiceImpl`

   ```java
   //根据帖子id查询出帖子
   public abstract DiscussPost selectDiscussPostById(int id); 
   
   @Override
    public DiscussPost selectDiscussPost(int id) {
        return discussPostMapper.selectDiscussPostById(id);
    }
   ```

3. `DiscussPostController.java`

   ```java
   @RequestMapping(path = "/detail/{discussPostId}", method = RequestMethod.GET)
   public String getDiscussPost(@PathVariable(value = "discussPostId") int discussPostId, Model model) {
       //根据 ID 查询
       DiscussPost discussPost = discussPostService.selectDiscussPost(discussPostId);
       model.addAttribute("post", discussPost);
       //查询该帖子的用户信息
       User user = userService.findUserById(discussPost.getUserId());
       model.addAttribute("user", user);
       return "/site/discuss-detail";
   }
   ```

4. `index.html + discuss-detail`

   ![](https://img-blog.csdnimg.cn/ad3cbd7d738a46a3b3ee19f9b51ad7d0.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBAQ3JBY0tlUi0x,size_20,color_FFFFFF,t_70,g_se,x_16)

#### 5.7.4 事务管理

- 什么是事务？

  - 事务是由`N`步数据库操作序列组成的逻辑执行单元，这一系列执行单元要么全执行要么全放弃执行

- 事务的特型 --- `ACID`

  - `A`：原子性 --- 事务是应用中不可再分的最小执行体
  - `C`：一致性 --- 事务执行的结果，需要使数据从一个一致性状态，变成另一个一致性状态
  - `I`：隔离性 --- 各个事务的执行互不干扰，任何事务的内部操作对其他的事务都是隔离的
  - `D`：持久性 --- 事务一旦提交，对数据所做的任何改变都要记录要永久存储器中

- 事务隔离性：

  - 脏读：某一个事务读取了另外一个事务未提交的数据

    ![](https://imgconvert.csdnimg.cn/aHR0cDovL2Nkbi5uZXVzd2MyMDE5Lnh5ei8yMDIwMDUyNjIwMDY0OC5wbmc?x-oss-process=image/format,png)

    例子：如上图`T1`将`N`的数据改为了`11`但是还没有提交，此时`T2`读取`N`将读到的是`11`，但是`T1`将此事务回滚，`N`真正的数据为：`10`，此时标明`T2`出现了脏读

  - 不可重复读：某一个事务对同一个数据前后读取的结果不一致

    ![](https://imgconvert.csdnimg.cn/aHR0cDovL2Nkbi5uZXVzd2MyMDE5Lnh5ei8yMDIwMDUyNjIwMDczNi5wbmc?x-oss-process=image/format,png)

    例子：如上图`T1`读取`N`的数据，`T2`再读取，读到的都是`10`，但后续`T1`将`N`的数据更改为`10`并且提交，此时`T2`再读取时读取到的`N=11`，此为不可重复读

  - 幻读：某一个事务对同一个表前后查询到的行数不一致

    ![](https://imgconvert.csdnimg.cn/aHR0cDovL2Nkbi5uZXVzd2MyMDE5Lnh5ei8yMDIwMDUyNjIwMDkwMy5wbmc?x-oss-process=image/format,png)

  - 隔离级别：

    - `READ UNCOMMITTED`：读未提交
    - `READ COMMITTED`：读已提交
    - `REPEATABLE READ`：可重复读
    - `SERIALIZABELE`：串行化

    ![](https://imgconvert.csdnimg.cn/aHR0cDovL2Nkbi5uZXVzd2MyMDE5Lnh5ei8yMDIwMDUyNjIwMTEwMy5wbmc?x-oss-process=image/format,png)

  - 实现机制

    - 悲观锁（数据库）
      - 共享锁`S锁`：事务A对某数据加了共享锁后，其他事务只能对该数据加共享锁，但不能加排他锁
      - 拍他锁`X锁`：事务A对某数据加了排他锁后，其他事务对该数据既不能加共享锁，也不能加排他锁
    - 乐观锁（自定义）
      - 版本号、时间戳等。在更新数据前，检查版本号是否发生变化。若变化则取消本次更新，否则就更新数据（版本号+1）

  - `Spring`事务管理

    - 声明式事务
      - 通过XML配置，声明某方法的事务特征
      - 通过注解，声明某方法的事务特征
    - 编程式事务
      - 通过`TransactionTemplate`管理事务， 并通过它执行数据库的操作。

  - 使用注解声明事务：

    ​	`@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)`

    - 这里简单介绍下`Propagation`指的是传播方法：
      - `REQUIRED`：表示一定需要，如果没有事务就新建一个事务，如果已经存在一个事务就加入到这个事务中
      - `REQUIRES_NEW`：表示一定需要新事务，新建一个事务，如果当前存在事务就挂起当前事务
      - `SUPPORTS`：表示仅仅是支持，不一定要有，有就加入当前事务没有就以非事务方式执行
      - `MANDATORY`：表示强制性的，使用当前事务，如果当前没有事务就抛出异常
      - `NOT_SUPPORTED`：表示不支持，以非事务方式执行操作，如果当前存在事务就把当前事务挂起
      - `NEVER`：表示永不，做得很绝，以非事务方式执行，如果存在当前事务，则抛出异常
      - `NESTED`：表示筑巢，嵌套，如果当前存在事务则嵌套在食物内执行，如果当前没有事务，则执行与`PROPAGATION_REQUIRED`类似的操作即没有事务就新建一个事务，有就直接加入到这个事务之中

#### 5.7.6 显示评论

1. 创建评论表

   这里详细说明一下`entity_id entity_type target_id`这三个：

   - `entity_type`指的是评论类型，是对帖子的评论呢？还是对帖子评论的评论呢？我们用`1`代表对帖子的评论，用`2`代表对帖子评论的评论
   - `entity_id`指的是评论目标的`id`，需要结合评论类型来看，如果评论类型是`1`，那么这里的`entity_id`指的是对哪个帖子`id`进行了评论，如果评论类型是`2`，那么这里的`entity_id`指的是对哪个评论的`id`进行了评论 ---> 合而为一，很妙啊
   - `target_id`指的是对用户的回复，比如有一个用户评价了这个帖子或这个评论，我们可以直接回复这个用户

   |     字段      |    类型     |                   备注                   |
   | :-----------: | :---------: | :--------------------------------------: |
   |     `id`      |    `int`    |                   主键                   |
   |   `user_id`   |    `int`    |            发表评论的用户`id`            |
   |  `entity_id`  |    `int`    |            发表评论的实体`id`            |
   | `entity_type` |    `int`    | 评论实体类型：`1-帖子评论`、`2-评论回复` |
   |  `target_id`  |    `int`    |               评论目标`id`               |
   |   `content`   |   `text`    |                 评论内容                 |
   |   `status`    |    `int`    |       评论状态：`0-有效`、`1-无效`       |
   | `create_time` | `timestamp` |               评论发表时间               |
   ```sql
   DROP TABLE IF EXISTS `comment`;
    SET character_set_client = utf8mb4 ;
   CREATE TABLE `comment` (
     `id` int(11) NOT NULL AUTO_INCREMENT,
     `user_id` int(11) DEFAULT NULL,
     `entity_type` int(11) DEFAULT NULL,
     `entity_id` int(11) DEFAULT NULL,
     `target_id` int(11) DEFAULT NULL,
     `content` text,
     `status` int(11) DEFAULT NULL,
     `create_time` timestamp NULL DEFAULT NULL,
     PRIMARY KEY (`id`),
     KEY `index_user_id` (`user_id`),
     KEY `index_entity_id` (`entity_id`)
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
   
   LOCK TABLES `comment` WRITE;
   INSERT INTO `comment` VALUES (1,111,1,228,0,'xxx',0,'2019-04-04 11:13:36'),(2,111,1,228,0,'yyy',0,'2019-04-04 11:13:40'),(3,111,1,228,0,'zzz',0,'2019-04-04 11:13:43'),(4,112,1,228,0,'haha',0,'2019-04-04 11:32:03'),(5,112,1,228,0,'111',0,'2019-04-04 11:32:51'),(6,112,1,228,0,'222',0,'2019-04-04 11:32:56'),(7,112,1,228,0,'333',0,'2019-04-04 11:33:00'),(8,112,1,229,0,'haha',0,'2019-04-05 14:33:17'),(9,111,1,228,0,'ok',0,'2019-04-08 03:53:19'),(10,131,1,228,0,'我是新人, 请多关照!',0,'2019-04-08 04:09:46'),(11,111,1,231,0,'别灌水',0,'2019-04-08 06:08:32'),(12,111,1,232,0,'Hello',0,'2019-04-08 07:58:17'),(13,132,2,12,0,'How are you?',0,'2019-04-08 07:58:42'),(14,111,2,12,0,'你好',0,'2019-04-08 08:27:31'),(15,132,2,12,0,'你吃了吗',0,'2019-04-08 08:30:56'),(16,111,2,12,132,'吃了',0,'2019-04-08 08:43:13'),(17,111,2,12,132,'你呢',0,'2019-04-08 08:46:21'),(18,111,1,232,0,'哎呀',0,'2019-04-08 09:15:46'),(19,132,2,18,0,'哈哈',0,'2019-04-08 09:16:00'),(21,111,2,18,132,'嗯嗯',0,'2019-04-08 09:18:30'),(22,132,2,18,111,'嘿嘿',0,'2019-04-08 09:18:48'),(23,111,1,232,0,'1',0,'2019-04-08 10:56:02'),(24,111,1,232,0,'2',0,'2019-04-08 10:56:05'),(25,111,1,232,0,'3',0,'2019-04-08 10:56:08'),(26,111,1,232,0,'4',0,'2019-04-08 10:56:10'),(27,111,1,232,0,'5',0,'2019-04-08 10:56:13'),(28,112,1,233,0,'bbb',0,'2019-04-10 03:10:33'),(29,111,2,28,0,'bbb',0,'2019-04-10 03:10:59'),(30,112,2,28,111,'ccc',0,'2019-04-10 03:11:15'),(31,113,1,233,0,'Spring Boot',0,'2019-04-13 06:48:02'),(32,113,1,233,0,'Kafka',0,'2019-04-13 06:56:50'),(33,119,1,233,0,'说的不错, 顶顶顶!!!',0,'2019-04-13 08:35:24'),(34,120,1,233,0,'说的透彻!',0,'2019-04-13 08:50:56'),(35,122,1,233,0,'大爱',0,'2019-04-13 08:52:03'),(36,124,1,233,0,'说道心坎里去了!!!',0,'2019-04-13 08:52:57'),(37,125,1,233,0,'完全赞同!',0,'2019-04-13 08:53:35'),(38,126,1,233,0,'就是就是',0,'2019-04-13 08:54:10'),(39,127,1,233,0,'支持楼主',0,'2019-04-13 08:54:53'),(40,128,1,233,0,'果断粉你',0,'2019-04-13 08:55:22'),(41,129,1,233,0,'很好',0,'2019-04-13 08:55:45'),(42,131,1,233,0,'厉害了',0,'2019-04-13 08:56:12'),(43,112,1,234,0,'哈哈',0,'2019-04-13 09:56:33'),(44,112,1,235,0,'哈哈',0,'2019-04-13 10:26:13'),(45,112,1,236,0,'haha',0,'2019-04-13 10:34:11'),(46,111,1,236,0,'hehe',0,'2019-04-13 10:35:33'),(47,113,2,46,0,'lala',0,'2019-04-13 10:36:05'),(48,112,1,237,0,'bbb',0,'2019-04-13 14:21:04'),(49,113,1,237,0,'ccc',0,'2019-04-13 14:23:01'),(50,114,1,237,0,'ddd',0,'2019-04-13 14:23:34'),(51,115,1,237,0,'eee',0,'2019-04-13 14:24:00'),(52,116,1,237,0,'fff',0,'2019-04-13 14:24:28'),(53,117,1,237,0,'ggg',0,'2019-04-13 14:24:56'),(54,118,1,237,0,'hhh',0,'2019-04-13 14:25:33'),(55,133,1,245,0,'haha',0,'2019-04-19 03:09:41'),(56,133,2,55,0,'hehe',0,'2019-04-19 03:09:46'),(57,133,2,55,133,'heihei',0,'2019-04-19 03:09:53'),(58,133,1,246,0,'hello',0,'2019-04-19 03:37:34'),(59,111,1,246,0,'gg',0,'2019-04-19 09:21:50'),(60,134,1,249,0,'kk',0,'2019-04-19 10:16:57'),(61,134,2,60,0,'gg',0,'2019-04-19 10:17:03'),(62,134,2,60,134,'ff',0,'2019-04-19 10:17:09'),(63,111,1,233,0,'public static void main(String[] args) {\r\n  System.out.println(&quot;Hello World.&quot;);\r\n}',0,'2019-04-23 04:34:03'),(64,111,1,246,0,'不错',0,'2019-04-23 07:30:47'),(65,111,1,233,0,'很好',0,'2019-04-23 07:31:13'),(66,137,1,270,0,'x',0,'2019-04-25 06:46:09'),(67,137,1,270,0,'y',0,'2019-04-25 06:46:13'),(68,137,1,270,0,'z',0,'2019-04-25 06:46:16'),(69,137,1,270,0,'a',0,'2019-04-25 06:46:25'),(70,137,1,270,0,'b',0,'2019-04-25 06:46:28'),(71,137,1,270,0,'c',0,'2019-04-25 06:46:31'),(72,137,2,66,0,'嗯嗯',0,'2019-04-25 06:46:43'),(73,137,2,66,0,'你好',0,'2019-04-25 06:46:48'),(74,137,2,66,0,'吃了',0,'2019-04-25 06:46:57'),(75,137,2,66,137,'你呢',0,'2019-04-25 06:47:05'),(76,138,1,271,0,'private',0,'2019-04-25 07:22:28'),(77,138,1,271,0,'protected',0,'2019-04-25 07:22:33'),(78,138,2,77,0,'void',0,'2019-04-25 07:22:43'),(79,138,2,77,138,'yes',0,'2019-04-25 07:22:50'),(80,111,1,272,0,'gg',0,'2019-04-26 07:42:54'),(81,111,2,80,0,'你好',0,'2019-04-26 07:43:26'),(82,111,2,80,0,'嗯嗯',0,'2019-04-26 07:43:33'),(83,111,2,80,111,'吃了',0,'2019-04-26 07:43:40'),(84,145,1,273,0,'嘿嘿',0,'2019-04-28 07:33:03'),(85,145,2,84,0,'你好',0,'2019-04-28 07:33:12'),(86,145,2,84,145,'嗯嗯',0,'2019-04-28 07:33:16'),(87,111,1,273,0,'不错',0,'2019-04-28 10:56:14'),(88,145,1,272,0,'xxx',0,'2019-05-06 03:37:28'),(89,146,1,234,0,'不错',0,'2019-05-10 02:35:01'),(90,146,2,43,0,'嗯嗯',0,'2019-05-10 02:35:09'),(91,112,2,43,0,'你好',0,'2019-05-10 02:35:29'),(92,113,1,234,0,'嘿嘿嘿',0,'2019-05-10 02:36:06'),(93,114,1,234,0,'啦啦啦',0,'2019-05-10 02:36:25'),(94,114,1,234,0,'yes',0,'2019-05-10 02:36:41'),(95,115,1,234,0,'很悬很悬',0,'2019-05-10 02:37:16'),(96,146,1,234,0,'行不行啊',0,'2019-05-10 04:39:16'),(97,146,2,96,0,'你呢',0,'2019-05-10 04:39:37'),(98,146,1,234,0,'天呐',0,'2019-05-10 04:40:08'),(99,146,1,234,0,'试试',0,'2019-05-10 04:40:32'),(100,146,1,234,0,'???',0,'2019-05-10 04:42:07'),(101,146,1,234,0,'why',0,'2019-05-10 04:46:22'),(102,146,1,234,0,'怎么回事',0,'2019-05-10 04:47:23'),(103,146,1,234,0,'hello',0,'2019-05-10 04:55:07'),(104,146,1,274,0,'顶起',0,'2019-05-15 03:34:32'),(105,111,2,104,0,'顶你',0,'2019-05-15 03:34:51'),(106,146,2,104,111,'谢谢兄弟',0,'2019-05-15 03:35:14'),(107,111,2,104,146,'其实我也想要offer',0,'2019-05-15 03:35:40'),(108,112,1,274,0,'哈哈',0,'2019-05-15 03:40:50'),(109,112,1,274,0,'???',0,'2019-05-15 03:45:53'),(110,111,1,274,0,'why',0,'2019-05-15 03:55:00'),(111,111,1,274,0,'没事了',0,'2019-05-15 04:00:37'),(112,111,1,274,0,'好了',0,'2019-05-15 07:05:03'),(113,111,1,274,0,'ok',0,'2019-05-15 07:26:52'),(114,111,1,274,0,'hello',0,'2019-05-15 07:27:06'),(115,111,1,274,0,'world',0,'2019-05-15 07:27:16'),(116,111,1,274,0,'quartz',0,'2019-05-15 07:35:20'),(117,111,1,274,0,'好的',0,'2019-05-15 09:09:09'),(118,111,1,274,0,'为啥',0,'2019-05-15 09:10:35'),(119,111,1,274,0,'666',0,'2019-05-15 09:15:43'),(120,111,1,274,0,'777',0,'2019-05-15 09:53:15'),(121,111,1,274,0,'888',0,'2019-05-15 09:53:52'),(122,111,1,274,0,'999',0,'2019-05-15 09:55:08'),(123,111,1,274,0,'111',0,'2019-05-15 09:55:39'),(124,111,1,274,0,'222',0,'2019-05-15 09:57:02'),(125,111,1,274,0,'333',0,'2019-05-15 09:57:20'),(126,111,1,274,0,'444',0,'2019-05-15 09:57:26'),(127,111,1,274,0,'555',0,'2019-05-15 09:57:31'),(128,111,1,274,0,'666',0,'2019-05-15 10:00:19'),(129,111,1,274,0,'777',0,'2019-05-15 10:00:25'),(130,111,1,274,0,'888',0,'2019-05-15 10:00:31'),(131,111,1,274,0,'999',0,'2019-05-15 10:00:37'),(132,111,1,274,0,'111',0,'2019-05-15 10:00:43'),(133,111,1,274,0,'111',0,'2019-05-15 10:01:34'),(134,111,1,274,0,'222',0,'2019-05-15 10:01:39'),(135,111,1,274,0,'333',0,'2019-05-15 10:01:45'),(136,111,1,274,0,'444',0,'2019-05-15 10:01:50'),(137,111,1,274,0,'555',0,'2019-05-15 10:01:55'),(138,111,1,274,0,'555',0,'2019-05-15 10:02:29'),(139,111,1,274,0,'333',0,'2019-05-15 10:18:20'),(140,111,1,274,0,'444',0,'2019-05-15 10:18:26'),(141,111,1,274,0,'555',0,'2019-05-15 10:18:31'),(142,111,1,274,0,'666',0,'2019-05-15 10:18:40'),(143,111,1,274,0,'aaa',0,'2019-05-15 10:26:59'),(152,111,1,275,0,'好吧，你赢了！',0,'2019-05-16 11:06:58'),(153,111,1,275,0,'222',0,'2019-05-16 11:07:05'),(154,111,1,275,0,'222',0,'2019-05-16 11:07:05'),(155,111,1,275,0,'333',0,'2019-05-16 11:07:11'),(156,111,1,275,0,'555',0,'2019-05-16 11:07:15'),(157,111,1,275,0,'555',0,'2019-05-16 11:07:38'),(158,111,1,275,0,'222',0,'2019-05-16 11:12:18'),(159,111,1,275,0,'333',0,'2019-05-16 11:12:24'),(160,111,1,275,0,'444',0,'2019-05-16 11:12:29'),(161,111,1,275,0,'555',0,'2019-05-16 11:12:35'),(162,111,1,275,0,'666',0,'2019-05-16 11:12:39'),(163,111,1,275,0,'777',0,'2019-05-16 11:12:45'),(164,149,1,276,0,'自己顶！！！',0,'2019-05-17 07:50:39'),(165,111,1,276,0,'欢迎！',0,'2019-05-17 07:51:00'),(166,112,1,276,0,'欢迎！',0,'2019-05-17 07:51:22'),(167,149,2,165,0,'谢谢',0,'2019-05-17 07:51:53'),(168,149,2,166,0,'谢谢',0,'2019-05-17 07:52:01'),(169,149,1,276,0,'感谢各位！',0,'2019-05-17 07:52:14'),(170,113,1,276,0,'欢迎你！',0,'2019-05-17 07:52:35'),(171,114,1,276,0,'哈哈',0,'2019-05-17 07:53:01'),(172,111,1,277,0,'111',0,'2019-05-17 09:07:11'),(173,111,1,277,0,'222',0,'2019-05-17 09:07:15'),(174,111,1,277,0,'333',0,'2019-05-17 09:07:19'),(175,111,1,277,0,'444',0,'2019-05-17 09:07:22'),(176,111,1,277,0,'555',0,'2019-05-17 09:07:25'),(177,111,1,277,0,'666',0,'2019-05-17 09:07:29'),(178,111,1,277,0,'777',0,'2019-05-17 09:07:34'),(179,111,1,277,0,'888',0,'2019-05-17 09:07:38'),(180,111,1,277,0,'999',0,'2019-05-17 09:07:43'),(181,111,1,277,0,'000',0,'2019-05-17 09:07:48'),(182,111,1,277,0,'111',0,'2019-05-17 09:07:53'),(183,111,1,277,0,'aaa',0,'2019-05-17 09:19:01'),(184,111,1,277,0,'bbb',0,'2019-05-17 09:25:37'),(188,111,1,277,0,'ccc',0,'2019-05-17 09:49:58'),(189,111,1,277,0,'ddd',0,'2019-05-17 09:52:50'),(190,111,1,277,0,'eee',0,'2019-05-17 10:03:41'),(191,111,1,277,0,'fff',0,'2019-05-17 10:04:43'),(192,111,1,277,0,'ggg',0,'2019-05-17 10:05:05'),(195,111,1,277,0,'hhh',0,'2019-05-17 10:10:41'),(196,111,1,277,0,'iii',0,'2019-05-17 10:12:10'),(197,111,1,277,0,'kkk',0,'2019-05-17 10:12:32'),(199,111,1,277,0,'xxx',0,'2019-05-17 10:21:36'),(200,111,1,277,0,'yyy',0,'2019-05-17 10:21:56'),(201,111,1,277,0,'zzz',0,'2019-05-17 10:22:31'),(202,111,1,277,0,'mmm',0,'2019-05-17 10:23:11'),(203,111,1,277,0,'nnn',0,'2019-05-17 10:25:09'),(204,111,1,277,0,'ttt',0,'2019-05-17 10:26:00'),(205,111,1,277,0,'aaa',0,'2019-05-17 10:26:46'),(206,111,1,277,0,'ddd',0,'2019-05-17 10:28:35'),(207,111,1,277,0,'ggg',0,'2019-05-17 10:29:04'),(208,111,1,277,0,'hhh',0,'2019-05-17 10:29:38'),(209,111,1,277,0,'vvv',0,'2019-05-17 10:30:29'),(210,111,1,277,0,'asd',0,'2019-05-17 10:33:21'),(211,111,1,277,0,'bbb',0,'2019-05-17 10:34:21'),(212,111,1,277,0,'qqq',0,'2019-05-17 10:34:45'),(213,111,1,277,0,'sada',0,'2019-05-17 10:35:18'),(214,111,1,277,0,'111',0,'2019-05-17 10:38:40'),(215,111,1,277,0,'222',0,'2019-05-17 10:39:15'),(216,149,1,280,0,'111',0,'2019-05-20 09:41:58'),(217,149,1,280,0,'222',0,'2019-05-20 09:42:20'),(218,149,1,280,0,'333',0,'2019-05-20 09:42:35'),(219,149,1,280,0,'444',0,'2019-05-20 09:42:48'),(220,149,1,280,0,'555',0,'2019-05-20 09:43:01'),(221,149,1,280,0,'666',0,'2019-05-20 09:43:19'),(222,149,1,280,0,'777',0,'2019-05-20 09:43:32'),(223,149,1,280,0,'888',0,'2019-05-20 09:43:36'),(224,149,1,280,0,'999',0,'2019-05-20 09:43:40'),(225,149,1,280,0,'000',0,'2019-05-20 09:43:48'),(226,149,1,280,0,'111',0,'2019-05-20 09:43:55'),(227,149,1,280,0,'222',0,'2019-05-20 09:44:10'),(228,149,1,280,0,'333',0,'2019-05-20 09:44:13'),(229,149,1,280,0,'444',0,'2019-05-20 09:44:16'),(230,149,1,280,0,'555',0,'2019-05-20 09:44:19'),(231,149,1,280,0,'666',0,'2019-05-20 09:44:25');
   UNLOCK TABLES;
   
   ```

2. 创建评论实体类：`/entity/Comment`

   ```java
   package com.zwm.entity;
   
   import lombok.AllArgsConstructor;
   import lombok.Data;
   import lombok.NoArgsConstructor;
   
   import java.util.Date;
   
   @Data
   @AllArgsConstructor
   @NoArgsConstructor
   public class Comment {
       private int id;
       private int userId;
       private int entityId;
       private int entityType;
       private int targetId;
       private String content;
       private int status;
       private Date createTime;
   
       @Override
       public String toString() {
           return "Comment{" +
                   "id=" + id +
                   ", userId=" + userId +
                   ", entityId=" + entityId +
                   ", entityType=" + entityType +
                   ", targetId=" + targetId +
                   ", content='" + content + '\'' +
                   ", status=" + status +
                   ", createTime=" + createTime +
                   '}';
       }
   }
   ```

3. 创建`CommentMapper.java + CommentMapper.xml`

   分页查询评论 ---> `start + end` + 查询评论数量

   ```java
   package com.zwm.dao;
   
   import com.zwm.entity.Comment;
   import org.apache.ibatis.annotations.Mapper;
   
   import java.util.List;
   
   @Mapper
   public interface CommentMapper {
       //entityType 决定评论类型：1-帖子评论 2-回复评论
       //entityId 决定发表评论的实体 Id
       public abstract List<Comment> selectCommentsByEntity(int entityType, int entityId, int start, int end);
   
       //entityType：1-帖子评论 2-回复评论
       //entityId：发表评论的实体 Id
       public abstract int selectCountByEntity(int entityType, int entityId);
   }
   ```

   ```xml
   <?xml version="1.0" encoding="UTF-8" ?>
   <!DOCTYPE mapper
           PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
           "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
   <mapper namespace="com.zwm.dao.CommentMapper">
   
       <sql id="selectFields">
           id, user_id, entity_type, entity_id, target_id, content, status, create_time
       </sql>
   
       <select id="selectCommentsByEntity" resultType="comment">
           select <include refid="selectFields"/> from comment
           where status = 0 and entity_type = #{entityType}
           and entity_id = #{entityId}
           order by create_time asc
           limit #{start}, #{end}
       </select>
       
       <select id="selectCountByEntity" resultType="java.lang.Integer">
           select count(id) from comment
           where status = 0 and entity_type = #{entityType}
           and entity_id = #{entityId}
       </select>
   </mapper>
   ```

4. 创建`CommentService + CommentServiceImpl`

   ```java
   package com.zwm.service;
   
   import com.zwm.entity.Comment;
   
   import java.util.List;
   
   public interface CommentService {
       //分页查找当前所有评论
       public abstract List<Comment> findCommentsByEntity(int entityType, int entityId, int start, int end);
   
       //根据实体查找评论数量
       public abstract int findCountByEntity(int entityType, int entityId);
   }
   ```

   ```java
   package com.zwm.service.impl;
   
   import com.zwm.dao.CommentMapper;
   import com.zwm.entity.Comment;
   import com.zwm.service.CommentService;
   import org.springframework.beans.factory.annotation.Autowired;
   
   import java.util.List;
   
   public class CommentServiceImpl implements CommentService {
   
       @Autowired
       private CommentMapper commentMapper;
   
       @Override
       public List<Comment> findCommentsByEntity(int entityType, int entityId, int start, int end) {
           return commentMapper.selectCommentsByEntity(entityType, entityId, start, end);
       }
   
       @Override
       public int findCountByEntity(int entityType, int entityId) {
           return commentMapper.selectCountByEntity(entityType, entityId);
       }
   }
   ```

5. 表现层：可以看到评论是在帖子详情下面显示的，所以显示帖子详情的时候附带显示评论故需要改造`DiscussPostController.java`文件，当显示帖子详情的时候，需要获取完整的评论信息，然后放到帖子详情页中，原来的帖子详情页如下，详细见`5.7.3`

   ```java
    @RequestMapping(path = "/detail/{discussPostId}", method = RequestMethod.GET)
    public String getDiscussPost(@PathVariable(value = "discussPostId") int discussPostId, Model model) {
        //根据 ID 查询
        DiscussPost discussPost = discussPostService.selectDiscussPost(discussPostId);
        model.addAttribute("post", discussPost);
        //查询该帖子的用户信息
        User user = userService.findUserById(discussPost.getUserId());
        model.addAttribute("user", user);
        return "/site/discuss-detail";
    }
   ```

   引入评论服务层`CommentServiceImpl`以及引入分页类`Page`

   这里涉及到一种很重要的思想就是嵌套思想，我们是要把整个帖子的评论都搞回去，但是有好多条帖子，所以使用`ArrayList`来装`HashMap`，`HashMap`里边有帖子评论的用户信息和帖子评论的信息，然后帖子里面又有好多评论评论的评论，所以把帖子评论的评论装到一个`ArrayList`里头，`ArrayList`里头又装着`HashMap`，这个`HashMap`装着每一个评论的评论的用户啊，评论的评论信息啊，然后再把评论的评论的`ArrayList`放到帖子的`HashMap`中，帖子就完整了。再把这个帖子放到`ArrayList`中最后面就形成了整个最大的资源包 ---> `commentMapList`

   ```java
   @RequestMapping(path = "/detail/{discussPostId}", method = RequestMethod.GET)
   public String getDiscussPost(@PathVariable(value = "discussPostId") int discussPostId, Model model, Page page) {
       System.out.println("start：" + page.getStart());
       //根据 ID 查询
       DiscussPost discussPost = discussPostService.selectDiscussPost(discussPostId);
       model.addAttribute("post", discussPost);
       //查询该帖子的用户信息
       User user = userService.findUserById(discussPost.getUserId());
       model.addAttribute("user", user);
       //设置一页显示多少条数据 ---> 这里为 5 条
       page.setLimit(5);
       //设置查询路径
       page.setPath("/discuss/detail/" + discussPostId);
       //设置数据总数
       page.setRows(discussPost.getCommentCount());
       //首先获取帖子评论，帖子评论的标志为 entityType = 1;
       //这里可以把它抽象出来，放到常量类，这样可以直接使用常量做到见名知意
       //帖子评论类型？是哪个帖子的？显示第几条到第几条数据？
       List<Comment> commentList = commentService.findCommentsByEntity(ENTITY_TYPE_POST, discussPostId, page.getStart(), page.getLimit());
       List<Map<String, Object>> commentMapList = new ArrayList<>();
       //下面找出回复帖子评论的评论：
       if (commentList != null) {
           for (Comment comment : commentList) {
               //每个帖子评论都有所属用户，需要把用户信息和评论信息放到Map中传回给前端
               Map<String, Object> discussPostCommentMap = new HashMap<>();
               discussPostCommentMap.put("comment", comment);
               discussPostCommentMap.put("user", userService.findUserById(comment.getUserId()));
               //整个帖子评论的的回复数量为
               int replyCount = commentService.findCountByEntity(ENTITY_TYPE_COMMENT, comment.getId());
               discussPostCommentMap.put("replyCount", replyCount);
               //也将帖子数量这一特性放入资源包中
               //从每一个回复帖子的评论中可以获取到该帖子评论的 id 值
               //所以可以从这每一个 id 值中再获取回复该评论的评论
               List<Comment> replyCommentsCommentList = commentService.findCommentsByEntity(ENTITY_TYPE_COMMENT, comment.getId(), 0, Integer.MAX_VALUE);
               //评论的评论作为一个整体，并且每条评论也有自己的信息和发布人，所以需要把评论的评论放入一个整体
               List<Map<String, Object>> replyCommentsCommentMapList = new ArrayList<>();
               //遍历评论的评论
               if (replyCommentsCommentList != null) {
                   for (Comment replyCommentsComment : replyCommentsCommentList) {
                       Map<String, Object> commentCommentMap = new HashMap<>();
                       commentCommentMap.put("reply", replyCommentsComment);
                       //回复评论的用户
                       commentCommentMap.put("user", userService.findUserById(replyCommentsComment.getUserId()));
                       //回复的目标
                       User target = replyCommentsComment.getTargetId() == 0 ? null : userService.findUserById(replyCommentsComment.getTargetId());
                       commentCommentMap.put("target", target);
                       replyCommentsCommentMapList.add(commentCommentMap);
                   }
               }
               //现在replyCommentsCommentMapList已经存放了该帖子下边评论的评论各种信息 ---> 已形成一个完整的资源包
               //还需要把这个资源包跟帖子的资源包进行配对，形成一个更大的资源包
               //这样就形成了，每个 Map 都包含：用户-帖子评论-帖子评论的评论
               discussPostCommentMap.put("replys", replyCommentsCommentMapList);
               //将其放到专门放资源包的集合当中
               commentMapList.add(discussPostCommentMap);
           }
       }
       model.addAttribute("comments", commentMapList);
       return "/site/discuss-detail";
   }
   ```

   此外这里还有一个比较重要的信息就是分页，可以将此前显示帖子的分页直接拿过来复用，在`index.html`中，当请求路径包含`current`中时将带到`Page`对象中去

#### 5.7.7 添加评论

添加评论的整个流程很常规，值得注意的一点就是需要更新帖子`DiscussPost`的评论数量

- 数据层

  - 增加评论数据

  - 修改帖子评论数量【不包含回复评论和回复个人的评论】

    ```java
    //增加评论
    public abstract int insertComment(Comment comment);
    ```

    ```xml
    <insert id="insertComment" parameterType="Comment">
        insert into comment(<include refid="insertFields"></include>)
        values (#{userId}, #{entityType}, #{entityId}, #{targetId}, #{content}, #{status}, #{createTime})
    </insert>
    ```

    ```java
    //更新评论的数量
    int updateDiscussPostCommentCount(int id, int commentCount);
    ```

    ```xml
    <update id="updateDiscussPostCommentCount">
        update discuss_post set comment_count = #{commentCount} where id = #{id}
    </update>
    ```

- 业务层

  - 处理添加评论的业务：先增加评论再更新帖子的评论数量

    ```java
    //增加帖子
    public abstract int addComment(Comment comment);
    
     //更新评论数量
     public abstract int updateDiscussPostCommentCount(int id, int commentCount);
    
    @Override
    public int updateDiscussPostCommentCount(int id, int commentCount) {
        return discussPostMapper.updateDiscussPostCommentCount(id, commentCount);
    }
    
    @Override
    public int addComment(Comment comment) {
        //需要将评论内容过滤敏感词 + 防止 XSS 攻击
        comment.setContent(HtmlUtils.htmlEscape(comment.getContent()));
        comment.setContent(sensitiveFilter.filter(comment.getContent()));
        //添加评论
        int row = commentMapper.insertComment(comment);
        //增加评论的数量 ---> DiscussPostServiceImpl
        //获取全部的评论数量
        int counts = commentMapper.selectCountByEntity(comment.getEntityType(), comment.getEntityId());
        discussPostService.updateDiscussPostCommentCount(comment.getEntityId(), counts);
        return commentMapper.insertComment(comment);
    }
    ```

    

- 表现层

  - 处理添加评论数据的请求

  - 设置添加评论的表单

    ```java
    @RequestMapping(value = "/add/{discussPostId}", method = RequestMethod.POST)
    public String addComment(@PathVariable(value = "discussPostId") int discussPostId, Comment comment) {
        //获取当前线程的用户
        User user = hostHolder.getUser();
        if (user != null) {
            comment.setUserId(user.getId());
            comment.setStatus(0);
            comment.setCreateTime(new Date());
            //添加帖子
            commentService.addComment(comment);
        }
        return "redirect:/discuss/detail/" + discussPostId;
    }
    ```

  - ![](https://img-blog.csdnimg.cn/e2700b45bc2c4e4086d3ed32db626072.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBAQ3JBY0tlUi0x,size_20,color_FFFFFF,t_70,g_se,x_16)

#### 5.7.8 私信列表

- 查询当前用户的会话列表，每个会话只显示一条最新的私信【类比于微信】
- 支持分页显示
- 点开私信可以查看私信详情，查询某个会话所包含的私信
- 支持分页显示

数据库设计如下：

![](https://imgconvert.csdnimg.cn/aHR0cDovL2Nkbi5uZXVzd2MyMDE5Lnh5ei8yMDIwMDYwNDIxNDYzOS5wbmc?x-oss-process=image/format,png)

数据库创建`sql`如下：

```sql
DROP TABLE IF EXISTS `message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `message` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `from_id` int(11) DEFAULT NULL,
  `to_id` int(11) DEFAULT NULL,
  `conversation_id` varchar(45) NOT NULL,
  `content` text,
  `status` int(11) DEFAULT NULL COMMENT '0-未读;1-已读;2-删除;',
  `create_time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `index_from_id` (`from_id`),
  KEY `index_to_id` (`to_id`),
  KEY `index_conversation_id` (`conversation_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `message` WRITE;
/*!40000 ALTER TABLE `message` DISABLE KEYS */;
INSERT INTO `message` VALUES (1,112,111,'111_112','你好',1,'2019-04-05 14:49:15'),(2,112,111,'111_112','在吗',1,'2019-04-06 13:34:29'),(3,112,111,'111_112','说话',1,'2019-04-06 13:34:29'),(4,112,111,'111_112','人呢',1,'2019-04-06 13:34:29'),(5,113,111,'111_113','Hello',1,'2019-04-06 13:35:02'),(6,113,111,'111_113','How are you?',1,'2019-04-06 13:35:02'),(7,113,111,'111_113','Are you ok?',1,'2019-04-06 13:35:02'),(8,114,111,'111_114','哈哈',1,'2019-04-06 13:35:24'),(9,114,111,'111_114','呵呵',1,'2019-04-06 13:35:24'),(10,114,111,'111_114','嘿嘿',1,'2019-04-06 13:35:24'),(11,115,111,'111_115','good',1,'2019-04-06 13:36:26'),(12,115,111,'111_115','yes',1,'2019-04-06 13:36:26'),(13,115,111,'111_115','perfect',1,'2019-04-06 13:36:26'),(14,115,111,'111_115','ah',1,'2019-04-06 13:36:26'),(15,116,111,'111_116','哎呀',1,'2019-04-06 13:36:54'),(16,116,111,'111_116','我发错了',1,'2019-04-06 13:36:54'),(17,117,111,'111_117','喂',1,'2019-04-06 13:37:17'),(18,118,111,'111_118','HI',1,'2019-04-06 13:59:16'),(19,118,111,'111_118','Hello',1,'2019-04-06 13:59:16'),(20,119,111,'111_119','AAA',1,'2019-04-06 14:00:16'),(21,119,111,'111_119','BBB',1,'2019-04-06 14:00:16'),(22,119,111,'111_119','CCC',1,'2019-04-06 14:00:16'),(23,112,111,'111_112','哥们',1,'2019-04-06 14:04:05'),(24,112,111,'111_112','出来',2,'2019-04-06 14:04:05'),(25,112,111,'111_112','商量点事',1,'2019-04-06 14:04:05'),(26,111,112,'111_112','干啥',2,'2019-04-06 15:22:07'),(27,111,112,'111_112','忙呢',2,'2019-04-06 15:22:07'),(28,111,112,'111_112','来呀',1,'2019-04-06 15:29:56'),(29,131,111,'111_131','你好',1,'2019-04-08 04:24:10'),(30,131,111,'111_131','haha',1,'2019-04-08 04:43:22'),(31,111,131,'111_131','hehe',1,'2019-04-08 04:50:56'),(32,131,111,'111_131','yeah',1,'2019-04-08 04:57:16'),(33,131,111,'111_131','aaa',1,'2019-04-08 04:58:03'),(34,111,131,'111_131','ttt',1,'2019-04-08 04:58:16'),(35,132,111,'111_132','为啥不让我灌水',1,'2019-04-08 06:08:55'),(36,111,132,'111_132','就是不让灌水',1,'2019-04-08 06:22:55'),(37,111,132,'111_132','以后注意啊',2,'2019-04-08 06:29:01'),(38,111,131,'111_131','啦啦啦',2,'2019-04-13 02:20:50'),(39,111,131,'111_131','嘎嘎嘎',0,'2019-04-13 02:21:03'),(92,1,111,'like','{\"entityType\":1,\"entityId\":237,\"postId\":237,\"userId\":112}',1,'2019-04-13 14:20:58'),(93,1,111,'comment','{\"entityType\":1,\"entityId\":237,\"postId\":237,\"userId\":112}',1,'2019-04-13 14:21:04'),(94,1,111,'follow','{\"entityType\":3,\"entityId\":111,\"userId\":112}',1,'2019-04-13 14:21:08'),(95,1,111,'like','{\"entityType\":1,\"entityId\":237,\"postId\":237,\"userId\":113}',1,'2019-04-13 14:22:58'),(96,1,111,'comment','{\"entityType\":1,\"entityId\":237,\"postId\":237,\"userId\":113}',1,'2019-04-13 14:23:01'),(97,1,111,'follow','{\"entityType\":3,\"entityId\":111,\"userId\":113}',1,'2019-04-13 14:23:04'),(98,1,111,'like','{\"entityType\":1,\"entityId\":237,\"postId\":237,\"userId\":114}',1,'2019-04-13 14:23:26'),(99,1,111,'comment','{\"entityType\":1,\"entityId\":237,\"postId\":237,\"userId\":114}',1,'2019-04-13 14:23:34'),(100,1,111,'follow','{\"entityType\":3,\"entityId\":111,\"userId\":114}',1,'2019-04-13 14:23:37'),(101,1,111,'like','{\"entityType\":1,\"entityId\":237,\"postId\":237,\"userId\":115}',1,'2019-04-13 14:23:57'),(102,1,111,'comment','{\"entityType\":1,\"entityId\":237,\"postId\":237,\"userId\":115}',1,'2019-04-13 14:24:00'),(103,1,111,'follow','{\"entityType\":3,\"entityId\":111,\"userId\":115}',2,'2019-04-13 14:24:02'),(104,1,111,'like','{\"entityType\":1,\"entityId\":237,\"postId\":237,\"userId\":116}',1,'2019-04-13 14:24:24'),(105,1,111,'comment','{\"entityType\":1,\"entityId\":237,\"postId\":237,\"userId\":116}',1,'2019-04-13 14:24:28'),(106,1,111,'follow','{\"entityType\":3,\"entityId\":111,\"userId\":116}',1,'2019-04-13 14:24:30'),(107,1,111,'like','{\"entityType\":1,\"entityId\":237,\"postId\":237,\"userId\":117}',1,'2019-04-13 14:24:52'),(108,1,111,'comment','{\"entityType\":1,\"entityId\":237,\"postId\":237,\"userId\":117}',1,'2019-04-13 14:24:56'),(109,1,111,'follow','{\"entityType\":3,\"entityId\":111,\"userId\":117}',1,'2019-04-13 14:24:59'),(110,1,111,'like','{\"entityType\":1,\"entityId\":237,\"postId\":237,\"userId\":118}',1,'2019-04-13 14:25:15'),(111,1,111,'comment','{\"entityType\":1,\"entityId\":237,\"postId\":237,\"userId\":118}',1,'2019-04-13 14:25:33'),(112,1,111,'follow','{\"entityType\":3,\"entityId\":111,\"userId\":118}',1,'2019-04-13 14:25:43'),(113,1,1,'follow','{\"entityType\":3,\"entityId\":1,\"userId\":133}',0,'2019-04-19 03:09:32'),(114,1,111,'comment','{\"entityType\":1,\"entityId\":245,\"postId\":245,\"userId\":133}',1,'2019-04-19 03:09:41'),(115,1,133,'comment','{\"entityType\":2,\"entityId\":55,\"postId\":245,\"userId\":133}',1,'2019-04-19 03:09:46'),(116,1,133,'comment','{\"entityType\":2,\"entityId\":55,\"postId\":245,\"userId\":133}',1,'2019-04-19 03:09:53'),(117,1,133,'like','{\"entityType\":1,\"entityId\":246,\"postId\":246,\"userId\":133}',1,'2019-04-19 03:37:28'),(118,1,133,'comment','{\"entityType\":1,\"entityId\":246,\"postId\":246,\"userId\":133}',1,'2019-04-19 03:37:34'),(119,111,131,'111_131','aaa',0,'2019-04-19 09:21:24'),(120,1,133,'like','{\"entityType\":1,\"entityId\":246,\"postId\":246,\"userId\":111}',0,'2019-04-19 09:21:45'),(121,1,133,'comment','{\"entityType\":1,\"entityId\":246,\"postId\":246,\"userId\":111}',0,'2019-04-19 09:21:50'),(122,1,134,'like','{\"entityType\":1,\"entityId\":249,\"postId\":249,\"userId\":134}',1,'2019-04-19 10:16:48'),(123,1,134,'like','{\"entityType\":1,\"entityId\":249,\"postId\":249,\"userId\":134}',1,'2019-04-19 10:16:50'),(124,1,134,'like','{\"entityType\":1,\"entityId\":249,\"postId\":249,\"userId\":134}',1,'2019-04-19 10:16:52'),(125,1,134,'comment','{\"entityType\":1,\"entityId\":249,\"postId\":249,\"userId\":134}',1,'2019-04-19 10:16:57'),(126,1,134,'like','{\"entityType\":2,\"entityId\":60,\"postId\":249,\"userId\":134}',1,'2019-04-19 10:17:00'),(127,1,134,'comment','{\"entityType\":2,\"entityId\":60,\"postId\":249,\"userId\":134}',1,'2019-04-19 10:17:04'),(128,1,134,'comment','{\"entityType\":2,\"entityId\":60,\"postId\":249,\"userId\":134}',1,'2019-04-19 10:17:09'),(129,1,111,'follow','{\"entityType\":3,\"entityId\":111,\"userId\":134}',1,'2019-04-19 10:17:44'),(130,134,111,'111_134','hello',1,'2019-04-19 10:19:04'),(131,111,134,'111_134','nihao',0,'2019-04-19 10:19:30'),(132,1,111,'comment','{\"entityType\":1,\"entityId\":233,\"postId\":233,\"userId\":111}',1,'2019-04-23 04:34:03'),(133,1,133,'like','{\"entityType\":1,\"entityId\":246,\"postId\":246,\"userId\":111}',0,'2019-04-23 07:30:38'),(134,1,133,'comment','{\"entityType\":1,\"entityId\":246,\"postId\":246,\"userId\":111}',0,'2019-04-23 07:30:47'),(135,1,111,'like','{\"entityType\":1,\"entityId\":237,\"postId\":237,\"userId\":111}',1,'2019-04-23 07:31:00'),(136,1,111,'like','{\"entityType\":1,\"entityId\":233,\"postId\":233,\"userId\":111}',1,'2019-04-23 07:31:07'),(137,1,111,'comment','{\"entityType\":1,\"entityId\":233,\"postId\":233,\"userId\":111}',1,'2019-04-23 07:31:13'),(138,1,132,'like','{\"entityType\":1,\"entityId\":232,\"postId\":232,\"userId\":111}',0,'2019-04-23 07:33:12'),(139,1,112,'like','{\"entityType\":1,\"entityId\":229,\"postId\":229,\"userId\":111}',0,'2019-04-23 07:33:56'),(140,1,138,'comment','{\"entityType\":1,\"entityId\":270,\"postId\":270,\"userId\":138}',1,'2019-04-25 06:46:09'),(141,1,138,'comment','{\"entityType\":1,\"entityId\":270,\"postId\":270,\"userId\":138}',1,'2019-04-25 06:46:13'),(142,1,138,'comment','{\"entityType\":1,\"entityId\":270,\"postId\":270,\"userId\":138}',1,'2019-04-25 06:46:16'),(143,1,138,'comment','{\"entityType\":1,\"entityId\":270,\"postId\":270,\"userId\":138}',1,'2019-04-25 06:46:25'),(144,1,138,'comment','{\"entityType\":1,\"entityId\":270,\"postId\":270,\"userId\":138}',1,'2019-04-25 06:46:28'),(145,1,138,'comment','{\"entityType\":1,\"entityId\":270,\"postId\":270,\"userId\":138}',1,'2019-04-25 06:46:31'),(146,1,138,'comment','{\"entityType\":2,\"entityId\":66,\"postId\":270,\"userId\":138}',1,'2019-04-25 06:46:43'),(147,1,138,'comment','{\"entityType\":2,\"entityId\":66,\"postId\":270,\"userId\":138}',1,'2019-04-25 06:46:48'),(148,1,138,'comment','{\"entityType\":2,\"entityId\":66,\"postId\":270,\"userId\":138}',1,'2019-04-25 06:46:57'),(149,1,138,'comment','{\"entityType\":2,\"entityId\":66,\"postId\":270,\"userId\":138}',1,'2019-04-25 06:47:06'),(150,1,138,'like','{\"entityType\":1,\"entityId\":270,\"postId\":270,\"userId\":138}',1,'2019-04-25 06:47:21'),(151,1,138,'like','{\"entityType\":1,\"entityId\":270,\"postId\":270,\"userId\":138}',1,'2019-04-25 06:47:25'),(152,1,138,'like','{\"entityType\":2,\"entityId\":66,\"postId\":270,\"userId\":138}',1,'2019-04-25 06:47:27'),(153,1,138,'like','{\"entityType\":2,\"entityId\":72,\"postId\":270,\"userId\":138}',1,'2019-04-25 06:47:30'),(154,1,138,'like','{\"entityType\":2,\"entityId\":73,\"postId\":270,\"userId\":138}',1,'2019-04-25 06:47:30'),(155,1,138,'like','{\"entityType\":2,\"entityId\":74,\"postId\":270,\"userId\":138}',1,'2019-04-25 06:47:31'),(156,1,138,'like','{\"entityType\":2,\"entityId\":75,\"postId\":270,\"userId\":138}',1,'2019-04-25 06:47:32'),(157,1,138,'like','{\"entityType\":2,\"entityId\":67,\"postId\":270,\"userId\":138}',1,'2019-04-25 06:47:33'),(158,1,138,'like','{\"entityType\":2,\"entityId\":68,\"postId\":270,\"userId\":138}',1,'2019-04-25 06:47:34'),(159,1,138,'like','{\"entityType\":2,\"entityId\":69,\"postId\":270,\"userId\":138}',1,'2019-04-25 06:47:36'),(160,1,138,'like','{\"entityType\":2,\"entityId\":70,\"postId\":270,\"userId\":138}',1,'2019-04-25 06:47:37'),(161,138,111,'111_138','hello',1,'2019-04-25 07:10:45'),(162,1,111,'follow','{\"entityType\":3,\"entityId\":111,\"userId\":138}',1,'2019-04-25 07:10:57'),(163,1,111,'follow','{\"entityType\":3,\"entityId\":111,\"userId\":138}',1,'2019-04-25 07:10:59'),(164,138,111,'111_138','heihei',1,'2019-04-25 07:12:48'),(165,138,111,'111_138','lala',1,'2019-04-25 07:12:58'),(166,1,138,'like','{\"entityType\":1,\"entityId\":271,\"postId\":271,\"userId\":138}',1,'2019-04-25 07:22:22'),(167,1,138,'comment','{\"entityType\":1,\"entityId\":271,\"postId\":271,\"userId\":138}',1,'2019-04-25 07:22:28'),(168,1,138,'comment','{\"entityType\":1,\"entityId\":271,\"postId\":271,\"userId\":138}',1,'2019-04-25 07:22:33'),(169,1,138,'comment','{\"entityType\":2,\"entityId\":77,\"postId\":271,\"userId\":138}',1,'2019-04-25 07:22:43'),(170,1,138,'like','{\"entityType\":2,\"entityId\":78,\"postId\":271,\"userId\":138}',1,'2019-04-25 07:22:46'),(171,1,138,'comment','{\"entityType\":2,\"entityId\":77,\"postId\":271,\"userId\":138}',1,'2019-04-25 07:22:50'),(172,138,111,'111_138','Hello aaa',1,'2019-04-25 07:23:27'),(173,138,111,'111_138','???',1,'2019-04-25 07:23:40'),(174,1,111,'like','{\"entityType\":1,\"entityId\":234,\"postId\":234,\"userId\":111}',1,'2019-04-26 06:15:14'),(175,1,111,'like','{\"entityType\":2,\"entityId\":29,\"postId\":233,\"userId\":111}',1,'2019-04-26 07:17:54'),(176,1,112,'like','{\"entityType\":2,\"entityId\":30,\"postId\":233,\"userId\":111}',0,'2019-04-26 07:17:55'),(177,1,111,'like','{\"entityType\":2,\"entityId\":29,\"postId\":233,\"userId\":111}',1,'2019-04-26 07:17:57'),(178,1,112,'like','{\"entityType\":2,\"entityId\":30,\"postId\":233,\"userId\":111}',0,'2019-04-26 07:17:58'),(179,1,112,'like','{\"entityType\":2,\"entityId\":28,\"postId\":233,\"userId\":111}',0,'2019-04-26 07:17:58'),(180,1,111,'like','{\"entityType\":1,\"entityId\":272,\"postId\":272,\"userId\":111}',1,'2019-04-26 07:42:48'),(181,1,111,'comment','{\"entityType\":1,\"entityId\":272,\"postId\":272,\"userId\":111}',1,'2019-04-26 07:42:54'),(182,1,111,'comment','{\"entityType\":2,\"entityId\":80,\"postId\":272,\"userId\":111}',1,'2019-04-26 07:43:26'),(183,1,111,'comment','{\"entityType\":2,\"entityId\":80,\"postId\":272,\"userId\":111}',1,'2019-04-26 07:43:33'),(184,1,111,'comment','{\"entityType\":2,\"entityId\":80,\"postId\":272,\"userId\":111}',1,'2019-04-26 07:43:40'),(185,1,138,'follow','{\"entityType\":3,\"entityId\":138,\"userId\":111}',0,'2019-04-26 11:46:08'),(186,1,138,'follow','{\"entityType\":3,\"entityId\":138,\"userId\":111}',0,'2019-04-26 11:46:09'),(187,1,138,'like','{\"entityType\":1,\"entityId\":271,\"postId\":271,\"userId\":111}',0,'2019-04-26 11:46:17'),(188,1,138,'like','{\"entityType\":2,\"entityId\":77,\"postId\":271,\"userId\":111}',0,'2019-04-26 11:46:19'),(189,1,138,'like','{\"entityType\":2,\"entityId\":79,\"postId\":271,\"userId\":111}',0,'2019-04-26 11:46:21'),(190,1,138,'like','{\"entityType\":2,\"entityId\":78,\"postId\":271,\"userId\":111}',0,'2019-04-26 11:46:22'),(191,111,111,'111_111','cc',1,'2019-04-28 02:16:08'),(192,1,145,'like','{\"entityType\":1,\"entityId\":273,\"postId\":273,\"userId\":145}',1,'2019-04-28 07:32:57'),(193,1,145,'comment','{\"entityType\":1,\"entityId\":273,\"postId\":273,\"userId\":145}',1,'2019-04-28 07:33:03'),(194,1,145,'like','{\"entityType\":2,\"entityId\":84,\"postId\":273,\"userId\":145}',1,'2019-04-28 07:33:05'),(195,1,145,'comment','{\"entityType\":2,\"entityId\":84,\"postId\":273,\"userId\":145}',1,'2019-04-28 07:33:12'),(196,1,145,'comment','{\"entityType\":2,\"entityId\":84,\"postId\":273,\"userId\":145}',1,'2019-04-28 07:33:16'),(197,1,111,'follow','{\"entityType\":3,\"entityId\":111,\"userId\":145}',1,'2019-04-28 07:33:40'),(198,1,145,'follow','{\"entityType\":3,\"entityId\":145,\"userId\":111}',1,'2019-04-28 07:33:52'),(199,111,145,'111_145','你好',1,'2019-04-28 07:34:18'),(200,1,111,'like','{\"entityType\":1,\"entityId\":272,\"postId\":272,\"userId\":145}',1,'2019-04-28 08:11:42'),(201,1,111,'like','{\"entityType\":1,\"entityId\":272,\"postId\":272,\"userId\":145}',1,'2019-04-28 08:12:40'),(202,1,145,'like','{\"entityType\":1,\"entityId\":273,\"postId\":273,\"userId\":111}',1,'2019-04-28 10:56:03'),(203,1,145,'comment','{\"entityType\":1,\"entityId\":273,\"postId\":273,\"userId\":111}',1,'2019-04-28 10:56:14'),(204,1,145,'follow','{\"entityType\":3,\"entityId\":145,\"userId\":111}',1,'2019-04-28 10:56:18'),(205,1,111,'comment','{\"entityType\":1,\"entityId\":272,\"postId\":272,\"userId\":145}',0,'2019-05-06 03:37:28'),(206,145,111,'111_145','hello',0,'2019-05-06 03:37:39'),(207,111,112,'111_112','bbb',0,'2019-05-08 08:56:46'),(208,1,111,'comment','{\"entityType\":1,\"entityId\":234,\"postId\":234,\"userId\":146}',0,'2019-05-10 02:35:01'),(209,1,112,'comment','{\"entityType\":2,\"entityId\":43,\"postId\":234,\"userId\":146}',0,'2019-05-10 02:35:09'),(210,1,112,'comment','{\"entityType\":2,\"entityId\":43,\"postId\":234,\"userId\":112}',0,'2019-05-10 02:35:29'),(211,1,112,'like','{\"entityType\":2,\"entityId\":43,\"postId\":234,\"userId\":113}',0,'2019-05-10 02:36:00'),(212,1,146,'like','{\"entityType\":2,\"entityId\":89,\"postId\":234,\"userId\":113}',0,'2019-05-10 02:36:02'),(213,1,111,'comment','{\"entityType\":1,\"entityId\":234,\"postId\":234,\"userId\":113}',0,'2019-05-10 02:36:06'),(214,1,111,'comment','{\"entityType\":1,\"entityId\":234,\"postId\":234,\"userId\":114}',0,'2019-05-10 02:36:25'),(215,1,111,'comment','{\"entityType\":1,\"entityId\":234,\"postId\":234,\"userId\":114}',0,'2019-05-10 02:36:41'),(216,1,112,'like','{\"entityType\":2,\"entityId\":43,\"postId\":234,\"userId\":114}',0,'2019-05-10 02:36:44'),(217,1,111,'like','{\"entityType\":1,\"entityId\":234,\"postId\":234,\"userId\":114}',0,'2019-05-10 02:36:44'),(218,1,146,'like','{\"entityType\":2,\"entityId\":89,\"postId\":234,\"userId\":114}',0,'2019-05-10 02:36:47'),(219,1,113,'like','{\"entityType\":2,\"entityId\":92,\"postId\":234,\"userId\":114}',0,'2019-05-10 02:36:48'),(220,1,114,'like','{\"entityType\":2,\"entityId\":93,\"postId\":234,\"userId\":114}',0,'2019-05-10 02:36:50'),(221,1,111,'comment','{\"entityType\":1,\"entityId\":234,\"postId\":234,\"userId\":115}',0,'2019-05-10 02:37:16'),(222,1,111,'comment','{\"entityType\":1,\"entityId\":234,\"postId\":234,\"userId\":146}',0,'2019-05-10 04:39:16'),(223,1,146,'comment','{\"entityType\":2,\"entityId\":96,\"postId\":234,\"userId\":146}',0,'2019-05-10 04:39:37'),(224,1,111,'comment','{\"entityType\":1,\"entityId\":234,\"postId\":234,\"userId\":146}',0,'2019-05-10 04:40:08'),(225,1,111,'comment','{\"entityType\":1,\"entityId\":234,\"postId\":234,\"userId\":146}',0,'2019-05-10 04:40:55'),(226,1,111,'comment','{\"entityType\":1,\"entityId\":234,\"postId\":234,\"userId\":146}',0,'2019-05-10 04:42:53'),(227,1,111,'comment','{\"entityType\":1,\"entityId\":234,\"postId\":234,\"userId\":146}',0,'2019-05-10 04:46:50'),(228,1,111,'comment','{\"entityType\":1,\"entityId\":234,\"postId\":234,\"userId\":146}',0,'2019-05-10 04:46:50'),(229,1,111,'comment','{\"entityType\":1,\"entityId\":234,\"postId\":234,\"userId\":146}',0,'2019-05-10 04:47:29'),(230,1,111,'comment','{\"entityType\":1,\"entityId\":234,\"postId\":234,\"userId\":146}',0,'2019-05-10 04:55:34'),(231,1,146,'comment','{\"entityType\":1,\"entityId\":274,\"postId\":274,\"userId\":146}',0,'2019-05-15 03:34:32'),(232,1,146,'like','{\"entityType\":1,\"entityId\":274,\"postId\":274,\"userId\":111}',0,'2019-05-15 03:34:46'),(233,1,146,'like','{\"entityType\":2,\"entityId\":104,\"postId\":274,\"userId\":111}',0,'2019-05-15 03:34:47'),(234,1,146,'comment','{\"entityType\":2,\"entityId\":104,\"postId\":274,\"userId\":111}',0,'2019-05-15 03:34:51'),(235,1,146,'comment','{\"entityType\":2,\"entityId\":104,\"postId\":274,\"userId\":146}',0,'2019-05-15 03:35:14'),(236,1,111,'like','{\"entityType\":2,\"entityId\":105,\"postId\":274,\"userId\":146}',0,'2019-05-15 03:35:16'),(237,1,146,'comment','{\"entityType\":2,\"entityId\":104,\"postId\":274,\"userId\":111}',0,'2019-05-15 03:35:40'),(238,1,146,'like','{\"entityType\":1,\"entityId\":274,\"postId\":274,\"userId\":112}',0,'2019-05-15 03:40:46'),(239,1,146,'comment','{\"entityType\":1,\"entityId\":274,\"postId\":274,\"userId\":112}',0,'2019-05-15 03:40:50'),(240,1,146,'comment','{\"entityType\":1,\"entityId\":274,\"postId\":274,\"userId\":112}',0,'2019-05-15 03:45:53'),(241,1,146,'comment','{\"entityType\":1,\"entityId\":274,\"postId\":274,\"userId\":111}',0,'2019-05-15 03:55:07'),(242,1,146,'comment','{\"entityType\":1,\"entityId\":274,\"postId\":274,\"userId\":111}',0,'2019-05-15 03:55:16'),(243,1,146,'comment','{\"entityType\":1,\"entityId\":274,\"postId\":274,\"userId\":111}',0,'2019-05-15 04:00:37'),(244,1,146,'comment','{\"entityType\":1,\"entityId\":274,\"postId\":274,\"userId\":111}',0,'2019-05-15 07:05:03'),(245,1,146,'comment','{\"entityType\":1,\"entityId\":274,\"postId\":274,\"userId\":111}',0,'2019-05-15 07:26:53'),(246,1,146,'comment','{\"entityType\":1,\"entityId\":274,\"postId\":274,\"userId\":111}',0,'2019-05-15 07:27:06'),(247,1,146,'comment','{\"entityType\":1,\"entityId\":274,\"postId\":274,\"userId\":111}',0,'2019-05-15 07:27:16'),(248,1,146,'comment','{\"entityType\":1,\"entityId\":274,\"postId\":274,\"userId\":111}',0,'2019-05-15 07:35:20'),(249,1,146,'comment','{\"entityType\":1,\"entityId\":274,\"postId\":274,\"userId\":111}',0,'2019-05-15 09:09:09'),(250,1,146,'comment','{\"entityType\":1,\"entityId\":274,\"postId\":274,\"userId\":111}',0,'2019-05-15 09:10:35'),(251,1,146,'comment','{\"entityType\":1,\"entityId\":274,\"postId\":274,\"userId\":111}',0,'2019-05-15 09:15:44'),(252,1,146,'comment','{\"entityType\":1,\"entityId\":274,\"postId\":274,\"userId\":111}',0,'2019-05-15 09:53:26'),(253,1,146,'comment','{\"entityType\":1,\"entityId\":274,\"postId\":274,\"userId\":111}',0,'2019-05-15 09:53:54'),(254,1,146,'comment','{\"entityType\":1,\"entityId\":274,\"postId\":274,\"userId\":111}',0,'2019-05-15 09:55:08'),(255,1,146,'comment','{\"entityType\":1,\"entityId\":274,\"postId\":274,\"userId\":111}',0,'2019-05-15 09:55:39'),(256,1,146,'comment','{\"entityType\":1,\"entityId\":274,\"postId\":274,\"userId\":111}',0,'2019-05-15 09:57:02'),(257,1,146,'comment','{\"entityType\":1,\"entityId\":274,\"postId\":274,\"userId\":111}',0,'2019-05-15 09:57:20'),(258,1,146,'comment','{\"entityType\":1,\"entityId\":274,\"postId\":274,\"userId\":111}',0,'2019-05-15 09:57:26'),(259,1,146,'comment','{\"entityType\":1,\"entityId\":274,\"postId\":274,\"userId\":111}',0,'2019-05-15 09:57:31'),(260,1,146,'comment','{\"entityType\":1,\"entityId\":274,\"postId\":274,\"userId\":111}',0,'2019-05-15 10:00:19'),(261,1,146,'comment','{\"entityType\":1,\"entityId\":274,\"postId\":274,\"userId\":111}',0,'2019-05-15 10:00:25'),(262,1,146,'comment','{\"entityType\":1,\"entityId\":274,\"postId\":274,\"userId\":111}',0,'2019-05-15 10:00:31'),(263,1,146,'comment','{\"entityType\":1,\"entityId\":274,\"postId\":274,\"userId\":111}',0,'2019-05-15 10:00:37'),(264,1,146,'comment','{\"entityType\":1,\"entityId\":274,\"postId\":274,\"userId\":111}',0,'2019-05-15 10:00:43'),(265,1,146,'comment','{\"entityType\":1,\"entityId\":274,\"postId\":274,\"userId\":111}',0,'2019-05-15 10:01:34'),(266,1,146,'comment','{\"entityType\":1,\"entityId\":274,\"postId\":274,\"userId\":111}',0,'2019-05-15 10:01:39'),(267,1,146,'comment','{\"entityType\":1,\"entityId\":274,\"postId\":274,\"userId\":111}',0,'2019-05-15 10:01:45'),(268,1,146,'comment','{\"entityType\":1,\"entityId\":274,\"postId\":274,\"userId\":111}',0,'2019-05-15 10:01:50'),(269,1,146,'comment','{\"entityType\":1,\"entityId\":274,\"postId\":274,\"userId\":111}',0,'2019-05-15 10:01:55'),(270,1,146,'comment','{\"entityType\":1,\"entityId\":274,\"postId\":274,\"userId\":111}',0,'2019-05-15 10:02:29'),(271,1,146,'comment','{\"entityType\":1,\"entityId\":274,\"postId\":274,\"userId\":111}',0,'2019-05-15 10:18:20'),(272,1,146,'comment','{\"entityType\":1,\"entityId\":274,\"postId\":274,\"userId\":111}',0,'2019-05-15 10:18:26'),(273,1,146,'comment','{\"entityType\":1,\"entityId\":274,\"postId\":274,\"userId\":111}',0,'2019-05-15 10:18:31'),(274,1,146,'comment','{\"entityType\":1,\"entityId\":274,\"postId\":274,\"userId\":111}',0,'2019-05-15 10:18:40'),(275,1,146,'comment','{\"entityType\":1,\"entityId\":274,\"postId\":274,\"userId\":111}',0,'2019-05-15 10:26:59'),(276,1,11,'comment','{\"entityType\":1,\"entityId\":275,\"postId\":275,\"userId\":111}',0,'2019-05-16 11:06:59'),(277,1,11,'comment','{\"entityType\":1,\"entityId\":275,\"postId\":275,\"userId\":111}',0,'2019-05-16 11:07:05'),(278,1,11,'comment','{\"entityType\":1,\"entityId\":275,\"postId\":275,\"userId\":111}',0,'2019-05-16 11:07:06'),(279,1,11,'comment','{\"entityType\":1,\"entityId\":275,\"postId\":275,\"userId\":111}',0,'2019-05-16 11:07:11'),(280,1,11,'comment','{\"entityType\":1,\"entityId\":275,\"postId\":275,\"userId\":111}',0,'2019-05-16 11:07:15'),(281,1,11,'comment','{\"entityType\":1,\"entityId\":275,\"postId\":275,\"userId\":111}',0,'2019-05-16 11:07:38'),(282,1,11,'comment','{\"entityType\":1,\"entityId\":275,\"postId\":275,\"userId\":111}',0,'2019-05-16 11:12:18'),(283,1,11,'comment','{\"entityType\":1,\"entityId\":275,\"postId\":275,\"userId\":111}',0,'2019-05-16 11:12:24'),(284,1,11,'comment','{\"entityType\":1,\"entityId\":275,\"postId\":275,\"userId\":111}',0,'2019-05-16 11:12:29'),(285,1,11,'comment','{\"entityType\":1,\"entityId\":275,\"postId\":275,\"userId\":111}',0,'2019-05-16 11:12:35'),(286,1,11,'comment','{\"entityType\":1,\"entityId\":275,\"postId\":275,\"userId\":111}',0,'2019-05-16 11:12:39'),(287,1,11,'comment','{\"entityType\":1,\"entityId\":275,\"postId\":275,\"userId\":111}',0,'2019-05-16 11:12:45'),(288,1,149,'like','{\"entityType\":1,\"entityId\":276,\"postId\":276,\"userId\":149}',1,'2019-05-17 07:50:30'),(289,1,149,'comment','{\"entityType\":1,\"entityId\":276,\"postId\":276,\"userId\":149}',0,'2019-05-17 07:50:39'),(290,1,149,'comment','{\"entityType\":1,\"entityId\":276,\"postId\":276,\"userId\":111}',1,'2019-05-17 07:51:00'),(291,1,149,'like','{\"entityType\":1,\"entityId\":276,\"postId\":276,\"userId\":111}',1,'2019-05-17 07:51:02'),(292,1,149,'comment','{\"entityType\":1,\"entityId\":276,\"postId\":276,\"userId\":112}',1,'2019-05-17 07:51:22'),(293,1,111,'like','{\"entityType\":2,\"entityId\":165,\"postId\":276,\"userId\":149}',0,'2019-05-17 07:51:40'),(294,1,112,'like','{\"entityType\":2,\"entityId\":166,\"postId\":276,\"userId\":149}',0,'2019-05-17 07:51:41'),(295,1,111,'comment','{\"entityType\":2,\"entityId\":165,\"postId\":276,\"userId\":149}',0,'2019-05-17 07:51:53'),(296,1,112,'comment','{\"entityType\":2,\"entityId\":166,\"postId\":276,\"userId\":149}',0,'2019-05-17 07:52:01'),(297,1,149,'comment','{\"entityType\":1,\"entityId\":276,\"postId\":276,\"userId\":149}',1,'2019-05-17 07:52:14'),(298,1,149,'comment','{\"entityType\":1,\"entityId\":276,\"postId\":276,\"userId\":113}',1,'2019-05-17 07:52:35'),(299,1,149,'comment','{\"entityType\":1,\"entityId\":276,\"postId\":276,\"userId\":114}',1,'2019-05-17 07:53:01'),(300,1,149,'comment','{\"entityType\":1,\"entityId\":277,\"postId\":277,\"userId\":111}',0,'2019-05-17 09:07:11'),(301,1,149,'comment','{\"entityType\":1,\"entityId\":277,\"postId\":277,\"userId\":111}',0,'2019-05-17 09:07:15'),(302,1,149,'comment','{\"entityType\":1,\"entityId\":277,\"postId\":277,\"userId\":111}',0,'2019-05-17 09:07:19'),(303,1,149,'comment','{\"entityType\":1,\"entityId\":277,\"postId\":277,\"userId\":111}',0,'2019-05-17 09:07:22'),(304,1,149,'comment','{\"entityType\":1,\"entityId\":277,\"postId\":277,\"userId\":111}',0,'2019-05-17 09:07:25'),(305,1,149,'comment','{\"entityType\":1,\"entityId\":277,\"postId\":277,\"userId\":111}',0,'2019-05-17 09:07:29'),(306,1,149,'comment','{\"entityType\":1,\"entityId\":277,\"postId\":277,\"userId\":111}',0,'2019-05-17 09:07:34'),(307,1,149,'comment','{\"entityType\":1,\"entityId\":277,\"postId\":277,\"userId\":111}',0,'2019-05-17 09:07:38'),(308,1,149,'comment','{\"entityType\":1,\"entityId\":277,\"postId\":277,\"userId\":111}',0,'2019-05-17 09:07:43'),(309,1,149,'comment','{\"entityType\":1,\"entityId\":277,\"postId\":277,\"userId\":111}',0,'2019-05-17 09:07:48'),(310,1,149,'comment','{\"entityType\":1,\"entityId\":277,\"postId\":277,\"userId\":111}',0,'2019-05-17 09:07:53'),(311,1,149,'comment','{\"entityType\":1,\"entityId\":277,\"postId\":277,\"userId\":111}',0,'2019-05-17 09:19:54'),(312,1,149,'comment','{\"entityType\":1,\"entityId\":277,\"postId\":277,\"userId\":111}',0,'2019-05-17 09:26:29'),(313,1,149,'comment','{\"entityType\":1,\"entityId\":277,\"postId\":277,\"userId\":111}',0,'2019-05-17 09:49:58'),(314,1,149,'comment','{\"entityType\":1,\"entityId\":277,\"postId\":277,\"userId\":111}',0,'2019-05-17 09:52:51'),(315,1,149,'comment','{\"entityType\":1,\"entityId\":277,\"postId\":277,\"userId\":111}',0,'2019-05-17 10:03:41'),(316,1,149,'comment','{\"entityType\":1,\"entityId\":277,\"postId\":277,\"userId\":111}',0,'2019-05-17 10:04:43'),(317,1,149,'comment','{\"entityType\":1,\"entityId\":277,\"postId\":277,\"userId\":111}',0,'2019-05-17 10:05:05'),(318,1,149,'comment','{\"entityType\":1,\"entityId\":277,\"postId\":277,\"userId\":111}',0,'2019-05-17 10:10:42'),(319,1,149,'comment','{\"entityType\":1,\"entityId\":277,\"postId\":277,\"userId\":111}',0,'2019-05-17 10:12:10'),(320,1,149,'comment','{\"entityType\":1,\"entityId\":277,\"postId\":277,\"userId\":111}',0,'2019-05-17 10:12:32'),(321,1,149,'comment','{\"entityType\":1,\"entityId\":277,\"postId\":277,\"userId\":111}',0,'2019-05-17 10:21:36'),(322,1,149,'comment','{\"entityType\":1,\"entityId\":277,\"postId\":277,\"userId\":111}',0,'2019-05-17 10:21:56'),(323,1,149,'comment','{\"entityType\":1,\"entityId\":277,\"postId\":277,\"userId\":111}',0,'2019-05-17 10:22:40'),(324,1,149,'comment','{\"entityType\":1,\"entityId\":277,\"postId\":277,\"userId\":111}',0,'2019-05-17 10:24:19'),(325,1,149,'comment','{\"entityType\":1,\"entityId\":277,\"postId\":277,\"userId\":111}',0,'2019-05-17 10:26:04'),(326,1,149,'comment','{\"entityType\":1,\"entityId\":277,\"postId\":277,\"userId\":111}',0,'2019-05-17 10:26:04'),(327,1,149,'comment','{\"entityType\":1,\"entityId\":277,\"postId\":277,\"userId\":111}',0,'2019-05-17 10:26:54'),(328,1,149,'comment','{\"entityType\":1,\"entityId\":277,\"postId\":277,\"userId\":111}',0,'2019-05-17 10:28:58'),(329,1,149,'comment','{\"entityType\":1,\"entityId\":277,\"postId\":277,\"userId\":111}',0,'2019-05-17 10:29:49'),(330,1,149,'comment','{\"entityType\":1,\"entityId\":277,\"postId\":277,\"userId\":111}',0,'2019-05-17 10:30:02'),(331,1,149,'comment','{\"entityType\":1,\"entityId\":277,\"postId\":277,\"userId\":111}',0,'2019-05-17 10:31:51'),(332,1,149,'comment','{\"entityType\":1,\"entityId\":277,\"postId\":277,\"userId\":111}',0,'2019-05-17 10:31:54'),(333,1,149,'comment','{\"entityType\":1,\"entityId\":277,\"postId\":277,\"userId\":111}',0,'2019-05-17 10:33:25'),(334,1,149,'comment','{\"entityType\":1,\"entityId\":277,\"postId\":277,\"userId\":111}',0,'2019-05-17 10:34:21'),(335,1,149,'comment','{\"entityType\":1,\"entityId\":277,\"postId\":277,\"userId\":111}',0,'2019-05-17 10:34:49'),(336,1,149,'comment','{\"entityType\":1,\"entityId\":277,\"postId\":277,\"userId\":111}',0,'2019-05-17 10:38:40'),(337,1,149,'comment','{\"entityType\":1,\"entityId\":277,\"postId\":277,\"userId\":111}',0,'2019-05-17 10:39:19'),(338,1,149,'like','{\"entityType\":1,\"entityId\":280,\"postId\":280,\"userId\":149}',0,'2019-05-20 09:41:37'),(339,1,149,'comment','{\"entityType\":1,\"entityId\":280,\"postId\":280,\"userId\":149}',0,'2019-05-20 09:41:58'),(340,1,149,'comment','{\"entityType\":1,\"entityId\":280,\"postId\":280,\"userId\":149}',0,'2019-05-20 09:42:20'),(341,1,149,'comment','{\"entityType\":1,\"entityId\":280,\"postId\":280,\"userId\":149}',0,'2019-05-20 09:42:35'),(342,1,149,'comment','{\"entityType\":1,\"entityId\":280,\"postId\":280,\"userId\":149}',0,'2019-05-20 09:42:48'),(343,1,149,'comment','{\"entityType\":1,\"entityId\":280,\"postId\":280,\"userId\":149}',0,'2019-05-20 09:43:01'),(344,1,149,'comment','{\"entityType\":1,\"entityId\":280,\"postId\":280,\"userId\":149}',0,'2019-05-20 09:43:19'),(345,1,149,'comment','{\"entityType\":1,\"entityId\":280,\"postId\":280,\"userId\":149}',0,'2019-05-20 09:43:32'),(346,1,149,'comment','{\"entityType\":1,\"entityId\":280,\"postId\":280,\"userId\":149}',0,'2019-05-20 09:43:36'),(347,1,149,'comment','{\"entityType\":1,\"entityId\":280,\"postId\":280,\"userId\":149}',0,'2019-05-20 09:43:40'),(348,1,149,'comment','{\"entityType\":1,\"entityId\":280,\"postId\":280,\"userId\":149}',0,'2019-05-20 09:43:48'),(349,1,149,'comment','{\"entityType\":1,\"entityId\":280,\"postId\":280,\"userId\":149}',0,'2019-05-20 09:43:55'),(350,1,149,'comment','{\"entityType\":1,\"entityId\":280,\"postId\":280,\"userId\":149}',0,'2019-05-20 09:44:10'),(351,1,149,'comment','{\"entityType\":1,\"entityId\":280,\"postId\":280,\"userId\":149}',0,'2019-05-20 09:44:13'),(352,1,149,'comment','{\"entityType\":1,\"entityId\":280,\"postId\":280,\"userId\":149}',0,'2019-05-20 09:44:16'),(353,1,149,'comment','{\"entityType\":1,\"entityId\":280,\"postId\":280,\"userId\":149}',0,'2019-05-20 09:44:19'),(354,1,149,'comment','{\"entityType\":1,\"entityId\":280,\"postId\":280,\"userId\":149}',0,'2019-05-20 09:44:25');
/*!40000 ALTER TABLE `message` ENABLE KEYS */;
UNLOCK TABLES;
```

1. 创建私信实体类

   ```java
   package com.zwm.entity;
   
   import lombok.AllArgsConstructor;
   import lombok.Data;
   import lombok.NoArgsConstructor;
   
   import java.util.Date;
   
   @Data
   @AllArgsConstructor
   @NoArgsConstructor
   public class Message {
       private int id;
       private int fromId;//发信人Id
       private int toId;//收信人Id
       private String conversationId;//交流ID，如：111_112
       private String content;//私信内容
       private int status;//当前状态：0-未读 1-已读 2-删除
       private Date createTime;//私信创建时间
   
       @Override
       public String toString() {
           return "Message{" +
                   "id=" + id +
                   ", fromId=" + fromId +
                   ", toId=" + toId +
                   ", conversationId='" + conversationId + '\'' +
                   ", content='" + content + '\'' +
                   ", status=" + status +
                   ", createTime=" + createTime +
                   '}';
       }
   }
   ```

2. 数据层`Dao`层：

   - 显示当前用户的所有会话，并且每个会话只显示最新的一条私信
   - 显示当前用户会话的总数量
   - 显示列表中某个会话包含的所有私信
   - 显示列表中某个会话包含的所有私信的数量
   - 显示列表中某个会话未读的私信的数量

   ```java
   package com.zwm.dao;
   
   import com.zwm.entity.Message;
   import org.apache.ibatis.annotations.Mapper;
   
   import java.util.List;
   
   @Mapper
   public interface MessageMapper {
       //查询当前用户的所有会话，每个会话只显示私信最新一条消息，分页查询【排除系统通知即 from_id != 2】
       public abstract List<Message> selectConversations(int userId, int start, int limit);
   
       //查询当前用户的会话数量
       public abstract int selectConversationCount(int userId);
   
       //查询某个会话中所包含的私信列表
       public abstract List<Message> selectLetters(String conversationId, int start, int limit);
   
       //查询某个会话中所包含的私信列表的数量
       public abstract int selectLetterCount(String conversationId);
   
       //查询某个会话中未读私信的数量
       public abstract int selectLetterUnreadCount(int userId, String conversationId);
   }
   
   <?xml version="1.0" encoding="UTF-8" ?>
   <!DOCTYPE mapper
           PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
           "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
   <mapper namespace="com.zwm.dao.MessageMapper">
       <sql id="selectFields">
           id
           ,from_id,to_id,conversation_id,content,status,create_time
       </sql>
   
       <!--会话中的私信不能是删除的，并且要么是发消息的是userId要么收消息的是userId并且不是系统消息-->
       <select id="selectConversations" resultType="message">
           select <include refid="selectFields"></include> from message
           where id in (select max(id) from message where status != 2 and from_id != 1 and (from_id = #{userId} or to_id =
           #{userId}) group by conversation_id)
           order by id desc limit #{start}, #{end}
       </select>
   
       <!--统计会话数量-->
       <select id="selectConversationCount" resultType="java.lang.Integer">
           select count(m.maxId)
           from (select max(id) as maxId
                 from message
                 where status != 2 and from_id != 1 and (from_id = #{userId} or to_id = #{userId})
                 group by conversation_id) m
       </select>
   
       <!--查询某个会话的所有私信-->
       <select id="selectLetters" resultType="message">
           select <include refid="selectFields"></include> from message where status != 2 and from_id != 1 and
           conversation_id = #{conversationId} order by id desc limit #{start}, #{limit};
       </select>
   
       <!--统计某个会话的私信数量-->
       <select id="selectLetterCount" resultType="java.lang.Integer">
           select count(id)
           from message
           where status != 2 and from_id != 1 and conversation_id = #{conversationId};
       </select>
   
       <!--统计某个会话中未读私信的数量-->
       <select id="selectLetterUnreadCount" resultType="java.lang.Integer">
           select count(id) from message where status = 0 and from_id != 1 and to_id=#{userId}
           <if test="conversationId!=null">
               and conversation_id=#{conversationId}
           </if>
       </select>
   </mapper>
   ```

3. 业务层

   ```java
   package com.zwm.service;
   
   import com.zwm.entity.Message;
   
   import java.util.List;
   
   public interface MessageService {
       //查询当前用户的所有会话，每个会话只显示私信最新一条消息，分页查询【排除系统通知即 from_id != 2】
       public abstract List<Message> findConversations(int userId, int start, int limit);
   
       //查询当前用户的会话数量
       public abstract int findConversationCount(int userId);
   
       //查询某个会话中所包含的私信列表
       public abstract List<Message> findLetters(String conversationId, int start, int limit);
   
       //查询某个会话中所包含的私信列表的数量
       public abstract int findLetterCount(String conversationId);
   
       //查询某个会话中未读私信的数量
       public abstract int findLetterUnreadCount(int userId, String conversationId);
   }
   
   
   package com.zwm.service.impl;
   
   import com.zwm.dao.MessageMapper;
   import com.zwm.entity.Message;
   import com.zwm.service.MessageService;
   import org.springframework.beans.factory.annotation.Autowired;
   
   import java.util.List;
   
   public class MessageServiceImpl implements MessageService {
   
       @Autowired
       private MessageMapper messageMapper;
   
       @Override
       public List<Message> findConversations(int userId, int start, int limit) {
           return messageMapper.selectConversations(userId, start, limit);
       }
   
       @Override
       public int findConversationCount(int userId) {
           return messageMapper.selectConversationCount(userId);
       }
   
       @Override
       public List<Message> findLetters(String conversationId, int start, int limit) {
           return messageMapper.selectLetters(conversationId, start, limit);
       }
   
       @Override
       public int findLetterCount(String conversationId) {
           return messageMapper.selectLetterCount(conversationId);
       }
   
       @Override
       public int findLetterUnreadCount(int userId, String conversationId) {
           return messageMapper.selectLetterUnreadCount(userId, conversationId);
       }
   }
   ```

4. 表现层`Controller`：

   ```java
   @RequestMapping(path = "/letter/list", method = RequestMethod.GET)
   public String getLetterList(Model model, Page page) {
       User user = hostHolder.getUser();
       //分页信息
       page.setLimit(5);
       page.setPath("/letter/list/");
       page.setRows(messageService.findConversationCount(user.getId()));
       //会话列表
       List<Message> conversationList = messageService.findConversations(user.getId(), page.getStart(), page.getLimit());
       List<Map<String, Object>> conversations = new ArrayList<>();
       if (conversationList != null) {
           //获取会话集合里的每一个会话
           for (Message conversation : conversationList) {
               Map<String, Object> map = new HashMap<>();
               map.put("conversation", conversation);
               //每个会话里头包含着私信总数量
               map.put("letterCount", messageService.findLetterCount(conversation.getConversationId()));
               //每个会话里头包含着未读私信总数量
               map.put("unreadCount", messageService.findLetterUnreadCount(user.getId(), conversation.getConversationId()));
               //显示发信人的用户信息
               //如果发信人是自己显示收信人的用户信息
               //如果收信人是自己显示发信人的用户信息
               int targetId = user.getId() == conversation.getFromId() ? conversation.getToId() : conversation.getFromId();
               map.put("target", userService.findUserById(targetId));
               //将每个会话的资源包放入一个大的资源包中
               conversations.add(map);
           }
       }
       model.addAttribute("conversations", conversations);
       //查询未读消息的数量
       int letterUnreadCount = messageService.findLetterUnreadCount(user.getId(), null);
       model.addAttribute("letterUnreadCount", letterUnreadCount);
       return "/site/letter";
   }
   ```

5. `letter.html`

   ```html
   <!doctype html>
   <html lang="en" xmlns:th="http://www.thymeleaf.org">
   <head>
   	<meta charset="utf-8">
   	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
   	<link rel="icon" href="https://static.nowcoder.com/images/logo_87_87.png"/>
   	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" crossorigin="anonymous">
   	<link rel="stylesheet" th:href="@{/css/global.css}" />
   	<link rel="stylesheet" th:href="@{/css/letter.css}" />
   	<title>牛客网-私信列表</title>
   </head>
   <body>
   <div class="nk-container">
   	<!-- 头部 -->
   	<header class="bg-dark sticky-top" th:replace="index::header">
   		<div class="container">
   			<!-- 导航 -->
   			<nav class="navbar navbar-expand-lg navbar-dark">
   				<!-- logo -->
   				<a class="navbar-brand" href="#"></a>
   				<button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
   					<span class="navbar-toggler-icon"></span>
   				</button>
   				<!-- 功能 -->
   				<div class="collapse navbar-collapse" id="navbarSupportedContent">
   					<ul class="navbar-nav mr-auto">
   						<li class="nav-item ml-3 btn-group-vertical">
   							<a class="nav-link" href="../index.html">首页</a>
   						</li>
   						<li class="nav-item ml-3 btn-group-vertical">
   							<a class="nav-link position-relative" href="letter.html">消息<span class="badge badge-danger">12</span></a>
   						</li>
   						<li class="nav-item ml-3 btn-group-vertical">
   							<a class="nav-link" href="register.html">注册</a>
   						</li>
   						<li class="nav-item ml-3 btn-group-vertical">
   							<a class="nav-link" href="login.html">登录</a>
   						</li>
   						<li class="nav-item ml-3 btn-group-vertical dropdown">
   							<a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
   								<img src="http://images.nowcoder.com/head/1t.png" class="rounded-circle" style="width:30px;"/>
   							</a>
   							<div class="dropdown-menu" aria-labelledby="navbarDropdown">
   								<a class="dropdown-item text-center" href="profile.html">个人主页</a>
   								<a class="dropdown-item text-center" href="setting.html">账号设置</a>
   								<a class="dropdown-item text-center" href="login.html">退出登录</a>
   								<div class="dropdown-divider"></div>
   								<span class="dropdown-item text-center text-secondary">nowcoder</span>
   							</div>
   						</li>
   					</ul>
   					<!-- 搜索 -->
   					<form class="form-inline my-2 my-lg-0" action="search.html">
   						<input class="form-control mr-sm-2" type="search" aria-label="Search" />
   						<button class="btn btn-outline-light my-2 my-sm-0" type="submit">搜索</button>
   					</form>
   				</div>
   			</nav>
   		</div>
   	</header>
   
   	<!-- 内容 -->
   	<div class="main">
   		<div class="container">
   			<div class="position-relative">
   				<!-- 选项 -->
   				<ul class="nav nav-tabs mb-3">
   					<li class="nav-item">
   						<a class="nav-link position-relative active" th:href="@{/letter/list}">
   							朋友私信<span class="badge badge-danger" th:text="${letterUnreadCount}" th:if="${letterUnreadCount!=0}">3</span>
   						</a>
   					</li>
   					<li class="nav-item">
   						<a class="nav-link position-relative" href="notice.html">系统通知<span class="badge badge-danger">27</span></a>
   					</li>
   				</ul>
   				<button type="button" class="btn btn-primary btn-sm position-absolute rt-0" data-toggle="modal" data-target="#sendModal">发私信</button>
   			</div>
   			<!-- 弹出框 -->
   			<div class="modal fade" id="sendModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
   				<div class="modal-dialog modal-lg" role="document">
   					<div class="modal-content">
   						<div class="modal-header">
   							<h5 class="modal-title" id="exampleModalLabel">发私信</h5>
   							<button type="button" class="close" data-dismiss="modal" aria-label="Close">
   								<span aria-hidden="true">&times;</span>
   							</button>
   						</div>
   						<div class="modal-body">
   							<form>
   								<div class="form-group">
   									<label for="recipient-name" class="col-form-label">发给：</label>
   									<input type="text" class="form-control" id="recipient-name">
   								</div>
   								<div class="form-group">
   									<label for="message-text" class="col-form-label">内容：</label>
   									<textarea class="form-control" id="message-text" rows="10"></textarea>
   								</div>
   							</form>
   						</div>
   						<div class="modal-footer">
   							<button type="button" class="btn btn-secondary" data-dismiss="modal">取消</button>
   							<button type="button" class="btn btn-primary" id="sendBtn">发送</button>
   						</div>
   					</div>
   				</div>
   			</div>
   			<!-- 提示框 -->
   			<div class="modal fade" id="hintModal" tabindex="-1" role="dialog" aria-labelledby="hintModalLabel" aria-hidden="true">
   				<div class="modal-dialog modal-lg" role="document">
   					<div class="modal-content">
   						<div class="modal-header">
   							<h5 class="modal-title" id="hintModalLabel">提示</h5>
   						</div>
   						<div class="modal-body" id="hintBody">
   							发送完毕!
   						</div>
   					</div>
   				</div>
   			</div>
   
   			<!-- 私信列表 -->
   			<ul class="list-unstyled">
   				<li class="media pb-3 pt-3 mb-3 border-bottom position-relative" th:each="map:${conversations}">
   					<span class="badge badge-danger" th:text="${map.unreadCount}" th:if="${map.unreadCount!=0}">3</span>
   					<a href="profile.html">
   						<img th:src="${map.target.headerUrl}" class="mr-4 rounded-circle user-header" alt="用户头像" >
   					</a>
   					<div class="media-body">
   						<h6 class="mt-0 mb-3">
   							<span class="text-success" th:utext="${map.target.username}">落基山脉下的闲人</span>
   							<span class="float-right text-muted font-size-12" th:text="${#dates.format(map.conversation.createTime,'yyyy-MM-dd HH:mm:ss')}">2019-04-28 14:13:25</span>
   						</h6>
   						<div>
   							<a th:href="@{|/letter/detail/${map.conversation.conversationId}|}" th:utext="${map.conversation.content}">米粉车, 你来吧!</a>
   							<ul class="d-inline font-size-12 float-right">
   								<li class="d-inline ml-2"><a href="#" class="text-primary">共<i th:text="${map.letterCount}">5</i>条会话</a></li>
   							</ul>
   						</div>
   					</div>
   				</li>
   			</ul>
   			<!-- 分页 -->
   			<nav class="mt-5" th:replace="index::pagination">
   				<ul class="pagination justify-content-center">
   					<li class="page-item"><a class="page-link" href="#">首页</a></li>
   					<li class="page-item disabled"><a class="page-link" href="#">上一页</a></li>
   					<li class="page-item active"><a class="page-link" href="#">1</a></li>
   					<li class="page-item"><a class="page-link" href="#">2</a></li>
   					<li class="page-item"><a class="page-link" href="#">3</a></li>
   					<li class="page-item"><a class="page-link" href="#">4</a></li>
   					<li class="page-item"><a class="page-link" href="#">5</a></li>
   					<li class="page-item"><a class="page-link" href="#">下一页</a></li>
   					<li class="page-item"><a class="page-link" href="#">末页</a></li>
   				</ul>
   			</nav>
   		</div>
   	</div>
   
   	<!-- 尾部 -->
   	<footer class="bg-dark">
   		<div class="container">
   			<div class="row">
   				<!-- 二维码 -->
   				<div class="col-4 qrcode">
   					<img src="https://uploadfiles.nowcoder.com/app/app_download.png" class="img-thumbnail" style="width:136px;" />
   				</div>
   				<!-- 公司信息 -->
   				<div class="col-8 detail-info">
   					<div class="row">
   						<div class="col">
   							<ul class="nav">
   								<li class="nav-item">
   									<a class="nav-link text-light" href="#">关于我们</a>
   								</li>
   								<li class="nav-item">
   									<a class="nav-link text-light" href="#">加入我们</a>
   								</li>
   								<li class="nav-item">
   									<a class="nav-link text-light" href="#">意见反馈</a>
   								</li>
   								<li class="nav-item">
   									<a class="nav-link text-light" href="#">企业服务</a>
   								</li>
   								<li class="nav-item">
   									<a class="nav-link text-light" href="#">联系我们</a>
   								</li>
   								<li class="nav-item">
   									<a class="nav-link text-light" href="#">免责声明</a>
   								</li>
   								<li class="nav-item">
   									<a class="nav-link text-light" href="#">友情链接</a>
   								</li>
   							</ul>
   						</div>
   					</div>
   					<div class="row">
   						<div class="col">
   							<ul class="nav btn-group-vertical company-info">
   								<li class="nav-item text-white-50">
   									公司地址：北京市朝阳区大屯路东金泉时代3-2708北京牛客科技有限公司
   								</li>
   								<li class="nav-item text-white-50">
   									联系方式：010-60728802(电话)&nbsp;&nbsp;&nbsp;&nbsp;admin@nowcoder.com
   								</li>
   								<li class="nav-item text-white-50">
   									牛客科技©2018 All rights reserved
   								</li>
   								<li class="nav-item text-white-50">
   									京ICP备14055008号-4 &nbsp;&nbsp;&nbsp;&nbsp;
   									<img src="http://static.nowcoder.com/company/images/res/ghs.png" style="width:18px;" />
   									京公网安备 11010502036488号
   								</li>
   							</ul>
   						</div>
   					</div>
   				</div>
   			</div>
   		</div>
   	</footer>
   </div>
   
   <script src="https://code.jquery.com/jquery-3.3.1.min.js" crossorigin="anonymous"></script>
   <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" crossorigin="anonymous"></script>
   <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" crossorigin="anonymous"></script>
   <script th:src="@{/js/global.js}"></script>
   <script th:src="@{/js/letter.js}"></script>
   </body>
   </html>
   ```
   
6. 当点击一个会话的时候，可以显示每一条会话信息【注：只有登录了才能看到消息，所以无需担心用户为`null`的问题】，并且查看详情的时候将未读消息设置为已读

   ```java
   @RequestMapping(value = "/letter/detail/{conversationId}", method = RequestMethod.GET)
   public String getConversationDetail(@PathVariable(value = "conversationId") String conversationId, Model mo
       //分页信息
       page.setLimit(5);
       page.setPath("/letter/detail/" + conversationId);
       page.setRows(messageService.findLetterCount(conversationId));
       //私信列表
       List<Message> letterList = messageService.findLetters(conversationId, page.getStart(), page.getLimit())
       List<Map<String, Object>> mapList = new ArrayList<>();
       //每条私信里面有用户头像信息
       if (letterList != null) {
           for (Message letter : letterList) {
               //使用 Map 数据结构存储数据
               Map<String, Object> map = new HashMap<>();
               map.put("letter", letter);
               map.put("fromUser", userService.findUserById(letter.getFromId()));
               //将 Map 数据结构资源包放到一个大的资源包中
               mapList.add(map);
           }
       }
       model.addAttribute("letters", mapList);
       model.addAttribute("target", getLetterTarget(conversationId));
       //点开以后之前未读消息将变成已读 getLetterIds 封装专门的方法获取未读消息的 ids
       List<Integer> unReadIds = getLetterIds(letterList);
       //如果存在未读消息，则将未读消息设置为已读
       if (!unReadIds.isEmpty()) {
           messageService.updateStatus(unReadIds, 1);
       }
       return "/site/letter-detail";
   }
   //不要显示当前用户的信息，尽量显示他人的用户信息
   private User getLetterTarget(String conversationId) {
       String[] ids = conversationId.split("_");
       int id0 = Integer.parseInt(ids[0]);
       int id1 = Integer.parseInt(ids[1]);
       if (hostHolder.getUser().getId() == id0) {
           return userService.findUserById(id1);
       } else {
           return userService.findUserById(id0);
       }
   }
   //获取当前会话中未读消息的 id
   private List<Integer> getLetterIds(List<Message> messageList) {
       List<Integer> unReadIds = new ArrayList<>();
       if (messageList != null) {
           for (Message message : messageList) {
               //如果当前状态为 0 表示未读消息，并且当前用户是接收方
               if (message.getStatus() == 0 && hostHolder.getUser().getId() == message.getToId()) {
                   unReadIds.add(message.getId());
               }
           }
       }
       return unReadIds;
   }
   ```

#### 5.7.9 发送私信

发送私信的功能开发就是一套标准的组合拳，从数据层再到业务层再到表现层然后是前端，发送私信的开发可以跟发送帖子的功能一样，也是采用异步的方式发送私信【也就是说要用到`AJAX`】，发送成功后刷新私信列表

- 数据层：增添私信的`SQL` + 修改私信状态的`SQL`，状态分为已读未读和删除三种状态

  ```java
  //添加私信
  public abstract int insertMessage(Message message);
  //修改私信状态 ---> 一个会话里头有多个私信，一旦打开会话需要将该会话中的所有私信都设置为已读
  public abstract int updateStatus(List<Integer> ids, int status);
  ```

  这里的更新状态因为是更新多条数据，也就意味着需要在`SQL`中使用循环遍历

  ```xml
  <insert id="insertMessage" parameterType="message" keyProperty="id" keyColumn="id">
      insert into message(<include refid="insertFields"/>) values(#{fromId},#{toId},#{conversationId},#{content},#{status},#{createTime});
  </insert>
  
  <update id="updateStatus">
      update message set status = #{status} where id in
      <foreach collection="ids" item="id" open="(" separator="," close=")">
          #{id}
      </foreach>
  </update>
  ```

- 业务层：直接调用`MessageMapper`返回数据即可，最主要的一步就是在调用之前将私信中的内容过滤下敏感词然后防范下`XSS`攻击

  ```java
  @Override
  public int insertMessage(Message message) {
      message.setContent(sensitiveFilter.filter(message.getContent
      message.setContent(HtmlUtils.htmlEscape(message.getContent()
      return messageMapper.insertMessage(message);
  }
  @Override
  public int updateStatus(List<Integer> ids, int status) {
      return messageMapper.updateStatus(ids, status);
  }
  ```

- 表现层：由于发送私信的时候获取到的是用户名，所以应该在`UserService`中增添一个根据用户名查找用户的功能

  ```java
   @Override
   public User selectUserByUsername(String username) {
       return userMapper.selectUserByName(username);
   }
  ```

  接收前端传来的用户名和内容，创建`message`对象

  ```java
   @RequestMapping(path = "/letter/send", method = RequestMethod.POST)
   @ResponseBody
   public String sendLetter(String toName, String content) {
       //根据用户名查找用户
       User toUser = userService.selectUserByUsername(toName);
       //获取收信人用户 ID
       int toId = toUser.getId();
       //获取当前用户的 ID
       User fromUser = hostHolder.getUser();
       int fromId = fromUser.getId();
       Message message = new Message();
       message.setContent(content);
       message.setFromId(fromId);
       message.setToId(toId);
       message.setStatus(0);
       message.setCreateTime(new Date());
       message.setConversationId(fromId < toId ? (fromId + "_" + toId) : (toId + "_" + fromId));
       messageService.insertMessage(message);
       return CommunityUtils.getJsonString(0);
   }
  ```

  下面用账号`test`发送私信给`testt`，当查看的时候设置私信为已读：

  ![](https://img-blog.csdnimg.cn/0f96dc48c0594dc98285c426e2c958dd.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBAQ3JBY0tlUi0x,size_20,color_FFFFFF,t_70,g_se,x_16)

  ![](https://img-blog.csdnimg.cn/2dc0935992024d569dbcfb9ed21230c0.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBAQ3JBY0tlUi0x,size_20,color_FFFFFF,t_70,g_se,x_16)

#### 5.7.10 统一处理异常

统一处理异常需要使用到四种注解，其中最大的注解就是：`@ControllerAdvice`

- `@ControllerAdvice`：用于修饰类，表示该类是`Controller`全局配置类，在此类中，可以对`Controller`进行如下三种全局配置：异常处理方案`@ExceptionHadnler`、绑定参数方案`@ModelAttribute`以及绑定数据方案`@DataBinder`

  - 异常处理方案：`@ExceptionHandler`用于修饰方法，该方法会在`Controller`出现异常后调用，用于处理捕获到的异常
  - 绑定数据方案：`@DataBinder`用于修饰方法，该方法会在`Controller`方法执行之前调用，用于绑定数据
  - 绑定参数方案：`@ModelAttribute`，该方法会在`Controller`方法执行之前调用，用于绑定参数

- 处理异常传统的方式就是搞一个`404 500`这些界面，然后访问出错的时候就跳转到这些界面，比较单一

- 第二种使用`@ControllerAdvice`，判断是否为异步请求从而做决定返回异步方式的结果还是重定向到`error`界面

  ```java
  package com.zwm.controller.advice;
  
  import com.zwm.util.CommunityUtils;
  import org.slf4j.Logger;
  import org.slf4j.LoggerFactory;
  import org.springframework.stereotype.Controller;
  import org.springframework.web.bind.annotation.ControllerAdvice;
  import org.springframework.web.bind.annotation.ExceptionHandler;
  
  import javax.servlet.http.HttpServletRequest;
  import javax.servlet.http.HttpServletResponse;
  import java.io.IOException;
  import java.io.PrintWriter;
  
  @ControllerAdvice(annotations = {Controller.class})
  public class ExceptionAdvice {
  
      private static final Logger logger = LoggerFactory.getLogger(ExceptionAdvice.class);
  
      @ExceptionHandler({Exception.class})
      public void exceptionHandler(Exception exception, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException {
          //报错日志
          logger.error("服务器发送异常：" + exception.getMessage());
          for (StackTraceElement stackTraceElement : exception.getStackTrace()) {
              logger.error(stackTraceElement.toString());
          }
          //根据请求判断是不是异步请求
          String xRequestedWith = httpServletRequest.getHeader("x-requested-with");
          if ("XMLHttpRequest".equals(xRequestedWith)) {
              httpServletResponse.setContentType("application/plain;charset-utf-8");
              PrintWriter printWriter = httpServletResponse.getWriter();
              printWriter.write(CommunityUtils.getJsonString(1, "服务器异常"));
          } else {
              httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/error");
          }
      }
  }
  ```

#### 5.7.11 统一记录日志

统一记录日志需要使用到面向切面编程，这样就可以避免将业务代码和记录日志的代码交织在一起，很冗余，也降低了代码的耦合度，`AOP`需要学习的知识主要是：切面、通知、切入点、连接点

一共有五种通知类型分别是：前置通知`@Before`、后置通知`@AfterReturning`、环绕通知`@Around`、异常通知`@AfterThrowing`、最终通知`@After`

1. 插入`AOP`依赖：

   ```xml
   <dependency>
   	<groupId>org.springframework.boot</groupId>
   	<artifactId>spring-boot-starter-aop</artifactId>
   </dependency>
   ```

2. 创建切面

   ```java
   package com.zwm.aspect;
   
   import org.aspectj.lang.JoinPoint;
   import org.aspectj.lang.annotation.Aspect;
   import org.aspectj.lang.annotation.Before;
   import org.aspectj.lang.annotation.Pointcut;
   import org.slf4j.Logger;
   import org.slf4j.LoggerFactory;
   import org.springframework.stereotype.Component;
   import org.springframework.web.context.request.RequestContextHolder;
   import org.springframework.web.context.request.ServletRequestAttributes;
   
   import javax.servlet.http.HttpServletRequest;
   import java.text.SimpleDateFormat;
   import java.util.Date;
   
   @Aspect
   @Component
   public class ServiceLogAspect {
       private static final Logger logger = LoggerFactory.getLogger(ServiceLogAspect.class);
   
       //设置切入点 ---> service
       @Pointcut(value = "execution(* com.zwm.service.impl.*.*(..))")
       public void pointcut() {
       }
   
       //前置通知记录日志
       @Before(value = "pointcut()")
       public void before(JoinPoint joinPoint) {
           //在目标方法执行前执行
           //1.获取用户IP
           ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
           HttpServletRequest httpServletRequest = servletRequestAttributes.getRequest();
           String ip = httpServletRequest.getRemoteHost();
           //2.获取访问时间
           String now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
           //3.获取用户调用的组件方法 ---> 类名.方法名
           String target = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();
           logger.info(String.format("用户[%s]，在[%s]访问了[%s].", ip, now, target));
       }
   }
   ```


### 5.8 `Redis`点赞关注+优化登录

#### 5.8.1 安装`windows`版本的`Redis`

下载地址：[Releases · microsoftarchive/redis (github.com)](https://github.com/microsoftarchive/redis/releases)+配置环境变量

#### 5.8.2 使用`Redis`

```powershell
# 安装后默认服务已经开启，这里开启客户端
redis-cli

# 选择库，总共有：0-15 16个库
select 0
select 1
select 15

# 清空当前库
flushdb

# String 类型入库
set test:count 1
incr test:count
decr test:count
get test:count

# Hash 类型入库[可以一个key对应多个value]
# 这里表示有个 test:user 的 map 存放了 id:1 username:zhangsan 两个键值对
hset test:user id 1
hset test:user username zhangsan
hget test:user id
hget test:user username

# List 类型入库【可以看作是一个横向容器】
# 插入后的实际排序为：103 102 101
# 这里表示有一个名为 test:ids 的列表从左边存放 101 102 103 三个数据
lpush test:ids 101 102 103
llen test:ids
lindex test:ids 0
lindex test:ids 1
lindex test:ids 2
lrange test:ids 0 2
lpop test:ids ---> "103"
#相对应的还有：rpush rlen rindex rrange rpop

# Set 类型入库【无序 + 不重复】
# 这里表示有一个 test:teachers 集合存放了 aaa bbb ccc ddd eee fff 六个元素
sadd test:teachers aaa bbb ccc ddd eee fff
# 获取 Set 类型长度
scard test:teachers
# 随机弹出数据【可用于抽奖】
spop test:teachers
# 查看集合中还剩什么元素：具体的
smembers test:teachers

# 有序集合 类型入库【不重复】【用分数来排序】
# zadd 添加元素
zadd test:students 10 aaa 20 bbb 30 ccc
# zcard 集合长度
zcard test:students
# zrank 排名
zrank test:students aaa ---> 0
# zrange 获取某个范围的元素
zrange test:students 0 2
# zscore 获取某个元素的分数
zscore test:students ccc

# 获取所有的 key
keys *
# 获取所有以 test 开头的 key
keys test*
# 获取某个 key 的类型
type test:students
# 判断是否存在某个 key
exists test:students
# 删除某个 key
del test:students
# 设置某个 key 的过期时间【单位：秒】
expire test:students 10
```

#### 5.8.3 `SpringBoot`整合`Redis`

- 这里见到介绍下：`Spring Data Redis`
  - `Spring Data Redis`对`Redis`底层开发包进行了高度封装形成了一个类就叫做`RedisTemplate`，开发人员只需要对`RedisTemplate`进行简单的配置然后使用`RedisTemplate`就可以对`Redis`实行各种操作、异常处理以及序列化等
  - 同一类型的操作封装为：`operation`接口：
    - `ValueOperations`：简单`Key-Value`操作
    - `SetOperations`：`Set`无序集合数据操作
    - `HashOperations`：`Map`类型数据操作
    - `ZSetOperations`：`ZSet`有序集合数据操作
    - `ListOperations`：`List`列表数据操作
  - 提供了对`Key`的绑定便捷化操作`API`，可以通过`Bound`封装指定的`Key`，然后进行一系列的操作而无需之间显示再次指定`Key`，即：`BoundKeyOperations`：
    - `BoundValueOperations`
    - `BoundSetOperations`
    - `BoundListOperations`
    - `BoundZSetOperations`
    - `BoundHashOperations`

- 引入依赖：`spring-boot-starter-data-redis`

  ```xml
  <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-data-redis</artifactId>
      <version>2.6.4</version>
  </dependency>
  ```

  【`version`可以省略，因为版本已经在父`pom`中已经定义好了】

- 配置`Redis`

  - 配置数据库参数

    `application.properties`：

    ```properties
    spring.redis.database=8
    spring.redis.host=localhost
    spring.redis.port=6379
    ```

  - 编写配置类，构造`RedisTemplate`【`SpringBoot`有配好的一套但是它的`key`类型是`Object`类型用起来不方便，所以还是自己配一套的好】，原配置代码如下：

    ```java
    /*
     * Copyright 2012-2021 the original author or authors.
     *
     * Licensed under the Apache License, Version 2.0 (the "License");
     * you may not use this file except in compliance with the License.
     * You may obtain a copy of the License at
     *
     *      https://www.apache.org/licenses/LICENSE-2.0
     *
     * Unless required by applicable law or agreed to in writing, software
     * distributed under the License is distributed on an "AS IS" BASIS,
     * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
     * See the License for the specific language governing permissions and
     * limitations under the License.
     */
    
    package org.springframework.boot.autoconfigure.data.redis;
    
    import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
    import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
    import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
    import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
    import org.springframework.boot.context.properties.EnableConfigurationProperties;
    import org.springframework.context.annotation.Bean;
    import org.springframework.context.annotation.Configuration;
    import org.springframework.context.annotation.Import;
    import org.springframework.data.redis.connection.RedisConnectionFactory;
    import org.springframework.data.redis.core.RedisOperations;
    import org.springframework.data.redis.core.RedisTemplate;
    import org.springframework.data.redis.core.StringRedisTemplate;
    
    /**
     * {@link EnableAutoConfiguration Auto-configuration} for Spring Data's Redis support.
     *
     * @author Dave Syer
     * @author Andy Wilkinson
     * @author Christian Dupuis
     * @author Christoph Strobl
     * @author Phillip Webb
     * @author Eddú Meléndez
     * @author Stephane Nicoll
     * @author Marco Aust
     * @author Mark Paluch
     * @since 1.0.0
     */
    @Configuration(proxyBeanMethods = false)
    @ConditionalOnClass(RedisOperations.class)
    @EnableConfigurationProperties(RedisProperties.class)
    @Import({ LettuceConnectionConfiguration.class, JedisConnectionConfiguration.class })
    public class RedisAutoConfiguration {
    
    	@Bean
    	@ConditionalOnMissingBean(name = "redisTemplate")
    	@ConditionalOnSingleCandidate(RedisConnectionFactory.class)
    	public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
    		RedisTemplate<Object, Object> template = new RedisTemplate<>();
    		template.setConnectionFactory(redisConnectionFactory);
    		return template;
    	}
    
    	@Bean
    	@ConditionalOnMissingBean
    	@ConditionalOnSingleCandidate(RedisConnectionFactory.class)
    	public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
    		return new StringRedisTemplate(redisConnectionFactory);
    	}
    
    }
    ```

    `Ctrl + N`可以搜索出`RedisAutoConfiguration`，这是`SpringBoot`配置的`Redis`，可以模仿上述创建一个`RedisConfig`：

    ```java
    package com.zwm.config;
    
    import org.springframework.context.annotation.Bean;
    import org.springframework.context.annotation.Configuration;
    import org.springframework.data.redis.connection.RedisConnectionFactory;
    import org.springframework.data.redis.core.RedisTemplate;
    import org.springframework.data.redis.serializer.RedisSerializer;
    
    @Configuration
    public class RedisConfig {
    
        @Bean
        public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
            RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
            redisTemplate.setConnectionFactory(redisConnectionFactory);
            //设置key的序列化方式
            redisTemplate.setKeySerializer(RedisSerializer.string());
            //设置value的序列化方式
            redisTemplate.setValueSerializer(RedisSerializer.json());
            //设置hash的key序列化方式
            redisTemplate.setHashKeySerializer(RedisSerializer.json());
            //设置hash的value序列化方式
            redisTemplate.setHashKeySerializer(RedisSerializer.json());
            redisTemplate.afterPropertiesSet();
            return redisTemplate;
        }
    }
    ```

- 访问`Redis`

  - `redisTemplate.opsForValue();`
  - `redisTemplate.opsForHash();`
  - `redisTemplate.opsForList();`
  - `redisTemplate.opsForSet();`
  - `redisTemplate.opsForzSet();`

  测试`String`类型数据：

  ```java
  System.out.println("----------String----------");
  String stringKey = "test:count";
  redisTemplate.opsForValue().set(stringKey, 1);
  System.out.println(redisTemplate.opsForValue().get(stringKey));
  System.out.println(redisTemplate.opsForValue().increment(stringKey));
  System.out.println(redisTemplate.opsForValue().decrement(stringKey));
  ```

  测试`Map`类型数据：

  ```java
  System.out.println("----------Hash----------");
  String hashKey = "test:user";
  redisTemplate.opsForHash().put(hashKey, "id", 1);
  redisTemplate.opsForHash().put(hashKey, "username", "ZhangSan");
  System.out.println(redisTemplate.opsForHash().get(hashKey, "id"));
  System.out.println(redisTemplate.opsForHash().get(hashKey, "username"));
  ```

  测试`List`类型数据：

  ```java
  System.out.println("----------List----------");
  String listKey = "test:ids";
  System.out.println(redisTemplate.opsForList().leftPush(listKey, "aaa"));
  System.out.println(redisTemplate.opsForList().leftPush(listKey, "bbb"));
  System.out.println(redisTemplate.opsForList().leftPush(listKey, "ccc"));
  System.out.println(redisTemplate.opsForList().size(listKey));
  System.out.println(redisTemplate.opsForList().index(listKey, 0));
  System.out.println(redisTemplate.opsForList().index(listKey, 1));
  System.out.println(redisTemplate.opsForList().index(listKey, 2));
  System.out.println(redisTemplate.opsForList().range(listKey, 0, 2));
  System.out.println(redisTemplate.opsForList().leftPop(listKey));
  System.out.println(redisTemplate.opsForList().leftPop(listKey));
  System.out.println(redisTemplate.opsForList().leftPop(listKey));
  ```

  测试`Set`类型数据：

  ```java
  System.out.println("----------Set----------");
  String setKey = "test:teachers";
  redisTemplate.opsForSet().add(setKey, "teacherA", "teacherB", "teacherC");
  System.out.println(redisTemplate.opsForSet().size(setKey));
  System.out.println(redisTemplate.opsForSet().members(setKey));
  System.out.println(redisTemplate.opsForSet().pop(setKey));
  ```

  测试`ZSet`类型数据：

  ```java
   System.out.println("----------ZSet----------");
   String zSetKey = "test:students";
   redisTemplate.opsForZSet().add(zSetKey, "studentA", 50);
   redisTemplate.opsForZSet().add(zSetKey, "studentB", 60);
   redisTemplate.opsForZSet().add(zSetKey, "studentC", 70);
   redisTemplate.opsForZSet().add(zSetKey, "studentD", 80);
   redisTemplate.opsForZSet().add(zSetKey, "studentE", 90);
   redisTemplate.opsForZSet().add(zSetKey, "studentF", 100);
   System.out.println(redisTemplate.opsForZSet().score(zSetKey, "studentF"));
   System.out.println(redisTemplate.opsForZSet().range(zSetKey, 0, 5));
   System.out.println(redisTemplate.opsForZSet().zCard(zSetKey));
   System.out.println(redisTemplate.opsForZSet().reverseRange(zSetKey, 0, 4));
  ```

  测试`Keys`：

  ```java
  System.out.println("----------Key----------");
  System.out.println(redisTemplate.hasKey("test:teachers"));
  redisTemplate.expire("test:teachers", 10, TimeUnit.SECONDS);
  System.out.println(redisTemplate.keys("*"));
  ```

  测试事务：

  ```java
   System.out.println("----------Transaction----------");
   //在事务内是无法查询数据的，redis为了更高的效率，会将事务中的每条处理都放入到一个队列中，所以在事务中的查询是查询不到任何结果的
   Object result = redisTemplate.execute(new SessionCallback() {
       @Override
       public Object execute(RedisOperations redisOperations) throws DataAccessException {
           String setKey = "text:tx";
           //开启事务
           redisOperations.multi();
           redisOperations.opsForSet().add(setKey, "ZhangSan");
           redisOperations.opsForSet().add(setKey, "LiSi");
           redisOperations.opsForSet().add(setKey, "WangWu");
           //事务中查询无法查询到
           System.out.println(redisOperations.opsForSet().members(setKey));
           //提交事务
           return redisOperations.exec();
       }
   });
   System.out.println(result);
  ```


#### 5.8.4 点赞功能

- 因为可能同时有很多人给一个用户点赞，所以要注重性能，那么存储点赞的信息最好是存储在`Redis`数据库中
  - 支持对帖子、评论点赞
  - 第一次点赞，第二次再点击点赞按钮表示取消点赞
- 首页点赞数量
  - 统计帖子的点赞数量
- 详情页点赞数量
  - 统计点赞数量
  - 显示点赞状态

1. 完成点赞功能

   1. 封装`Key`工具类

      ```java
      package com.zwm.util;
      
      public class RedisKeyUtil {
          private static final String SPLIT = ":";
          private static final String PREFIX_ENTITY_LIKE = "like:entity";
      
          //某个实体的赞，包括帖子的赞和评论的赞，传入实体类型判断是帖子还是评论的赞，传入实体ID判断是哪个帖子或者哪个评论
          //用一个集合，因为集合是无序不重复的，当存入 userId 的时候表示某个 id 为 userId 的用户给该实体点了个赞
          //如果一个用户即给帖子点了赞又给评论点了赞不是重复了吗？
          //不对，这个 entityType 和 entityId 是不一样的，key 不一样就不用担心这个问题
          //使用集合可以防止重复 ---> 因为点双数的赞就是取消点赞
          public static String getEntityLikeKey(int entityType, int entityId) {
              return PREFIX_ENTITY_LIKE + SPLIT + entityType + SPLIT + entityId;
          }
      }
      ```

   2. 完成点赞+取消点赞【完成点赞的】、查询点赞的数量、查询点赞的状态

      ```java
      package com.zwm.service.impl;
      
      import com.zwm.service.RedisService;
      import com.zwm.util.RedisKeyUtil;
      import org.springframework.beans.factory.annotation.Autowired;
      import org.springframework.data.redis.core.RedisTemplate;
      
      public class RedisServiceImpl implements RedisService {
      
          @Autowired
          private RedisTemplate redisTemplate;
      
          @Override
          //实现给帖子或者评论点赞功能
          public void like(int userId, int entityType, int entityId) {
              String entityKey = RedisKeyUtil.getEntityLikeKey(entityType, entityId);
              //是点赞还是取消点赞？
              boolean isLike = redisTemplate.opsForSet().isMember(entityKey, userId);
              if (isLike) {
                  //说明已经点赞了，所以这里是取消点赞
                  redisTemplate.opsForSet().remove(entityKey, userId);
              } else {
                  //说明需要点赞
                  //这里表示在 entityKey 中存放了
                  redisTemplate.opsForSet().add(entityKey, userId);
              }
          }
      
          //点赞的数量，点赞上述已经完成，这里需要查询点赞的数量
          public long findLikeNumbers(int entityType, int entityId) {
              //返回点赞的数据
              return redisTemplate.opsForSet().size(RedisKeyUtil.getEntityLikeKey(entityType, entityId));
          }
      
          //点赞的状态，查询某个用户是否点赞了，如果点赞前端将显示不同的样式
          /*public boolean findLikeStatus(int userId, int entityType, int entityId) {
              return redisTemplate.opsForSet().isMember(RedisKeyUtil.getEntityLikeKey(entityType, entityId), userId);
          }*/
      
          //试想，点赞的状态只有点赞这种吗？难道我不能点踩吗？所以用 boolean 数据类型无法满足需求，这里可以巧妙地转换成 int 类型，根据不同的数字判断是点赞还是点踩等
          public int findLikeStatus(int userId, int entityType, int entityId) {
              return redisTemplate.opsForSet().isMember(RedisKeyUtil.getEntityLikeKey(entityType, entityId), userId) ? 1 : 0;
          }
      }
      ```

   3. 思考：前端用户点赞的时候，每次点赞和取消点赞，更新点赞数量和状态不能说整个页面都刷新，所以这里使用的是异步请求

      注：这里暂时不用判断用户是否已登录，或者可以使用之前的注解：`@LoginRequired`，后续将结合`SpringSecurity`做权限认证

      ```java
      package com.zwm.controller;
      
      import com.zwm.annotation.LoginRequired;
      import com.zwm.entity.User;
      import com.zwm.service.impl.LikeServiceImpl;
      import com.zwm.util.CommunityUtils;
      import com.zwm.util.HostHolder;
      import com.zwm.util.RedisKeyUtil;
      import org.springframework.beans.factory.annotation.Autowired;
      import org.springframework.stereotype.Controller;
      import org.springframework.web.bind.annotation.RequestMapping;
      import org.springframework.web.bind.annotation.RequestMethod;
      import org.springframework.web.bind.annotation.ResponseBody;
      
      import java.util.HashMap;
      import java.util.Map;
      
      @Controller
      public class LikeController {
      
          @Autowired
          private LikeServiceImpl likeService;
      
          @Autowired
          private HostHolder hostHolder;
      
          //发送点赞/取消点赞请求
          @RequestMapping(value = "/like", method = RequestMethod.GET)
          @ResponseBody
          @LoginRequired
          public String like(int entityType, int entityId) {
              User user = hostHolder.getUser();
              likeService.like(user.getId(), entityType, entityId);
              //更新点赞数量
              long likeNumbers = likeService.findLikeNumbers(entityType, entityId);
              //更新点赞状态
              int likeStatus = likeService.findLikeStatus(user.getId(), entityType, entityId);
              //传递给页面
              Map<String, Object> map = new HashMap<>();
              map.put("likeCount", likeNumbers);
              map.put("likeStatus", likeStatus);
              //返回 JSON 字符串数据
              return CommunityUtils.getJsonString(0, null, map);
          }
      }
      ```

   4. 更改前端，启动检验完成功能，发现打开只有帖子的界面是没问题的，但是打开有评论的帖子就有问题，原因是在直接显示帖子详情的时候，没有显示帖子评论的点赞数量和点赞状态，也没有显示帖子的评论的评论的带你赞数量和点赞状态，并且在`index`页面发现帖子点赞数量和帖子不对所以需要在初始化界面时候就显示：

      1. 修改：`HomeController`

         ```java
         if (discussPostList != null) {
             for (DiscussPost discussPost : discussPostList) {
                 Map<String, Object> map = new HashMap<>();
                 map.put("post", discussPost);
                 User user = userService.findUserById(discussPost.getUserId());
                 map.put("user", user);
                 //获取该帖子的点赞数量和当前用户【如果有登录的话】显示点赞状态
                 long likeCount = likeService.findLikeNumbers(ENTITY_TYPE_POST, discussPost.getId());
                 map.put("likeCount", likeCount);
                 discussPosts.add(map);
             }
         }
         ```

      2. 修改`index.html`

      3. 修改`DiscussPostController.java`

         ```java
         //获取该帖子的赞的数量
         long likeCount = likeService.findLikeNumbers(ENTITY_TYPE_POST, discussPostId);
         //如果当前用户不为空，验证状态，是否是已点赞的状态
         int likeStatus = hostHolder.getUser() == null ? 0 : likeService.findLikeStatus(hostHolder.getUser().getId(), ENTITY_TYPE_POST, discussPostId);
         model.addAttribute("likeCount", likeCount);
         model.addAttribute("likeStatus", likeStatus);
         
         //将帖子评论的赞放入map中以便前端获取数据
         long discussPostCommentLikeCount = likeService.findLikeNumbers(ENTITY_TYPE_COMMENT, comment.getId());
         int discussPostCommentLikeStatus = hostHolder.getUser() == null ? 0 : likeService.findLikeStatus(hostHolder.getUser().getId(), ENTITY_TYPE_COMMENT, comment.getId());
         discussPostCommentMap.put("likeCount", discussPostCommentLikeCount);
         discussPostCommentMap.put("likeStatus", discussPostCommentLikeStatus);
         
         //将帖子评论的赞放入map中以便前端获取数据
         long replyCommentsCommentLikeCount = likeService.findLikeNumbers(ENTITY_TYPE_COMMENT, replyCommentsComment.getId());
         int replyCommentsCommentLikeStatus = hostHolder.getUser() == null ? 0 : likeService.findLikeStatus(hostHolder.getUser().getId(), ENTITY_TYPE_COMMENT, replyCommentsComment.getId());
         commentCommentMap.put("likeCount", replyCommentsCommentLikeCount);
         commentCommentMap.put("likeStatus", replyCommentsCommentLikeStatus);
         ```

      4. 修改`discuss-detail.html`

      5. 已登录和未登录显示的首页结果一致，显示如下：

         ![](https://img-blog.csdnimg.cn/4cca3ec4778a43b4a9e6419006d2a26f.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBAQ3JBY0tlUi0x,size_20,color_FFFFFF,t_70,g_se,x_16)

      6. 未登录用户的帖子详情页显示如下：

         ![](https://img-blog.csdnimg.cn/38f7753ec22f44ef8c763cc68169c870.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBAQ3JBY0tlUi0x,size_20,color_FFFFFF,t_70,g_se,x_16)

      7. 已登录的用户的帖子详情页显示如下：

         ![](https://img-blog.csdnimg.cn/214d335b9cdd465aa84d6138d9199393.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBAQ3JBY0tlUi0x,size_20,color_FFFFFF,t_70,g_se,x_16)

#### 5.8.5 个人主页之我收到的赞

这是每次一个用户给另外一个用户的帖子和评论点赞的时候，就会在用户的主页上显示总共点赞的数量，所以需要增添一个`key`用来存储用户的赞的信息，增加跟减少点赞可以使用：`incremnet decrement`

1. 在`RedisKeyUtil`中增添用户`key`

   ```java
   private static final String PREFIX_USER_LIKE = "like:user";
   
   public static String getUserLikeKey(int entityUserId) {
       return PREFIX_USER_LIKE + SPLIT + entityUserId;
   }
   ```

2. 点赞有被点赞的还有执行点赞的两种情况，被点赞的用户变量名叫做：`entityUserId`，点赞的用户我们叫做：`userId`，当`userId`给`entityUserId`的帖子或者评论，这里就需要`entityType`和`entityId`来确定是帖子还是评论，之前只是单纯的给帖子和评论点赞，但是现在我们想跟具体发帖或者发评论的用户绑定上，此时我们只需要在原先的`LikeService`稍加修改下`like`方法即可

   ```java
   //点赞
   public abstract void like(int userId, int entityType, int entityId, int entityUserId);
   ```

   `Redis`使用`increment decrement`会直接从`0`开始初始化，所以这里每当点赞或者取消点赞的时候都可以使用`incr decr`来增加个人主页中相应的赞的数量

   ```java
   @Override
   //实现给帖子或者评论点赞功能
   public void like(int userId, int entityType, int entityId, int entityUserId) {
       //如果这里点赞了，那么响应的用户个人主页显示的获取赞数也要相应的增加或者减少
       String entityKey = RedisKeyUtil.getEntityLikeKey(entityType, entityId);
       String userKey = RedisKeyUtil.getUserLikeKey(entityUserId);
       //是点赞还是取消点赞？
       boolean isLike = redisTemplate.opsForSet().isMember(entityKey, userId);
       if (isLike) {
           //说明已经点赞了，所以这里是取消点赞
           redisTemplate.opsForSet().remove(entityKey, userId);
           redisTemplate.opsForValue().decrement(userKey);
       } else {
           //说明需要点赞
           //这里表示在 entityKey 中存放了
           redisTemplate.opsForSet().add(entityKey, userId);
           redisTemplate.opsForValue().increment(userKey);
       }
   }
   ```

3. 查询用户总共获取的被点赞的数量【包括帖子点赞和评论点赞】 ---> `LikeServiceImpl`

   ```java
   @Override
   public long findUserLikeNumbers(int entityUserId) {
       String entityUserKey = RedisKeyUtil.getUserLikeKey(entityUserId);
       Integer likeCount = (Integer) redisTemplate.opsForValue().get(entityUserKey);
       //return (long) redisTemplate.opsForValue().get(entityUserKey);
       //万一没人给它点赞呢？那redis里边岂不是为空，所以需要判断一下是否为空
       return likeCount == null ? 0 : likeCount;
   }
   ```

4. 更改请求：`LikeController` --->注意这里添加了`entityUserId`参数

   ```java
   //发送点赞/取消点赞请求
   @RequestMapping(value = "/like", method = RequestMethod.POST)
   @ResponseBody
   @LoginRequired
   public String like(int entityType, int entityId, int entityUserId) {
       User user = hostHolder.getUser();
       likeService.like(user.getId(), entityType, entityId, entityUserId);
       //更新点赞数量
       long likeNumbers = likeService.findLikeNumbers(entityType, entityId);
       //更新点赞状态
       int likeStatus = likeService.findLikeStatus(user.getId(), entityType, entityId);
       //传递给页面
       Map<String, Object> map = new HashMap<>();
       map.put("likeCount", likeNumbers);
       map.put("likeStatus", likeStatus);
       //返回 JSON 字符串数据
       return CommunityUtils.getJsonString(0, null, map);
   }
   ```

5. 更改`index.html`

   ```html
   <a class="dropdown-item text-center" th:href="@{|/user/profile/${loginUser.id}|}">个人主页</a>
   
   <a th:href="@{|/user/profile/${map.user.id}|}">
       <img th:src="${map.user.headerUrl}" class="mr-4 rounded-circle" alt="用户头像" style="width:50px;height:50px;">
   </a>
   ```

6. 更改`discuss.html`，传入点赞帖子或者评论的所属用户`id`，还包括这里的用户头像、用户姓名都加上一个网址链接传输`userId`，显示个人主页

   ```html
    <a href="javascript:;" th:onclick="|like(this,1,${post.id},${post.userId});|" class="text-primary">
        <b th:text="${likeStatus==1?'已赞':'赞'}">赞</b> <i th:text="${likeCount}">11</i>
    </a>
   
   <a href="javascript:;" th:onclick="|like(this,2,${cvo.comment.id}, ${cvo.comment.userId});|" class="text-primary"
       <b th:text="${cvo.likeStatus==1?'已赞':'赞'}">赞</b>(<i th:text="${cvo.likeCount}">1</i>)
   </a>
   
   <a href="javascript:;" th:onclick="|like(this,2,${rvo.reply.id},${rvo.reply.userId});|" class="text-prima
       <b th:text="${rvo.likeStatus==1?'已赞':'赞'}">赞</b>(<i th:text="${rvo.likeCount}">1</i>)
   </a>
   ```

7. 更改`discuss.js`，传入`entityUserId`

   ```javascript
   function like(btn, entityType, entityId, entityUserId) {
       $.post(
           CONTEXT_PATH + "/like",
           {"entityType":entityType,"entityId":entityId, "entityUserId":entityUserId},
           function(data) {
               data = $.parseJSON(data);
               if(data.code == 0) {
                   $(btn).children("i").text(data.likeCount);
                   $(btn).children("b").text(data.likeStatus==1?'已赞':"赞");
               } else {
                   alert(data.msg);
               }
           }
       );
   }
   ```

8. 创建访问个人主页的请求`UserController.getProfilePage`，点进每个人的主页都可以看到他的信息，所以这里是根据`userId`来查找数据库中的用户是否存在，存在才继续，不存在则需要报错

   ```java
   @RequestMapping(value = "/profile/{userId}", method = RequestMethod.GET)
   public String getProfilePage(@PathVariable(value = "userId") int userId, Model model) {
       //判断该 userId 的用户是否存在，防止恶意用户
       User user = userService.findUserById(userId);
       if (user == null) throw new RuntimeException("该用户不存在");
       model.addAttribute("user", user);
       //获取当前用户收到的点赞数量
       int likeCount = likeService.findUserLikeNumbers(userId);
       model.addAttribute("likeCount", likeCount);
       return "/site/profile";
   }
   ```

#### 5.8.6 个人主页之我的关注和我的粉丝

使用：`follower`表示粉丝，`followee`表示关注的人，这两个信息存放到`Redis`中，一起放在`RedisKeyUtil`中

说明：

- `A`关注了`B`，可以通过关注一个帖子、评论、问题或者用户本身而关注到`B`，所以我们这里可以放一个`entityType`，实体类型，说明`A`通过某种类型关注到了`B`。
- `A`随机就成了`B`的粉丝

关注这个动作发生在个人主页当中，点开某个人的主页就可以点击`关注TA`

粉丝：`follower:entityType:entityId`

关注：`followee:userId:entityType`

```java
private static final String PREFIX_FOLLOWER = "follower";
private static final String PREFIX_FOLLOWEE = "followee";

//获取关注 ---> 某个用户关注了某个类型的实体 ---> value 为实体 id
public static String getFolloweeKey(int userId, int entityType) {
    return PREFIX_FOLLOWER + SPLIT + userId + SPLIT + entityType;
}
//获取粉丝 ---> 实体类型和实体 Id 可以唯一确定一个 实体
public static String getFollowerKey(int entityType, int entityId) {
    return PREFIX_FOLLOWEE + SPLIT + entityType + SPLIT + entityId;
}
```

因为关注/取消关注是一个全新的业务，所以新建一个：`FollowService`

```java
package com.zwm.service.impl;

import com.zwm.service.FollowService;
import com.zwm.util.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Service;

@Service
public class FollowServiceImpl implements FollowService {

    @Autowired
    RedisTemplate redisTemplate;

    //完成关注功能 ---> userId 关注类型为 entityType 的 entityId
    //因为某个用户关注了另外一个用户，需要完成 followee 和 follower 两个，这两个是捆绑在一起的，需要使用事务
    public void follow(int userId, int entityType, int entityId) {
        redisTemplate.execute(new SessionCallback() {
            @Override
            public Object execute(RedisOperations operations) throws DataAccessException {
                //开启事务
                operations.multi();
                //关注之前首先需要获取 key
                String followeeKey = RedisKeyUtil.getFolloweeKey(userId, entityType);
                //关注，关注需要有一个排序 ---> 到时候会发送通知给被关注的用户
                redisTemplate.opsForZSet().add(followeeKey, entityId, System.currentTimeMillis());
                //某个实体收到了一个粉丝，这个信息也需要存放到数据库中
                //获取粉丝 key ---> 某个实体entityType entityId拥有粉丝 userId
                //我觉得这个很强词夺理，因为它根本就是为后面发送通知消息这个操作来描述现在的场景
                //而不是根据现在的当前业务出发点出发
                //就是因为后面需要开发出，XXX通过帖子/评论XXX关注了你，才需要搞这种东西
                //如果实实在在以当前场景出发的话，应该直接使用 userId ，某个人的粉丝有 XXX 用户，XXX 用户关注了 XXX 用户
                //服了。。。真的服了
                String followerKey = RedisKeyUtil.getFollowerKey(entityType, entityId);
                redisTemplate.opsForZSet().add(followerKey, userId, System.currentTimeMillis());
                return redisTemplate.exec();
            }
        });
    }

    @Override
    public void unfollow(int userId, int entityType, int entityId) {
        redisTemplate.execute(new SessionCallback() {
            @Override
            public Object execute(RedisOperations operations) throws DataAccessException {
                //开启事务
                operations.multi();
                //关注之前首先需要获取 key
                String followeeKey = RedisKeyUtil.getFolloweeKey(userId, entityType);
                //关注，关注需要有一个排序 ---> 到时候会发送通知给被关注的用户
                redisTemplate.opsForZSet().remove(followeeKey, entityId);
                String followerKey = RedisKeyUtil.getFollowerKey(entityType, entityId);
                redisTemplate.opsForZSet().remove(followerKey, userId);
                return redisTemplate.exec();
            }
        });
    }

    //查询某个用户的粉丝个数
    public long followerCount(int entityType, int entityId) {
        String followerKey = RedisKeyUtil.getFollowerKey(entityType, entityId);
        return redisTemplate.opsForZSet().size(followerKey);
    }

    //查询某个用户的关注个数
    public long followeeCount(int userId, int entityType) {
        String followeeKey = RedisKeyUtil.getFolloweeKey(userId, entityType);
        return redisTemplate.opsForZSet().size(followeeKey);
    }

    //查询当前用户是否已关注当前实体
    public boolean hasFollowed(int userId, int entityType, int entityId) {
        System.out.println(redisTemplate.opsForZSet().score(RedisKeyUtil.getFolloweeKey(userId, entityType), entityId) == null ? "null" : redisTemplate.opsForZSet().zCard(RedisKeyUtil.getFolloweeKey(userId, entityType)));
        return redisTemplate.opsForZSet().score(RedisKeyUtil.getFolloweeKey(userId, entityType), entityId) != null ? true : false;
    }
}

```

`FollowController.java`：

```java
package com.zwm.controller;

import com.zwm.entity.User;
import com.zwm.service.impl.FollowServiceImpl;
import com.zwm.util.CommunityUtils;
import com.zwm.util.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class FollowController {
    @Autowired
    private FollowServiceImpl followService;

    @Autowired
    private HostHolder hostHolder;

    //点击关注
    @RequestMapping(value = "/follow", method = RequestMethod.POST)
    @ResponseBody
    public String follow(int entityType, int entityId) {
        //当前用户关注了某人
        User user = hostHolder.getUser();
        System.out.println("什么鬼啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊");
        followService.follow(user.getId(), entityType, entityId);
        return CommunityUtils.getJsonString(0, "已关注");
    }

    //点击取消关注
    @RequestMapping(value = "/unfollow", method = RequestMethod.POST)
    @ResponseBody
    public String unfollow(int entityType, int entityId) {
        //当前用户关注了某人
        User user = hostHolder.getUser();
        followService.unfollow(user.getId(), entityType, entityId);
        return CommunityUtils.getJsonString(0, "已取消关注");
    }
}
```

`UserController.java`

```java
package com.zwm.controller;

import com.zwm.annotation.LoginRequired;
import com.zwm.entity.User;
import com.zwm.service.impl.FollowServiceImpl;
import com.zwm.service.impl.LikeServiceImpl;
import com.zwm.service.impl.UserServiceImpl;
import com.zwm.util.CommunityUtils;
import com.zwm.util.HostHolder;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

import static com.zwm.util.CommunityConstantTwo.ENTITY_TYPE_USER;

@Controller
@RequestMapping(value = "/user")
public class UserController {

    private static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Value("${community.path.update}")
    private String uploadPath;

    @Value("${community.path.domain}")
    private String domain;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private LikeServiceImpl likeService;

    @Autowired
    private FollowServiceImpl followService;

    @LoginRequired
    @RequestMapping(value = "/setting", method = RequestMethod.GET)
    public String getSettingPage() {
        return "/site/setting";
    }

    @LoginRequired
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String uploadHeader(MultipartFile headerImage, Model model) {
        //如果上传文件为空就返回
        if (headerImage == null) {
            model.addAttribute("error", "您还没有选择图片!");
            return "/site/setting";
        }
        //获取图片文件名
        String fileName = headerImage.getOriginalFilename();
        //获取图片文件名后缀
        String suffix = fileName.substring(fileName.lastIndexOf('.'));
        //检测后缀是否符合图片格式
        if (!StringUtils.isBlank(suffix) || ".jpg".equals(suffix) || ".png".equals(suffix) || ".jpeg".equals(suffix)) {
            String uploadFileName = CommunityUtils.generateUUID() + suffix;
            //存放文件到服务器中
            File uploadToServerFile = new File(uploadPath + uploadFileName);
            try {
                headerImage.transferTo(uploadToServerFile);
            } catch (IOException e) {
                logger.error("上传文件失败: " + e.getMessage());
                throw new RuntimeException("上传文件失败,服务器发生异常!", e);
            }
            //跟新用户头像URL地址
            //1. 获取当前线程的用户
            User user = hostHolder.getUser();
            int id = user.getId();
            //2. 制作当前上传头像的URL地址
            //http://localhost/community/user/header/xxx.jpg
            String headerUrl = domain + contextPath + "/user/header/" + uploadFileName;
            //3. 更新用户的头像URL地址
            userService.updateUserHeaderUrl(id, headerUrl);
            return "redirect:/index";
        } else {
            model.addAttribute("error", "文件格式不正确！请选择：jpg png jpeg 格式文件！");
            return "/site/setting";
        }
    }

    //获取头像，访问头像URL地址 ---> RESTFUL 形式 ---> 从 response 中返回回去
    @RequestMapping(value = "/header/{fileName}", method = RequestMethod.GET)
    public void getUserHeader(@PathVariable(value = "fileName") String fileName, HttpServletResponse httpServletResponse) {
        OutputStream outputStream = null;
        FileInputStream fileInputStream = null;
        try {
            //获取后缀名
            httpServletResponse.setContentType("image/" + fileName.substring(fileName.lastIndexOf(".") + 1));
            outputStream = httpServletResponse.getOutputStream();
            fileInputStream = new FileInputStream(uploadPath + fileName);
            int b = 0;
            byte[] bytes = new byte[1024];
            while ((b = fileInputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, b);
            }
        } catch (FileNotFoundException e) {
            logger.error("读取头像失败: " + e.getMessage());
        } catch (IOException e) {
            logger.error("读取头像失败: " + e.getMessage());
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    logger.error("读取头像失败: " + e.getMessage());
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    logger.error("读取头像失败: " + e.getMessage());
                }
            }
        }
    }

    @RequestMapping(value = "/profile/{userId}", method = RequestMethod.GET)
    public String getProfilePage(@PathVariable(value = "userId") int userId, Model model) {
        //判断该 userId 的用户是否存在，防止恶意用户
        User user = userService.findUserById(userId);
        if (user == null) throw new RuntimeException("该用户不存在");
        model.addAttribute("user", user);
        //获取当前用户收到的点赞数量
        int likeCount = likeService.findUserLikeNumbers(userId);
        model.addAttribute("likeCount", likeCount);
        //判断该用户是否已经被关注 ---> 用户是 3
        boolean hasFollowed = false;
        if (hostHolder.getUser() != null) {
            hasFollowed = followService.hasFollowed(hostHolder.getUser().getId(), ENTITY_TYPE_USER, userId);
        }
        model.addAttribute("hasFollowed", hasFollowed);
        //传递粉丝数量
        long followerCount = followService.followerCount(ENTITY_TYPE_USER, user.getId());
        model.addAttribute("followerCount", followerCount);
        //传递关注数量
        long followeeCount = followService.followeeCount(user.getId(), ENTITY_TYPE_USER);
        model.addAttribute("followeeCount", followeeCount);
        return "/site/profile";
    }
}
```

`profile.html`：

```html
<!doctype html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	<link rel="icon" href="https://static.nowcoder.com/images/logo_87_87.png"/>
	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" crossorigin="anonymous">
	<link rel="stylesheet" th:href="@{/css/global.css}" />
	<title>牛客网-个人主页</title>
</head>
<body>
<div class="nk-container">
	<!-- 头部 -->
	<header class="bg-dark sticky-top" th:replace="index::header">
		<div class="container">
			<!-- 导航 -->
			<nav class="navbar navbar-expand-lg navbar-dark">
				<!-- logo -->
				<a class="navbar-brand" href="#"></a>
				<button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
					<span class="navbar-toggler-icon"></span>
				</button>
				<!-- 功能 -->
				<div class="collapse navbar-collapse" id="navbarSupportedContent">
					<ul class="navbar-nav mr-auto">
						<li class="nav-item ml-3 btn-group-vertical">
							<a class="nav-link" href="../index.html">首页</a>
						</li>
						<li class="nav-item ml-3 btn-group-vertical">
							<a class="nav-link position-relative" href="letter.html">消息<span class="badge badge-danger">12</span></a>
						</li>
						<li class="nav-item ml-3 btn-group-vertical">
							<a class="nav-link" href="register.html">注册</a>
						</li>
						<li class="nav-item ml-3 btn-group-vertical">
							<a class="nav-link" href="login.html">登录</a>
						</li>
						<li class="nav-item ml-3 btn-group-vertical dropdown">
							<a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
								<img src="http://images.nowcoder.com/head/1t.png" class="rounded-circle" style="width:30px;"/>
							</a>
							<div class="dropdown-menu" aria-labelledby="navbarDropdown">
								<a class="dropdown-item text-center" href="profile.html">个人主页</a>
								<a class="dropdown-item text-center" href="setting.html">账号设置</a>
								<a class="dropdown-item text-center" href="login.html">退出登录</a>
								<div class="dropdown-divider"></div>
								<span class="dropdown-item text-center text-secondary">nowcoder</span>
							</div>
						</li>
					</ul>
					<!-- 搜索 -->
					<form class="form-inline my-2 my-lg-0" action="search.html">
						<input class="form-control mr-sm-2" type="search" aria-label="Search" />
						<button class="btn btn-outline-light my-2 my-sm-0" type="submit">搜索</button>
					</form>
				</div>
			</nav>
		</div>
	</header>

	<!-- 内容 -->
	<div class="main">
		<div class="container">
			<!-- 选项 -->
			<div class="position-relative">
				<ul class="nav nav-tabs">
					<li class="nav-item">
						<a class="nav-link active" href="profile.html">个人信息</a>
					</li>
					<li class="nav-item">
						<a class="nav-link" href="my-post.html">我的帖子</a>
					</li>
					<li class="nav-item">
						<a class="nav-link" href="my-reply.html">我的回复</a>
					</li>
				</ul>
			</div>
			<!-- 个人信息 -->
			<div class="media mt-5">
				<img th:src="${user.headerUrl}" class="align-self-start mr-4 rounded-circle" alt="用户头像" style="width:50px;">
				<div class="media-body">
					<h5 class="mt-0 text-warning">
						<span th:utext="${user.username}">nowcoder</span>
						<input type="hidden" id="entityId" th:value="${user.id}">
						<button type="button" th:class="|btn ${hasFollowed?'btn-secondary':'btn-info'} btn-sm float-right mr-5 follow-btn|"
								th:text="${hasFollowed?'已关注':'关注TA'}" th:if="${loginUser!=null&&loginUser.id!=user.id}">关注TA</button>
					</h5>
					<div class="text-muted mt-3">
						<span>注册于 <i class="text-muted" th:text="${#dates.format(user.createTime,'yyyy-MM-dd HH:mm:ss')}">2015-06-12 15:20:12</i></span>
					</div>
					<div class="text-muted mt-3 mb-5">
						<span>关注了 <a class="text-primary" href="followee.html" th:text="${followeeCount}">5</a> 人</span>
						<span class="ml-4">关注者 <a class="text-primary" href="follower.html" th:text="${followerCount}">123</a> 人</span>
						<span class="ml-4">获得了 <i class="text-danger" th:text="${likeCount}">87</i> 个赞</span>
					</div>
				</div>
			</div>
		</div>
	</div>

	<!-- 尾部 -->
	<footer class="bg-dark">
		<div class="container">
			<div class="row">
				<!-- 二维码 -->
				<div class="col-4 qrcode">
					<img src="https://uploadfiles.nowcoder.com/app/app_download.png" class="img-thumbnail" style="width:136px;" />
				</div>
				<!-- 公司信息 -->
				<div class="col-8 detail-info">
					<div class="row">
						<div class="col">
							<ul class="nav">
								<li class="nav-item">
									<a class="nav-link text-light" href="#">关于我们</a>
								</li>
								<li class="nav-item">
									<a class="nav-link text-light" href="#">加入我们</a>
								</li>
								<li class="nav-item">
									<a class="nav-link text-light" href="#">意见反馈</a>
								</li>
								<li class="nav-item">
									<a class="nav-link text-light" href="#">企业服务</a>
								</li>
								<li class="nav-item">
									<a class="nav-link text-light" href="#">联系我们</a>
								</li>
								<li class="nav-item">
									<a class="nav-link text-light" href="#">免责声明</a>
								</li>
								<li class="nav-item">
									<a class="nav-link text-light" href="#">友情链接</a>
								</li>
							</ul>
						</div>
					</div>
					<div class="row">
						<div class="col">
							<ul class="nav btn-group-vertical company-info">
								<li class="nav-item text-white-50">
									公司地址：北京市朝阳区大屯路东金泉时代3-2708北京牛客科技有限公司
								</li>
								<li class="nav-item text-white-50">
									联系方式：010-60728802(电话)&nbsp;&nbsp;&nbsp;&nbsp;admin@nowcoder.com
								</li>
								<li class="nav-item text-white-50">
									牛客科技©2018 All rights reserved
								</li>
								<li class="nav-item text-white-50">
									京ICP备14055008号-4 &nbsp;&nbsp;&nbsp;&nbsp;
									<img src="http://static.nowcoder.com/company/images/res/ghs.png" style="width:18px;" />
									京公网安备 11010502036488号
								</li>
							</ul>
						</div>
					</div>
				</div>
			</div>
		</div>
	</footer>
</div>

<script src="https://code.jquery.com/jquery-3.3.1.min.js" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" crossorigin="anonymous"></script>
<script th:src="@{/js/global.js}"></script>
<script th:src="@{/js/profile.js}"></script>
</body>
</html>
```

#### 5.8.7 关注列表

这里我们使用的是`nosql`数据库`Redis`所以直接从业务层开始做

- 业务层：
  - 查询某个用户关注的人的列表 --- 获取`Redis`数据库中的`followee`相关数据
  - 查询某个用户粉丝列表 --- 获取`Redis`数据库中的`follower`相关数据
- 表现层：
  - 用户点击“查询关注的人”之后发送给`DispatcherController`，进一步调用特定的控制器从而完成处理请求的操作
  - 用户点击“查询粉丝”之后发送给`DispatcherController`，进一步调用特定的控制器从而完成处理请求的操作
- 前端：
  - 完成某用户关注列表的显示
  - 完成某用户粉丝列表的显示

1. `FollowServiceImpl.java`：查询某个用户关注的所有人 + 查询某个用户的粉丝所有人【业务层】

   ```java
   //获取关注列表 ---> 支持分页
   public List<Map<String, Object>> getFolloweeUsersList(int userId, int start, int limit) {
       String followeeKey = RedisKeyUtil.getFolloweeKey(userId, ENTITY_TYPE_USER);
       //分页获取所有关注的人的 user_id
       Set<Integer> followeeZSet = redisTemplate.opsForZSet().reverseRange(followeeKey, start, limit - 1);
       //判断到底有没有关注人即有无关注的人
       if (followeeZSet == null) {
           return null;
       } else {
           //如果有关注列表，需要往集合中放 User 用户，还要放置关注时间，所以这里用 map 来存储
           //最后将 Map 放入到整个 List 集合中
           List<Map<String, Object>> followeeList = new ArrayList<>();
           for (Integer followeeUserId : followeeZSet) {
               Map<String, Object> followeeMap = new HashMap<>();
               User user = userService.findUserById(followeeUserId);
               //放入用户
               followeeMap.put("user", user);
               //放入关注时间
               Double followeeTime = redisTemplate.opsForZSet().score(followeeKey, followeeUserId);
               followeeMap.put("followTime", new Date(followeeTime.longValue()));
               followeeList.add(followeeMap);
           }
           return followeeList;
       }
   }
   //获取粉丝列表 ---> 支持分页
   public List<Map<String, Object>> getFollowerUsersList(int userId, int start, int limit) {
       String followerKey = RedisKeyUtil.getFollowerKey(ENTITY_TYPE_USER, userId);
       //获取粉丝列表的集合
       Set<Integer> followerUserIds = redisTemplate.opsForZSet().reverseRange(followerKey, start, limit - 1);
       if (followerKey == null) {
           return null;
       } else {
           List<Map<String, Object>> followerList = new ArrayList<>();
           for (Integer followerUserId : followerUserIds) {
               Map<String, Object> followerMap = new HashMap<>();
               User user = userService.findUserById(followerUserId);
               followerMap.put("user", user);
               //获取成为粉丝的时间
               Double followerTime = redisTemplate.opsForZSet().score(followerKey, followerUserId);
               followerMap.put("followTime", new Date(followerTime.longValue()));
               followerList.add(followerMap);
           }
           return followerList;
       }
   }
   ```

2. `FollowController.java` ---> 【表现层】根据用户请求返回关注列表、粉丝列表

   ```java
   package com.zwm.controller;
   
   import com.zwm.entity.Page;
   import com.zwm.entity.User;
   import com.zwm.service.impl.FollowServiceImpl;
   import com.zwm.service.impl.UserServiceImpl;
   import com.zwm.util.CommunityUtils;
   import com.zwm.util.HostHolder;
   import org.springframework.beans.factory.annotation.Autowired;
   import org.springframework.stereotype.Controller;
   import org.springframework.ui.Model;
   import org.springframework.web.bind.annotation.PathVariable;
   import org.springframework.web.bind.annotation.RequestMapping;
   import org.springframework.web.bind.annotation.RequestMethod;
   import org.springframework.web.bind.annotation.ResponseBody;
   
   import java.util.List;
   import java.util.Map;
   
   import static com.zwm.util.CommunityConstantTwo.ENTITY_TYPE_USER;
   
   @Controller
   public class FollowController {
       @Autowired
       private FollowServiceImpl followService;
   
       @Autowired
       private HostHolder hostHolder;
   
       @Autowired
       private UserServiceImpl userService;
   
       //点击关注
       @RequestMapping(value = "/follow", method = RequestMethod.POST)
       @ResponseBody
       public String follow(int entityType, int entityId) {
           //当前用户关注了某人
           User user = hostHolder.getUser();
           followService.follow(user.getId(), entityType, entityId);
           return CommunityUtils.getJsonString(0, "已关注");
       }
   
       //点击取消关注
       @RequestMapping(value = "/unfollow", method = RequestMethod.POST)
       @ResponseBody
       public String unfollow(int entityType, int entityId) {
           //当前用户关注了某人
           User user = hostHolder.getUser();
           followService.unfollow(user.getId(), entityType, entityId);
           return CommunityUtils.getJsonString(0, "已取消关注");
       }
   
       //获取关注列表
       @RequestMapping(value = "/followees/{userId}", method = RequestMethod.GET)
       public String getFolloweeListPage(@PathVariable(value = "userId") int userId, Page page, Model model) {
           page.setLimit(5);
           page.setPath("/followees/" + userId);
           //查询总共有多少条关注的人数
           page.setRows((int) followService.followeeCount(userId, ENTITY_TYPE_USER));
           //判断当前用户是否为空，如果是空的报错
           User user = userService.findUserById(userId);
           if (user == null) {
               throw new RuntimeException("该用户不存在！");
           }
           //查询出用户是为了显示页面信息："XXX关注的人" "关注XXX的人"
           model.addAttribute("user", user);
           List<Map<String, Object>> followeeList = followService.getFolloweeUsersList(userId, page.getStart(), page.getLimit());
           //判断是不是已经关注过的人【这样就可以直接关注或者想取消关注就取消关注了】
           if (followeeList != null) {
               for (Map<String, Object> followeeMap : followeeList) {
                   User followeeUser = (User) followeeMap.get("user");
                   //判断用户是否已关注
                   boolean hasFollowed = false;
                   if (hostHolder.getUser() == null) {
                       hasFollowed = false;
                   } else {
                       hasFollowed = followService.hasFollowed(hostHolder.getUser().getId(), ENTITY_TYPE_USER, followeeUser.getId());
                   }
                   followeeMap.put("hasFollowed", hasFollowed);
               }
           }
   
           model.addAttribute("users", followeeList);
           return "/site/followee";
       }
   
       //获取粉丝列表
       @RequestMapping(value = "/followers/{userId}", method = RequestMethod.GET)
       public String getFollowerListPage(@PathVariable(value = "userId") int userId, Page page, Model model) {
           page.setLimit(5);
           page.setPath("/followeRs/" + userId);
           //查询总共有多少条关注的人数
           page.setRows((int) followService.followerCount(ENTITY_TYPE_USER, userId));
           //判断当前用户是否为空，如果是空的报错
           User user = userService.findUserById(userId);
           if (user == null) {
               throw new RuntimeException("该用户不存在！");
           }
           //查询出用户是为了显示页面信息："XXX关注的人" "关注XXX的人"
           model.addAttribute("user", user);
           List<Map<String, Object>> followerList = followService.getFollowerUsersList(userId, page.getStart(), page.getLimit());
           //判断是不是已经关注过的人【这样就可以直接关注或者想取消关注就取消关注了】
           if (followerList != null) {
               for (Map<String, Object> followerMap : followerList) {
                   User followerUser = (User) followerMap.get("user");
                   //判断用户是否已关注
                   boolean hasFollowed = false;
                   if (hostHolder.getUser() == null) {
                       hasFollowed = false;
                   } else {
                       hasFollowed = followService.hasFollowed(hostHolder.getUser().getId(), ENTITY_TYPE_USER, followerUser.getId());
                   }
                   followerMap.put("hasFollowed", hasFollowed);
               }
           }
           model.addAttribute("users", followerList);
           return "/site/follower";
       }
   }
   ```

3. 更新`profile.html`+`followee.html`+`follower.html`

4. 结果显示如图：

   ![](https://img-blog.csdnimg.cn/d7c8de292d634264a120124dcda6069a.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBAQ3JBY0tlUi0x,size_20,color_FFFFFF,t_70,g_se,x_16)

   ![](https://img-blog.csdnimg.cn/abaca47803d04549ab205d41bb6c5ed9.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBAQ3JBY0tlUi0x,size_20,color_FFFFFF,t_70,g_se,x_16)

#### 5.8.8 优化登录模块

##### 5.8.8.1 优化验证码

当初制作验证码功能的时候是通过`LoginController.java`存储在`session`里头，这样做会出现如下问题：

1. 验证码可能需要不停地更新访问刷新，对性能要求很高
2. 验证码不应该保存很长的时间而应该很短的时间之内就过期
3. 验证码虽然不存放在客户端这边相对`cookie`来说安全但是存放在服务器，增加了服务器的内存压力
4. 分布式部署的时候，存在`session`共享的问题

以上种种原因引出新思路：将验证码存放在`Redis`

`RedisKeyUtil`生成验证码键：

```java
private static final String PREFIX_KAPTCHA = "kaptcha";

//生成验证码Key
public static String getKaptchaKey(String owner) {
    return PREFIX_KAPTCHA + SPLIT + owner;
}
```

`LoginController.java`改造验证码生成：

```java
//验证码功能 ---> 返回图片
@RequestMapping(value = "/kaptcha", method = RequestMethod.GET)
public void getKaptcha(HttpServletResponse httpServletResponse, HttpSession httpSession) {
    //生成验证码
    String text = kaptchaProducer.createText();
    BufferedImage bufferedImage = kaptchaProducer.createImage(text);
    //保存验证码文件到本地
    //httpSession.setAttribute("kaptcha", text);
    //保存验证码文件到Redis
    String owner = CommunityUtils.generateUUID();
    //将 owner 发送给 cookie，这样才可以获取到redis的键值对
    Cookie cookie = new Cookie("kaptchaOwner", owner);
    cookie.setMaxAge(60);
    cookie.setPath(contextPath);
    //添加cookie返回
	httpServletResponse.addCookie(cookie);
    //制造 key
    String kaptchaKey = RedisKeyUtil.getKaptchaKey(owner);
    //存放 key ---> 设置一分钟超时时间
    redisTemplate.opsForValue().set(kaptchaKey, text, 60, TimeUnit.SECONDS);
    //设置返回的格式
    httpServletResponse.setContentType("image/png");
    try {
        OutputStream outputStream = httpServletResponse.getOutputStream();
        ImageIO.write(bufferedImage, "png", outputStream);
    } catch (IOException e) {
        e.printStackTrace();
    }
}
```

登录时检验验证码`login`方法：

```java
//登录功能
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String userLogin(Model model, HttpServletResponse httpServletResponse, /*HttpSession httpSession,*/ String username, String password, String code, boolean rememberMe, @CookieValue(value = "kaptchaOwner") String kaptchaOwner) {
        //参数分别为：数据 + 响应类型 + 会话 + 用户名 + 密码 + 验证码 + 是否记住我
        //session保存在服务器，内置属性：kaptcha
        //String kaptcha = (String) httpSession.getAttribute("kaptcha");
        String kaptcha = null;
        if (StringUtils.isNotBlank(kaptchaOwner)) {
            //从 redis 获取验证码
            String kaptchaKey = RedisKeyUtil.getKaptchaKey(kaptchaOwner);
            kaptcha = (String) redisTemplate.opsForValue().get(kaptchaKey);
        }
        //如果验证码为空或者不匹配直接返回到登录页面 ---> 检验验证码
        if (StringUtils.isBlank(kaptcha) || StringUtils.isBlank(code) || !kaptcha.equals(code)) {
            model.addAttribute("codeMsg", "验证码不正确!");
            return "/site/login";
        }

        //查询用户名和密码匹配的用户信息 ---> UserService
        //设置存活时间：要么半天要么100天
        int expiredSeconds = rememberMe ? REMEMBER_EXPIRED_SECONDS : DEFAULT_EXPIRED_SECONDS;
        Map<String, String> map = userService.login(username, password, expiredSeconds);
        //验证map，如果包含`ticket`表明账户验证通过，否则表示不通过
        if (map.containsKey("loginTicket")) {
            Cookie cookie = new Cookie("ticket", map.get("loginTicket").toString());
            cookie.setPath(contextPath);
            cookie.setMaxAge(expiredSeconds);
            httpServletResponse.addCookie(cookie);
            //重定向到首页
            return "redirect:/index";
        } else {
            model.addAttribute("usernameMsg", map.get("usernameMsg"));
            model.addAttribute("passwordMsg", map.get("passwordMsg"));
            return "/site/login";
        }
    }
}
```

其实改的东西也没多少，就是`cookie`存放一个`UUID`，这个东西可以获取到`key`然后将验证码绑定到这个`key`存放到`Redis`中，当需要验证的时候，首先从客户端那里拿到`cookie`里面的`owner`相关数据，然后获取到`redis`比对`value`就完事了。

`git`回退版本：

```
git log
git reset --hard 版本号
git push -f 强制推上
```

##### 5.8.8.2 优化登录凭证

创建登陆凭证的`Key`：

```java
private static final String PREFIX_TICKET = "ticket";

//生成登录凭证
public static String getLoginTicketKey(String loginTicket) {
    return PREFIX_TICKET + SPLIT + loginTicket;
}
```

优化`UserServiceImpl.java`用户登录`login`功能：

```java
//前面都顺利执行过来表示用户可以正常登录使用，制作登录凭证
LoginTicket loginTicket = new LoginTicket();
loginTicket.setUserId(user.getId());
loginTicket.setTicket(CommunityUtils.generateUUID());
loginTicket.setStatus(0);
//生存时间根据是否记住决定 ---> 这里由 LoginController 完成传递过来，Service 这边直接赋值即可
loginTicket.setExpired(new Date(System.currentTimeMillis() + expiredSeconds * 1000));
//创建登陆凭证记录存储到数据库中
//loginTicketMapper.insertLoginTicket(loginTicket);
//获取登录凭证的 Key
String ticketKey = RedisKeyUtil.getLoginTicketKey(loginTicket.getTicket());
redisTemplate.opsForValue().set(ticketKey, loginTicket);
```

优化`UserServiceImpl.java`用户登出`logout`功能，取出`loginTicket`将其状态更改为`1`：

```java
//用户登出 ---> 设置当前票据的状态为无效 0-有效 1-无效
public void logout(String ticket) {
    //loginTicketMapper.updateStatusByLoginTicket(ticket, 1);
    String ticketKey = RedisKeyUtil.getLoginTicketKey(ticket);
    LoginTicket loginTicket = (LoginTicket) redisTemplate.opsForValue().get(ticketKey);
    loginTicket.setStatus(1);
    redisTemplate.opsForValue().set(ticketKey, loginTicket);
}
```

登录拦截器需要获取`ticket`然后进一步查询`loginTicket`，所以需要修改下`UserServiceImpl.java`中的`selectLoginTicktByTicket`：

```java
//查询用户登录凭证
public LoginTicket selectLoginTicketByTicket(String ticket) {
    String loginTicketKey = RedisKeyUtil.getLoginTicketKey(ticket);
    //return loginTicketMapper.selectLoginTicketByTicket(ticket);
    return (LoginTicket) redisTemplate.opsForValue().get(loginTicketKey);
}
```

##### 5.8.8.3 缓存用户信息

这里只是做一个思路，需要缓存可以优化的地方其实都可以优化，以根据`user_id`查询用户为例，每次都需要往数据库中查`User`，我们可以将该信息存放在`Redis`当中，并且设置一个超时时间，第一次访问如果缓存中没有就去数据库中找然后缓存到`Redis`，其余的都从数据库中拿，当`User`用户的某一属性值变化时就清除`Redis`中该用户的相关键值对

【注：每次请求请求帖子也好评论也罢还是注册页面还是登录页面，都需要向数据库请求查询`userId`，所以这里拿出来做例子，很具代表性】

创建用户缓存`key`：

```java
private static final String PREFIX_USER = "user";
public static String getUserKey(int userId) {
    return PREFIX_TICKET + SPLIT + userId;
}
```

获取缓存【如果没有缓存就从数据库拿数据后再缓存】+删除缓存：

```java
//获取缓存中的用户数据
public User getUserCache(int userId) {
    String userKey = RedisKeyUtil.getUserKey(userId);
    //如果 userKey 从 Redis 搜出来的用户为空表示没有缓存，需从数据库中拿过来缓存
    User user = (User) redisTemplate.opsForValue().get(userKey);
    if (user == null) {
        user = userMapper.selectUserById(userId);
        redisTemplate.opsForValue().set(userKey, user, 3600, TimeUnit.SECONDS);
    }
    return user;
}

//当 User 舒心更改时清楚缓存
public void deleteUserCache(int userId) {
    String userKey = RedisKeyUtil.getUserKey(userId);
    redisTemplate.delete(userKey);
}
```

重构`findUserById`：

```java
@Override
public User findUserById(int id) {
    //return userMapper.selectUserById(id);
    return getUserCache(id);
}
```

### 5.9 `Kafka`开发系统通知

#### 5.9.1 阻塞队列

阻塞队列在`Java`中就有 ---> `BlockingQueue`，它可以解决线程通信的问题，阻塞方法采用：`put`和`take`形式

阻塞队列使用的是生产者消费者模式：生产者 ---> 产生数据的线程 + 消费者 ---> 使用数据的线程

实现：`ArrayBlockingQueue + LinkedBlockingQueue + PriorityBlockingQueue + SynchronousQueue + DelayQueue`等 

![](https://img-blog.csdnimg.cn/f48af7c76e3a4d82866e4006bbaf5cce.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBAQ3JBY0tlUi0x,size_18,color_FFFFFF,t_70,g_se,x_16)

阻塞队列存在的意义就是：

1. 如果生产者这一方生产的速度很快，但是消费者这边消费速度很慢跟不上，如果没有阻塞队列，生产者就会一直生产一直生产造成性能上的不足
2. 如果消费者这一方的消费速度远远超过生产者的生产速度，那么就会一直等待一直等待，消耗大量性能、内存资源

演示阻塞队列的使用：

```java
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class BlockingQueueTest {
    public static void main(String[] args) {
        BlockingQueue<Integer> blockingQueue = new ArrayBlockingQueue<Integer>(3);
        new Thread(new Producer(blockingQueue)).start();
        for (int i = 0; i < 20; i++) {
            new Thread(new Consumer(blockingQueue)).start();
        }
    }
}

class Producer implements Runnable {

    private BlockingQueue<Integer> blockingQueue;

    public Producer(BlockingQueue<Integer> blockingQueue) {
        this.blockingQueue = blockingQueue;
    }

    @Override
    public void run() {
        //生产者每 20 ms 生产一个消息，生产 100 个消息
        try {
            for (int i = 0; i < 100; i++) {
                Thread.sleep(20);
                blockingQueue.put(i);
                System.out.println(Thread.currentThread().getName() + " 生产消息：" + blockingQueue.size());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class Consumer implements Runnable {
    private BlockingQueue<Integer> blockingQueue;

    public Consumer(BlockingQueue<Integer> blockingQueue) {
        this.blockingQueue = blockingQueue;
    }

    @Override
    public void run() {
        //消费者随机时间消费数据
        try {
            Thread.sleep(new Random().nextInt(1000));
            blockingQueue.take();
            System.out.println(Thread.currentThread().getName() + " 消费消息： " + blockingQueue.size());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
```

#### 5.9.2 `Kafka`入门

之前学习过了`RabbitMQ`，`Kafka`一样的，都是消息队列，常应用于：消息系统、日志收集、用户行为跟踪、流式处理

特点：

- 高吞吐量（对硬盘数据顺序读写，性能很强，高于对内存的随机读取）
- 消息持久化（数据存放到硬盘中，且硬盘价格比内存低容量还比内存大）
- 高可靠性（分布式服务器，集成部署，一台服务器挂了还有另一台）
- 高扩展性（服务器不够用了可以继续加服务器）
- 可以用于处理`TB`级的数据，主要用`Kafka`来发消息

相关术语：

- `Broker`：`Kafka`的单个服务器
- `Zookeeper`：用于管理服务器集群
- `Topic`：存放消息的位置
- `Partition`：消息队列中的索引
- `Offset`：`Topic`中的分区，为了提高容错率以及多存数据
- `Leader Replica`：主副本，处理请求，比如处理消费者的使用请求
- `Follower Replica`：从副本，当主副本挂了的时候，从从副本中选择一个作为主副本

下载：

1. 访问：https://kafka.apache.org/

2. `Download` ---> `Scala 2.13 - kafka_2.13-3.1.0.tgz(asc, sha512)` ---> `We suggest the following site for your download: https://dlcdn.apache.org/kafka/3.1.0/kafka_2.13-3.1.0.tgz`

3. 下载速度极慢......

4. 在`/config`中配置`zookeeper.properties`和`server.properties`中的`dataDir`
   - `log.dirs=/tmp/kafka-logs` ---> `log.dirs=E:/me/kafka_2.13-3.1.0/kafka-logs`
   - `dataDir=/tmp/zookeeper` ---> `dataDir=E:/me/kafka_2.13-3.1.0/zookeeper`
   
5. 启动`kafka`【在`kafka`目录下】：
   - `bin\windows\zookeeper-server-start.bat config\zookeeper.properties`
   - `bin\windows\kafka-server-start.bat config\server.properties`
   
6. 配置生产者并生产消息

   ```powershell
   kafka-topics.bat --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1 --topic test
   
   kafka-topics.bat --list --bootstrap-server localhost:9092
   
   kafka-console-producer.bat --broker-list localhost:9092 --topic test
   ```

7. 配置消费者消费消息

   ```powershell
   kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic test -from-beginning
   ```

8. 如果遇到报错需要将日志文件夹整个删除，`windows`端的`kafka`很不稳定，相比较而言`linux`端的很稳定

#### 5.9.3 `SpringBoot`整合`Kafka`

1. `SpringBoot`整合`Kafka`也需要使用命令启动`Zookeeper`和`Kafka`服务

2. 引入`kafka`依赖：

   ```xml
   <dependency>
       <groupId>org.springframework.kafka</groupId>
       <artifactId>spring-kafka</artifactId>
       <version>2.8.3</version>
   </dependency>
   ```

3. 配置`application.properties`

   ```properties
   # 配置kafka服务器
   spring.kafka.bootstrap-servers=localhost:9092
   # 配置消费者分组id
   spring.kafka.consumer.group.id=community-consumer-group
   # 是否自动提交
   spring.kafka.consumer.enable-auto-commit=true
   # 自动提交的频率
   spring.kafka.consumer.auto-commit-interval=3000
   ```

4. 编写测试类

   ```java
   import com.zwm.CommunityApplication;
   import org.apache.kafka.clients.consumer.ConsumerRecord;
   import org.junit.Test;
   import org.junit.runner.RunWith;
   import org.springframework.beans.factory.annotation.Autowired;
   import org.springframework.boot.test.context.SpringBootTest;
   import org.springframework.kafka.annotation.KafkaListener;
   import org.springframework.kafka.core.KafkaTemplate;
   import org.springframework.stereotype.Component;
   import org.springframework.test.context.ContextConfiguration;
   import org.springframework.test.context.junit4.SpringRunner;
   
   @SpringBootTest
   @RunWith(SpringRunner.class)
   @ContextConfiguration(classes = CommunityApplication.class)
   public class KafkaTest {
       @Autowired
       private KafkaProducer kafkaProducer;
   
       @Test
       public void test() {
           kafkaProducer.sendMessage("tests", "hello?");
           kafkaProducer.sendMessage("tests", "anybody home?");
           try {
               Thread.sleep(1000 * 10);
           } catch (InterruptedException e) {
               e.printStackTrace();
           }
       }
   
   }
   
   @Component
   class KafkaProducer {
       @Autowired
       private KafkaTemplate kafkaTemplate;
   
       //被动生产消息
       public void sendMessage(String topic, String content) {
           kafkaTemplate.send(topic, content);
       }
   }
   
   @Component
   class KafkaConsumer {
       //自动消费消息
       @KafkaListener(topics = {"tests"})
       public void handleMessage(ConsumerRecord consumerRecord) {
           System.out.println(consumerRecord.value());
       }
   }
   ```

#### 5.9.4 发送系统通知

系统通知是非常频繁的一个功能因为我们的用户非常多，所以为了保障性能就要使用到消息队列，为什么需要用到消息队列呢？当用户评论之后我们就要发布通知，当用户点赞之后我们就要发布通知，当用户关注之后我们也要发布通知。也就是说系统发送通知有三种类型：评论点赞加关注【三连】

所以我们可以对三种类型做一个抽象，抽象出三种类型共有特征形成一个事件。使用`topic`来区分是评论点赞和关注这三种类型的哪一个 ---> `entity.Event.java`

```java
package com.zwm.entity;

import java.util.HashMap;
import java.util.Map;

public class Event {
    private String topic; //区分类型 ---> CommunityConstantTwo
    private int userId; //是谁评论点赞关注了
    private int entityType; //评论点赞关注的实体类型
    private int entityId; //评论点赞关注的实体id
    private int entityUserId; //评论点赞关注了哪个用户
    private Map<String, Object> data = new HashMap<>(); //后续可能有其它数据都可以放到 Map 中去

    //增添 Getter 和 Setter 方法支持链式编程并对 Map 做一个小改动

    public String getTopic() {
        return topic;
    }

    public Event setTopic(String topic) {
        this.topic = topic;
        return this;
    }

    public int getUserId() {
        return userId;
    }

    public Event setUserId(int userId) {
        this.userId = userId;
        return this;
    }

    public int getEntityType() {
        return entityType;
    }

    public Event setEntityType(int entityType) {
        this.entityType = entityType;
        return this;
    }

    public int getEntityId() {
        return entityId;
    }

    public Event setEntityId(int entityId) {
        this.entityId = entityId;
        return this;
    }

    public int getEntityUserId() {
        return entityUserId;
    }

    public Event setEntityUserId(int entityUserId) {
        this.entityUserId = entityUserId;
        return this;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public Event setData(String key, Object value) {
        this.data.put(key, value);
        return this;
    }

    @Override
    public String toString() {
        return "Event{" +
                "topic='" + topic + '\'' +
                ", userId=" + userId +
                ", entityType=" + entityType +
                ", entityId=" + entityId +
                ", entityUserId=" + entityUserId +
                ", data=" + data +
                '}';
    }
}
```

在`CommunityConstantTwo.java`加入`TOPIC`常量类型，目前为：评论、点赞、关注三种类型：

```java
//生产者生产评论类型 ---> 实则为系统发送评论通知
String TOPIC_COMMENT = "comment";

//生产者生产点赞类型
String TOPIC_LIKE = "like";

//生产者生产关注类型
String TOPIC_FOLLOW = "follow";
```

生产者生产事件对象，这个事件对象包含了某个用户`userId`对实体要么是帖子要么是评论要么是用户目前就这三种类型`entityType`特定的`entityId`，这三个后续构成了消息的主要内容，比如：`aaa通过帖子评论了你`，点开这个消息还可以跳转到指定的帖子，然后`entityUserId`是目标用户，比如某个人点评论、点赞】关注都有一个目标用户，`entityUserId`就是目标用户，如果有其他的数据可以放到`Map`类型的`data`里面。

生产者生产消息：

```java
package com.zwm.event;

import com.alibaba.fastjson.JSONObject;
import com.zwm.entity.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class EventProducer {

    @Autowired
    private KafkaTemplate kafkaTemplate;

    //生产者生产消息
    public void fireEvent(Event event) {
        kafkaTemplate.send(event.getTopic(), JSONObject.toJSON(event));
    }
}
```

消费者消费消息：

- 这里编写代码的时候总是出现错误：

  ```java
  Caused by: java.lang.IllegalStateException: No group.id found in consumer config, container properties, or @KafkaListener annotation; a group.id is required when group management is used.
  ```

  解决方案：`@KafkaListener(groupId = "test-consumer-group", topics = {TOPIC_COMMENT, TOPIC_LIKE, TOPIC_FOLLOW})`

- 根据传递过来的 Event 往数据库存储 Message 消息，我们规定 from_id = 1 表示系统用户

  ```java
  //规定 userId = 1 为系统用户
  int SYSTEM_USER_ID = 1;
  ```

- 根据传递过来的 Event 往数据库存储 Message 消息，我们规定 from_id = 1 表示系统用户

  ```java
  Message message = new Message();
  message.setFromId(SYSTEM_USER_ID);
  message.setToId(event.getEntityUserId());
  message.setConversationId(event.getTopic());
  message.setCreateTime(new Date());
  ```

- 构造消息内容 ---> 内容为： `XXX`评论了你的帖子/回复
  `XXX`就需要传入`userId`， 帖子/回复就需要传入`entityType`，点击该内容可以跳转到具体的帖子/回复 ---> 所以还需要`entityId`

  ```java
  Map<String, Object> content = new HashMap<>();
  content.put("user", event.getUserId());
  content.put("entityType", event.getEntityType());
  content.put("entityId", event.getEntityId());
  ```

- 如果传递过来的`Event`还有其它内容`data`的话，需要也将这些内容放到消息内容中去

  ```java
  Map<String, Object> data = event.getData();
  if (!data.isEmpty()) {
      for (Map.Entry<String, Object> entry : data.entrySet()) {
          content.put(entry.getKey(), entry.getValue());
      }
  }
  //存入JSON字符串格式的数据
  message.setContent(JSONObject.toJSONString(content));
  ```

- 将消息存入到数据库中

  ```java
  messageService.insertMessage(message);
  ```

- 整个消费者的代码如下：

  ```java
  package com.zwm.event;
  
  import com.alibaba.fastjson.JSON;
  import com.alibaba.fastjson.JSONObject;
  import com.zwm.entity.Event;
  import com.zwm.entity.Message;
  import com.zwm.service.MessageService;
  import com.zwm.service.impl.MessageServiceImpl;
  import org.apache.kafka.clients.consumer.ConsumerRecord;
  import org.slf4j.Logger;
  import org.slf4j.LoggerFactory;
  import org.springframework.beans.factory.annotation.Autowired;
  import org.springframework.kafka.annotation.KafkaListener;
  import org.springframework.stereotype.Component;
  
  import java.util.Date;
  import java.util.HashMap;
  import java.util.Map;
  
  import static com.zwm.util.CommunityConstantTwo.*;
  
  @Component
  public class EventConsumer {
  
      @Autowired
      private MessageServiceImpl messageService;
  
      private static Logger logger = LoggerFactory.getLogger(EventConsumer.class);
  
      //消费者消费消息 ---> 生产者生产的是事件对象
      @KafkaListener(groupId = "test-consumer-group", topics = {TOPIC_COMMENT, TOPIC_LIKE, TOPIC_FOLLOW})
      public void handlerEvent(ConsumerRecord consumerRecord) {
          //如果消费到的消息是null或者是空的，日志报错
          if (consumerRecord == null || consumerRecord.value() == null) {
              logger.error("消息的内容为空!");
              return;
          }
  
          //获取到 JSON 对象转化为 Event 事件对象
          Event event = JSON.parseObject(consumerRecord.value().toString(), Event.class);
          if (event == null) {
              logger.error("消息格式错误!");
              return;
          }
  
          //获取到消息就可以发送系统消息了，当时发送消息的时候使用的是 MessageService 所以这里要使用 MessageService
          //可以打印一下获取到的消息
          System.out.println(event.toString());
          //Event{topic='comment', userId=152, entityType=2, entityId=232, entityUserId=152, data=null}
          //根据传递过来的 Event 往数据库存储 Message 消息，我们规定 from_id = 1 表示系统用户
          Message message = new Message();
          message.setFromId(SYSTEM_USER_ID);
          message.setToId(event.getEntityUserId());
          message.setConversationId(event.getTopic());
          message.setCreateTime(new Date());
          //构造消息内容 ---> 内容为： XXX 评论了你的帖子/回复
          //XXX 就需要传入 userId ， 帖子/回复就需要传入 entityType，点击该内容可以跳转到具体的帖子/回复 ---> 所以还需要 entityId
          Map<String, Object> content = new HashMap<>();
          content.put("user", event.getUserId());
          content.put("entityType", event.getEntityType());
          content.put("entityId", event.getEntityId());
          //如果传递过来的Event还有其它内容data的话，需要也将这些内容放到消息内容中去
          Map<String, Object> data = event.getData();
          if (!data.isEmpty()) {
              for (Map.Entry<String, Object> entry : data.entrySet()) {
                  content.put(entry.getKey(), entry.getValue());
              }
          }
          //存入JSON字符串格式的数据
          message.setContent(JSONObject.toJSONString(content));
          //将消息存入到数据库中
          messageService.insertMessage(message);
      }
  }
  ```

修改评论类`CommentController.java`，当评论帖子或者回复的时候，唤醒生产者，使其生产事件对象

为了防止自动提交报错，这里将`application.properties`修改下：

```properties
spring.kafka.consumer.enable-auto-commit=false
```

```java
//测试收到的事件
Event event = new Event();
event.setTopic(TOPIC_COMMENT)
        .setUserId(user.getId())
        .setEntityType(comment.getEntityType())
        .setEntityId(comment.getEntityId());
//这里需要根据实体类型：帖子还是评论，获取该帖子/评论的主人信息
if (comment.getEntityType() == ENTITY_TYPE_POST) {
    //根据当前 entityId 也就是帖子编号查找到帖子，从而获取 userId 主人 id 编号
    DiscussPost discussPost = discussPostService.selectDiscussPost(comment.getEntityId());
    event.setEntityUserId(discussPost.getUserId());
} else if (comment.getEntityType() == ENTITY_TYPE_COMMENT) {
    //如果是评论回复类型，需要找到该条评论的主人，通过 entityId 找到该评论然后再找到这个评论的主人
    Comment thisComment = commentService.findCommentById(comment.getEntityId());
    event.setEntityUserId(thisComment.getUserId());
}
eventProducer.fireEvent(event);
```

修改帖子类`LikePostController.java` ---> 实现系统通知发送点赞消息

```java
//发送通知给被点赞的人 ---> 如果点赞才发送通知，没点就不要发送了
if (likeStatus == 1) {
    Event event = new Event();
    event.setTopic(TOPIC_LIKE)
            .setUserId(user.getId())
            .setEntityType(entityType)
            .setEntityId(entityId)
            .setEntityUserId(entityUserId);
    //触发点赞
    eventProducer.fireEvent(event);
}
```

修改关注类`FollowController.java` ---> 实现系统通知发送关注消息

```java
 //系统发送通知给被关注人 ---> 目标用户就是 entityId
 Event event = new Event();
 event.setTopic(TOPIC_FOLLOW)
         .setUserId(user.getId())
         .setEntityType(entityType)
         .setEntityId(entityId)
         .setEntityUserId(entityId);
 eventProducer.fireEvent(event);
```

#### 5.9.5 显示系统通知

- 通知列表：显示评论、点赞、关注三种类型的通知
- 通知情况：分页显示某一类主题所包含的通知
- 未读消息：在页面头部显示所有的未读消息数量

1. 通知列表

   - 显示评论、点赞、关注三种类型的通知。
   - 只显示最新的一条消息
   - 左上角显示未读数量
   - 点击通知后可以跳转到详情页

2. 数据层：查询某个类型下最新的系统通知、查询某个类型所包含的系统通知的数量、查询未读的系统通知的数量 ---> `MessageMapper`

   ```java
   //查询某个类型最新的系统通知
   public abstract Message selectLatestNotice(String topic);
   
   //查询某个类型所包含的系统通知数量
   public abstract int selectNoticeCount(String topic);
   
   //查询某个类型所包含的未读通知的数量 不传入类型时代表的是总数
   public abstract int selectUnreadNoticeCount(String topic);
   ```

   ```xml
   <select id="selectLatestNotice" resultType="message">
       select <include refid="selectFields"/> from message
       where id in (select max(id) from message where status != 2 and from_id = 1 and to_id = #{userId} and conversation_id = #{topic})
   </select>
   <select id="selectNoticeCount" resultType="java.lang.Integer">
       select count(*) from message
       where status != 2 and from_id = 1 and to_id = #{userId} and conversation_id = #{topic}
   </select>
   <select id="selectUnreadNoticeCount" resultType="java.lang.Integer">
       select count(*) from message
       where status = 0 and from_id = 1 and to_id = #{userId} <if test="topic!=null"> and conversation_id = #{topic} </if>
   </select>
   ```

3. 业务层`MessageServiceImpl.java`：

   ```java
   @Override
   public Message findLatestNotice(int userId, String topic) {
       return messageMapper.selectLatestNotice(userId, topic);
   }
   @Override
   public int findNoticeCount(int userId, String topic) {
       return messageMapper.selectNoticeCount(userId, topic);
   }
   @Override
   public int findUnreadNoticeCount(int userId, String topic) {
       return messageMapper.selectUnreadNoticeCount(userId, topic);
   }
   ```

4. 表现层`MessageController`：

   ```java
    @RequestMapping(path = "/notice/list", method = RequestMethod.GET)
    public String getNoticeList(Model model) {
        User user = hostHolder.getUser();
        //一共有三类通知：评论、点赞、关注 ---> 查询数据库中的内容然后传递给前端
        //消息通知为评论类型 ---> 查询当前用户的最新通知
        Message message = messageService.findLatestNotice(user.getId(), TOPIC_COMMENT);
        //返回内容给前端，这里用 map 集合存储信息返回给前端
        Map<String, Object> messageCommentMap = new HashMap<>();
        //如果存在评论类型的系统消息，返回
        if (message != null) {
            messageCommentMap.put("message", message);
            //获取消息内容
            String content = HtmlUtils.htmlUnescape(message.getContent());
            Map<String, Object> contentData = JSONObject.parseObject(content, HashMap.class);
            //拼接内容
            messageCommentMap.put("user", userService.findUserById((Integer) contentData.get("user")));
            messageCommentMap.put("entityType", contentData.get("entityType"));
            messageCommentMap.put("entityId", contentData.get("entityId"));
            //获取评论消息总数
            int count = messageService.findNoticeCount(user.getId(), TOPIC_COMMENT);
            messageCommentMap.put("count", count);
            //获取未读评论总数
            int unreadCount = messageService.findUnreadNoticeCount(user.getId(), TOPIC_COMMENT);
            messageCommentMap.put("unread", unreadCount);
        }
        model.addAttribute("commentNotice", messageCommentMap);
        //点赞通知类
        Message messageLike = messageService.findLatestNotice(user.getId(), TOPIC_LIKE);
        //返回内容给前端，这里用 map 集合存储信息返回给前端
        Map<String, Object> messageLikeMap = new HashMap<>();
        //如果存在评论类型的系统消息，返回
        if (messageLike != null) {
            messageLikeMap.put("message", messageLike);
            //获取消息内容
            String content = HtmlUtils.htmlUnescape(messageLike.getContent());
            Map<String, Object> contentData = JSONObject.parseObject(content, HashMap.class);
            //拼接内容
            messageLikeMap.put("user", userService.findUserById((Integer) contentData.get("user")));
            messageLikeMap.put("entityType", contentData.get("entityType"));
            messageLikeMap.put("entityId", contentData.get("entityId"));
            //获取评论消息总数
            int count = messageService.findNoticeCount(user.getId(), TOPIC_LIKE);
            messageLikeMap.put("count", count);
            //获取未读评论总数
            int unreadCount = messageService.findUnreadNoticeCount(user.getId(), TOPIC_LIKE);
            messageLikeMap.put("unread", unreadCount);
        }
        model.addAttribute("likeNotice", messageCommentMap);
        //关注通知类
        Message messageFollow = messageService.findLatestNotice(user.getId(), TOPIC_FOLLOW);
        //返回内容给前端，这里用 map 集合存储信息返回给前端
        Map<String, Object> messageFollowMap = new HashMap<>();
        //如果存在评论类型的系统消息，返回
        if (messageFollow != null) {
            messageFollowMap.put("message", messageFollow);
            //获取消息内容
            String content = HtmlUtils.htmlUnescape(messageFollow.getContent());
            Map<String, Object> contentData = JSONObject.parseObject(content, HashMap.class);
            //拼接内容
            messageFollowMap.put("user", userService.findUserById((Integer) contentData.get("user")));
            messageFollowMap.put("entityType", contentData.get("entityType"));
            messageFollowMap.put("entityId", contentData.get("entityId"));
            //获取评论消息总数
            int count = messageService.findNoticeCount(user.getId(), TOPIC_FOLLOW);
            messageFollowMap.put("count", count);
            //获取未读评论总数
            int unreadCount = messageService.findUnreadNoticeCount(user.getId(), TOPIC_FOLLOW);
            messageFollowMap.put("unread", unreadCount);
        }
        model.addAttribute("followNotice", messageCommentMap);
        //查询全部类型未读消息的数量
        int noticeUnreadCount = messageService.findNoticeCount(user.getId(), null);
        model.addAttribute("noticeUnreadCount", noticeUnreadCount);
        return "/site/notice";
    }
   ```

5. 显示某个类型的通知详情

   数据层：

   ```java
   //查询某个类型的所有系统通知 ---> 分页查询
   public abstract List<Message> selectNotices(int userId, String topic, int start, int limit);
   
   <select id="selectNotices" resultType="message">
       select
       <include refid="selectFields"></include>
       from message
       where status != 2
       and from_id = 1
       and to_id = #{userId}
       and conversation_id = #{topic}
       order by create_time desc
       limit #{start}, #{limit}
   </select>
   ```

   业务层：

   ```java
   @Override
   public List<Message> findNotices(int userId, String topic, int start, int limit) {
       return messageMapper.selectNotices(userId, topic, start, limit);
   }
   ```

   表现层：

   ```java
   @RequestMapping(path = "/notice/detail/{topic}", method = RequestMethod.GET)
   public String getNoticeDetail(@PathVariable("topic") String topic, Page page, Model model) {
       //获取当前用户
       User user = hostHolder.getUser();
       //配置分页信息
       page.setLimit(5);
       page.setPath("/notice/detail/" + topic);
       page.setRows(messageService.findNoticeCount(user.getId(), topic));
       //获取当前类型的所有系统通知
       List<Message> noticeList = messageService.findNotices(user.getId(), topic, page.getStart(), page.getLimit());
       //将 noticeList 中的每一个系统通知都存入一个 Map 再讲这些 Map 封装到一起
       List<Map<String, Object>> noticeReturnList = new ArrayList<>();
       if (noticeList != null) {
           for (Message notice : noticeList) {
               Map<String, Object> map = new HashMap<>();
               map.put("notice", notice);
               //内容
               String content = HtmlUtils.htmlUnescape(notice.getContent());
               Map<String, Object> data = JSONObject.parseObject(content, HashMap.class);
               map.put("user", userService.findUserById((Integer) data.get("user")));
               map.put("entityType", data.get("entityType"));
               map.put("entityId", data.get("entityId"));
               //显示是谁发送的
               map.put("fromUser", userService.findUserById(notice.getFromId()));
               noticeReturnList.add(map);
           }
       }
       model.addAttribute("notices", noticeReturnList);
       //设置已读
       List<Integer> ids = getLetterIds(noticeList);
       if (!ids.isEmpty()) {
           messageService.updateStatus(ids, 1);
       }
       return "/site/notice-detail";
   }
   ```

这里还要引入一个`postId`，因为需要点击查看帖子详情，更改前端后即可显示

![](https://img-blog.csdnimg.cn/bbc9cb566e6445cb9950ed09473889d3.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBAQ3JBY0tlUi0x,size_20,color_FFFFFF,t_70,g_se,x_16)



这里有一个漏网之鱼，登录后每个页面都需要显示消息的总数量，所以需要配置一个拦截器，当然，使用`AOP`思想我认为也是可以的，但是因为要写切入点比较麻烦，所以这里直接使用拦截器 ---> 新建拦截器`MessageInterceptor`：

```java
package com.zwm.controller.interceptor;

import com.zwm.entity.User;
import com.zwm.service.impl.MessageServiceImpl;
import com.zwm.util.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class MessageInterceptor implements HandlerInterceptor {
    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private MessageServiceImpl messageService;

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler, ModelAndView modelAndView) throws Exception {
        //获取当前用户
        User user = hostHolder.getUser();
        //获取所有未读消息通知
        if (user != null && modelAndView != null) {
            int letterUnreadCount = messageService.findLetterUnreadCount(user.getId(), null);
            int noticeUnreadCount = messageService.findUnreadNoticeCount(user.getId(), null);
            modelAndView.addObject("allUnreadCount", letterUnreadCount + noticeUnreadCount);
        }
    }
}
```

配置拦截器`WebMvcConfig`：

```java
@Autowired
private MessageInterceptor messageInterceptor;
@Override
public void addInterceptors(InterceptorRegistry registry) {
    //registry.addInterceptor(alphaInterceptor).addPathPatterns("/register", "/login").excludePathPatterns("/**/*.css", "/**/*.js", "/**/*.jpg", "/**/*.png", "/**/*.j
    registry.addInterceptor(loginInterceptor).excludePathPatterns("/**/*.css", "/**/*.js", "/**/*.jpg", "/**/*.png", "/**/*.jpeg");
    registry.addInterceptor(loginRequiredInterceptor).excludePathPatterns("/**/*.css", "/**/*.js", "/**/*.jpg", "/**/*.png", "/**/*.jpeg");
    registry.addInterceptor(messageInterceptor).excludePathPatterns("/**/*.css", "/**/*.js", "/**/*.jpg", "/**/*.png", "/**/*.jpeg");
}
```

```html
<li class="nav-item ml-3 btn-group-vertical" th:if="${loginUser!=null}">
    <a class="nav-link position-relative" th:href="@{/letter/list}">消息<span class="badge badge-danger" th:text="${allUnreadCount!=0?allUnreadCount:''}">12</span></a>
</li>
```

这样就可以显示总消息了

### 5.10 `ElasticSearch`分布式搜索引擎

#### 5.10.1 `ElasticSearch`入门

【`ElasticSearch`可以看作是一个特殊的数据库】

- 一个分布式的、`Restful`风格的搜索引擎
- 支持对各种类型的数据的检索
- 搜索速度快，可以提供实时的搜索服务
- 便于水平扩展，每秒可以处理`P8`级海量数据

术语包括：索引、类型、文档、字段、集群、节点、分片、副本

下载地址：https://www.elastic.co/cn/ ---> 结合`Spring Data elsaticsearch`查看集成版本

- 更改配置：`config/elsaticsearch.yml` ---> `cluster.name: nowcoder`集群名字 ---> `path.data: d:\work\data\elasticsearch-6.4.3\data`数据目录 ---> `path.logs: d:\work\data\elasticsearch-6.4.3\data`日志目录

- 配置环境变量：`bin`目录

- 安装中文分词插件：`github`搜索：`elasticsearch ik` ---> 安装到`plugins/ik`目录下

- 启动`DOS`测试 ---> 双击`elasticsearch-cli.bat` ---> 端口：`9200`：

  ```powershell
  健康状态：curl -X GET "localhost:9200/_cat/health?v"
  查看节点：curl -X GET "localhost:9200/_cat/nodes?v"
  查看索引：curl -X GET "localhost:9200/_cat/indices?v"
  添加索引：curl -X PUT "localhost:9200/test"
  查看索引：curl -X GET "localhost:9200/_cat/indices?v"
  删除索引：curl -X DELETE "localhost:9200/test"
  ```

- 使用`Postman`演示：`GET PUT DELETE `

  ![](https://img-blog.csdnimg.cn/efb63cb43f2b483a9e5513ccd56b5cbd.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBAQ3JBY0tlUi0x,size_20,color_FFFFFF,t_70,g_se,x_16)

  ![](https://img-blog.csdnimg.cn/85d0db673b7f48a28343af3dd8464755.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBAQ3JBY0tlUi0x,size_20,color_FFFFFF,t_70,g_se,x_16)

  ![](https://img-blog.csdnimg.cn/d267700a855c4b62a076bb86a023cc01.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBAQ3JBY0tlUi0x,size_20,color_FFFFFF,t_70,g_se,x_16)

  发送`PUT` ---> `JSON`格式：`Content-Type:application/json; charset=UTF-8`

  ![](https://img-blog.csdnimg.cn/2454bdee4b0648079229cb53e9abbb9e.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBAQ3JBY0tlUi0x,size_20,color_FFFFFF,t_70,g_se,x_16)

  同样的道理创建：`localhost:9200/test/_doc/2` 和 `localhost:9200/test/_doc/3`：

  ```json
  {
      "titles":"互联网招聘",
      "content":"招聘一份运营岗位的工作"
  }
  
  {
      "titles": "互联网招聘",
  	"content": "招聘一百年陈年程序员！"
  }
  
  {
      "titles":"实习生推荐",
      "content":"我这里有一个亿的实习生可以推荐！"
  }
  ```

  演示搜索功能：`GET ---> localhost:9200/test/_search`：

  ```json
  {
      "took": 33,
      "timed_out": false,
      "_shards": {
          "total": 5,
          "successful": 5,
          "skipped": 0,
          "failed": 0
      },
      "hits": {
          "total": 3,
          "max_score": 1.0,
          "hits": [
              {
                  "_index": "test",
                  "_type": "_doc",
                  "_id": "2",
                  "_score": 1.0,
                  "_source": {
                      "titles": "互联网招聘",
                      "content": "招聘一百年陈年程序员！"
                  }
              },
              {
                  "_index": "test",
                  "_type": "_doc",
                  "_id": "1",
                  "_score": 1.0,
                  "_source": {
                      "titles": "互联网求职",
                      "content": "寻求一份运营岗位的工作"
                  }
              },
              {
                  "_index": "test",
                  "_type": "_doc",
                  "_id": "3",
                  "_score": 1.0,
                  "_source": {
                      "titles": "实习生推荐",
                      "content": "我这里有一个亿的实习生可以推荐！"
                  }
              }
          ]
      }
  }
  ```

  `localhost:9200/test/_search?q=titles:招聘`

  `localhost:9200/test/_search?q=content:实习`

  `localhost:9200/test/_search?q=content:运营实习` ---> 搜索引擎可以对关键词进行分词

  多个字段搜索匹配：

  ```json
  localhost:9200/test/_search
  
  {
      "query":{
          "multi_match":{
              "query":"互联网",
              "fields":["titles","content"]
          }
      }
  }
  ```

  

#### 5.10.2 `SpringBoot`整合`ElasticSearch`

- 引入依赖：`spring-boot-starter-data-elasticsearch`

  ```xml
  <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-data-elasticsearch</artifactId>
      <version>2.6.4</version>
  </dependency>
  ```

- 配置`ElasticSearch`：`cluster-name`、`cluster-nodes`【集群名字、集群结点】

  - `ElasticSearch`有两个端口，一个是`http`端口：`9200`，一个是`TCP`端口：`9300`

  - `ElasticSearch`和`Redis`底层都使用的是`Netty`进行网络传输，会造成冲突，需要解决掉这个问题：

    `NettyRuntime.java`：

    ![](https://img-blog.csdnimg.cn/8197c0077711464aaa73d6420b5d5ac3.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBAQ3JBY0tlUi0x,size_20,color_FFFFFF,t_70,g_se,x_16)

    其实这个在`Redis`已经设置好了，所以就会报错，`ElasticSearch`在`Netty4Utils.java`中调用`NettyRuntime.java`：

    ![](https://img-blog.csdnimg.cn/0d436969972d4706bd94bcc3ae54198d.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBAQ3JBY0tlUi0x,size_20,color_FFFFFF,t_70,g_se,x_16)

    所以需要做一个小小的改动因为这里没有考虑到别的地方有可能使用了`Netty`，通过源码可以看到，当`Netty4Utils.java`中的`es.set.netty.runtime.available.processors`为`false`的时候直接`return`不用判断是否设置了进程，虽然这样做也有一定的缺陷，但是这里可以解决掉`Netty`冲突

    ![](https://img-blog.csdnimg.cn/a62beb3f50bf4a29a4566f9bfa8a4515.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBAQ3JBY0tlUi0x,size_20,color_FFFFFF,t_70,g_se,x_16)

    如何更改的，我们可以在服务启动的时候配置一下`es.set.netty.runtime.available.processors`，使其成为`false` ---> `CommunityApplication.java` ---> `@PostConstruct`：

    ```java
    package com.zwm;
    
    import org.springframework.boot.SpringApplication;
    import org.springframework.boot.autoconfigure.SpringBootApplication;
    
    import javax.annotation.PostConstruct;
    
    @SpringBootApplication
    public class CommunityApplication {
    
        @PostConstruct
        public void init() {
            //解决ElasticSearch和Redis底层Netty冲突
            System.setProperty("es.set.netty.runtime.available.processors", "false");
        }
    
        public static void main(String[] args) {
            SpringApplication.run(CommunityApplication.class, args);
        }
    }
    ```

- `Spring Data ElasticSearch`：`ElasticsearchTemplate`、`ElasticsearchRepository`

  想办法将帖子存入到`ES`服务器里，这样就可以使用服务器搜索帖子 ---> 通过注解就可以索引 ---> `entity\DiscussPost.java`

  ```java
  @Document(indexName = "discusspost"[索引名字], type = "_doc"[类型], shards = 6[分片], replicas = 3[副本])
  
  存入每一个字段，注意这里的 title content 是重点，存储的时候要尽量拆分单词/语句，搜索的时候颗粒可以粗一点
  @Field(tyope = Field.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
  
  
  package com.zwm.entity;
  
  import lombok.AllArgsConstructor;
  import lombok.Data;
  import lombok.NoArgsConstructor;
  import org.springframework.data.annotation.Id;
  import org.springframework.data.elasticsearch.annotations.Document;
  import org.springframework.data.elasticsearch.annotations.Field;
  import org.springframework.data.elasticsearch.annotations.FieldType;
  
  import java.util.Date;
  
  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  @Document(indexName = "discusspost", indexStoreType = "_doc", shards = 6, replicas = 3)
  public class DiscussPost {
      @Id
      private int id;//帖子ID
      @Field(type = FieldType.Integer)
      private int userId;//用户ID
      @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
      private String title;//帖子标题
      @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
      private String content;//帖子内容
      @Field(type = FieldType.Integer)
      private int type;//帖子类型：0-普通 + 1-置顶
      @Field(type = FieldType.Integer)
      private int status;//帖子状态：0-普通 + 1-精华 + 2-拉黑
      @Field(type = FieldType.Integer)
      private int commentCount;//帖子评论数量
      @Field(type = FieldType.Date)
      private Date createTime;//帖子创建日期
      @Field(type = FieldType.Double)
      private Double score;//帖子获得的分数
  
      @Override
      public String toString() {
          return "DiscussPost{" +
                  "id=" + id +
                  ", userId=" + userId +
                  ", title='" + title + '\'' +
                  ", content='" + content + '\'' +
                  ", type=" + type +
                  ", status=" + status +
                  ", commentCount=" + commentCount +
                  ", createTime=" + createTime +
                  ", score=" + score +
                  '}';
      }
  }
  ```

  定义接口：`ElasticsearchRepository` ---> 从数据库中取到数据然后转存给`ES` ---> 有些时候`ElasticSearchRepository`无法完成需要使用`ElasticSearchTemplate`

  ```java
  package com.zwm.dao.elasticsearch;
  
  import com.zwm.entity.DiscussPost;
  import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
  import org.springframework.stereotype.Repository;
  
  @Repository
  public interface DiscussPostRepository extends ElasticsearchRepository<DiscussPost, Integer> {
  }
  ```

  测试，将数据库中的帖子数据取出放到`ElasticSearch`服务器中：

  其中出现了一个`bug` ---> `Caused by: org.elasticsearch.client.ResponseException: method [HEAD], host [http://localhost:9200], URI [/discusspost?ignore_throttled=false&ignore_unavailable=false&expand_wildcards=open%2Cclosed&allow_no_indices=false], status line [HTTP/1.1 400 Bad Request]` ---> 原因是因为`ES`的本版和`SpringBoot`版本冲突

  解决办法：本来是想将`ElasticSearch`换成`7.15.2`的，但是`JDK`需要`JDK 11`，无奈只能降低`SpringBoot`的版本，然后发现`application.properties`关于`ES`不报错了，然后还需要进一步修改`Kafka`的版本

  ```java
  import com.zwm.CommunityApplication;
  import com.zwm.dao.DiscussPostMapper;
  import com.zwm.dao.elasticsearch.DiscussPostRepository;
  import org.junit.Test;
  import org.junit.runner.RunWith;
  import org.springframework.beans.factory.annotation.Autowired;
  import org.springframework.boot.test.context.SpringBootTest;
  import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
  import org.springframework.test.context.ContextConfiguration;
  import org.springframework.test.context.junit4.SpringRunner;
  
  @RunWith(SpringRunner.class)
  @SpringBootTest
  @ContextConfiguration(classes = CommunityApplication.class)
  public class ESTest {
  
      @Autowired
      private DiscussPostMapper discussPostMapper;
  
      @Autowired
      private DiscussPostRepository discussPostRepository;
  
      @Autowired
      private ElasticsearchTemplate elasticsearchTemplate;
  
      @Test
      public void testESAdd() {
          //测试往ES存储索引
          discussPostRepository.save(discussPostMapper.selectDiscussPostById(241));
          discussPostRepository.save(discussPostMapper.selectDiscussPostById(242));
          discussPostRepository.save(discussPostMapper.selectDiscussPostById(243));
      }
  }
  ```

  `Postman`查询数据：`localhost:9200/discusspost/_search`

  ````java
  @Test
  public void testESAddAll() {
      //测试往ES添加多条数据
      discussPostRepository.saveAll(discussPostMapper.selectDiscussPosts(101, 0, 100));
      discussPostRepository.saveAll(discussPostMapper.selectDiscussPosts(102, 0, 100));
      discussPostRepository.saveAll(discussPostMapper.selectDiscussPosts(103, 0, 100));
      discussPostRepository.saveAll(discussPostMapper.selectDiscussPosts(111, 0, 100));
      discussPostRepository.saveAll(discussPostMapper.selectDiscussPosts(112, 0, 100));
      discussPostRepository.saveAll(discussPostMapper.selectDiscussPosts(131, 0, 100));
      discussPostRepository.saveAll(discussPostMapper.selectDiscussPosts(132, 0, 100));
      discussPostRepository.saveAll(discussPostMapper.selectDiscussPosts(133, 0, 100));
      discussPostRepository.saveAll(discussPostMapper.selectDiscussPosts(134, 0, 100));
  }
  ````

  查询数据：`localhost:9200/discusspost/_search` ---> 共有`141`条数据 ---> 表示添加成功

  更新数据，其实就是插入覆盖掉原先的数据而已【删除然后添加新的数据】，原先的数据如图所示：

  ![](https://img-blog.csdnimg.cn/170f87e0fb454c5781ab631b5498039e.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBAQ3JBY0tlUi0x,size_20,color_FFFFFF,t_70,g_se,x_16)

  ```java
  @Test
  public void testESUpdate() {
      //测试往ES更新数据
      DiscussPost discussPost = discussPostMapper.selectDiscussPostById(231);
      discussPost.setContent("我是新人，使劲灌水");
      discussPostRepository.save(discussPost);
  }
  ```

  再次查询可以发现内容更改：`localhost:9200/discusspost/_search?q=id:231`

  删除`ElasticSearch`服务器中的数据：

  ```java
  @Test
  public void testESDelete() {
      //测试删除ES数据
      discussPostRepository.deleteAll();
  }
  ```

  查询`localhost:9200/discusspost/_search`，可以看到没有数据表示删除完毕

  自此上述就演示完毕`ElasticSearch`的增删改查

- 搜索数据 ---> 构造搜索条件 ---> 构造排序方式 ---> 构造分页条件 ---> 设置结果字段高亮

  ```java
  @Test
  public void testESSearchByRepository() {
      //构造搜索条件 ---> QueryBuilders.multiMatchQuery[复合]
      //构造排序条件 ---> SortBuilders
      SearchQuery searchQuery = new NativeSearchQueryBuilder()
              .withQuery(QueryBuilders.multiMatchQuery("互联网寒冬", "title", "content"))
              .withSort(SortBuilders.fieldSort("type").order(SortOrder.DESC))
              .withSort(SortBuilders.fieldSort("score").order(SortOrder.DESC))
              .withSort(SortBuilders.fieldSort("createTime").order(SortOrder.DESC))
              .withPageable(PageRequest.of(0, 10))
              .withHighlightFields(
                      new HighlightBuilder.Field("title").preTags("<em>").postTags("</em>"),
                      new HighlightBuilder.Field("content").preTags("<em>").postTags("</em>")
              ).build();
      //查询
      Page<DiscussPost> discussPosts = discussPostRepository.search(searchQuery);
      System.out.println(discussPosts.getTotalElements());
      System.out.println(discussPosts.getTotalPages());
      System.out.println(discussPosts.getNumber());
      System.out.println(discussPosts.getSize());
      for (DiscussPost discussPost : discussPosts) {
          System.out.println(discussPost.toString());
      }
  }
  ```

- 测试`ElasticsearchTemplate.java`查询数据

  ```java
  @Test
  public void testESSearchByTemplate() {
      //构造搜索条件 ---> QueryBuilders.multiMatchQuery[复合]
      //构造排序条件 ---> SortBuilders
      SearchQuery searchQuery = new NativeSearchQueryBuilder()
              .withQuery(QueryBuilders.multiMatchQuery("互联网寒冬", "title", "content"))
              .withSort(SortBuilders.fieldSort("type").order(SortOrder.DESC))
              .withSort(SortBuilders.fieldSort("score").order(SortOrder.DESC))
              .withSort(SortBuilders.fieldSort("createTime").order(SortOrder.DESC))
              .withPageable(PageRequest.of(0, 10))
              .withHighlightFields(
                      new HighlightBuilder.Field("title").preTags("<em>").postTags("</em>"),
                      new HighlightBuilder.Field("content").preTags("<em>").postTags("</em>")
              ).build();
      Page<DiscussPost> discussPosts = elasticsearchTemplate.queryForPage(searchQuery, DiscussPost.class, new SearchResultMapper() {
          @Override
          public <T> AggregatedPage<T> mapResults(SearchResponse searchResponse, Class<T> aClass, Pageable pageable) {
              SearchHits searchHits = searchResponse.getHits();
              if (searchHits.getTotalHits() <= 0) return null;
              List<DiscussPost> discussPostList = new ArrayList<>();
              for (SearchHit searchHit : searchHits) {
                  DiscussPost discussPost = new DiscussPost();
                  discussPost.setId(Integer.valueOf(searchHit.getSourceAsMap().get("id").toString()));
                  discussPost.setUserId(Integer.valueOf(searchHit.getSourceAsMap().get("userId").toString()));
                  discussPost.setUserId(Integer.valueOf(searchHit.getSourceAsMap().get("status").toString()));
                  discussPost.setTitle(searchHit.getSourceAsMap().get("title").toString());
                  discussPost.setTitle(searchHit.getSourceAsMap().get("content").toString());
                  discussPost.setCreateTime(new Date(Long.valueOf(searchHit.getSourceAsMap().get("createTime").toString())));
                  discussPost.setCommentCount(Integer.valueOf(searchHit.getSourceAsMap().get("commentCount").toString()));
                  //处理高亮结果
                  HighlightField highlightField = searchHit.getHighlightFields().get("title");
                  //如果搜索到的结果不为空，则传递给 discussPost 后面前端将高亮显示内容 ---> <em></em>包括了搜索关键词
                  if (highlightField != null) {
                      //因为搜索时会分词这里只要查询第一个就可以了
                      discussPost.setTitle(highlightField.getFragments()[0].toString());
                  }
                  HighlightField highlightField1 = searchHit.getHighlightFields().get("content");
                  if (highlightField1 != null) {
                      discussPost.setContent(highlightField1.getFragments()[0].toString());
                  }
                  discussPostList.add(discussPost);
              }
              return new AggregatedPageImpl(discussPostList, pageable, searchHits.getTotalHits(), searchResponse.getAggregations(), searchResponse.getScrollId(), searchHits.getMaxScore());
          }
      });
      System.out.println(discussPosts.getTotalElements());
      System.out.println(discussPosts.getTotalPages());
      System.out.println(discussPosts.getNumber());
      System.out.println(discussPosts.getSize());
      for (DiscussPost discussPost : discussPosts) {
          System.out.printf(discussPost.toString());
      }
  }
  ```

  可以发现使用`ElasticsearchRepository`比`ElasticsearchTemplate`要简单得多，所以我们以`ElasticsearchRepository`为主，以`ElasticsearchTemplate`为辅 ---> 高亮显示`ElaticsearcgRepository`无法完成，需要借助`ElasticsearchTemplate`

#### 5.10.3 社区搜索功能

- 搜索服务

  - 将贴子保存到`Elasticsearch`服务器中

    - 修改`keyProperty`使其可以自动搜寻到主键`id`

      ```xml
      <insert id="insertDiscussPost" parameterType="discussPost" keyProperty="id">
          insert into discuss_post(<include refid="insertFields"/>) values(#{userId}, #{title}, #{content}, #{type},
          #{status}, #{commentCount}, #{createTime}, #{score})
      </insert>
      ```

    - 增加一条帖子到服务器中

      ```java
      //添加帖子到ES服务器
      public void insertPostToES(DiscussPost discussPost) {
          discussPostRepository.save(discussPost);
      }
      ```

  - 从`Elasticsearch`服务器中删除帖子

    ```java
    //从ES服务器中删除帖子
    public void deletePostFromES(int id) {
        discussPostRepository.deleteById(id);
    }
    ```

    

  - 从`Elasticsearch`服务器中搜索帖子 ---> 需要有关键字，当前是第几页，某页显示多少条数据

    ```java
    //在ES中搜索帖子 ---> 关键字 当前页 显示条数
    public Page<DiscussPost> searchPostFromES(String keyword, int current, int limit) {
        //构造搜索条件 ---> QueryBuilders.multiMatchQuery[复合]
        //构造排序条件 ---> SortBuilders
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.multiMatchQuery(keyword, "title", "content"))
                .withSort(SortBuilders.fieldSort("type").order(SortOrder.DESC))
                .withSort(SortBuilders.fieldSort("score").order(SortOrder.DESC))
                .withSort(SortBuilders.fieldSort("createTime").order(SortOrder.DESC))
                .withPageable(PageRequest.of(current, limit))
                .withHighlightFields(
                        new HighlightBuilder.Field("title").preTags("<em>").postTags("</em>"),
                        new HighlightBuilder.Field("content").preTags("<em>").postTags("</em>")
                ).build();
        return elasticsearchTemplate.queryForPage(searchQuery, DiscussPost.class, new SearchResultMapper() {
            @Override
            public <T> AggregatedPage<T> mapResults(SearchResponse searchResponse, Class<T> aClass, Pageable pageable) {
                SearchHits searchHits = searchResponse.getHits();
                if (searchHits.getTotalHits() <= 0) return null;
                List<DiscussPost> discussPostList = new ArrayList<>();
                for (SearchHit searchHit : searchHits) {
                    DiscussPost discussPost = new DiscussPost();
                    discussPost.setId(Integer.valueOf(searchHit.getSourceAsMap().get("id").toString()));
                    discussPost.setUserId(Integer.valueOf(searchHit.getSourceAsMap().get("userId").toString()));
                    discussPost.setUserId(Integer.valueOf(searchHit.getSourceAsMap().get("status").toString()));
                    discussPost.setTitle(searchHit.getSourceAsMap().get("title").toString());
                    discussPost.setTitle(searchHit.getSourceAsMap().get("content").toString());
                    discussPost.setCreateTime(new Date(Long.valueOf(searchHit.getSourceAsMap().get("createTime").toString())));
                    discussPost.setCommentCount(Integer.valueOf(searchHit.getSourceAsMap().get("commentCount").toString()));
                    //处理高亮结果
                    HighlightField highlightField = searchHit.getHighlightFields().get("title");
                    //如果搜索到的结果不为空，则传递给 discussPost 后面前端将高亮显示内容 ---> <em></em>包括了搜索关键词
                    if (highlightField != null) {
                        //因为搜索时会分词这里只要查询第一个就可以了
                        discussPost.setTitle(highlightField.getFragments()[0].toString());
                    }
                    HighlightField highlightField1 = searchHit.getHighlightFields().get("content");
                    if (highlightField1 != null) {
                        discussPost.setContent(highlightField1.getFragments()[0].toString());
                    }
                    discussPostList.add(discussPost);
                }
                return new AggregatedPageImpl(discussPostList, pageable, searchHits.getTotalHits(), searchResponse.getAggregations(), searchResponse.getScrollId(), searchHits.getMaxScore());
            }
        });
    }
    ```

  - 整个`ElasticsearchServiceImpl.java`的代码如下：

    ```java
    package com.zwm.service.impl;
    
    import com.zwm.dao.DiscussPostMapper;
    import com.zwm.dao.elasticsearch.DiscussPostRepository;
    import com.zwm.entity.DiscussPost;
    import com.zwm.service.ElasticsearchService;
    import org.elasticsearch.action.search.SearchResponse;
    import org.elasticsearch.index.query.QueryBuilders;
    import org.elasticsearch.search.SearchHit;
    import org.elasticsearch.search.SearchHits;
    import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
    import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
    import org.elasticsearch.search.sort.SortBuilders;
    import org.elasticsearch.search.sort.SortOrder;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.data.domain.Page;
    import org.springframework.data.domain.PageRequest;
    import org.springframework.data.domain.Pageable;
    import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
    import org.springframework.data.elasticsearch.core.SearchResultMapper;
    import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
    import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
    import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
    import org.springframework.data.elasticsearch.core.query.SearchQuery;
    import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
    import org.springframework.stereotype.Service;
    
    import java.util.ArrayList;
    import java.util.Date;
    import java.util.List;
    
    @Service
    public class ElasticsearchServiceImpl implements ElasticsearchService {
    
        @Autowired
        private DiscussPostRepository discussPostRepository;
    
        @Autowired
        private ElasticsearchTemplate elasticsearchTemplate;
    
        //添加帖子到ES服务器
        public void insertPostToES(DiscussPost discussPost) {
            discussPostRepository.save(discussPost);
        }
    
        //从ES服务器中删除帖子
        public void deletePostFromES(int id) {
            discussPostRepository.deleteById(id);
        }
    
        //在ES中搜索帖子 ---> 关键字 当前页 显示条数
        public Page<DiscussPost> searchPostFromES(String keyword, int current, int limit) {
            //构造搜索条件 ---> QueryBuilders.multiMatchQuery[复合]
            //构造排序条件 ---> SortBuilders
            SearchQuery searchQuery = new NativeSearchQueryBuilder()
                    .withQuery(QueryBuilders.multiMatchQuery(keyword, "title", "content"))
                    .withSort(SortBuilders.fieldSort("type").order(SortOrder.DESC))
                    .withSort(SortBuilders.fieldSort("score").order(SortOrder.DESC))
                    .withSort(SortBuilders.fieldSort("createTime").order(SortOrder.DESC))
                    .withPageable(PageRequest.of(current, limit))
                    .withHighlightFields(
                            new HighlightBuilder.Field("title").preTags("<em>").postTags("</em>"),
                            new HighlightBuilder.Field("content").preTags("<em>").postTags("</em>")
                    ).build();
            return elasticsearchTemplate.queryForPage(searchQuery, DiscussPost.class, new SearchResultMapper() {
                @Override
                public <T> AggregatedPage<T> mapResults(SearchResponse searchResponse, Class<T> aClass, Pageable pageable) {
                    SearchHits searchHits = searchResponse.getHits();
                    if (searchHits.getTotalHits() <= 0) return null;
                    List<DiscussPost> discussPostList = new ArrayList<>();
                    for (SearchHit searchHit : searchHits) {
                        DiscussPost discussPost = new DiscussPost();
                        discussPost.setId(Integer.valueOf(searchHit.getSourceAsMap().get("id").toString()));
                        discussPost.setUserId(Integer.valueOf(searchHit.getSourceAsMap().get("userId").toString()));
                        discussPost.setUserId(Integer.valueOf(searchHit.getSourceAsMap().get("status").toString()));
                        discussPost.setTitle(searchHit.getSourceAsMap().get("title").toString());
                        discussPost.setTitle(searchHit.getSourceAsMap().get("content").toString());
                        discussPost.setCreateTime(new Date(Long.valueOf(searchHit.getSourceAsMap().get("createTime").toString())));
                        discussPost.setCommentCount(Integer.valueOf(searchHit.getSourceAsMap().get("commentCount").toString()));
                        //处理高亮结果
                        HighlightField highlightField = searchHit.getHighlightFields().get("title");
                        //如果搜索到的结果不为空，则传递给 discussPost 后面前端将高亮显示内容 ---> <em></em>包括了搜索关键词
                        if (highlightField != null) {
                            //因为搜索时会分词这里只要查询第一个就可以了
                            discussPost.setTitle(highlightField.getFragments()[0].toString());
                        }
                        HighlightField highlightField1 = searchHit.getHighlightFields().get("content");
                        if (highlightField1 != null) {
                            discussPost.setContent(highlightField1.getFragments()[0].toString());
                        }
                        discussPostList.add(discussPost);
                    }
                    return new AggregatedPageImpl(discussPostList, pageable, searchHits.getTotalHits(), searchResponse.getAggregations(), searchResponse.getScrollId(), searchHits.getMaxScore());
                }
            });
        }
    }
    ```

- 发布事件

  - 发布帖子时，将贴子异步的提交到`Elasticsearch`服务器 ---> `DiscussPostController.java`

    添加一个常量：

    ```java
    //生产者生产帖子发布到ES类型
    String TOPIC_PUBLISH = "publish";
    ```

    生产者生产事件：

    ```java
    Event event = new Event();
    event.setTopic(TOPIC_PUBLISH).setUserId(user.getId()).setEntityType(ENTITY_TYPE_POST).setEntityId(discussPost.getId());
    eventProducer.fireEvent(event);
    ```

  - 增加评论时，将贴子异步的提交到`Elasticsearch`服务器 ---> 帖子评论

    ```java
    //如果是帖子的评论
    if (comment.getEntityType() == ENTITY_TYPE_POST) {
        Event event1 = new Event();
        event1.setTopic(TOPIC_PUBLISH).setUserId(user.getId()).setEntityType(ENTITY_TYPE_POST).setEntityId(discussPostId);
        eventProducer.fireEvent(event);
    }
    ```

  - 在消费组件中增加一个方法，消费帖子发布事件 ---> `EventConsumer`

    ```java
    package com.zwm.service.impl;
    
    import com.zwm.dao.elasticsearch.DiscussPostRepository;
    import com.zwm.entity.DiscussPost;
    import com.zwm.service.ElasticsearchService;
    import org.elasticsearch.action.search.SearchResponse;
    import org.elasticsearch.index.query.QueryBuilders;
    import org.elasticsearch.search.SearchHit;
    import org.elasticsearch.search.SearchHits;
    import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
    import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
    import org.elasticsearch.search.sort.SortBuilders;
    import org.elasticsearch.search.sort.SortOrder;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.data.domain.Page;
    import org.springframework.data.domain.PageRequest;
    import org.springframework.data.domain.Pageable;
    import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
    import org.springframework.data.elasticsearch.core.SearchResultMapper;
    import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
    import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
    import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
    import org.springframework.data.elasticsearch.core.query.SearchQuery;
    import org.springframework.stereotype.Service;
    
    import java.util.ArrayList;
    import java.util.Date;
    import java.util.List;
    
    @Service
    public class ElasticsearchServiceImpl implements ElasticsearchService {
    
        @Autowired
        private DiscussPostRepository discussPostRepository;
    
        @Autowired
        private ElasticsearchTemplate elasticsearchTemplate;
    
        //添加帖子到ES服务器
        public void insertPostToES(DiscussPost discussPost) {
            discussPostRepository.save(discussPost);
        }
    
        //从ES服务器中删除帖子
        public void deletePostFromES(int id) {
            discussPostRepository.deleteById(id);
        }
    
        //在ES中搜索帖子 ---> 关键字 当前页 显示条数
        public Page<DiscussPost> searchPostFromES(String keyword, int current, int limit) {
            //构造搜索条件 ---> QueryBuilders.multiMatchQuery[复合]
            //构造排序条件 ---> SortBuilders
            SearchQuery searchQuery = new NativeSearchQueryBuilder()
                    .withQuery(QueryBuilders.multiMatchQuery(keyword, "title", "content"))
                    .withSort(SortBuilders.fieldSort("type").order(SortOrder.DESC))
                    .withSort(SortBuilders.fieldSort("score").order(SortOrder.DESC))
                    .withSort(SortBuilders.fieldSort("createTime").order(SortOrder.DESC))
                    .withPageable(PageRequest.of(current, limit))
                    .withHighlightFields(
                            new HighlightBuilder.Field("title").preTags("<em>").postTags("</em>"),
                            new HighlightBuilder.Field("content").preTags("<em>").postTags("</em>")
                    ).build();
            return elasticsearchTemplate.queryForPage(searchQuery, DiscussPost.class, new SearchResultMapper() {
                @Override
                public <T> AggregatedPage<T> mapResults(SearchResponse searchResponse, Class<T> aClass, Pageable pageable) {
                    SearchHits searchHits = searchResponse.getHits();
                    if (searchHits.getTotalHits() <= 0) return null;
                    List<DiscussPost> discussPostList = new ArrayList<>();
                    for (SearchHit searchHit : searchHits) {
                        DiscussPost discussPost = new DiscussPost();
                        discussPost.setId(Integer.valueOf(searchHit.getSourceAsMap().get("id").toString()));
                        discussPost.setUserId(Integer.valueOf(searchHit.getSourceAsMap().get("userId").toString()));
                        discussPost.setStatus(Integer.valueOf(searchHit.getSourceAsMap().get("status").toString()));
                        discussPost.setTitle(searchHit.getSourceAsMap().get("title").toString());
                        discussPost.setContent(searchHit.getSourceAsMap().get("content").toString());
                        discussPost.setCreateTime(new Date(Long.valueOf(searchHit.getSourceAsMap().get("createTime").toString())));
                        discussPost.setCommentCount(Integer.valueOf(searchHit.getSourceAsMap().get("commentCount").toString()));
                        //处理高亮结果
                        HighlightField highlightField = searchHit.getHighlightFields().get("title");
                        //如果搜索到的结果不为空，则传递给 discussPost 后面前端将高亮显示内容 ---> <em></em>包括了搜索关键词
                        if (highlightField != null) {
                            //因为搜索时会分词这里只要查询第一个就可以了
                            discussPost.setTitle(highlightField.getFragments()[0].toString());
                        }
                        HighlightField highlightField1 = searchHit.getHighlightFields().get("content");
                        if (highlightField1 != null) {
                            discussPost.setContent(highlightField1.getFragments()[0].toString());
                        }
                        discussPostList.add(discussPost);
                    }
                    return new AggregatedPageImpl(discussPostList, pageable, searchHits.getTotalHits(), searchResponse.getAggregations(), searchResponse.getScrollId(), searchHits.getMaxScore());
                }
            });
        }
    }
    ```

- 显示结果

  - 在控制器中处理搜索请求，在`HTML`上显示搜索结果

    `ElasticsearchController.java`

    ```java
    package com.zwm.controller;
    
    import com.zwm.entity.DiscussPost;
    import com.zwm.entity.Page;
    import com.zwm.entity.User;
    import com.zwm.service.impl.ElasticsearchServiceImpl;
    import com.zwm.service.impl.LikeServiceImpl;
    import com.zwm.service.impl.UserServiceImpl;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.stereotype.Controller;
    import org.springframework.ui.Model;
    import org.springframework.web.bind.annotation.RequestMapping;
    import org.springframework.web.bind.annotation.RequestMethod;
    
    import java.util.ArrayList;
    import java.util.HashMap;
    import java.util.List;
    import java.util.Map;
    
    import static com.zwm.util.CommunityConstantTwo.ENTITY_TYPE_POST;
    
    @Controller
    public class ElasticsearchController {
    
        @Autowired
        private ElasticsearchServiceImpl elasticsearchService;
    
        //搜索到帖子之后还要展示作者信息和点赞数量
        @Autowired
        private UserServiceImpl userService;
    
        @Autowired
        private LikeServiceImpl likeService;
    
        //search?keyword=xxx ---> GET 请求
        @RequestMapping(value = "/search", method = RequestMethod.GET)
        public String searchPost(String keyword, Page page, Model model) {
            org.springframework.data.domain.Page<DiscussPost> discussPostPage = elasticsearchService.searchPostFromES(keyword, page.getCurrent() - 1, page.getLimit());
            //聚合数据 ---> 封装给前端 ---> 因为有多个 用户+帖子+点赞，所以需要一个集合装着
            List<Map<String, Object>> discussPostList = new ArrayList<>();
            if (discussPostPage != null) {
                for (DiscussPost discussPost : discussPostPage) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("post", discussPost);
                    //搜索出当前帖子的用户
                    User user = userService.findUserById(discussPost.getUserId());
                    map.put("user", user);
                    //搜索出当前帖子的点赞数量
                    map.put("likeCount", likeService.findLikeNumbers(ENTITY_TYPE_POST, discussPost.getId()));
                    discussPostList.add(map);
                }
            }
            //传递搜索出的帖子
            model.addAttribute("discussPosts", discussPostList);
            //传递关键字
            model.addAttribute("keyword", keyword);
            //传递分页信息
            page.setPath("/search?keyword=" + keyword);
            page.setRows(discussPostPage == null ? 0 : (int) discussPostPage.getTotalElements());
            return "/site/search";
        }
    }
    ```

  - `index.html`复用搜索框，这样大家都可以使用

    ```html
    <!-- 搜索 -->
    <form class="form-inline my-2 my-lg-0" method="get" th:action="@{/search}">
        <input class="form-control mr-sm-2" type="search" aria-label="Search" name="keyword" th:value="${keyword}"/>
        <button class="btn btn-outline-light my-2 my-sm-0" type="submit">搜索</button>
    </form>
    ```

  - `search.html`

    ```html
    <!doctype html>
    <html lang="en" xmlns:th="http://www.thymeleaf.org">
    <head>
    	<meta charset="utf-8">
    	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    	<link rel="icon" href="https://static.nowcoder.com/images/logo_87_87.png"/>
    	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" crossorigin="anonymous">
    	<link rel="stylesheet" th:href="@{/css/global.css}" />
    	<title>牛客网-搜索结果</title>
    </head>
    <body>
    <div class="nk-container">
    	<!-- 头部 -->
    	<header class="bg-dark sticky-top" th:replace="index::header">
    		<div class="container">
    			<!-- 导航 -->
    			<nav class="navbar navbar-expand-lg navbar-dark">
    				<!-- logo -->
    				<a class="navbar-brand" href="#"></a>
    				<button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
    					<span class="navbar-toggler-icon"></span>
    				</button>
    				<!-- 功能 -->
    				<div class="collapse navbar-collapse" id="navbarSupportedContent">
    					<ul class="navbar-nav mr-auto">
    						<li class="nav-item ml-3 btn-group-vertical">
    							<a class="nav-link" href="../index.html">首页</a>
    						</li>
    						<li class="nav-item ml-3 btn-group-vertical">
    							<a class="nav-link position-relative" href="letter.html">消息<span class="badge badge-danger">12</span></a>
    						</li>
    						<li class="nav-item ml-3 btn-group-vertical">
    							<a class="nav-link" href="register.html">注册</a>
    						</li>
    						<li class="nav-item ml-3 btn-group-vertical">
    							<a class="nav-link" href="login.html">登录</a>
    						</li>
    						<li class="nav-item ml-3 btn-group-vertical dropdown">
    							<a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
    								<img src="http://images.nowcoder.com/head/1t.png" class="rounded-circle" style="width:30px;"/>
    							</a>
    							<div class="dropdown-menu" aria-labelledby="navbarDropdown">
    								<a class="dropdown-item text-center" href="profile.html">个人主页</a>
    								<a class="dropdown-item text-center" href="setting.html">账号设置</a>
    								<a class="dropdown-item text-center" href="login.html">退出登录</a>
    								<div class="dropdown-divider"></div>
    								<span class="dropdown-item text-center text-secondary">nowcoder</span>
    							</div>
    						</li>
    					</ul>
    					<!-- 搜索 -->
    					<form class="form-inline my-2 my-lg-0" action="search.html">
    						<input class="form-control mr-sm-2" type="search" aria-label="Search" />
    						<button class="btn btn-outline-light my-2 my-sm-0" type="submit">搜索</button>
    					</form>
    				</div>
    			</nav>
    		</div>
    	</header>
    
    	<!-- 内容 -->
    	<div class="main">
    		<div class="container">
    			<h6><b class="square"></b> 相关帖子</h6>
    			<!-- 帖子列表 -->
    			<ul class="list-unstyled mt-4">
    				<li class="media pb-3 pt-3 mb-3 border-bottom" th:each="map:${discussPosts}">
    					<img th:src="${map.user.headerUrl}" class="mr-4 rounded-circle" alt="用户头像" style="width:50px;height:50px;">
    					<div class="media-body">
    						<h6 class="mt-0 mb-3">
    							<a th:href="@{|/discuss/detail/${map.post.id}|}" th:utext="${map.post.title}">备战<em>春招</em>，面试刷题跟他复习，一个月全搞定！</a>
    						</h6>
    						<div class="mb-3" th:utext="${map.post.content}">
    							金三银四的金三已经到了，你还沉浸在过年的喜悦中吗？ 如果是，那我要让你清醒一下了：目前大部分公司已经开启了内推，正式网申也将在3月份陆续开始，金三银四，<em>春招</em>的求职黄金时期已经来啦！！！ 再不准备，作为19应届生的你可能就找不到工作了。。。作为20届实习生的你可能就找不到实习了。。。 现阶段时间紧，任务重，能做到短时间内快速提升的也就只有算法了， 那么算法要怎么复习？重点在哪里？常见笔试面试算法题型和解题思路以及最优代码是怎样的？ 跟左程云老师学算法，不仅能解决以上所有问题，还能在短时间内得到最大程度的提升！！！
    						</div>
    						<div class="text-muted font-size-12">
    							<u class="mr-3" th:utext="${map.user.username}">寒江雪</u>
    							发布于 <b th:text="${#dates.format(map.post.createTime,'yyyy-MM-dd HH:mm:ss')}">2019-04-15 15:32:18</b>
    							<ul class="d-inline float-right">
    								<li class="d-inline ml-2">赞 <i th:text="${map.likeCount}">11</i></li>
    								<li class="d-inline ml-2">|</li>
    								<li class="d-inline ml-2">回复 <i th:text="${map.post.commentCount}">7</i></li>
    							</ul>
    						</div>
    					</div>
    				</li>
    			</ul>
    			<!-- 分页 -->
    			<nav class="mt-5" th:replace="index::pagination">
    				<ul class="pagination justify-content-center">
    					<li class="page-item"><a class="page-link" href="#">首页</a></li>
    					<li class="page-item disabled"><a class="page-link" href="#">上一页</a></li>
    					<li class="page-item active"><a class="page-link" href="#">1</a></li>
    					<li class="page-item"><a class="page-link" href="#">2</a></li>
    					<li class="page-item"><a class="page-link" href="#">3</a></li>
    					<li class="page-item"><a class="page-link" href="#">4</a></li>
    					<li class="page-item"><a class="page-link" href="#">5</a></li>
    					<li class="page-item"><a class="page-link" href="#">下一页</a></li>
    					<li class="page-item"><a class="page-link" href="#">末页</a></li>
    				</ul>
    			</nav>
    		</div>
    	</div>
    
    	<!-- 尾部 -->
    	<footer class="bg-dark">
    		<div class="container">
    			<div class="row">
    				<!-- 二维码 -->
    				<div class="col-4 qrcode">
    					<img src="https://uploadfiles.nowcoder.com/app/app_download.png" class="img-thumbnail" style="width:136px;" />
    				</div>
    				<!-- 公司信息 -->
    				<div class="col-8 detail-info">
    					<div class="row">
    						<div class="col">
    							<ul class="nav">
    								<li class="nav-item">
    									<a class="nav-link text-light" href="#">关于我们</a>
    								</li>
    								<li class="nav-item">
    									<a class="nav-link text-light" href="#">加入我们</a>
    								</li>
    								<li class="nav-item">
    									<a class="nav-link text-light" href="#">意见反馈</a>
    								</li>
    								<li class="nav-item">
    									<a class="nav-link text-light" href="#">企业服务</a>
    								</li>
    								<li class="nav-item">
    									<a class="nav-link text-light" href="#">联系我们</a>
    								</li>
    								<li class="nav-item">
    									<a class="nav-link text-light" href="#">免责声明</a>
    								</li>
    								<li class="nav-item">
    									<a class="nav-link text-light" href="#">友情链接</a>
    								</li>
    							</ul>
    						</div>
    					</div>
    					<div class="row">
    						<div class="col">
    							<ul class="nav btn-group-vertical company-info">
    								<li class="nav-item text-white-50">
    									公司地址：北京市朝阳区大屯路东金泉时代3-2708北京牛客科技有限公司
    								</li>
    								<li class="nav-item text-white-50">
    									联系方式：010-60728802(电话)&nbsp;&nbsp;&nbsp;&nbsp;admin@nowcoder.com
    								</li>
    								<li class="nav-item text-white-50">
    									牛客科技©2018 All rights reserved
    								</li>
    								<li class="nav-item text-white-50">
    									京ICP备14055008号-4 &nbsp;&nbsp;&nbsp;&nbsp;
    									<img src="http://static.nowcoder.com/company/images/res/ghs.png" style="width:18px;" />
    									京公网安备 11010502036488号
    								</li>
    							</ul>
    						</div>
    					</div>
    				</div>
    			</div>
    		</div>
    	</footer>
    </div>
    
    <script src="https://code.jquery.com/jquery-3.3.1.min.js" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" crossorigin="anonymous"></script>
    <script th:src="@{/js/global.js}"></script>
    </body>
    </html>
    ```

- 启动：报错

  ```java
  mapper [createTime] of different type, current_type [long], merged_type [date] 	at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.initializeBean(AbstractAutowireCapableBeanFactory.java:1778) ~[spring-beans-5.1.7.RELEASE.jar:5
  ```

  解决办法：修改`entity/DiscussPost`上关于`ES`的注解 ---> 测试的时候没报错为什么这里会报错？---> 电脑关机过重启数据不一致导致

  解决办法：删掉`data`文件夹里的数据，重启`ES`

结果如图所示：![](https://img-blog.csdnimg.cn/bfcbfe72d29f490f8652c427f836e550.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBAQ3JBY0tlUi0x,size_20,color_FFFFFF,t_70,g_se,x_16)

### 5.11 `Spring Security` + 权限控制 + 置顶加精删除

- 简介

  - `Spring Security`是一个专注于为`Java`应用程序提供身份认证和授权的框架，它的强大之处在于它可以轻松扩展以满足自定义的需求

- 特征

  - 对身份的**认证**和**授权**提供全面的、可扩展的支持
  - 防止各种攻击，如会话固定攻击、点击劫持、`csrf`攻击等
  - 支持与`Servlet API`、`Spring MVC`等`Web`技术集成

- 原理

  `DispatcherServlet`是具体的`Servlet`接口的实现类，满足了`JavaEE`的规范，`Filter`也是`JavaEE`的规范，`Filter`可以拦截`DispatcherServlet`这些`Servlet`，`SpringSecurity`其实就是这些`Filter`的集成，一共是是`11`个【后续可能有增加】，判断的时机要早于`SpringMvc`，`SpringSecurity`是`Spring`所有模块中最复杂的模块，如果能把这些`Filter`都弄透的话，其余的模块也就不难研究了。

  如何研究：没有书、官网也没有系统的案例单纯点到为止 ---> `http://www.spring4all.com/`
  
  如果这个能吃透，`Spring`其它模块也就没有什么太大的问题了
  
- `Spring Security`演示案例

### 5.12 `Redis`高级数据类型 + 网站数据统计

【主要是学习下面两种类型，对网站数据进行统计并且非常节省内存】

- `HyperLogLog`

  > 采用一种基数算法，用于完成独立总数的统计 ---> `UV`统计
  >
  > 占据空间小，无论统计多少个数据，只占`12K`的内存空间
  >
  > 不精确的统计算法，标准误差为`0.81%`

  1. 统计`20w`条不重复的数据：结果显示 ---> 9978【表明有误差但是在可接受范围之内】

     ```java
     @Test
     public void testHyperLogLogNoRepeat() {
         String redisKey = "test:hyperloglog";
         for (int i = 1; i <= 10000; i++) redisTemplate.opsForHyperLogLog().add(redisKey, i);
         for (int i = 1; i <= 10000; i++) redisTemplate.opsForHyperLogLog().add(redisKey, (int) (Math.random() * 10000 + 1));
         System.out.println(redisTemplate.opsForHyperLogLog().size(redisKey));
     }
     ```

  2. 统计数据合并的结果：结果显示 ---> `30`【合并以后的大小】

     ```java
     @Test
     public void testHyperLogLogMerge() {
         String redisKey1 = "test:hyperloglog:redisKey1";
         String redisKey2 = "test:hyperloglog:redisKey2";
         String redisKey3 = "test:hyperloglog:redisKey3";
         String redisKeyUnion = "test:hyperloglog:redisKeyUnion";
         for (int i = 1; i <= 10; i++) redisTemplate.opsForHyperLogLog().add(redisKey1, i);
         for (int i = 11; i <= 20; i++) redisTemplate.opsForHyperLogLog().add(redisKey2, i);
         for (int i = 21; i <= 30; i++) redisTemplate.opsForHyperLogLog().add(redisKey3, i);
         redisTemplate.opsForHyperLogLog().union(redisKeyUnion, redisKey1, redisKey2, redisKey3);
         System.out.println(redisTemplate.opsForHyperLogLog().size(redisKeyUnion));
     }
     ```

- `Bitmap`

  > 不是一种独立的数据结构，本质上就是字符串，但是是特殊格式的字符串
  >
  > 按位存取数据，可以看成是字节`byte`数组
  >
  > 适合存储大量的连续的数据的布尔值

  1. 统计一组数据的布尔值【签到功能】：结果显示 ---> `true false true false true 3`
  
     ```java
     @Test
     public void testBitmapStatistics() {
         String redisKey0 = "test:bitmap:redisKey0";
         redisTemplate.opsForValue().setBit(redisKey0, 0, true);
         redisTemplate.opsForValue().setBit(redisKey0, 2, true);
         redisTemplate.opsForValue().setBit(redisKey0, 4, true);
         for (int i = 1; i <= 4; i++) System.out.println(redisTemplate.opsForValue().getBit(redisKey0, i));
         Object object = redisTemplate.execute(new RedisCallback() {
             @Override
             public Object doInRedis(RedisConnection redisConnection) throws DataAccessException {
                 return redisConnection.bitCount(redisKey0.getBytes());
             }
         });
         System.out.println(object);
     }
     ```
  
  2. 对几组数据进行或运算 ---> `AND`运算，以`1`为例，三个`key`合起来就是`true + false + true`所以结果为`false`
  
     ```java
     @Test
     public void testBitmapOperation() {
         String redisKey0 = "test:bitmap:redisKey0";
         String redisKey1 = "test:bitmap:redisKey1";
         String redisKey2 = "test:bitmap:redisKey2";
         redisTemplate.opsForValue().setBit(redisKey0, 1, true);
         redisTemplate.opsForValue().setBit(redisKey0, 3, true);
         redisTemplate.opsForValue().setBit(redisKey0, 5, true);
         redisTemplate.opsForValue().setBit(redisKey1, 1, false);
         redisTemplate.opsForValue().setBit(redisKey1, 3, true);
         redisTemplate.opsForValue().setBit(redisKey1, 5, true);
         redisTemplate.opsForValue().setBit(redisKey2, 1, true);
         redisTemplate.opsForValue().setBit(redisKey2, 3, true);
         redisTemplate.opsForValue().setBit(redisKey2, 5, true);
         String redisKeyOperation = "test:bitmap:redisOperation";
         Object object = redisTemplate.execute(new RedisCallback() {
             @Override
             public Object doInRedis(RedisConnection redisConnection) throws DataAccessException {
                 redisConnection.bitOp(RedisStringCommands.BitOperation.AND, redisKeyOperation.getBytes(), redisKey0.getBytes(), redisKey1.getBytes(), redisKey2.getBytes());
                 return redisConnection.bitCount(redisKeyOperation.getBytes());
             }
         });
         System.out.println(object);
         for (int i = 0; i < 6; i++) System.out.println(redisTemplate.opsForValue().getBit(redisKeyOperation, i));
     }
     ```

- `UV(Unique Vistor)` ---> `HyperLogerLoger`

  > 独立访客，需要通过用户`IP`排重统计数据
  >
  > 每次访问都需要统计
  >
  > `HyperLogLog`性能好，且存储空间小

- `DAU(Daily Active User)` ---> `Bitmap`

  > 日活跃用户，需通过用户`ID`排重统计数据
  >
  > 访问过一次，则认为其活跃
  >
  > `Bitmap`，性能好、且可以统计精确的结果

![](https://img-blog.csdnimg.cn/e89219365dd841669524a1ba1bf49b46.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBAQ3JBY0tlUi0x,size_17,color_FFFFFF,t_70,g_se,x_16)

1. `RedisKeyUtil.java`【包括单日`UV`和区间`UV`以及单日`DAU`和区间`DAU`】：

   ```java
   //获取单日用户UV
   public static String getUVKey(String date) {
       return PREFIX_UV + SPLIT + date;
   }
   //获取一段时间的用户UV
   public static String getUVKey(String startDate, String endDate) {
       return PREFIX_UV + SPLIT + startDate + SPLIT + endDate;
   }
   //获取单日用户UV
   public static String getDAUKey(String date) {
       return PREFIX_DAU + SPLIT + date;
   }
   //获取一段时间的用户UV
   public static String getDAUKey(String startDate, String endDate) {
       return PREFIX_DAU + SPLIT + startDate + SPLIT + endDate;
   }
   ```

2. `DataServiceImpl.java`

   ```java
   package com.zwm.service.impl;
   
   import com.zwm.service.DataService;
   import com.zwm.util.RedisKeyUtil;
   import org.springframework.beans.factory.annotation.Autowired;
   import org.springframework.dao.DataAccessException;
   import org.springframework.data.redis.connection.RedisConnection;
   import org.springframework.data.redis.connection.RedisStringCommands;
   import org.springframework.data.redis.core.RedisCallback;
   import org.springframework.data.redis.core.RedisTemplate;
   import org.springframework.stereotype.Service;
   
   import java.text.SimpleDateFormat;
   import java.util.ArrayList;
   import java.util.Calendar;
   import java.util.Date;
   import java.util.List;
   
   @Service
   public class DataServiceImpl implements DataService {
   
       @Autowired
       private RedisTemplate redisTemplate;
   
       private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
   
       @Override
       //记录单日UV
       public void recordUV(String ip) {
           //获取 key
           String redisUVKey = RedisKeyUtil.getUVKey(simpleDateFormat.format(new Date()));
           //记录 key:ip
           redisTemplate.opsForHyperLogLog().add(redisUVKey, ip);
       }
   
       @Override
       //记录单日 DAU
       public void recordDAU(int userId) {
           //获取 key
           String redisDAUKey = RedisKeyUtil.getDAUKey(simpleDateFormat.format(new Date()));
           //记录 key:ip
           redisTemplate.opsForValue().setBit(redisDAUKey, userId, true);
       }
   
       @Override
       //统计区间 UV
       public long calculateUV(Date startDate, Date endDate) {
           //判断参数是否为空，为空抛出异常
           if (startDate == null || endDate == null) {
               throw new IllegalArgumentException("参数不能为空！");
           }
           //传统的 Date 的类型无法做加减运算，这里借助 Calendar ---> Calendar.getInstance() 可以获取指定时间点的日期
           Calendar calendar = Calendar.getInstance();
           calendar.setTime(startDate);
           String redisUVAllKey = RedisKeyUtil.getDAUKey(simpleDateFormat.format(startDate), simpleDateFormat.format(endDate));
           List<String> redisUVKeyList = new ArrayList<>();
           //开始时间不能晚于结束时间，在这一范围之内的 UV 添加到 Redis 统计 UV 的 Key 中
           while (!calendar.getTime().after(endDate)) {
               String redisUVKey = RedisKeyUtil.getUVKey(simpleDateFormat.format(startDate));
               //获取这一天的数据key ---> 到时候统一存储在 redisUVAllKey 中【使用 union】 ---> 用一个 List 保存
               redisUVKeyList.add(redisUVKey);
               //起始日期加 1
               calendar.add(Calendar.DATE, 1);
           }
           //统计，将redisUVKeyList中的数据合并到redisUVAllKey中
           redisTemplate.opsForHyperLogLog().union(redisUVAllKey, redisUVKeyList.toArray());
           return redisTemplate.opsForHyperLogLog().size(redisUVAllKey);
       }
   
       @Override
       public long calculateDAU(Date startDate, Date endDate) {
           //判断参数是否为空，为空抛出异常
           if (startDate == null || endDate == null) {
               throw new IllegalArgumentException("参数不能为空！");
           }
           //传统的 Date 的类型无法做加减运算，这里借助 Calendar ---> Calendar.getInstance() 可以获取指定时间点的日期
           Calendar calendar = Calendar.getInstance();
           calendar.setTime(startDate);
           String redisDAUAllKey = RedisKeyUtil.getDAUKey(simpleDateFormat.format(startDate), simpleDateFormat.format(endDate));
           List<byte[]> redisDAUKeyList = new ArrayList<>();
           //开始时间不能晚于结束时间，在这一范围之内的 UV 添加到 Redis 统计 UV 的 Key 中
           while (!calendar.getTime().after(endDate)) {
               String redisDAUKey = RedisKeyUtil.getDAUKey(simpleDateFormat.format(startDate));
               //获取这一天的数据key ---> 到时候统一存储在 redisUVAllKey 中【使用 union】 ---> 用一个 List 保存
               redisDAUKeyList.add(redisDAUKey.getBytes());
               //起始日期加 1
               calendar.add(Calendar.DATE, 1);
           }
           //统计，一段时间内只要有登录就为 true
           return (long) redisTemplate.execute(new RedisCallback() {
               @Override
               public Object doInRedis(RedisConnection redisConnection) throws DataAccessException {
                   redisConnection.bitOp(RedisStringCommands.BitOperation.OR, redisDAUAllKey.getBytes(), redisDAUKeyList.toArray(new byte[0][0]));
                   return redisConnection.bitCount(redisDAUAllKey.getBytes());
               }
           });
       }
   }
   ```

3. `DataController.java`

   ```java
   package com.zwm.dao;
   
   import com.zwm.service.DataService;
   import com.zwm.service.impl.DataServiceImpl;
   import org.springframework.beans.factory.annotation.Autowired;
   import org.springframework.format.annotation.DateTimeFormat;
   import org.springframework.stereotype.Controller;
   import org.springframework.ui.Model;
   import org.springframework.web.bind.annotation.RequestMapping;
   import org.springframework.web.bind.annotation.RequestMethod;
   
   import java.util.Date;
   
   @Controller
   public class DataController {
       @Autowired
       private DataServiceImpl dataService;
   
       // 统计页面
       @RequestMapping(path = "/data", method = {RequestMethod.GET, RequestMethod.POST})
       public String getDataPage() {
           return "/site/admin/data";
       }
   
       //统计网站UV
       @RequestMapping(path = "/data/uv", method = RequestMethod.POST)
       public String getUV(@DateTimeFormat(pattern = "yyyy-MM-dd") Date start, @DateTimeFormat(pattern = "yyyy-MM-dd") Date end, Model model) {
           long uv = dataService.calculateUV(start, end);
           model.addAttribute("uvResult", uv);
           model.addAttribute("uvStartDate", start);
           model.addAttribute("uvEndDate", end);
           return "forward:/data";
       }
   
       //统计活跃用户DAU
       @RequestMapping(path = "/data/dau", method = RequestMethod.POST)
       public String getDAU(@DateTimeFormat(pattern = "yyyy-MM-dd") Date start, @DateTimeFormat(pattern = "yyyy-MM-dd") Date end, Model model) {
           long dau = dataService.calculateDAU(start, end);
           model.addAttribute("dauResult", dau);
           model.addAttribute("dauStartDate", start);
           model.addAttribute("dauEndDate", end);
           return "forward:/data";
       }
   }
   ```

4. 配置拦截器，每次访问该页面的时候都记录`UV DAU` ---> ``：

   ```java
   package com.zwm.controller.interceptor;
   
   import com.zwm.entity.User;
   import com.zwm.service.impl.DataServiceImpl;
   import com.zwm.util.HostHolder;
   import org.springframework.beans.factory.annotation.Autowired;
   import org.springframework.stereotype.Component;
   import org.springframework.web.servlet.HandlerInterceptor;
   
   import javax.servlet.http.HttpServletRequest;
   import javax.servlet.http.HttpServletResponse;
   
   @Component
   public class DataInterceptor implements HandlerInterceptor {
   
       @Autowired
       private DataServiceImpl dataService;
   
       @Autowired
       private HostHolder hostHolder;
   
       @Override
       public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
           //获取 IP 地址
           String ip = request.getRemoteHost();
           //记录IP独立访客数据
           dataService.recordUV(ip);
           User user = hostHolder.getUser();
           //如果当前存在登录用户，记录存活用户
           if (user != null) dataService.recordDAU(user.getId());
           return true;
       }
   }
   ```

   `WebMVCConfig`使其生效：

   ```java
   registry.addInterceptor(dataInterceptor).excludePathPatterns("/**/*.css", "/**/*.js", "/**/*.jpg", "/**/*.png", "/**/*.jpeg");
   ```

5. 创建前端配页面`data.html`：

   ```html
   <!doctype html>
   <html lang="en" xmlns:th="http://www.thymeleaf.org">
   <head>
   	<meta charset="utf-8">
   	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
   	<link rel="icon" href="https://static.nowcoder.com/images/logo_87_87.png"/>
   	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" crossorigin="anonymous">
   	<link rel="stylesheet" th:href="@{/css/global.css}" />
   	<title>牛客网-数据统计</title>
   </head>
   <body>
   <div class="nk-container">
   	<!-- 头部 -->
   	<header class="bg-dark sticky-top" th:replace="index::header">
   		<div class="container">
   			<!-- 导航 -->
   			<nav class="navbar navbar-expand-lg navbar-dark">
   				<!-- logo -->
   				<a class="navbar-brand" href="#"></a>
   				<button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
   					<span class="navbar-toggler-icon"></span>
   				</button>
   				<!-- 功能 -->
   				<div class="collapse navbar-collapse" id="navbarSupportedContent">
   					<ul class="navbar-nav mr-auto">
   						<li class="nav-item ml-3 btn-group-vertical">
   							<a class="nav-link" href="../../index.html">首页</a>
   						</li>
   						<li class="nav-item ml-3 btn-group-vertical">
   							<a class="nav-link position-relative" href="../letter.html">消息<span class="badge badge-danger">12</span></a>
   						</li>
   						<li class="nav-item ml-3 btn-group-vertical">
   							<a class="nav-link" href="../register.html">注册</a>
   						</li>
   						<li class="nav-item ml-3 btn-group-vertical">
   							<a class="nav-link" href="../login.html">登录</a>
   						</li>
   						<li class="nav-item ml-3 btn-group-vertical dropdown">
   							<a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
   								<img src="http://images.nowcoder.com/head/1t.png" class="rounded-circle" style="width:30px;"/>
   							</a>
   							<div class="dropdown-menu" aria-labelledby="navbarDropdown">
   								<a class="dropdown-item text-center" href="../profile.html">个人主页</a>
   								<a class="dropdown-item text-center" href="../setting.html">账号设置</a>
   								<a class="dropdown-item text-center" href="../login.html">退出登录</a>
   								<div class="dropdown-divider"></div>
   								<span class="dropdown-item text-center text-secondary">nowcoder</span>
   							</div>
   						</li>
   					</ul>
   					<!-- 搜索 -->
   					<form class="form-inline my-2 my-lg-0" action="../site/search.html">
   						<input class="form-control mr-sm-2" type="search" aria-label="Search" />
   						<button class="btn btn-outline-light my-2 my-sm-0" type="submit">搜索</button>
   					</form>
   				</div>
   			</nav>
   		</div>
   	</header>
   
   	<!-- 内容 -->
   	<div class="main">
   		<!-- 网站UV -->
   		<div class="container pl-5 pr-5 pt-3 pb-3 mt-3">
   			<h6 class="mt-3"><b class="square"></b> 网站 UV</h6>
   			<form class="form-inline mt-3" method="post" th:action="@{/data/uv}">
   				<input type="date" class="form-control" required name="start" th:value="${#dates.format(uvStartDate,'yyyy-MM-dd')}"/>
   				<input type="date" class="form-control ml-3" required name="end" th:value="${#dates.format(uvEndDate,'yyyy-MM-dd')}"/>
   				<button type="submit" class="btn btn-primary ml-3">开始统计</button>
   			</form>
   			<ul class="list-group mt-3 mb-3">
   				<li class="list-group-item d-flex justify-content-between align-items-center">
   					统计结果
   					<span class="badge badge-primary badge-danger font-size-14" th:text="${uvResult}">0</span>
   				</li>
   			</ul>
   		</div>
   		<!-- 活跃用户 -->
   		<div class="container pl-5 pr-5 pt-3 pb-3 mt-4">
   			<h6 class="mt-3"><b class="square"></b> 活跃用户</h6>
   			<form class="form-inline mt-3" method="post" th:action="@{/data/dau}">
   				<input type="date" class="form-control" required name="start" th:value="${#dates.format(dauStartDate,'yyyy-MM-dd')}"/>
   				<input type="date" class="form-control ml-3" required name="end" th:value="${#dates.format(dauEndDate,'yyyy-MM-dd')}"/>
   				<button type="submit" class="btn btn-primary ml-3">开始统计</button>
   			</form>
   			<ul class="list-group mt-3 mb-3">
   				<li class="list-group-item d-flex justify-content-between align-items-center">
   					统计结果
   					<span class="badge badge-primary badge-danger font-size-14" th:text="${dauResult}">0</span>
   				</li>
   			</ul>
   		</div>
   	</div>
   
   	<!-- 尾部 -->
   	<footer class="bg-dark">
   		<div class="container">
   			<div class="row">
   				<!-- 二维码 -->
   				<div class="col-4 qrcode">
   					<img src="https://uploadfiles.nowcoder.com/app/app_download.png" class="img-thumbnail" style="width:136px;" />
   				</div>
   				<!-- 公司信息 -->
   				<div class="col-8 detail-info">
   					<div class="row">
   						<div class="col">
   							<ul class="nav">
   								<li class="nav-item">
   									<a class="nav-link text-light" href="#">关于我们</a>
   								</li>
   								<li class="nav-item">
   									<a class="nav-link text-light" href="#">加入我们</a>
   								</li>
   								<li class="nav-item">
   									<a class="nav-link text-light" href="#">意见反馈</a>
   								</li>
   								<li class="nav-item">
   									<a class="nav-link text-light" href="#">企业服务</a>
   								</li>
   								<li class="nav-item">
   									<a class="nav-link text-light" href="#">联系我们</a>
   								</li>
   								<li class="nav-item">
   									<a class="nav-link text-light" href="#">免责声明</a>
   								</li>
   								<li class="nav-item">
   									<a class="nav-link text-light" href="#">友情链接</a>
   								</li>
   							</ul>
   						</div>
   					</div>
   					<div class="row">
   						<div class="col">
   							<ul class="nav btn-group-vertical company-info">
   								<li class="nav-item text-white-50">
   									公司地址：北京市朝阳区大屯路东金泉时代3-2708北京牛客科技有限公司
   								</li>
   								<li class="nav-item text-white-50">
   									联系方式：010-60728802(电话)&nbsp;&nbsp;&nbsp;&nbsp;admin@nowcoder.com
   								</li>
   								<li class="nav-item text-white-50">
   									牛客科技©2018 All rights reserved
   								</li>
   								<li class="nav-item text-white-50">
   									京ICP备14055008号-4 &nbsp;&nbsp;&nbsp;&nbsp;
   									<img src="http://static.nowcoder.com/company/images/res/ghs.png" style="width:18px;" />
   									京公网安备 11010502036488号
   								</li>
   							</ul>
   						</div>
   					</div>
   				</div>
   			</div>
   		</div>
   	</footer>
   </div>
   
   <script src="https://code.jquery.com/jquery-3.3.1.min.js" crossorigin="anonymous"></script>
   <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" crossorigin="anonymous"></script>
   <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" crossorigin="anonymous"></script>
   <script th:src="@{/js/global.js}"></script>
   </body>
   </html>
   ```

6. 显示界面如下：

   ![](https://img-blog.csdnimg.cn/a0ab02ddeb214de98799c15bcd755119.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBAQ3JBY0tlUi0x,size_20,color_FFFFFF,t_70,g_se,x_16)

### 5.13 任务执行和调度 + 热帖排行

有些功能是服务器定时处理的，比如每隔半小时清理临时存储文件等等，这样都需要任务调度组件，需要多线程运行，那么就需要一个线程池，这样就可以让线程复用，节约资源。

- `JDK`线程池【`JDK`自带的两个常用的线程池】

  > `ExecutorService` ---> 普通的线程池
  >
  > `ScheduledExecutorService` ---> 能够创建定时任务的线程池

- `Spring`线程池

  > `ThreadPoolTaskExecutor` ---> 类比于`JDK`线程池的第一个，普通的线程池
  >
  > `ThreadPoolTaskScheduler`---> 类比于`JDK`线程池的第二个，可以执行定时任务

- 分布式定时任务

  > `Spring Quartz` ---> 前面的线程池在分布式环境下使用存在一些问题，所以我们可以使用`Spring Quartz`，整合`Spring`

一张图说明在分布式环境下使用`JDK`线程池和`Spring`线程池会出现什么问题：

**通过下图可以发现，发送`Controller`请求是没有什么问题的，但是如果是定时任务，这样就会导致重复实现定时任务，这就导致了资源的浪费，`JDK`和`Spring`的参数是配置在内存的，这就导致二者的数据是不共享的，但是`Quartz`程序运行的参数的配置是保存在数据库中，我们知道，数据库是唯一的，即便同时发生也可以通过锁机制有个先后手访问。**

![](https://img-blog.csdnimg.cn/7241e5f70875409e94e6dde3a3961e72.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBAQ3JBY0tlUi0x,size_20,color_FFFFFF,t_70,g_se,x_16)

演示`JDK`线程池、`Spring`线程池以及`Quartz`线程池：`ThreadPoolTest.java`

### 5.14 生成长图

将网页转换成`PDF`/图片 ---> `wkhtmltopdf url file`、`wkhtmltoimage url file`：https://wkhtmltopdf.org ---> 下载、安装、配置环境变量 ---> 生成`PDF/IMAGE`需要提前准备好存储路径

`Java`执行`cmd`命令：`Runtime.getRuntime.exec(cmd);`

### 5.15 上传文件到云服务器

### 5.16 优化网站性能

优化性能行之有效的方法就是加缓存，这样就可以不用通过服务器访问`DataBase`，直接就可以获取，缓存主要缓存的是长时间不变的数据，如果一直有数据的更新，使用缓存变得非常不合理

- 本地缓存

  > 将数据缓存在应用服务器上，性能最好
  >
  > 常用缓存工具：`Ehcache、Guava、Caffeine【很不错效率很高】`等

- 分布式缓存

  > 将数据缓存在`NoSQL`数据库上，跨服务器，第一次访问`DataBase`存储一份到`Redis`中然后下一次访问就可以直接从`Redis`中获取数据，`Redis`的访问效率很高
  >
  > 常用缓存工具：`MemCache、Redis`等
  >
  > 比本地缓存开销要大一些，需要网络开销，比本地的开销当然会稍微大些

- 多级缓存

  > 一级缓存（本地缓存） ---> 二级缓存（分布式缓存） ---> `DataBase`
  >
  > 避免缓存雪崩（缓存失效【某一级缓存挂了】，大量请求直达`DataBase`）提高系统的可用性

### 5.17 单元测试

单元测试在开发和项目上线之前都非常的重要

- `Spring Boot Testing`

  > 依赖：`spring-boot-starter-test`
  >
  > 包括：`Junit、Spring Test、AssertJ、...`

- `Test Case`

  > 要求：保证测试方法的独立性【不是一次性的功能去执行，而是整个项目，不依赖其余的东西】
  >
  > 步骤：初始化数据、执行测试代码、验证测试结果、清理测试数据
  >
  > 常用注解：`@BeforeClass`、`@AfterClass`、`@Before`、`@After`
  >
  > 通常有多个测试方法的数据是同一个，那么就可以使用`@BeforeClass`在类加载初始化之前就使用，`@AfterClass`在类销毁之后执行

```java

import com.zwm.CommunityApplication;
import com.zwm.entity.DiscussPost;
import com.zwm.service.impl.DiscussPostServiceImpl;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class SpringBootTests {

    @Autowired
    private DiscussPostServiceImpl discussPostService;

    private DiscussPost data;

    @BeforeClass
    public static void beforeClass() {
        System.out.println("beforeClass");
    }

    @AfterClass
    public static void afterClass() {
        System.out.println("afterClass");
    }

    @Before
    public void before() {
        System.out.println("before");

        // 初始化测试数据
        data = new DiscussPost();
        data.setUserId(111);
        data.setTitle("Test Title");
        data.setContent("Test Content");
        data.setCreateTime(new Date());
        discussPostService.addDiscussPost(data);
    }

    @After
    public void after() {
        System.out.println("after");
        // 删除测试数据
        discussPostService.updateDiscussPostCommentCount(data.getId(), 2);
    }

    @Test
    public void test1() {
        System.out.println("test1");
    }

    @Test
    public void test2() {
        System.out.println("test2");
    }

    @Test
    public void testFindById() {
        DiscussPost post = discussPostService.selectDiscussPost(data.getId());
        Assert.assertNotNull(post);
        Assert.assertEquals(data.getTitle(), post.getTitle());
        Assert.assertEquals(data.getContent(), post.getContent());
    }

    @Test
    public void testUpdateScore() {
        int rows = discussPostService.updateDiscussPostCommentCount(data.getId(), 2000);
        Assert.assertEquals(1, rows);

        DiscussPost post = discussPostService.selectDiscussPost(data.getId());
        Assert.assertEquals(2000.00, post.getScore());
    }
}
```

### 5.18 项目监控

- `Spring Boot Actuator`

  > `Endpoints`：监控应用的入口，`Spring Boot`内置了很多断电，也支持自定义端点
  >
  > 监控方式：`HTTP`或`JMX`
  >
  > 访问路径：例如：`/actuator/health`
  >
  > 注意事项：按需配置暴露的端点，并对所有端点进行权限控制 ---> 只有管理员可以使用
  >
  > 有好多个端点，只有一个端点没有启用，就是关闭服务器这成了一个后门，所以一般不要启用这个端点，默认暴露了两个端点：`/actuator/health`、`/actuator/info`，只要一装上这个工具就立马可以显示信息

- 配置`Actuator`【演示】：

  ```xml
  <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-actuator</artifactId>
  </dependency>
  ```

  ```http
  http://localhost/community/actuator/health
  http://localhost/community/actuator/info
  ```

  ```properties
  management.endpoints.web.exposure.include=*
  management.endpoints.web.exposure.exclude=info,caches
  ```

  访问：`localhost/community/index/actuator/loggers`

- 自定义端点监控个性化内容：

  ```java
  package com.zwm.actuator;
  
  import com.zwm.util.CommunityUtils;
  import org.slf4j.Logger;
  import org.slf4j.LoggerFactory;
  import org.springframework.beans.factory.annotation.Autowired;
  import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
  import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
  import org.springframework.boot.actuate.endpoint.web.annotation.WebEndpoint;
  import org.springframework.stereotype.Component;
  
  import javax.sql.DataSource;
  import java.sql.Connection;
  import java.sql.SQLException;
  
  @Component
  @WebEndpoint(id = "datasource")
  public class DataBaseEndPoint {
      //监控数据库是否连接成功的端点
      @Autowired
      private DataSource dataSource;
  
      private static final Logger logger = LoggerFactory.getLogger(DataBaseEndPoint.class);
  
      @ReadOperation
      public String checkConnection() {
          try (
                  Connection connection = dataSource.getConnection();
          ) {
              return CommunityUtils.getJsonString(0, "获取数据库连接成功！");
          } catch (SQLException sqlException) {
              logger.error("获取数据库连接失败：" + sqlException.getMessage());
              return CommunityUtils.getJsonString(1, "获取数据库连接失败！");
          }
      }
  }
  ```

### 5.19 项目部署

![](https://img-blog.csdnimg.cn/8a452aa6d26b4bbd95f3c0c2a63fcdfc.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBAQ3JBY0tlUi0x,size_20,color_FFFFFF,t_70,g_se,x_16)

### 5.20 项目总结 + 题目

1. `MySQL`：存储引擎、事务、锁、索引

   - 存储引擎 ---> `MySQL 5.1`之后默认就是`InnoDB`存储引擎，因为它支持事务，还有一个引擎是`NDB`,因为它可以做集群，但是数据库能不做集群就不做集群，但是`NDB`无法做外键，不同的引擎机制不一样，只需要了解我们使用的`InnoDB`存储引擎的机制即可

   - 事务

     > 事务的特性：原子性、一致性、隔离性、持久性
     >
     > 事务的隔离性：
     >
     > - 第一类丢失更新、第二类丢失更新、脏读、不可重复读、幻读
     > - 隔离级别：读未提交、读已提交、可重复读、序列化
     >
     > `Spring`的事务管理：
     >
     > - 声明式事务
     > - 编程式事务

   - 锁

     > 范围：
     >
     > - 表级锁：开销小、加锁快、发生所得冲突的概率高、并发度低，不会出现死锁
     > - 行级锁：开销大、加锁慢、发生所得冲突的概率低、并发度高，可能会出现死锁
     >
     > 类型`（InnoDB）`：
     >
     > - 共享锁`(S)`：行级，读取一行
     > - 排他锁`(X)`：行级，更新一行
     > - 意向共享锁`(IS)`：表级，准备加共享锁
     > - 意向排他锁`(IX)`：表级，准备加排他锁
     > - 间隙锁`(NK)`：行级，使用范围条件时
     >
     > 【注：对范围内不存在的记录加锁，一是为了防止幻读，二是为了满足恢复和复制的需要】
     >
     > 加锁：
     >
     > - 增加行级锁之前，`InnoDB`会自动给表加意向锁
     > - 执行`DML`语句，`InnoDB`会自动给数据加排他锁
     > - 执行`DQL`语句，需要自己手动加
     >   - 共享锁：`select ... from ... where ... lock in share mode;`
     >   - 排他锁：`select ... from ... where ... for update;`
     >   - 间隙锁：上述`SQL`采用范围条件时，`InnoDB`对不存在的记录自动增加间隙锁
     >
     > 死锁：
     >
     > - 场景【`id = 2`和`id = 1`同时锁，后续造成死锁】
     >   - 事务1：`update t set ... where id = 1; update t set ... where id = 2;`
     >   - 事务2：`update t set ... where id = 2; update t set ... where id = 1;`
     > - 解决方案：
     >   1. 一般`InnoDB`会自动检测到，并使一个事务回滚，另一个事务继续
     >   2. 设置超时等待参数`innodB_lock_wait_timeout;`
     > - 避免死锁：
     >   1. 大多数时候死锁是人为的，也就是说逻辑有问题，所以我们避免写这种代码
     >   2. 不同的业务并发访问多个表的时候，应约定以相同的顺序来访问这些表
     >   3. 以批量的方式处理数据的时候，应该事先对数据排序，保证线程按固定的顺序来处理数据
     >   4. 在事务中，如果要更新记录，应该直接申请足够级别的锁，即排他锁
     >
     > 悲观锁（数据库）
     >
     > 乐观锁（自定义【不会发生什么问题，但是出现了问题我又可以解决】）：
     >
     > 1. 版本号机制：`update ... set ...,version=#{version+1} where ... and version=${version}`
     >
     > 2. `CAS`算法【`Compare And Swap`】
     >
     >    是一种无锁的算法，该算法涉及`3`个操作数（内存值`V`、旧值`A`、新值`B`），当`V=A`的时候，采用原子方式使用`B`更新`V`。该算法通常采用自旋操作【循环等待看阻塞的有无结束】也叫自旋锁，效率很高。但是它也有缺点：
     >
     >    - `ABA`的问题：某线程将`A`改为`B`，再改回`A`，则`CAS`会误认为`A`没被修改过
     >    - 自旋操作采用循环的方式实现，若加锁时间长，则会给`CPU`带来巨大的开销
     >    - `CAS`只能保证一个共享变量的原子操作
     >
     > 索引：
     >
     > - 使用的算法是`B+`树算法
     > - 数据分块存储，每一块称之为一页
     > - 所有的值都是按顺序存储的，并且每一个叶子到根的距离相同
     > - 非叶节点存储数据的边界，叶子节点存储指向数据行的指针
     > - 通过边界缩小数据的范围，从而避免全表扫描，加快了查找的速度

2. `Redis`：数据类型、过期策略、淘汰策略、缓存穿透、缓存击穿、缓存雪崩、分布式锁

   > 数据类型：
   >
   > - `key 512M`
   > - `string 512M`
   > - `hash 2^32 - 1`
   > - `list 2^32 - 1 `
   > - `set 2^32 - 1`
   > - `sorted set 不确定`
   > - `bitmap 512M`【本质也是字符串】
   > - `hyperloglog 12k`
   >
   > 过期策略:
   >
   > - `Redis`会把设置了过期时间的`key`放入一个独立的字典里，在`key`过期时并不会立刻删除它
   > - `Redis`会通过如下两种策略来删除过期的`key`：
   >   - 惰性删除
   >     - 客户端访问某个`key`时，`Redis`会检查该`key`是否过期，若过期则删除
   >   - 定期扫描
   >     - `Redis`默认每秒执行`10`次过期扫描（配置`hz`选项），扫描策略如下：
   >       1. 从过期字典中随机选择`20`个`key`
   >       2. 删除这`20`个`key`中已过期的`key`
   >       3. 如果过期的`key`的比例超过`25%`，则重复步骤`1`
   >
   > 淘汰策略：
   >
   > - 当`Redis`占用内存超出最大限制的时候，可采用如下策略：
   >
   >   - 让`Redis`淘汰一些数据，以腾出空间继续提供读写服务：
   >     - `noeviction`：对可能导致增大内存的命令返回错误（大多数写命令，`DEL`除外）
   >     - `volatile-ttl`：在设置了过期时间的`key`中，选择剩余寿命`(TTL)`最短的`key`，将其淘汰
   >     - `volatile-lru`：在设置了过期时间的`key`中，选择最少使用的`key(LRU算法)`，将其淘汰
   >     - `volatile-random`：在设置了过期时间的`key`中，随机选择一些将其淘汰
   >     - `allkeys-lru`：在所有的`key`中，选择最少使用的`key(LRU算法)`，将其淘汰
   >     - `allkeys-random`：在所有的`key`中，随机选择`key`，将其淘汰
   >
   > - `LRU`算法：
   >
   >   - 维护一个链表，用于顺序存储被访问过的`key`
   >   - 在访问数据的时候，用最新访问过的`key`将被移动到表头，那么最后就导致了最近最新访问的`key`都在表头，最少访问，访问次数最少的的`key`在表尾
   >
   > - `Redis`采用的是近似`LRU`算法：
   >
   >   - 给每一个`key`维护一个时间戳，淘汰时随机采样`5`个`key`，从中淘汰掉最旧的`key`。如果还是超出内存限制，则继续随机采样淘汰
   >
   >     优点：比`LRU`算法节约内存，却可以取得非常近似的效果
   >
   > 缓存穿透：
   >
   > - 场景：【通常为恶意请求】
   >   - 查询根本不存在的数据，使得请求直达存储层，导致其负载过大，甚至宕机
   > - 解决方案：
   >   1. 缓存空对象：存储层未命中后，仍将控制存入缓存层，再次访问该数据的时候，缓存层会直接返回空值
   >   2. 布隆过滤器：将所有存在的`key`提前存入布隆过滤器，在访问缓存层之前，先通过过滤器拦截，若请求的是不存在的`key`，则直接返回空值
   >
   > 缓存击穿：
   >
   > - 场景：【正常可能产生的现象】
   >
   >   - 一份热点数据，它的访问量非常大。在其缓存失效瞬间，大量请求直达存储层，导致服务崩溃。
   >
   > - 解决方案：
   >
   >   1. 加互斥锁：对数据的访问加互斥锁，当一个线程访问该数据的时候，其它线程只能等待。这个线程访问过后，缓存中线程将被重建，届时其它线程就可以直接从缓存取值。
   >
   >   2. 永不过期
   >
   >      不设置过期时间，所以不会出现失效这种情况也就不会出现上述问题，即物理上不过期。为每个`value`设置逻辑过期时间，当发现该值逻辑过期的时候，使用单独的线程重建缓存。
   >
   > 缓存雪崩：
   >
   > - 场景：【正常可能产生的现象】
   >   - 由于某些原因，缓存曾不能提供服务，导致所有的请求直达存储层，导致存储层宕机
   > - 解决方案：
   >   1. 避免同时过期：设置过期时间的时候附带一个随机数也就是随机过期，避免大量的`key`同时过期【从`key`出发】
   >   2. 构建高可用的`Redis`缓存：部署多个`Redis`实例，个别节点宕机，依然可以保持服务的整体可用【从`Redis`出发】
   >   3. 构建多级缓存：增加本地缓存，在存储层前面多加一级屏障，降低请求直达存储层的几率【从层级出发】
   >   4. 启用限流和降级措施：对存储层增加限流措施，当请求超出限制的时候，对其提供降级服务
   >
   > 分布式锁：
   >
   > - 场景：修改时，经常需要先将数据读取到内存，在内存中修改后再存回去。在分布式应用中，可能多个进程同时执行上述操作，而读取和修改非原子操作，会产生冲突。增加分布式锁，可以解决此类问题。
   >
   > - 基本原理：
   >
   >   - 同步锁：在多个线程都能访问到的地方，做一个标记，标记该数据的访问权限
   >   - 分布式锁：在多个进程都能访问到的地方，做一个标记，标记该数据的访问权限
   >
   > - 实现方式：
   >
   >   1. 基于数据库实现分布式锁
   >   2. 基于`Redis`实现分布式锁
   >   3. 基于`Zookeeper`实现分布式锁
   >
   > - 实现原则：
   >
   >   1. 安全属性：独享。在任一时刻，只有一个客户端持有锁
   >   2. 活性`A`：无死锁。即便持有锁的客户端崩溃或者网络被分裂，锁仍然可以被获取
   >   3. 活性`B`：容错。只要大部分`Redis`节点都活着，客户端就可以获取和释放锁
   >
   > - 单个`Redis`实现分布式锁：
   >
   >   1. 获取锁使用命令：`SET resource_name my_random_value NX PX 30000`
   >
   >      `NX:仅在key不存在的时候才执行成功`
   >
   >      `PX:设置锁的自动过期时间`
   >
   >   2. 通过`lua`脚本释放锁：
   >
   >      ```lua
   >      if redis.call("get", KEYS[1]) == ARGV[1] then return redis.call("del", KEYS[1]) else return 0 end
   >      ```
   >
   >   `lua`可以避免删除别的客户端获取成功的锁，把别的锁给释放了
   >
   > - 多个`Redis`实现分布式锁：
   >
   >   - `Redlock`算法，该算法有现成的实现，其`Java`版本的库为`Redisson`
   >
   >   1. 获取当前`Unix`时间，以毫秒为单位
   >   2. 依次尝试从`N`个实例，使用相同的`key`和随机值获取锁，并设置响应超时时间。如果服务器没有在规定时间内响应，客户端应该尽快尝试另外一个`Redis`实例
   >   3. 客户端使用当前时间减去开始获取锁的时间，得到获取锁的使用时间。当且仅当大多数的`Redis`节点都取到锁，并且使用的时间小于锁失效时间的时候，锁才算取得成功
   >   4. 如果取到了锁，`key`的真正有效时间等于有效时间减去获取锁使用的时间
   >   5. 如果获取锁失败，客户端应该在所有的`Redis`实例上进行解锁

3. `Spring`：`Spring IOC`、`Spring AOP`、`Spring Mvc`

   > `Bean`的作用域
   >
   > `Spring AOP`
   >
   > `Spring Mvc`的整个流程

