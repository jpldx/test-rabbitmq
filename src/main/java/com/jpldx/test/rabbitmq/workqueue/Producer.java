package com.jpldx.test.rabbitmq.workqueue;

import com.jpldx.test.rabbitmq.utils.RabbitMqUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.MessageProperties;
import org.junit.Test;

import java.io.IOException;

/**
 * "Work Queue" 模型 - 工作队列
 * 生产者
 *
 * @author chen.xudong
 * @version 1.0
 * @since 2021/1/18
 */
public class Producer {
    private static final Integer MOCK_MSG_NUM = 10;

    @Test
    public void test() throws IOException {

        Connection connection = RabbitMqUtils.getConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare("work", true, false, false, null);
        // 模拟生产多条消息
        for (int i = 1; i <=  MOCK_MSG_NUM; i++) {
            channel.basicPublish("","work", MessageProperties.PERSISTENT_TEXT_PLAIN,("hello work queue: " + i).getBytes());
        }

        RabbitMqUtils.close(channel, connection);
    }

}
