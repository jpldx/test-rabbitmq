package com.jpldx.test.rabbitmq.helloworld;

import com.jpldx.test.rabbitmq.utils.RabbitMQUtils;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 消费者
 *
 * @author jpldx
 */
public class Consumer {

    public static final String QUEUE_NAME = "hello";

    /**
     * 因为junit不支持多线程，无法实现监听，所以使用main函数测试
     *
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
        // 创建连接、创建信道
        Connection connection = RabbitMQUtils.getConnection();
        Channel channel = connection.createChannel();
//        channel.queueDeclare("hello", true, false, false, null);

        // 消费消息
        // queue：队列名称（消费哪个队列中的消息）
        // autoAck：是否自动确认
        // callback：消费回调接口
//        channel.basicConsume(QUEUE_NAME, true, new DefaultConsumer(channel) {
//            // 消息处理回调
//            // body：消息内容
//            @Override
//            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
//                String msg = new String(body);
//                System.out.println(msg);
//            }
//        });

        // 消费消息2
        // DeliverCallback: 成功消费的回调
        // CancelCallback: 取消消费的回调
        DeliverCallback deliverCallback = (consumerTag,message) -> {
            System.out.println("Receive message: " + new String(message.getBody()));
        };

        CancelCallback cancelCallback = consumerTag -> {
            System.out.println("Message has been canceled...");
        };

        channel.basicConsume(QUEUE_NAME, true, deliverCallback, cancelCallback);


        // 5、关闭通道、关闭连接；不关闭则会持续监听
//        channel.close();
//        connection.close();
    }

}
