package com.zjb.hive.udaf;

import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDAFEvaluator;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.PrimitiveObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorUtils;
import org.apache.hadoop.io.LongWritable;

/**
 * @author zhaojianbo
 * @date 2020/4/12 16:50
 */
public class GenericUDAFSumLong extends GenericUDAFEvaluator {

    private PrimitiveObjectInspector inputOI;
    private LongWritable result;
    private boolean warned = false;

    /**
     * 存储sum的值的类
     */
    static class SumLongAgg implements AggregationBuffer {
        boolean empty;
        long sum;
    }

    /**
     * 这个方法返回了UDAF的返回类型，这里确定了sum自定义函数的返回类型是Long类型
     * @param m
     * @param parameters
     * @return
     * @throws HiveException
     */
    @Override
    public ObjectInspector init(Mode m, ObjectInspector[] parameters) throws HiveException {
        assert (parameters.length == 1);
        super.init(m, parameters);
        result = new LongWritable(0);
        inputOI = (PrimitiveObjectInspector) parameters[0];
        return PrimitiveObjectInspectorFactory.writableLongObjectInspector;
    }

    /**
     * 创建新的聚合计算的需要的内存，用来存储mapper,combiner,reducer运算过程中的相加总和
     * @return
     * @throws HiveException
     */
    @Override
    public AggregationBuffer getNewAggregationBuffer() throws HiveException {
        SumLongAgg result = new SumLongAgg();
        reset(result);
        return result;
    }

    /**
     * mapreduce支持mapper和reducer的重用，所以为了兼容，也需要做内存的重用。
     * @param agg
     * @throws HiveException
     */
    @Override
    public void reset(AggregationBuffer agg) throws HiveException {
        SumLongAgg myAgg = (SumLongAgg) agg;
        myAgg.empty = true;
        myAgg.sum = 0;
    }

    /**
     * map阶段调用，只要把保存当前和的对象agg，再加上输入的参数，就可以了。
     * @param agg
     * @param parameters
     * @throws HiveException
     */
    @Override
    public void iterate(AggregationBuffer agg, Object[] parameters) throws HiveException {
        assert parameters.length == 1;
        try {
            merge(agg, parameters[0]);
        } catch (Exception e) {
            if (!warned) {
                warned = true;
            }
        }
    }

    /**
     * mapper结束要返回的结果，还有combiner结束返回的结果
     * @param agg
     * @return
     * @throws HiveException
     */
    @Override
    public Object terminatePartial(AggregationBuffer agg) throws HiveException {
        return terminate(agg);
    }

    @Override
    public void merge(AggregationBuffer agg, Object partial) throws HiveException {
        if (partial != null) {
            SumLongAgg sAgg = (SumLongAgg) agg;
            sAgg.sum += PrimitiveObjectInspectorUtils.getLong(partial, inputOI);
            ((SumLongAgg) agg).empty = false;
        }
    }

    /**
     * reducer返回结果，或者是只有mapper，没有reducer时，在mapper端返回结果。
     * @param agg
     * @return
     * @throws HiveException
     */
    @Override
    public Object terminate(AggregationBuffer agg) throws HiveException {
        SumLongAgg sAgg = (SumLongAgg) agg;
        if (sAgg.empty) {
            return null;
        }
        result.set(sAgg.sum);
        return result;
    }
}
