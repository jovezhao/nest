# Nest说明

全新的nest框架

#spring cloud与nest鸟窝DDD集成方式

## pom文件引入
```POM文件
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.kupanda.kuxuebangbang</groupId>
    <artifactId>parent</artifactId>
    <version>1.0-SNAPSHOT</version>
    <packaging>pom</packaging>


    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.1.7.RELEASE</version>
        <relativePath/>
    </parent>

    <properties>
        <jdk.version>1.8</jdk.version>
        <spring-cloud.version>Greenwich.SR2</spring-cloud.version>
        <lombok.version>1.18.8</lombok.version>
        <spring-cloud-alibaba-dependencies.version>2.1.0.RELEASE</spring-cloud-alibaba-dependencies.version>
        <nest.version>1.1-SNAPSHOT</nest.version>
        <mysql-connector-java.version>8.0.17</mysql-connector-java.version>
        <HikariCP.version>3.3.1</HikariCP.version>
        <dozer.version>5.5.1</dozer.version>
        <mybatis-spring-boot-starter.version>2.1.0</mybatis-spring-boot-starter.version>
        <pagehelper-spring-boot-starter.version>1.2.12</pagehelper-spring-boot-starter.version>
        <chuanglan253-sms.version>1.0-SNAPSHOT</chuanglan253-sms.version>
    </properties>

    <dependencies>

        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-dependencies</artifactId>
            <version>${spring-cloud.version}</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>

        <!-- https://mvnrepository.com/artifact/org.projectlombok/lombok -->
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <version>${lombok.version}</version>
            <scope>provided</scope>
        </dependency>


        <dependency>
            <groupId>net.sf.dozer</groupId>
            <artifactId>dozer</artifactId>
            <version>${dozer.version}</version>
        </dependency>


        <dependency>
            <groupId>com.alibaba.cloud</groupId>
            <artifactId>spring-cloud-alibaba-dependencies</artifactId>
            <version>${spring-cloud-alibaba-dependencies.version}</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>

        <dependency>
            <groupId>com.guoshouxiang.nest</groupId>
            <artifactId>nest-ddd</artifactId>
            <version>${nest.version}</version>
        </dependency>

        <dependency>
            <groupId>com.guoshouxiang.nest</groupId>
            <artifactId>nest-spring-boot-starter</artifactId>
            <version>${nest.version}</version>
        </dependency>

        <dependency>
            <groupId>com.guoshouxiang.nest</groupId>
            <artifactId>nest-redis</artifactId>
            <version>${nest.version}</version>
        </dependency>


        <dependency>
            <groupId>com.guoshouxiang.nest</groupId>
            <artifactId>nest-activemq</artifactId>
            <version>${nest.version}</version>
        </dependency>

        <dependency>
            <groupId>com.guoshouxiang.nest</groupId>
            <artifactId>nest-ioc</artifactId>
            <version>${nest.version}</version>
        </dependency>


        <!-- 数据库驱动 -->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>${mysql-connector-java.version}</version>
        </dependency>
        <!-- 数据库驱动结束 -->

        <!-- 光连接池 -->
        <dependency>
            <groupId>com.zaxxer</groupId>
            <artifactId>HikariCP</artifactId>
            <version>${HikariCP.version}</version>
        </dependency>
        <!-- 光连接池 -->

        <!-- https://mvnrepository.com/artifact/org.mybatis.spring.boot/mybatis-spring-boot-starter -->
        <dependency>
            <groupId>org.mybatis.spring.boot</groupId>
            <artifactId>mybatis-spring-boot-starter</artifactId>
            <version>${mybatis-spring-boot-starter.version}</version>
        </dependency>

        <!-- https://mvnrepository.com/artifact/com.github.pagehelper/pagehelper-spring-boot-starter -->
        <dependency>
            <groupId>com.github.pagehelper</groupId>
            <artifactId>pagehelper-spring-boot-starter</artifactId>
            <version>${pagehelper-spring-boot-starter.version}</version>
        </dependency>

        <!-- https://mvnrepository.com/artifact/com.github.pagehelper/pagehelper-spring-boot-starter -->
        <dependency>
            <groupId>com.chuanglan</groupId>
            <artifactId>ChuangLanSmsDemo</artifactId>
            <version>${chuanglan253-sms.version}</version>
        </dependency>


        <!--JSON-->
        <dependency>
            <groupId>com.fasterxml.jackson.core</groupId>
            <artifactId>jackson-annotations</artifactId>
            <version>2.9.0</version>
        </dependency>

        <!-- https://mvnrepository.com/artifact/org.redisson/redisson -->
        <dependency>
            <groupId>org.redisson</groupId>
            <artifactId>redisson</artifactId>
            <version>3.11.1</version>
        </dependency>

        <!-- https://mvnrepository.com/artifact/io.github.openfeign/feign-httpclient -->
        <dependency>
            <groupId>io.github.openfeign</groupId>
            <artifactId>feign-httpclient</artifactId>
            <version>10.4.0</version>
        </dependency>
    </dependencies>


    <distributionManagement>
        <snapshotRepository>
            <id>deploy-snapshots</id>
            <url>http://192.168.2.251:8081/repository/maven-snapshots/</url>
        </snapshotRepository>
        <repository>
            <id>deploy-releases</id>
            <url>http://192.168.2.251:8081/repository/maven-releases/</url>
        </repository>
    </distributionManagement>

</project>
```

## nest缓存配置
```JAVA
package com.kupanda.kuxuebangbang.sms.context.configure;

import com.guoshouxiang.nest.configuration.CacheConfiguration;
import com.guoshouxiang.nest.configuration.ConfigurationManager;
import com.guoshouxiang.nest.container.BeanFinder;
import com.guoshouxiang.nest.redis.RedisCacheProvider;
import com.guoshouxiang.nest.utils.EntityCacheUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Redis配置
 **/
@Configuration
@Slf4j
public class RedisConfiguration {

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private int port;

    @Value("${spring.redis.timeout}")
    private int timeout;

    @Value("${spring.redis.jedis.pool.max-idle}")
    private int maxIdle;

    @Value("${spring.redis.jedis.pool.min-idle}")
    private int minIdle;

    @Value("${spring.redis.jedis.pool.max-wait}")
    private long maxWaitMillis;

    @Value("${spring.redis.password}")
    private String password;


    @Value("${spring.application.name}")
    private String applicationName;

    @Bean
    JedisPoolConfig jedisPoolConfig() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMinIdle(minIdle);
        jedisPoolConfig.setMaxIdle(maxIdle);
        jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);
        return jedisPoolConfig;
    }


    @Bean
    JedisPool jedisPool() {
        log.info("JedisPool注入成功！！");
        log.info("redis地址：" + host + ":" + port);
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(maxIdle);
        jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);
        // 连接耗尽时是否阻塞, false报异常,ture阻塞直到超时, 默认true
        jedisPoolConfig.setBlockWhenExhausted(true);
        // 是否启用pool的jmx管理功能, 默认true
        jedisPoolConfig.setJmxEnabled(true);
        JedisPool jedisPool = new JedisPool(jedisPoolConfig, host, port, timeout, password);
        return jedisPool;
    }

    @Bean(RedisCacheProvider.CODE)
    RedisCacheProvider redisCacheProvider(JedisPool jedisPool) {
        RedisCacheProvider redisCacheProvider = new RedisCacheProvider(jedisPool);
        return redisCacheProvider;
    }

    /**
     * 配置管理器
     *
     * @param beanFinder
     * @return
     */
    @Bean
    ConfigurationManager configurationManager(BeanFinder beanFinder) {
        ConfigurationManager configurationManager = new ConfigurationManager(beanFinder);
        return configurationManager;
    }

    /**
     * 配置缓存
     *
     * @param configurationManager
     * @return
     */
    @Bean
    CacheConfiguration cacheConfiguration(ConfigurationManager configurationManager) {
        CacheConfiguration cacheConfiguration = new CacheConfiguration();
        cacheConfiguration.setCode(EntityCacheUtils.getCacheCode());
        cacheConfiguration.setIdleSeconds(2000);
        cacheConfiguration.setName(applicationName);
        cacheConfiguration.setProviderCode(RedisCacheProvider.CODE);
        configurationManager.register(cacheConfiguration);
        return cacheConfiguration;
    }

}


```


## springboot 集成nest activeMq 业务
```JAVA
package com.kupanda.kuxuebangbang.wechat.applet.distribution.api.configure;

import com.guoshouxiang.nest.activemq.ActiveMQMessageChannel;
import com.guoshouxiang.nest.configuration.EventConfiguration;
import com.guoshouxiang.nest.container.BeanFinder;
import com.kupanda.kuxuebangbang.sms.event.SendSmsEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RefreshScope
public class MQConfigure {


    @Value(value = "${active.brokers}")
    private String BROKERS;

    @Bean(ActiveMQMessageChannel.Code)
    public ActiveMQMessageChannel activeMQMessageChannel(BeanFinder beanFinder) {
        ActiveMQMessageChannel messageChannel = new ActiveMQMessageChannel(beanFinder, BROKERS);
        return messageChannel;
    }
	
	/**
	*需要指定消息事件的名称以及对应事件选择哪个MQ
	* eventConfiguration.setEventCode(),设置消息通道名称
	  eventConfiguration.setMessageChannelCode(),设置该通道选择走哪个MQ
	**/
    @Bean(SendSmsEvent.EVENT_NAME)
    public EventConfiguration sendSmsEvent() {
        EventConfiguration eventConfiguration = new EventConfiguration();
        eventConfiguration.setEventCode(SendSmsEvent.EVENT_NAME);
        eventConfiguration.setMessageChannelCode(ActiveMQMessageChannel.Code);
        return eventConfiguration;
    }
}

```
### 对EventBus进行自动注册,消息发送和接收端均需要进行此注册操作
```JAVA
package com.kupanda.kuxuebangbang.wechat.applet.distribution.api.configure;

import com.guoshouxiang.nest.context.event.EventBus;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class DefaultCommandLineRunner implements ApplicationContextAware {
    @Autowired
    private EventBus eventBus;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

        eventBus.autoRegister();


    }
}



```


### 消息事件定义
```JAVA

package com.kupanda.kuxuebangbang.sms.event;

import com.guoshouxiang.nest.context.event.BaseEvent;
import com.kupanda.kuxuebangbang.sms.event.domain.SendSmsHandlerData;

/**
 * 短信发送事件
 **/
public class SendSmsEvent extends BaseEvent<SendSmsHandlerData> {

    public static final String EVENT_NAME="SEND_SMS";

    public SendSmsEvent(SendSmsHandlerData eventObject, String source) {
        super(eventObject, source);
    }

    @Override
    public String getEventCode() {
        return EVENT_NAME;
    }
}

```

### 消息内容定义
```JAVA
package com.kupanda.kuxuebangbang.sms.event.domain;

import com.guoshouxiang.nest.context.event.EventData;
import com.kupanda.kuxuebangbang.sms.event.domain.base.EventType;
import com.kupanda.kuxuebangbang.sms.event.domain.base.Platform;
import com.kupanda.kuxuebangbang.sms.event.domain.base.SmsType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 *
 **/
@NoArgsConstructor
@Data
public class SendSmsHandlerData extends EventData {

    /**
     * 平台手机号
     */
     private List<PlatformPhone> platformPhone;

    /**
     * 发送参数
     */
    private Map<String,String> param;

    /**
     * 发送平台
     */
     private Platform sendPlatform;

    /**
     * 业务事件
     */
    private EventType eventType;

    /**
     * 短息类型
     */
    private SmsType smsType;

    @Override
    public String getEventCode() {
        return null;
    }
}

```

### 消息发送者
```JAVA

/**
*
* 需要使用Nest定义的工具bean，则需要用@AppService来进行注解,否则找不到对应的bean实例
*/
@AppService
public class SecurityServiceImpl implements SecurityService {
 /**
 *发送消息体,通过eventBus.pulish() 来进行对应的消息事件进行发送
 *
 **/
 @Override
    public void sendCaptcha(SendCaptchaVo vo) {
        AssertUtil.isNotNull(vo, GlobalEnum.GlobalValueVerifyEnum.PARAM_IS_NULL.getCode(), "手机号为空", "发送验证码参数为空");
        AssertUtil.isNotNull(vo.getPhone(), GlobalEnum.GlobalValueVerifyEnum.PARAM_IS_NULL.getCode(), "手机号为空", "发送验证码手机号为空");
        AssertUtil.isNotNull(vo.getEventType(), GlobalEnum.GlobalValueVerifyEnum.PARAM_IS_NULL.getCode(), "系统异常", "短信事件类型为空");
        AssertUtil.isNotNull(vo.getSmsType(), GlobalEnum.GlobalValueVerifyEnum.PARAM_IS_NULL.getCode(), "系统异常", "短信类型为空");

        //验证用户是否发送过验证码
        String actionRedisKey = SEND_SMS_CAPTCHA_ACTION_REDIS_PREFIX + vo.getPhone();
        Object o = redisTemplate.opsForValue().get(actionRedisKey);
        if (!ObjectUtils.isEmpty(o)) {
            throw new BaseException(GlobalEnum.GlobalOtherEnum.GLOBAL_FAILED.getCode(), "请勿重复发送验证码", "请勿重复发送验证码");
        }


        //生成验证码
        String captcha = NumberUtil.getNumber(VERIFICATION_CODE_LENGTH);
        SendSmsHandlerData sendSmsHandlerData = new SendSmsHandlerData();
        sendSmsHandlerData.setSmsType(vo.getSmsType());
        sendSmsHandlerData.setSendPlatform(Platform.DISTRIBUTION_API);
        sendSmsHandlerData.setEventType(vo.getEventType());
        Map<String, String> param = new HashMap<>();
        param.put("captcha", captcha);
        param.put("minute", VERIFICATION_CODE_EXPIRATION_TIME.toString());
        sendSmsHandlerData.setParam(param);

        List<PlatformPhone> platformPhone = new ArrayList<>();
        PlatformPhone platformPhone1 = new PlatformPhone();
        List<String> phone = new ArrayList<>();
        phone.add(vo.getPhone());
        platformPhone1.setPhone(phone);
        platformPhone1.setTargetPlatform(Platform.DISTRIBUTION_API);
        platformPhone.add(platformPhone1);
        sendSmsHandlerData.setPlatformPhone(platformPhone);
        SendSmsEvent smsEvent = new SendSmsEvent(sendSmsHandlerData, "");
        eventBus.publish(smsEvent);

        //发送验证码操作入REDIS
        redisTemplate.opsForValue().set(actionRedisKey, vo.getPhone(), SEND_SMS_CAPTCHA_ACTION_EXPIRATION_TIME, TimeUnit.SECONDS);

        String redisKey = SEND_SMS_CAPTCHA_REDIS_PREFIX + vo.getPhone();
        //验证码入REDIS
        redisTemplate.opsForValue().set(redisKey, captcha, VERIFICATION_CODE_EXPIRATION_TIME, TimeUnit.MINUTES);
    }
}

```

### 消息接受者
```JAVA
package com.kupanda.kuxuebangbang.sms.context.event;

import com.guoshouxiang.nest.context.event.EventArgs;
import com.guoshouxiang.nest.context.event.EventHandler;
import com.kupanda.kuxuebangbang.sms.context.appservice.SmsService;
import com.kupanda.kuxuebangbang.sms.context.domain.SaveSmsVo;
import com.kupanda.kuxuebangbang.sms.context.strategy.ChuangLanSMSStrategy;
import com.kupanda.kuxuebangbang.sms.context.strategy.SMSStrategy;
import com.kupanda.kuxuebangbang.sms.event.SendSmsEvent;
import com.kupanda.kuxuebangbang.sms.event.domain.SendSmsHandlerData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 *
 **/
@Component
public class SendSmsHandler implements EventHandler<SendSmsHandlerData> {


    @Autowired
    private Map<String, SMSStrategy> strategyMap=new HashMap<>();


    @Override
    public String getEventCode() {
        return SendSmsEvent.EVENT_NAME;
    }

    @Override
    public Class<SendSmsHandlerData> getEventDataClass() {
        return SendSmsHandlerData.class;
    }

    @Override
    public void handle(SendSmsHandlerData smsSendHandlerData, EventArgs eventArgs) {
        SMSStrategy smsStrategy = strategyMap.get(ChuangLanSMSStrategy.STRATEGY_NAME);
        smsStrategy.sendSMS(smsSendHandlerData);

    }
}

```

## 实体定义
```JAVA
package com.kupanda.kuxuebangbang.sms.context.appservice.impl.repository;

import com.guoshouxiang.nest.context.model.BaseEntity;
import com.guoshouxiang.nest.context.model.StringIdentifier;
import com.kupanda.kuxuebangbang.common.identifier.NumIdentifier;
import com.kupanda.kuxuebangbang.sms.context.domain.EventType;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Map;

/**
 * 短信实体
 **/
@NoArgsConstructor
@Data
public class Sms extends BaseEntity<NumIdentifier> {

    /**
     * 手机号
     */
    private String phone;

    /**
     * 短信通道
     */
    private Channel channel;

    /**
     * 发送平台
     */
    private Platform sendPlatform;

    /**
     * 目标平台
     */
    private Platform targetPlatform;

    /**
     * 发送参数
     */
    private Map<String,String> param;

    /**
     * 业务类型
     */
    private EventType eventType;


    /**
     * 回调
     */
    private String callback;


    /**
     * 是否发送成功
     */
    private SmsStatus status;

    /**
     * 创建时间
     */
    private Date createTime;


    /**
     * 初始化
     * @param phone
     * @param channel
     * @param sendPlatform
     * @param targetPlatform
     * @param param
     * @param eventType
     */
    public void init(String phone, Channel channel, Platform sendPlatform, Platform targetPlatform, Map<String,String> param, EventType eventType, String callback, SmsStatus status){
        this.phone=phone;
        this.channel=channel;
        this.sendPlatform=sendPlatform;
        this.targetPlatform=targetPlatform;
        this.param=param;
        this.eventType=eventType;
        this.callback=callback;
        this.status=status;
        this.createTime=new Date();

    }


    /**
     * 回调
     * @param callback
     * @param status
     */
    public void callback(String callback,SmsStatus status){
        this.callback=callback;
        this.status=status;
    }

}
```
## 实体仓储
```JAVA
package com.kupanda.kuxuebangbang.sms.context.appservice.impl.repository.model;

import com.guoshouxiang.nest.context.loader.EntityLoader;
import com.guoshouxiang.nest.context.model.Identifier;
import com.guoshouxiang.nest.context.repository.Repository;
import com.guoshouxiang.nest.utils.EntityUtils;
import com.guoshouxiang.nest.utils.JsonUtils;
import com.kupanda.kuxuebangbang.common.bean.DmoToEntityConverter;
import com.kupanda.kuxuebangbang.common.bean.EntityToDmoConverter;
import com.kupanda.kuxuebangbang.sms.context.appservice.impl.repository.Sms;
import com.kupanda.kuxuebangbang.sms.context.appservice.impl.repository.mappers.SmsDmoMapper;
import com.kupanda.kuxuebangbang.sms.context.appservice.impl.repository.mappers.dmo.SmsDmoWithBLOBs;
import net.sf.cglib.beans.BeanCopier;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.Map;


/**
 * 短信仓储
 **/
@Component
public class SmsRepository implements Repository<Sms> {

    @Resource
    private SmsDmoMapper smsDmoMapper;


    @Override
    public Class<Sms> getEntityClass() {
        return Sms.class;
    }
	/**
	* 我这走的cglib的Bean属性注入,DMO和Entityly互相映射,但因为cglib的映射必须有set方法，但是这个有和实体定义的贫血模型方式有冲突，暂时还没有找到其他类似的对象映射框架,暂时用cglib来进行替代，后面要是有合适的则把实体的@Data注解换成@Geter注解就可以了
	*
	**/
    private BeanCopier dmoToEntity = BeanCopier.create(SmsDmoWithBLOBs.class, Sms.class, true);

    private BeanCopier entityToDmo = BeanCopier.create(Sms.class, SmsDmoWithBLOBs.class, true);

    @Override
    public Sms getEntityById(Identifier identifier, EntityLoader<Sms> entityLoader) {


        SmsDmoWithBLOBs smsDmoWithBLOBs = smsDmoMapper.selectByPrimaryKey(identifier.toValue());
        if(ObjectUtils.isEmpty(smsDmoWithBLOBs)){
            return null;
        }

        Sms sms = entityLoader.create(identifier);
        dmoToEntity.copy(smsDmoWithBLOBs, sms, new DmoToEntityConverter(this.getClass(), "param"));
        if(!ObjectUtils.isEmpty(smsDmoWithBLOBs.getParam())){
            Map param = JsonUtils.toObj(smsDmoWithBLOBs.getParam(), Map.class);
            EntityUtils.setValue(Sms.class,sms,"param",param);
        }

        return sms;
    }

    @Override
    public void save(Sms sms) {
        if(ObjectUtils.isEmpty(sms)){
           return;
        }

        SmsDmoWithBLOBs smsDmoWithBLOBs = new SmsDmoWithBLOBs();
        entityToDmo.copy(sms, smsDmoWithBLOBs, new EntityToDmoConverter(this.getClass(), "param"));
        if(!ObjectUtils.isEmpty(sms.getParam())){
            smsDmoWithBLOBs.setParam(JsonUtils.toJsonString(sms.getParam()));
        }
        if(smsDmoMapper.updateByPrimaryKeyWithBLOBs(smsDmoWithBLOBs)<=0){
            smsDmoMapper.insert(smsDmoWithBLOBs);
        }
    }

    @Override
    public void remove(Sms sms) {
        //TODO 短信暂时不实现删除功能
    }
}


```

### 实体的创建和加载,EntityFactory.create 创建实体(无中生有),EntityFactory.load()加载实体

```JAVA
package com.kupanda.kuxuebangbang.sms.context.appservice.impl;

import com.guoshouxiang.nest.context.EntityFactory;
import com.guoshouxiang.nest.spring.AppService;
import com.kupanda.kuxuebangbang.common.identifier.NumIdentifier;
import com.kupanda.kuxuebangbang.sms.context.appservice.SmsService;
import com.kupanda.kuxuebangbang.sms.context.appservice.impl.repository.Sms;
import com.kupanda.kuxuebangbang.sms.context.domain.SaveSmsVo;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

/**
 * 短信业务实现层
 **/
@AppService
public class SmsServiceImpl implements SmsService {


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(SaveSmsVo vo) {
        if(ObjectUtils.isEmpty(vo)){
            throw new NullPointerException("短信参数为空");
        }
        if(ObjectUtils.isEmpty(vo.getPhone())){
            throw new NullPointerException("手机号为空");
        }
        if(ObjectUtils.isEmpty(vo.getChannel())){
            throw new NullPointerException("短信发送通道为空");
        }
        if(ObjectUtils.isEmpty(vo.getSendPlatform())){
            throw new NullPointerException("发送平台为空");
        }
        if(ObjectUtils.isEmpty(vo.getTargetPlatform())){
            throw new NullPointerException("目标平台为空");
        }
        if(ObjectUtils.isEmpty(vo.getCallback())){
            throw new NullPointerException("回调参数为空");
        }
        if(ObjectUtils.isEmpty(vo.getStatus())){
            throw new NullPointerException("发送成功状态为空");
        }
        if(ObjectUtils.isEmpty(vo.getEventType())){
            throw new NullPointerException("发送事件为空");
        }


        Sms sms = null;
        if(ObjectUtils.isEmpty(vo.getId())){
            sms = EntityFactory.create(Sms.class, new NumIdentifier());
            sms.init(vo.getPhone(),vo.getChannel(),vo.getSendPlatform(),vo.getTargetPlatform(),vo.getParam(),vo.getEventType(),vo.getCallback(), vo.getStatus());
        }else{
            sms = EntityFactory.load(Sms.class, NumIdentifier.valueOf(vo.getId()));
            if(ObjectUtils.isEmpty(sms)){
                throw new IllegalArgumentException("短信不存在");
            }
        }


    }
}


```




