package com.shsxt;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * Hello world!
 *
 */
public class Starter {
    public static void main( String[] args )
    {
        ClassPathXmlApplicationContext ac=new ClassPathXmlApplicationContext(
                "spring/applicationContext-dao.xml",
                "spring/applicationContext-service.xml",
                "spring/applicationContext-tx.xml",
                "spring/applicationContext-redis.xml",
                "spring/applicationContext-dubbo.xml");
        ac.start();
        //阻塞程序的运行
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ac.stop();
    }
}
