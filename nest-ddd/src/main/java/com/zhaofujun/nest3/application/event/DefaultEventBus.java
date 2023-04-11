package com.zhaofujun.nest3.application.event;

import com.zhaofujun.nest3.application.NestApplication;
import com.zhaofujun.nest3.application.config.EventConfiguration;
import com.zhaofujun.nest3.application.provider.MessageChannelProvider;
import com.zhaofujun.nest3.model.EventData;
import com.zhaofujun.nest3.model.EventHandler;

import java.util.ArrayList;
import java.util.List;

public class DefaultEventBus implements EventBus {
    private final String defaultMessageChannelProviderCode = "DefaultMessageChannelProvider";
    private NestApplication nestApplication;
    private ThreadGroup threadGroup;
    private List<MessageController> messageControllers;

    public DefaultEventBus(NestApplication nestApplication) {
        this.nestApplication = nestApplication;
        this.threadGroup = new ThreadGroup("messageControllerGroup");
        this.messageControllers = new ArrayList<>();
    }

    @Override
    public void registerHandler(EventHandler eventHandler) {
        EventConfiguration eventConfiguration = nestApplication.getConfigurationManager().getEventConfigurationByEventCode(eventHandler.getEventCode());
        String messageChannelCode = eventConfiguration == null ? defaultMessageChannelProviderCode : eventConfiguration.getMessageChannelCode();
        MessageController messageController = getMessageController(messageChannelCode);

        messageController.subscribe(eventHandler);
    }

    private MessageController getMessageController(String providerCode) {
        MessageController messageController = messageControllers.stream()
                .filter(p -> p.getMessageChannelProvider().getCode().equals(providerCode))
                .findFirst()
                .orElseGet(() -> getMessageController(defaultMessageChannelProviderCode));
        return messageController;
    }

    @Override
    public void publish(EventData eventData, String eventSource, long delaySecond) {

        EventInfo eventInfo = new EventInfo(eventData, eventSource, delaySecond);
        eventInfo.attach();
    }

    @Override
    public void init() {

        //找到所有通道，并且为每个通道创建一个消息控制器。
        List<MessageChannelProvider> list = nestApplication.getProviderManage().getList(MessageChannelProvider.class);
        list.forEach(p -> {
            MessageController messageController = new MessageController(threadGroup, "messageController_" + p.getCode(), p);
            messageController.start();
            this.messageControllers.add(messageController);
        });
    }

    public void end() {

    }

}
