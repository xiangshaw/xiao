# 本地热点新闻

<p style="text-align: center">
 <a href="https://badgen.net/https/cal-badge-icd0onfvrxx6.runkit.sh/Asia/Shanghai" target="_blank"><img alt="Read the Docs" src="https://badgen.net/https/cal-badge-icd0onfvrxx6.runkit.sh/Asia/Shanghai"></a><br/>
 <a href="https://gitee.com/xiangshaw/xiao" target="_blank"><img alt="Gitee" src="https://img.shields.io/badge/Gitee-xiao-orange?style=social&logo=gitee&colorA=F77234&link=https://gitee.com/xiangshaw/xiao"></a>
 <a href="https://github.com/xiangshaw/xiao" target="_blank"><img alt="GitHub" src="https://img.shields.io/badge/Github-xiao-orange?style=social&logo=github&colorA=F77234&link=https://github.com/xiangshaw/xiao"></a>
 <a href="https://github.com/xiangshaw/xiao" target="_blank"><img alt="stars" src="https://badgen.net/github/stars/xiangshaw/xiao"></a>
 <a href="https://github.com/xiangshaw/xiao" target="_blank"><img alt="release" src="https://badgen.net/github/release/xiangshaw/xiao"></a>
</p>


- 积丝成寸，积寸成尺；尺寸不已，遂成丈匹。

> 结合自己学过的技术，编写项目
> 
>---
>项目描述: 本地热点新闻是一款最热最新的新闻资讯平台，通过系统计算分类，分析用户喜好精准推送资讯新闻从而满足用户的需求。
整个项目采用前后端分离技术架构，基于 SpringBoot+SpringCloud 构建，数据访问层使用 MyBatis Plus；
使用MySQL 进行数据存储，通过消息中间件 Kafka 实现的消息的 异步处理和服务之间松耦合，通过缓存 Redis、以及评论数据存储 MongDB 减轻数据库访问压力；
搜索功能使用 ElasticSearch 配合 IK 分词器实现站内搜索；
- 软件环境: MySQL + Redis + MongoDB + FastDFS + Ngnix + Maven + Git + IDEA + JDK1.8
- 项目技术: SpringBoot + SpringCloud + Nacos + Seata + MyBatis Plus + Kafka + Elasticsearch + xxl-job + kafka stream
## 已经完成的功能
- [x] 接口通用请求和响应
- [x] 标签管理
- [x] 标签新增时，数据已存在判断
- [x] 集成Swagger\knife4j
- [x] 通用异常处理
- [x] 敏感词管理
- [x] JWT加密
- [x] admin登陆实现
- [x] nacos注册中心
- [x] admin网关-全局过滤器JWT校验
- [x] 查询用户认证
- [x] 认证后自媒体用户
- [x] 文章作者
- [x] 用户审核
- [x] 在实名认证是否通过，通过就 创建自媒体账户 并 创建作者
- [x] 底栏徽章
- [x] 集成Seata分布式事务（还存在问题）
- [x] 素材图片上传、查询、删除、收藏、取消收藏
- [x] 集成FastDFS
- [x] 自媒体文章-列表查询、标签列表展示、发布、修改、保存草稿、删除、上下架、审核
- [x] 首页文章展示
- [x] app端的网关搭建
- [x] app端文章列表、详情展示
- [x] 解决Long类型精度丢失问题
- [x] app端登录
- [x] app端用户关注/取消关注 作者
- [x] 用户行为微服务-关注、阅读、不喜欢、收藏行为记录
- [x] 文章关系展示
- [x] 引入mongodb 保存评论数据
- [x] 评论列表、点赞评论、回复评论列表、回复评论、点赞回复评论、
- [x] Elasticsearch文章搜索
- [x] 搜索新增、查、删
- [x] 关键字联想词
- [x] 新热文章计算

