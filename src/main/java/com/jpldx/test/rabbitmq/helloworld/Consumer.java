package com.jpldx.test.rabbitmq.helloworld;

import com.jpldx.test.rabbitmq.utils.RabbitMqUtils;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 消费者
 *
 * @author chen.xudong
 * @version 1.0
 * @since 2021/1/18
 */
public class Consumer {

    /**
     * 因为junit不支持多线程，无法实现监听，所以使用main函数测试
     * @param args
     * @throws IOException
     * @throws TimeoutException
     */
    public static void main(String[] args) throws IOException, TimeoutException {
//        ConnectionFactory connectionFactory = new ConnectionFactory();
//        connectionFactory.setHost("127.0.0.1");
//        connectionFactory.setPort(5672);
//        connectionFactory.setVirtualHost("/test");
//        connectionFactory.setUsername("root");
//        connectionFactory.setPassword("admin");
//
//        Connection connection = connectionFactory.newConnection();
        Connection connection = RabbitMqUtils.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare("hello", true, false, false, null);

        // 消费消息
        // queue：队列名称（消费哪个队列中的消息）
        // autoAck：是否自动确认
        // callback：消费回调接口
        channel.basicConsume("hello", true, new DefaultConsumer(channel) {
            // 消息处理回调
            // body：消息内容
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg = new String(body);
                System.out.println(msg);
            }
        });

        // 5、关闭通道、关闭连接；不关闭则会持续监听
//        channel.close();
//        connection.close();
    }

}
