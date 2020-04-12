package com.zjb.hive.udf;

import javafx.scene.text.Text;
import org.apache.hadoop.hive.ql.exec.UDFArgumentException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDF;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.PrimitiveObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorUtils;

import java.net.URLDecoder;

/**
 * @author zhaojianbo
 * @date 2020/4/12 16:24
 */
public class UrlDecodeUDF extends GenericUDF {

    private transient PrimitiveObjectInspector inputOI;

    /**
     * 检测参数个数以及数据类型
     * @param arguments
     * @return
     * @throws UDFArgumentException
     */
    @Override
    public ObjectInspector initialize(ObjectInspector[] arguments) throws UDFArgumentException {
        //检测参数个数
        if (arguments.length != 1) {
            throw new UDFArgumentException("only one argument");
        }
        if (arguments[0].getCategory() != ObjectInspector.Category.PRIMITIVE) {
            if (((PrimitiveObjectInspector) arguments[0]).getPrimitiveCategory() != PrimitiveObjectInspector.PrimitiveCategory.STRING) {
                throw new UDFArgumentException("argument type is fail");
            }
        }
        inputOI = (PrimitiveObjectInspector) arguments[0];
        return PrimitiveObjectInspectorFactory.writableStringObjectInspector;
    }

    @Override
    public Object evaluate(DeferredObject[] arguments) throws HiveException {
        if (arguments == null || arguments[0] == null) {
            return new Text("");
        }
        //提取数据
        String content = PrimitiveObjectInspectorUtils.getString(arguments[0].get(), inputOI);
        if (content == null || content.length() <= 0) {
            return "";
        }
        String result = "";
        content = content.replaceAll("%(?![0-9a-fA-F]{2})", "%25");
        try {
            result = URLDecoder.decode(content, "UTF-8");
        } catch (Exception e) {
            result = content;
        }
        return result;
    }

    @Override
    public String getDisplayString(String[] children) {
        StringBuilder sb = new StringBuilder();
        sb.append("url_decode");
        sb.append("(");
        if (children.length > 0) {
            sb.append(children[0]);
            for (int index = 1; index < children.length - 1; index++) {
                sb.append(",");
                sb.append(children[index]);
            }
        }
        sb.append(")");
        return sb.toString();
    }
}
