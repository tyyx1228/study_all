package com.ty.study

import java.net.URL

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}


/**
 * 需求：web访问日志：求每个网站下各url的访问次数
 * eg：
 * www.baidu.com/index
 * 网站：www.baidu.com
 * url: www.baidu.com/index
 *
 * 数据格式：
 * 2016132501201201 http://java.itcast.cn/index
 * 2016132531201201	http://php.itcast.cn/php/1/share
 * 2016132531301201	http://net.itcast.cn/sdf/ils/lov/kii
 * 2016132501201201	http://java.itcast.cn/index
 *
 * Created by relax on 2016/8/23.
 */
object HostViewCount {

  def main(args: Array[String]) {
    //创建sparkContext
    val conf = new SparkConf().setAppName("HostViewCount").setMaster("local[4]")
    val sc: SparkContext = new SparkContext(conf)

    /**
     * 数据格式:
     * 以\t分割
     *    2016132501201201	http://java.itcast.cn/index
     */
    val line: RDD[String] = sc.textFile("E:\\wordcount\\spark\\url_log\\access.log")

    //取出url，对每个url计数一
    val step1 = line.map(line => {
      val fields: Array[String] = line.split("\t")
      val url = fields(1)
      (url,1)
    })
    //对各个不同的url汇总求和，即：url的访问次数
    val step2: RDD[(String, Int)] = step1.reduceByKey(_+_)

    //根据url取出网站名称
    val step3: RDD[(String, Iterable[(String, String, Int)])] = step2.map(u => {
      val url = new URL(u._1)
      val host = url.getHost
      (host,u._1,u._2)
    }).groupBy(_._1)  //根据host主机进行分组，分组后的数据结构 ( host,((host,url,count),(host,url,count),.....) ),  ( host,(host,url,count) )

    //根据
    val step4 = step3.mapValues(_.toList.sortBy(_._3).reverse)
    println(step4.collect().toBuffer)
  }
}
