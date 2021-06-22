package com.mango.bean;

import lombok.Data;

/**
 * @author mango
 * @date 2021/6/22 16:49
 * @description:
 */
@Data
public class MethodInfo {

    /**
     * 方法名
     */
    private String name;

    /**
     * 执行耗时
     */
    private Long times;

    /**
     * 结束时间
     */
    private Long endTimes;

    public MethodInfo() {

    }

    public MethodInfo(String name, Long times, Long endTimes) {
        this.name = name;
        this.times = times;
        this.endTimes = endTimes;
    }
}
