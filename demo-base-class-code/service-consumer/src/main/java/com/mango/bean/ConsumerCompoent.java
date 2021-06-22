package com.mango.bean;

import com.mango.service.HelloService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Component;

/**
 * @author mango
 * @date 2021/3/12 19:31
 * @description:
 */
@Component
public class ConsumerCompoent {

    @Reference
    private HelloService helloService;

    public String sayHello(String name, Integer timeToWait) {

        return helloService.sayHello(name, timeToWait);
    }

    public String sayHello2(String name, Integer timeToWait) {

        return helloService.sayHello2(name, timeToWait);
    }

    public String sayHello3(String name, Integer timeToWait) {

        return helloService.sayHello3(name, timeToWait);
    }
}
