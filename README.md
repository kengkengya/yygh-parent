# yygh-parent

#### 介绍
基于springboot的一个预约挂号系统（简单版），有助于自己实现并且理解springboot的开发流程和开发工具。
#### 技术点总结
1. Springboot2.x    
    1.1 启动类一般放在com.atguigu.yygh.xxx.ServiceApplication,如果需要不需要访问数据库就@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)   
    1.2 配置文件一般放在config文件夹下，主要配置各类的配置文件。  
2. SpringCloud  
    1.1 nacos  注册中心  
    1.2 Feign  服务调用  
    1.3 Gateway  网关整合微服务  
3. Redis  
    1.1 使用Redis 作为缓存使用  
    1.2 验证码有效时间  
    1.3 验证码有效时间  
4. MongoDB  
    1.1 使用MongoDB存储医院相关数据  
    1.2 使用mongoTemplate、mongoRepository操作进行聚合、统计等操作  
5. EasyExcel  
    1.1 数据导入导出  
6. MyBatisPlus  
    1.1 在service里面继承Iservice、在mapper里面继承BaseMapper  
7. RabbitMq  
    1.1 消息队列模块、订单相关操作发送mq消息，增强并发功能  
    1.2 封装了一个发送消息的工具类，然后在订单功能里面使用，在receiver里面进行监听接收  
8. aliyun-oss  
    1.1 花钱、未实现  
9. WX-PLAY  
    1.1支付、退款，调用微信接口，参考SDK文档
10. Docker  
    1.1 docker pull  
    1.2 docker run   
11. 容联云  
    1.1 免费短信服务  
12. 定时任务  
    启动@Scheduling 注解  
13. -------前端技术--------vue  
14. element-ui  
15. nuxt 后端SSR渲染技术  
16. npm  
17. echarts
 





#### 软件架构

![软件架构说明](https://images.gitee.com/uploads/images/2021/0714/101216_9d9a0d6c_8238864.png "尚医通架构图.png")

#### 业务流程
![业务流程](https://images.gitee.com/uploads/images/2021/0714/101424_e6104b29_8238864.png "尚医通业务流程.png")


#### 开发软件

1.  IDEA
2.  Navicat
3.  Redis-desktop

#### 使用说明

1.  个人自测项目





