//package com.zhaofujun.nest3.application.context;
//
//import com.zhaofujun.nest3.application.NestApplication;
//import com.zhaofujun.nest3.application.config.EventConfiguration;
//import com.zhaofujun.nest3.application.event.MessageBacklog;
//import com.zhaofujun.nest3.application.event.MessageInfo;
//import com.zhaofujun.nest3.application.manager.ConfigurationManager;
//import com.zhaofujun.nest3.application.provider.MessageChannelProvider;
//
//import java.util.HashSet;
//import java.util.Set;
//
//public class MessageUnitOfWork {
//    private Set<MessageBacklog> messageBacklogs = new HashSet<>();
//    private MessageResendStore messageResendStore;
//    public void addMessageBacklog(MessageInfo messageInfo){
//        //
//        MessageBacklog backlog=new MessageBacklog(messageInfo.getData().getEventCode(),"",messageInfo.getData().getClass().getName(),messageInfo.getMessageId(),messageInfo.getSendTime());
//        this.messageBacklogs.add(backlog);
//    }
//
//    private void commitMessage() {
//        messageBacklogs.forEach(p -> {
//
//
//            ConfigurationManager configurationManager = NestApplication.current().getConfigurationManager();
//            EventConfiguration eventConfiguration = configurationManager.getEventConfigurationByEventCode(p.getEventCode());
//            MessageChannelProvider messageChannelProvider = NestApplication.current().getProviderManage().get(MessageChannelProvider.class, eventConfiguration.getMessageChannelCode());
//            try {
//                messageChannelProvider.commit(p.getEventCode(),p.getMessageId() , p.getMessageInfoString());
//            } catch (Exception ex) {
//                //投递到消息中间件时发生异常，将有异常的数据存入待发送区域，用于消息补偿
//                logger.warn("提交消息时失败，消息将通过补偿器重试，失败原因：" + ex.getMessage(), ex);
//
//                MessageResendStore messageResendStore = MessageResendFactory.create();
//                messageResendStore.add(p);
//            }
//        });
//        delayMessageBacklogs.forEach(p -> {
//            try {
//                DelayMessageStore delayMessageStore = DelayMessageStoreFactory.create();
//                delayMessageStore.add(p);
//            } catch (Exception ex) {
//                //考虑一般情况下，DelayMessageStore和MessageResendStore存储中间件相同，所以加到待发送区域也应该会失败，所以暂不做重试逻辑
//                logger.warn("提交延时消息失败，失败原因", ex);
//            }
//        });
//    }
//
//}
