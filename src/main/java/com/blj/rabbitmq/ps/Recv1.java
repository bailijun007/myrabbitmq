package com.blj.rabbitmq.ps;

import com.blj.rabbitmq.util.ConnectionUtils;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 订阅模式 消费者1
 *
 * @author BaiLiJun on 2019/10/26 0026
 */
public class Recv1 {
    private static final String EXCHANGE_NAME = "test_exchange_fanout";
    private static final String QUEUE_NAME = "test_queue_fanout_email";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connetion = ConnectionUtils.getConnetion();
         Channel channel = connetion.createChannel();
        //声明队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        //绑定队列到交换机
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "");
        //保证只发送一次
        channel.basicQos(1);
        //定义消费者
        DefaultConsumer consumer = new DefaultConsumer(channel) {
            //消息到达，触发这个方法
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg = new String(body, "UTF-8");
                System.out.println("[1] Recv msg = " + msg);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    System.out.println("[1] done ");
                    //处理完消息，手动回执
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
