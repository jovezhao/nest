# 概述

## 简介

Nest是一套帮助开发人员基于领域驱动设计（DDD）开发系统的一套技术框架。他定义了领域驱动设计中新工具（实体、值对象、角色、工厂、仓储、应用服务、事件），并且通过服务上下文、工作单元、缓存等模块将整个体系串联。

## 优势

Nest扩展性强，简单，易用，高效。

# 设计原则

Nest本身的设计不依赖于spring等ioc容器，而是定义了一个Ioc容器的标准接入接口。可以基于这一个标准接入接口实现不同Ioc容器的集成。

Nest的设计受DDD战略、战术设计思想指导，使用Nest前需要对DDD有一定的了解。

# 开发手册

## 如何获取 nest

**Maven引用**
```xml
<!-- https://mvnrepository.com/artifact/com.zhaofujun.nest/nest-ddd -->
<dependency>
    <groupId>com.zhaofujun.nest</groupId>
    <artifactId>nest-ddd</artifactId>
    <version>1.2.RELEASE</version>
</dependency>
```
**Gradle引用**
```groovy
// https://mvnrepository.com/artifact/com.zhaofujun.nest/nest-ddd
compile group: 'com.zhaofujun.nest', name: 'nest-ddd', version: '1.2.RELEASE'

```

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

默认缓存提供者使用ehcache支持，我们也可以通过实现com.zhaofujun.nest.cache.provider.CacheProvider接口来集成其它缓存中间件，比如Redis。

> 缓存中间件集成方案见： [缓存通道扩展与集成](#缓存通道扩展与集成)

### Nest与领域驱动设计(DDD)的对应关系

在DDD中，有几个重要概念，它们分别是实体、实体标识、值对象、聚合、仓储、工厂、限界上下文、应用服务、领域服务、事件、域&子域等。


其中域&子域是战略设计的重要成果，用来表达一个问题空间。一个子域可能会有一个或多个限界上下文组成，共同协调解决问题空间中的问题域。

限界上下文常常可以定义为一个可以独立部署的应用程序，对应于问题空间，限界上下文代表了一个问题空间的解决方案。

将限界上下文当成一个独立的黑盒系统，这个限界上下文所具备的能力由应用服务与事件来表达。这也是多个限界上下文之间交互的重要手段。

而限界上下文的核心能力都是由内部的领域模型提供，领域模型是基于当前问题空间进行战术设计的产物。所以对领域模型的建模便是战术设计的工作核心。

领域模型由实体、实体标识、值对象、聚合、领域服务组成。

而仓储、工厂分别是对领域模型的生命周期而服务，仓储负责对模型的持久化与重新加载，工厂封装了实体被创建的一系列细节。

为了让大家更好完成领域建模，这里将对实体、值对象、聚合、领域服务进行简单的介绍。

**实体**

通过在通用语言中提练出来的一些重要名词，并且这些名词将在后继业务中被使用，我们往往通过一个唯一标识来表达（引用）这个名词，并且关注这个名词的生命周期，在战术设计建模中，我们常常用实体来标识这些名词。

所以实体具有两个重要特性，一是它需要有唯一标识，并且可以通过这个唯一标识来引用这个实体，二是我们需要关注他的生命周期（构建、状态变化、持久化、重新加载）。


在领域建模中，实体不单单通过属性来表达自己的形态，还存在一系列行为，这些行为可以自组织的管理实体内部的属性变化。

**值对象**

与实体相似的另一个概念是值对象，它也是从通用语言中提炼出来的名词。区别于实体，我们对值对象不关注它的生命周期，正因为如此，值对象往往不会独立存在，它常常用来描述实体的一些特性。值对象从创建之后就不应该有状态变化，他对于实体而言应该是没有负作用的，如果需要对一个值对象进行修改，常常是创建一个新的值对象去替代原来的值对象。

值对象更像是一个复杂结构，由一系列的基本属性组合成的结构体，服务于实体。

**领域建模**

领域建模是DDD战术设计的重要内容
分析一个东西是否是实体时需要在当前业务场景中来分析，比如同样是商品信息，在商品上下文中，他是一个实体，我们可以对这个商品进行上下架操作。我们可以通过商品的编号来唯一标识该商品信息。而在订单上下文中，他就成了一个值对象，他只是用来描述订单信息的一个组成部分，在订单被创建之后，这些商品信息就不会再发生变化，即使原来的商品已经被删除，这里的商品信息也不会受到影响。因为值对象的存储方式是一份副本，而不是基于实体标识的引用。

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
