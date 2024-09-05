package com.ty.study.cases.mobileLocation

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  * 数据文件:
  * 1.bs_log:手机连接基站的日志 （数据已简化）
  * 手机号，连接时长，基站id，连接状态：1（连接） 2（断开）
  * 18611132889,20160327075000,9F36407EAD0629FC166F14DDE7970F68,1
  * 18688888888,20160327075100,9F36407EAD0629FC166F14DDE7970F68,1
  *
  * 2.基站基本信息 lac_info.txt （数据已简化）
  * 基站id，经度，纬度，...
  * 9F36407EAD0629FC166F14DDE7970F68,116.304864,40.050645,6
  *
  * 需求：计算每个用户在在哪两个基站连接时间最长
  *
  * Created by bigDate on 2016/8/22.
  */
object MobileLocation {
  def main(args: Array[String]) {
    //创建SparkContext
    val conf = new SparkConf().setAppName("MobileLocation").setMaster("local")
    val sc = new SparkContext(conf)

    //读取手机连接日志信息
    //val lines: RDD[String] = sc.textFile(args(0))
    val lines: RDD[String] = sc.textFile("E:\\wordcount\\spark\\bs_log")
    //切分
    //lines.map(_.split(",")).map(arr => (arr(0), arr(1).toLong, arr(2), args(3)))
    val splited = lines.map(line => {
      val fields = line.split(",")
      val mobile = fields(0)
      val lac = fields(2)
      val tp = fields(3)
      //此计算用于聚合：连接时间 = 断开的时刻 - 连接的时刻
      val time = if(tp == "1") -fields(1).toLong else fields(1).toLong
      //拼接数据
      ((mobile, lac), time)
    })
    //分组聚合，计算出连接时间的集合
    val reduced : RDD[((String, String), Long)] = splited.reduceByKey(_+_)
    //println(reduced.collect().toBuffer)

    //为连接`基站信息`数据，作手机连接信息的数据转换，连接规则为基站id
    val lmt = reduced.map(x => {
      //（基站，（手机号， 时间））
      (x._1._2, (x._1._1, x._2))
    })

    //加载基站数据
    //val lacInfo: RDD[String] = sc.textFile(args(1))
    val lacInfo: RDD[String] = sc.textFile("E:\\wordcount\\spark\\mobileSit\\lac_info.txt")
    //整理基站数据
    val splitedLacInfo = lacInfo.map(line => {
      val fields = line.split(",")
      val id = fields(0)
      val x = fields(1)
      val y = fields(2)
      (id, (x, y))   //（基站id，（位置经度，位置纬度））
    })

    //连接jion  : (K,V)结构的数据才能jion
    val joined: RDD[(String, ((String, Long), (String, String)))] = lmt.join(splitedLacInfo)
    //println(joined.collect.toBuffer)

    //根据手机号进行分组  分组后的数据结构为 String，(String, ((String, Long), (String, String)))
    val groupedByMobile = joined.groupBy(_._2._1._1)
    //println(groupedByMobile.collect.toBuffer)

    //根据连接时长倒序排序，取出连接时常最长的前两个
    val result = groupedByMobile.mapValues(_.toList.sortBy(_._2._1._2).reverse.take(2))
    println(result.collect().toBuffer)
    sc.stop()

  }

}
