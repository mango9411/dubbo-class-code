package com.mango.service.impl;

import com.mango.service.HelloService;
import org.apache.dubbo.config.annotation.Service;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author mango
 * @date 2021/3/11 21:16
 * @description:
 */
@Service
public class HellpServiceImpl implements HelloService {

    @Override
    public String sayHello(String name, Integer timeToWait) {
        int random = ThreadLocalRandom.current().nextInt(timeToWait);
        try {
            Thread.sleep(random);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "hello: " + name;
    }

    @Override
    public String sayHello2(String name, Integer timeToWait) {
        int random = ThreadLocalRandom.current().nextInt(timeToWait);
        try {
            Thread.sleep(random);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "hello2: " + name;
    }

    @Override
    public String sayHello3(String name, Integer timeToWait) {
        int random = ThreadLocalRandom.current().nextInt(timeToWait);
        try {
            Thread.sleep(random);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "hello3: " + name;
    }
}
