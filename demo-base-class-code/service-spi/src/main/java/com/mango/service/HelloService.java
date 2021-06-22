package com.mango.service;

/**
 * @author mango
 * @date 2021/3/12 19:21
 * @description:
 */
public interface HelloService {

    /**
     *
     * @param name
     * @param timeToWait
     * @return
     */
    String sayHello(String name, Integer timeToWait);

    /**
     *
     * @param name
     * @param timeToWait
     * @return
     */
    String sayHello2(String name, Integer timeToWait);

    /**
     *
     * @param name
     * @param timeToWait
     * @return
     */
    String sayHello3(String name, Integer timeToWait);
}
