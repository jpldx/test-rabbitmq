package com.jpldx.test.rabbitmq.workqueue;

import com.jpldx.test.rabbitmq.utils.RabbitMQUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.MessageProperties;
import org.junit.Test;

import java.io.IOException;

/**
 * "Work Queue" 模型 - 工作队列
 * 生产者 模拟发送大量消息
 *
 * @author jpldx
 */
public class Tasker1 {

    private static final String QUEUE_NAME = "work";
    private static final Integer MOCK_MSG_NUM = 10;

    @Test
    public void test() throws IOException {
        Connection connection = RabbitMQUtils.getConnection();
        Channel channel = connection.createChannel();
        // 声明队列
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);
        // 模拟发送大量消息
        for (int i = 1; i <=  MOCK_MSG_NUM; i++) {
            channel.basicPublish("",
                    QUEUE_NAME,
                    MessageProperties.PERSISTENT_TEXT_PLAIN,
                    ("message-" + i).getBytes());
        }

        System.out.println("Sending success!");
        RabbitMQUtils.close(channel, connection);
    }

}
