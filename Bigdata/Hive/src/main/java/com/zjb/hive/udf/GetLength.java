package com.zjb.hive.udf;

import org.apache.hadoop.hive.ql.exec.UDF;

/**
 * @author zhaojianbo
 * @date 2020/4/12 16:14
 */
public class GetLength extends UDF {
    public int evaluate(String str) {
        try {
            return str.length();
        } catch (Exception e) {
            return -1;
        }
    }
}
