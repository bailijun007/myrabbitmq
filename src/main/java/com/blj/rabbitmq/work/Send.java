package com.blj.rabbitmq.work;

import com.blj.rabbitmq.util.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

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
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        for (int i = 0; i < 50; i++) {
            String msg = "hello" + i;
            System.out.println("Send msg = " + msg);
            //发布消息
            channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
            Thread.sleep(i*20);
        }
        channel.close();
        connetion.close();
    }


}
