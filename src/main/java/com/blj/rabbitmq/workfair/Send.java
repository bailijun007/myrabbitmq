package com.blj.rabbitmq.workfair;

import com.blj.rabbitmq.util.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * 生产者
 *
 * @author BaiLiJun on 2019/10/26 0026
 */
public class Send {
    public static final String QUEUE_NAME = "test_work_queue";

    public static void main(String[] args) throws Exception {
        //获取连接
        Connection connetion = ConnectionUtils.getConnetion();
        //建立通道
        Channel channel = connetion.createChannel();
        //队列声明
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);

        /**
         * 每个消费者发送确认消息之前，消息队列不发送下一条消息到消费者，一次只处理一条数据
         *
         * 限制发送给同一个消费者不得超过一条消息
         */
        int prefetchCount=1;
        channel.basicQos(prefetchCount);
        for (int i = 0; i < 50; i++) {
            String msg = "hello" + i;
            System.out.println("Send msg = " + msg);
            //发布消息
            channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
            Thread.sleep(i*5);
        }
        channel.close();
        connetion.close();
    }


}
