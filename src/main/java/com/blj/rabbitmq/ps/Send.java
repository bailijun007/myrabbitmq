package com.blj.rabbitmq.ps;

import com.blj.rabbitmq.util.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 订阅模式 生产者
 *
 * @author BaiLiJun on 2019/10/26 0026
 */
public class Send {
    private static final String EXCHANGE_NAME="test_exchange_fanout";
    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connetion = ConnectionUtils.getConnetion();
        Channel channel = connetion.createChannel();
        //声明交换机     fanout分发
        channel.exchangeDeclare(EXCHANGE_NAME,"fanout");
        String msg="hello ps!";
        System.out.println("send msg = " + msg);
        channel.basicPublish(EXCHANGE_NAME,"",null,msg.getBytes());
        channel.close();
        connetion.close();
    }
}
