package com.zjb.spark

import org.apache.spark.{SparkConf, SparkContext}

/**
  * @author zhaojianbo
  * @date 2020/4/28 22:03
  */
object WordCount {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
    conf.setAppName("WC")
//    conf.setMaster("local[*]")
    val sc = new SparkContext(conf)
    val tuples = sc.textFile("Bigdata/Spark/in").flatMap(_.split(" ")).map((_, 1)).reduceByKey(_+_).sortBy(_._2, false).collect()
    tuples.foreach(println)
    sc.stop()
  }
}
