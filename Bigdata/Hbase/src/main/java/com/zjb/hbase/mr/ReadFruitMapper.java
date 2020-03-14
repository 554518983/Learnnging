package com.zjb.hbase.mr;

import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableMapper;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;

/**
 * @author zhaojianbo
 * @date 2020/3/14 10:48
 */
public class ReadFruitMapper extends TableMapper<ImmutableBytesWritable, Put> {

    @Override
    protected void map(ImmutableBytesWritable key, Result value, Context context) throws IOException, InterruptedException {
        //将fruit的name和color取出来，相当于将每一行数据读出来放入到put对象中
        Put put = new Put(key.get());
        for (Cell cell : value.rawCells()) {
            //添加/克隆列族：info
            if ("info".equals(Bytes.toString(CellUtil.cloneFamily(cell)))) {
                //添加/克隆列:name
                if ("name".equals(Bytes.toString(CellUtil.cloneQualifier(cell)))) {
                    put.add(cell);
                } else if ("color".equals(Bytes.toString(CellUtil.cloneQualifier(cell)))) {
                    put.add(cell);
                }
            }
        }
        context.write(key, put);
    }
}
