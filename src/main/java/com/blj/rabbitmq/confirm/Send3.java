package com.blj.rabbitmq.confirm;

import com.blj.rabbitmq.util.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * 生产者
 * confirm 异步模式
 *
 * @author BaiLiJun on 2019/10/28 0028
 */
public class Send3 {

    private static final String QUEUE_NAME = "test_queue_confirm3";

    public static void main(String[] args) throws Exception {
        Connection connetion = ConnectionUtils.getConnetion();
        Channel channel = connetion.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        //生产者调用confirmselect将channel 设置为conf1xm模式
        //注意confirm模式跟事务机制不能在同一个队列中
        channel.confirmSelect();

        //未确认的消息标识
        SortedSet<Long> confirmSet = Collections.synchronizedSortedSet(new TreeSet<Long>());

        //通过添加监听
        channel.addConfirmListener(new ConfirmListener() {
           //没有问题的handleAck
            @Override
            public void handleAck(long deliveryTag, boolean multip1e) throws IOException {
                if(multip1e){
                    System.out.println("----handleAck----multip1e");
                    confirmSet.headSet(deliveryTag+1).clear();
                }else {
                    System.out.println("----handleAck----multip1e false");
                    confirmSet.remove(deliveryTag);
                }
            }

           //handleNack
            @Override
            public void handleNack(long deliveryTag, boolean multip1e) throws IOException {
                if(multip1e){
                    System.out.println("----handleNack----multip1e");
                    confirmSet.headSet(deliveryTag+1).clear();
                }else {
                    System.out.println("----handleNack----multip1e false");
                    confirmSet.remove(deliveryTag);
                }
            }
        });

        String msg = "hello confirm message!";
        while (true){
            long seqNo = channel.getNextPublishSeqNo();
            channel.basicPublish("",QUEUE_NAME,null,msg.getBytes());
            confirmSet.add(seqNo);
        }

    }

}
