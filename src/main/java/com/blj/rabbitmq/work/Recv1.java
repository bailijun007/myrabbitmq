package com.blj.rabbitmq.work;

import com.blj.rabbitmq.util.ConnectionUtils;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 消费者1
 *
 * @author BaiLiJun on 2019/10/26 0026
 */
public class Recv1 {
    public static final String QUEUE_NAME = "test_work_queue";

    public static void main(String[] args) throws Exception {
        //获取连接
        Connection connetion = ConnectionUtils.getConnetion();
        //建立通道
        Channel channel = connetion.createChannel();
        //队列声明
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        //定义消费者
        DefaultConsumer consumer = new DefaultConsumer(channel) {
            //消息到达，触发这个方法
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg = new String(body, "utf-8");
                System.out.println("[1] Recv msg = " + msg);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    System.out.println("[1] done");
                }
            }
        };

        //监听队列
        boolean autoAck=true;
        channel.basicConsume(QUEUE_NAME,autoAck,consumer);
    }


}
