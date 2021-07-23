package com.jpldx.test.rabbitmq.utils;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * RabbitMQ 工具类
 *
 * @author chen.xudong
 * @version 1.0
 * @since 2021/1/19
 */
public class RabbitMqUtils {

    private static final ConnectionFactory connectionFactory;
    static{
        // 连接工厂属于重量级资源 -> 类加载时初始化一次
        connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("127.0.0.1");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/test");
        connectionFactory.setUsername("root");
        connectionFactory.setPassword("admin");
    }

    /**
     * 获取连接
     *
     * @return
     */
    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = connectionFactory.newConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }

    /**
     * 关闭通道和连接
     *
     * @param connection
     * @param channel
     */
    public static void close(Channel channel, Connection connection) {
        try {
            if(channel != null)channel.close();
            if(connection != null)connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
