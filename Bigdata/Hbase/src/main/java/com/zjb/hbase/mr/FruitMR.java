package com.zjb.hbase.mr;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

/**
 * @author zhaojianbo
 * @date 2020/3/14 11:03
 */
public class FruitMR extends Configured implements Tool {

    @Override
    public int run(String[] strings) throws Exception {
        //获取conf配置文件
        Configuration conf = this.getConf();
        Job job = Job.getInstance(conf, this.getClass().getSimpleName());
        job.setJarByClass(FruitMR.class);

        //配置job
        Scan scan = new Scan();
        scan.setCacheBlocks(false);
        scan.setCaching(500);
        TableMapReduceUtil.initTableMapperJob("fruit", scan, ReadFruitMapper.class, ImmutableBytesWritable.class, Put.class, job);
        TableMapReduceUtil.initTableReducerJob("fruit_mr", WriteFruitReducer.class, job);
        job.setNumReduceTasks(1);

        boolean isSuccess = job.waitForCompletion(true);
        if (!isSuccess) {
            throw new Exception("Job running with error");
        }
        return isSuccess ? 0 : 1;
    }

    public static void main( String[] args ) throws Exception{
        Configuration conf = HBaseConfiguration.create();
        int status = ToolRunner.run(conf, new FruitMR(), args);
        System.exit(status);
    }
}
