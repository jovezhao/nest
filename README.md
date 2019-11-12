# 概述

## 简介

Nest是一套帮助开发人员基于领域驱动设计（DDD）开发系统的一套技术框架。他定义了领域驱动设计中新工具（实体、值对象、角色、工厂、仓储、应用服务、事件），并且通过服务上下文、工作单元、缓存等模块将整个体系串联。

## 优势

Nest扩展性强，简单，易用，高效。

# 设计原则

Nest本身的设计不依赖于spring等ioc容器，而是定义了一个Ioc容器的标准接入接口。可以基于这一个标准接入接口实现不同Ioc容器的集成。

Nest的设计受DDD战略、战术设计思想指导，使用Nest前需要对DDD有一定的了解。

# 开发手册

## 演练-使用nest和nest-ioc创建可运行的项目

本案例使用nest-ioc快速创建基于DDD的项目，并且完成创建用户的演示操作

项目由领域模型、应用服务和应用程序以及nest-ioc的集成配置组成。

**领域模型代码：**
```java
package com.zhaofujun.nest.ioc.test.models;

import com.zhaofujun.nest.context.model.BaseEntity;
import com.zhaofujun.nest.context.model.StringIdentifier;


public class User extends BaseEntity<StringIdentifier> {

    private String pwd;

    public void changPwd(String newpwd) {

        this.pwd = newpwd;

    }
}

```
**应用服务代码：**
```java
package com.zhaofujun.nest.ioc.test.appservices;

import com.zhaofujun.nest.context.event.EventBus;
import com.zhaofujun.nest.ioc.annotation.AppService;
import com.zhaofujun.nest.ioc.annotation.Autowired;
import com.zhaofujun.nest.ioc.test.models.PasswordChangedEventData;
import com.zhaofujun.nest.ioc.test.models.User;
import com.zhaofujun.nest.context.model.StringIdentifier;
import com.zhaofujun.nest.context.loader.ConstructEntityLoader;
import com.zhaofujun.nest.context.loader.EntityLoader;
import com.zhaofujun.nest.context.loader.RepositoryEntityLoader;

@AppService
public class TestAppservices {

    @Autowired
    EventBus eventBus;


    public void changPwd(String userName, String newPwd) {
        EntityLoader<User> entityLoader = new RepositoryEntityLoader<>(User.class);
        User user = entityLoader.create(StringIdentifier.valueOf(userName));
        user.changPwd(newPwd);
    }


    public void createUser(String usrName, String pwd) {
        EntityLoader<User> entityLoader = new ConstructEntityLoader<>(User.class);
        User user = entityLoader.create(StringIdentifier.valueOf(usrName));
        user.changPwd(pwd);
    }
}

```

**应用程序代码**

```java
package com.zhaofujun.nest.ioc.test;

import com.zhaofujun.nest.NestApplication;
import com.zhaofujun.nest.container.ContainerProvider;
import com.zhaofujun.nest.context.event.EventBus;
import com.zhaofujun.nest.event.ApplicationEvent;
import com.zhaofujun.nest.event.ApplicationListener;
import com.zhaofujun.nest.event.ServiceContextListener;
import com.zhaofujun.nest.event.ServiceEvent;
import com.zhaofujun.nest.ioc.annotation.Component;
import com.zhaofujun.nest.ioc.annotation.Autowired;
import com.zhaofujun.nest.ioc.config.IocConfiguration;
import com.zhaofujun.nest.ioc.test.appservices.TestAppservices;

import java.lang.reflect.Method;

@Component
public class Application   {


    public Application() {


    }

    @Autowired
    private TestAppservices testAppservices;


    @Autowired
    private NestApplication nestApplication;


    //设置
    public static void main(String[] args) {

        IocConfiguration iocConfiguration = new IocConfiguration("com.zhaofujun.nest.ioc.test");
        iocConfiguration.init();



        ContainerProvider containerProvider = iocConfiguration.getContainerProvider();

        
        Application application = containerProvider.getInstance(Application.class);
        application.run();

    }


    public void run() {

        nestApplication.start();

        eventBus.autoRegister();
        testAppservices.createUser("111", "pwd");
        nestApplication.close();

    }
}
```

**nest-ioc集成配置代码**

```java
package com.zhaofujun.nest.ioc.config;

import com.zhaofujun.nest.NestApplication;
import com.zhaofujun.nest.container.ContainerProvider;
import com.zhaofujun.nest.context.event.EventBus;
import com.zhaofujun.nest.ioc.DefaultContainerProvider;

public class IocConfiguration {
    DefaultContainerProvider beanContainerProvider;

    public IocConfiguration(String prefix) {
        beanContainerProvider = new DefaultContainerProvider(prefix);
        NestApplication nestApplication = new NestApplication(beanContainerProvider);
        beanContainerProvider.register(DefaultContainerProvider.class, beanContainerProvider);
        beanContainerProvider.register(NestApplication.class, nestApplication);
        beanContainerProvider.register(EventBus.class, new EventBus(beanContainerProvider));
    }

    public void init() {


        beanContainerProvider.initialize();
    }

    public ContainerProvider getContainerProvider() {
        return beanContainerProvider;
    }
}

```

在本案例中，我们只是简单的对演示了如何对领域模型User的创建过程。
没有做任何仓储的实现，框架会提供基于内存的默认仓库来实现，这样可以帮助程序员快速完成业务部分的代码开发与测试。

## Nest进阶教程

### IOC集成方案

### 缓存管理

### 事件总线

### 异常处理

### 日志处理

### 应用及服务全生命周期


## 最佳实践 nest-plus

### DDD-分层架构

### DDD-六边型架构

### 缓存管理

### 事件总线


# 常见问题汇总
