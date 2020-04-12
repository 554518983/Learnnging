package com.zjb.hive.udtf;

import org.apache.hadoop.hive.ql.exec.UDFArgumentException;
import org.apache.hadoop.hive.ql.exec.UDFArgumentLengthException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDTF;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspectorFactory;
import org.apache.hadoop.hive.serde2.objectinspector.StructObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;
import org.apache.http.util.Args;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhaojianbo
 * @date 2020/4/12 17:08
 */
public class UDTFDemo extends GenericUDTF {

    /**
     * 此方法返回UDTF的返回行的信息（返回个数，类型）
     * @param argOIs
     * @return
     * @throws UDFArgumentException
     */
    @Override
    public StructObjectInspector initialize(ObjectInspector[] argOIs) throws UDFArgumentException {
        if (argOIs.length != 1) {
            throw new UDFArgumentLengthException("ExplodeMap takes only one argument");
        }
        if (argOIs[0].getCategory() != ObjectInspector.Category.PRIMITIVE) {
            throw new UDFArgumentException("ExplodeMap takes string as a parameter");
        }
        //多出的列名
        List<String> fieldNames = new ArrayList<String>();
        //每一列的类型
        List<ObjectInspector> fieldTypes = new ArrayList<ObjectInspector>();
        fieldNames.add("c1");
        fieldTypes.add(PrimitiveObjectInspectorFactory.javaStringObjectInspector);
        fieldNames.add("c2");
        fieldTypes.add(PrimitiveObjectInspectorFactory.javaStringObjectInspector);
        return ObjectInspectorFactory.getStandardStructObjectInspector(fieldNames, fieldTypes);
    }

    /**
     * 初始化完成之后会调用process方法,真正的处理过程在process函数中，在process中，每一次forward()调用产生一行；
     * 如果产生多列可以将多个列的值放在一个数组中，然后将该数组传入到forward()函数。
     */
    @Override
    public void process(Object[] args) throws HiveException {
        //每一行数 key:value,key:value,key:value
        String input = args[0].toString();
        String[] test = input.split(",");
        for (int i = 0; i < test.length; i++) {
            try {
                String[] result = test[i].split(":");
                forward(result);
            } catch (Exception e) {
                continue;
            }
        }
    }

    @Override
    public void close() throws HiveException {

    }
}
