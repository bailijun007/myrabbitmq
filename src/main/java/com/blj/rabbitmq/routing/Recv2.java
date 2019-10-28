package com.blj.rabbitmq.routing;

import com.blj.rabbitmq.util.ConnectionUtils;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 路由模式 消费者2
 *
 * @author BaiLiJun on 2019/10/27 0027
 */
public class Recv2 {
    private static final String EXCHANGE_NAME = "test_exchange_direct";
    private static final String QUEUE_NAME = "test_queue_direct_2";

    public static void main(String[] args) throws IOException, TimeoutException {

        Connection connetion = ConnectionUtils.getConnetion();
        Channel channel = connetion.createChannel();
        //队列声明
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        //把队列绑定到交换机
        channel.queueBind(QUEUE_NAME,EXCHANGE_NAME, "error");
        channel.queueBind(QUEUE_NAME,EXCHANGE_NAME, "info");
        channel.queueBind(QUEUE_NAME,EXCHANGE_NAME, "warning");
        //保证只发送一次
        channel.basicQos(1);
        //定义消费者
        DefaultConsumer consumer = new DefaultConsumer(channel) {
            //消息到达，触发这个方法
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg = new String(body, "utf-8");
                System.out.println("[2] Recv msg = " + msg);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    System.out.println("[2] done");
                    //手动反馈
                    channel.basicAck(envelope.getDeliveryTag(), false);
                }
            }
        };
        //自动应答改成false
        boolean autoAck = false;
        //监听队列
        channel.basicConsume(QUEUE_NAME, autoAck, consumer);
    }

}
