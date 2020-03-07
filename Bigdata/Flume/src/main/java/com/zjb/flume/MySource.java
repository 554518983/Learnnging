package com.zjb.flume;

import org.apache.flume.Context;
import org.apache.flume.EventDeliveryException;
import org.apache.flume.PollableSource;
import org.apache.flume.conf.Configurable;
import org.apache.flume.event.SimpleEvent;
import org.apache.flume.source.AbstractSource;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhaojianbo
 * @date 2020/3/7 20:59
 */
public class MySource extends AbstractSource implements Configurable, PollableSource {

    private Long delay;
    private String field;

    /**
     * 配置信息
     * @param context
     */
    @Override
    public void configure(Context context) {
        //获取配置信息
        delay = context.getLong("delay");
        field = context.getString("field", "Hello!");
    }

    @Override
    public Status process() throws EventDeliveryException {
        try {
            //创建事件头信息
            Map<String, String> headerMap = new HashMap<>();
            //创建事件
            SimpleEvent event = new SimpleEvent();
            for (int i = 0; i < 5; i++) {
                //给事件添加头信息
                event.setHeaders(headerMap);
                //给事件设置内容
                event.setBody((field + i).getBytes());
                //将事件写入到channel中
                getChannelProcessor().processEvent(event);
                Thread.sleep(delay);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Status.BACKOFF;
        }
        return Status.READY;
    }

    @Override
    public long getBackOffSleepIncrement() {
        return 0;
    }

    @Override
    public long getMaxBackOffSleepInterval() {
        return 0;
    }
}
