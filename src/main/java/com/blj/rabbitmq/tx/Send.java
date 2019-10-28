package com.blj.rabbitmq.tx;

import com.blj.rabbitmq.util.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 事务机制 生产者
 *
 * @author BaiLiJun on 2019/10/27 0027
 */
public class Send {
    private static final String QUEUE_NAME = "test_queue_tx";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connetion = ConnectionUtils.getConnetion();
        Channel channel = connetion.createChannel();
        //队列声明
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        String msg = "hello tx message!";

        try {
            channel.txSelect();
            channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
            //故意报错，查看是否能回滚
            int i = 1 / 0;
            System.out.println("msg = " + msg);
            channel.txCommit();
        } catch (Exception e) {
            channel.txRollback();
            System.out.println("send message txRollback!");
        }
        channel.close();
        connetion.close();


    }


}
