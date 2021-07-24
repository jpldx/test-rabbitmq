package com.jpldx.test.rabbitmq.workqueue;

import com.jpldx.test.rabbitmq.utils.RabbitMQUtils;
import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * 消费者-1 (工作线程-1)
 *
 * @author jpldx
 */
public class Worker1 {

    private static final String QUEUE_NAME = "work";

    public static void main(String[] args) throws IOException {
        Connection connection = RabbitMQUtils.getConnection();
        Channel channel = connection.createChannel();

        System.out.println("Worker1 waiting...");
        channel.basicConsume(QUEUE_NAME, true, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) {
                System.out.println("Receive message：" + new String(body));
            }
        });
    }
}
