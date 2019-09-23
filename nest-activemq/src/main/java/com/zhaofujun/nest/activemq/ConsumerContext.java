package com.zhaofujun.nest.activemq;

import javax.jms.Connection;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;

public class ConsumerContext{
    private Connection connection;
    private Session session;
    private MessageConsumer messageConsumer;
    private Message message;


    public Connection getConnection() {
        return connection;
    }

    public Session getSession() {
        return session;
    }

    public MessageConsumer getMessageConsumer() {
        return messageConsumer;
    }

    public Message getMessage() {
        return message;
    }

    public ConsumerContext(Connection connection, Session session, MessageConsumer messageConsumer, Message message) {
        this.connection = connection;
        this.session = session;
        this.messageConsumer = messageConsumer;
        this.message = message;
    }
}
