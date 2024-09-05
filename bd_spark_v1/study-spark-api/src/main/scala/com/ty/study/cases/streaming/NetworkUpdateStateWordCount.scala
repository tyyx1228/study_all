package com.ty.study.cases.streaming

import org.apache.spark.{HashPartitioner, SparkConf}
import org.apache.spark.streaming.{StreamingContext, Seconds}

object NetworkUpdateStateWordCount {
  /**
    * String : 单词 hello
    *
    * Seq[Int] ：单词在当前批次出现的次数
    * Option[Int] ： 历史结果
    */
  val updateFunc = (iter: Iterator[(String, Seq[Int], Option[Int])]) => {
    //iter.flatMap(it=>Some(it._2.sum + it._3.getOrElse(0)).map(x=>(it._1,x)))

    iter.flatMap{case(x,y,z)=>Some(y.sum + z.getOrElse(0)).map(m=>(x, m))}
  }

  def main(args: Array[String]) {
    LoggerLevels.setStreamingLogLevels
    val conf = new SparkConf().setMaster("local[2]").setAppName("TCPDemo")
    val ssc = new StreamingContext(conf, Seconds(2))
    //做checkpoint 写入共享存储中
    ssc.checkpoint("c://aaa")
    val lines = ssc.socketTextStream("192.168.122.10", 9999)
    //reduceByKey 结果无法累加，只能计算本批次输入的数据
    //val result = lines.flatMap(_.split(" ")).map((_, 1)).reduceByKey(_+_)

    //updateStateByKey结果可以累加但是需要传入一个自定义的累加函数：updateFunc
    val results = lines.flatMap(_.split(" ")).map((_,1)).updateStateByKey(updateFunc, new HashPartitioner(ssc.sparkContext.defaultParallelism), true)
    results.print()
    ssc.start()
    ssc.awaitTermination()
  }
}