package com.ty.study.cases.urlviewcount

import java.net.URL

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}


object UrlCount1 {

  def main(args: Array[String]) {
    val conf = new SparkConf().setAppName("UrlCount1").setMaster("local[2]")
    val sc = new SparkContext(conf)
//    val lines: RDD[String] = sc.textFile(args(0))
    val lines: RDD[String] = sc.textFile("E:\\wordcount\\spark\\url_log\\access.log")
    //split
    val urlAndOne = lines.map(line => {
      val fields = line.split("\t")
      val url = fields(1)
      (url, 1)
    })
    //聚合
    val summedUrl = urlAndOne.reduceByKey(_+_).cache()

    val grouped = summedUrl.map(t => {
      val host = new URL(t._1).getHost
      (host, t._1, t._2)
    }).groupBy(_._1)

    val rseult = grouped.mapValues(_.toList.sortBy(_._3).reverse.take(3))

    println(rseult.collect().toBuffer)


    sc.stop()
  }
}
