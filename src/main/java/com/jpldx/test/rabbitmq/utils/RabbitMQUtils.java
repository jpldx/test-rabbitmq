package com.jpldx.test.rabbitmq.utils;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * RabbitMQ 工具类
 *
 * @author jpldx
 */
public class RabbitMQUtils {

    private static final ConnectionFactory connectionFactory;
    // 创建连接工厂(连接工厂属于重量级资源 -> 类加载时初始化一次)
    static{
        connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("docker.vm.com");
        connectionFactory.setPort(5672);
//        connectionFactory.setVirtualHost("/test");
        connectionFactory.setUsername("root");
        connectionFactory.setPassword("jpldx");
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
     * 关闭信道和连接
     *
     * @param connection
     * @param channel
     */
    public static void close(Channel channel, Connection connection) {
        try {
            if(channel != null) {
                channel.close();
            }
            if(connection != null) {
                connection.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
