package com.blj.rabbitmq.simple;

import com.blj.rabbitmq.util.ConnectionUtils;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 消费者
 *
 * @author BaiLiJun on 2019/10/24 0024
 */
public class Recv {
    public static final String QUEUE_NAME = "test_simple_queue";

    public static void main(String[] args) throws Exception {
        //获取链接
        Connection connetion = ConnectionUtils.getConnetion();
        //创建频道
        Channel channel = connetion.createChannel();
        //创建队列声明
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        //定义消费者
        DefaultConsumer consumer = new DefaultConsumer(channel) {
           //获取到达消息
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg = new String(body, "utf-8");
                System.out.println("new api recv msg = " + msg);
            }
        };

        //监听队列
        channel.basicConsume(QUEUE_NAME, true, consumer);

    }


    private static void oldApi() throws IOException, TimeoutException, InterruptedException {
        //获取链接
        Connection connetion = ConnectionUtils.getConnetion();
        //创建频道
        Channel channel = connetion.createChannel();
        //定义队列消费者
        QueueingConsumer consumer = new QueueingConsumer(channel);
        //监听队列
        channel.basicConsume(QUEUE_NAME, true, consumer);
        while (true) {
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String msg = new String(delivery.getBody());
            System.out.println("old api Recv msg = " + msg);
        }
    }


}


