package com.jovezhao.nest.rabbitmq;



import com.jovezhao.nest.core.log.ILog;
import com.jovezhao.nest.core.log.LogAdapter;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;


import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by Jove on 2016-03-11.
 */
public class MQConnection {
    private ILog logger=new LogAdapter(this.getClass());
    ConnectionFactory factory;
    Connection connection;

    public MQConnection(String host, int port, String user, String pwd) {
        factory = new ConnectionFactory();
        factory.setAutomaticRecoveryEnabled(true);
        factory.setHost(host);
        factory.setPort(port);
        factory.setUsername(user);
        factory.setPassword(pwd);
    }

    public synchronized Connection getConnection() throws IOException, TimeoutException {
        if (connection == null)
            connection = factory.newConnection();
        if (!connection.isOpen())
            connection = factory.newConnection();
        return connection;
    }

    public void close() {
        if (connection != null && connection.isOpen()) {
            try {
                connection.close();
            } catch (IOException e) {
                logger.warn(e.getMessage());
            }
        }
    }
}
