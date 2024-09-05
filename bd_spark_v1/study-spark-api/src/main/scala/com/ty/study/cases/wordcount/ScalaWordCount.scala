package com.ty.study.cases.wordcount

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}


object ScalaWordCount {

  def main(args: Array[String]) {

    val conf = new SparkConf().setAppName("ScalaWordCount")
    //非常重要的一个对象SparkContext
    val sc = new SparkContext(conf)

    //textFile方法生成了两个RDD： HadoopRDD[LongWritable, Text]  ->  MapPartitionRDD[String]
    val lines: RDD[String] = sc.textFile(args(0))
    //flatMap方法生成了一个MapPartitionRDD[String]
    val words: RDD[String] = lines.flatMap(_.split(" "))

    //Map方法生成了一个MapPartitionRDD[(String, Int)]
    val wordAndOne: RDD[(String, Int)] = words.map((_, 1))

    val counts: RDD[(String, Int)] = wordAndOne.reduceByKey(_+_)

    val sortedCounts: RDD[(String, Int)] = counts.sortBy(_._2, false)
    //保存的HDFS
    //sortedCounts.saveAsTextFile(args(1))
    counts.saveAsTextFile(args(1))
    //释放SparkContext
    sc.stop()

  }
}
