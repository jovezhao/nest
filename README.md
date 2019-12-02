# 概述

## 简介

Nest是一套帮助开发人员基于领域驱动设计（DDD）开发系统的一套技术框架。他定义了领域驱动设计中新工具（实体、值对象、角色、工厂、仓储、应用服务、事件），并且通过服务上下文、工作单元、缓存等模块将整个体系串联。

## 优势

Nest基于DDD理论设计。

扩展性强，可以自由扩展任何Ioc容器、消息中间件、缓存中间件等。

简单易用，不加入过多配置，一个注解便可以无缝对接，节省大量开发配置时间。


# 设计原则

Nest本身的设计不依赖于spring等ioc容器，而是定义了一个Ioc容器的标准接入接口。可以基于这一个标准接入接口实现不同Ioc容器的集成。

Nest的设计受DDD战略、战术设计思想指导，使用Nest前需要对DDD有一定的了解。可以参考[Nest与领域驱动设计(DDD)的对应关系](#Nest与领域驱动设计(DDD)的对应关系)

# 开发手册

## 如何获取 nest

**Maven引用**
```xml
<!-- https://mvnrepository.com/artifact/com.zhaofujun.nest/nest-ddd -->
<dependency>
    <groupId>com.zhaofujun.nest</groupId>
    <artifactId>nest-ddd</artifactId>
    <version>2.0.4</version>
</dependency>
```
**Gradle引用**
```groovy
// https://mvnrepository.com/artifact/com.zhaofujun.nest/nest-ddd
compile group: 'com.zhaofujun.nest', name: 'nest-ddd', version: '2.0.4'

```

## 快速上手演练-使用nest和nest-ioc创建可运行的项目

本案例使用nest-ioc快速创建基于DDD的项目，并且完成创建用户的演示操作

项目由领域模型、应用服务和应用程序以及nest-ioc的集成配置组成。

**领域模型代码：**
```java
package com.zhaofujun.nest.ioc.test.models;

import com.zhaofujun.nest.core.BaseEntity;
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

import com.zhaofujun.nest.core.EventBus;
import com.zhaofujun.nest.ioc.annotation.AppService;
import com.zhaofujun.nest.ioc.annotation.Autowired;
import com.zhaofujun.nest.ioc.test.models.PasswordChangedEventData;
import com.zhaofujun.nest.ioc.test.models.User;
import com.zhaofujun.nest.context.model.StringIdentifier;
import com.zhaofujun.nest.context.loader.ConstructEntityLoader;
import com.zhaofujun.nest.core.EntityLoader;
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
import com.zhaofujun.nest.core.EventBus;
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
import com.zhaofujun.nest.core.EventBus;
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

nest提供了默认的`nest-ioc`容器，`nest-ioc`是一个极度精简的ioc实现方案，提供了容器管理、依赖注入的功能，通过注解完成包扫描与注入。

当我们的项目中没有使用spring等相关的ioc容器时，可以用`nest-ioc`简化代码开发，在企业项目中推荐使用spring ioc做为nest的容器提供者。

集成一个容器，只需要实现`com.zhaofujun.nest.container.ContainerProvider`接口即可。

#### nest-ioc 实现方式
nest-ioc定义了AppService注解、Component注解、Store注解以及Autowired注解

**`@AppService`注解**
用于定义应用服务，当使用了该注释的类，nest-ioc会认为这是一个应用服务，调用该应用服务中的方法时会通过切面初始化一个服务上下文和工作单元。在该应用服务中的所有领域对象的变化都将记录在工作单元中，直到应用服务方法调用完毕后一起提交给对应的仓储。

**`@Component`注解**
用于定义一个类可以被`nest-ioc`容器扫描并且托管

**`@Store`注解**
用户定义这个类是一个仓储的实现，该注解目前没有实际作用，与`@Component`注解效果一致。

**`@Autowired`注解**
作用于类的字段上，当这个类被nest-ioc容器托管时，使用了该注解的字段将自动从容器中获取实例并且注入相应的值

> 使用方式见 [演练-使用nest和nest-ioc创建可运行的项目](#演练-使用nest和nest-ioc创建可运行的项目)

#### 实现spring的集成
> 见[集成Spring与Spring boot](#集成Spring与Spring-boot)

### 缓存管理

Nest 将不同场景下用到缓存的信息进行了分组，为每一个分组指定了一个代号`cacheCode`。

在使用时，通过`com.zhaofujun.nest.cache.CacheClientFactory`的getCacheClient方法获取一个`CacheClient`。

`CacheClient`对缓存的操作见如下定义

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
缓存分组信息通过`com.zhaofujun.nest.configuration.ConfigurationManager`管理。

开发人员可以通过`ConfigurationManager.register(CacheConfiguration cacheConfiguration)`方法手动注册一组缓存配置，也可以将`CacheConfiguration`的bean配置到ioc容器中由`ConfigurationManager`去自动发现。

`CacheConfiguration` 可以配置缓存组的代号、名称、使用的缓存提供者和统一的过期时间。

如果配置的缓存提供者无法找到，系统将使用默认的缓存提供者。

默认缓存提供者使用ehcache支持，我们也可以通过实现`com.zhaofujun.nest.cache.provider.CacheProvider`接口来集成其它缓存中间件，比如`Redis`。

> 缓存中间件集成方案见： [缓存通道扩展与集成](#缓存通道扩展与集成)

### Nest与领域驱动设计(DDD)的对应关系

在DDD中，有几个重要概念，它们分别是实体、实体标识、值对象、聚合、仓储、工厂、限界上下文、应用服务、领域服务、事件、域&子域等。


其中域&子域是战略设计的重要成果，用来表达一个问题空间。一个子域可能会有一个或多个限界上下文组成，共同协调解决问题空间中的问题域。

限界上下文常常可以定义为一个可以独立部署的应用程序，对应于问题空间，限界上下文代表了一个问题空间的解决方案。

将限界上下文当成一个独立的黑盒系统，这个限界上下文所具备的能力由应用服务与事件来表达。这也是多个限界上下文之间交互的重要手段。

而限界上下文的核心能力都是由内部的领域模型提供，领域模型是基于当前问题空间进行战术设计的产物。所以对领域模型的建模便是战术设计的工作核心。

领域模型由实体、实体标识、值对象、聚合、领域服务组成。

而仓储、工厂分别是对领域模型的生命周期而服务，仓储负责对模型的持久化与重新加载，工厂封装了实体被创建的一系列细节。

#### 领域建模

为了让大家更好完成领域建模，这里将对实体、值对象、聚合、领域服务进行简单的介绍。

**实体**

通过在通用语言中提练出来的一些重要名词，并且这些名词将在后继业务中被使用，我们往往通过一个唯一标识来表达（引用）这个名词，并且关注这个名词的生命周期，在战术设计建模中，我们常常用实体来标识这些名词。

所以实体具有两个重要特性，一是它需要有唯一标识，并且可以通过这个唯一标识来引用这个实体，二是我们需要关注他的生命周期（构建、状态变化、持久化、重新加载）。


在领域建模中，实体不单单通过属性来表达自己的形态，还存在一系列行为，这些行为可以自组织的管理实体内部的属性变化。

**值对象**

与实体相似的另一个概念是值对象，它也是从通用语言中提炼出来的名词。区别于实体，我们对值对象不关注它的生命周期，正因为如此，值对象往往不会独立存在，它常常用来描述实体的一些特性。值对象从创建之后就不应该有状态变化，他对于实体而言应该是没有负作用的，如果需要对一个值对象进行修改，常常是创建一个新的值对象去替代原来的值对象。

值对象更像是一个复杂结构，由一系列的基本属性组合成的结构体，服务于实体。

**聚合**

将一系列关系密切的实体、值对象联系在一起，组成的一个比较大的实体。我们将其称为一个聚合，但聚合过大时往往会带来一些不好的影响。

**领域服务**

在通用语言中抽象出来的一些动作，这些动作往往需要协调多个实体才能完成，而这样的行为放在其中任何一个实体中都不那么和谐时，便可以用领域服务来处理。 但是我们不要太过于依赖领域服务，只有在没有办法的情况下才用领域服务，否则容易产生贫血模式。


**领域建模原则**

领域建模是DDD战术设计的重要内容。其中最基础的模型过程便是区分实体与值对象。

分析一个名词是否是实体时需要在当前业务场景中来分析，比如同样是商品信息，在商品上下文中，他是一个实体，我们可以对这个商品进行上下架操作。我们可以通过商品的编号来唯一标识该商品信息。而在订单上下文中，他就成了一个值对象，他只是用来描述订单信息的一个组成部分，在订单被创建之后，这些商品信息就不会再发生变化，即使原来的商品已经被删除，这里的商品信息也不会受到影响。因为值对象的存储方式是一份副本，而不是基于实体标识的引用。

领域模型应该包括实体、值对象、领域服务。并且可以通过领域模型图完成业务演练与代码开发指导。

#### 应用服务与事件

应用服务与事件代表了当前上下文具有的能力及交互方式。应用服务的定义应该基于用例，并且一个应用服务代表了一个事务。应用服务通封装领域模型中的能力组装业务逻辑。事件的发布也需要通过应用服务来触发。

#### 四色原型建模
暂略...

#### 使用Nest关于DDD的定义

根据DDD的术语，Nest的支持对应关系如下：

DDD术语 | Nest
---|---
限界上下文 | NestApplication
应用服务 | AppService
实体 | BaseEntity
实体标识 | Identifier
值对象 | ValueObject
实体工厂 | EntityFactory
领域事件 | EventBus
仓储 | Repository

**定义一个实体**

要定义一个实体，只需要将该类继承`com.zhaofujun.nest.core.BaseEntity<T extends Identifier>`。

Nest为开发人员提供了`com.zhaofujun.nest.context.model.Entity`，它默认使用了`com.zhaofujun.nest.context.model.StringIdentifier`作为实体标识。

实体的标识可以按要求自定义。系统还提供了`com.zhaofujun.nest.context.model.UUIdentifier`给开发人员选择，开发人员也可以继承`com.zhaofujun.nest.core.Identifier`实现自定义的实体标识。

**为实体实现仓储**

实体是需要关注基生命周期的，可以通过实现`com.zhaofujun.nest.core.Repository<T extends BaseEntity>`完成实体的持久化处理。该接口定义了`insert`、`update`、`delete`方法用于对数据库的操作。同时还定义了`batchInsert`、`batchUpdate`、`batchDelete`方法用于批量处理，批量处理方法都提供了默认的实现，如果需要批量处理的数据量较大，建议使用数据库的batch方式提交。

仓储将通过`RepositoryFactory`从容器中加载，所以仓储定义后需要使用容器来托管。


**如何加载或创建实体**

可以使用实体工厂`com.zhaofujun.nest.core.EntityFactory`来加载或创建一个实体，`load`方法将通过仓储来加载，在仓储加载之前优先使用当前工作单元中的实体，如果当前工作单元中找不到，就会去缓存加载，如果缓存也没有才会使用仓储在数据库中去加载。`create`方法将创建一个全新的实体，建议按实体的标识建立数据库唯一索引，可以有效利用数据库的一些额外能力，比如提升查询性能、处理重复数据等。


### 事件总线

当系统需要接受外部的异步消息或发布事件时，可以通过事件总线`com.zhaofujun.nest.core.EventBus`来处理。 

事件总线的定义如下：
```java
package com.zhaofujun.nest.core;


import com.zhaofujun.nest.context.event.EventData;


public interface EventBus{
    void autoRegister();
    void registerHandler(EventHandler eventHandler);
    void publish(EventData eventData);
}

```
**事件订阅**

`autoRegister`方法自动从容器中扫描注册EventHandler，也可以通过`registerHandler`手动注册一个EventHandler。

**事件发布**

`publish`方法发布一个事件，发布一个事件需要实现`com.zhaofujun.nest.core.EventData`抽象类做为事件发布的内容。 其中`getEventCode`抽象方法定义当前事件代号。事件代号可以通过配置指定当前事件发送或接受的管道。

**事件管道配置**

事件管道的配置信息由配置管理器`com.zhaofujun.nest.configuration.ConfigurationManager`管理，配置管理器优先从静态注册的配置信息中获取配置项，如果没有找到再从容器中查找，如果仍然没有找到将使用内置的默认管道发布或订阅事件。

事件管道配置`com.zhaofujun.nest.configuration.EventConfiguration`用事件代号与管道代号连接事件与管道的关系。在容器下，我们可以直接通过定义`EventConfiguration`类型的bean来完成配置。

#### 代码演示


**事件数据 PasswordChangedEventData**

```java
package com.zhaofujun.nest.ioc.test.models;

import com.zhaofujun.nest.core.EventData;

public class PasswordChangedEventData extends EventData {
    public static final String EVENT_CODE = "PASSWORD_CHANGED";

    @Override
    public String getEventCode() {
        return EVENT_CODE;
    }
    private String newPassword;
    private String oldPassword;
    private String userId;

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public PasswordChangedEventData(String newPassword, String oldPassword, String userId) {
        this.newPassword = newPassword;
        this.oldPassword = oldPassword;
        this.userId = userId;
    }
}
```

**从应用服务发布事件TestAppservices**
```java
package com.zhaofujun.nest.ioc.test.appservices;

import com.zhaofujun.nest.core.EventBus;
import com.zhaofujun.nest.ioc.annotation.AppService;
import com.zhaofujun.nest.ioc.annotation.Autowired;
import com.zhaofujun.nest.ioc.test.models.PasswordChangedEventData;
import com.zhaofujun.nest.ioc.test.models.User;
import com.zhaofujun.nest.context.model.StringIdentifier;
import com.zhaofujun.nest.context.loader.ConstructEntityLoader;
import com.zhaofujun.nest.core.EntityLoader;
import com.zhaofujun.nest.context.loader.RepositoryEntityLoader;

@AppService
public class TestAppservices {

    @Autowired
    EventBus eventBus;


    public void changPwd(String userName, String newPwd) {
        EntityLoader<User> entityLoader = new RepositoryEntityLoader<>(User.class);
        User user = entityLoader.create(StringIdentifier.valueOf(userName));
        user.changPwd(newPwd);
//        EventBus eventBus = new EventBus(beanFinder);

        PasswordChangedEventData eventObject = new PasswordChangedEventData("newpwd", "oldpwd", "111");

        eventBus.publish(eventObject);
    }


    public void createUser(String usrName, String pwd) {
        EntityLoader<User> entityLoader = new ConstructEntityLoader<>(User.class);
        User user = entityLoader.create(StringIdentifier.valueOf(usrName));
        user.changPwd(pwd);

//        EventBus eventBus = new EventBus(beanFinder);

        PasswordChangedEventData eventObject = new PasswordChangedEventData("newpwd", "oldpwd", "111");

        eventBus.publish(eventObject);
    }
}


```

**事件处理器PwdChangedEventHandler**

```java
package com.zhaofujun.nest.ioc.test;

import com.zhaofujun.nest.context.event.EventArgs;
import com.zhaofujun.nest.core.EventHandler;
import com.zhaofujun.nest.ioc.annotation.Component;
import com.zhaofujun.nest.ioc.test.models.PasswordChangedEventData;

@Component("PASSWORD_CHANGED")
public class PwdChangedEventHandler implements EventHandler<PasswordChangedEventData> {
    public static final String EVENT_CODE = "PASSWORD_CHANGED";

    @Override
    public String getEventCode() {
        return EVENT_CODE;
    }

    @Override
    public Class<PasswordChangedEventData> getEventDataClass() {
        return PasswordChangedEventData.class;
    }

    @Override
    public void handle(PasswordChangedEventData eventData, EventArgs eventArgs) {
        System.out.println(eventData.toString());
    }
}

```


### 应用及服务全生命周期

一个系统称为一个应用，每一个用例对应的请求都将启动一个服务上下文。

Nest可以通过监听器`com.zhaofujun.nest.event.ApplicationListener`和`com.zhaofujun.nest.event.ServiceContextListener`来监听应用与服务的事件。


**`ApplicationListener`定义如下**

```java
package com.zhaofujun.nest.event;

import java.util.EventListener;

public interface ApplicationListener extends EventListener {

    void applicationStarted(ApplicationEvent applicationEvent);

    void applicationClosed(ApplicationEvent applicationEvent);
}
```

**`ServiceContextListener`定义如下

```java

public interface ServiceContextListener extends EventListener {
    void serviceCreated(ServiceEvent serviceEvent);

    void serviceMethodStart(ServiceEvent serviceEvent, Method method);

    void serviceMethodEnd(ServiceEvent serviceEvent, Method method);

    void beforeCommit(ServiceEvent serviceEvent);
    void committed(ServiceEvent serviceEvent);

    void serviceEnd(ServiceEvent serviceEvent);
}

```

## Nest最佳实践

推荐使用nest-plus来扩展nest

[点击进入](https://github.com/jovezhao/nest-plus)

# 常见问题汇总
