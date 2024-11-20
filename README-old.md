# 概述

## 简介

Nest是一套帮助开发人员基于领域驱动设计（DDD）开发系统的一套技术框架。他定义了领域驱动设计中新工具（实体、值对象、工厂、仓储、应用服务、事件），并且通过服务上下文、工作单元、缓存等模块将整个体系串联。

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
    <version>3.0</version>
</dependency>
```
**Gradle引用**
```groovy
// https://mvnrepository.com/artifact/com.zhaofujun.nest/nest-ddd
compile group: 'com.zhaofujun.nest', name: 'nest-ddd', version: '2.2.10'

```

## 快速上手演练-使用nest创建可运行的项目

本案例使用nest-ddd快速创建基于DDD的项目，并且完成创建用户的演示操作，源码参考nest-ddd项目test目录。

项目由领域模型、应用服务和应用程序组成。

**领域模型代码：**
```java
package com.zhaofujun.nest.test.domain;

import com.zhaofujun.nest.context.model.BaseEntity;
import com.zhaofujun.nest.context.model.LongIdentifier;

public abstract class Teacher extends BaseEntity<LongIdentifier> {
    private String name;

    public void init(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}


```
**应用服务代码：**
```java
package com.zhaofujun.nest.test.application;

import com.zhaofujun.nest.context.model.EntityFactory;
import com.zhaofujun.nest.context.model.LongIdentifier;
import com.zhaofujun.nest.standard.AppService;
import com.zhaofujun.nest.test.domain.Teacher;

@AppService
public class TeacherApplicationService {

    public void teachCreate() {
        Teacher teacher = EntityFactory.create(Teacher.class, LongIdentifier.valueOf(11L));
        teacher.init("teacher 1");
    }
}

```

**应用程序代码**

```java
package com.zhaofujun.nest.test;

import com.zhaofujun.nest.NestApplication;
import com.zhaofujun.nest.context.appservice.ApplicationServiceCreator;
import com.zhaofujun.nest.test.application.TeacherApplicationService;


public class Application {
    public static void main(String[] args)   {


        NestApplication application = NestApplication.current();
        application.start();

        TeacherApplicationService teacherApplicationService=ApplicationServiceCreator.create(TeacherApplicationService.class);
        teacherApplicationService.teachCreate();

        application.close();
    }
}

```


在本案例中，我们只是简单的对演示了如何对领域模型User的创建过程。
没有做任何仓储的实现，框架会提供基于内存的默认仓库来实现，这样可以帮助程序员快速完成业务部分的代码开发与测试，默认仓库使用内存仓储实体的json结构。

## Nest进阶教程

### 应用服务

使用`@AppService`注解标识一个应用服务类，标注过该注解的类的公共方法将开启服务上下文，上下文内所有实体的变化将通过工作单元记录并校验后调用实体仓储完成持久化。

可以使用`@AppServiceIgnore`注解忽略应用服务下的公共方法使其不开启服务上下文。


### 扩展

Nest本身具有丰富的可扩展性，并且扩展起来也相当简单。

#### 继承至CacheProvider的缓存提供者

CacheProvider定义了一个缓存的提供者，nest-plus集成了redis。如果用户需要集成其它缓存提供者只需要实现CacheProvider接口并且添加到配置中。

使用`NestApplication.getProviderManage().addProvider(Provider... providers)` 可以注册新的缓存提供者。

可以使用IOC定义一个继承至CacheProvider的Bean，系统将自动集成该类型的缓存提供方式。

#### 继承至MessageConverter的消息转换器

MessageConverter定义了MessageInfo与字符串的互相转换过程，用户可以通过MessageConverter来实现自定义转换的过程。

使用`NestApplication.getProviderManage().addProvider(Provider... providers)` 可以注册新的消息转换器。

完成自定义的MessageConverter后还需要修改配置项`NestApplication.getMessageConfiguration().setConverter(String code)`使其生效。


#### 继承至MessageResendStore的消息暂存器

MessageResendStore定义了投递失败的消息存储方式，默认投递失败的消息使用内存空间暂存，为了保证可靠性，一般情况需要使用外部存储空间来保存投递失败的消息。

使用`NestApplication.getProviderManage().addProvider(Provider... providers)` 可以注册新的消息暂存器。

完成自定义的MessageResendStore后还需要修改配置项`NestApplication.getMessageConfiguration().setResendStore(String code)`使其生效。

#### 继承至MessageStore的消息存储器

MessageStore定义消费成功的消息的存储方式，用于处理消费者的幂等性，使消息不被重复消费。默认存储方式是使用的内存空间，为了保证可靠性，一般情况需要使用外部存储空间来保存已经消费的消息。

使用`NestApplication.getProviderManage().addProvider(Provider... providers)` 可以注册新的消息存储器。

完成自定义的MessageStore后还需要修改配置项`NestApplication.getMessageConfiguration().setStore(String code)`使其生效。

#### 继承至DelayMessageStore的消息存储器

DelayMessageStore定义延迟消息的存储方式,默认存储方式是使用的内存空间，为了保证可靠性，一般情况需要使用外部存储空间来保存已经消费的消息。

使用`NestApplication.getProviderManage().addProvider(Provider... providers)` 可以注册新的消息存储器。

完成自定义的DelayMessageStore后还需要修改配置项`NestApplication.getMessageConfiguration().setDelayStore(String code)`使其生效。

#### 继承至LockProvider的锁提供者

LockProvider定义锁的实现方式,默认实现使用ReentrantLock实现，在分布式环境下推进使用分布式锁，nest-plus提供了基于redis的分布式锁实现。

使用`NestApplication.getProviderManage().addProvider(Provider... providers)` 可以注册新的锁提供者。

完成自定义的DelayMessageStore后还需要修改配置项`NestApplication.getLockConfiguration().setProvider(String code)`使其生效。

#### 继承至MessageChannelProvider的消息通道提供者

MessageChannelProvider定义了消息通道的实现方式，默认的消息通道是命名用的本地内存的实现方式，不支持分布式，在分布式系统中一般消息通道都需要使用第三方消息中间件来传递消息。

使用`NestApplication.getProviderManage().addProvider(Provider... providers)` 可以注册新的消息通道提供者。

nest-plus实现了常用消息中间件集成方式，包括ActiveMQ、RocketMQ、RabbitMQ，用户也可以自己实现相应的消息通道，并且配置通道的参数。

#### 继承至ApplicationListener的应用监听器

ApplicationListener 定义了应用程序的监听器，当应用程序启动或者停止时将回调该监听器相关方法。

实现该应用监听器接口后需要使用`NestApplication.getListenerManager().addListeners(NestEventListener ... eventListeners)`注册监听器，也可以将ApplicationListener注册到IOC容器中，由IOC容器自动注册。

#### 继承至ServiceContextListener的服务上下文监听器

ServiceContextListener 定义了服务调用的过程，包括服务创建、服务方法开始、服务方法结束、开始提交工作单元、完成工作单元提交、服务结束等回调过程。

实现该应用监听器接口后需要使用`NestApplication.getListenerManager().addListeners(NestEventListener ... eventListeners)`注册监听器，也可以将ServiceContextListener注册到IOC容器中，由IOC容器自动注册。


### 实现spring的集成
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

开发人员可以通过`NestApplication.getConfigurationManager().addConfigurationItem(ConfigurationItem... configurationItems)`方法手动注册一组缓存配置，也可以将`CacheConfiguration`的bean配置到ioc容器中实现自动注册。

`CacheConfiguration` 可以配置缓存组的代号、名称、使用的缓存提供者和统一的过期时间。

如果配置的缓存提供者无法找到，系统将使用默认的缓存提供者。

默认缓存提供者使用ehcache支持，我们也可以通过实现`com.zhaofujun.nest.cache.CacheProvider`接口来集成其它缓存中间件，比如`Redis`。

> 缓存中间件集成方案见： [缓存通道扩展与集成](#缓存通道扩展与集成)

### 锁实现

开发过程中往往需要使用锁解决多线程下资源冲突问题，nest提供了简单的锁工具可以简化开发过程。
调用方式：`LockUtils.runByLock(String key, Runnable runnable)`，配合`LockProvider`可以实现分布式锁的实现。

### 标识生成器

可以实现`IdentifierGenerator`接口定义标识生成方式，nest提供了本地自增，雪花算法生产Long型的实现。参考`LocalLongGenerator`和`SnowflakeLongGenerator`类的实现。

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
限界上下文 | 应用程序
应用服务 | AppService
实体 | Entity
实体标识 | Identifier
值对象 | ValueObject
实体工厂 | EntityLoader
领域事件 | EventBus
仓储 | Repository

**定义一个实体**

要定义一个实体，只需要将该类继承`com.zhaofujun.nest.context.model.BaseEntity<T extends AbstractIdentifier>`。

实体的标识可以按要求自定义。系统还提供了`com.zhaofujun.nest.context.model.UUIdentifier`、`com.zhaofujun.nest.context.model.LongIdentifier`、`com.zhaofujun.nest.context.model.StringIdentifier`给开发人员选择.

开发人员也可以继承`com.zhaofujun.nest.context.model.AbstractIdentifier`实现自定义的实体标识。

**为实体实现仓储**

实体是需要关注基生命周期的，可以通过实现`com.zhaofujun.nest.standard.Repository<T extends Entity>`完成实体的持久化处理。该接口定义了`insert`、`update`、`delete`方法用于对数据库的操作。同时还定义了`batchInsert`、`batchUpdate`、`batchDelete`方法用于批量处理，批量处理方法都提供了默认的实现，如果需要批量处理的数据量较大，建议使用数据库的batch方式提交。

自定义的仓储实现需要使用`NestApplication.getRepositoryManager().addRepository(Repository... repositories)`方法向系统注册仓储，也可以将仓储注册到IOC从容器，由IOC容器自动注册。


**如何加载或创建实体**

可以使用实体工厂`com.zhaofujun.nest.context.model.EntityFactory`来加载或创建一个实体。

`load`方法将通过仓储来加载，在仓储加载之前优先使用当前工作单元中的实体，如果当前工作单元中找不到，就会去缓存加载，如果缓存也没有才会使用仓储在数据库中去加载。

`create`方法将创建一个全新的实体，建议按实体的标识建立数据库唯一索引，可以有效利用数据库的一些额外能力，比如提升查询性能、处理重复数据等。


### 事件总线

当系统需要接受外部的异步消息或发布事件时，可以通过事件总线`com.zhaofujun.nest.standard.EventBus`来处理。 

事件总线的定义如下：
```java
package com.zhaofujun.nest.core;


import com.zhaofujun.nest.context.event.EventData;


public interface EventBus{
    void registerHandler(EventHandler eventHandler);
    void publish(EventData eventData);
}

```
**事件订阅**

通过`registerHandler`手动注册一个EventHandler。当然在IOC容器下，也可以将EventHandler注册到IOC容器中，由IOC容器自动注册。

**事件发布**

`publish`方法发布一个事件，发布一个事件需要实现`com.zhaofujun.nest.core.EventData`抽象类做为事件发布的内容。 其中`getEventCode`抽象方法定义当前事件代号。

事件的发布应该在应用服务层发起，事件提交并不是立刻进行，而是与实体的提交一起进行，如果实体提交失败，事件将不会提交，如果实体提交成功而事件提交失败时，事件消息将通过补偿机制重新发起以达到最终一致性。

**事件管道配置**

事件管道配置由`com.zhaofujun.nest.context.event.EventConfiguration`定义，事件代号对应EventData中的Code，管道对应于`MessageChannelProvider`的实现。表示当前事件使用才能方式发出。

使用`NestApplication.getConfigurationManager().addConfigurationItem(ConfigurationItem... configurationItems)`向应用程序注册新的配置项。

可在IOC容器下，可以使用IOC定义一个EventConfiguration的Bean，系统将自动注册该配置。

**监听实体变更**

如果实体实现了`EntityNotify`接口，当该实体向仓储提交数据时将自动发起事件`EntityNotifyEventData`事件，该事件可以根据项目需求配置到指定的消息通道。

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

import com.zhaofujun.nest.standard.EventBus;
import com.zhaofujun.nest.context.model.StringIdentifier;
import com.zhaofujun.nest.context.loader.ConstructEntityLoader;
import com.zhaofujun.nest.standard.EntityLoader;
import com.zhaofujun.nest.context.loader.RepositoryEntityLoader;

@AppService
public class TestAppservices {

    @Autowired
    EventBus eventBus;


    public void changPwd(String userName, String newPwd) {
        EntityLoader<User> entityLoader = new RepositoryEntityLoader<>(User.class);
        User user = entityLoader.create(StringIdentifier.valueOf(userName));
        user.changPwd(newPwd);

        PasswordChangedEventData eventObject = new PasswordChangedEventData("newpwd", "oldpwd", "111");

        eventBus.publish(eventObject);
    }


    public void createUser(String usrName, String pwd) {
        EntityLoader<User> entityLoader = new ConstructEntityLoader<>(User.class);
        User user = entityLoader.create(StringIdentifier.valueOf(usrName));
        user.changPwd(pwd);

        PasswordChangedEventData eventObject = new PasswordChangedEventData("newpwd", "oldpwd", "111");

        eventBus.publish(eventObject);
    }
}


```

**事件处理器PwdChangedEventHandler**

```java
package com.zhaofujun.nest.ioc.test;

import com.zhaofujun.nest.standard.EventArgs;
import com.zhaofujun.nest.standard.EventHandler;
import com.zhaofujun.nest.ioc.test.models.PasswordChangedEventData;

@Component
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

**`ServiceContextListener`定义如下**

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
