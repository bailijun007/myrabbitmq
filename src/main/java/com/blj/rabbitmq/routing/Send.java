package com.blj.rabbitmq.routing;

import com.blj.rabbitmq.util.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 路由模式 生产者
 *
 * @author BaiLiJun on 2019/10/27 0027
 */
public class Send {
    private static final String EXCHANGE_NAME = "test_exchange_direct";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connetion = ConnectionUtils.getConnetion();
        Channel channel = connetion.createChannel();
        //声明交换机  Direct(处理路由键)
        channel.exchangeDeclare(EXCHANGE_NAME, "direct");

        String msg = "hello direct";

        String routingKey = "info";
        channel.basicPublish(EXCHANGE_NAME, routingKey, null, msg.getBytes());
        System.out.println("Send msg = " + msg);
        channel.close();
        connetion.close();

    }


}
