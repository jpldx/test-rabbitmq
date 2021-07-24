package com.jpldx.test.rabbitmq.helloworld;

import com.jpldx.test.rabbitmq.utils.RabbitMQUtils;
import com.rabbitmq.client.*;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * "Hello World" 模型 点对点直连队列
 * 生产者
 *
 * @author jpldx
 */
public class Producer {

    public static final String QUEUE_NAME = "hello";

    @Test
    public void test() throws IOException, TimeoutException {
        // 1、创建rabbitmq的连接工厂对象
//        ConnectionFactory connectionFactory = new ConnectionFactory();
//        connectionFactory.setHost("127.0.0.1");
//        connectionFactory.setPort(5672);
//        // 虚拟主机
//        connectionFactory.setVirtualHost("/test");
//        // 设置访问虚拟主机的用户名密码
//        connectionFactory.setUsername("root");
//        connectionFactory.setPassword("admin");
//
//        // 2、获取连接
//        Connection connection = connectionFactory.newConnection();
        Connection connection = RabbitMQUtils.getConnection();
        // 3、获取信道
        Channel channel = connection.createChannel();
        // 通过通道声明队列
        // queue：队列名称(不存在则自动创建)
        // durable：队列是否持久化(未持久化的队列会在服务重启时丢失;该参数不代表消息是否持久化) - Feature1
        // exclusive：队列是否独占(队列是否只能当前连接可用 false => 只允许一个消费者进行消费 / true => 允许多个消费者进行消费)
        // autoDelete：队列是否在消费完成并断开连接后自动删除 - Feature2
        // arguments：额外参数
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);

        // 4、发布消息
        // exchange：交换机名称
        // routingKey：队列名称
        // props：额外设置(包括设置消息是否持久化)
        // body：消息内容(字节数组)

        String message = "Hello RabbitMQ ~!";
        channel.basicPublish("",
                QUEUE_NAME,
                MessageProperties.PERSISTENT_TEXT_PLAIN,
                message.getBytes());

        System.out.println("Sending success!");

        // 5、关闭通道、关闭连接
//        channel.close();
//        connection.close();
        RabbitMQUtils.close(channel, connection);
    }

}
