package com.ty.study.cases.urlviewcount

import java.net.URL

import org.apache.spark.rdd.RDD
import org.apache.spark.{Partitioner, SparkConf, SparkContext}

import scala.collection.mutable

/**
  * Created by tongyu on 2016/8/23.
  */
object UrlCount3 {

  def main(args: Array[String]) {
    val conf = new SparkConf().setAppName("UrlCount1").setMaster("local[2]")
    val sc = new SparkContext(conf)
    val lines: RDD[String] = sc.textFile("E:\\wordcount\\spark\\url_log\\access.log")
    //split
    val urlAndOne = lines.map(line => {
      val fields = line.split("\t")
      val url = fields(1)
      (url, 1)
    })
    //聚合
    val summedUrl = urlAndOne.reduceByKey(_+_).cache()

    val rdd1 = summedUrl.map(t => {
      val host = new URL(t._1).getHost
      (host, (t._1, t._2))
    })


    val urls = rdd1.map(_._1).distinct().collect   //筛选出全部的host作为新的集合

    val partitioner = new HostPartitioner(urls)
    //安装自定义的分区器重新分区
    val partitionedRdd : RDD[(String, (String, Int))] = rdd1.partitionBy(partitioner)


    val result = partitionedRdd.mapPartitions(it => {
      it.toList.sortBy(_._2._2).reverse.take(3).iterator
    })

    //result.saveAsTextFile("c://out000")
    println(result.collect.toBuffer)
    sc.stop()
  }
}

/**
 * 自定义分区规则：有多少urlHost就有多少个分区，每个urlHost对应一个分区编号
 * @param urls
 */
class HostPartitioner(urls: Array[String]) extends Partitioner {

  val rules = new mutable.HashMap[String, Int]()
  var index = 0
  for(url <- urls) {
    rules.put(url, index)
    index += 1
  }

  override def getPartition(key: Any): Int = {
    val url = key.toString
    rules.getOrElse(url, 0)
  }

  override def numPartitions: Int = urls.length
}
