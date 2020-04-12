package com.zjb.hive.udaf;

import org.apache.hadoop.hive.ql.exec.UDFArgumentTypeException;
import org.apache.hadoop.hive.ql.parse.SemanticException;
import org.apache.hadoop.hive.ql.udf.generic.AbstractGenericUDAFResolver;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDAFEvaluator;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.typeinfo.PrimitiveTypeInfo;
import org.apache.hadoop.hive.serde2.typeinfo.TypeInfo;

/**
 * @author zhaojianbo
 * @date 2020/4/12 16:44
 */
public class UDAFDemo extends AbstractGenericUDAFResolver {
    /**
     * 检验参数
     * @param info
     * @return
     * @throws SemanticException
     */
    @Override
    public GenericUDAFEvaluator getEvaluator(TypeInfo[] info) throws SemanticException {
        if (info.length != 1) {
            throw new SemanticException("arguments is fail");
        }
        if (info[0].getCategory() != ObjectInspector.Category.PRIMITIVE) {
            throw new SemanticException("arguments is fail");
        }
        switch (((PrimitiveTypeInfo) info[0]).getPrimitiveCategory()) {
            case BYTE:
            case SHORT:
            case INT:
            case LONG:
            case TIMESTAMP:
                return new GenericUDAFSumLong();
            case FLOAT:
            case DOUBLE:
            case BOOLEAN:
            default:
                throw new UDFArgumentTypeException(0, "Only numeric or string type arguments are accepted but " + info[0].getTypeName() + " is passed.");
        }
    }
}
