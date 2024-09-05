package com.ty.study.cases.urlviewcount

import java.net.URL

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}


object UrlCount2 {

  def main(args: Array[String]) {
    val urls = Array("http://java.tong.cn", "http://php.tong.cn", "http://net.tong.cn")

    val conf = new SparkConf().setAppName("UrlCount1").setMaster("local[2]")
    val sc = new SparkContext(conf)
    //val lines: RDD[String] = sc.textFile(args(0))
    val lines: RDD[String] = sc.textFile("E:\\wordcount\\spark\\url_log\\access.log")
    //split
    val urlAndOne = lines.map(line => {
      val fields = line.split("\t")
      val url = fields(1)
      (url, 1)
    })
    //聚合
    val summedUrl = urlAndOne.reduceByKey(_+_)

    //循环过滤
    for(u <- urls) {
      val insRdd = summedUrl.filter(t => {
        val url = t._1
        url.startsWith(u)
      })
      val result = insRdd.sortBy(_._2, false).take(3)
      println(result.toBuffer)
    }

    sc.stop()
  }
}
