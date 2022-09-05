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

   ```java
   package com.kk.giot.pojo;
   
   import lombok.AllArgsConstructor;
   import lombok.Data;
   import lombok.NoArgsConstructor;
   
   import java.util.Date;
   
   @Data
   @NoArgsConstructor
   @AllArgsConstructor
   public class DiscussPost {
       private Integer id;
       private Integer userId;
       private String title;
       private String content;
       private Integer type;
       private Integer status;
       private Integer commentCount;
       private Date createTime;
       private Double score;
   }
   ```

5. 创建帖子映射接口 ---> `mapper/DiscussPostMapper`

   ```java
   package com.kk.giot.mapper;
   
   import com.baomidou.mybatisplus.core.mapper.BaseMapper;
   import com.kk.giot.pojo.DiscussPost;
   import org.apache.ibatis.annotations.Mapper;
   
   @Mapper
   public interface DiscussPostMapper extends BaseMapper<DiscussPost> {
   }
   ```

6. 创建帖子服务类接口 ---> `service/DiscussPostService`

   ```java
   package com.kk.giot.service;
   
   import com.baomidou.mybatisplus.extension.service.IService;
   import com.kk.giot.pojo.DiscussPost;
   
   public interface DiscussPostService extends IService<DiscussPost> {
   }
   ```

7. 创建帖子服务类接口实现类 ---> `service/impl/DiscussPostServiceImpl`

   ```java
   package com.kk.giot.service.impl;
   
   import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
   import com.kk.giot.mapper.DiscussPostMapper;
   import com.kk.giot.pojo.DiscussPost;
   import com.kk.giot.service.DiscussPostService;
   import org.springframework.stereotype.Service;
   
   @Service
   public class DiscussPostServiceImpl extends ServiceImpl<DiscussPostMapper, DiscussPost> implements DiscussPostService {
   }
   ```

8. 创建帖子表现层类，并且自定义一个方法：实现分页查询，查询的是没有被拉黑的即`status != 2`，默认每页显示`10`条（使用`MyBatisPlus`自带的分页插件完成）【静态资源已放置在了`static、templates`目录中】

   首先观察前端，可以发现要显示的帖子数据包裹在了：`discussPosts`中，而且这是一个集合，集合里面的元素是一个个的`map`，因为显示的一个帖子里面存放了`DiscussPost`还有`User`，这里可以创建一个`DiscussPostAndUserPageDTO`用于封装整个对象：帖子 + 用户【用户因为还没创建，所以得先创建用户相关的三层架构类】，也可以使用`Map`的方式封装返回，这里使用第一种方式

   ```java
   package com.kk.giot.pojo.page;
   
   import com.kk.giot.pojo.DiscussPost;
   import com.kk.giot.pojo.User;
   import lombok.AllArgsConstructor;
   import lombok.Data;
   import lombok.NoArgsConstructor;
   
   @Data
   @NoArgsConstructor
   @AllArgsConstructor
   public class DiscussPostAndUserPageDTO {
       private DiscussPost post;
       private User user;
   }
   ```

   并且这里需要使用分页显示：因为使用的是`MyBatisPlus`你得使用拦截器拦截分页信息

   ```java
   package com.kk.giot.controller;
   
   import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
   import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
   import com.kk.giot.pojo.DiscussPost;
   import com.kk.giot.pojo.DiscussPostAndUser;
   import com.kk.giot.pojo.User;
   import com.kk.giot.pojo.page.PageDTO;
   import com.kk.giot.service.DiscussPostService;
   import com.kk.giot.service.UserService;
   import lombok.extern.slf4j.Slf4j;
   import org.springframework.beans.factory.annotation.Autowired;
   import org.springframework.stereotype.Controller;
   import org.springframework.ui.Model;
   import org.springframework.web.bind.annotation.GetMapping;
   import org.springframework.web.bind.annotation.RequestParam;
   
   import java.util.ArrayList;
   import java.util.List;
   
   @Slf4j
   @Controller
   public class HomeController {
   
       @Autowired
       private DiscussPostService discussPostService;
   
       @Autowired
       private UserService userService;
   
       /**
        * 使用 MyBatisPlus 进行分页查询
        *
        * @param model
        * @param current
        * @param size
        * @return
        */
       @GetMapping(value = "/index")
       public String getIndexPage(Model model, @RequestParam(value = "current", defaultValue = "1") Long current, @RequestParam(value = "size", defaultValue = "10") Long size) {
           Page<DiscussPost> discussPostPage = discussPostService.query().ne("status", 2).page(new Page<DiscussPost>(current, size));
           System.out.println(discussPostPage.getTotal());
           List<DiscussPost> discussPostList = discussPostPage.getRecords();
           List<DiscussPostAndUser> discussPosts = new ArrayList<>();
           if (discussPostList != null) {
               for (DiscussPost discussPost : discussPostList) {
                   Integer userId = discussPost.getUserId();
                   User user = userService.getById(userId);
                   DiscussPostAndUser discussPostAndUserPageDTO = new DiscussPostAndUser(discussPost, user);
                   discussPosts.add(discussPostAndUserPageDTO);
               }
           }
           PageDTO pageDTO = new PageDTO(discussPostPage.getTotal(), discussPosts, discussPostPage);
           model.addAttribute("pageDTO", pageDTO);
           return "/index";
       }
   }
   ```

   ```java
   package com.kk.giot.pojo.page;
   
   import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
   import com.kk.giot.pojo.DiscussPost;
   import com.kk.giot.pojo.DiscussPostAndUser;
   import lombok.AllArgsConstructor;
   import lombok.Data;
   import lombok.NoArgsConstructor;
   
   import java.util.List;
   
   @Data
   @NoArgsConstructor
   @AllArgsConstructor
   public class PageDTO {
       private Long total;
       private List<DiscussPostAndUser> discussPosts;
       private Page<DiscussPost> page;
   }
   ```

   ```java
   package com.kk.giot.pojo;
   
   import lombok.AllArgsConstructor;
   import lombok.Data;
   import lombok.NoArgsConstructor;
   
   @Data
   @NoArgsConstructor
   @AllArgsConstructor
   public class DiscussPostAndUser {
       //帖子列表单项是由帖子实体 + 用户构成的，所以这里封装成：DiscussPostAndUser
       private DiscussPost post;
       private User user;
   }
   ```

   ```html
   <!-- 分页 -->
   <nav class="mt-5" th:if="${pageDTO.total>0}" th:fragment="pagination">
       <ul class="pagination justify-content-center">
           <li class="page-item">
               <!--<a class="page-link" th:href="@{${page.path}(current=1)}">首页</a>-->
               <a class="page-link" th:href="'/index?current=1'">首页</a>
           </li>
           <li th:class="|page-item ${pageDTO.page.current==1?'disabled':''}|">
               <a class="page-link" th:href="'/index?current=' + ${pageDTO.page.current-1}">上一页</a></li>
           <li th:class="|page-item ${i==pageDTO.page.current?'active':''}|" th:each="i:${#numbers.sequence((pageDTO.page.current - 3) < 1 ? 1 : (pageDTO.page.current - 3),(pageDTO.page.current + 3) > ((pageDTO.page.total % pageDTO.page.size) == 0 ? (pageDTO.page.total / pageDTO.page.size) : (pageDTO.page.total / pageDTO.page.size + 1)) ? ((pageDTO.page.total % pageDTO.page.size) == 0 ? (pageDTO.page.total / pageDTO.page.size) : (pageDTO.page.total / pageDTO.page.size + 1)) : (pageDTO.page.current + 3))}">
               <a class="page-link" th:href="'/index?current=' + ${i}" th:text="${i}">1</a>
           </li>
           <li th:class="|page-item ${pageDTO.page.current==((pageDTO.page.total % pageDTO.page.size) == 0 ? (pageDTO.page.total / pageDTO.page.size) : (pageDTO.page.total / pageDTO.page.size + 1))?'disabled':''}|">
               <a class="page-link" th:href="'/index?current=' + ${pageDTO.page.current+1}">下一页</a>
           </li>
           <li class="page-item">
               <a class="page-link" th:href="'/index?current=' + ${(pageDTO.page.total % pageDTO.page.size) == 0 ? (pageDTO.page.total / pageDTO.page.size) : (pageDTO.page.total / pageDTO.page.size + 1)}">末页</a>
           </li>
       </ul>
   </nav>
   ```

   ```java
   package com.kk.giot.configuration;
   
   import com.baomidou.mybatisplus.annotation.DbType;
   import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
   import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
   import org.springframework.context.annotation.Bean;
   import org.springframework.context.annotation.Configuration;
   
   @Configuration
   public class MyConfiguration {
       @Bean
       public MybatisPlusInterceptor mybatisPlusInterceptor() {
           MybatisPlusInterceptor mybatisPlusInterceptor = new MybatisPlusInterceptor();
           mybatisPlusInterceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
           return mybatisPlusInterceptor;
       }
   }
   ```

9. 首页这就搞定了，通常有了首页第一个都是实现用户注册功能。

   因为注册需要接收验证码，所以这里要结合`SpringBoot + Email`的功能：`spring-boot-starter-mail`

   注册、登录都属于`LoginController`：

   数据库中的密码是经过`MD5`加密过后的数据，所以后续登录时需要比对的话也需要将用户这边传递进来的数据进行加密。

   

   