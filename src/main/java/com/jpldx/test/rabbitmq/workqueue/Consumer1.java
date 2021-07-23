package com.jpldx.test.rabbitmq.workqueue;

import com.jpldx.test.rabbitmq.utils.RabbitMqUtils;
import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * 消费者-1
 *
 * @author chen.xudong
 * @version 1.0
 * @since 2021/1/19
 */
public class Consumer1 {

    public static void main(String[] args) throws IOException {
        Connection connection = RabbitMqUtils.getConnection();

        Channel channel = connection.createChannel();
        channel.queueDeclare("work", true, false, false, null);

        channel.basicConsume("work", true, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg = new String(body);
                System.out.println("消费者-1接收到消息：" + msg);
            }
        });
    }
}
