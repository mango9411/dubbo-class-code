package com.mango;

import com.mango.bean.ConsumerCompoent;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author mango
 * @date 2021/3/12 19:34
 * @description:
 */
public class AnnotationConsumerMain {

    public static void main(String[] args) throws Exception {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ConsumerConfiguration.class);
        context.start();

        ConsumerCompoent consumerCompoent = context.getBean(ConsumerCompoent.class);
        while (true) {
            consumerCompoent.sayHello("mango", 100);
            consumerCompoent.sayHello2("mango", 100);
            consumerCompoent.sayHello3("mango", 100);
        }
    }

    @Configuration
    @PropertySource("classpath:/dubbo-consumer.properties")
    @ComponentScan(basePackages = "com.mango.bean")
    @EnableDubbo
    static class ConsumerConfiguration {

    }
}
