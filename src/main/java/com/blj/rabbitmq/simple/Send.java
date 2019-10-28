package com.blj.rabbitmq.simple;

import com.blj.rabbitmq.util.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;
/**
 * 生产者
 *
 * @author BaiLiJun on 2019/10/24 0024
 */
public class Send {
public static final String QUEUE_NAME="test_simple_queue";
    public static void main(String[] args) throws IOException, TimeoutException {
         //获取一个链接
        Connection connetion = ConnectionUtils.getConnetion();
        //从链接中获取一个通道
        Channel channel = connetion.createChannel();
        //创建队列声明
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        String msg="hello simple!";
        channel.basicPublish("",QUEUE_NAME,null,msg.getBytes());
        System.out.println("-- send msg"+msg);
        channel.close();
        connetion.close();
    }

}
