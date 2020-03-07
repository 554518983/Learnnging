package com.zjb.flume;

import org.apache.flume.*;
import org.apache.flume.conf.Configurable;
import org.apache.flume.sink.AbstractSink;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zhaojianbo
 * @date 2020/3/7 21:36
 */
public class MySink extends AbstractSink implements Configurable {

    private static final Logger log = LoggerFactory.getLogger(MySink.class);

    private String prefix;
    private String suffix;

    @Override
    public void configure(Context context) {
        prefix = context.getString("prefix", "hello");
        suffix = context.getString("suffix");
    }

    @Override
    public Status process() throws EventDeliveryException {
        //声明返回值状态信息
        Status status;
        //获取当前sink绑定的channel
        Channel channel = getChannel();
        //获取事物
        Transaction transaction = channel.getTransaction();
        //声明事件
        Event event;
        //开启事物
        transaction.begin();
        try {
            Event take;
            while ((take = channel.take()) == null) {
                Thread.sleep(200);
            }
            //到这里我们就拿到数据了
            log.info(prefix + new String(take.getBody()) + suffix);
            transaction.commit();
            status = Status.READY;
        } catch (Throwable e) {
            transaction.rollback();
            status = Status.BACKOFF;
            if (e instanceof Error) {
                throw (Error) e;
            }
        } finally {
            transaction.close();
        }

        return status;
    }

}
