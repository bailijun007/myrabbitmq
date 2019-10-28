package com.blj.rabbitmq.topic;

import com.blj.rabbitmq.util.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * topic 生产者
 *
 * @author BaiLiJun on 2019/10/27 0027
 */
public class Send {
    private static final String EXCHANGE_NAME = "test_exchange_topic";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connetion = ConnectionUtils.getConnetion();
        Channel channel = connetion.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, "topic");
        String msg = "商品。。。";
       // String routingKey = "goods.add";
        String routingKey = "goods.update";
        channel.basicPublish(EXCHANGE_NAME, routingKey, null, msg.getBytes());
        System.out.println("Send msg = " + msg);
        channel.close();
        connetion.close();


    }
}
