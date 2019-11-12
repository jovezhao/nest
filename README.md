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

### Ioc容器集成方式

nest提供了默认的nest-ioc容器，nest-ioc是一个极度简的ioc实现方案，提供了容器管理、依赖注入的功能，通过注解完成包扫描与注入。

当我们的项目中没有使用spring等相关的ioc容器时，可以用nest-ioc简化代码开发，在企业项目中推荐使用spring ioc做为nest的容器提供者。

集成一个容器，只需要实现com.zhaofujun.nest.container.ContainerProvider接口即可。

#### nest-ioc 实现方式
nest-ioc定义了AppService注解、Component注解、Store注解以及Autowired注解

**AppService注解**
用于定义应用服务，当使用了该注释的类，nest-ioc会认为这是一个应用服务，调用该应用服务中的方法时会通过切面初始化一个服务上下文和工作单元。在该应用服务中的所有领域对象的变化都将记录在工作单元中，直到应用服务方法调用完毕后一起提交给对应的仓储。

**Component注解**
用于定义一个类可以被nest-ioc容器扫描并且托管

**Store注解**
用户定义这个类是一个仓储的实现，该注解目前没有实际作用，与Component注解效果一致。

**Autowired注解**
作用于类的字段上，当这个类被nest-ioc容器托管时，使用了该注解的字段将自动从容器中获取实例并且注入相应的值

> 使用方式见 [演练-使用nest和nest-ioc创建可运行的项目](#演练-使用nest和nest-ioc创建可运行的项目)

#### 实现spring的集成
> 见[集成Spring与Spring boot](#集成Spring与Spring-boot)

### 缓存管理

Nest 将不同场景下用到缓存的信息进行了分组，为每一个分组指定了一个代号"cacheCode""。

在使用时，通过com.zhaofujun.nest.cache.CacheClientFactory的getCacheClient方法获取一个CacheClient。

CacheClient对缓存的操作见如下定义

```java

public interface CacheClient {
    <T> T get(Class<T> clazz, String key);


    <T> Map<String, T> get(Class<T> clazz, String... keys);


    void put(String key, Object value, long idleSeconds);

    void put(String key, Object value);

    boolean remove(String key);

    void removeAll();

    boolean containsKey(String key);

    String[] getKeys();
}

```
缓存分组信息通过com.zhaofujun.nest.configuration.ConfigurationManager管理。

开发人员可以通过ConfigurationManager类的 register(CacheConfiguration cacheConfiguration)方法手动注册一组缓存配置，也可以将CacheConfiguration的bean配置到ioc容器中由ConfigurationManager去自动发现。

CacheConfiguration 可以配置缓存组的代号、名称、使用的缓存提供者和统一的过期时间。

如果配置的缓存提供者无法找到，系统将使用默认的缓存提供者。

默认缓存提供者使用ehcache支持，我们也可以通过实现com.zhaofujun.nest.cache.provider.CacheProvider接口来集成其它缓存，比如Redis。

> 缓存集成方案见： [缓存通道扩展与集成](#缓存通道扩展与集成)


### 事件总线

### 异常处理

### 日志处理

### 应用及服务全生命周期


## 最佳实践 nest-plus

### DDD-分层架构

### DDD-六边型架构

### 集成Spring与Spring boot

### 缓存通道扩展与集成

### 事件通道扩展与集成


# 常见问题汇总
