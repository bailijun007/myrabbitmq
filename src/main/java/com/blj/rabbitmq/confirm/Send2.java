package com.blj.rabbitmq.confirm;

import com.blj.rabbitmq.util.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * 生产者
 * confirm 批量模式
 *
 * @author BaiLiJun on 2019/10/28 0028
 */
public class Send2 {

    private static final String QUEUE_NAME = "test_queue_confirm1";

    public static void main(String[] args) throws Exception {
        Connection connetion = ConnectionUtils.getConnetion();
        Channel channel = connetion.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        //生产者调用confirmselect将channel 设置为conf1xm模式
        //注意confirm模式跟事务机制不能在同一个队列中
        channel.confirmSelect();
        String msg = "hello confirm message!";
        //批量发送
        for (int i = 0; i <10 ; i++) {
            channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
        }
        //确认
        if (!channel.waitForConfirms()) {
            System.out.println("confirm message send failed");
        } else {
            System.out.println("confirm message send ok");
        }
        channel.close();
        connetion.close();
    }

}
