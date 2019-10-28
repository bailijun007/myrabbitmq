package com.blj.rabbitmq.util;


import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ConnectionUtils {

    /**
     * 获取MQ链接
     *
     * @return
     */
    public static Connection getConnetion() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        //设置服务地址
        factory.setHost("127.0.0.1");
        //设置端口号 AMQP 5672
        factory.setPort(5672);
        //VirtualHost
        factory.setVirtualHost("/test");
        //用户名
        factory.setUsername("root");
        //密码
        factory.setPassword("root1234");
        return factory.newConnection();
    }

}
