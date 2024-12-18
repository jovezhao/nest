# 简介

Nest 框架是一个帮助开发人员快速实现基于领域驱动设计的技术框架。在 Nest 中定义了领域驱动设计的基本概念，包括聚合根、实体、值对、领域服务、应用服务、服务事件、仓储等，使用 Nest 可以帮助你全面执行你在领域模型建模中的面向对象思维。有效指导开发人员按模型编写代码，为代码检查等提供必要的依据。

## 目录

* [领域驱动设计](#领域驱动设计)
* [Nest快速入门](#Nest快速入门)
* [领域建模与实现](#领域建模与实现)
* [仓储与查询](#仓储与查询)
* [应用服务与事件驱动](#应用服务与事件驱动)
* [NestEngine使用说明](#NestEngine使用说明)
* [分层架构](#分层架构)
* [CQRS](#CQRS)
* [异常处理](#异常处理)
* [单元测试](#单元测试)
* [生命周期管理](#生命周期管理)
* [缓存管理](#缓存管理)
* [事件管理](#事件管理)
* [Nest扩展](#Nest扩展)
* [更多工具](#更多工具)
* [锁管理](#锁管理)
* [Json序列化](#Json序列化)


# 领域驱动设计

## 什么是领域驱动设计
领域驱动设计（Domain-Driven Design，DDD）是一种软件开发方法，旨在将软件项目的核心关注点放在业务领域上，通过对业务领域的深入理解和建模，来指导软件的设计和开发，从而使软件系统能够更好地满足业务需求，并具有更好的可维护性、可扩展性和可理解性。

领域驱动设计通过聚焦业务领域、建立清晰的模型和架构，以及促进团队协作等方式，为软件开发带来了诸多好处，能够有效地提高软件系统的质量和适应性，助力企业更好地实现业务目标和战略发展。

## 领域驱动设计与传统开发方式对比

### 领域驱动设计优缺点

**优点：**

- **紧密围绕业务领域建模**：DDD 强调以业务领域为核心进行软件设计和开发，使得软件模型能够更准确地反映业务逻辑和规则，提高了软件与业务的契合度。例如，在一个电商系统中，通过领域驱动设计，可以清晰地构建出订单、商品、用户等领域模型，以及它们之间的复杂业务关系，从而更好地满足电商业务的多样化需求。
- **增强代码的可维护性和可扩展性**：由于领域模型的清晰划分和明确职责，使得代码结构更加模块化，易于理解和维护。当业务需求发生变化时，开发人员可以更准确地定位到需要修改的代码位置，降低了修改的难度和风险。比如，当电商系统需要增加新的商品类型或促销规则时，基于领域驱动设计的代码结构能够更方便地进行扩展和修改，而不会对其他无关的模块产生过多的影响。
- **提升团队沟通效率**：DDD 提供了一套通用的语言和概念体系，使得业务人员、领域专家和开发人员能够在同一个层面上进行有效的沟通和交流。大家可以使用统一的领域术语来描述业务需求和软件功能，减少了因沟通不畅而导致的误解和错误。例如，在讨论电商系统的订单处理流程时，业务人员和开发人员都能够基于 “订单”“支付”“发货” 等领域概念进行清晰的沟通，提高了项目的推进效率。

**缺点：**

- **学习曲线较陡峭**：领域驱动设计涉及到许多新的概念、原则和方法，如领域模型、限界上下文、聚合根等，对于开发团队来说，需要花费一定的时间和精力去学习和理解这些知识，才能熟练掌握和运用 DDD 进行开发。这可能会在项目初期增加一定的学习成本和时间成本。
- **设计和开发复杂度相对较高**：与传统的面向数据库开发相比，DDD 需要更加深入地分析和理解业务领域，进行更细致的领域建模和设计，这无疑增加了设计和开发的复杂度。在实际项目中，可能需要更多的时间和精力来进行领域模型的构建、验证和优化，以确保其准确性和有效性。
- **对团队协作要求较高**：DDD 强调团队成员之间的紧密协作和沟通，需要业务人员、领域专家和开发人员等多角色的深度参与和配合。如果团队协作不够顺畅，或者成员之间对领域知识的理解存在偏差，可能会导致项目出现问题。例如，业务人员未能准确地传达业务需求，或者开发人员对领域模型的理解不够深入，都可能影响软件的最终质量和交付时间。

### 传统面向数据库设计优缺点

**优点：**

- **简单直接**：对于一些简单的业务场景，传统面向数据库开发的方式较为直接和易于理解。开发人员可以快速地根据业务需求设计数据库表结构，然后通过编写 SQL 语句和简单的代码逻辑来实现业务功能。这种方式在小型项目或业务逻辑不太复杂的情况下，能够快速地完成开发任务，节省时间和成本。
- **技术门槛较低**：相对于领域驱动设计，传统面向数据库开发所涉及的技术和概念相对较为基础和常见，如数据库操作、编程语言的基本语法等，开发人员更容易上手和掌握。对于初学者或技术能力较弱的团队来说，这种开发方式更容易实现和维护。
- **数据一致性保障较好**：由于传统面向数据库开发主要以数据库为核心，通过数据库的事务机制和约束条件等，可以较好地保证数据的一致性和完整性。在数据操作较为频繁和复杂的系统中，这一点尤为重要，可以有效地避免数据不一致的问题，确保系统的稳定运行。

**缺点：**

- **业务与技术耦合度高**：传统面向数据库开发往往将业务逻辑和数据库操作紧密地耦合在一起，导致代码的可维护性和可扩展性较差。当业务需求发生变化时，可能需要对大量的 SQL 语句和相关的代码逻辑进行修改，容易引发错误和漏洞，增加了维护成本和风险。
- **难以应对复杂业务场景**：随着业务的不断发展和复杂度的增加，传统面向数据库开发的局限性逐渐显现。由于缺乏对业务领域的深入建模和抽象，难以有效地组织和管理复杂的业务逻辑，容易导致代码混乱、难以理解和维护。在处理复杂的业务流程、多业务模块之间的交互等问题时，传统开发方式往往显得力不从心。
- **不利于团队沟通和协作**：在传统面向数据库开发中，开发人员主要关注数据库表结构和 SQL 语句的编写，与业务人员之间的沟通往往存在一定的障碍。业务人员可能难以理解技术细节，而开发人员也可能对业务需求的理解不够深入，从而影响项目的顺利进行和软件质量。

### 一点点建议
如果你面向对项目业务领域复杂、业务需求变更频繁、多团队协作开发、需要长期维护和演进的系统时，我强烈建议你采用领域驱动设计方法，领域驱动设计能够更好的帮助理解和梳理业务，有效应对频繁变更的业务需求，明确多个团队间的业务边界，特别适合指导微服务中的服务划分问题，帮助你建立高可维护性与可扩展性的系统奠定基础。

如果你只需要处理简单的业务场景、需求明确且时间紧迫的短期项目，使用领域驱动设计给你带来的效益将大打折扣，甚至还会带来额外的成本。


## 领域驱动设计的概念

在DDD中，有几个重要概念，它们分别是实体、聚合根、实体标识、值对象、仓储、工厂、限界上下文、应用服务、领域服务、事件、域&子域等。

其中域&子域是战略设计的重要成果，用来表达一个问题空间。一个子域可能会有一个或多个限界上下文组成，共同协调解决问题空间中的问题域。

限界上下文常常可以定义为一个可以独立部署的应用程序，对应于问题空间，限界上下文代表了一个问题空间的解决方案。

将限界上下文当成一个独立的黑盒系统，这个限界上下文所具备的能力由应用服务与事件来表达,可使用用例来表达。这也是多个限界上下文之间交互的重要手段。

而限界上下文的核心能力都是由内部的领域模型提供，领域模型是基于当前问题空间进行战术设计的产物。所以对领域模型的建模便是战术设计的工作核心。

领域模型由实体、实体标识、值对象、聚合根、领域服务组成。

而仓储、工厂分别是对领域模型的生命周期而服务，仓储负责对模型的持久化与重新加载，工厂封装了实体被创建的一系列细节。




#  Nest快速入门
## 获取 Nest
Nest已经通过Maven中央仓库托管，你可以在项目中直接引用依赖。
**Maven引用**
```xml
<!-- https://mvnrepository.com/artifact/com.zhaofujun.nest/nest-ddd -->
<dependency>
    <groupId>com.zhaofujun.nest</groupId>
    <artifactId>nest-ddd</artifactId>
    <version>3.0.0</version>
</dependency>
```
**Gradle引用**
```groovy
// https://mvnrepository.com/artifact/com.zhaofujun.nest/nest-ddd
compile group: 'com.zhaofujun.nest', name: 'nest-ddd', version: '3.0.0'

```

## 快速上手演练-使用nest创建可运行的项目

本案例使用nest-ddd快速创建基于DDD的项目，并且完成创建用户，发送创建用户成功的事件，并且自己作为消费者处理事件。

项目由领域模型、应用服务和应用程序、事件处理器组成。

**创建用户领域模型**
```java
package com.zhaofujun.nest.test;

import com.zhaofujun.nest.ddd.AggregateRoot;
import com.zhaofujun.nest.ddd.StringIdentifier;

public class User extends AggregateRoot<StringIdentifier> {
    public User(StringIdentifier identifier) {
        super(identifier);
    }

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User [name=" + name + ", id=" + id.toValue() + "]"+this.hashCode();
    }

    
}
```
`User`类从`AggregateRoot`聚合根继承，并且指定了`User`类使用`String`类型的标识为唯一标识。

**创建应用服务**

应用服务包括一个服务类以及一个DTO类。

```java
package com.zhaofujun.nest.test;

public class UserDto {
    private String id;
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
```

```java
package com.zhaofujun.nest.test;

import com.zhaofujun.nest.ddd.ApplicationService;
import com.zhaofujun.nest.ddd.StringIdentifier;
import com.zhaofujun.nest.utils.EventUtil;

public class DefaultUserAppService implements ApplicationService {
    public UserDto create() {
        User user = new User(new StringIdentifier("111"));
        user.setName("name");

        UserDto userDto = new UserDto();
        userDto.setId(user.getId().toValue());
        userDto.setName(user.getName());
        EventUtil.publish("user_created", userDto，100);

        return userDto;
    }
}
```
应用服务需要继承至`ApplicationService`类，在应用服务内创建用户模型、将用户模型转换为 DTO，并且通过`EventUtil`发布用户创建完成的事件

**创建应用**
应用可以是 web，也可以是命令行工具等。 本例使用 junit
```java 
package com.zhaofujun.nest.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.zhaofujun.nest.NestEngine;
import com.zhaofujun.nest.ddd.event.EventAppService;
import com.zhaofujun.nest.inner.DefaultEventInfoRepository;
import com.zhaofujun.nest.utils.AppServiceUtil;

public class UserTest {

    @BeforeAll
    public void initEngine() throws Throwable {
        NestEngine nestEngine = new NestEngine();

        EventAppService eventAppService = AppServiceUtil.create(EventAppService.class);
        eventAppService.setQuery(new DefaultEventInfoRepository());

        nestEngine.setEventAppService(eventAppService);

        // 注册事件处理器
        nestEngine.registerEventHandler(new UserCreatedHandler());

        // 启动引擎
        nestEngine.start();

    }

    @Test
    public void createUser() throws Throwable {

        // 创建化用户应用服务
        DefaultUserAppService userAppService = AppServiceUtil.create(DefaultUserAppService.class);
        UserDto userDto = userAppService.create("1111", "Li lei");

        assertEquals("111", userDto.getId());
        assertEquals("Li lei", userDto.getName());

    }

}
```
应用服务的创建不能直接使用使用`new`关键字，必须使用`AppServiceUtil.create`方法创建，因为`Nest`框架将使用`AOP`处理方法切面。

```java

package com.zhaofujun.nest.test;

import com.zhaofujun.nest.ddd.EventHandler;

public class UserCreatedHandler implements EventHandler<UserDto> {

    @Override
    public String getEventName() {
        return "user_created";
    }

    @Override
    public Class<UserDto> getEventDataClass() {
        return UserDto.class;
    }

    @Override
    public void handle(UserDto eventData) {
        System.out.println("接收到用户创建成功的事件：" + eventData.toString());
    }

}

```
在本例中没有创建用户模型的仓储实现，Nest 将使用默认的仓储实现，默认仓储使用本地缓存存储数据，可用于单元测试或集成测试，请勿用于生产环境。

## 使用Springboot

本例将使用Springboot3.0创建入门应用，案例创建通过仓储加载一个`User`模型，修改name属性后发布事件。并且使用两个事务处理器处理事件。

**添加`nest-spring-boot-starter`依赖**

Nest已经通过Maven中央仓库托管，你可以在项目中直接引用依赖。
**Maven引用**
```xml
<!-- https://mvnrepository.com/artifact/com.zhaofujun.nest/nest-ddd -->
<dependency>
    <groupId>com.zhaofujun.nest</groupId>
    <artifactId>nest-spring-boot-starter</artifactId>
    <version>3.0.0</version>
</dependency>
```
**Gradle引用**
```groovy
// https://mvnrepository.com/artifact/com.zhaofujun.nest/nest-ddd
compile group: 'com.zhaofujun.nest', name: 'nest-spring-boot-starter', version: '3.0.0'

```
**创建`User`模型**

```java
package com.zhaofujun.nest.demo.appservices.model;

import com.zhaofujun.nest.ddd.AggregateRoot;
import com.zhaofujun.nest.ddd.LongIdentifier;

public class User extends AggregateRoot<LongIdentifier>  {
    private String name;
    private long age;

    public User(LongIdentifier longIdentifier){
        super(longIdentifier);
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public long getAge() {
        return age;
    }
    public void setAge(long age) {
        this.age = age;
    }

}
```
创建模型方式与之前一样，需继承至`AggregateRoot`

**创建应用服务**

```java
package com.zhaofujun.nest.demo.appservices;

public class UserDto {
    private String id;
    private String name;
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    
}

```

```java
package com.zhaofujun.nest.demo.appservices;

import com.zhaofujun.nest.boot.AppService;
import com.zhaofujun.nest.ddd.LongIdentifier;
import com.zhaofujun.nest.demo.appservices.model.User;
import com.zhaofujun.nest.utils.EntityUtil;
import com.zhaofujun.nest.utils.EventUtil;

@AppService
public class UserAppservice {
    
    public UserDto changeName(long id) {
        User user = EntityUtil.load(User.class, new LongIdentifier(id));
        user.setName("name1");

        var result = new UserDto();
        result.setId(user.getId().toValue());
        result.setName(user.getName());

        EventUtil.publish("user_name_changed", result);

        return result;
    }
}
```
在Spring环境中有两种方式标识应用服务，可使用`@AppService`注解或通过继承`ApplicationService`接口。使用后者需要为应用服务类注解`@Component`使其被Spring容器管理，而使用前者将自动被注册到Spring容器。

另外获取应用服务实例时使用Spring自动注入即可，不再需要`AppServiceUtil.create`方法创建。

**创建User仓储**

```java
package com.zhaofujun.nest.demo.repositories;

import java.lang.reflect.Type;

import org.springframework.stereotype.Component;

import com.zhaofujun.nest.ddd.Identifier;
import com.zhaofujun.nest.ddd.LongIdentifier;
import com.zhaofujun.nest.ddd.Repository;
import com.zhaofujun.nest.demo.appservices.model.User;

@Component
public class UserRepository implements Repository<User> {

    @Override
    public void insert(User t) {
        System.out.println("新增 User，可以使用 DAO 方式操作数据库");
    }

    @Override
    public void update(User t) {
        System.out.println("修改 User，可以使用 DAO 方式操作数据库");

    }

    @Override
    public void delete(User t) {

    }

    @Override
    public Type getEntityType() {
        return User.class;
    }

    @Override
    public User getEntityById(Class<? extends User> tClass, Identifier identifier) {
        System.out.println("直接创建一个 User 类，模拟数据库查询");

        User user = new User((LongIdentifier) identifier);
        user.setName("test");
        user.setAge(10);
        return user;

    }

}
```
仓储的定义需要继承至`Repository`接口并实现`insert`,`update`,`delete`,`getEntityType` 和`getEntityById`方法。

在Spring环境中需要为仓储类添加`@Component`注解以便容器管理。

**创建RestController**

```java
package com.zhaofujun.nest.demo.webapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zhaofujun.nest.demo.appservices.UserAppservice;
import com.zhaofujun.nest.demo.appservices.UserDto;

@RestController
public class UserController {
    @Autowired
    private UserAppservice userAppservice;

    @PostMapping("/user/{id}")
    public UserDto changeName(@PathVariable Long id) {
        return userAppservice.changeName(id);
    }
}
```
**创建事务处理器**
本案例使用两种方式处理事件，分别使用`EventHandler`接口方式和`@EventListener`注解方式。

```java
package com.zhaofujun.nest.demo.handlers;

import org.springframework.stereotype.Component;

import com.zhaofujun.nest.ddd.EventHandler;
import com.zhaofujun.nest.demo.appservices.UserDto;
@Component
public class UserEventHandler implements EventHandler<UserDto> {

    @Override
    public String getEventName() {
        return "user_name_changed";
    }

    @Override
    public Class<UserDto> getEventDataClass() {
        return UserDto.class;
    }

    @Override
    public void handle(UserDto eventData) {
        System.out.println("event handler");
    }

}
```

```java
package com.zhaofujun.nest.demo.handlers;

import org.springframework.stereotype.Component;
import com.zhaofujun.nest.boot.EventListener;
import com.zhaofujun.nest.demo.appservices.UserDto;

@Component
public class UserEventHandlerListener {
    @EventListener(eventDataClass = UserDto.class, eventName = "user_name_changed")
    public void userCreate(UserDto userDto) {
        System.out.println("event listener");
    }
}

```

**启动Springboot应用程序**

```java
package com.zhaofujun.nest.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
```

在使用了SpringBoot后，我们将不再关注 NestEngine 的初始化过程，`nest-spring-boot-starter`将自动初始化NestEngine。也不需要手动注册事件处理器，只需要将处理器放入Spring容器中管理即可

# 领域建模与实现
领域建模是战术设计的工作核心，为了让大家更好完成领域建模，这里将对实体、值对象、聚合根、领域服务进行简单的介绍。

## 实体

领域实体是领域模型中的核心概念，它是从通用语言中提炼出来的名词，它代表了业务领域中具有唯一标识且生命周期较长的事物。领域实体通常具有自己的属性和行为，并且其状态可以随着时间和业务操作而发生变化。

**特性:**

- **唯一标识** 可以通过这个唯一标识来引用这个实体
- **可变性** 实体状态(实体的各类属性)可以随着时间和业务操作而发生变化
- **行为和业务逻辑** 领域实体通常包含与自身相关的业务逻辑和行为方法。这些方法用于操作实体的属性，执行与实体相关的业务操作

## 值对象

与实体相似的另一个概念是值对象，它也是从通用语言中提炼出来的名词。区别于实体，它没有唯一标识，其主要目的是为了传递和表示一些不可变的值，所以值对象往往不会独立存在，它常常用来描述实体的一些特性。值对象从创建之后就不应该有状态变化，他对于实体而言应该是没有负作用的，如果需要对一个值对象进行修改，常常是创建一个新的值对象去替代原来的值对象。

**特点：**

- **不可变性** 值对象一旦创建，其内部的值就不能被修改。如果需要修改值对象的值，就需要创建一个新的值对象。这种不可变性使得值对象在多线程环境下更加安全，也更容易进行缓存和共享。
- **无唯一标识** 与领域实体不同，值对象不需要有唯一的标识符。因为值对象的主要作用是表示一个值，而不是代表一个具有独立生命周期的事物。例如，一个表示颜色的值对象 “红色”，它不需要有一个特定的标识来区分它与其他 “红色” 值对象。
- **值相等性** 对于值对象，判断两个值对象是否相等通常是通过比较它们内部的所有属性值是否相等来确定的。如果两个值对象的所有属性值都相等，那么它们就被认为是相等的值对象。

## 聚合根

将一系列关系密切的实体、值对象联系在一起，组成的一个比较大的实体,我们将其称为一个聚合。而能够访问聚合内实体的根实体我们将之称为聚合根，聚合根负责维护聚合内其他实体和值对象的一致性.

**特点：**

- **聚合边界** 聚合根定义了一个聚合的范围，聚合内的所有实体和值对象都与聚合根存在着紧密的关联关系。聚合根将相关的领域对象组合在一起，形成一个高内聚的业务单元。例如，在一个订单聚合中，订单实体作为聚合根，它包含了订单项实体、收货地址值对象等，这些对象共同构成了一个完整的订单业务概念。
- **一致性维护** 聚合根负责确保聚合内对象的一致性。当对聚合内的任何对象进行操作时，都必须通过聚合根来进行，以保证操作的原子性和一致性。比如在修改订单的某个订单项时，必须通过订单聚合根来执行相应的操作，以确保订单的整体状态和数据的一致性。
- **数据持久化** 在数据持久化方面，聚合根通常是作为一个整体进行存储和检索的。也就是说，当将聚合持久化到数据库时，通常是将整个聚合根及其包含的所有实体和值对象一起存储到数据库中，以保证聚合内数据的完整性和一致性。

> tips1: 一个聚合内可能只有一个聚合根，也可能有多个聚合根。聚合可以很小，小到只有一个实体，那么该实体本身也是一个聚合根。

> tips2: 聚合与实体的关系和实体与值对象的关系对比：
> - 聚合与实体间对应于UML中的聚合关系
> - 实体与值对象对应于UML中的组合关系
> - 实体可以独立于聚合存在，呈弱引用关系
> - 实体与值对象是组合关系，值对象跟随实体的生命周期，是包含关系

> tips3: 虽然聚合才关注持久化，但在实现开发设计中往往会给实体提供仓储功能，由聚合统一调配，这样可以减少对仓储的操作。为了加快聚合对象的创建过程，往往推荐大家设计更小的聚合。

## 领域服务

在通用语言中抽象出来的一些动作，这些动作往往需要协调多个聚合才能完成，而这样的行为放在其中任何一个聚合中都不那么和谐时，便可以用领域服务来处理。 但是我们不要太过于依赖领域服务，只有在没有办法的情况下才用领域服务，否则容易产生贫血模式。


## 领域建模原则

领域建模是DDD战术设计的重要内容。其中最基础的模型过程便是区分实体与值对象。

分析一个名词是否是实体时需要在当前业务场景中来分析，比如同样是商品信息，在商品上下文中，他是一个实体，我们可以对这个商品进行上下架操作。我们可以通过商品的编号来唯一标识该商品信息。而在订单上下文中，他就成了一个值对象，他只是用来描述订单信息的一个组成部分，在订单被创建之后，这些商品信息就不会再发生变化，即使原来的商品已经被删除，这里的商品信息也不会受到影响。因为值对象的存储方式是一份副本，而不是基于实体标识的引用。

领域模型应该包括聚合根、实体、值对象、领域服务。并且可以通过领域模型图完成业务演练与代码开发指导。

领域建模可以使用UML语言，为了区分这四类模型，可以采用四种不同的颜色来区分，称之为四色原型法。

## 使用nest-ddd定义模型

**`DomainObject`接口**

领域对象接口，用于标识一个类是不是领域对象，值对象、实体、聚合根、领域服务都是一种领域对象。

**`ValueObject`类**

值对象类，根据值对象的定义，重写了`equals`和`hashCode`方法，使其对比两个值对象值是否相同的方式不按引用，而是对比每个属性的值是否相同。为了不使用反射影响性能，定义为值对象的类继承`ValueObject`时需要实现`Object[] getPropertiesForComparison()`抽象方法。例如：
```java 
public class ConcreteValueObject extends ValueObject  {
    private String name;
    private int age;
    @Override
    protected Object[] getPropertiesForComparison() {
        return new Object[]{name, age};
    }
}
```
**`Identifier`类**

实体标识抽象类，用来代表实体的唯一标识，实体标识本身也是一个值对象。

- `toValue()`：使用字符串方式表达实体的唯一标识，在实现一个实体标识类时需要实现该方法

Nest框架内置了`StringIdentifier`、`LongIdentifier`和`UUIDIdentifier`，用户可以根据自己需要自定义`Identifier`，如下：

```java
package com.zhaofujun.nest.ddd.event;

import com.zhaofujun.nest.ddd.Identifier;

public class ProcessIdentifier extends Identifier {

    private String messageId;
    private String handlerId;

    public ProcessIdentifier(String messageId, String handlerId) {
        this.messageId = messageId;
        this.handlerId = handlerId;
    }

    public String getMessageId() {
        return messageId;
    }

    public String getHandlerId() {
        return handlerId;
    }

    @Override
    public String toValue() {
        return messageId + "-" + handlerId;
    }

}
```
`ProcessIdentifier`类使用了两个属性作为联合标识来唯一确定一个标识。

**`Entity`类**

定义一个领域对象是一个实体，`Entity`类是一个泛型类，需要指定一个`Identifier`的实现类来表示实体的唯一标识。

`Entity`类不同于`ValueObject`，在对比两个实体是否相等时，只对比其实体标识是否相同。

- `delete()`：删除一个实体
- `verify()`：验证实体是否满足定义的规则，如果验证失败，将抛出`VerifyFailedException`异常
- `getId()`：获取实体的唯一标识
- `getBeginSnapshot()`：获取实体在业务操作前的快照信息，快照以 json 格式表示
- `getEndSnapshot()`：获取实体在业务操作之后的快照信息，快照以 json 格式表示

**`AggregateRoot`类**

定义一个领域对象是一个聚合根，聚合根本质上是一个实体，具有实体所有的特性。区别于实体，聚合根可以被`EntityUtil.load()`方法加载，而实体不可以。

当一个聚合内只有一个实体时，请将该实体标识为一个聚合根。


# 仓储与查询 

在领域驱动设计（DDD）中，仓储（Repository）是一个用于管理领域对象持久化和检索的组件。它提供了一种统一的方式来访问数据存储（如数据库、文件系统等），使得领域层能够与底层的数据存储技术相分离，专注于业务逻辑的实现。

仓储位于数据访问层之上，数据访问层直接面向存储器而仓储使用数据访问层的能力将实体持久化到具体的存储器中。

## 使用nest-ddd实现仓储与查询

**`Repository`接口**

为实体定义仓储实现，Repository接口是一个泛型接口，根据要求需要指定一个`Entity`的实现类。

- `getEntityType()`：返回当前仓储能处理的实体类型
- `getEntityById()`：按实体标识找到具体的实体
- `insert()`：插入实体
- `update()`：更新实体
- `delete()`：删除实体
- `batchInsert()`：批量插入实体，当仓储实现该方法后，`insert()`方法将不再被应用。
- `batchUpdate()`：批量更新实现，当仓储实现该方法后，`update()`方法将不再被应用。
- `batchDelete()`：批量删除实体，当仓储实现该方法后，`delete()`方法将不再被应用。

当业务过程中发生了对实体的创建、修改或删除操作后，Nest将自动根据`getEntityType()`方法来匹配具体的仓储实现并执行对应的插入、更新或删除行为。开发人员不用关注仓储何时被调用。

当使用`getEntityType()`方法没有匹配到实体的仓储实现时，Nest会使用实体的父类去匹配仓储的实现，直到找到`Entity`类对应的默认实现`DefaultRepository`。这种使用父类的方式匹配仓储可以让我们对领域模型的单元测试时不用关心实体仓储的实现，而把精力放在领域模型的开发中。

如果你的业务中通过继承方式定义了多个实体，例如外卖订单、物流订单都通过订单基类扩展，我们可以为外卖订单和物流订单分别设计仓储，也可以只实现一个订单仓储，在仓储内同时处理多种不同的实现方式。

开发人员需要使用将仓储注册到


**`Query`接口**

为查询接口提供标识，该接口是一个空接口，不需要实现任何内容。

使用Query接口标识的查询仓储将被查询上下文托管，以保证在查询仓储中创建的实体对象能够被Nest统一管理与维护。

当业务系统需要进行查询操作时，需要先自定义一个查询接口并继承至`Query`接口。如下：
```java
package com.zhaofujun.nest.ddd.event;

import java.util.List;

import com.zhaofujun.nest.ddd.Query;

public interface EventMessageQuery extends Query {

    List<Long> getListToBeSent(int commonSize,int failSize, int maxFailTime);

}
```
查询接口的定义一般在应用服务层。如果查询内容将用于数据传输，推荐使用DTO对象，且勿直接返回领域对象，避免破坏或泄露领域对象的业务细节。

完成接口定义后，可以在仓储层实现该接口的查询仓储。实现仓储查询的技术方案可以根据系统架构自由选择，如 SQL、ES 或其它技术方案。

# 应用服务与事件驱动

一个应用与其它应用之间的传递信息可以分为同步或异步。应用服务用于同步传递，而事件为异步传递。

## 应用服务

应用服务是领域驱动设计（DDD）架构中的一个重要组成部分。它位于领域层之上，主要负责接收来自外部（如用户界面、其他系统等）的请求，协调领域层中的领域服务、领域实体和值对象来完成具体的业务操作，然后将处理结果返回给外部请求者。应用服务充当了外部世界与领域模型之间的桥梁。

**职责：**

- 请求接收与转发：应用服务接收来自外部（如用户界面、其他系统等）的请求，通过领域层的领域服务、聚合或值对象完成业务封装。
- 发起事件：多个系统之间的交互关系除常用的请求/响应模式外，还应支持事件驱动模式，可以通过应用服务发起事件
- 数据转换与适配：应用服务需要将来自外部的请求数据结构转换为领域对象或将领域对象转换为外部的响应数据。
- 事务管理：应用服务对应一个业务用例，用例内的所有业务操作应该在一个事务内处理以保证其一致性与完整性。
- 安全与权限控制：应用服务应该对业务请求进行安全性验证，以保证该应用有执行特定业务的操作权限。

> tips1: 应用服务区别于领域服务:
> - 应用服务是封装业务而领域服务是实现业务
> - 应用服务面向业务用例设计而领域服务面向业务行为而设计
> - 应用服务位于领域服务上层，应用服务可调用领域服务而领域服务不能调用应用服务

## 事件驱动

事件驱动架构（Event - Driven Architecture，EDA）是一种软件架构风格,是领域驱动设计（DDD）架构中的另一个重要组成部分。 它一般由事件生产者、事件消费者和事件通道组成，事件生产者一般由应用服务引发，用于与其它界限上下文异步处理业务以达到解耦的作用。

事件传递的消息与应用服务一样，都以数据传输对象（DTO）形式承载数据对象。

## 使用nest-ddd实现应用服务和事件驱动。

**`ApplicationService`** 接口

标识一个类是应用服务，使用`nest-spring-boot-starter`组件的情况下，需要使用`@Component` 标识为spring bean。也可以用`@AppService` 注解标识应用服务，此时可以不用实现接口`ApplicationService`。

> tips1，可使用`ApplicationService`或`@AppService`标识应用服务，如果服务类无任何标识，Nest将不会跟踪服务方法内对实体的任何操作行为。可能会导致事件和实体无法进入仓储而丢失数据。

- `getTransactionClass()` 方法，定义该应用服务所使用的事务方法， 默认值`Transaction.DefaultTransaction.class`，该方式不处理任何事务。使用`nest-spring-boot-starter`组件的情况下，可以使用`SpringTransaction.class`，该方式使用`spring`的事务管理机制处理，你也可以实现`Transaction`接口自定义你的事务管理方式，比如扩展一些分布式事务方法。

**`@AppService`** 注解

该注解是nest-spring的增强，需要先引入`nest-spring-boot-starter`组件。

与`ApplicationService`接口一样，用于标识一个类为应用服务，使用`ApplicationService`接口标识的应用服务需要手动将服务类注册或添加`@Component`注解，以方便`Spring IOC`统一管理，而使用`@AppService`将自动注册到`Spring IOC`。

- `transaction`属性，定义应用服务所使用的事务方法，默认值`Transaction.DefaultTransaction.class`，该方式不处理任何事务。也可以使用`SpringTransaction.class`，该方式使用`spring`的事务管理机制处理，你也可以实现`Transaction`接口自定义你的事务管理方式，比如扩展一些分布式事务方法。

**`@AppServiceIgnore`** 注解

标识为忽略应用服务，标识该注解的方法在被调用时不会被Nest跟踪管理。

如果一个正常的应用服务方法内调用了一个注解该类的服务方法时，注解该类的服务方法内部的实体操作会被正常的应用服务方法接管而统一管理。

**`EventUtil`** 工具类

用于发布事件，使用该类发布的事件将通过消息中间件发布到不同应用的消费者。

在Nest 中，EventUtil 发布一个事件后，事件消息将跟随业务数据一起持久化到本地事件表，然后由 NestEngine 启动的事件发布工作线程从业务库的本地事件表获取未发布的事件并提交到对应的消息中间件。

- `publish`方法，用于发布一个事件，该方法有两个重载，区别在于可指定延迟发布事件的时间。事件发布时只需要指定事件名称和事件需要传递的 DTO对象。Nest将通过配置信息查找该事件将使用的事件通道，并使用相同的事件通道发布事件。 如果未配置通道，Nest使用默认的本地通道发布，这将可能导致跨应用的消费者无法获得事件通道。

**`EventHandler`** 接口

定义一个事件处理器（事件消费者），用于接受并处理事件消息。

- `getEventName()`方法，定义该处理器处理的事件名称
- `getEventDataClass()`方法，定义该处理器处理的事件数据的类型
- `getConsumeMode()`方法，定义该处理器使用的消费模式，模式分推和拉两种
- `handle()`方法，具体的处理事件消息的处理器

在Nest中，定义了处理器后需要使用`NestEngine.registerEventHandler()`方法注册。注意，注册事件需要在`NestEngine.start()`前执行。在使用了`nest-spring-boot-starter`组件情况下， 只需要将`EventHandler`的实现类注册到`Spring Ioc`中即可，比如`@Component`注解。

```java
package com.zhaofujun.nest.test;

import com.zhaofujun.nest.ddd.EventHandler;

public class UserCreatedHandler implements EventHandler<UserDto> {

    @Override
    public String getEventName() {
        return "user_created";
    }

    @Override
    public Class<UserDto> getEventDataClass() {
        return UserDto.class;
    }

    @Override
    public void handle(UserDto eventData) {
        System.out.println("接收到用户创建成功的事件：" + eventData.toString());
    }

}

```
**`@EventListener`** 注解

该注解是nest-spring的增强，需要先引入`nest-spring-boot-starter`组件。

与`EventHandler`一样，用于标识一个方法是消费者。标识该注解的类需要被 Spring 容器管理。服务方法签名是固定的

- `eventName` 定义事件名称
- `eventDataClass` 定义事件接收消息的类型
- `consumeMode` 定义事件获取模式，包括推和拉两种模式


```java
package com.zhaofujun.nest.demo.handlers;

import org.springframework.stereotype.Component;
import com.zhaofujun.nest.boot.EventListener;
import com.zhaofujun.nest.demo.appservices.UserDto;

@Component
public class UserEventHandlerListener {
    @EventListener(eventDataClass = UserDto.class, eventName = "user_created")
    public void userCreate(UserDto userDto) {
        System.out.println("event listener");
    }
}

```

# NestEngine使用说明


**`NestEngine`类**

Nest框架的主引擎，用于初始化和管理 Nest 框架的默认核心组件，还支持注册事件处理器、第三方组件扩展、仓储、和缓存管理等。

- `initEventEnvironment()`方法：初始化事件驱动架构的相关环境，包括事件仓储与事件应用服务
- `registerByContainer()`方法：通过容器自动注册缓存、事件、事件处理器、仓储、第三方扩展等
- `registerProviders()`方法：手动注册第三方扩展组件，支持扩展缓存提供者、锁提供者、编号生成器、事件通道提供者等
- `registerCacheItem()`方法：手动注册缓存项，支持定义某一组缓存策略，如缓存提供者、统一的过期时间等
- `registerEventItem()`方法：手动注册事件项，支持定义一个事件的策略，如事件对应的消息中间件等
- `registerRepository()`方法：手动注册仓储
- `registerEventHandler()`方法：手动注册事件处理器
- `start()`方法：启动引擎，引擎将开启内部消息的处理，并启动事件发布工作线程与事件处理工作线程
- `stop()`方法：关闭引擎，引擎关闭的同时自动关闭事件发布工作线程与事件处理工作线程

