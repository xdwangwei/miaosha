# 基于springboot+js的商品秒杀项目基础项目（前后端分离）


## 开发环境

1. maven 3.6
2. SpringBoot 2.2.4
3. IDEA 2019.3
4. mysql 8.0
5. VS Code
6. 阿里云主机+redis
7. lombok插件支持

## 依赖导入

1. springboot-web
2. hibernate validator
3. 阿里云市场短信服务相关依赖
4. mysql驱动（8.0）及数据源（com.alibaba.Druid）
5. joda-time时间支持

## 功能介绍

### 前端

1. register.html 注册页面（实现手机号+验证码注册方式）
2. login.html 登录页面（手机号+密码）
3. itemlist.html 显示商品列表
4. item.html 商品详情页以及下单功能
5. ajax交互（json数据传输）

### 后端

1. hibernate validator数据校验，自定义结果封装
2. redis存储用户注册验证码
3. 阿里云云市场短信服务，发送注册码
4. 全局异常统一处理，返回格式统一
5. 返回值封装，返回统一对象
6. 持久层、业务层、控制器层对象转换
7. mybatis generator
8. 用户注册与登录功能
9. 商品查询与增加功能
10. 订单创建功能
11. 密码加密，单独建表

### 注意事项

1. 若克隆项目或直接下载代码，请修改application.xml中redis配置的host和port，以及用于阿里云短信服务的appId（若无，请注释掉UserController注册方法中发送短信功能代码，在控制台输出语句以代替）
2. 注意mysql版本，8.0使用的驱动是com.mysql.cj.jdbc.Driver
3. mybatis-generator配置文件中，有一项是设置mysql驱动包的位置，注意修改

