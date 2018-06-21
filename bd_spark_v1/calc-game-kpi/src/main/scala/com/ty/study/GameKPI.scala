package com.ty.study

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by root on 2016/7/10.
  */
object GameKPI {
  def main(args: Array[String]) {

    val queryTime = "2016-02-01 00:00:00"
    val beginTime = TimeUtils(queryTime)

    val endTime = TimeUtils.getCertainDayTime(+1)
    //t1  02-02
    val t1 = TimeUtils.getCertainDayTime(+1)
    //t2  02-03
    val t2 = TimeUtils.getCertainDayTime(+2)

    val conf = new SparkConf().setAppName("GameKPI").setMaster("local")
    //非常重要的一个对象SparkContext
    val sc = new SparkContext(conf)

    val splitedLog: RDD[Array[String]] = sc.textFile("E:\\WorkSpace\\idea\\study_all1\\bd_spark_v1\\calc-game-kpi\\data\\GameLog.txt").map(_.split("\\|"))

    //新增用户
    class FilterUtils


//    splitedLog.filter(_(0) == "1").filter(x => {
//      val time = x(1)
//      //不好，每filter一次就会new一个SimpleDateFormat实例，会浪费资源
//      val sdf = new SimpleDateFormat("yyyy年MM月dd日,E,HH:mm:ss")
//      val timeLong = sdf.parse(time).getTime
//    })


    //过滤后并缓冲
    val filteredLogs = splitedLog.filter(fields => FilterUtils.filterByTime(fields, beginTime, endTime))
      .cache()

    //日新增用户数，Daily New Users 缩写 DNU
    val dnu: RDD[Array[String]] = filteredLogs.filter(arr => FilterUtils.filterByType(arr, EventType.REGISTER))

    println(dnu.count())


    //日活跃用户数 DAU （Daily Active Users）
    val dau = filteredLogs.filter(arr => FilterUtils.filterByTypes(arr, EventType.REGISTER, EventType.LOGIN))
      .map(_(3)).distinct()

    println(dau.count())

    //（次日留存）用户留存
    val dnuMap: RDD[(String, Int)] = dnu.map(arr =>(arr(3), 1))
    val d2Login: RDD[Array[String]] = splitedLog.filter(arr => FilterUtils.filterByTypeAndTime(arr, EventType.LOGIN, t1, t2))

    val d2UnameMap: RDD[(String, Int)] = d2Login.map(_(3)).distinct().map((_, 1))

//    //  留存率：某段时间的新增用户数记为A，经过一段时间后，仍然使用的用户占新增用户A的比例即为留存率
//    //  次日留存率（Day 1 Retention Ratio） Retention [rɪ'tenʃ(ə)n] Ratio ['reɪʃɪəʊ]
//    //  日新增用户在+1日登陆的用户占新增用户的比例
    val d1rr: RDD[(String, (Int, Int))] = dnuMap.join(d2UnameMap)


    val rdda = sc.parallelize(Array("a", "b", "c"))

    val rddb = sc.parallelize(Array("a", "d", "e", "f"))

    val r = rdda.subtract(rddb).collect().toBuffer

    println(r)

//    println(d1rr.collect().toBuffer)
//
  //println(d1rr.count())
//
//    println("dnu" + dnu.count())
//
//    println("dau" + dau.count())

    sc.stop()
  }
}
