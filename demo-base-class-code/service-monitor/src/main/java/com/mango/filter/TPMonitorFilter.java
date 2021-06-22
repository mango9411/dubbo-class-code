package com.mango.filter;

import com.mango.bean.MethodInfo;
import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.Filter;
import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.Result;
import org.apache.dubbo.rpc.RpcException;
import org.springframework.util.CollectionUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author mango
 * @date 2021/6/22 15:54
 * @description:
 */

@Activate(group = {CommonConstants.CONSUMER})
public class TPMonitorFilter implements Filter, Runnable {

    Map<String, List<MethodInfo>> methodTimes = new ConcurrentHashMap<>();

    public TPMonitorFilter() {
        //每隔5秒打印线程使用情况
        Executors.newSingleThreadScheduledExecutor()
                .scheduleWithFixedDelay(this, 5, 5, TimeUnit.SECONDS);
    }

    @Override
    public void run() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String dateStr = sdf.format(date);
        for (Map.Entry<String, List<MethodInfo>> methodInfos : methodTimes.entrySet()) {
            System.out.println(dateStr + methodInfos.getKey() + "的TP90:" + getTP(methodInfos.getValue(), 0.9) + "毫秒,"
                    + "TP99:" + getTP(methodInfos.getValue(), 0.99) + "毫秒");
        }
    }

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        Result result = null;
        Long takeTime = 0L;
        try {
            Long startTime = System.currentTimeMillis();
            result = invoker.invoke(invocation);
            if (result.getException() instanceof Exception) {
                throw new Exception(result.getException());
            }
            takeTime = System.currentTimeMillis() - startTime;
        } catch (Exception e) {
            e.printStackTrace();
            return result;
        }
        String methodName = invocation.getMethodName();
        List<MethodInfo> methodInfos = methodTimes.get(methodName);
        if (CollectionUtils.isEmpty(methodInfos)) {
            methodInfos = new ArrayList<>();
            methodTimes.put(methodName, methodInfos);
        }
        methodInfos.add(new MethodInfo(methodName, takeTime, System.currentTimeMillis()));
        return result;
    }

    private Long getTP(List<MethodInfo> methodInfos, double rate) {
        long endTime = System.currentTimeMillis();
        long startTime = System.currentTimeMillis() - 60000;
        List<MethodInfo> sortInfo = methodInfos
                .stream()
                .filter(m -> m.getEndTimes() >= startTime && m.getEndTimes() <= endTime)
                .collect(Collectors.toList());
        sortInfo.sort(new Comparator<MethodInfo>() {
            @Override
            public int compare(MethodInfo o1, MethodInfo o2) {
                if (o1.getTimes() > o2.getTimes()) {
                    return 1;
                } else if (o1.getTimes() < o2.getTimes()) {
                    return -1;
                } else {
                    return 0;
                }
            }
        });
        int index = (int) (sortInfo.size() * rate);

        return sortInfo.get(index).getTimes();
    }
}
