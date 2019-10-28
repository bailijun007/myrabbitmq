package com.blj.rabbitmq.confirm;

import com.blj.rabbitmq.util.ConnectionUtils;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 消费者1
 * confirm 普通模式
 *
 * @author BaiLiJun on 2019/10/27 0027
 */
public class Recv1 {
    private static final String QUEUE_NAME = "test_queue_confirm1";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connetion = ConnectionUtils.getConnetion();
        Channel channel = connetion.createChannel();
        //队列声明
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        //声明消费者
        DefaultConsumer consumer = new DefaultConsumer(channel) {
            //消息到达，触发此方法
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg = new String(body, "utf-8");
                System.out.println("[confirm] Recv msg = " + msg);
            }
        };

        //监听队列
        channel.basicConsume(QUEUE_NAME, true, consumer);

    }

}
